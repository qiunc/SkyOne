package com.tj.skyone.widget.dialog;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;

import com.tj.skyone.R;


/**
 * @describe
 * @anthor wdq
 * @time 2019/4/23 13:48
 * @email wudq@infore.com
 */
public class HomePopWindow extends PopupWindow {

    public View view1, view2, view3, view4, view5;

    public HomePopWindow(Context context) {
        super(context);
        setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        setWidth(ViewGroup.LayoutParams.WRAP_CONTENT);

        setOutsideTouchable(true);
        setFocusable(true);
        setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        View contentView = LayoutInflater.from(context).inflate(R.layout.dialog_home_s, null, false);
        setContentView(contentView);


        view1 = contentView.findViewById(R.id.time);
        view2 = contentView.findViewById(R.id.user);
        view3 = contentView.findViewById(R.id.about);
        view4 = contentView.findViewById(R.id.use);
        view5 = contentView.findViewById(R.id.cancel);

    }
}
