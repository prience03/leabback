package com.open.sample.ui;

import android.annotation.TargetApi;
import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.Button;

import com.open.leanback.widget.ArrayObjectAdapter;
import com.open.leanback.widget.HeaderItem;
import com.open.leanback.widget.ItemBridgeAdapter;
import com.open.leanback.widget.ListRow;
import com.open.leanback.widget.OnChildViewHolderSelectedListener;
import com.open.leanback.widget.Presenter;
import com.open.leanback.widget.RowPresenter;
import com.open.leanback.widget.VerticalGridView;
import com.open.sample.R;
import com.open.sample.model.Movie;
import com.open.sample.model.MovieList;
import com.open.sample.presenter.ButtonListRow;
import com.open.sample.presenter.CardPresenter;
import com.open.sample.presenter.NewPresenterSelector;

import java.util.Collections;
import java.util.List;

/**
 * VerticalActivity
 *
 * @author Clendy
 * @time 2016/11/20 16:13
 * @e-mail yc330483161@outlook.com
 */
public class VerticalActivity extends Activity {

    private static final String TAG = "hailongqiu";

    VerticalGridView mRecyclerView;
    ArrayObjectAdapter mRowsAdapter;
    ItemBridgeAdapter mItemBridgeAdapter;
    int mSubPosition;
    ItemBridgeAdapter.ViewHolder mSelectedViewHolder;

    private static final int NUM_ROWS = 10;
    private static final int NUM_COLS = 5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vertical);
        mRecyclerView = (VerticalGridView) findViewById(R.id.recyclerView);

        List<Movie> list = MovieList.setupMovies();
        final NewPresenterSelector newPresenterSelector = new NewPresenterSelector();
        mRowsAdapter = new ArrayObjectAdapter(newPresenterSelector); // 填入Presenter选择器.
        CardPresenter cardPresenter = new CardPresenter();

        int i;
        // 电影海报测试数据.
        for (i = 0; i < NUM_ROWS; i++) {
            if (i != 0) {
                Collections.shuffle(list);
            }
            ArrayObjectAdapter listRowAdapter = new ArrayObjectAdapter(cardPresenter);
            for (int j = 0; j < NUM_COLS; j++) {
                listRowAdapter.add(list.get(j % 5));
            }
            HeaderItem header = new HeaderItem(i, MovieList.MOVIE_CATEGORY[i % 5]);
            ListRow listRow = new ListRow(header, listRowAdapter);
            mRowsAdapter.add(listRow);
        }

        // 测试其它数据.
        HeaderItem gridHeader = new HeaderItem(i, "系统设置");
        GridItemPresenter mGridPresenter = new GridItemPresenter();
        ArrayObjectAdapter gridRowAdapter = new ArrayObjectAdapter(mGridPresenter);
        gridRowAdapter.add("音频");
        gridRowAdapter.add("投影设置");
        gridRowAdapter.add("明天是否");
        mRowsAdapter.add(new ButtonListRow(gridHeader, gridRowAdapter));

        // 多测试几行数据，检测.
        for (i = 0; i < NUM_ROWS; i++) {
            if (i != 0) {
                Collections.shuffle(list);
            }
            ArrayObjectAdapter listRowAdapter = new ArrayObjectAdapter(cardPresenter);
            for (int j = 0; j < NUM_COLS; j++) {
                listRowAdapter.add(list.get(j % 5));
            }
            HeaderItem header = new HeaderItem(i, MovieList.MOVIE_CATEGORY[i % 5]);
            ListRow listRow = new ListRow(header, listRowAdapter);
            mRowsAdapter.add(listRow);
        }

        mItemBridgeAdapter = new ItemBridgeAdapter(mRowsAdapter);
        mItemBridgeAdapter.setAdapterListener(mBridgeAdapterListener); // 测试一行选中颜色的改变.
        mRecyclerView.setAdapter(mItemBridgeAdapter);
        mRecyclerView.setOnChildViewHolderSelectedListener(new OnChildViewHolderSelectedListener() {
            @TargetApi(Build.VERSION_CODES.M)
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onChildViewHolderSelected(RecyclerView parent, RecyclerView.ViewHolder viewHolder, int position, int subposition) {
                Log.d("hailongqiu", "选择一行");
                // 测试一行选中颜色的改变.
                if(mSelectedViewHolder != viewHolder || mSubPosition != subposition) {
                    mSubPosition = subposition;
                    if(mSelectedViewHolder != null) {
                        setRowViewSelected(mSelectedViewHolder, false);
                    }
                    mSelectedViewHolder = (ItemBridgeAdapter.ViewHolder)viewHolder;
                    if(mSelectedViewHolder != null) {
                        setRowViewSelected(mSelectedViewHolder, true);
                    }
                }
            }
        });
        // 不然有一些item放大被挡住了. (注意)
        mRecyclerView.setClipChildren(false);
        mRecyclerView.setClipToPadding(false);
        // 设置间隔.
        mRecyclerView.setPadding(30, 30, 30, 30);
        // 设置垂直item的间隔.
        mRecyclerView.setVerticalMargin(30);
        // 设置缓存.
        mRecyclerView.getRecycledViewPool().setMaxRecycledViews(0, 100);
    }

    ItemBridgeAdapter.AdapterListener mBridgeAdapterListener = new ItemBridgeAdapter.AdapterListener() {
        @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
        @Override
        public void onCreate(ItemBridgeAdapter.ViewHolder viewHolder) {
            setRowViewSelected(viewHolder, false);
        }

        @Override
        public void onDetachedFromWindow(ItemBridgeAdapter.ViewHolder vh) {
            if (mSelectedViewHolder == vh) {
                setRowViewSelected(mSelectedViewHolder, false);
                mSelectedViewHolder = null;
            }
        }

        public void onUnbind(ItemBridgeAdapter.ViewHolder vh) {
            setRowViewSelected(vh, false);
        }
    };

    /**
     *  测试一行选中的颜色改变.
     */
    @TargetApi(Build.VERSION_CODES.M)
    void setRowViewSelected(ItemBridgeAdapter.ViewHolder vh, boolean selected) {
//        vh.itemView.setBackground(new ColorDrawable(selected ? Color.TRANSPARENT : Color.RED));
    }

    private static final int GRID_ITEM_WIDTH = 200;
    private static final int GRID_ITEM_HEIGHT = 200;

    public static class GridItemPresenter extends Presenter {

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent) {
            Button view = new Button(parent.getContext());
            view.setLayoutParams(new ViewGroup.LayoutParams(GRID_ITEM_WIDTH, GRID_ITEM_HEIGHT));
            view.setFocusable(true);
            view.setFocusableInTouchMode(true);
            view.setTextColor(Color.WHITE);
            view.setGravity(Gravity.CENTER);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(Presenter.ViewHolder viewHolder, Object item) {
            ((Button) viewHolder.view).setText((String) item);
        }

        @Override
        public void onUnbindViewHolder(ViewHolder viewHolder) {
        }
    }

}
