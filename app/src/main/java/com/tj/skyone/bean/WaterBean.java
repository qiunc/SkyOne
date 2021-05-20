package com.tj.skyone.bean;

import java.io.Serializable;

/**
 * @describe
 * @anthor wdq
 * @time 2021/2/22 14:31
 * @email wudq@infore.com
 */
public class WaterBean implements Serializable {

    private String w_Agroup;
    private String w_Bgroup;
    private String w_key;
    private String w_drain;
    private String  w_type;

    public String getW_type() {
        return w_type;
    }

    public void setW_type(String w_type) {
        this.w_type = w_type;
    }

    public String getW_Agroup() {
        return w_Agroup;
    }

    public void setW_Agroup(String w_Agroup) {
        this.w_Agroup = w_Agroup;
    }

    public String getW_Bgroup() {
        return w_Bgroup;
    }

    public void setW_Bgroup(String w_Bgroup) {
        this.w_Bgroup = w_Bgroup;
    }

    public String getW_key() {
        return w_key;
    }

    public void setW_key(String w_key) {
        this.w_key = w_key;
    }

    public String getW_drain() {
        return w_drain;
    }

    public void setW_drain(String w_drain) {
        this.w_drain = w_drain;
    }
}
