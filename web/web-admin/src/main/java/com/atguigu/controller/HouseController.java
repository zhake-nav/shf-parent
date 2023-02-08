package com.atguigu.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.atguigu.base.BaseController;
import com.atguigu.entity.*;
import com.atguigu.result.Result;
import com.atguigu.service.*;
import com.atguigu.vo.HouseQueryVo;
import com.atguigu.vo.HouseVo;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * @Author ZhangHaoYu
 * @Date 2023/2/4 20:41
 * @Version 1.0
 */
@Controller
@RequestMapping("/house")
public class HouseController extends BaseController {
    private static final String PAGE_INDEX = "house/index";
    private static final String PAGE_CREATE = "house/create";
    private static final String PAGE_EDIT = "house/edit";
    private final static String PAGE_SUCCESS = "common/successPage";
    private final static String LIST_ACTION = "redirect:/house";
    private final static String PAGE_SHOW = "house/show";


    @Reference
    private HouseService houseService;
    @Reference
    private DictService dictService;
    @Reference
    private CommunityService communityService;
    @Reference
    private HouseBrokerService houseBrokerService;
    @Reference
    private HouseUserService houseUserService;
    @Reference
    private HouseImageService houseImageService;

    /**
     * 提取前端单选框数据，为首页添加界面提供数据
     * @param model
     */
    private void toHTMLSelectData(ModelMap model) {
        // 向前端发送单选框数据
        model.addAttribute("communityList", communityService.findAll());
        model.addAttribute("houseTypeList", dictService.findListByDictCode("houseType"));
        model.addAttribute("floorList", dictService.findListByDictCode("floor"));
        model.addAttribute("buildStructureList", dictService.findListByDictCode("buildStructure"));
        model.addAttribute("decorationList", dictService.findListByDictCode("decoration"));
        model.addAttribute("directionList", dictService.findListByDictCode("direction"));
        model.addAttribute("houseUseList", dictService.findListByDictCode("houseUse"));
    }

    /**
     * index界面
     * @param request
     * @param model
     * @return
     */
    @RequestMapping
    public String index(HttpServletRequest request, ModelMap model) {
        // 开启分页
        Map<String, Object> filters = getFilters(request);
        PageInfo<House> page = houseService.findPage(filters);
        model.addAttribute("page", page);
        model.addAttribute("filters", filters);

        toHTMLSelectData(model);
        return PAGE_INDEX;
    }

    /**
     * 带数据去新增界面
     * @param model
     * @return
     */
    @RequestMapping("/create")
    public String toCreatePage(ModelMap model) {
        toHTMLSelectData(model);
        return PAGE_CREATE;
    }

    /**
     * 保留新增
     * @param house
     * @return
     */
    @RequestMapping("/save")
    public String save(House house) {
        houseService.insert(house);
        return PAGE_SUCCESS;
    }

    /**
     * 前往编辑界面
     * @param id
     * @param model
     * @return
     */
    @RequestMapping("/edit/{id}")
    public String toEditPage(@PathVariable Long id, ModelMap model) {
        House house = houseService.getById(id);
        model.addAttribute("house", house);
        toHTMLSelectData(model);
        return PAGE_EDIT;
    }

    /**
     * 保存跟新
     * @param house
     * @return
     */
    @RequestMapping("/update")
    public String update(House house) {
        houseService.update(house);
        return PAGE_SUCCESS;
    }

    /**
     * 删除
     * @param id
     * @return
     */
    @RequestMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {
        houseService.delete(id);
        return LIST_ACTION;
    }

    /**
     * 发布房源信息
     * @param id
     * @param status
     * @return
     */
    @RequestMapping("/publish/{id}/{status}")
    public String publish(@PathVariable Long id, @PathVariable Integer status) {
        houseService.publish(id, status);
        System.out.println("id = " + id);
        System.out.println("status = " + status);
        return LIST_ACTION;
    }

    /**
     * 详情界面
     * @param model
     * @param id
     * @return
     */
    @RequestMapping("/{id}")
    public String show(ModelMap model, @PathVariable Long id) {
        House house = houseService.getById(id);
        Community community = communityService.getById(house.getCommunityId());
        List<HouseBroker> houseBrokerList = houseBrokerService.findListByHouseId(id);
        List<HouseUser> houseUserList = houseUserService.findListByHouseId(id);
        List<HouseImage> houseImage1List = houseImageService.findList(id, 1);
        List<HouseImage> houseImage2List = houseImageService.findList(id, 2);

        model.addAttribute("houseImage1List",houseImage1List);
        model.addAttribute("houseImage2List",houseImage2List);
        model.addAttribute("houseUserList", houseUserList);
        model.addAttribute("houseBrokerList", houseBrokerList);
        model.addAttribute("house", house);
        model.addAttribute("community", community);
        return PAGE_SHOW;
    }
}
