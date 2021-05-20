package com.tj.skyone.utils.eventbus;

/**
 * @author wdq
 * @describe Event的对象封装
 * @time 2018/8/21 15:57
 * @email wudq@infore.com
 */


public class AnyEventTypes {
    private Object anyData;
    private String eventCode;

    public AnyEventTypes(Object anyData) {
        this.anyData = anyData;
    }

    public AnyEventTypes(String code, Object anyData) {
        this.anyData = anyData;
        this.eventCode = code;
    }



    public Object getAnyData() {
        return anyData;
    }

    public void setAnyData(Object anyData) {
        this.anyData = anyData;
    }

    public String getEventCode() {
        return eventCode;
    }

    public void setEventCode(String eventCode) {
        this.eventCode = eventCode;
    }
}
