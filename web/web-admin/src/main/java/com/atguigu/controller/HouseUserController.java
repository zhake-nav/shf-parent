package com.atguigu.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.atguigu.entity.HouseUser;
import com.atguigu.service.HouseUserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @Author ZhangHaoYu
 * @Date 2023/2/5 21:17
 * @Version 1.0
 */
@Controller
@RequestMapping("/houseUser")
public class HouseUserController {
    public static final String PAGE_CREATE = "houseUser/create";
    public static final String PAGE_EDIT = "houseUser/edit";
    public static final String PAGE_SUCCESS = "common/successPage";
    public static final String LIST_ACTION = "redirect:/house/";

    @Reference
    private HouseUserService houseUserService;

    /**
     * 去添加界面
     * @return
     */
    @RequestMapping("/create")
    public String toCreatePage(@RequestParam Long houseId, ModelMap model) {
        model.addAttribute("houseId", houseId);
        return  PAGE_CREATE;
    }

    /**
     * 保存新增
     * @param houseUser
     * @return
     */
    @RequestMapping("/save")
    public String save(HouseUser houseUser) {
        houseUserService.insert(houseUser);
        return PAGE_SUCCESS;
    }

    /**
     * 去编辑界面
     * @param id
     * @param model
     * @return
     */
    @RequestMapping("/edit/{id}")
    public String toEditPage(@PathVariable Long id,ModelMap model) {
        HouseUser houseUser = houseUserService.getById(id);
        model.addAttribute("houseUser",houseUser);
        return PAGE_EDIT;
    }

    /**
     * 保留更新
     * @param houseUser
     * @return
     */
    @RequestMapping("/update")
    public String update(HouseUser houseUser) {
        houseUserService.update(houseUser);
        return PAGE_SUCCESS;
    }

    /**
     * 删除
     * @param houseId
     * @param id
     * @return
     */
    @RequestMapping("/delete/{houseId}/{id}")
    public String delete(@PathVariable Long houseId, @PathVariable Long id) {
        houseUserService.delete(id);
        return LIST_ACTION + houseId;
    }
}
