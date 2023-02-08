package com.atguigu.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.atguigu.entity.Admin;
import com.atguigu.entity.HouseBroker;
import com.atguigu.service.AdminService;
import com.atguigu.service.HouseBrokerService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @Author ZhangHaoYu
 * @Date 2023/2/5 15:11
 * @Version 1.0
 */

@Controller
@RequestMapping("/houseBroker")
public class HouseBrokerController {
    public static final String PAGE_CREATE = "houseBroker/create";
    public static final String PAGE_EDIT = "houseBroker/edit";
    public static final String LIST_ACTION = "redirect:/house/";
    public static final String PAGE_SUCCESS = "common/successPage";

    @Reference
    private HouseBrokerService houseBrokerService;

    @Reference
    private AdminService adminService;

    /**
     * 去往添加界面
     * @param model
     * @param houseId
     * @return
     */
    @RequestMapping("/create")
    public String toCreatePage(ModelMap model, @RequestParam Long houseId) {
        List<Admin> adminList = adminService.findAll();
        model.addAttribute("adminList", adminList);
        model.addAttribute("houseId", houseId);
        return PAGE_CREATE;
    }

    /**
     * 保存新增
     * @param houseBroker
     * @return
     */
    @RequestMapping("/save")
    public String save(HouseBroker houseBroker) {
        // houseBroker的broker_id值等于admin的id值
        Admin admin = adminService.getById(houseBroker.getBrokerId());
        houseBroker.setBrokerName(admin.getName());
        houseBroker.setBrokerHeadUrl(admin.getHeadUrl());
        houseBrokerService.insert(houseBroker);

        return PAGE_SUCCESS;
    }

    /**
     * 去修改界面
     * @param id
     * @param model
     * @return
     */
    @RequestMapping("/edit/{id}")
    public String toEditPage(@PathVariable Long id,ModelMap model) {
        HouseBroker houseBroker = houseBrokerService.getById(id);
        List<Admin> adminList = adminService.findAll();
        model.addAttribute("adminList", adminList);
        model.addAttribute("houseBroker", houseBroker);
        return PAGE_EDIT;
    }

    /**
     * 保存修改
     * @param houseBroker
     * @return
     */
    @RequestMapping("/update")
    public String update(HouseBroker houseBroker){
        Admin admin = adminService.getById(houseBroker.getBrokerId());
        houseBroker.setBrokerName(admin.getName());
        houseBroker.setBrokerHeadUrl(admin.getHeadUrl());
        houseBrokerService.update(houseBroker);
        return PAGE_SUCCESS;
    }

    /**
     * 删除
     * @param id
     * @return
     */
    @RequestMapping("/delete/{houseId}/{id}")
    public String delete(@PathVariable Long houseId, @PathVariable Long id) {
        houseBrokerService.delete(id);
        return LIST_ACTION + houseId;
    }
}
