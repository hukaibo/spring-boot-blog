package com.example.demo.service;

import com.example.demo.entity.User;
import com.example.demo.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class UserService implements UserDetailsService {
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    //private Map<String, User> users = new ConcurrentHashMap<>();

    private UserMapper userMapper;

    @Inject
    public UserService(BCryptPasswordEncoder bCryptPasswordEncoder,UserMapper userMapper) {
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.userMapper=userMapper;
        //save("user", "password");
    }

    public void save(String username, String password) {
        userMapper.save(username, bCryptPasswordEncoder.encode(password));

    }

//    public String getPassword(String username) {
//        return users.get(username);
//    }

    public User getUserByUsername(String username) {
        return userMapper.findUserByUsername(username);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = getUserByUsername(username);

        if (user == null) {
            throw new UsernameNotFoundException(username + "不存在");
        }
        return new org.springframework.security.core.userdetails.User(username, user.getEncryptedPassword(), Collections.emptyList());
    }
}
