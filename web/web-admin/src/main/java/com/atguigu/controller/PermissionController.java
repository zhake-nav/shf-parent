package com.atguigu.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.atguigu.entity.Permission;
import com.atguigu.service.PermissionService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
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
    @Reference
    private PermissionService permissionService;

    private final static String LIST_ACTION = "redirect:/permission";

    private final static String PAGE_INDEX = "permission/index";
    private final static String PAGE_CREATE = "permission/create";
    private final static String PAGE_EDIT = "permission/edit";
    private final static String PAGE_SUCCESS = "common/successPage";

    /**
     * 权限管理页面
     * @return
     */
    @RequestMapping
    public String toIndexPage(ModelMap model){
        // 渲染数据 （permission 表数据）
        List<Permission> list =  permissionService.findAllMenu();
        model.addAttribute("list", list);
        return PAGE_INDEX;
    }

    /**
     * 去新增菜单界面
     * @return
     */
    @RequestMapping("/create")
    public String toCreatePage(Permission permission,
                               ModelMap model) {
        model.addAttribute("permission", permission);
        return PAGE_CREATE;
    }

    /**
     * 保存新增
     * @param permission
     * @return
     */
    @RequestMapping("/save")
    public String save(Permission permission) {
        permissionService.insert(permission);
        return PAGE_SUCCESS;
    }

    /**
     * 根据id获取permission 数据，并返回给前端
     * @param id
     * @return
     */
    @RequestMapping("/edit/{id}")
    public String toEditPage(@PathVariable Long id, ModelMap model) {
        Permission permission = permissionService.getById(id);
        model.addAttribute("permission", permission);
        return PAGE_EDIT;
    }

    /**
     * 保存更新
     * @param permission
     * @return
     */
    @RequestMapping("/update")
    public String update(Permission permission) {
        permissionService.update(permission);
        return PAGE_SUCCESS;
    }

    @RequestMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {
        permissionService.delete(id);
        return LIST_ACTION;
    }
}
