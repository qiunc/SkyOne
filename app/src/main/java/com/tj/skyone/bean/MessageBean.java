package com.tj.skyone.bean;

import java.io.Serializable;

/**
 * @describe
 * @anthor wdq
 * @time 2021/3/3 19:28
 * @email wudq@infore.com
 */
public class MessageBean implements Serializable {

    private String reset_fg;
    private String reset_dy;
    private String reset_yb;
    private String reset_ps;
    private String fg_count;
    private String dy_count;
    private String yb_count;
    private String ps_count;
    private String config;
    private String count;

    public String getReset_fg() {
        return reset_fg;
    }

    public void setReset_fg(String reset_fg) {
        this.reset_fg = reset_fg;
    }

    public String getReset_dy() {
        return reset_dy;
    }

    public void setReset_dy(String reset_dy) {
        this.reset_dy = reset_dy;
    }

    public String getReset_yb() {
        return reset_yb;
    }

    public void setReset_yb(String reset_yb) {
        this.reset_yb = reset_yb;
    }

    public String getReset_ps() {
        return reset_ps;
    }

    public void setReset_ps(String reset_ps) {
        this.reset_ps = reset_ps;
    }

    public String getFg_count() {
        return fg_count;
    }

    public void setFg_count(String fg_count) {
        this.fg_count = fg_count;
    }

    public String getDy_count() {
        return dy_count;
    }

    public void setDy_count(String dy_count) {
        this.dy_count = dy_count;
    }

    public String getYb_count() {
        return yb_count;
    }

    public void setYb_count(String yb_count) {
        this.yb_count = yb_count;
    }

    public String getPs_count() {
        return ps_count;
    }

    public void setPs_count(String ps_count) {
        this.ps_count = ps_count;
    }

    public String getConfig() {
        return config;
    }

    public void setConfig(String config) {
        this.config = config;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }
}
