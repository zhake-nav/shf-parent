package com.atguigu.service;

import com.atguigu.base.BaseService;
import com.atguigu.entity.Role;

import java.util.List;
import java.util.Map;

/**
 * @Author ZhangHaoYu
 * @Date 2023/1/30 19:50
 * @Version 1.0
 */
public interface RoleService extends BaseService<Role> {
    /**
     * 获取所有的角色信息
     * @return
     */
    List<Role> findAll();

    /**
     * 根据用户id获取职位信息
     * @param adminId
     * @return
     */
    Map<String, Object> findRoleIdByAdminId(Long adminId);

    /**
     * 根据adminId 在role——admin中添加（roleIds）职务数据
     * @param adminId
     * @param roleIds
     */
    void assignRoleBYAdminAndIds(Long adminId, Long[] roleIds);
}
