package com.example.demo.controller;

import com.example.demo.entity.LoginResult;
import com.example.demo.entity.User;
import com.example.demo.entity.result;
import com.example.demo.service.UserService;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
public class AuthController {


    private UserService userService;

    private AuthenticationManager authenticationManager;

    @Inject
    public AuthController(UserService userService,AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
        this.userService=userService;
    }

    @GetMapping(value = "/auth", produces = "application/json;charset=utf-8")
    @ResponseBody

    public Object auth() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User loguer = userService.getUserByUsername(authentication==null?null:authentication.getName());
        if (loguer == null) {
            return LoginResult.failMethod("用户不存在");

        } else {
            return LoginResult.successMethod2("",loguer);

        }
    }

    @PostMapping("/auth/register")
    @ResponseBody
    public LoginResult register(@RequestBody Map<String, String> usernameAndpassword) {
        String username = usernameAndpassword.get("username");
        String password = usernameAndpassword.get("password");
        if (username.length() < 1 || username.length() > 15) {
            return LoginResult.failMethod("用户名不符合");
        }
        if (password.length() < 6 || password.length() > 16) {
            return LoginResult.failMethod("密码不符合");
        }
        try {
            userService.save(username, password);

        }catch (Exception e){
            e.printStackTrace();
            return LoginResult.failMethod("user already exists");
        }
        return LoginResult.successMethod("success!");

    }

    @GetMapping("/auth/logout")
    @ResponseBody
    public LoginResult logout() {
        String name = SecurityContextHolder.getContext().getAuthentication().getName();

        User user = userService.getUserByUsername(name);
        if (user == null) {
            return LoginResult.failMethod("用户没登录");
        } else {
            SecurityContextHolder.clearContext();
            return LoginResult.successMethod("注销成功");

        }
    }

    @GetMapping(value = "/test", produces = "application/json;charset=utf-8")
    @ResponseBody
    public Object aa() {
        return new A();
    }

    private static class A {
    List<User> users=new ArrayList<>();

        public String getHukaibo() {
            return "hukaibo";
        }

        public String get胡凯杨() {
            return "hukaiyang";
        }
        public Object getData(){
            users.add(new User(1,"a","a"));
            users.add(new User(2,"b","b"));

            return users;
        }
    }

    @PostMapping(value = "/auth/login",produces = "application/json;charset=utf-8")
    @ResponseBody
    public LoginResult login(
            @RequestBody Map<String, String> usernameAndpassword

    ) {
        String username = usernameAndpassword.get("username");
        String password = usernameAndpassword.get("password");
        UserDetails userDetails = null;
        try {
            userDetails = userService.loadUserByUsername(username);
        } catch (UsernameNotFoundException e) {
            return LoginResult.failMethod("用户不存在");
        }
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(userDetails, password, userDetails.getAuthorities());
        try {
            authenticationManager.authenticate(token);
            SecurityContextHolder.getContext().setAuthentication(token);
            return LoginResult.successMethod2("登录成功",userService.getUserByUsername(username));
        } catch (BadCredentialsException e) {
            return LoginResult.failMethod("密码不正确");
        }
    }




}
