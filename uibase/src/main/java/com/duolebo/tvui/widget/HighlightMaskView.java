package com.duolebo.tvui.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.view.View;

/**
 * @author zlhl
 * @date 2014年7月2日
 */
public class HighlightMaskView extends View {

    private int top = 0;
    private int bottom = 0;
    private int transitionHeight = 0;
    private int colorDark;
    private int colorBright;

    private Paint paint;

    public HighlightMaskView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public HighlightMaskView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public HighlightMaskView(Context context) {
        super(context);
        init();
    }

    private void init() {
        colorDark = Color.argb(128, 0, 0, 0);
        colorBright = Color.argb(255, 0, 0, 0);
        paint = new Paint();
    }

    public void setHighlightedZone(int top, int bottom, int transitionHight) {
        this.top = top;
        this.bottom = bottom;
        this.transitionHeight = transitionHight;
        invalidate();
    }
    
    public void setHighlightColor(int colorDark, int colorBright) {
        this.colorDark = colorDark;
        this.colorBright = colorBright;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        int y1, y2;

        y1 = top - transitionHeight / 2;
        y2 = y1 + transitionHeight;
        paint.setShader(null);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_IN));
        paint.setShader(new LinearGradient(0, y1, 0, y2, colorDark, colorBright,
                Shader.TileMode.CLAMP));
        canvas.drawPaint(paint);

        y1 = bottom + transitionHeight / 2;
        y2 = y1 - transitionHeight;
        paint.setShader(null);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_IN));
        paint.setShader(new LinearGradient(0, y1, 0, y2, colorDark, colorBright,
                Shader.TileMode.CLAMP));
        canvas.drawPaint(paint);

    }
}
