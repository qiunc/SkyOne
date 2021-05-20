package com.tj.skyone.widget;

import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

public class MyRecyclerView extends RecyclerView {

    public MyRecyclerView(android.content.Context context, android.util.AttributeSet attrs) {
        super(context, attrs);
    }

    public MyRecyclerView(android.content.Context context) {
        super(context);
    }

    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int expandSpec = View.MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, View.MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }
}