package com.example.demo.entity;

import javax.xml.crypto.Data;

public class LoginResult extends result<User> {
    boolean isLogin;
    private User user;
    private Object data;
        public static LoginResult failMethod(String msg){
        return new LoginResult("fail",msg,false);
    }
    public static LoginResult successMethod(String msg){
        return new LoginResult("ok",msg,true);
    }
    public static LoginResult successMethod2(String msg,Object data){
        return new LoginResult("ok",msg,true,data);
    }

   private LoginResult(String status,String msg,boolean isLogin){
            super(status,msg);
            this.isLogin=isLogin;
   }
    private LoginResult(String status,String msg,boolean isLogin,Object data){
        super(status,msg);
        this.isLogin=isLogin;
        this.data=data;
    }

    @Override
    public Object getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public boolean isLogin() {
        return isLogin;
    }

    public void setLogin(boolean login) {
        isLogin = login;
    }
}
