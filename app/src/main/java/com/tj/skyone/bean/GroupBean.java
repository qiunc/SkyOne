package com.tj.skyone.bean;

import java.io.Serializable;

public class GroupBean implements Serializable {
    private String name;
    private boolean tag;
    private boolean select;

    public boolean isSelect() {
        return select;
    }

    public void setSelect(boolean select) {
        this.select = select;
    }

    public GroupBean(){

    }
    public GroupBean(String name){
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isTag() {
        return tag;
    }

    public void setTag(boolean tag) {
        this.tag = tag;
    }
}
