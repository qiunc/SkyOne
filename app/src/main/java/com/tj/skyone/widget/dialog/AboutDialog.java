package com.tj.skyone.widget.dialog;

import android.content.Context;
import android.view.View;

import androidx.appcompat.app.AppCompatDialog;

import com.tj.skyone.R;
import com.yatoooon.screenadaptation.ScreenAdapterTools;

import butterknife.ButterKnife;

/**
 *
 */
public class AboutDialog extends AppCompatDialog {



    public AboutDialog(Context context) {
        super(context, R.style.dialogstyle);


        setContentView(R.layout.dialog_about);
        ScreenAdapterTools.getInstance().loadView(getWindow().getDecorView());
        this.setCancelable(false);
        ButterKnife.bind(this);

        findViewById(R.id.btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });

    }






}
