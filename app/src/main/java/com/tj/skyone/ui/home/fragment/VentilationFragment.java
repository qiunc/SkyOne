package com.tj.skyone.ui.home.fragment;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.blankj.utilcode.util.GsonUtils;
import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.google.gson.Gson;
import com.tj.skyone.R;
import com.tj.skyone.base.BaseFragment;
import com.tj.skyone.bean.GroupBean;
import com.tj.skyone.bean.RockerBean;
import com.tj.skyone.bean.VentilationBean;
import com.tj.skyone.utils.HttpParam;
import com.tj.skyone.utils.NoDoubleClickUtils;
import com.tj.skyone.utils.TcpClient;
import com.tj.skyone.utils.eventbus.AnyEventTypes;
import com.tj.skyone.utils.eventbus.EventBusUtils;

import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @describe 通风
 * @anthor wdq
 * @time 2021/1/11 16:09
 * @email wudq@infore.com
 */
public class VentilationFragment extends BaseFragment {

    @BindView(R.id.tv_v)
    TextView tvV;
    @BindView(R.id.btn_f)
    Button btnF;
    @BindView(R.id.btn_add)
    FrameLayout btnAdd;
    @BindView(R.id.btn_on_off)
    ImageView btnOnOff;
    @BindView(R.id.btn_re)
    FrameLayout btnRe;

    private int values = 0;

    private boolean tag = true;//默认关闭

    @Override
    public void initData(@Nullable Bundle bundle) {

    }

    @Override
    public int bindLayout() {
        return R.layout.fragment_ventilation;
    }

    @Override
    public void initView(Bundle savedInstanceState, View contentView) {

    }

    @Override
    public void doBusiness() {
        EventBusUtils.register(this);

    }

    @Override
    public void onWidgetClick(View view) {

    }

    @Override
    public void isStart() {

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

    @OnClick({R.id.btn_f, R.id.btn_add, R.id.btn_on_off, R.id.btn_re})
    public void onViewClicked(View view) {

        if (!NoDoubleClickUtils.isDoubleClick()) {
            switch (view.getId()) {
                case R.id.btn_f:

                    values = 50;
                    tvV.setText(values + "");

                    setData();
                    break;
                case R.id.btn_add:

                    if (values == 60) {

                    } else {

                        values = values + 1;

                    }

                    tvV.setText(values + "");

                    setData();

                    break;

                case R.id.btn_on_off:

                    if (tag) {
                        btnOnOff.setImageResource(R.mipmap.off_iocn_ms);
                        tag = false;
                    } else {

                        btnOnOff.setImageResource(R.mipmap.no_iocn_ms);
                        tag = true;
                    }

                    setData();

                    break;
                case R.id.btn_re:

                    if (values == 20) {

                    } else {

                        values = values - 1;

                    }

                    tvV.setText(values + "");

                    setData();

                    break;
            }
        }
    }

    private void setData(){

        getDialog().show();

        HttpParam httpParam = new HttpParam();
        httpParam.getMap().put("methodName","ventilate");
        httpParam.getMap().put("v_fengsu",values+"");
        httpParam.getMap().put("v_key",tag == true?"1":"0");


        httpParam.getMap().put("dataLen", "end");

        TcpClient.getInstance().sendChsPrtCmds(new Gson().toJson(httpParam.getMap()), 1001);

    }

    public void getData(){
        getDialog().show();

        HttpParam httpParam = new HttpParam();
        httpParam.getMap().put("methodName","ventilate");
        httpParam.getMap().put("dataLen", "end");
        TcpClient.getInstance().sendChsPrtCmds(new Gson().toJson(httpParam.getMap()), 1001);

    }


    @Subscribe()
    public void onEvent(AnyEventTypes event) {

        getDialog().dismiss();
        if (StringUtils.equals("ventilate",event.getEventCode())) {

            VentilationBean bean = GsonUtils.fromJson(event.getAnyData().toString(),VentilationBean.class);

            values = StringUtils.isEmpty(bean.getV_fengsu())?0:Integer.valueOf(bean.getV_fengsu());
            tvV.setText(values+"");

            if (StringUtils.equals("1",bean.getV_key())){//ON

                btnOnOff.setImageResource(R.mipmap.no_iocn_ms);
                tag = true;

            }else{

                btnOnOff.setImageResource(R.mipmap.off_iocn_ms);
                tag = false;

            }

        }
    }



    @Override
    public void onDestroy() {
        EventBusUtils.unregister(this);
        super.onDestroy();
    }
}
