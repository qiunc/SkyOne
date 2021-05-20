package com.tj.skyone.utils;

import com.blankj.utilcode.util.SPUtils;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @describe
 * @anthor wdq
 * @time 2021/2/2 11:57
 * @email wudq@infore.com
 */
public class HttpParam {

    private Map<String, Object> map;

    public HttpParam(){
        map = new LinkedHashMap<>();
        map.put("user", SPUtils.getInstance().getString("user"));
//        map.put("dataLen","end");

    }

    public Map<String, Object> getMap() {
        return map;
    }
}
