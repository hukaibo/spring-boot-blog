package com.example.demo.entity;

public class result<T> {
    String status;
    String msg;
    T data;

    //    public static result failMethod(String msg){
//        return new result("fail",msg,false);
//    }
//    public static result successMethod(String msg){
//        return new result("ok",msg,true);
//    }
//    public static result successMethod2(String msg,User user){
//        return new result("ok",msg,true,user);
//    }
    public result(String status, String msg) {
        this.status = status;
        this.msg = msg;

    }

    public result(String status, String msg, T data) {
        this.status = status;
        this.msg = msg;
        this.data = data;

    }

    public String getStatus() {
        return status;
    }

    public String getMsg() {
        return msg;
    }


    public Object getData() {
        return data;
    }
}
