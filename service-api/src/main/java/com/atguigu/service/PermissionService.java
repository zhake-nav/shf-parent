package com.atguigu.service;

import com.atguigu.base.BaseService;
import com.atguigu.entity.Permission;

import java.util.List;
import java.util.Map;

/**
 * @Author ZhangHaoYu
 * @Date 2023/2/9 21:02
 * @Version 1.0
 */
public interface PermissionService extends BaseService<Permission> {
    /**
     * 找出acl_permission 中所有有效数据
     * @return
     */
    List<Permission> findAll();

    /**
     * 找出所有的permission 并根据角色id 勾选其permission
     * @param roleId
     * @return
     */
    List<Map<String, Object>> findPermissionByRoleId(Long roleId);

    /**
     * 保存前端来的此roleId 的permissions数据
     * @param roleId
     * @param permissionIds
     */
    void savePermissionIdsByRoleId(Long roleId, Long[] permissionIds);

    /**
     * 根据id 实现动态菜单
     * @param adminId
     * @return
     */
    List<Permission> findMenuPermissionByAdminId(Long adminId);


    /**
     * 查询所有permission 并转为树形格式
     * @return
     */
    List<Permission> findAllMenu();

    /**
     *  获取用户功能权限
     * @param adminId
     * @return
     */
    List<String> findCodeListByAdminId(Long adminId);
}
