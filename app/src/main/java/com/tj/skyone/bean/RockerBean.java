package com.tj.skyone.bean;

import java.io.Serializable;

/**
 * @describe
 * @anthor wdq
 * @time 2021/2/3 14:29
 * @email wudq@infore.com
 */
public class RockerBean implements Serializable {


    /**
     * r_Agroup : 10000000
     * r_Bgroup : 00000000
     * r_up : x
     * r_down : x
     * r_pause : x
     * r_run : x
     */

    private String r_Agroup;
    private String r_Bgroup;
    private String r_up;
    private String r_down;
    private String r_pause;
    private String r_run;
    private String r_key;
    private String r_type;

    public String getR_type() {
        return r_type;
    }

    public void setR_type(String r_type) {
        this.r_type = r_type;
    }

    public String getR_key() {
        return r_key;
    }

    public void setR_key(String r_key) {
        this.r_key = r_key;
    }

    public String getR_Agroup() {
        return r_Agroup;
    }

    public void setR_Agroup(String r_Agroup) {
        this.r_Agroup = r_Agroup;
    }

    public String getR_Bgroup() {
        return r_Bgroup;
    }

    public void setR_Bgroup(String r_Bgroup) {
        this.r_Bgroup = r_Bgroup;
    }

    public String getR_up() {
        return r_up;
    }

    public void setR_up(String r_up) {
        this.r_up = r_up;
    }

    public String getR_down() {
        return r_down;
    }

    public void setR_down(String r_down) {
        this.r_down = r_down;
    }

    public String getR_pause() {
        return r_pause;
    }

    public void setR_pause(String r_pause) {
        this.r_pause = r_pause;
    }

    public String getR_run() {
        return r_run;
    }

    public void setR_run(String r_run) {
        this.r_run = r_run;
    }
}
