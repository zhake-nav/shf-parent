package com.atguigu.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.atguigu.base.BaseController;
import com.atguigu.entity.Role;
import com.atguigu.service.RoleService;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;;
import java.util.Map;

/**
 * @Author ZhangHaoYu
 * @Date 2023/1/30 19:54
 * @Version 1.0
 */
@Controller
@RequestMapping("/role")
public class RoleController extends BaseController {
    private static final String PAGE_INDEX = "role/index";
    private final static String PAGE_SUCCESS = "common/successPage";
    private final static String PAGE_EDIT = "role/edit";
    private final static String LIST_ACTION = "redirect:/role";
    private final static String PAGE_CREATE = "role/create";

    @Reference
    private RoleService roleService;

    /* 无分页的index列表界面 */
    /*@RequestMapping
    public String getAll(Map<String, Object> map) {
        List<Role> roleList = roleService.findAll();
        map.put("list", roleList);
        return "role/index";
    }
*/
    @RequestMapping
    public String index(Map<String, Object> model, HttpServletRequest request) {
        Map<String,Object> filters = getFilters(request);
        PageInfo<Role> page = roleService.findPage(filters);

        model.put("page", page);
        model.put("filters", filters);
        return PAGE_INDEX;
    }

    @RequestMapping("/create")
    public String create(){
        return PAGE_CREATE;
    }

    @RequestMapping("/save")
    public String save(Role role, HttpServletRequest request) {
        roleService.insert(role);
        return PAGE_SUCCESS;
    }

    @RequestMapping("/edit/{id}")
    public String edit(@PathVariable Long id, Map<String, Object> map) {
        Role role = roleService.getById(id);
        map.put("role", role);
        return PAGE_EDIT;
    }
    @RequestMapping("/update")
    public String update(Role role) {
        roleService.update(role);
        return PAGE_SUCCESS;
    }
    @RequestMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {
        roleService.delete(id);
        return LIST_ACTION;
    }
}
