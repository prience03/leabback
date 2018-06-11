package com.open.sample.utils;

import android.content.Context;
import android.util.DisplayMetrics;

public class NgScreenUtils
{
    public static final int a = 1920;
    public static final int b = 1080;

    private static final float c = 160.0F;
    private static final float d = 1.0F;

    private float densityDpi;
    private float scaledDensity;
    private float g;
    private int widthPx;
    private int heighPx;
    private float j;
    private float k;

    public NgScreenUtils(Context paramContext)
    {
        DisplayMetrics dm = paramContext.getResources().getDisplayMetrics();
        this.widthPx = dm.widthPixels;
        this.heighPx = dm.heightPixels;
        this.densityDpi = dm.densityDpi;
        this.scaledDensity = dm.scaledDensity;
        this.j = (this.widthPx / 1920.0F);
        this.k = (this.heighPx / 1080.0F);
    }

    public int a()
    {
        return this.widthPx;
    }

    public int a(float paramFloat)
    {
        return (int)(paramFloat / (densityDpi / 160.0F) * (densityDpi / 160.0F) * this.j);
    }

    public int a(Context paramContext, float paramFloat)
    {
        return (int)((int)(this.g * paramFloat) / this.densityDpi + 0.5D);
    }

    public int b()
    {
        return this.heighPx;
    }

    public int b(float paramFloat)
    {
        return (int)(paramFloat / (this.densityDpi / 160.0F) * (this.densityDpi / 160.0F) * this.k);
    }

    public int c(float paramFloat)
    {
        return (int)(paramFloat / scaledDensity * (scaledDensity / 1.0F) / scaledDensity * this.j);
    }

    public int d(float paramFloat)
    {
        return (int)((int)(this.g * paramFloat) * densityDpi + 0.5D);
    }
}