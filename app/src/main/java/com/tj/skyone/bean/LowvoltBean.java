package com.tj.skyone.bean;

import java.io.Serializable;

/**
 * @describe
 * @anthor wdq
 * @time 2021/2/26 08:54
 * @email wudq@infore.com
 */
public class LowvoltBean implements Serializable {

    private String lv_value;
    private String lv_DCAC;
    private String lv_akey;
    private String lv_bkey;
    private String lv_tkey;
    private String lv_lock;
    private String lv_sure;

    public String getLv_value() {
        return lv_value;
    }

    public void setLv_value(String lv_value) {
        this.lv_value = lv_value;
    }

    public String getLv_DCAC() {
        return lv_DCAC;
    }

    public void setLv_DCAC(String lv_DCAC) {
        this.lv_DCAC = lv_DCAC;
    }

    public String getLv_akey() {
        return lv_akey;
    }

    public void setLv_akey(String lv_akey) {
        this.lv_akey = lv_akey;
    }

    public String getLv_bkey() {
        return lv_bkey;
    }

    public void setLv_bkey(String lv_bkey) {
        this.lv_bkey = lv_bkey;
    }

    public String getLv_tkey() {
        return lv_tkey;
    }

    public void setLv_tkey(String lv_tkey) {
        this.lv_tkey = lv_tkey;
    }

    public String getLv_lock() {
        return lv_lock;
    }

    public void setLv_lock(String lv_lock) {
        this.lv_lock = lv_lock;
    }

    public String getLv_sure() {
        return lv_sure;
    }

    public void setLv_sure(String lv_sure) {
        this.lv_sure = lv_sure;
    }
}
