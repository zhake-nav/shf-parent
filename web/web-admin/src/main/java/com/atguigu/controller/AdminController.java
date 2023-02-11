package com.atguigu.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.atguigu.base.BaseController;
import com.atguigu.entity.Admin;
import com.atguigu.entity.AdminRole;
import com.atguigu.result.Result;
import com.atguigu.service.AdminService;
import com.atguigu.service.RoleService;
import com.atguigu.util.MD5;
import com.atguigu.util.QiniuUtil;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Map;
import java.util.UUID;

/**
 * @Author ZhangHaoYu
 * @Date 2023/2/1 21:24
 * @Version 1.0
 */
@Controller
@RequestMapping("/admin")
public class AdminController extends BaseController {
    private final static String LIST_ACTION = "redirect:/admin";
    private final static String PAGE_INDEX = "admin/index";
    private final static String PAGE_CREATE = "admin/create";
    private final static String PAGE_EDIT = "admin/edit";
    private final static String PAGE_SUCCESS = "common/successPage";
    private final static String PAGE_UPLOAD_SHOW = "admin/upload";
    private final static String PAGE_ASSIGN_SHOW = "admin/assignShow";

    @Reference
    private AdminService adminService;

    @Reference
    private RoleService roleService;

    @Autowired
    private PasswordEncoder passwordEncoder;
    /**
     * 列表
     * @param request
     * @param model
     * @return
     */
    @RequestMapping
    public String index(HttpServletRequest request, ModelMap model) {
        Map<String,Object> filters = getFilters(request);
        PageInfo<Admin> page = adminService.findPage(filters);

        model.addAttribute("page", page);
        model.addAttribute("filters", filters);
        return PAGE_INDEX;
    }

    /**
     * 进入新增界面
     * @param admin
     * @return
     */
    @RequestMapping("/create")
    public String create(Admin admin) {
        return PAGE_CREATE;
    }

    /**
     * 保存新增
     * @param admin
     * @return
     */
    @PostMapping("/save")
    public String save(Admin admin) {
        //设置默认头像
        admin.setPassword(passwordEncoder.encode(admin.getPassword()));
        admin.setHeadUrl("http://47.93.148.192:8080/group1/M00/03/F0/rBHu8mHqbpSAU0jVAAAgiJmKg0o148.jpg");
        adminService.insert(admin);
        return PAGE_SUCCESS;
    }

    /**
     * 删除
     * @param id
     * @return
     */
    @RequestMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {
        adminService.delete(id);
        return LIST_ACTION;
    }

    /**
     * 进入编辑界面，获取编辑数据
     * @param id
     * @param model
     * @return
     */
    @RequestMapping("/edit/{id}")
    public String displayData(@PathVariable Long id, ModelMap model) {
        Admin admin = adminService.getById(id);
        model.addAttribute("admin", admin);
        return PAGE_EDIT;
    }

    /**
     * 保留更新
     * @param admin
     * @return
     */
    @RequestMapping("/update")
    public String update(Admin admin) {
        adminService.update(admin);
        return PAGE_SUCCESS;
    }

    /**
     * 上传界面
     * @param id
     * @param model
     * @return
     */
    @RequestMapping("/uploadShow/{id}")
    public String toUploadPage(@PathVariable Long id,ModelMap model) {
        model.addAttribute("id",id);
        return PAGE_UPLOAD_SHOW;
    }

    /**
     * 图片同步到七牛云，将七牛云上的图片链接更新到admin
     * @param id
     * @param file
     * @return
     * @throws IOException
     */
    @RequestMapping("/upload/{id}")
    public String upload(@PathVariable Long id, @RequestParam("file")MultipartFile file) throws IOException {
        Admin admin = adminService.getById(id);
        String newFilename = UUID.randomUUID().toString();
        QiniuUtil.upload2Qiniu(file.getBytes(), newFilename);
        String url = "http://rpnesvx53.hn-bkt.clouddn.com/" + newFilename;
        admin.setHeadUrl(url);
        adminService.update(admin);
        return PAGE_SUCCESS;
    }

    /**
     * 根据admin-id找到这个id所对应的职务信息
     * @param adminId
     * @return
     */
    @GetMapping("/assignShow/{adminId}")
    public String assignShow(@PathVariable Long adminId, ModelMap model) {
        // 需要准备(角色种类)已分配职务和未分配职务，将其用一个map接受，再service.impl层实现
        Map<String, Object> roleMap = roleService.findRoleIdByAdminId(adminId);

        model.addAllAttributes(roleMap);
        model.addAttribute("adminId", adminId);

        return PAGE_ASSIGN_SHOW;
    }

    /**
     * 获取表单的数据，将要分配的职务id，添加到该用户信息表上
     * @param adminId 操作的用户id
     * @param roleIds 要分配的roleIds 职务
     * @return
     */
    @RequestMapping("/assignRole")
    public String assignRole(Long adminId, Long[] roleIds) {
        roleService.assignRoleBYAdminAndIds(adminId, roleIds);
        return PAGE_SUCCESS;
    }
}
