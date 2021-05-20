package com.tj.skyone.widget.dialog;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatDialog;

import com.tj.skyone.R;
import com.yatoooon.screenadaptation.ScreenAdapterTools;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 *
 */
public class PubDialog extends AppCompatDialog {


    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.img)
    public ImageView img;
    @BindView(R.id.text)
    TextView text;
    @BindView(R.id.btn)
    public TextView btn;
    @BindView(R.id.ok)
    public TextView ok;

    private boolean isImg = false;
    private boolean isImgs = false;
    private String titles = "";
    private String content = "";
    private boolean showbtn =false;

    public PubDialog(Context context) {
        super(context, R.style.dialogstyle);

    }

    public PubDialog(Context context, boolean isImg, String title, String content) {
        super(context, R.style.dialogstyle);
        this.isImg = isImg;
        titles = title;
        this.content = content;

    }

    public PubDialog(Context context, boolean isImg, String title, String content,boolean showbtn,boolean isImgs) {
        super(context, R.style.dialogstyle);
        this.isImg = isImg;
        titles = title;
        this.content = content;
        this.showbtn =showbtn;
        this.isImgs =isImgs;

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_login);
        ScreenAdapterTools.getInstance().loadView(getWindow().getDecorView());
        this.setCancelable(false);
        ButterKnife.bind(this);

        if (isImg) {

            img.setImageResource(R.mipmap.ok_bgs);

        } else {

            img.setImageResource(R.mipmap.x_icon);

        }


        if (!titles.equals("")) {
            title.setText(titles);
        }

        if (!content.equals("")) {
            text.setText(content);
        }

        if (showbtn){
            ok.setVisibility(View.VISIBLE);
        }

        if (isImgs)img.setImageResource(R.mipmap.uu_icon);


    }


    @OnClick(R.id.btn)
    public void onViewClicked() {
        dismiss();
    }
}
