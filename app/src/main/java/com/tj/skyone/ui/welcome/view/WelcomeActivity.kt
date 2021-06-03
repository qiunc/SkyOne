package com.tj.skyone.ui.welcome.view

import android.content.Intent
import android.content.pm.ActivityInfo
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.blankj.utilcode.util.LogUtils
import com.blankj.utilcode.util.ObjectUtils
import com.blankj.utilcode.util.SPUtils
import com.blankj.utilcode.util.ServiceUtils
import com.tj.skyone.R
import com.tj.skyone.service.MyService
import com.tj.skyone.ui.GlobalApp
import com.tj.skyone.ui.home.view.ConfigActivity
import com.tj.skyone.ui.home.view.HomeActivity
import com.tj.skyone.ui.login.LoginActivity

class WelcomeActivity : AppCompatActivity() {
    private var isTwoPane = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_welcome)

        SPUtils.getInstance().put("ip", "192.168.4.1")
        SPUtils.getInstance().put("port", "333")

         isTwoPane = findViewById<View>(R.id.padFlag) != null
        if (isTwoPane) {
            GlobalApp.isPad = true
            val applicationInfo = packageManager.getApplicationInfo(packageName, PackageManager.GET_META_DATA)
            val metaData = applicationInfo.metaData
            LogUtils.e(metaData.get("designwidth"))
            metaData.putString("designwidth", "1920")
            LogUtils.e(metaData.get("designwidth"))
            requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE

        }
        if (ObjectUtils.isEmpty(SPUtils.getInstance().getString("ip"))||ObjectUtils.isEmpty(SPUtils.getInstance().getString("port"))) {
            val intent = Intent(this, ConfigActivity::class.java)
            startActivity(intent)
        }else{
            try {
                if (!ServiceUtils.isServiceRunning(MyService::class.java))
                    ServiceUtils.startService(MyService::class.java)
            } catch (e: Exception) {
                e.printStackTrace()
            }

//            if (ObjectUtils.isEmpty(SPUtils.getInstance().getString("user",""))){

                val intent = Intent(this, HomeActivity::class.java)
                startActivity(intent)

//            }else{
//
//                val intent = Intent(this, HomeActivity::class.java)
//                startActivity(intent)
//
//
//            }


        }

        finish()

    }
}
