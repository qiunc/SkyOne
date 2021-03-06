package com.tj.skyone.base;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;

import androidx.annotation.LayoutRes;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.blankj.utilcode.util.BarUtils;
import com.blankj.utilcode.util.ServiceUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.tj.skyone.service.MyService;
import com.tj.skyone.ui.GlobalApp;
import com.tj.skyone.widget.dialog.LoadingDialog;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import me.jessyan.autosize.internal.CustomAdapt;

/**
 * description: Activity基类
 * Created by wdq
 * date: 2017/12/26  15:23
 */
public abstract class BaseActivity extends AppCompatActivity implements IBaseView, CustomAdapt {

    private boolean isButterKnife = true;
    private boolean isStart = true;

    public Activity atys;

    /**
     * ButterKnife
     */
    private Unbinder butterKnife;

    private LoadingDialog dialog;


    @SuppressLint("NewApi")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(GlobalApp.Companion.isPad()) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        } else {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }
        //防止Home按键app重启
        if (!isTaskRoot()) {
            Intent intent = getIntent();
            String action = intent.getAction();
            if (intent.hasCategory(Intent.CATEGORY_LAUNCHER) && Intent.ACTION_MAIN.equals(action)) {
                finish();
                return;
            }
        }

        try {
            //封装获取id
            setContentView(getLayoutId());
            atys = this;
            butterKnife = ButterKnife.bind(this);

            //隐藏状态栏
            BarUtils.setStatusBarVisibility(this,false);
            //初始化数据
            initEventAndData();
        } catch (Exception e) {

            ToastUtils.showLong("系统异常："+e.toString()+"："+e.getMessage());
            e.printStackTrace();
        }
    }



    @Override
    protected void onResume() {
        try {
            //检查后台服务状态
            if (!ServiceUtils.isServiceRunning(MyService.class))
                ServiceUtils.startService(MyService.class);

        } catch ( Exception e) {
            e.printStackTrace();

        }
        super.onResume();


    }

    @Override
    protected void onStop() {

//        try {
//
//            if (ServiceUtils.isServiceRunning(MyService.class))
//                ServiceUtils.stopService(MyService.class);
//
//        } catch ( Exception e) {
//            e.printStackTrace();
//
//        }

        super.onStop();


    }

    @Override
    protected void onStart() {
        super.onStart();
        if (isStart) {
            isStart = false;
            try {
                isStart();
            } catch (Exception e) {
                ToastUtils.showLong("系统异常："+e.toString()+"："+e.getMessage());
                e.printStackTrace();
            }
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (dialog != null) dialog.dismiss();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (isButterKnife) if (butterKnife != null) butterKnife.unbind();
        if (dialog != null) dialog.cancel();
        atys = null;
    }

    public void setIsButterKnife(boolean isButterKnife) {
        this.isButterKnife = isButterKnife;
    }

    //跳转至另一个activity
    protected void readyGo(Class<?> clazz) {
        Intent intent = new Intent(this, clazz);
        startActivity(intent);
    }

    //带参数的跳转至另一个activity
    protected void readyGo(Class<?> clazz, Bundle bundle) {
        Intent intent = new Intent(this, clazz);
        if (null != bundle) {
            intent.putExtras(bundle);
        }
        startActivity(intent);
    }

    //初始化Dialog
    @Override
    public LoadingDialog getDialog() {
        if (dialog == null) {
            dialog = new LoadingDialog(this);
        }
        return dialog;
    }

    //初始化Dialog
    @Override
    public LoadingDialog getDialog(boolean cancel) {
        if (dialog == null) {
            dialog = new LoadingDialog(this);
        }
        dialog.setCanceledOnTouchOutside(cancel);
        return dialog;
    }

    //设置Dialog标题
    @Override
    public LoadingDialog getDialog(String title) {
        if (dialog == null) {
            dialog = new LoadingDialog(this, title);
        } else {
            if (dialog.isShowing()) {
                dialog.setTitle(title);
            } else {
                dialog = new LoadingDialog(this, title);
            }
        }
        return dialog;
    }

    /**
     * 设置 app 不随着系统字体的调整而变化
     */
    @Override
    public Resources getResources() {
        Resources res = super.getResources();
        Configuration config = new Configuration();
        config.setToDefaults();
        res.updateConfiguration(config, res.getDisplayMetrics());
        return res;
    }

    @Override
    public boolean isBaseOnWidth() {
        return true;
    }

    @Override
    public float getSizeInDp() {
        if (GlobalApp.Companion.isPad()) {
            return (float) 853;
        } else {
            return (float) 392.0;
        }
    }

    //获取布局id
    protected abstract @LayoutRes int  getLayoutId();

    //获取沉淀式布局
//    protected abstract View getStatusBarView();

    //初始化布局参数
    protected abstract void initEventAndData();

    //Start第一次启动
    protected abstract void isStart();



}
