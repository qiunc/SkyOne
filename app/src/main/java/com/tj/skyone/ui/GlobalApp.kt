package com.tj.skyone.ui

import android.app.Activity
import android.app.Application
import android.content.Context
import com.blankj.utilcode.util.Utils
import com.tj.skyone.utils.CrashHandler
import com.tj.skyone.utils.UnCeHandler
import me.jessyan.autosize.AutoSize
import me.jessyan.autosize.AutoSizeConfig
import me.jessyan.autosize.onAdaptListener
import me.jessyan.autosize.unit.Subunits
import me.jessyan.autosize.utils.AutoSizeLog
import me.jessyan.autosize.utils.ScreenUtils
import java.lang.String
import java.util.*


/**
 * @describe
 * @anthor wdq
 * @time 2020/6/8 09:20
 * @email wudq@infore.com
 */
class GlobalApp : Application() {


    companion object {
        lateinit var instance: GlobalApp
        //设备开关全局变量标准
        var openTheSwitch: Boolean = false
        //是否是平板设备标志
        var isPad: Boolean = false
    }

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        //全局功能开关

        //初始化工具类
        Utils.init(this)
        CrashHandler.create(this)
        //CrashCollectHandler.instance.init(this)

        val catchExcep = UnCeHandler(this)
        Thread.setDefaultUncaughtExceptionHandler(catchExcep)

        //当 App 中出现多进程, 并且您需要适配所有的进程,适配方案
        AutoSize.initCompatMultiProcess(this)
        AutoSize.checkAndInit(this)
        AutoSizeConfig.getInstance()
            .setCustomFragment(true)
            .setExcludeFontScale(true)
            .setPrivateFontScale(0.8f)
            .setLog(false)
            .setBaseOnWidth(true)
            .setUseDeviceSize(true).onAdaptListener = object : onAdaptListener{
            override fun onAdaptBefore(target: Any?, activity: Activity?) {
                AutoSizeConfig.getInstance().screenWidth = ScreenUtils.getScreenSize(activity)[0];
                AutoSizeConfig.getInstance().screenHeight = ScreenUtils.getScreenSize(activity)[1];
                AutoSizeLog.d(
                    String.format(
                        Locale.ENGLISH,
                        "%s onAdaptBefore!",
                        target?.javaClass?.name
                    )
                )
            }

            override fun onAdaptAfter(target: Any?, activity: Activity?) {
                AutoSizeLog.d(
                    String.format(
                        Locale.ENGLISH,
                        "%s onAdaptAfter!",
                        target?.javaClass?.name
                    )
                )
            }

        }
        configUnits()
    }
    private fun configUnits() {
        AutoSizeConfig.getInstance()
            .unitsManager
            .setSupportDP(true)
            .setDesignSize(2560f, 1800f)
            .setSupportSP(true)
            .supportSubunits = Subunits.MM
    }


}