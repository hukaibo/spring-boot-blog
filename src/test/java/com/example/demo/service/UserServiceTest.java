package com.example.demo.service;

import com.example.demo.entity.User;
import com.example.demo.dao.UserMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

//创建测试的快捷键 shift+ctrl+t
@ExtendWith(MockitoExtension.class)
class UserServiceTest {
    @Mock
    BCryptPasswordEncoder mockEncoder;
    @Mock
    UserMapper mockMapper;
    @InjectMocks
    UserService userService;

    @Test
    public void testSave() {
        //调用userService
        // 验证userService将请求转发给了userMapper
        //给定一个条件
        Mockito.when(mockEncoder.encode("myPassword")).thenReturn("myEncodedPassword");

        userService.save("myUser", "myPassword");

        Mockito.verify(mockMapper).save("myUser", "myEncodedPassword");
    }

    @Test
    public void testGetUserByUsername() {
        userService.getUserByUsername("myUser");
        Mockito.verify(mockMapper).findUserByUsername("myUser");
    }

    @Test
    public void throwExceptionWhenUserNotFound() {
        Mockito.when(mockMapper.findUserByUsername("myUser")).thenReturn(null);
        Assertions.assertThrows(UsernameNotFoundException.class,
                () -> userService.loadUserByUsername("myUser"));
    }
    @Test
    public void returnUserDetailsWhenUserFound(){
        Mockito.when(mockMapper.findUserByUsername("myUser"))
                .thenReturn(new User(123,"myUser","myEncodedPassword"));
        UserDetails userDetails = userService.loadUserByUsername("myUser");
        Assertions.assertEquals("myUser",userDetails.getUsername());
        Assertions.assertEquals("myEncodedPassword",userDetails.getPassword());
    }
}