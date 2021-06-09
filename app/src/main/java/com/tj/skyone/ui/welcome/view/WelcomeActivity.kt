package com.tj.skyone.ui.welcome.view

import android.content.Intent
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.blankj.utilcode.util.LogUtils
import com.blankj.utilcode.util.ObjectUtils
import com.blankj.utilcode.util.SPUtils
import com.blankj.utilcode.util.ServiceUtils
import com.tj.skyone.R
import com.tj.skyone.service.MyService
import com.tj.skyone.ui.GlobalApp
import com.tj.skyone.ui.home.view.ConfigActivity
import com.tj.skyone.ui.login.LoginActivity


class WelcomeActivity : AppCompatActivity(){
    private var isTwoPane = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_welcome)
        SPUtils.getInstance().put("ip", "192.168.4.1")
        SPUtils.getInstance().put("port", "333")

         if (findViewById<View>(R.id.padFlag) != null) {
             isTwoPane = true
         }
        if (isTwoPane) {
            LogUtils.e(isTwoPane)
            GlobalApp.isPad = true
//            val applicationInfo = packageManager.getApplicationInfo(packageName, PackageManager.GET_META_DATA)
//            val metaData = applicationInfo.metaData
            //更改平板宽度基数
            getDisplay()
            //屏幕适配初始化

            //锁横屏
            //requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        } else {
            GlobalApp.isPad = false

            //更改平板宽度基数

            //屏幕适配初始化
//            ScreenAdapterTools.init(GlobalApp.instance)
//            ScreenAdapterTools.getInstance().loadView(window.decorView)
            getDisplay()
            //锁横屏
            //requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        }
        if (ObjectUtils.isEmpty(SPUtils.getInstance().getString("ip"))||ObjectUtils.isEmpty(SPUtils.getInstance().getString("port"))) {
            val intent = Intent(this, ConfigActivity::class.java)
            startActivity(intent)
        }else{
                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
        }

        finish()

    }

    private fun getDisplay() {
        val metric = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(metric)

        val width = metric.widthPixels // 宽度（PX）

        val height = metric.heightPixels // 高度（PX）


        val density = metric.density // 密度（0.75 / 1.0 / 1.5）

        val densityDpi = metric.densityDpi // 密度DPI（120 / 160 / 240）


        //屏幕宽度算法:屏幕宽度（像素）/屏幕密度

        //屏幕宽度算法:屏幕宽度（像素）/屏幕密度
        val screenWidth = (width / density).toInt() //屏幕宽度(dp)

        val screenHeight = (height / density).toInt() //屏幕高度(dp)

        LogUtils.e("""宽度:$width 高度:$height 密度:$density 密度DPI:$densityDpi
屏幕dp宽度：$screenWidth 屏幕dp高度：$screenHeight""")
//        Toast.makeText(
//            this, """宽度:$width 高度:$height 密度:$density 密度DPI:$densityDpi
//屏幕dp宽度：$screenWidth 屏幕dp高度：$screenHeight""", Toast.LENGTH_LONG
//        ).show()
    }

}
