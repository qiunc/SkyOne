package com.tj.skyone.widget.dialog;

import android.content.Context;

import androidx.appcompat.app.AppCompatDialog;

import com.tj.skyone.R;

import butterknife.ButterKnife;

/**
 * 车辆编辑-司机多选弹窗
 */
public class HomeSelectDialog extends AppCompatDialog {



    public HomeSelectDialog(Context context) {
        super(context, R.style.dialogstyle);


        setContentView(R.layout.dialog_home_s);
        this.setCancelable(false);
        ButterKnife.bind(this);

    }






}
