package com.tj.skyone.utils.eventbus;

import org.greenrobot.eventbus.EventBus;

/**
 * @author wdq
 * @describe Event封装类
 * @time 2018/8/21 16:00
 * @email wudq@infore.com
 */


public class EventBusUtils {
    //注册EventBus
    public static void register(Object object) {
        EventBus.getDefault().register(object);
    }

    //解除注册
    public static void unregister(Object object) {
        EventBus.getDefault().unregister(object);
    }

    //发送消息
    public static void post(int code, Object o) {
        EventBus.getDefault().post(new AnyEventType(code, o));
    }

    public static void post(String code, Object o) {
        EventBus.getDefault().post(new AnyEventTypes(code, o));
    }

    public static <T> void postSticky(T object) {
        EventBus.getDefault().postSticky(object);
    }
}
