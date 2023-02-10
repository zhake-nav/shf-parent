package com.atguigu.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.atguigu.base.BaseController;
import com.atguigu.entity.Role;
import com.atguigu.service.PermissionService;
import com.atguigu.service.RoleService;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;;
import java.util.List;
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
    private static final String PAGE_ASSIGN_SHOW = "role/assignShow";

    @Reference
    private RoleService roleService;
    @Reference
    private PermissionService permissionService;

    /* 无分页的index列表界面 */
    /*@RequestMapping
    public String getAll(Map<String, Object> map) {
        List<Role> roleList = roleService.findAll();
        map.put("list", roleList);
        return "role/index";
    }
*/

    /**
     * 角色管理界面
     * @param model
     * @param request
     * @return
     */
    @RequestMapping
    public String index(Map<String, Object> model, HttpServletRequest request) {
        Map<String,Object> filters = getFilters(request);
        PageInfo<Role> page = roleService.findPage(filters);

        model.put("page", page);
        model.put("filters", filters);
        return PAGE_INDEX;
    }

    /**
     * 角色添加界面
     * @return
     */
    @RequestMapping("/create")
    public String create(){
        return PAGE_CREATE;
    }

    /**
     * 保留新增数据
     * @param role
     * @param request
     * @return
     */
    @RequestMapping("/save")
    public String save(Role role, HttpServletRequest request) {
        roleService.insert(role);
        return PAGE_SUCCESS;
    }

    /**
     * 编辑界面数据
     * @param id
     * @param map
     * @return
     */
    @RequestMapping("/edit/{id}")
    public String edit(@PathVariable Long id, Map<String, Object> map) {
        Role role = roleService.getById(id);
        map.put("role", role);
        return PAGE_EDIT;
    }

    /**
     * 保留更新
     * @param role
     * @return
     */
    @RequestMapping("/update")
    public String update(Role role) {
        roleService.update(role);
        return PAGE_SUCCESS;
    }

    /**
     * 删除角色
     * @param id
     * @return
     */
    @RequestMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {
        roleService.delete(id);
        return LIST_ACTION;
    }

    /**
     * 进入分配权限页面
     * @param roleId
     * @return
     */
    @RequestMapping("/assignShow/{roleId}")
    public String assignShow(@PathVariable Long roleId, ModelMap model) {
        //获取处理过的所有权限列表
        List<Map<String,Object>> zNodes = permissionService.findPermissionByRoleId(roleId);

        model.addAttribute("zNodes", zNodes);
        model.addAttribute("roleId", roleId);
        return PAGE_ASSIGN_SHOW;
    }

    @RequestMapping("/assignPermission")
    public String assignPermission(Long roleId, Long[] permissionIds) {
        // 先删除role_permission中存在的此roleId数据，在保留前端传过来的数据
        permissionService.savePermissionIdsByRoleId(roleId, permissionIds);
        return PAGE_SUCCESS;
    }
}
