package com.tj.skyone.ui

import android.app.Activity
import android.app.Application
import android.content.Context
import android.content.pm.ActivityInfo
import android.content.pm.PackageManager
import android.os.Bundle
import com.blankj.utilcode.util.LogUtils
import com.blankj.utilcode.util.ObjectUtils
import com.blankj.utilcode.util.SPUtils
import com.blankj.utilcode.util.Utils
import com.tj.skyone.utils.CrashHandler
import com.tj.skyone.utils.UnCeHandler
import com.yatoooon.screenadaptation.ScreenAdapterTools


/**
 * @describe
 * @anthor wdq
 * @time 2020/6/8 09:20
 * @email wudq@infore.com
 */
class GlobalApp : Application() {


    companion object {
        lateinit var instance: GlobalApp
        var openTheSwitch: Boolean = false
        var isPad: Boolean = false
    }

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        //全局功能开关
        val applicationInfo = packageManager.getApplicationInfo(packageName, PackageManager.GET_META_DATA)
        val metaData = applicationInfo.metaData
        LogUtils.e(metaData.get("designwidth"))
        metaData.putString("designwidth", "1920")
        LogUtils.e(metaData.get("designwidth"))
        //适配方案
        ScreenAdapterTools.init(this)
        //初始化工具类
        Utils.init(this)
        CrashHandler.create(this)
//        CrashCollectHandler.instance.init(this)

        val catchExcep = UnCeHandler(this)
        Thread.setDefaultUncaughtExceptionHandler(catchExcep)

        if (ObjectUtils.isEmpty(SPUtils.getInstance().getString("ip"))|| ObjectUtils.isEmpty(SPUtils.getInstance().getString("port"))) {

        }else{

//            if (!ServiceUtils.isServiceRunning(MyService::class.java)){
//
//                try {
//                    ServiceUtils.startService(MyService::class.java)
//                }catch (e:Exception){
//                    e.printStackTrace()
//                }
//
//            }

        }

    }


}