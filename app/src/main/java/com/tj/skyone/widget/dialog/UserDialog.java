package com.tj.skyone.widget.dialog;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatDialog;

import com.tj.skyone.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 *
 */
public class UserDialog extends AppCompatDialog {


    @BindView(R.id.update)
    public TextView update;
    @BindView(R.id.add)
    public TextView add;
    @BindView(R.id.mgt)
    public TextView mgt;
    @BindView(R.id.btn)
    TextView btn;

    public UserDialog(Context context) {
        super(context, R.style.dialogstyle);


        setContentView(R.layout.dialog_user);
        this.setCancelable(false);
        ButterKnife.bind(this);

        findViewById(R.id.btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });

    }


//    @OnClick({R.id.update,  R.id.mgt})
//    public void onViewClicked(View view) {
//        switch (view.getId()) {
//            case R.id.update:
//
//
//
//                break;
//
//            case R.id.mgt:
//                break;
//        }
//    }
}
