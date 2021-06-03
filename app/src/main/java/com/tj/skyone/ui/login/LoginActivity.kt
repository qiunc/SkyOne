package com.tj.skyone.ui.login

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.widget.addTextChangedListener
import com.blankj.utilcode.util.SPUtils
import com.blankj.utilcode.util.StringUtils
import com.blankj.utilcode.util.ToastUtils
import com.google.gson.Gson
import com.tj.skyone.R
import com.tj.skyone.databinding.ActivityLoginBinding
import com.tj.skyone.ui.home.view.HomeActivity
import com.tj.skyone.utils.TcpClient
import com.tj.skyone.utils.eventbus.AnyEventTypes
import com.tj.skyone.utils.eventbus.EventBusUtils
import com.tj.skyone.widget.dialog.LoadingDialog
import org.greenrobot.eventbus.Subscribe

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private lateinit var dialog: LoadingDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initEventData()
    }

    private fun initEventData() {
        //注册EventBus
        EventBusUtils.register(this)
        //初始化加载框
        dialog = LoadingDialog(this)

        //设置密码输入框监听时间
        binding.etPwdOrCode.addTextChangedListener {
            val content = it.toString()
            if (content.isNotEmpty()) {
                binding.btnLogin.background = ContextCompat.getDrawable(this, R.mipmap.loginbs)
            }
        }

        //设置登录按钮监听
        binding.btnLogin.setOnClickListener {
            when {
                StringUtils.isEmpty(binding.etPhone.text) -> {
                    ToastUtils.showLong("请输入用户名")
                }
                StringUtils.isEmpty(binding.etPwdOrCode.text) -> {
                    ToastUtils.showLong("请输入密码")
                }
                else -> {
                    val map = HashMap<Any, Any>()
                    map["methodName"] = "appLogin"
                    map["loginName"] = binding.etPhone.text.toString()
                    map["loginPassword"] = binding.etPwdOrCode.text.toString()
                    TcpClient.getInstance().sendChsPrtCmds(Gson().toJson(map), 1001)
                    dialog.show()
                }
            }
        }
    }

    @Subscribe
    fun onEvent(event: AnyEventTypes) {
        dialog.dismiss()
        if (StringUtils.equals("appLogin", event.eventCode)) {
            SPUtils.getInstance().put("user", binding.etPhone.text.toString())
            val intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}


