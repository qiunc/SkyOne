package com.tj.skyone.ui.home.fragment;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.blankj.utilcode.util.GsonUtils;
import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.google.gson.Gson;
import com.tj.skyone.R;
import com.tj.skyone.base.BaseFragment;
import com.tj.skyone.bean.LowvoltBean;
import com.tj.skyone.ui.GlobalApp;
import com.tj.skyone.utils.HttpParam;
import com.tj.skyone.utils.NoDoubleClickUtils;
import com.tj.skyone.utils.TcpClient;
import com.tj.skyone.utils.eventbus.AnyEventTypes;
import com.tj.skyone.utils.eventbus.EventBusUtils;

import org.greenrobot.eventbus.Subscribe;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @describe 低压
 * @anthor wdq
 * @time 2021/1/11 16:09
 * @email wudq@infore.com
 */
public class LowVoltageFragment extends BaseFragment {

    @BindView(R.id.tv_z)
    TextView tvZ;
    @BindView(R.id.tv_j)
    TextView tvJ;
    @BindView(R.id.lw_a)
    ImageView lwA;
    @BindView(R.id.lw_alin)
    LinearLayout lwAlin;
    @BindView(R.id.lw_b)
    ImageView lwB;
    @BindView(R.id.lw_blin)
    LinearLayout lwBlin;
    @BindView(R.id.lw_c)
    ImageView lwC;
    @BindView(R.id.lw_clin)
    LinearLayout lwClin;
    @BindView(R.id.et_unit_cost)
    EditText etUnitCost;
    @BindView(R.id.tv_s)
    TextView tvS;
    @BindView(R.id.tv_o)
    TextView tvO;

    @BindView(R.id.ac_dc)
    TextView acDc;

    private boolean tag1 = true;
    private boolean tag2 = true;
    private boolean tag3 = true;

    private boolean tags1 = false;
    private boolean tags2 = false;
    private boolean tags3 = false;
    private boolean tags4 = false;

    private String strCost = "";

    @Override
    public void initData(@Nullable Bundle bundle) {

    }

    @Override
    public int bindLayout() {
        return R.layout.fragment_lowvoltage;
    }

    @Override
    public void initView(Bundle savedInstanceState, View contentView) {


    }

    @Override
    public void doBusiness() {

        EventBusUtils.register(this);


        etUnitCost.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                strCost = charSequence.toString();

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                String input = etUnitCost.getText().toString();

                if (input.startsWith(".")) {
                    ToastUtils.showShort("不能以小数点开头");
                    etUnitCost.setText(strCost);
                } else if (input.startsWith("0") && input.length() >= 2 && !input.startsWith("0.")) {
                    ToastUtils.showShort("0后面只能是小数点");
                    etUnitCost.setText(strCost);
                } else if (input.indexOf(".") != input.lastIndexOf(".")) {
                    ToastUtils.showShort("只能有一个小数点");
                    etUnitCost.setText(strCost);
                } else if (input.contains(".") && input.substring(input.indexOf(".") + 1).length() > 1) {
                    ToastUtils.showShort("小数点后只能一位小数");
                    etUnitCost.setText(strCost);
                } else if (!StringUtils.isEmpty(input) && tags1 ==false && Double.parseDouble(input) > 32) {
                    ToastUtils.showShort("不能大于32V");
                    etUnitCost.setText(strCost);
                }else if (!StringUtils.isEmpty(input) && tags1 ==false && Double.parseDouble(input) < 0) {
                    ToastUtils.showShort("不能小于2V");
                    etUnitCost.setText(strCost);
                }else if (!StringUtils.isEmpty(input) && tags1 ==true && Double.parseDouble(input) > 32) {
                    ToastUtils.showShort("不能大于32V");
                    etUnitCost.setText(strCost);
                }else if (!StringUtils.isEmpty(input) && tags1 ==true && Double.parseDouble(input) < 0) {
                    ToastUtils.showShort("不能小于6V");
                    etUnitCost.setText(strCost);
                }
                etUnitCost.setSelection(etUnitCost.getText().toString().length());

            }
        });


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

    @OnClick({R.id.lw_alin, R.id.lw_blin, R.id.lw_clin})
    public void onViewClicked(View view) {
        if (GlobalApp.Companion.getOpenTheSwitch()) {
            if (tags3) {

                switch (view.getId()) {

                    case R.id.lw_alin:

                        if (tag1) {
                            lwA.setImageResource(R.mipmap.off_iocn_ms);
                            tag1 = false;
                        } else {
                            lwA.setImageResource(R.mipmap.no_iocn_ms);
                            tag1 = true;
                        }

                        getDatas();

                        break;
                    case R.id.lw_blin:

                        if (tag2) {
                            lwB.setImageResource(R.mipmap.off_iocn_ms);
                            tag2 = false;
                        } else {

                            lwB.setImageResource(R.mipmap.no_iocn_ms);
                            tag2 = true;
                        }

                        getDatas();
                        break;
                    case R.id.lw_clin:

                        if (tag3) {
                            lwC.setImageResource(R.mipmap.off_iocn_ms);
                            tag3 = false;
                        } else {

                            lwC.setImageResource(R.mipmap.no_iocn_ms);
                            tag3 = true;
                        }

                        getDatas();

                        break;
                }

            }
        }

    }

    @OnClick({R.id.tv_z, R.id.tv_j, R.id.tv_s, R.id.tv_o})
    public void onViewClickeds(View view) {
        if (GlobalApp.Companion.getOpenTheSwitch() && !NoDoubleClickUtils.isDoubleClick()) {
            switch (view.getId()) {
                case R.id.tv_z:

                    if (tag1 == false && tag2 == false && tag3 == false) {

                        acDc.setText("DC");
                        tvZ.setBackground(getResources().getDrawable(R.drawable.group_t));
                        tvJ.setBackground(getResources().getDrawable(R.drawable.group_f));

                        tags1 = false;

                        getDatas();


                    }


                    break;
                case R.id.tv_j:

                    if (tag1 == false && tag2 == false && tag3 == false) {

                        acDc.setText("AC");
                        tvZ.setBackground(getResources().getDrawable(R.drawable.group_f));
                        tvJ.setBackground(getResources().getDrawable(R.drawable.group_t));

                        tags1 = true;

                        getDatas();

                    }

                    break;
                case R.id.tv_s:

                    if (tags3) {

                        tvS.setBackground(getResources().getDrawable(R.drawable.group_f));
                        tags3 = false;


                        lwA.setImageResource(R.mipmap.off_iocn_ms);
                        tag1 = false;

                        lwB.setImageResource(R.mipmap.off_iocn_ms);
                        tag2 = false;

                        lwC.setImageResource(R.mipmap.off_iocn_ms);
                        tag3 = false;

                    } else {

                        tvS.setBackground(getResources().getDrawable(R.drawable.group_t));
                        tags3 = true;

                    }

                    getDatas();

                    break;
                case R.id.tv_o:

                    if (tags4) {

                        tvO.setBackground(getResources().getDrawable(R.drawable.group_f));
                        tags4 = false;


                    } else {

                        tvO.setBackground(getResources().getDrawable(R.drawable.group_t));
                        tags4 = true;

                    }


                    getDatas();


                    break;
            }
        }
    }


    @Subscribe()
    public void onEvent(AnyEventTypes event) {
        getDialog().dismiss();

        if (StringUtils.equals("Lowvolt", event.getEventCode())) {

            LowvoltBean bean = GsonUtils.fromJson(event.getAnyData().toString(), LowvoltBean.class);

            etUnitCost.setText(StringUtils.isEmpty(bean.getLv_value())?"0":bean.getLv_value());

            if (StringUtils.equals(bean.getLv_DCAC(),"0")){//直流

                acDc.setText("DC");
                tvZ.setBackground(getResources().getDrawable(R.drawable.group_t));
                tvJ.setBackground(getResources().getDrawable(R.drawable.group_f));

                tags1 = false;

            }else if (StringUtils.equals(bean.getLv_DCAC(),"1")){//交流

                acDc.setText("AC");
                tvZ.setBackground(getResources().getDrawable(R.drawable.group_f));
                tvJ.setBackground(getResources().getDrawable(R.drawable.group_t));

                tags1 = true;

            }

            //A组开关
            if (StringUtils.equals("1",bean.getLv_akey())){//ON

                lwA.setImageResource(R.mipmap.no_iocn_ms);
                tag1 = true;


            }else if (StringUtils.equals("0",bean.getLv_akey())){

                lwA.setImageResource(R.mipmap.off_iocn_ms);
                tag1 = false;

            }

            //B组开关
            if (StringUtils.equals("1",bean.getLv_bkey())){//ON

                lwB.setImageResource(R.mipmap.no_iocn_ms);
                tag2 = true;

            }else if (StringUtils.equals("0",bean.getLv_bkey())){

                lwB.setImageResource(R.mipmap.off_iocn_ms);
                tag2 = false;

            }

            //C组开关
            if (StringUtils.equals("1",bean.getLv_tkey())){//ON

                lwC.setImageResource(R.mipmap.no_iocn_ms);
                tag3 = true;


            }else if (StringUtils.equals("0",bean.getLv_tkey())){

                lwC.setImageResource(R.mipmap.off_iocn_ms);
                tag3 = false;

            }


            //锁定电压
            if (StringUtils.equals("1",bean.getLv_lock())){ //ON

                tvS.setBackground(getResources().getDrawable(R.drawable.group_t));
                tags3 = true;

            }else if (StringUtils.equals("0",bean.getLv_lock())){

                tvS.setBackground(getResources().getDrawable(R.drawable.group_f));
                tags3 = false;

            }

            //确认输出
            if (StringUtils.equals("1",bean.getLv_sure())){//ON

                tvO.setBackground(getResources().getDrawable(R.drawable.group_t));
                tags4 = true;

            }else if (StringUtils.equals("0",bean.getLv_sure())){

                tvO.setBackground(getResources().getDrawable(R.drawable.group_f));
                tags4 = false;

            }


        }

    }

//    private void setData(){
//
//
//        HttpParam httpParam = new HttpParam();
//        httpParam.getMap().put("methodName","Lowvolt");
//        httpParam.getMap().put("lv_value",etUnitCost.getText().toString());
//        httpParam.getMap().put("lv_DCAC",tags1 == true ? "1" :"0");
//        httpParam.getMap().put("lv_akey",tag1 == true ? "1" :"0");
//        httpParam.getMap().put("lv_bkey",tag2 == true ? "1" :"0");
//        httpParam.getMap().put("lv_tkey",tag3 == true ? "1" :"0");
//        httpParam.getMap().put("lv_lock",tags3 == true ? "1" :"0");
//        httpParam.getMap().put("lv_sure",tags4 == true ? "1" :"0");
//
//        httpParam.getMap().put("dataLen", "end");
//
//        TcpClient.getInstance().sendChsPrtCmds(new Gson().toJson(httpParam.getMap()), 1001);
//
//        getDialog().show();
//
//
//    }



    public void getDatas(){

        HttpParam httpParam = new HttpParam();
        httpParam.getMap().put("methodName","Lowvolt");
        httpParam.getMap().put("lv_value",etUnitCost.getText().toString());
        httpParam.getMap().put("lv_DCAC",tags1 == false?"0":"1");
        httpParam.getMap().put("lv_akey",tag1 == true?"1":"0");
        httpParam.getMap().put("lv_bkey",tag2 == true?"1":"0");
        httpParam.getMap().put("lv_tkey",tag3 == true?"1":"0");

        httpParam.getMap().put("lv_lock",tags3 == true?"1":"0");
        httpParam.getMap().put("lv_sure",tags4 == true?"1":"0");


        httpParam.getMap().put("dataLen", "end");

        TcpClient.getInstance().sendChsPrtCmds(new Gson().toJson(httpParam.getMap()), 1001);

        getDialog().show();


    }
    public void getData(){


        HttpParam httpParam = new HttpParam();
        httpParam.getMap().put("methodName","Lowvolt");
        httpParam.getMap().put("dataLen", "end");

        TcpClient.getInstance().sendChsPrtCmds(new Gson().toJson(httpParam.getMap()), 1001);

        getDialog().show();

    }




    @Override
    public void onDestroy() {
        EventBusUtils.unregister(this);
        super.onDestroy();
    }

}
