package com.tj.skyone.bean;

import java.io.Serializable;

/**
 * @describe
 * @anthor wdq
 * @time 2021/3/4 09:38
 * @email wudq@infore.com
 */
public class UserBean implements Serializable {

    private String user;
    private String pwd;

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }
}
