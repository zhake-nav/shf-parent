package com.atguigu.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * @Author ZhangHaoYu
 * @Date 2023/2/11 14:26
 * @Version 1.0
 */

@Configuration  // 标识只一个配置类
@EnableWebSecurity  // @EnableWebSecurity 是开始SpringSecurity的标识
@EnableGlobalMethodSecurity(prePostEnabled = true)  // 开启controller方法权限控制
public class MySpringSecurityConfig extends WebSecurityConfigurerAdapter {
    // No AuthenticationProvider found for UsernamePasswordAuthenticationToken
    // 在内存中设置一个被认证的用户名和密码
/*    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication()
                .withUser("admin")
                .password(new BCryptPasswordEncoder().encode("111111"))
                .roles("");
    }*/
    // SpringSecurity 不允许内联页面（iframe）
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // 不重写登录界面的话，必须调用父类的方法，否则就不需要认证即可访问
        //super.configure(http);

        // 允许iframe 显示
        http.headers().frameOptions().sameOrigin();

        // 登录设置
        http
                .authorizeRequests()
                .antMatchers("/static/**", "/login").permitAll()  // 允许匿名用户访问的路径
                .anyRequest().authenticated()  // 其他界面全需要验证
                .and()
                .formLogin()
                .loginPage("/login") //用户未登录时，访问任何需要权限的资源都转跳到该路径，即登录页面，此时登陆成功后会继续跳转到第一次访问的资源页面（相当于被过滤了一下）
                .defaultSuccessUrl("/") //登录认证成功后默认转跳的路径，意思时admin登录后也跳转到/
                .and()
                .logout()
                .logoutUrl("/logout") //退出登陆的路径，指定spring security拦截的注销url,退出功能是security提供的
                .logoutSuccessUrl("/login") //用户退出后要被重定向的url
                .and()
                .csrf().disable();//关闭跨域请求伪造功能
    }

    // There is no PasswordEncoder mapped for the id "null" 没有为id“null”映射PasswordEncoder
    // PasswordEncoderFactories 从这里创建一个密码加密器放到IOC容器中
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }


}
