package com.atguigu.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.atguigu.base.BaseDao;
import com.atguigu.base.BaseServiceImpl;
import com.atguigu.dao.AdminRoleDao;
import com.atguigu.dao.RoleDao;
import com.atguigu.entity.AdminRole;
import com.atguigu.entity.Role;
import com.atguigu.service.RoleService;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author ZhangHaoYu
 * @Date 2023/1/30 19:51
 * @Version 1.0
 */
@Service(interfaceClass = RoleService.class)
@Transactional
public class RoleServiceImpl extends BaseServiceImpl<Role> implements RoleService {
    @Resource
    private RoleDao roleDao;
    @Resource
    private AdminRoleDao adminRoleDao;

    @Override
    public List<Role> findAll() {
        return roleDao.listAll();
    }

    @Override
    public Map<String, Object> findRoleIdByAdminId(Long adminId) {
        List<Role> roleList = roleDao.listAll();
        List<Long> roleIdList = roleDao.findRoleIdByAdminId(adminId);

        ModelMap model = new ModelMap();
        ArrayList<Role> noAssignRoleList = new ArrayList<>();
        ArrayList<Role> assignRoleList = new ArrayList<>();

        for (Role role : roleList) {
            if (roleIdList.contains(role.getId())) {
                // 如果此时的职位id在该用户职责范围内，添加到assignRoleList
                assignRoleList.add(role);
            } else {
                noAssignRoleList.add(role);
            }
        }

        model.addAttribute("noAssignRoleList", noAssignRoleList);
        model.addAttribute("assignRoleList", assignRoleList);

        return model;
    }

    @Override
    public void assignRoleBYAdminAndIds(Long adminId, Long[] roleIds) {
        // 执行添加操作的时候需要删除该用户之前的职责，因为前端传过来的是已经添加的和新添加的职责
        adminRoleDao.delete(adminId);
        // 循环前端传来的roleIds，向表中添加数据
        for (Long roleId : roleIds) {
            if (StringUtils.isEmpty(roleId)) {
                continue;
            }

            AdminRole adminRole = new AdminRole();
            adminRole.setAdminId(adminId);
            adminRole.setRoleId(roleId);
            adminRoleDao.insert(adminRole);
        }
    }

    @Override
    protected BaseDao<Role> getEntityDao() {
        return roleDao;
    }
}
