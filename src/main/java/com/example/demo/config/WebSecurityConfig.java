package com.example.demo.config;

import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

import javax.inject.Inject;


@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    UserService userService;


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();
        // 配置拦截规则
        http.authorizeRequests().antMatchers("/", "/auth/**").permitAll();
//                .antMatchers("/page1/**").hasRole("LEVEL1")
//                .antMatchers("/page2/**").hasRole("LEVEL2")
//                .antMatchers("/page3/**").hasRole("LEVEL3");
//        // 配置登录功能
//        http.formLogin().usernameParameter("user")
//                .passwordParameter("pwd")
//                .loginPage("/userLogin");
//        // 注销成功跳转首页
//        http.logout().logoutSuccessUrl("/");
//        //开启记住我功能
//        http.rememberMe().rememberMeParameter("remeber");
    }

    //    @Bean
//    public UserDetailsService userDetailsService(){
//        UserDetails user=
//                User.withDefaultPasswordEncoder()
//                .username("user")
//                .password("password")
//                .roles("USER")
//                .build();
//        return new InMemoryUserDetailsManager(user);
//    }
    @Bean
    public AuthenticationManager customAuthenticationManager() throws Exception {
        return authenticationManager();
    }

//    @Autowired
//    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
//        auth.userDetailsService(userService).passwordEncoder(bCryptPasswordEncoder());
//    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
