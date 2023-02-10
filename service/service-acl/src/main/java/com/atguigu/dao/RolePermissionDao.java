package com.atguigu.dao;

import com.atguigu.base.BaseDao;
import com.atguigu.entity.RolePermission;

import java.util.List;

/**
 * @Author ZhangHaoYu
 * @Date 2023/2/10 13:22
 * @Version 1.0
 */
public interface RolePermissionDao extends BaseDao<RolePermission> {
    List<Long> findPermissionIdListByRoleId(Long roleId);
}
