package com.atguigu.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.atguigu.entity.Permission;
import com.atguigu.service.PermissionService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * @Author ZhangHaoYu
 * @Date 2023/2/9 21:02
 * @Version 1.0
 */
@Controller
@RequestMapping("/permission")
public class PermissionController {
    public static final String PAGE_INDEX = "permission/index";

    @Reference
    private PermissionService permissionService;

    /**
     * 权限管理页面
     * @return
     */
    @RequestMapping
    public String toIndexPage(ModelMap model){
//        // 渲染数据 （permission 表数据）
//        List<Permission> list =  permissionService.findAll();
//        model.addAttribute("list", list);
        return PAGE_INDEX;
    }
}
