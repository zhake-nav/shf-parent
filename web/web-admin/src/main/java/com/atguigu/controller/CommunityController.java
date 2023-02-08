package com.atguigu.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.atguigu.base.BaseController;
import com.atguigu.entity.Community;
import com.atguigu.entity.Dict;
import com.atguigu.service.CommunityService;
import com.atguigu.service.DictService;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * @Author ZhangHaoYu
 * @Date 2023/2/4 14:56
 * @Version 1.0
 */
@Controller
@RequestMapping("/community")
public class CommunityController extends BaseController {
    private final static String PAGE_INDEX = "community/index";
    private final static String PAGE_CREATE = "community/create";
    private final static String PAGE_EDIT = "community/edit";
    private final static String PAGE_SUCCESS = "common/successPage";
    private final static String LIST_ACTION = "redirect:/community";

    @Reference
    private CommunityService communityService;
    @Reference
    private DictService dictService;

    @RequestMapping
    public String index(ModelMap model,HttpServletRequest request) {
        // 开启分页
        Map<String, Object> filters = getFilters(request);
        PageInfo<Community> page = communityService.findPage(filters);
        model.addAttribute("page", page);
        model.addAttribute("filters", filters);

        // 默认北京
        List<Dict> areaList = dictService.findListByDictCode("beijing");
        model.addAttribute("areaList", areaList);

        return PAGE_INDEX;
    }

    /**
     * 去新增界面
     * @param model
     * @return
     */
    @RequestMapping("/create")
    public String toCreatePage(ModelMap model) {// 默认北京
        List<Dict> areaList = dictService.findListByDictCode("beijing");
        model.addAttribute("areaList", areaList);
        return PAGE_CREATE;
    }

    /**
     * 持久化新增数据
     * @param community
     * @return
     */
    @RequestMapping("/save")
    public String save(Community community) {
        communityService.insert(community);
        return PAGE_SUCCESS;
    }

    /**
     * 删除房源信息
     * @param id
     * @return
     */
    @RequestMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {
        communityService.delete(id);
        return LIST_ACTION;
    }

    /**
     * 去到编辑界面
     * @param id
     * @param model
     * @return
     */
    @RequestMapping("/edit/{id}")
    public String toEditPage(@PathVariable Long id, ModelMap model) {
        Community community = communityService.getById(id);
        model.addAttribute("community", community);

        // 下拉框数据
        List<Dict> areaList = dictService.findListByDictCode("beijing");
        model.addAttribute("areaList", areaList);
        return PAGE_EDIT;
    }

    @RequestMapping("/update")
    public String update(Community community) {
        communityService.update(community);
        return PAGE_SUCCESS;
    }
}
