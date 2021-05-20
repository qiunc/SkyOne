package com.tj.skyone.bean;

import java.io.Serializable;
import java.util.List;

/**
 * @describe
 * @anthor wdq
 * @time 2021/2/2 09:00
 * @email wudq@infore.com
 */
public class Response implements Serializable {

    /**
     * codes : 0
     * codedes : success
     * datas : [{"loginName":"xxxxxxxxxx","loginPassword":"xxxxxxxxxx"}]
     * methodName : appLogin
     * dataLen : xxxxend
     */

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
