package com.tj.skyone.ui.home.view;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.core.widget.PopupWindowCompat;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.blankj.utilcode.util.GsonUtils;
import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.google.gson.Gson;
import com.tj.skyone.R;
import com.tj.skyone.adapter.SaveStatePagerAdapter;
import com.tj.skyone.base.BaseActivity;
import com.tj.skyone.bean.HomeBean;
import com.tj.skyone.bean.HomeCheckBean;
import com.tj.skyone.ui.GlobalApp;
import com.tj.skyone.ui.home.fragment.DrainageFragment;
import com.tj.skyone.ui.home.fragment.HighVoltageFragment;
import com.tj.skyone.ui.home.fragment.HomeFragment;
import com.tj.skyone.ui.home.fragment.InfoFragment;
import com.tj.skyone.ui.home.fragment.LightingFragment;
import com.tj.skyone.ui.home.fragment.LowVoltageFragment;
import com.tj.skyone.ui.home.fragment.RockerArmFragment;
import com.tj.skyone.ui.home.fragment.VentilationFragment;
import com.tj.skyone.utils.HttpParam;
import com.tj.skyone.utils.NoDoubleClickUtils;
import com.tj.skyone.utils.TcpClient;
import com.tj.skyone.utils.eventbus.AnyEventTypes;
import com.tj.skyone.utils.eventbus.EventBusUtils;
import com.tj.skyone.widget.dialog.AboutDialog;
import com.tj.skyone.widget.dialog.CreateUserDialog;
import com.tj.skyone.widget.dialog.HomePopWindow;
import com.tj.skyone.widget.dialog.PubDialog;
import com.tj.skyone.widget.dialog.UseDialog;

import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
@SuppressLint("NonConstantResourceId")
public class HomeActivity extends BaseActivity {

    @BindView(R.id.img_yb)
    ImageView imgYb;
    @BindView(R.id.img_gps)
    ImageView imgGps;
    @BindView(R.id.img_gy)
    ImageView imgGy;
    @BindView(R.id.img_tf)
    ImageView imgTf;
    @BindView(R.id.img_zm)
    ImageView imgZm;
    @BindView(R.id.img_dy)
    ImageView imgDy;
    private AlertDialog alertDialog;


    @BindView(R.id.btn1)
    LinearLayout btn1;
    @BindView(R.id.btn2)
    LinearLayout btn2;
    @BindView(R.id.btn3)
    LinearLayout btn3;
    @BindView(R.id.btn4)
    LinearLayout btn4;
    @BindView(R.id.btn5)
    LinearLayout btn5;
    @BindView(R.id.btn6)
    LinearLayout btn6;
    @BindView(R.id.btn7)
    LinearLayout btn7;
    @BindView(R.id.btn8)
    LinearLayout btn8;
    @BindView(R.id.lin_top)
    LinearLayout linTop;
    @BindView(R.id.lin_tops)
    LinearLayout linTops;
    @BindView(R.id.test)
    ImageView test;
    @BindView(R.id.home_a)
    TextView homeA;
    @BindView(R.id.home_b)
    TextView homeB;
    @BindView(R.id.btn_right)
    ImageView btnRight;
    @BindView(R.id.btn_off)
    ImageView btnOff;
    private List<LinearLayout> linearLayoutList;
    List<Fragment> fragmentList;

    @BindView(R.id.mViewPager)
    ViewPager viewPager;

    private HomeFragment homeFragment;
    private RockerArmFragment rockerArmFragment;
    private DrainageFragment drainageFragment;
    private HighVoltageFragment highVoltageFragment;
    private VentilationFragment ventilationFragment;
    private LightingFragment lightingFragment;
    private LowVoltageFragment lowVoltageFragment;
    private InfoFragment infoFragment;

    private CreateUserDialog createUserDialog;


    private boolean onOff = false;


    @Override
    protected int getLayoutId() {
        if(GlobalApp.Companion.isPad()) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        }
        return R.layout.activity_home;
    }

    //初始化布局参数
    @Override
    protected void initEventAndData() {
        EventBusUtils.register(this);

        //防止Home按键app重启
        if (!isTaskRoot()) {
            Intent intent = getIntent();
            String action = intent.getAction();
            if (intent.hasCategory(Intent.CATEGORY_LAUNCHER) && Intent.ACTION_MAIN.equals(action)) {
                finish();
                return;
            }
        }


        fragmentList = new ArrayList<>();
        linearLayoutList = new ArrayList<>();
        homeFragment = new HomeFragment();//首页
        rockerArmFragment = new RockerArmFragment();//摇臂
        drainageFragment = new DrainageFragment();//给排水
        highVoltageFragment = new HighVoltageFragment();//高压
        ventilationFragment = new VentilationFragment();//通风
        lightingFragment = new LightingFragment();//照明
        lowVoltageFragment = new LowVoltageFragment();//低压
        infoFragment = new InfoFragment();//信息

        fragmentList.add(homeFragment);
        fragmentList.add(rockerArmFragment);
        fragmentList.add(drainageFragment);
        fragmentList.add(highVoltageFragment);
        fragmentList.add(ventilationFragment);
        fragmentList.add(lightingFragment);
        fragmentList.add(lowVoltageFragment);
        fragmentList.add(infoFragment);

        SaveStatePagerAdapter adapter = new SaveStatePagerAdapter(getSupportFragmentManager(), viewPager, fragmentList);
        viewPager.setAdapter(adapter);
        viewPager.setOffscreenPageLimit(fragmentList.size());


        linearLayoutList.add(btn1);
        linearLayoutList.add(btn2);
        linearLayoutList.add(btn3);
        linearLayoutList.add(btn4);
        linearLayoutList.add(btn5);
        linearLayoutList.add(btn6);
        linearLayoutList.add(btn7);
        linearLayoutList.add(btn8);


    }

    @Override
    protected void isStart() {

        //向设备发进入主页面指令
        HttpParam httpParam = new HttpParam();
        httpParam.getMap().put("methodName", "homepage");
        httpParam.getMap().put("dataLen", "end");

        TcpClient.getInstance().sendChsPrtCmds(new Gson().toJson(httpParam.getMap()), 1001);


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


    @Subscribe()
    public void onEvent(AnyEventTypes event) {
        //解除加载等待框


        //if (alertDialog != null) alertDialog.dismiss();

        //返回标识为temphumi  激活订阅事件
        if (StringUtils.equals("temphumi", event.getEventCode())) {
            getDialog().dismiss();

            HomeBean bean = GsonUtils.fromJson(event.getAnyData().toString(), HomeBean.class);
            //设置主界面温度
            homeA.setText(StringUtils.isEmpty(bean.getTemperature()) ? "0℃" : bean.getTemperature() + "℃");
            //设置主界面湿度
            homeB.setText(StringUtils.isEmpty(bean.getHumidity()) ? "0%" : bean.getHumidity() + "%");

            String runstatus = bean.getRunstatus();

            for (int i = 0; i < runstatus.length(); i++) {

                char ch = runstatus.charAt(i);

                if (i == 0) {

                    imgYb.setImageResource(StringUtils.equals("1", String.valueOf(ch)) ? R.drawable.ovalsts : R.drawable.ovals);

                } else if (i == 1) {

                    imgGps.setImageResource(StringUtils.equals("1", String.valueOf(ch)) ? R.drawable.ovalsts : R.drawable.ovals);

                } else if (i == 2) {

                    imgGy.setImageResource(StringUtils.equals("1", String.valueOf(ch)) ? R.drawable.ovalsts : R.drawable.ovals);

                } else if (i == 3) {

                    imgTf.setImageResource(StringUtils.equals("1", String.valueOf(ch)) ? R.drawable.ovalsts : R.drawable.ovals);

                } else if (i == 4) {

                    imgZm.setImageResource(StringUtils.equals("1", String.valueOf(ch)) ? R.drawable.ovalsts : R.drawable.ovals);

                } else if (i == 5) {

                    imgDy.setImageResource(StringUtils.equals("1", String.valueOf(ch)) ? R.drawable.ovalsts : R.drawable.ovals);

                }

            }


        } else if (StringUtils.equals("homepage", event.getEventCode())) {

            //dismissLoadingDialog();

            HomeCheckBean bean = GsonUtils.fromJson(event.getAnyData().toString(), HomeCheckBean.class);
            Log.e("returnData", bean.getKey());
            if (StringUtils.equals("ON", bean.getKey())) {
                getDialog().dismiss();

                if (!onOff) {
                    onOff = true;
                    GlobalApp.Companion.setOpenTheSwitch(true);
                    btnOff.setImageResource(R.mipmap.home_btn_d);

                } else {
                    PubDialog dialog = new PubDialog(this, true, "关机确认", "有部件未到达原位，请检查设备后关机！！");
                    dialog.show();
                    dialog.img.setImageResource(R.mipmap.gj_icon);

                    dialog.btn.setOnClickListener(view1 -> {
                        dialog.dismiss();
                    });
                }
            } else {
                getDialog().dismiss();
                onOff = false;
                GlobalApp.Companion.setOpenTheSwitch(false);
                btnOff.setImageResource(R.mipmap.home_btn_ds);
                viewPager.setCurrentItem(0);
                showView(0);

            }

        }

//        else if (event.getEventCode() == EventBusConsts.CT_USER) {//创建成功
//
//
//            if (createUserDialog != null)
//                createUserDialog.dismiss();
//
//            PubDialog dialog = new PubDialog(this, true, "", "账号注册成功，请牢记登录密码");
//            dialog.show();
//
//
//        } else if (event.getEventCode() == EventBusConsts.UP_USER) {//修复改密码
//
//
//            if (createUserDialog != null)
//                createUserDialog.dismiss();
//
//            PubDialog dialog = new PubDialog(this, true, "", "密码修改成功，请牢记新密码");
//            dialog.show();
//
//
//        } else if (event.getEventCode() == EventBusConsts.D_USER) {//修复改密码
//
//
//            if (createUserDialog != null)
//                createUserDialog.dismiss();
//
//            PubDialog dialog = new PubDialog(this, true, "", "用户删除成功！");
//            dialog.show();
//
//
//        }


    }

    /**
     * 底部菜单切换高亮
     * @param i 菜单下标
     */
    private void showView(int i) {

        if (i == 0) {

            linTop.setVisibility(View.INVISIBLE);

        } else {

            linTop.setVisibility(View.VISIBLE);

        }

        for (LinearLayout linearLayout : linearLayoutList) {

            linearLayout.setBackground(getResources().getDrawable(R.color.color_00000000));

        }

        linearLayoutList.get(i).setBackground(getResources().getDrawable(R.mipmap.home_btn_bg));

    }


    private static int makeDropDownMeasureSpec(int measureSpec) {
        int mode;
        if (measureSpec == ViewGroup.LayoutParams.WRAP_CONTENT) {
            mode = View.MeasureSpec.UNSPECIFIED;
        } else {
            mode = View.MeasureSpec.EXACTLY;
        }
        return View.MeasureSpec.makeMeasureSpec(View.MeasureSpec.getSize(measureSpec), mode);
    }

    private int mOffsetX = 0;
    private int mOffsetY = 0;
    private int mGravity = Gravity.START;


    @SuppressLint("NonConstantResourceId")
    @OnClick({R.id.test, R.id.lin_tops, R.id.btn_off})
    public void onViewClicked(View view) {

        if (!NoDoubleClickUtils.isDoubleClick()) {

            switch (view.getId()) {
                case R.id.btn_off:


                    if (onOff) {

//                        PubDialog dialog = new PubDialog(this, true, "关机确认", "有部件未到达原位，请检查设备后关机！！");
//                        dialog.show();
//                        dialog.img.setImageResource(R.mipmap.gj_icon);
//
//                        dialog.btn.setOnClickListener(view1 -> {
//                            dialog.dismiss();

                        HttpParam httpParam = new HttpParam();
                        httpParam.getMap().put("methodName", "homepage");
                        httpParam.getMap().put("key", "OFF");
                        httpParam.getMap().put("dataLen", "end");

                        TcpClient.getInstance().sendChsPrtCmds(new Gson().toJson(httpParam.getMap()), 1001);

                        getDialog().show();
//                            alertDialog = new AlertDialog.Builder(atys).create();
//                            alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable());
//                            alertDialog.setCancelable(false);
//                            alertDialog.setOnKeyListener((dialog12, keyCode, event) -> {
//                                return keyCode == KeyEvent.KEYCODE_SEARCH || keyCode == KeyEvent.KEYCODE_BACK;
//                            });
//                            alertDialog.show();
//                            alertDialog.setContentView(R.layout.loading_alert);
//                            alertDialog.setCanceledOnTouchOutside(false);

//                        });

                    } else {

//                        PubDialog dialog = new PubDialog(this, true, "开机确认", "有部件未到达原位，请检查设备后开机！！");
//                        dialog.show();
//                        dialog.img.setImageResource(R.mipmap.gj_icon);
//
//                        dialog.btn.setOnClickListener(new View.OnClickListener() {
//                            @Override
//                            public void onClick(View view) {
//
//                                dialog.dismiss();


                        HttpParam httpParam = new HttpParam();
                        httpParam.getMap().put("methodName", "homepage");
                        httpParam.getMap().put("key", "ON");

                        httpParam.getMap().put("dataLen", "end");

                        TcpClient.getInstance().sendChsPrtCmds(new Gson().toJson(httpParam.getMap()), 1001);

                        getDialog().show();
//                        alertDialog = new AlertDialog.Builder(atys).create();
//                        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable());
//                        alertDialog.setCancelable(false);
//                        alertDialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
//                            @Override
//                            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
//                                return keyCode == KeyEvent.KEYCODE_SEARCH || keyCode == KeyEvent.KEYCODE_BACK;
//                            }
//                        });
//                        alertDialog.show();
//                        alertDialog.setContentView(R.layout.loading_alert);
//                        alertDialog.setCanceledOnTouchOutside(false);
                        //                           }
//                        });
                    }
                    break;

                case R.id.test:
                    readyGo(RecordActivity.class);
                    break;
                case R.id.lin_tops:

                    HomePopWindow homePopWindow = new HomePopWindow(this);
                    View contentView = homePopWindow.getContentView();

                    contentView.measure(makeDropDownMeasureSpec(homePopWindow.getWidth()), makeDropDownMeasureSpec(homePopWindow.getHeight()));

                    mGravity = Gravity.START;


//                //时间
//                homePopWindow.view1.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//
//                    }
//                });

                    //用户
                    homePopWindow.view2.setOnClickListener(view12 -> {

//                        UserDialog userDialog = new UserDialog(HomeActivity.this);
//                        userDialog.show();

                        createUserDialog = new CreateUserDialog(HomeActivity.this, true);
                        createUserDialog.show();


//                        userDialog.add.setOnClickListener(new View.OnClickListener() {
//                            @Override
//                            public void onClick(View view) {
//
//
////                                createUserDialog = new CreateUserDialog(HomeActivity.this, false);
////                                createUserDialog.show();
//
//                                createUserDialog = new CreateUserDialog(HomeActivity.this, true);
//                                createUserDialog.show();
//                            }
//                        });
//
//                        userDialog.update.setOnClickListener(new View.OnClickListener() {
//                            @Override
//                            public void onClick(View view) {
//
//
//                                createUserDialog = new CreateUserDialog(HomeActivity.this, true);
//                                createUserDialog.show();
//                            }
//                        });
//
//
//                        userDialog.mgt.setOnClickListener(new View.OnClickListener() {
//                            @Override
//                            public void onClick(View view) {
//
//
//                                MgtDialog mgtDialog = new MgtDialog(HomeActivity.this);
//                                mgtDialog.show();
//                            }
//                        });


                    });

                    //关于
                    homePopWindow.view3.setOnClickListener(view13 -> {

                        AboutDialog dialog = new AboutDialog(HomeActivity.this);
                        dialog.show();

                    });

                    //使用
                    homePopWindow.view4.setOnClickListener(view14 -> {

                        UseDialog dialog = new UseDialog(HomeActivity.this);
                        dialog.show();


                    });

                    homePopWindow.view5.setOnClickListener(view15 -> {
                        PubDialog dialog1 = new PubDialog(HomeActivity.this, false, "退出", "是否退出！", true, true);
                        dialog1.show();
                        dialog1.btn.setOnClickListener(view1512 -> {

                            finish();
                            System.exit(0);


                        });

                        dialog1.ok.setOnClickListener(view151 -> {
                            dialog1.dismiss();
                        });


                    });
                    PopupWindowCompat.showAsDropDown(homePopWindow, linTops, mOffsetX, mOffsetY, mGravity);
                    break;
            }

        }

    }

    @SuppressLint("NonConstantResourceId")
    @OnClick({R.id.btn1, R.id.btn2, R.id.btn3, R.id.btn4, R.id.btn5, R.id.btn6, R.id.btn7, R.id.btn8})
    public void onViewClickeds(View view) {
        if (!NoDoubleClickUtils.isDoubleClick()) {

            if (onOff) {


                switch (view.getId()) {

                    case R.id.btn1:

                        viewPager.setCurrentItem(0);
                        showView(0);
                        break;

                    case R.id.btn2:
                        viewPager.setCurrentItem(1);
                        showView(1);

                        rockerArmFragment.getData();

                        break;
                    case R.id.btn3:
                        viewPager.setCurrentItem(2);
                        showView(2);

                        drainageFragment.getData();

                        break;
                    case R.id.btn4:
                        viewPager.setCurrentItem(3);
                        showView(3);
                        highVoltageFragment.getData();
                        break;
                    case R.id.btn5:
                        viewPager.setCurrentItem(4);
                        showView(4);
                        ventilationFragment.getData();
                        break;
                    case R.id.btn6:
                        viewPager.setCurrentItem(5);
                        showView(5);
                        lightingFragment.getData();
                        break;
                    case R.id.btn7:
                        viewPager.setCurrentItem(6);
                        showView(6);
                        lowVoltageFragment.getData();
                        break;
                    case R.id.btn8:
                        viewPager.setCurrentItem(7);
                        showView(7);
                        infoFragment.getData();
                        break;
                }
            }
        }


    }

    @Override
    protected void onDestroy() {
        EventBusUtils.unregister(this);
        super.onDestroy();
    }

    private long exitTime = 0; //用户再按一次退出程序时间记录

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            //判断点击的是返回键，并且动作是Down按下
            if ((System.currentTimeMillis() - exitTime) > 3000) {
                ToastUtils.showShort("再按一次离开应用");
                exitTime = System.currentTimeMillis();
            } else {
                finish();
                System.exit(0);
            }

            return true;
        }


        return super.onKeyDown(keyCode, event);
    }


    public void dismissLoadingDialog() {
        if (null != alertDialog && alertDialog.isShowing()) {
            alertDialog.dismiss();
        }
    }

}