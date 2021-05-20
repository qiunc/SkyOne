package com.tj.skyone.bean;

import java.io.Serializable;

/**
 * @describe
 * @anthor wdq
 * @time 2021/2/22 16:07
 * @email wudq@infore.com
 */
public class HighvoltBean implements Serializable {

    private String h_Agroup;
    private String h_Bgroup;
    private String h_key;
    private String h_type;

    public String getH_Agroup() {
        return h_Agroup;
    }

    public void setH_Agroup(String h_Agroup) {
        this.h_Agroup = h_Agroup;
    }

    public String getH_Bgroup() {
        return h_Bgroup;
    }

    public void setH_Bgroup(String h_Bgroup) {
        this.h_Bgroup = h_Bgroup;
    }

    public String getH_key() {
        return h_key;
    }

    public void setH_key(String h_key) {
        this.h_key = h_key;
    }

    public String getH_type() {
        return h_type;
    }

    public void setH_type(String h_type) {
        this.h_type = h_type;
    }
}
