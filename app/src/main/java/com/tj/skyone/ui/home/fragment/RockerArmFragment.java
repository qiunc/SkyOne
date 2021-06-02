package com.tj.skyone.ui.home.fragment;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.blankj.utilcode.util.GsonUtils;
import com.blankj.utilcode.util.StringUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.google.gson.Gson;
import com.tj.skyone.R;
import com.tj.skyone.adapter.GropAdapter;
import com.tj.skyone.base.BaseFragment;
import com.tj.skyone.bean.GroupBean;
import com.tj.skyone.bean.RockerBean;
import com.tj.skyone.ui.GlobalApp;
import com.tj.skyone.utils.HttpParam;
import com.tj.skyone.utils.TcpClient;
import com.tj.skyone.utils.eventbus.AnyEventTypes;
import com.tj.skyone.utils.eventbus.EventBusUtils;

import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @describe 摇臂
 * @anthor wdq
 * @time 2021/1/11 16:09
 * @email wudq@infore.com
 */
public class RockerArmFragment extends BaseFragment {

    @BindView(R.id.r1)
    RecyclerView r1;
    @BindView(R.id.r2)
    RecyclerView r2;
    @BindView(R.id.btn_a)
    Button btnA;
    @BindView(R.id.btn_c)
    Button btnC;
    @BindView(R.id.lin_top)
    FrameLayout linTop;
    @BindView(R.id.btn_go)
    FrameLayout btnGo;
    @BindView(R.id.btn_bottom)
    FrameLayout btnBottom;

    private GropAdapter gropAdapter1;
    private GropAdapter gropAdapter2;

    @BindView(R.id.img)
    ImageView imageView;
    @BindView(R.id.name)
    TextView name;


    private Integer rRuns = 0;


    private boolean tag = true;
    private boolean tags = false;


    private boolean tag1 = false;
    private boolean tag2 = true;
    private boolean tag3 = false;

    @Override
    public void initData(@Nullable Bundle bundle) {

    }

    @Override
    public int bindLayout() {
        return R.layout.fragment_rocker_arm;
    }

    @Override
    public void initView(Bundle savedInstanceState, View contentView) {

    }

    @Override
    public void doBusiness() {
        EventBusUtils.register(this);

        imageView.setBackgroundResource(R.mipmap.b_icon);
        name.setText("老师");

        gropAdapter1 = new GropAdapter();
        r1.setLayoutManager(new LinearLayoutManager(getContext()));
        r1.setAdapter(gropAdapter1);


        gropAdapter2 = new GropAdapter();
        r2.setLayoutManager(new LinearLayoutManager(getContext()));
        r2.setAdapter(gropAdapter2);


        gropAdapter1.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull BaseQuickAdapter<?, ?> adapter, @NonNull View view, int position) {

                List<GroupBean> list = (List<GroupBean>) adapter.getData();

                if (list.get(position).isSelect()) {

                    list.get(position).setSelect(false);

                } else {

                    list.get(position).setSelect(true);

                }

                gropAdapter1.notifyDataSetChanged();

            }
        });


        gropAdapter2.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull BaseQuickAdapter<?, ?> adapter, @NonNull View view, int position) {

                List<GroupBean> list = (List<GroupBean>) adapter.getData();

                if (list.get(position).isSelect()) {

                    list.get(position).setSelect(false);

                } else {

                    list.get(position).setSelect(true);

                }
                gropAdapter2.notifyDataSetChanged();

            }
        });

    }

    @Override
    public void onWidgetClick(View view) {

    }

    @Override
    public void isStart() {



    }

    @Subscribe()
    public void onEvent(AnyEventTypes event) {

        getDialog().dismiss();
        if (StringUtils.equals("rocker",event.getEventCode())) {


            RockerBean bean = GsonUtils.fromJson(event.getAnyData().toString(),RockerBean.class);

            String agroup =  bean.getR_Agroup();
            String bgroup =  bean.getR_Bgroup();

            List<GroupBean> list1 = new ArrayList<>();

            for( int i=0; i < agroup.length(); i++ ){

                GroupBean beans = new GroupBean();
                beans.setName("A"+(i+1));

                char ch = agroup.charAt(i);

                if (StringUtils.equals(String.valueOf(ch),"1")){

                    beans.setTag(true);

                }
                if (gropAdapter1.getData().size() > 0 ){

                    beans.setSelect(gropAdapter1.getData().get(i).isSelect());
                }

                list1.add(beans);

            }

            gropAdapter1.setList(list1);

            List<GroupBean> list2 = new ArrayList<>();

            for( int i = 0; i < bgroup.length() ; i++ ){

                GroupBean beans = new GroupBean();
                beans.setName("B"+(i+1));

                char ch = bgroup.charAt(i);

                if (StringUtils.equals(String.valueOf(ch),"1")){

                    beans.setTag(true);

                }

                if (gropAdapter2.getData().size() > 0 ){

                    beans.setSelect(gropAdapter2.getData().get(i).isSelect());

                }


                list2.add(beans);

            }

            gropAdapter2.setList(list2);

            String rRun =  bean.getR_type();

            rRuns =  Integer.valueOf(rRun);

            if (tags == true && Integer.valueOf(rRun) == 0){

                imageView.setBackgroundResource(R.mipmap.g_icon);

            }else if (tags == true && Integer.valueOf(rRun) == 1){

                imageView.setBackgroundResource(R.mipmap.gg_icon);

            }else if (tags == false && Integer.valueOf(rRun) == 0){

                imageView.setBackgroundResource(R.mipmap.b_icon);

            }else if (tags == false && Integer.valueOf(rRun) == 1){

                imageView.setBackgroundResource(R.mipmap.b_g_icon);

            }


            if (StringUtils.equals(bean.getR_up(),"1")) {

                linTop.setBackgroundResource(R.drawable.group_t);
            }else{

                linTop.setBackgroundResource(R.drawable.group_f);
            }


             if (StringUtils.equals(bean.getR_pause(),"1")){

                 btnGo.setBackgroundResource(R.drawable.group_t);

            }else{

                 btnGo.setBackgroundResource(R.drawable.group_f);

             }

            if (StringUtils.equals(bean.getR_down(),"1")){

                btnBottom.setBackgroundResource(R.drawable.group_t);

            }else{

                btnBottom.setBackgroundResource(R.drawable.group_f);

            }


//            if (StringUtils.equals(bean.getType(),"1")){
//
//                name.setText("老师");
//
//            }else if (StringUtils.equals(bean.getType(),"0")){
//
//                name.setText("学生");
//
//            }

        }
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


    private void check(boolean b) {

        String rRun  = rRuns+"";

        if (b == true && Integer.valueOf(rRun) == 0){

            imageView.setBackgroundResource(R.mipmap.g_icon);

        }else if (b == true && Integer.valueOf(rRun) == 1){

            imageView.setBackgroundResource(R.mipmap.gg_icon);

        }else if (b == false && Integer.valueOf(rRun) == 0){

            imageView.setBackgroundResource(R.mipmap.b_icon);

        }else if (b == false && Integer.valueOf(rRun) == 1){

            imageView.setBackgroundResource(R.mipmap.b_g_icon);

        }

        tags = b;

    }


    @OnClick({R.id.btn_a, R.id.btn_c, R.id.lin_top, R.id.btn_go, R.id.btn_bottom, R.id.img})
    public void onViewClicked(View view) {

        if (GlobalApp.Companion.getOpenTheSwitch()) {

            List<GroupBean> list1 = (List<GroupBean>) gropAdapter1.getData();
            List<GroupBean> list2 = (List<GroupBean>) gropAdapter2.getData();
            switch (view.getId()) {


                case R.id.img:
                    if (tags) {

                        tags = false;

                    } else {

                        tags = true;

                    }


                    if (tags == true && rRuns == 0) {

                        imageView.setBackgroundResource(R.mipmap.g_icon);

                    } else if (tags == true && rRuns == 1) {

                        imageView.setBackgroundResource(R.mipmap.gg_icon);

                    } else if (tags == false && rRuns == 0) {

                        imageView.setBackgroundResource(R.mipmap.b_icon);

                    } else if (tags == false && rRuns == 1) {

                        imageView.setBackgroundResource(R.mipmap.b_g_icon);

                    }


                    break;

                case R.id.btn_a:

                    check(true);

                    for (GroupBean bean : list1) {

                        bean.setSelect(true);

                    }

                    gropAdapter1.notifyDataSetChanged();


                    for (GroupBean bean : list2) {

                        bean.setSelect(true);

                    }

                    gropAdapter2.notifyDataSetChanged();


                    break;
                case R.id.btn_c:

                    check(false);

                    for (GroupBean bean : list1) {

                        bean.setSelect(false);

                    }

                    gropAdapter1.notifyDataSetChanged();


                    for (GroupBean bean : list2) {

                        bean.setSelect(false);

                    }

                    gropAdapter2.notifyDataSetChanged();

                    break;
                case R.id.lin_top:

                    if (tag2) {

                        tag1 = true;
                        tag2 = false;
                        tag3 = false;

                        linTop.setBackgroundResource(R.drawable.group_t);
                        btnGo.setBackgroundResource(R.drawable.group_f);
                        btnBottom.setBackgroundResource(R.drawable.group_f);


                        check(0);

                    }


                    break;

                case R.id.btn_go:

                    tag1 = false;
                    tag2 = true;
                    tag3 = false;

                    linTop.setBackgroundResource(R.drawable.group_f);
                    btnGo.setBackgroundResource(R.drawable.group_t);
                    btnBottom.setBackgroundResource(R.drawable.group_f);


                    check(1);
                    break;

                case R.id.btn_bottom:

                    if (tag2) {

                        tag1 = false;
                        tag2 = false;
                        tag3 = true;

                        linTop.setBackgroundResource(R.drawable.group_f);
                        btnGo.setBackgroundResource(R.drawable.group_f);
                        btnBottom.setBackgroundResource(R.drawable.group_t);

                        check(2);

                    }


                    break;
            }
        }
    }


    public void getData(){

        HttpParam httpParam = new HttpParam();
        httpParam.getMap().put("methodName","rocker");
        httpParam.getMap().put("dataLen", "end");

        TcpClient.getInstance().sendChsPrtCmds(new Gson().toJson(httpParam.getMap()), 1001);

        getDialog().show();

    }


    private void check (int i){

        List<GroupBean> listBean1 = gropAdapter1.getData();
        List<GroupBean> listBean2 = gropAdapter2.getData();

        String r_Agroup = "";
        for (GroupBean bean: listBean1){

            if (bean.isSelect()){
                r_Agroup = r_Agroup+"1";
            }else{
                r_Agroup = r_Agroup+"0";
            }

        }

        String r_Bgroup = "";
        for (GroupBean bean: listBean2){

            if (bean.isSelect()){
                r_Bgroup = r_Bgroup+"1";
            }else{
                r_Bgroup = r_Bgroup+"0";
            }

        }

        HttpParam httpParam = new HttpParam();
        httpParam.getMap().put("methodName","rocker");
        httpParam.getMap().put("r_Agroup",r_Agroup);
        httpParam.getMap().put("r_Bgroup",r_Bgroup);

        if (i == 0){
            httpParam.getMap().put("r_up","1");
            httpParam.getMap().put("r_down","0");
            httpParam.getMap().put("r_pause","0");

        }else if (i == 1){

            httpParam.getMap().put("r_up","0");
            httpParam.getMap().put("r_down","0");
            httpParam.getMap().put("r_pause","1");

        }else if (i == 2){

            httpParam.getMap().put("r_up","0");
            httpParam.getMap().put("r_down","1");
            httpParam.getMap().put("r_pause","0");

        }


        httpParam.getMap().put("r_type",tags == true?"1":"0");

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
