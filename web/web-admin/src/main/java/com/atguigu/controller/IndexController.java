package com.atguigu.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.atguigu.entity.Admin;
import com.atguigu.entity.Permission;
import com.atguigu.service.AdminService;
import com.atguigu.service.PermissionService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * @Author ZhangHaoYu
 * @Date 2023/2/1 13:49
 * @Version 1.0
 */
@Controller
public class IndexController {
    @Reference
    private AdminService adminService;
    @Reference
    private PermissionService permissionService;

    /**
     * 框架首页
     * @return
     */
    @RequestMapping("/")
    public String index(ModelMap model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();
        String adminName = user.getUsername();  // 从sprintSecurity中获取用户名
        Admin admin = adminService.getByUserName(adminName);
        Long adminId = admin.getId();  // 获取用户id
        List<Permission> permissionList = permissionService.findMenuPermissionByAdminId(adminId);

        model.addAttribute("admin",admin);
        model.addAttribute("permissionList", permissionList);
        return "frame/index";
    }

    @RequestMapping("main")
    public String main() {
        return "frame/main";
    }

    @RequestMapping("/login")
    public String login() {
        return "frame/login";
    }

    @RequestMapping("/getInfo")
    @ResponseBody
    public Object getInfo() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        return authentication.getPrincipal();
    }
}
