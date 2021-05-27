package com.tj.skyone.ui.welcome.view

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.blankj.utilcode.util.ObjectUtils
import com.blankj.utilcode.util.SPUtils
import com.blankj.utilcode.util.ServiceUtils
import com.tj.skyone.R
import com.tj.skyone.service.MyService
import com.tj.skyone.ui.home.view.ConfigActivity
import com.tj.skyone.ui.login.LoginActivity

class WelcomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_welcome)

        SPUtils.getInstance().put("ip", "192.168.4.1")
        SPUtils.getInstance().put("port", "333")


        if (ObjectUtils.isEmpty(SPUtils.getInstance().getString("ip"))||ObjectUtils.isEmpty(SPUtils.getInstance().getString("port"))) {

//            val intent = Intent(this, LoginActivity::class.java)
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

                val intent = Intent(this, LoginActivity::class.java)
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
