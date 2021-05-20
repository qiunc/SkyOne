package com.tj.skyone.bean;

import java.io.Serializable;

public class InofBean implements Serializable {
    private String name;
    private boolean tag1;
    private boolean tag2;
    private boolean tag3;
    private boolean tag4;


    public InofBean(String name,boolean tag1,boolean tag2,boolean tag3,boolean tag4){
        this.name = name;
        this.tag1 = tag1;
        this.tag2 = tag2;
        this.tag3 = tag3;
        this.tag4 = tag4;

    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isTag1() {
        return tag1;
    }

    public void setTag1(boolean tag1) {
        this.tag1 = tag1;
    }

    public boolean isTag2() {
        return tag2;
    }

    public void setTag2(boolean tag2) {
        this.tag2 = tag2;
    }

    public boolean isTag3() {
        return tag3;
    }

    public void setTag3(boolean tag3) {
        this.tag3 = tag3;
    }

    public boolean isTag4() {
        return tag4;
    }

    public void setTag4(boolean tag4) {
        this.tag4 = tag4;
    }
}
