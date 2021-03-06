package com.tj.skyone.widget.dialog;

import android.content.Context;

import androidx.appcompat.app.AppCompatDialog;

import com.tj.skyone.R;

import butterknife.ButterKnife;

/**
 * 关于我们对话框
 */
public class AboutDialog extends AppCompatDialog {



    public AboutDialog(Context context) {
        super(context, R.style.dialogstyle);


        setContentView(R.layout.dialog_about);
        this.setCancelable(false);
        ButterKnife.bind(this);

        findViewById(R.id.btn).setOnClickListener(view -> dismiss());

    }






}
