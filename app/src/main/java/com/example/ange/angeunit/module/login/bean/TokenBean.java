package com.example.ange.angeunit.module.login.bean;

/**
 * Created by Administrator on 2016/10/2.
 */
public class TokenBean {
    private String token;
    private String message;
    private int status_code;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getStatus_code() {
        return status_code;
    }

    public void setStatus_code(int status_code) {
        this.status_code = status_code;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
