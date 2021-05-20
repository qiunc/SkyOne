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
public class UseDialog extends AppCompatDialog {




    public UseDialog(Context context) {
        super(context, R.style.dialogstyle);


        setContentView(R.layout.dialog_use);
        ScreenAdapterTools.getInstance().loadView(getWindow().getDecorView());
        this.setCancelable(false);

        findViewById(R.id.btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });

    }






}
