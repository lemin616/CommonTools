package com.lemin.commontools.httpapi.bean;

import java.io.Serializable;

/**
 * 返回数据基类
 */

public class BaseData implements Serializable {
    /**
     * 错误码
     */
    private int code;
    /**
     * 错误描述
     */
    private String message;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return message;
    }

    public void setMsg(String msg) {
        this.message = msg;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
