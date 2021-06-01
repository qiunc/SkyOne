package com.tj.skyone.widget.dialog;

import android.content.Context;

import androidx.appcompat.app.AppCompatDialog;

import com.tj.skyone.R;
import com.yatoooon.screenadaptation.ScreenAdapterTools;

/**
 * 使用说明对话框
 */
public class UseDialog extends AppCompatDialog {




    public UseDialog(Context context) {
        super(context, R.style.dialogstyle);


        setContentView(R.layout.dialog_use);
        ScreenAdapterTools.getInstance().loadView(getWindow().getDecorView());
        this.setCancelable(false);

        findViewById(R.id.btn).setOnClickListener(view -> dismiss());

    }






}
