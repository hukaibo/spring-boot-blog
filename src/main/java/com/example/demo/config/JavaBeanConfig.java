package com.example.demo.config;

import com.example.demo.mapper.UserMapper;
import com.example.demo.service.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
public class JavaBeanConfig {
//        @Bean
//        public UserDetailsService userDetailsService(BCryptPasswordEncoder bCryptPasswordEncoder){
//            return new UserService(bCryptPasswordEncoder);
//        }
}
