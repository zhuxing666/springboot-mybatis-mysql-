package com.zhuzhu.store.util;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.Serializable;

public class JsonResult implements Serializable {
    //响应转台码
    private Integer state;
    //返回消息
    private String message;

    //响应的数据
    private Object data;

    public JsonResult() {
    }

    public JsonResult(Integer state) {
        this.state = state;
    }


    public JsonResult(Integer state, Object data) {
        this.state = state;
        this.data = data;
    }

    public JsonResult(String message, Object data) {
        this.message = message;
        this.data = data;
    }

    public JsonResult(Integer state, String message, Object data) {
        this.state = state;
        this.message = message;
        this.data = data;
    }

    /** 出现异常时调用 */
    public JsonResult(Throwable e) {
        super();
        // 获取异常对象中的异常信息
        this.message = e.getMessage();
    }


    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
