package com.ange.utils;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;

/**
 * Created by Administrator on 2017/1/5 0005.
 */

public class GustomGridLayoutManager extends GridLayoutManager {
    private boolean isScrollEnabled = true;

    public GustomGridLayoutManager(Context context,int i) {
        super(context,i);
    }

    public void setScrollEnabled(boolean flag) {
        this.isScrollEnabled = flag;
    }

    @Override
    public boolean canScrollVertically() {
        //Similarly you can customize "canScrollHorizontally()" for managing horizontal scroll
        return isScrollEnabled && super.canScrollVertically();
    }
}
