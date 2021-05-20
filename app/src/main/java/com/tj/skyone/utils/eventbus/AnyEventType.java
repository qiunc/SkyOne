package com.tj.skyone.utils.eventbus;

/**
 * @author wdq
 * @describe Event的对象封装
 * @time 2018/8/21 15:57
 * @email wudq@infore.com
 */


public class AnyEventType {
    private Object anyData;
    private int eventCode;

    public AnyEventType(Object anyData) {
        this.anyData = anyData;
    }

    public AnyEventType(int code, Object anyData) {
        this.anyData = anyData;
        this.eventCode = code;
    }



    public Object getAnyData() {
        return anyData;
    }

    public void setAnyData(Object anyData) {
        this.anyData = anyData;
    }

    public int getEventCode() {
        return eventCode;
    }

    public void setEventCode(int eventCode) {
        this.eventCode = eventCode;
    }
}
