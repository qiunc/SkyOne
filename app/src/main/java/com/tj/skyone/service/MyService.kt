package com.tj.skyone.service

import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.IBinder
import androidx.annotation.RequiresApi
import com.blankj.utilcode.util.*
import com.google.gson.Gson
import com.tj.skyone.bean.Response
import com.tj.skyone.utils.CheckDatas
import com.tj.skyone.utils.HttpParam
import com.tj.skyone.utils.TcpClient
import com.tj.skyone.utils.eventbus.EventBusUtils
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.functions.Consumer
import java.io.IOException
import java.nio.charset.Charset
import java.util.concurrent.TimeUnit


/**
 * @describe
 * @anthor wdq
 * @time 2020/8/6 10:31
 * @email wudq@infore.com
 */
class MyService : Service() {

    private var heartbeat: Disposable? = null
    private var heartbeatMain: Disposable? = null


    companion object {

        @RequiresApi(Build.VERSION_CODES.O)
        fun strtService(context: Context) {
            LogUtils.e("==strtService===")
            try {
                val startIntent = Intent(context, MyService::class.java)
                context.startForegroundService(startIntent)
            }catch (e: IOException) {
                LogUtils.e("==FService===" + e.message)
                e.printStackTrace()
            }
        }
        fun stpService(context: Context){
            val stopIntent = Intent(context, MyService::class.java)
            context.stopService(stopIntent)
        }
    }

    // Start ForegroundService
    private fun startForeService() {
        LogUtils.e("==startForeService===")

//        createNotificationChannel()
//        startFS()
    }


    override fun onCreate() {
        super.onCreate()

        startForeService()
    }


    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {


        TcpClient.getInstance().setOnDataReceiveListener(dataReceiveListener)
        connect()
        heartbeatMain()

        return START_STICKY
    }


    fun rxrDispose() {

        if (heartbeat != null && !heartbeat!!.isDisposed()) {
            heartbeat!!.dispose()

        }

        heartbeat == null

    }

    fun rxrDisposeMain() {

        if (heartbeatMain != null && !heartbeatMain!!.isDisposed()) {
            heartbeatMain!!.dispose()
        }

    }

    private fun heartbeatMain() {

        heartbeatMain =
            Observable.interval(10, TimeUnit.SECONDS).observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    Consumer {

                        if (!TcpClient.getInstance().isConnect) {

//                            rxrDispose()
                            initDataReceiver()

                        }

                    })
    }

    private fun connect() {

        if (!TcpClient.getInstance().isConnect) {

            TcpClient.getInstance().connect(
                SPUtils.getInstance().getString("ip"),
                SPUtils.getInstance().getString("port").toInt()
            )

        }

    }


    private fun rxHeartbeat() {

        if (heartbeat == null){

            heartbeat = Observable.interval(5, TimeUnit.SECONDS).observeOn(AndroidSchedulers.mainThread())
                    .subscribe(
                        Consumer {
                            if (TcpClient.getInstance().isConnect) {

                                val data = "0".toByteArray()
                                send(data)

                            } else {

                                TcpClient.getInstance().disconnect()
                                connect()
                                ToastUtils.showLong("尚未连接，请连接Socket")

                            }

                        })

        }

    }


    private fun send(data: ByteArray) {
        TcpClient.getInstance().sendByteCmd(data, 1001)
    }

    private val dataReceiveListener: TcpClient.OnDataReceiveListener = object :
        TcpClient.OnDataReceiveListener {

        override fun onConnectSuccess() {

            ToastUtils.showLong("已连接")
            rxHeartbeat()

        }

        override fun onConnectFail() {

            ToastUtils.showLong("未连接")
//            rxrDispose()


        }

        override fun onDataReceive(buffer: ByteArray?, size: Int, requestCode: Int) {

            val data = ByteArray(size)
            System.arraycopy(buffer!!, 0, data, 0, size)

            val charset = Charset.forName("GB2312")
            val values = String(data, charset)

//            ToastUtils.showLong("服务端返回数据$values")

            if (values.isNotEmpty()) {


                var tt = values;
                var str = ","

                try {

                    val mList = CheckDatas.check(values)
                    for (index in mList.indices){

                        val vals:String =  mList[index]

                        val bean = GsonUtils.fromJson(vals, Response::class.java)
                        str = bean.codedes.toString()+","+bean.codes.toString()+","+","+bean.methodName.toString()+","+bean.datas.toString()
                        if (StringUtils.equals("0", bean.codes)) {

                            LogUtils.e(bean.methodName)
                            LogUtils.e(bean.datas.toString())

                            EventBusUtils.post(bean.methodName, bean.datas)

                            val httpParam = HttpParam()
                            httpParam.map["methodName"] = bean.methodName
                            httpParam.map["codes"] = "0"
                            httpParam.map["codedes"] = "success"


                            TcpClient.getInstance().sendChsPrtCmds(Gson().toJson(httpParam.map), 1001)

                        } else {

                            ToastUtils.showLong(bean.codedes)
                            EventBusUtils.post("", "")

                        }
                    }

                    LogUtils.e(values)


                } catch (e: Exception) {
                    EventBusUtils.post("", "")

                    ToastUtils.showLong("数据异常！"+tt+","+str.toString()+e.message+"")
                    e.printStackTrace()
                }
            }

        }
    }


    private fun initDataReceiver() {

        TcpClient.getInstance().setOnDataReceiveListener(dataReceiveListener)
        connect()//连接

    }

    override fun onDestroy() {
        super.onDestroy()

        LogUtils.e("===Service=====onDestroy===")

        TcpClient.getInstance().disconnect()
        rxrDispose()
        rxrDisposeMain()

        try {
            if (!ServiceUtils.isServiceRunning(MyService::class.java))
                ServiceUtils.startService(MyService::class.java)

        } catch (e: Exception) {
            e.printStackTrace()

        }



    }

    override fun onBind(intent: Intent): IBinder? {
        return null
    }


}