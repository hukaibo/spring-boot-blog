package com.example.demo.controller;

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
            return result.failMethod("用户不存在");

        } else {
            return result.successMethod2("",loguer);

        }
    }

    @PostMapping("/auth/register")
    @ResponseBody
    public result register(@RequestBody Map<String, String> usernameAndpassword) {
        String username = usernameAndpassword.get("username");
        String password = usernameAndpassword.get("password");
        if (username.length() < 1 || username.length() > 15) {
            return result.failMethod("用户名不符合");
        }
        if (password.length() < 6 || password.length() > 16) {
            return result.failMethod("密码不符合");
        }
        try {
            userService.save(username, password);

        }catch (Exception e){
            e.printStackTrace();
            return result.failMethod("user already exists");
        }
        return result.successMethod("success!");

    }

    @GetMapping("/auth/logout")
    @ResponseBody
    public result logout() {
        String name = SecurityContextHolder.getContext().getAuthentication().getName();

        User user = userService.getUserByUsername(name);
        if (user == null) {
            return result.failMethod("用户没登录");
        } else {
            SecurityContextHolder.clearContext();
            return result.successMethod("注销成功");

        }
    }

    @GetMapping(value = "/test", produces = "application/json;charset=utf-8")
    @ResponseBody
    public Object aa() {
        return new A();
    }

    private static class A {

        public String getHukaibo() {
            return "hukaibo";
        }

        public String get胡凯杨() {
            return "hukaiyang";
        }
    }

    @PostMapping(value = "/auth/login",produces = "application/json;charset=utf-8")
    @ResponseBody
    public result login(
            @RequestBody Map<String, String> usernameAndpassword

    ) {
        String username = usernameAndpassword.get("username");
        String password = usernameAndpassword.get("password");
        UserDetails userDetails = null;
        try {
            userDetails = userService.loadUserByUsername(username);
        } catch (UsernameNotFoundException e) {
            return result.failMethod("用户不存在");
        }
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(userDetails, password, userDetails.getAuthorities());
        try {
            authenticationManager.authenticate(token);
            SecurityContextHolder.getContext().setAuthentication(token);
            return result.successMethod2("登录成功",userService.getUserByUsername(username));
        } catch (BadCredentialsException e) {
            return result.failMethod("密码不正确");
        }
    }




}
