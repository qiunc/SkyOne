package com.tj.skyone.bean;

import java.io.Serializable;

/**
 * @describe 主页信息类
 * @anthor wdq
 * @time 2021/2/2 11:45
 * @email wudq@infore.com
 */
public class HomeBean implements Serializable {



    private String temperature;
    private String humidity;
    private String runstatus;

    public String getRunstatus() {
        return runstatus;
    }

    public void setRunstatus(String runstatus) {
        this.runstatus = runstatus;
    }

    public String getTemperature() {
        return temperature;
    }

    public void setTemperature(String temperature) {
        this.temperature = temperature;
    }

    public String getHumidity() {
        return humidity;
    }

    public void setHumidity(String humidity) {
        this.humidity = humidity;
    }
}
