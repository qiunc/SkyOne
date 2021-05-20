package com.tj.skyone.widget.dialog;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatDialog;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.tj.skyone.R;
import com.tj.skyone.adapter.GropAdapter;
import com.tj.skyone.adapter.MgtAdapter;
import com.tj.skyone.bean.MgtBean;
import com.yatoooon.screenadaptation.ScreenAdapterTools;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 *
 */
public class MgtDialog extends AppCompatDialog {


    @BindView(R.id.r1)
    RecyclerView r1;
    @BindView(R.id.btn)
    TextView btn;
    private MgtAdapter mgtAdapter;


    public MgtDialog(Context context) {
        super(context, R.style.dialogstyle);


        setContentView(R.layout.dialog_mgt);
        ButterKnife.bind(this);
        ScreenAdapterTools.getInstance().loadView(getWindow().getDecorView());
        this.setCancelable(false);

        mgtAdapter = new MgtAdapter();
        r1.setLayoutManager(new LinearLayoutManager(getContext()));
        r1.setAdapter(mgtAdapter);

        List<MgtBean> list = new ArrayList<>();

        list.add(new MgtBean("1","123456","普通用户"));
        list.add(new MgtBean("2","123456","管理员"));
        list.add(new MgtBean("3","123456","普通用户"));
        list.add(new MgtBean("4","123456","普通用户"));
        list.add(new MgtBean("5","123456","普通用户"));
        list.add(new MgtBean("6","123456","管理员"));
        list.add(new MgtBean("7","123456","普通用户"));
        list.add(new MgtBean("8","123456","管理员"));
        list.add(new MgtBean("9","123456","普通用户"));
        list.add(new MgtBean("10","123456","普通用户"));


        list.add(new MgtBean("1","123456","普通用户"));
        list.add(new MgtBean("2","123456","管理员"));
        list.add(new MgtBean("3","123456","普通用户"));
        list.add(new MgtBean("4","123456","普通用户"));
        list.add(new MgtBean("5","123456","普通用户"));
        list.add(new MgtBean("6","123456","管理员"));
        list.add(new MgtBean("7","123456","普通用户"));
        list.add(new MgtBean("8","123456","管理员"));
        list.add(new MgtBean("9","123456","普通用户"));
        list.add(new MgtBean("10","123456","普通用户"));


        mgtAdapter.setNewInstance(list);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });



    }


}
