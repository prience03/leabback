package com.duolebo.tvui.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Shader;
import android.os.AsyncTask;
import android.os.Handler;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.ViewTreeObserver.OnPreDrawListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.duolebo.tvui.utils.UIUtils;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DynamicWin8View extends FocusRelativeLayout {

    private Context context;
    private int maxRow = 0;
    private int maxCol = 0;
    private int currRow = 0;
    private int currCol = 0;
    private int hgap;
    private int vgap;
    private boolean showMirror = false;

    private int table[][];

    private int freezeCols = 0;
    private int freezeRows = 0;

    private static final int VIEW_ID_AVAILABLE = 0;
    private static final int VIEW_ID_FROZEN = -1;

    public static Map<String,String> itemPositionMap = new HashMap<>();
    private int positonCurrRow = 0;
    private int positonCurrCol = 0;
    private String positionMenuName;
    private Map<Integer, Integer> diffMap = new HashMap<>();

    public String getPositionMenuName() {
        return positionMenuName;
    }

    public void setPositionMenuName(String positionMenuName) {
        this.positionMenuName = positionMenuName;
    }


    public static class ViewPosInfo {
        View view = null;
        int rowStart = 0;
        int colStart = 0;
        int rowSpan = 1;
        int colSpan = 1;
        int index = 0;

        static class SnapShot {
            int top = 0;
            int left = 0;
            public SnapShot(int top, int left) {
                this.top = top;
                this.left = left;

                //Log.i("SnapShot top:" + top + ", left:" + left);
            }
        }

        List<SnapShot> snapShots = new ArrayList<SnapShot>();

        public ViewPosInfo(View view, int index, int rowSpan, int colSpan) {
            this.view = view;
            this.index = index;
            this.rowSpan = rowSpan;
            this.colSpan = colSpan;
        }

        ViewPosInfo(View view, int rowStart, int colStart, int rowSpan, int colSpan) {
            this.view = view;
            this.rowStart = rowStart;
            this.colStart = colStart;
            this.rowSpan = rowSpan;
            this.colSpan = colSpan;
        }

        void takeSnapShot() {
            if (null == view) return;
            snapShots.add(new SnapShot(view.getTop(), view.getLeft()));
        }

        void animLastSnapShot() {
            animLastSnapShot(0);
        }

        @SuppressLint("NewApi")
        void animLastSnapShot(long startDelay) {
            if (0 == snapShots.size()) {
                view.setScaleX(0.5f);
                view.setScaleY(0.5f);
                view.setAlpha(0.f);
                view.animate().alpha(1.f).scaleX(1.f).scaleY(1.f).setDuration(500).setStartDelay(startDelay).start();
            } else {
                SnapShot ss = snapShots.get(snapShots.size()-1);
                view.setTranslationX(ss.left - view.getLeft());
                view.setTranslationY(ss.top - view.getTop());
                view.animate().translationX(0).translationY(0).setDuration(500).setStartDelay(startDelay).start();
            }
        }
    }

    private List<ViewPosInfo> allViewPosInfo = new ArrayList<ViewPosInfo>();

    private List<ImageView> mirrorViews = new ArrayList<ImageView>();
    private int mirrorViewHeight = 70;

    public enum RowColOption {
        FixedRow("fixedrow"),
        FixedCol("fixedcol"),
        FixedBoth("fixedboth");

        private String option;

        RowColOption(String code) {
            this.option = code;
        }

        public String toString() {
            return this.option;
        }

        public static RowColOption fromString(String layoutStr) {
            for (RowColOption lc : RowColOption.values()) {
                if (lc.option.equalsIgnoreCase(layoutStr)) {
                    return lc;
                }
            }
            return FixedBoth;
        }

    };

    private RowColOption option = RowColOption.FixedBoth;
    private static final int maxRowOrCol = 100;

    private static int gViewId = 10000;

    public DynamicWin8View(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
    }

    public DynamicWin8View(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public DynamicWin8View(Context context) {
        super(context);
        init(context);
    }

    private void init(Context context) {
        this.context = context;
        setClipToPadding(false);
        initTable(2, 14);
    }

    public boolean applyOptions(RowColOption option, int... limits) {
        this.option = option;
        maxRow = maxCol = maxRowOrCol;
        switch (option) {
            case FixedBoth:
                if (limits.length < 2)
                    return false;
                maxRow = limits[0];
                maxCol = limits[1];
                break;

            case FixedRow:
                if (limits.length < 1)
                    return false;
                maxRow = limits[0];
                break;

            case FixedCol:
                if (limits.length < 1)
                    return false;
                maxCol = limits[0];
                break;
        }

        initTable(maxRow, maxCol);
        return true;
    }

    private void initTable(int maxRow, int maxCol) {
        this.maxRow = maxRow;
        this.maxCol = maxCol;
        this.currRow = 0;
        this.currCol = 0;
        table = new int[maxRow][maxCol];
        for (int r=0; r<(maxRow); r++) {
            for (int c=0; c<maxCol; c++) {
                table[r][c] = VIEW_ID_AVAILABLE;
                if (r < freezeRows) table[r][c] = VIEW_ID_FROZEN;
                if (c < freezeCols) table[r][c] = VIEW_ID_FROZEN;
            }
        }
    }

    public void freezeColumns(int cols) {
        freezeCols = cols;
    }

    public void freezeRows(int rows) {
        freezeRows = rows;
    }

    public void addFrozenView(View view, int rowSpan, int colSpan) {
        if (!(rowSpan <= maxRow)) return;
        if (!(colSpan <= maxCol)) return;
        takeFrozenRoom(view, rowSpan, colSpan);
    }

    /*
	@Override
	public void addView(View child) {
	    addView(child, 1, 1);
	}

	@Override
	public void addView(View child, int index) {
	    addView(index, child, 1, 1);
	}

	@Override
	public void addView(View child, int index, android.view.ViewGroup.LayoutParams params) {
	    child.setLayoutParams(params);
	    addView(index, child, 1, 1);
	}

	@Override
	public void addView(View child, android.view.ViewGroup.LayoutParams params) {
	    child.setLayoutParams(params);
	    addView(child, 1, 1);
	}
     */

    @Override
    public void addView(View view, int rowSpan, int colSpan) {
        if (!(rowSpan <= maxRow)) return;
        if (!(colSpan <= maxCol)) return;
        takeRoom(view, rowSpan, colSpan);
    }

    public void addView(int index, View view, int rowSpan, int colSpan) {
        updateViewPosInfo(new ViewPosInfo[]{
                new ViewPosInfo(view, index, rowSpan, colSpan)
        }, null);
    }

    public void delView(View view) {
        updateViewPosInfo(null, new ViewPosInfo[]{
                new ViewPosInfo(view, 0, 0, 0)
        });
    }

    public void updateViewPosInfo(ViewPosInfo[] addList, ViewPosInfo[] removeList) {
        int posToChangeIndex = allViewPosInfo.size();

        /// to be added
        for (int i=0; null!=addList && i<addList.length; i++) {
            ViewPosInfo add = addList[i];
            int index = add.index;
            if (0 <= index) {
                if (index<posToChangeIndex) {
                    posToChangeIndex = index;
                }
            }
        }

        /// to be removed
        for (int i=0; null!=removeList && i<removeList.length; i++) {
            ViewPosInfo remove = removeList[i];
            int index = findIndexOfViewPosInfo(allViewPosInfo, remove);
            if (0 <= index) {
                if (index<posToChangeIndex) {
                    posToChangeIndex = index;
                }
            }
        }

        /// now we know those whose index is larger
        /// than posToChangeIndx will have some change.

        final List<ViewPosInfo> posToChange = new ArrayList<ViewPosInfo>(allViewPosInfo.subList(posToChangeIndex, allViewPosInfo.size()));
        allViewPosInfo = new ArrayList<ViewPosInfo>(allViewPosInfo.subList(0, posToChangeIndex));
        for (int idx=0; idx<posToChange.size(); idx++) {
            ViewPosInfo posInfo = posToChange.get(idx);
            if (null == posInfo || null == posInfo.view) continue;
            posInfo.takeSnapShot();
            for (int i=posInfo.rowStart; i<posInfo.rowStart+posInfo.rowSpan; i++) {
                for (int j=posInfo.colStart; j<posInfo.colStart+posInfo.colSpan; j++) {
                    table[i][j] = 0;
                }
            }
        }

        if (0 < posToChange.size()) {
            currRow = posToChange.get(0).rowStart;
            currCol = posToChange.get(0).colStart;
        }

        /// to be added
        for (int i=0; null!=addList && i<addList.length; i++) {
            ViewPosInfo add = addList[i];
            int idx = add.index - posToChangeIndex;
            if (posToChange.size() <= idx) {
                posToChange.add(add);
            } else {
                posToChange.add(idx, add);
            }
        }

        /// to be removed
        for (int i=0; null!=removeList && i<removeList.length; i++) {
            ViewPosInfo remove = removeList[i];
            int idx = findIndexOfViewPosInfo(posToChange, remove);
            if (0 <= idx) {
                posToChange.remove(idx);
                removeView(remove.view);
            }
        }

        /// add back leftovers.
        for (int idx=0; idx<posToChange.size(); idx++) {
            ViewPosInfo posInfo = posToChange.get(idx);
            if (null == posInfo || null == posInfo.view) continue;
            addView(posInfo.view, posInfo.rowSpan, posInfo.colSpan);
        }

        final ViewTreeObserver obs = getViewTreeObserver();
        obs.addOnPreDrawListener(new OnPreDrawListener() {
            @Override
            public boolean onPreDraw() {
                getViewTreeObserver().removeOnPreDrawListener(this);
                for(int i=0; i<posToChange.size(); i++) {
                    ViewPosInfo posInfo = posToChange.get(i);
                    posInfo.animLastSnapShot(10*i);
                }
                return false;
            }
        });

        //refreshFocus();
    }

    private int findIndexOfViewPosInfo(List<ViewPosInfo> posInfoList, ViewPosInfo lookup) {
        if (null != lookup && null != lookup.view) {
            for(int i=0; i<posInfoList.size(); i++) {
                ViewPosInfo posInfo = posInfoList.get(i);
                if (null != posInfo && lookup.view == posInfo.view) {
                    // update lookup info
                    lookup.rowStart = posInfo.rowStart;
                    lookup.rowSpan = posInfo.rowSpan;
                    lookup.colStart = posInfo.colStart;
                    lookup.colSpan = posInfo.colSpan;
                    lookup.index = i;
                    return i;
                }
            }
        }
        return -1;
    }

    private boolean hasRoom(int rowSpan, int colSpan, int freeSpaceVal) {
        for (int r=currRow; r<(currRow + rowSpan); r++) {
            if (!(r<maxRow)) return false;
            for (int c=currCol; c<(currCol + colSpan); c++) {
                if (!(c<maxCol)) return false;
                if (table[r][c] != freeSpaceVal) return false;
            }
        }
        return true;
    }

    private boolean takeFrozenRoom(View view, int rowSpan, int colSpan) {
        if (!hasRoom(rowSpan, colSpan, VIEW_ID_FROZEN)) return false;
        if (View.NO_ID == view.getId()) view.setId(gViewId++);

        RelativeLayout.LayoutParams lp;
        if (null != view.getLayoutParams()) {
            lp = new LayoutParams(view.getLayoutParams());
        } else {
            lp = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        }

        lp.addRule(RelativeLayout.BELOW, NO_ID);
        lp.addRule(RelativeLayout.RIGHT_OF, NO_ID);

        int viewId = 0;
        if (0 == currRow) {
        } else {
            viewId = getViewIdByPositionIndx(currRow - 1, currCol);
            lp.addRule(RelativeLayout.BELOW, viewId);
            lp.topMargin = getVGap();
        }

        if (0 == currCol) {
        } else {
            viewId = getViewIdByPositionIndx(currRow, currCol - 1);
            lp.addRule(RelativeLayout.RIGHT_OF, viewId);
            lp.leftMargin = getHGap();
        }

        if (null == view.getParent()) {
            addView(view, lp);
        } else {
            view.setLayoutParams(lp);
        }

        for (int r=currRow; r<(currRow + rowSpan); r++) {
            for (int c=currCol; c<(currCol + colSpan); c++) {
                table[r][c] = view.getId();
            }
        }

        boolean hasMoreRoom = false;

        int newCurrRow = currRow;
        int newCurrCol = currCol + colSpan;
        for (; newCurrRow < maxRow; newCurrRow++) {
            if (newCurrRow > currRow) {
                newCurrCol = 0;
            }

            for(; newCurrCol < maxCol; newCurrCol++) {
                if (VIEW_ID_FROZEN == table[newCurrRow][newCurrCol]) {
                    hasMoreRoom = true;
                    currRow = newCurrRow;
                    currCol = newCurrCol;
                    break;
                }
            }

            if (hasMoreRoom) {
                break;
            }
        }

        if (!hasMoreRoom) {
            for (newCurrRow = 0; newCurrRow < maxRow; newCurrRow++) {
                for(newCurrCol = 0; newCurrCol < maxCol; newCurrCol++) {
                    if (VIEW_ID_AVAILABLE == table[newCurrRow][newCurrCol]) {
                        hasMoreRoom = true;
                        currRow = newCurrRow;
                        currCol = newCurrCol;
                        break;
                    }
                }

                if (hasMoreRoom) {
                    break;
                }
            }
        }

        printPosInfo();

        return true;
    }

    private boolean takeRoom(View view, int rowSpan, int colSpan) {
        if (!hasRoom(rowSpan, colSpan, VIEW_ID_AVAILABLE)) return false;

        if (View.NO_ID == view.getId()) view.setId(gViewId++);

        RelativeLayout.LayoutParams lp;
        if (null != view.getLayoutParams()) {
            lp = new LayoutParams(view.getLayoutParams());
        } else {
            lp = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        }

        lp.addRule(RelativeLayout.BELOW, NO_ID);
        lp.addRule(RelativeLayout.RIGHT_OF, NO_ID);

        int viewId = 0;
        if (0 == currRow) {
        } else {
            viewId = getViewIdByPositionIndx(currRow - 1, currCol);
            lp.addRule(RelativeLayout.BELOW, viewId);
            lp.topMargin = getVGap();
        }

        if (0 == currCol) {
        } else {
            viewId = getViewIdByPositionIndx(currRow, currCol - 1);
            lp.addRule(RelativeLayout.RIGHT_OF, viewId);
            lp.leftMargin = getHGap();
        }

        if (null == view.getParent()) {
            addView(view, lp);
        } else {
            view.setLayoutParams(lp);
        }
        //将当前位置赋值给统计需要的位置字段
        positonCurrRow = currRow;
        positonCurrCol = currCol;

        allViewPosInfo.add(new ViewPosInfo(view, currRow, currCol, rowSpan, colSpan));

        for (int r=currRow; r<(currRow + rowSpan); r++) {
            for (int c=currCol; c<(currCol + colSpan); c++) {
                table[r][c] = view.getId();
            }
        }

        boolean hasMoreRoom = false;
        if (option == RowColOption.FixedBoth || option == RowColOption.FixedCol) {
            int newCurrRow = currRow;
            int newCurrCol = currCol + colSpan;
            for (; newCurrRow < maxRow; newCurrRow++) {
                if (newCurrRow > currRow) {
                    newCurrCol = 0;
                }

                for(; newCurrCol < maxCol; newCurrCol++) {
                    if (0 == table[newCurrRow][newCurrCol]) {
                        hasMoreRoom = true;
                        currRow = newCurrRow;
                        currCol = newCurrCol;
                        break;
                    }
                }

                if (hasMoreRoom) {
                    break;
                }
            }
        } else {
            int newCurrCol = currCol;
            int newCurrRow = currRow + rowSpan;
            for (; newCurrCol < maxCol; newCurrCol++) {
                if (newCurrCol > currCol) {
                    newCurrRow = 0;
                }

                for(; newCurrRow < maxRow; newCurrRow++) {
                    if (0 == table[newCurrRow][newCurrCol]) {
                        hasMoreRoom = true;
                        currRow = newCurrRow;
                        currCol = newCurrCol;
                        break;
                    }
                }

                if (hasMoreRoom) {
                    break;
                }
            }
        }

        if (!hasMoreRoom) {
            currRow = maxRow + 1;
            currCol = maxCol + 1;
        }

        printPosInfo();

        return true;
    }

    private int getViewIdByPositionIndx(int row, int col) {
        if (-1 < row && row < maxRow && -1 < col && col < maxCol) {
            return table[row][col];
        }
        return 0;
    }

    @Deprecated
    public int getGap() {
        return getHGap();
    }

    @Deprecated
    public void setGap(int gap) {
        setHGap(gap);
        setVGap(gap);
    }

    public int getVGap() {
        return UIUtils.getPixelFromDpi(context, vgap);
    }

    public void setVGap(int vgap) {
        this.vgap = vgap;
    }

    public int getHGap() {
        return UIUtils.getPixelFromDpi(context, hgap);
    }

    public void setHGap(int hgap) {
        this.hgap = hgap;
    }

    /**
     * Obsoleted, use applyOptions instead.
     *
     * @param maxRow
     * @param maxCol
     */
    public void setRowCol(int maxRow, int maxCol) {
        initTable(maxRow, maxCol);
    }

    public void printPosInfo() {
        /*
        StringBuilder sb = new StringBuilder();
        sb.append("----------------------");
        sb.append("\n");
        for (int r=0; r<maxRow; r++) {
            for (int c=0; c<maxCol; c++) {
                sb.append(table[r][c]).append(",");
            }
            sb.append("\n");
        }
        sb.append("----------------------");
        sb.append("\n");
        sb.append("currRow: " + currRow);
        sb.append("currCol: " + currCol);

        Log.i(sb.toString());
        */
    }

    //--------------------------------------------------------------------------------
    // Build from JSON template:
    //--------------------------------------------------------------------------------
    //
    //	{
    //	      "rows": 2,
    //	      "columns": 7,
    //	      "cells": [{
    //	          "rowspan": 2,
    //	          "colspan": 1
    //	      },
    //	      {
    //	          "rowspan": 1,
    //	          "colspan": 2
    //	      },
    //	      {
    //	          "rowspan": 2,
    //	          "colspan": 1
    //	      },
    //	      {
    //	          "rowspan": 1,
    //	          "colspan": 1,
    //            "type": 1
    //	      }]
    //	  }

    public interface Win8ChildViewProvider {
        public View createCellView(int index, int rowspan, int colspan, JSONObject cell);
    }

    public void buildFrom(JSONObject json, Win8ChildViewProvider provider) {
        /// Quick check.
        if (null == json || null == provider) {
            return;
        }

        int maxRow = json.optInt("rows");
        int maxCol = json.optInt("columns");
        int version = json.optInt("version", 1);
        if (version >=2) {
            setHGap(json.optInt("hgap"));
            setVGap(json.optInt("vgap"));
        }
        JSONArray jarray = json.optJSONArray("cells");

        removeAllViews();
        String option = json.optString("option");
        applyOptions(RowColOption.fromString(option), maxRow, maxCol);

        for (int i = 0; null != jarray && i < jarray.length(); i++) {
            JSONObject jobj = jarray.optJSONObject(i);
            if (null != jobj) {
                /// Add cell views.
                int rowspan = jobj.optInt("rowspan");
                int colspan = jobj.optInt("colspan");
                //得到该Item的id进行保存，留待统计时候时候
                String id = jobj.optString("id");
                View view = provider.createCellView(i, rowspan, colspan, jobj);
                if (null != view) {
                    if (View.NO_ID == view.getId()) {
                        view.setId(gViewId++);
                    }
                    addView(view, rowspan, colspan);

                    //记录Win8View的生成位置，留待进行统计上传 Add By Frewen.Wong
                    if (!TextUtils.isEmpty(positionMenuName)) {
                        int tmpDiff = 0;
                        if (diffMap.containsKey(positonCurrRow)) {
                            tmpDiff = diffMap.get(positonCurrRow);
                        }

                        if (colspan > 1) {
                            tmpDiff = 0;
                            if (diffMap.containsKey(positonCurrRow)) {
                                tmpDiff = diffMap.get(positonCurrRow);
                            }
                            diffMap.put(positonCurrRow,  colspan - 1+ tmpDiff);
                        }

                        String key = positionMenuName + id;
                        //储存位置信息
                        String value = (positonCurrRow + 1) + "_" + (positonCurrCol- tmpDiff + 1 );

                        itemPositionMap.put(key, value);

                    }
                }
            }
        }

        if (showMirror) {
            addMirrorRow();
        }
    }

    //--------------------------------------------------------------------------------
    // Creating Mirror
    //--------------------------------------------------------------------------------

    public interface IMirrorSourceView {
        public View getMirrorSourceView();
        public boolean isReadyForMirroring();
    }

    private void addMirrorRow() {

        /// remove old ones
        for (int i=0; i<mirrorViews.size(); i++) {
            removeView(mirrorViews.get(i));
        }
        mirrorViews.clear();

        /// adding mirror row.
        for (int i=0; i<maxCol;) {
            int id = table[maxRow-1][i];
            int span = 0;
            for(int j=0; j<(maxCol-i); j++) {
                if (id == table[maxRow-1][i+j]) {
                    span++;
                    continue;
                } else {
                    break;
                }
            }

            i += span;

            View view = findViewById(id);
            if (null != view) {
                ImageView mirrorView = createMirrorView(view);
                addView(mirrorView);
                mirrorViews.add(mirrorView);
            }
        }
    }

    public void showMirror(boolean show) {
        this.showMirror = show;
        for (int i=0; i<mirrorViews.size(); i++) {
            ImageView mirrorView = mirrorViews.get(i);
            mirrorView.setVisibility(show? View.VISIBLE : View.GONE);
        }
        requestLayout();
    }

    public void updateMirror() {
        if(!handler.hasMessages(CREATE_MIRROR_ROW))
            handler.sendEmptyMessage(CREATE_MIRROR_ROW);
    }

    private  boolean isMirrorReadyUpdate() {
        for (int i=0; i<mirrorViews.size(); i++) {
            View v = mirrorViews.get(i);
            View t = (View)v.getTag();
            if (t instanceof IMirrorSourceView) {
                if (!((IMirrorSourceView)t).isReadyForMirroring())
                    return false;
            } else {
                if (0 == v.getWidth())
                    return false;
            }
        }
        return true;
    }

    private ImageView createMirrorView(View view) {
        ViewGroup.LayoutParams lp = view.getLayoutParams();
        ImageView mirrorView = new ImageView(getContext());
        LayoutParams mlp = new LayoutParams(lp.width, mirrorViewHeight);
        mlp.addRule(RelativeLayout.BELOW, view.getId());
        mlp.addRule(RelativeLayout.ALIGN_LEFT, view.getId());
        mirrorView.setLayoutParams(mlp);
        mirrorView.setTag(view);
        return mirrorView;
    }

    private class MirrorTask extends AsyncTask<Void, Void, Bitmap> {

        private ImageView imageView;
        public MirrorTask(ImageView imageView) {
            super();
            this.imageView = imageView;
        }

        @Override
        protected Bitmap doInBackground(Void... params) {
            View view = (View)imageView.getTag();


            ViewGroup.LayoutParams lpMirrorView = imageView.getLayoutParams();
            ViewGroup.LayoutParams lpView = view.getLayoutParams();

            Bitmap bmp = Bitmap.createBitmap(lpView.width, lpView.height, Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(bmp);
            canvas.save();
            canvas.rotate(180, lpView.width/2, lpView.height/2);
            if (view instanceof IMirrorSourceView) {
                ((IMirrorSourceView)view).getMirrorSourceView().draw(canvas);
            } else {
                view.draw(canvas);
            }
            canvas.restore();
            Matrix flip = new Matrix();
            flip.setScale(-1, 1);

            Paint paint = new Paint();
            LinearGradient lg = new LinearGradient(0.f, 0.f, 0.f, mirrorViewHeight,
                    Color.argb(100, 0, 0, 0), Color.argb(0, 0, 0, 0), Shader.TileMode.CLAMP);
            paint.setShader(lg);
            paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_IN));

            canvas.drawRect(0.f, 0.f, lpView.width, lpView.height, paint);

            Bitmap bmp2 = Bitmap.createBitmap(bmp, 0, 0,
                    lpMirrorView.width, lpMirrorView.height, flip, true);

            bmp.recycle();
            //Log.i("Creating Mirror: view.getWidth():" + view.getWidth() + ", view.getHeight():" + view.getHeight() + ", lpView.width:" + lpView.width + ", lpView.height:" + lpView.height + ", lpMirrorView.width:" + lpMirrorView.width + ", lpMirrorView.height:" + lpMirrorView.height);

            return bmp2;
        }

        @Override
        protected void onPostExecute(Bitmap result) {
            super.onPostExecute(result);
            imageView.setImageBitmap(result);
        }
    }

    final static int CREATE_MIRROR_ROW = 1000;
    Handler handler = new Handler() {
        @Override
        public void handleMessage(android.os.Message msg) {
            if (CREATE_MIRROR_ROW == msg.what) {
                if (isMirrorReadyUpdate()) {
                    int size = mirrorViews.size();
                    for (int i = 0; i < size; i++) {
                        new MirrorTask(mirrorViews.get(i)).execute();
                    }
                    return;
                }
                sendEmptyMessageDelayed(CREATE_MIRROR_ROW, 500);
            }
        };
    };

}
