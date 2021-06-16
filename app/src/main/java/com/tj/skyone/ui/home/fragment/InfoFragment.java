package com.tj.skyone.ui.home.fragment;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;

import com.blankj.utilcode.util.GsonUtils;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.StringUtils;
import com.google.gson.Gson;
import com.tj.skyone.R;
import com.tj.skyone.adapter.InfoAdapter;
import com.tj.skyone.base.BaseFragment;
import com.tj.skyone.bean.InofBean;
import com.tj.skyone.bean.MessageBean;
import com.tj.skyone.ui.GlobalApp;
import com.tj.skyone.utils.HttpParam;
import com.tj.skyone.utils.NoDoubleClickUtils;
import com.tj.skyone.utils.TcpClient;
import com.tj.skyone.utils.eventbus.AnyEventTypes;
import com.tj.skyone.utils.eventbus.EventBusUtils;
import com.tj.skyone.widget.MyRecyclerView;

import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @describe 消息
 * @anthor wdq
 * @time 2021/1/11 16:09
 * @email wudq@infore.com
 */
public class InfoFragment extends BaseFragment {

    @BindView(R.id.r1)
    MyRecyclerView r1;
    @BindView(R.id.r2)
    MyRecyclerView r2;
    @BindView(R.id.info_a)
    TextView infoA;
    @BindView(R.id.info_b)
    TextView infoB;
    @BindView(R.id.info_c)
    TextView infoC;
    @BindView(R.id.info_d)
    TextView infoD;
    private InfoAdapter adapter1;
    private InfoAdapter adapter2;

    @BindView(R.id.tv_fg)
    TextView tvfg;

    @BindView(R.id.tv_dy)
    TextView tvdy;

    @BindView(R.id.tv_yb)
    TextView tvyb;

    @BindView(R.id.tv_ps)
    TextView tvps;

    @BindView(R.id.tv_fgs)
    TextView tvfgs;

    @BindView(R.id.tv_dys)
    TextView tvdys;

    @BindView(R.id.tv_ybs)
    TextView tvybs;

    @BindView(R.id.tv_pss)
    TextView tvpss;

    @BindView(R.id.tv_number)
    TextView tvNumber;

    private String config = "0";

    @Override
    public void initData(@Nullable Bundle bundle) {

    }

    @Override
    public int bindLayout() {
        return R.layout.fragment_info;
    }

    @Override
    public void initView(Bundle savedInstanceState, View contentView) {

    }

    @Override
    public void doBusiness() {

        EventBusUtils.register(this);

        adapter1 = new InfoAdapter();
        adapter2 = new InfoAdapter();

        r1.setLayoutManager(new GridLayoutManager(getContext(), 2));
        r1.setAdapter(adapter1);


        r2.setLayoutManager(new GridLayoutManager(getContext(), 2));
        r2.setAdapter(adapter2);



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



    @OnClick({R.id.info_a, R.id.info_b, R.id.info_c, R.id.info_d,R.id.info_all})
    public void onViewClicked(View view) {

        if (GlobalApp.Companion.getOpenTheSwitch() && !NoDoubleClickUtils.isDoubleClick()) {

            List<InofBean> list1 = adapter1.getData();
            List<InofBean> list2 = adapter2.getData();

            switch (view.getId()) {


                case R.id.info_all:

                    config = "5";
                    sendData();

                    break;
                case R.id.info_a:

                    config = "1";
                    sendData();

                    break;
                case R.id.info_b:

                    config = "3";
                    sendData();
                    break;
                case R.id.info_c:

                    config = "2";
                    sendData();
                    break;
                case R.id.info_d:

                    config = "4";
                    sendData();

                    break;
            }
        }
    }



    private void sendData(){

        HttpParam httpParam = new HttpParam();
        httpParam.getMap().put("methodName","message");
        httpParam.getMap().put("config",config);


        httpParam.getMap().put("dataLen", "end");


        TcpClient.getInstance().sendChsPrtCmds(new Gson().toJson(httpParam.getMap()), 1001);

        getDialog().show();


    }

    @Subscribe()
    public void onEvent(AnyEventTypes event) {
        getDialog().dismiss();

        if (StringUtils.equals("message", event.getEventCode())) {

            MessageBean bean = GsonUtils.fromJson(event.getAnyData().toString(), MessageBean.class);



            String reset_fg = bean.getReset_fg();//风管
            String reset_dy = bean.getReset_dy();//低压
            String reset_yb = bean.getReset_yb();//低压
            String reset_ps = bean.getReset_ps();//排水异常


            List<InofBean> inofBeanList1 = new ArrayList<>();
            List<InofBean> inofBeanList2 = new ArrayList<>();

            int t = 0;
            for( int i = 0; i < reset_fg.length(); i++ ){

                char str_fg = reset_fg.charAt(i);
                char str_dy = reset_dy.charAt(i);
                char str_yb = reset_yb.charAt(i);
                char str_ps = reset_ps.charAt(i);

                boolean b_fg = StringUtils.equals(String.valueOf(str_fg),"1")?true:false;
                boolean b_dy = StringUtils.equals(String.valueOf(str_dy),"1")?true:false;
                boolean b_yb = StringUtils.equals(String.valueOf(str_yb),"1")?true:false;
                boolean b_ps = StringUtils.equals(String.valueOf(str_ps),"1")?true:false;

                if (i>7){

                    if ( i == reset_fg.length()-1 ){

                        inofBeanList2.add(new InofBean("老师",b_fg,b_yb,b_dy,b_ps));

                    }else{

                        inofBeanList2.add(new InofBean("B"+(t+1),b_fg,b_yb,b_dy,b_ps));
                        t++;

                    }

                }else{

                    inofBeanList1.add(new InofBean("A"+(i+1),b_fg,b_yb,b_dy,b_ps));

                }

            }

            adapter1.setNewInstance(inofBeanList1);
            adapter2.setNewInstance(inofBeanList2);


            LogUtils.e(bean.getFg_count());
            LogUtils.e(bean.getDy_count());
            LogUtils.e(bean.getYb_count());
            LogUtils.e(bean.getPs_count());

            String fg = "";
            String fgs = "";

            if (bean.getFg_count().length() == 4){

                fg = bean.getFg_count().substring(0,2);

                if (StringUtils.equals("00",fg)){

                    fg = "0";

                }

                fgs =  bean.getFg_count().substring(2,bean.getFg_count().length());

                if (StringUtils.equals("00",fgs)){

                    fgs = "0";

                }

            }

            String dy = "";
            String dys = "";


            if (bean.getDy_count().length() == 4){

                dy = bean.getDy_count().substring(0,2);

                if (StringUtils.equals("00",dy)){

                    dy = "0";

                }

                dys =  bean.getDy_count().substring(2,bean.getDy_count().length());

                if (StringUtils.equals("00",dys)){

                    dys = "0";

                }

            }

            String yb = "";
            String ybs = "";

            if (bean.getYb_count().length() == 4){

                yb = bean.getYb_count().substring(0,2);

                if (StringUtils.equals("00",yb)){

                    yb = "0";

                }

                ybs =  bean.getYb_count().substring(2,bean.getYb_count().length());

                if (StringUtils.equals("00",ybs)){

                    ybs = "0";

                }

            }

            String ps = "";
            String pss = "";

            if (bean.getPs_count().length() == 4){

                ps = bean.getPs_count().substring(0,2);

                if (StringUtils.equals("00",ps)){
                    ps = "0";
                }

                pss =  bean.getPs_count().substring(2,bean.getPs_count().length());

                if (StringUtils.equals("00",pss)){

                    pss = "0";

                }

            }


            tvfgs.setText(StringUtils.isEmpty(fg)?"0台":fg+"台");
            tvdys.setText(StringUtils.isEmpty(dy)?"0台":dy+"台");
            tvybs.setText(StringUtils.isEmpty(yb)?"0台":yb+"台");
            tvpss.setText(StringUtils.isEmpty(ps)?"0台":ps+"台");

            tvfg.setText(StringUtils.isEmpty(fgs)?"0次":fgs+"次");
            tvdy.setText(StringUtils.isEmpty(dys)?"0次":dys+"次");
            tvyb.setText(StringUtils.isEmpty(ybs)?"0次":ybs+"次");
            tvps.setText(StringUtils.isEmpty(pss)?"0次":pss+"次");

            tvNumber.setText(StringUtils.isEmpty(bean.getCount())?"在线设备  0台":"在线设备  "+bean.getCount()+"台");



        }

    }



    public void getData(){


        HttpParam httpParam = new HttpParam();
        httpParam.getMap().put("methodName","message");
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
