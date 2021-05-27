package com.tj.skyone.bean;

import java.io.Serializable;

/**
 * @describe 服务端返回数据类
 * codes : 0
 * codedes : success
 * datas : [{"loginName":"xxxxxxxxxx","loginPassword":"xxxxxxxxxx"}]
 * methodName : appLogin
 * dataLen : xxxxend
 */
public class Response implements Serializable {
    private static final long serialVersionUID = 1L;
    private String codes;
    private String codedes;
    private String methodName;
    private String dataLen;
    private Object datas;

    public String getCodes() {
        return codes;
    }

    public void setCodes(String codes) {
        this.codes = codes;
    }

    public String getCodedes() {
        return codedes;
    }

    public void setCodedes(String codedes) {
        this.codedes = codedes;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public String getDataLen() {
        return dataLen;
    }

    public void setDataLen(String dataLen) {
        this.dataLen = dataLen;
    }

    public Object getDatas() {
        return datas;
    }

    public void setDatas(Object datas) {
        this.datas = datas;
    }
}
