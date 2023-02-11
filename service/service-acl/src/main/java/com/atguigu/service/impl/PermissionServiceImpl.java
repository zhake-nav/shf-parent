package com.atguigu.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.atguigu.base.BaseDao;
import com.atguigu.base.BaseServiceImpl;
import com.atguigu.dao.PermissionDao;
import com.atguigu.dao.RolePermissionDao;
import com.atguigu.entity.Permission;
import com.atguigu.entity.RolePermission;
import com.atguigu.helper.PermissionHelper;
import com.atguigu.service.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author ZhangHaoYu
 * @Date 2023/2/9 21:03
 * @Version 1.0
 */
@Service(interfaceClass = PermissionService.class)
@Transactional
public class PermissionServiceImpl extends BaseServiceImpl<Permission> implements PermissionService {
    @Autowired
    private PermissionDao permissionDao;
    @Autowired
    private RolePermissionDao rolePermissionDao;

    @Override
    protected BaseDao<Permission> getEntityDao() {
        return permissionDao;
    }

    @Override
    public List<Permission> findAll() {
        List<Permission> permissionList = permissionDao.findAll();
        if(CollectionUtils.isEmpty(permissionList)) {
            return null;
        }
        return permissionList;
    }

    @Override
    public List<Map<String, Object>> findPermissionByRoleId(Long roleId) {
        // 获取所有的权限列表
        List<Permission> permissionList = permissionDao.findAll();

        // 根据roleId在role_permission表中获取permission id 列表
        List<Long> permissionIdList = rolePermissionDao.findPermissionIdListByRoleId(roleId);

        //构建ztree数据
        // { id:2, pId:0, name:"随意勾选 2", checked:true, open:true},
        List<Map<String,Object>> zNodes = new ArrayList<>();
        for (Permission permission : permissionList) {
            // 构建map数据
            HashMap<String, Object> map = new HashMap<>();
            map.put("id", permission.getId());
            map.put("pId", permission.getParentId());
            map.put("name", permission.getName());

            // 判断是否被checked
            if (permissionIdList.contains(permission.getId())) {
                map.put("checked", true);
            }
            zNodes.add(map);
        }
        return zNodes;
    }

    @Override
    public void savePermissionIdsByRoleId(Long roleId, Long[] permissionIds) {
        // 先删除此roleId 在数据库中已经存在的数据
        rolePermissionDao.delete(roleId);

        // 循环遍历前端数据添加到表中
        for (Long permissionId : permissionIds) {
            if (StringUtils.isEmpty(permissionId)) {
                continue;
            }
            RolePermission rolePermission = new RolePermission();
            rolePermission.setRoleId(roleId);
            rolePermission.setPermissionId(permissionId);

            rolePermissionDao.insert(rolePermission);
        }
    }

    @Override
    public List<Permission> findMenuPermissionByAdminId(Long adminId) {
        // TODO 默认的超级管理员的id为1
        List<Permission> permissionList = null;

        if (adminId == 1) {
            permissionList = permissionDao.findAll();
        } else {
            permissionList = permissionDao.findListByAdminId(adminId);
        }

        // 将permission数据模型，转为树形模型
        return PermissionHelper.bulid(permissionList);
    }

    @Override
    public List<Permission> findAllMenu() {
        //全部权限列表
        List<Permission> permissionList = permissionDao.findAll();
        if(CollectionUtils.isEmpty(permissionList)) return null;

        //构建树形数据,总共三级
        //把权限数据构建成树形结构数据
        return PermissionHelper.bulid(permissionList);
    }

    @Override
    public List<String> findCodeListByAdminId(Long adminId) {
        //超级管理员admin账号id为：1
        if(adminId == 1) {
            return permissionDao.findAllCodeList();
        }
        return permissionDao.findCodeListByAdminId(adminId);
    }
}
