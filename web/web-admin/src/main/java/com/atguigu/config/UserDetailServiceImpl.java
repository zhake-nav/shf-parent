package com.atguigu.config;

import com.alibaba.dubbo.config.annotation.Reference;
import com.atguigu.entity.Admin;
import com.atguigu.service.AdminService;
import com.atguigu.service.PermissionService;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * @Author ZhangHaoYu
 * @Date 2023/2/11 16:23
 * @Version 1.0
 */


@Component
public class UserDetailServiceImpl implements UserDetailsService {
    @Reference
    private AdminService adminService;
    @Reference
    private PermissionService permissionService;
    
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // 根据前端传来的用户名 判断是否存在该用户
        System.out.println("username = " + username);
        Admin admin = adminService.getByUserName(username);

        if (admin == null) {
            throw new UsernameNotFoundException("用户名不存在！");  // 抛出一个用户名不存在
        }

        // 对用户功能权限进行限制
        List<String> codeList = permissionService.findCodeListByAdminId(admin.getId());
        Collection<GrantedAuthority> authorities = new ArrayList<>();
        for (String code : codeList) {
            System.out.println("code = " + code);
            if(StringUtils.isEmpty(code)) {
                continue;
            }
            SimpleGrantedAuthority authority = new SimpleGrantedAuthority(code);
            authorities.add(authority);
        }
        // 对传进来的数据进行操作
        return new User(username,admin.getPassword(), authorities);
    }
}
