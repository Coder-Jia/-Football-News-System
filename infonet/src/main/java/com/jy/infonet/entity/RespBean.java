package com.jy.infonet.entity;

/**
 * 用于返回处理结果
 */
public class RespBean {
    private String status;
    private String message;

    public RespBean() {
    }

    public RespBean(String status, String message) {
        this.status = status;
        this.message = message;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
