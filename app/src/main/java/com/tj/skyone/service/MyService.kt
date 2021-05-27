package com.tj.skyone.service

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Build
import android.os.IBinder
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import com.blankj.utilcode.util.*
import com.google.gson.Gson
import com.tj.skyone.R
import com.tj.skyone.bean.Response
import com.tj.skyone.ui.welcome.view.WelcomeActivity
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

//    //伴生对象
//    companion object {
//        @RequiresApi(Build.VERSION_CODES.O)
//        fun strtService(context: Context) {
//            LogUtils.e("==strtService===")
//            try {
//                val startIntent = Intent(context, MyService::class.java)
//                context.startForegroundService(startIntent)
//            } catch (e: IOException) {
//                LogUtils.e("==FService===" + e.message)
//                e.printStackTrace()
//            }
//        }
//
//        fun stpService(context: Context) {
//            val stopIntent = Intent(context, MyService::class.java)
//            context.stopService(stopIntent)
//        }
//    }

    /**
     * 重写方法，服务创建时调用
     */
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

    /**
     * 服务断开释放资源
     */
    override fun onDestroy() {
        super.onDestroy()

        LogUtils.e("===Service=====onDestroy===")
        TcpClient.getInstance().disconnect()
        stopForeground(true);
        rxrDispose()
        rxrDisposeMain()

//        try {
//            if (!ServiceUtils.isServiceRunning(MyService::class.java))
//                ServiceUtils.startService(MyService::class.java)
//
//        } catch (e: Exception) {
//            e.printStackTrace()
//
//        }
    }

    override fun onBind(intent: Intent): IBinder? {
        return null
    }

    // Start ForegroundService
    private fun startForeService() {
//      LogUtils.e("==startForeService===")
        createNotification()
//        startFS()
    }

    private fun createNotificationChannel(): NotificationManager {
        //获取NotificationManager对象
        val manager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            //构建通知渠道
            val channel = NotificationChannel("socketService", "服务端连接",
                NotificationManager.IMPORTANCE_DEFAULT)
            manager.createNotificationChannel(channel)
        }
        return manager
    }

    private fun createNotification() {
        val manager = createNotificationChannel()
        val intent = Intent(this, WelcomeActivity::class.java)
        val pi = PendingIntent.getActivity(this, 0, intent, 0)
        val notification = NotificationCompat.Builder(this,
            "socketService")
            .setContentTitle("服务器连接")
            .setContentText("启动连接")
            .setSmallIcon(R.mipmap.ic_launcher)
            .setLargeIcon(BitmapFactory.decodeResource(resources, R.mipmap.login_bg))
            .setContentIntent(pi)
            .build()
        startForeground(0, notification)
    }

    /**
     * TcpClient连接请求封装
     */
    private fun connect() {

        if (!TcpClient.getInstance().isConnect) {

            TcpClient.getInstance().connect(
                SPUtils.getInstance().getString("ip"),
                SPUtils.getInstance().getString("port").toInt()
            )

        }

    }

    /**
     * 主线程开启心跳包订阅
     */
    private fun heartbeatMain() {

        heartbeatMain =
            Observable.interval(10, TimeUnit.SECONDS).observeOn(AndroidSchedulers.mainThread())
                .subscribe {

                    if (!TcpClient.getInstance().isConnect) {

//                            rxrDispose()
                        initDataReceiver()

                    }

                }
    }

    /**
     * socket心跳检测
     * */
    private fun rxHeartbeat() {

        if (heartbeat == null) {
            heartbeat =
                Observable.interval(5, TimeUnit.SECONDS).observeOn(AndroidSchedulers.mainThread())
                    .subscribe {
                        if (TcpClient.getInstance().isConnect) {
                            val data = "0".toByteArray()
                            send(data)

                        } else {
                            //连接完全释放后重新连接
                            TcpClient.getInstance().disconnect()
                            connect()
                            ToastUtils.showLong("尚未连接，请连接Socket")
                        }
                    }

        }

    }

    /**
     * TcpClient发送封装,发送心跳检测
     */
    private fun send(data: ByteArray) {
        TcpClient.getInstance().sendByteCmd(data, 1001)
    }

    /**
     * 数据接收监听器实现对象表达式
     */
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
            //创建大小为SIZE的全0字节数组
            val data = ByteArray(size)
            //数组复制
            System.arraycopy(buffer!!, 0, data, 0, size)
            //获得字符集对象
            val charset = Charset.forName("GB2312")
            //字符数组转字符串
            val values = String(data, charset)

//            ToastUtils.showLong("服务端返回数据$values")

            if (values.isNotEmpty()) {
                //存储服务端返回数据用于异常抛出
                val tt = values;
                var str = ","

                try {
                    //正则表达式分离数组中的*
                    val mList = CheckDatas.check(values)

                    for (index in mList.indices) {
                        val vals: String = mList[index]
                        //Json转对象
                        val bean = GsonUtils.fromJson(vals, Response::class.java)
                        //
                        str = bean.codedes.toString() + "," + bean.codes.toString() +
                                "," + "," + bean.methodName.toString() +
                                "," + bean.datas.toString()
                        if (StringUtils.equals("0", bean.codes)) {

                            LogUtils.e(bean.methodName)
                            LogUtils.e(bean.datas.toString())
                            //发布事件到Event
                            EventBusUtils.post(bean.methodName, bean.datas)
                            //接收成功后封装返回数据
                            val httpParam = HttpParam()
                            httpParam.map["methodName"] = bean.methodName
                            httpParam.map["codes"] = "0"
                            httpParam.map["codedes"] = "success"

                            //给服务器发送接收成功标识
                            TcpClient.getInstance()
                                .sendChsPrtCmds(Gson().toJson(httpParam.map), 1001)
                        } else {

                            ToastUtils.showLong(bean.codedes)
                            EventBusUtils.post("", "")

                        }
                    }
                    LogUtils.e(values)

                } catch (e: Exception) {
                    EventBusUtils.post("", "")
                    ToastUtils.showLong("数据异常！" + tt + "," + str + e.message + "")
                    e.printStackTrace()
                }
            }

        }
    }

    /**
     * 初始化心跳数据接收
     */
    private fun initDataReceiver() {

        TcpClient.getInstance().setOnDataReceiveListener(dataReceiveListener)
        connect()//连接

    }

    /**
     * 释放Socket心跳检测资源
     */
    private fun rxrDispose() {

        if (heartbeat != null && !heartbeat!!.isDisposed) {
            heartbeat!!.dispose()

        }
    }

    /**
     * 释放主线程心跳包资源
     */
    private fun rxrDisposeMain() {

        if (heartbeatMain != null && !heartbeatMain!!.isDisposed) {
            heartbeatMain!!.dispose()
        }

    }
}