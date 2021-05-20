package com.tj.skyone.bean;

import java.io.Serializable;

/**
 * @describe
 * @anthor wdq
 * @time 2021/2/22 16:18
 * @email wudq@infore.com
 */
public class LightingBean implements Serializable {

    private String l_Agroup;
    private String l_Bgroup;
    private String l_key;
    private String l_type;


    public String getL_Agroup() {
        return l_Agroup;
    }

    public void setL_Agroup(String l_Agroup) {
        this.l_Agroup = l_Agroup;
    }

    public String getL_Bgroup() {
        return l_Bgroup;
    }

    public void setL_Bgroup(String l_Bgroup) {
        this.l_Bgroup = l_Bgroup;
    }

    public String getL_key() {
        return l_key;
    }

    public void setL_key(String l_key) {
        this.l_key = l_key;
    }

    public String getL_type() {
        return l_type;
    }

    public void setL_type(String l_type) {
        this.l_type = l_type;
    }
}
