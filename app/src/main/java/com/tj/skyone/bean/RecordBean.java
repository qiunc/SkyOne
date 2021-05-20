package com.tj.skyone.bean;

import java.io.Serializable;

public class RecordBean implements Serializable {
    private String id;
    private String name;
    private String content;
    private String time;

    public RecordBean(String id, String name, String content,String time){

        this.id = id;
        this.name = name;
        this.content = content;
        this.time = time;

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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
