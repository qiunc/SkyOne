package com.tj.skyone.utils;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.tj.skyone.utils.eventbus.EventBusUtils;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.InetAddress;
import java.net.Socket;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author Administrator
 * @describe
 */
public class TcpClient {

    private static TcpClient mSocketClient = null;
    /**
     * 私有化构造器与反射安全
     */
    private TcpClient() {
        if (mSocketClient != null){
            throw new RuntimeException("Use getInstance() method to get the single instance of this class.");
        }
    }


    /**
     * single instance TcpClient
     */
    private static class SingletonContainer{
        private static final TcpClient instance = new TcpClient();
    }

    public static TcpClient getInstance() {
        mSocketClient = SingletonContainer.instance;
        return mSocketClient;
    }



//    public enum mTcpClient {
//        INSTANCE;
//        private final TcpClient instance;
//        mTcpClient() {
//            instance = new TcpClient();
//        }
//        public TcpClient getInstance() {
//            return instance;
//        }
//    }

    /**
     设置线程池参数
     */
    private static final int CORE_POOL_SIZE = 50;
    private static final int MAX_POOL_SIZE = 90;
    private static final int QUEUE_CAPACITY = 100;
    private static final Long KEEP_ALIVE_TIME = 1L;

    private Socket mSocket;

    private OutputStream mOutputStream;
    private InputStream mInputStream;

    private boolean isStop = false;

    private final ThreadPoolExecutor poolExecutor = new ThreadPoolExecutor(
            CORE_POOL_SIZE,
            MAX_POOL_SIZE,
            KEEP_ALIVE_TIME,
            TimeUnit.SECONDS,
            new LinkedBlockingDeque<>(QUEUE_CAPACITY),
            new ThreadPoolExecutor.CallerRunsPolicy());

    /**
     * 128 - 数据按照最长接收，一次性
     */
    private class SocketThread implements Runnable {

        private final String ip;
        private final int port;

        public SocketThread(String ip, int port) {
            this.ip = ip;
            this.port = port;
        }

        @Override
        public void run() {
            //connect ...
            try {
                if (mSocket != null) {
                    mSocket.close();
                    mSocket = null;
                }
                //建立Socket连接
                InetAddress ipAddress = InetAddress.getByName(ip);
                mSocket = new Socket(ipAddress, port);
                //mSocket.setSoTimeout(10000);

                //设置不延时发送
                //mSocket.setTcpNoDelay(true);
                //设置输入输出缓冲流大小
                //mSocket.setSendBufferSize(8*1024);
                //mSocket.setReceiveBufferSize(8*1024);

                if (isConnect()) {
                    mOutputStream = mSocket.getOutputStream();
                    mInputStream = mSocket.getInputStream();

                    isStop = false;

                    uiHandler.sendEmptyMessage(1);
                }
                /* 此处这样做没什么意义不大，真正的socket未连接还是靠心跳发送，等待服务端回应比较好，一段时间内未回应，则socket未连接成功 */
                else {
                    uiHandler.sendEmptyMessage(-1);
                    return;
                }

            } catch (IOException e) {
                uiHandler.sendEmptyMessage(-1);
                e.printStackTrace();
                return;
            }


            //read ...
            while (isConnect() && !isStop ) {

                int size;
                try {
                    byte[] buffer = new byte[1024];
                    if (mInputStream == null) {
                        return;
                    }
                    size = mInputStream.read(buffer);//null data -1 , zrd serial rule size default 10

                    if (size > 0) {
                        Message msg = new Message();
                        msg.what = 100;
                        Bundle bundle = new Bundle();
                        bundle.putByteArray("data", buffer);
                        bundle.putInt("size", size);
                        bundle.putInt("requestCode", requestCode);
                        msg.setData(bundle);
                        uiHandler.sendMessage(msg);
                    }

                    if (size == -1) {
                        uiHandler.sendEmptyMessage(-1);
                    }
//                    Thread.sleep(100);//log eof
                } catch (IOException e) {
                    uiHandler.sendEmptyMessage(-1);
                    e.printStackTrace();
                    return;
                }
            }
        }
    }


    //==============================socket connect============================

    /**
     * connect socket in thread
     * Exception : android.os.NetworkOnMainThreadException
     */
    public void connect(String ip, int port) {
        poolExecutor.execute(new SocketThread(ip, port));
    }

    /**
     * socket is connect
     */
    public boolean isConnect() {
        boolean flag = false;
        if (mSocket != null) {
            flag = mSocket.isConnected();
        }
        return flag;
    }

    /**
     * socket disconnect
     */
    public void disconnect() {
        isStop = true;
        try {
            if (mOutputStream != null) {
                mOutputStream.close();
            }

            if (mInputStream != null) {
                mInputStream.close();
            }

            if (mSocket != null) {
                mSocket.close();
                mSocket = null;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * send byte[] cmd
     * Exception : android.os.NetworkOnMainThreadException
     */
    public void sendByteCmd(final byte[] mBuffer, int requestCode) {
        this.requestCode = requestCode;
        if (mOutputStream != null) {
            poolExecutor.execute(() -> sendByte(mBuffer));
        }

    }

    /**
     * 设置流输出线程同步
     */
    private synchronized void sendByte(final byte[] mBuffer) {
        try {
            mOutputStream.write(mBuffer);
            mOutputStream.flush();
        } catch (IOException e) {
            TcpClient.getInstance().disconnect();
            e.printStackTrace();
        }
    }


    /**
     * send string cmd to serial
     */
    public void sendStrCmds(String cmd, int requestCode) {
        byte[] mBuffer = cmd.getBytes();
        sendByteCmd(mBuffer, requestCode);
    }


    /**
     * send prt content cmd to serial
     */
    public void sendChsPrtCmds(String content, int requestCode) {
        try {


            if (TcpClient.getInstance().isConnect()) {
                LogUtils.e("content==" + content);
//                ToastUtils.showLong("客户端请求数据"+content);

                byte[] mBuffer = content.getBytes("GB2312");
                sendByteCmd(mBuffer, requestCode);

            } else {

                ToastUtils.showLong("未连接的状态下不允许操作功能，请检查您的网络！");

                new Handler().postDelayed(() ->
                        EventBusUtils.post("", ""), 800);

            }


        } catch (UnsupportedEncodingException e1) {
            e1.printStackTrace();
        }
    }


    /**
     * 异步回调处理
     */
    Handler uiHandler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(@NotNull Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                //connect error
                case -1:
                    if (null != onDataReceiveListener) {
                        onDataReceiveListener.onConnectFail();
                        disconnect();
                    }
                    break;

                //connect success
                case 1:
                    if (null != onDataReceiveListener) {
                        onDataReceiveListener.onConnectSuccess();
                    }
                    break;

                //receive data
                case 100:
                    Bundle bundle = msg.getData();
                    byte[] buffer = bundle.getByteArray("data");
                    int size = bundle.getInt("size");
                    int mequestCode = bundle.getInt("requestCode");
                    if (null != onDataReceiveListener) {
                        onDataReceiveListener.onDataReceive(buffer, size, mequestCode);
                    }
                    break;
                default:
                    throw new IllegalStateException("Unexpected value: " + msg.what);
            }
        }
    };


    /**
     * socket response data listener
     */
    private OnDataReceiveListener onDataReceiveListener = null;
    private int requestCode = -1;

    public interface OnDataReceiveListener {

        /**
         * 接收成功
         */
        void onConnectSuccess();

        /**
         * 接收失败
         */
        void onConnectFail();

        /**
         * 接收数据的具体逻辑
         * @param buffer 字节数组
         * @param size 长度
         * @param requestCode 请求码
         */
        void onDataReceive(byte[] buffer, int size, int requestCode);
    }

    public void setOnDataReceiveListener(OnDataReceiveListener dataReceiveListener) {

        onDataReceiveListener = dataReceiveListener;
    }


}
