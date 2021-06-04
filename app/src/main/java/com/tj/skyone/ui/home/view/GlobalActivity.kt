package com.tj.skyone.ui.home.view

import android.content.Intent
import android.content.res.Configuration
import android.content.res.Resources
import android.os.Bundle
import android.os.Handler
import android.text.method.ScrollingMovementMethod
import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.blankj.utilcode.util.SPUtils
import com.blankj.utilcode.util.ToastUtils
import com.tj.skyone.R
import com.tj.skyone.utils.TcpClient
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.functions.Consumer
import kotlinx.android.synthetic.main.activity_global2.*
import java.nio.charset.Charset
import java.util.concurrent.TimeUnit

class GlobalActivity : AppCompatActivity(){


    private var heartbeat: Disposable? = null
    private var heartbeatMain: Disposable? = null




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_global2)



        //防止Home按键app重启
        if (!isTaskRoot) {
            val intent = intent
            val action = intent.action
            if (intent.hasCategory(Intent.CATEGORY_LAUNCHER) && Intent.ACTION_MAIN == action) {
                finish()
                return
            }
        }

//        initDataReceiver()
//        heartbeatMain()//监听是否主动断开


        clear_log.setOnClickListener {

            text_data.text = ""

        }

        text_data.movementMethod = ScrollingMovementMethod.getInstance()



        btn.setOnClickListener {

            val intent = Intent(this, ConfigActivity::class.java)
            startActivity(intent)

        }

        btns.setOnClickListener {

            TcpClient.getInstance().sendChsPrtCmds("{\"user\":\"18516091812\",\"pwd\":\"123456\"}", 1001)
        }


    }


    //添加日志
    private fun addText(textView: TextView, content: String) {
        textView.append(content)
        textView.append("\n")
        val offset: Int = textView.getLineCount() * textView.getLineHeight()
        if (offset > textView.getHeight()) {
            textView.scrollTo(0, offset - textView.getHeight())
        }
    }


    fun  initData(values:String){

        //获取有效长度的数据

//            val values ="2AFFFF632C0000610A03170F32170064006400630063013A013A0266026602EF02EF090101010101010101010401010101160A"
        Log.e("values", values)
        Log.e("values", values.length.toString())

        try {

//            text_data.text = "接收数据：$values"

            Log.e("========",values)

            addText(text_data,"接收数据：$values")

        }catch (e:Exception){

            e.printStackTrace()
            ToastUtils.showLong("数据异常："+e.toString()+":"+e.message)

        }

    }




    override fun onPause() {
        super.onPause()
    }



    private fun connect() {

        if (!TcpClient.getInstance().isConnect) {

            TcpClient.getInstance().connect(
                SPUtils.getInstance().getString("ip"),
                SPUtils.getInstance().getString("port").toInt()
            )

        }

    }

    private fun initDataReceiver() {

        TcpClient.getInstance().setOnDataReceiveListener(dataReceiveListener)
        connect()//连接

    }


    private val dataReceiveListener: TcpClient.OnDataReceiveListener = object :
        TcpClient.OnDataReceiveListener {

        override fun onConnectSuccess() {

            ToastUtils.showLong("已连接")
            rxHeartbeat();

        }

        override fun onConnectFail() {

            ToastUtils.showLong("未连接")
            rxrDispose()

            Handler().postDelayed({

                initDataReceiver()

            }, 5000)

        }

        override fun onDataReceive(buffer: ByteArray?, size: Int, requestCode: Int) {

            try {

                //获取有效长度的数据
                val data = ByteArray(size)
                System.arraycopy(buffer!!, 0, data, 0, size)

//                val values = HexUtil.byte2hex(data)
                val charset = Charset.forName("GB2312")
                val values = String(data, charset)
                initData(values);

            }catch (e:Exception){
                e.printStackTrace()
            }

        }
    }

    override fun onDestroy() {
        TcpClient.getInstance().disconnect()
        rxrDispose()
        rxrDisposeMain()
        super.onDestroy()
    }

    fun rxrDispose() {

        if (heartbeat != null && !heartbeat!!.isDisposed()) {
            heartbeat!!.dispose()
            heartbeat = null
        }

    }

    fun rxrDisposeMain() {

        if (heartbeatMain != null && !heartbeatMain!!.isDisposed()) {
            heartbeatMain!!.dispose()
        }

    }


    /**
     * socket send
     */
    private fun send(data: ByteArray) {
        TcpClient.getInstance().sendChsPrtCmds("心跳0", 1001)
    }

    private fun rxHeartbeat() {

        if (heartbeat == null){

            heartbeat =
                Observable.interval(5, TimeUnit.SECONDS).observeOn(AndroidSchedulers.mainThread())
                    .subscribe(
                        Consumer {

                            if (TcpClient.getInstance().isConnect) {
                                val data = "0".toByteArray()
                                send(data)
//                                text_data.text = "发送数据：0"
                                addText(text_data,"发送数据：0")

                            } else {

                                TcpClient.getInstance().disconnect()
                                ToastUtils.showLong("尚未连接，请连接Socket")

                            }

                        })

        }

    }

    private fun heartbeatMain() {

        heartbeatMain =
            Observable.interval(30, TimeUnit.SECONDS).observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    Consumer {

                        if (!TcpClient.getInstance().isConnect) {

                            rxrDispose()
                            initDataReceiver()

                        }

                    })

    }

    override fun getResources(): Resources? {
        val res = super.getResources()
        val config = Configuration()
        config.setToDefaults()
        res.updateConfiguration(config, res.displayMetrics)
        return res
    }
}