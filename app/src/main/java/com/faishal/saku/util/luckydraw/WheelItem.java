package com.faishal.saku.util.luckydraw;

import android.graphics.Bitmap;

public class WheelItem {

    public int color;
    public Bitmap bitmap;
    public String text;

    public WheelItem(int color, Bitmap bitmap) {
        this.color = color;
        this.bitmap = bitmap;
    }

    public WheelItem(int color, Bitmap bitmap, String text) {
        this.color = color;
        this.bitmap = bitmap;
        this.text = text;
    }

}
