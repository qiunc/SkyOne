package com.tj.skyone.utils;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Looper;
import android.util.Log;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.tj.skyone.ui.GlobalApp;
import com.tj.skyone.ui.welcome.view.WelcomeActivity;

/**
 * @describe
 * @anthor wdq
 * @time 2020/6/28 11:57
 * @email wudq@infore.com
 */
public class UnCeHandler implements Thread.UncaughtExceptionHandler {

    private Thread.UncaughtExceptionHandler mDefaultHandler;
    public static final String TAG = "CatchExcep";
    GlobalApp application;

    public UnCeHandler(GlobalApp application){
        //获取系统默认的UncaughtException处理器
        mDefaultHandler = Thread.getDefaultUncaughtExceptionHandler();
        this.application = application;
    }

    @Override
    public void uncaughtException(Thread thread, Throwable ex) {
        if(!handleException(ex) && mDefaultHandler != null){
            //如果用户没有处理则让系统默认的异常处理器来处理
            mDefaultHandler.uncaughtException(thread, ex);
        }else{
            try{
                Thread.sleep(2000);
            }catch (InterruptedException e){
                Log.e(TAG, "error : ", e);
            }
            Intent intent = new Intent(application.getApplicationContext(), WelcomeActivity.class);
            @SuppressLint("WrongConstant") PendingIntent restartIntent = PendingIntent.getActivity(
                    application.getApplicationContext(), 0, intent,
                    Intent.FLAG_ACTIVITY_NEW_TASK);
            //退出程序
            AlarmManager mgr = (AlarmManager)application.getSystemService(Context.ALARM_SERVICE);
            mgr.set(AlarmManager.RTC, System.currentTimeMillis() + 1000,
                    restartIntent); // 1秒钟后重启应用
//            application.finishActivity();
            ActivityUtils.finishAllActivities();
            android.os.Process.killProcess(android.os.Process.myPid());
        }
    }

    /**
     * 自定义错误处理,收集错误信息 发送错误报告等操作均在此完成.
     *
     * @param ex
     * @return true:如果处理了该异常信息;否则返回false.
     */
    private boolean handleException(Throwable ex) {
        if (ex == null) {
            return false;
        }
        //使用Toast来显示异常信息
        new Thread(){
            @Override
            public void run() {
                Looper.prepare();
                ToastUtils.showLong( "很抱歉,程序出现异常,即将退出.");

                Looper.loop();
            }
        }.start();
        return true;
    }
}
