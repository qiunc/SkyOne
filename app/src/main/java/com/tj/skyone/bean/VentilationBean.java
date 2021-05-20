package com.tj.skyone.bean;

import java.io.Serializable;

/**
 * @describe
 * @anthor wdq
 * @time 2021/2/26 08:35
 * @email wudq@infore.com
 */
public class VentilationBean implements Serializable {

    private String v_fengsu;
    private String v_key;

    public String getV_fengsu() {
        return v_fengsu;
    }

    public void setV_fengsu(String v_fengsu) {
        this.v_fengsu = v_fengsu;
    }

    public String getV_key() {
        return v_key;
    }

    public void setV_key(String v_key) {
        this.v_key = v_key;
    }
}
