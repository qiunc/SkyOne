package com.tj.skyone.ui

import android.app.Application
import android.content.Context
import com.blankj.utilcode.util.ObjectUtils
import com.blankj.utilcode.util.SPUtils
import com.blankj.utilcode.util.Utils
import com.tj.skyone.utils.CrashHandler
import com.tj.skyone.utils.UnCeHandler
import me.jessyan.autosize.AutoSize
import me.jessyan.autosize.AutoSizeConfig


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

        //当 App 中出现多进程, 并且您需要适配所有的进程,适配方案
        AutoSize.initCompatMultiProcess(this)
        AutoSizeConfig.getInstance().isCustomFragment = true  //让框架支持自定义 Fragment 的适配参数

        //初始化工具类
        Utils.init(this)
        CrashHandler.create(this)
        //CrashCollectHandler.instance.init(this)

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