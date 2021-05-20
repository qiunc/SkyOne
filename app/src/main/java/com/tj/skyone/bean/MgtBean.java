package com.tj.skyone.bean;

import com.tj.skyone.widget.dialog.MgtDialog;

import java.io.Serializable;

public class MgtBean implements Serializable {
    private String id;
    private String name;
    private String type;

    public MgtBean(String id,String name,String type){

        this.id = id;
        this.name = name;
        this.type = type;

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
