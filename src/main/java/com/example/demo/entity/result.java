package com.example.demo.entity;

public class result {
    String status;
    String msg;
    boolean isLogin;
    Object data;
    public static result failMethod(String msg){
        return new result("fail",msg,false);
    }
    public static result successMethod(String msg){
        return new result("ok",msg,true);
    }
    public static result successMethod2(String msg,User user){
        return new result("ok",msg,true,user);
    }

    private result(String status, String msg, boolean isLogin) {
        this.status = status;
        this.msg = msg;
        this.isLogin = isLogin;
    }

    private result(String status, String msg, boolean isLogin, Object data) {
        this.status = status;
        this.msg = msg;
        this.isLogin = isLogin;
        this.data = data;
    }

    public String getStatus() {
        return status;
    }

    public String getMsg() {
        return msg;
    }

    public boolean isLogin() {
        return isLogin;
    }

    public Object getData() {
        return data;
    }
}
