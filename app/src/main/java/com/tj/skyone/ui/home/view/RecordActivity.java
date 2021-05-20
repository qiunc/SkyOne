package com.tj.skyone.ui.home.view;


import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.tj.skyone.R;
import com.tj.skyone.adapter.RecordAdapter;
import com.tj.skyone.base.BaseActivity;
import com.tj.skyone.bean.RecordBean;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class RecordActivity extends BaseActivity {

    @BindView(R.id.left_img)
    ImageView leftImg;
    @BindView(R.id.left_btn)
    LinearLayout leftBtn;
    @BindView(R.id.r1)
    RecyclerView r1;
    private RecordAdapter adapter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_record;
    }

    @Override
    protected void initEventAndData() {

        adapter = new RecordAdapter();
        r1.setLayoutManager(new LinearLayoutManager(this));
        r1.setAdapter(adapter);

        List<RecordBean> list = new ArrayList<>();

        list.add(new RecordBean("1","123456","A2摇臂复位过程中，由于电源插头或其他物件阻挡，复位电机过载保护，停止复位。","2021-01-15"));
        list.add(new RecordBean("2","123456","A2摇臂复位过程中，由于电源插头或其他物件阻挡，复位电机过载保护，停止复位。","2021-01-15"));
        list.add(new RecordBean("3","123456","A2摇臂复位过程中，由于电源插头或其他物件阻挡，复位电机过载保护，停止复位。","2021-01-15"));
        list.add(new RecordBean("4","123456","A2摇臂复位过程中，由于电源插头或其他物件阻挡，复位电机过载保护，停止复位。","2021-01-15"));
        list.add(new RecordBean("5","123456","A2摇臂复位过程中，由于电源插头或其他物件阻挡，复位电机过载保护，停止复位。","2021-01-15"));
        list.add(new RecordBean("6","123456","A2摇臂复位过程中，由于电源插头或其他物件阻挡，复位电机过载保护，停止复位。","2021-01-15"));
        list.add(new RecordBean("7","123456","A2摇臂复位过程中，由于电源插头或其他物件阻挡，复位电机过载保护，停止复位。","2021-01-15"));
        list.add(new RecordBean("8","123456","A2摇臂复位过程中，由于电源插头或其他物件阻挡，复位电机过载保护，停止复位。","2021-01-15"));
        list.add(new RecordBean("9","123456","A2摇臂复位过程中，由于电源插头或其他物件阻挡，复位电机过载保护，停止复位。","2021-01-15"));
        list.add(new RecordBean("10","123456","A2摇臂复位过程中，由于电源插头或其他物件阻挡，复位电机过载保护，停止复位。","2021-01-15"));


        list.add(new RecordBean("1","123456","A2摇臂复位过程中，由于电源插头或其他物件阻挡，复位电机过载保护，停止复位。","2021-01-15"));
        list.add(new RecordBean("2","123456","A2摇臂复位过程中，由于电源插头或其他物件阻挡，复位电机过载保护，停止复位。","2021-01-15"));
        list.add(new RecordBean("3","123456","A2摇臂复位过程中，由于电源插头或其他物件阻挡，复位电机过载保护，停止复位。","2021-01-15"));
        list.add(new RecordBean("4","123456","A2摇臂复位过程中，由于电源插头或其他物件阻挡，复位电机过载保护，停止复位。","2021-01-15"));
        list.add(new RecordBean("5","123456","A2摇臂复位过程中，由于电源插头或其他物件阻挡，复位电机过载保护，停止复位。","2021-01-15"));
        list.add(new RecordBean("6","123456","A2摇臂复位过程中，由于电源插头或其他物件阻挡，复位电机过载保护，停止复位。","2021-01-15"));
        list.add(new RecordBean("7","123456","A2摇臂复位过程中，由于电源插头或其他物件阻挡，复位电机过载保护，停止复位。","2021-01-15"));
        list.add(new RecordBean("8","123456","A2摇臂复位过程中，由于电源插头或其他物件阻挡，复位电机过载保护，停止复位。","2021-01-15"));
        list.add(new RecordBean("9","123456","A2摇臂复位过程中，由于电源插头或其他物件阻挡，复位电机过载保护，停止复位。","2021-01-15"));
        list.add(new RecordBean("10","123456","A2摇臂复位过程中，由于电源插头或其他物件阻挡，复位电机过载保护，停止复位。","2021-01-15"));


        list.add(new RecordBean("1","123456","A2摇臂复位过程中，由于电源插头或其他物件阻挡，复位电机过载保护，停止复位。","2021-01-15"));
        list.add(new RecordBean("2","123456","A2摇臂复位过程中，由于电源插头或其他物件阻挡，复位电机过载保护，停止复位。","2021-01-15"));
        list.add(new RecordBean("3","123456","A2摇臂复位过程中，由于电源插头或其他物件阻挡，复位电机过载保护，停止复位。","2021-01-15"));
        list.add(new RecordBean("4","123456","A2摇臂复位过程中，由于电源插头或其他物件阻挡，复位电机过载保护，停止复位。","2021-01-15"));
        list.add(new RecordBean("5","123456","A2摇臂复位过程中，由于电源插头或其他物件阻挡，复位电机过载保护，停止复位。","2021-01-15"));
        list.add(new RecordBean("6","123456","A2摇臂复位过程中，由于电源插头或其他物件阻挡，复位电机过载保护，停止复位。","2021-01-15"));
        list.add(new RecordBean("7","123456","A2摇臂复位过程中，由于电源插头或其他物件阻挡，复位电机过载保护，停止复位。","2021-01-15"));
        list.add(new RecordBean("8","123456","A2摇臂复位过程中，由于电源插头或其他物件阻挡，复位电机过载保护，停止复位。","2021-01-15"));
        list.add(new RecordBean("9","123456","A2摇臂复位过程中，由于电源插头或其他物件阻挡，复位电机过载保护，停止复位。","2021-01-15"));
        list.add(new RecordBean("10","123456","A2摇臂复位过程中，由于电源插头或其他物件阻挡，复位电机过载保护，停止复位。","2021-01-15"));


        list.add(new RecordBean("1","123456","A2摇臂复位过程中，由于电源插头或其他物件阻挡，复位电机过载保护，停止复位。","2021-01-15"));
        list.add(new RecordBean("2","123456","A2摇臂复位过程中，由于电源插头或其他物件阻挡，复位电机过载保护，停止复位。","2021-01-15"));
        list.add(new RecordBean("3","123456","A2摇臂复位过程中，由于电源插头或其他物件阻挡，复位电机过载保护，停止复位。","2021-01-15"));
        list.add(new RecordBean("4","123456","A2摇臂复位过程中，由于电源插头或其他物件阻挡，复位电机过载保护，停止复位。","2021-01-15"));
        list.add(new RecordBean("5","123456","A2摇臂复位过程中，由于电源插头或其他物件阻挡，复位电机过载保护，停止复位。","2021-01-15"));
        list.add(new RecordBean("6","123456","A2摇臂复位过程中，由于电源插头或其他物件阻挡，复位电机过载保护，停止复位。","2021-01-15"));
        list.add(new RecordBean("7","123456","A2摇臂复位过程中，由于电源插头或其他物件阻挡，复位电机过载保护，停止复位。","2021-01-15"));
        list.add(new RecordBean("8","123456","A2摇臂复位过程中，由于电源插头或其他物件阻挡，复位电机过载保护，停止复位。","2021-01-15"));
        list.add(new RecordBean("9","123456","A2摇臂复位过程中，由于电源插头或其他物件阻挡，复位电机过载保护，停止复位。","2021-01-15"));
        list.add(new RecordBean("10","123456","A2摇臂复位过程中，由于电源插头或其他物件阻挡，复位电机过载保护，停止复位。","2021-01-15"));

        adapter.setNewInstance(list);


    }

    @Override
    protected void isStart() {

    }

    @Override
    public void showError(String errorMessage) {

    }

    @Override
    public void showResult(Object object) {

    }

    @Override
    public void showResult(Object object, String code) {

    }



    @OnClick(R.id.left_btn)
    public void onViewClicked() {

        finish();
    }
}