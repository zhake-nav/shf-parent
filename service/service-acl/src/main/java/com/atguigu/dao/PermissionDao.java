package com.atguigu.dao;

import com.atguigu.base.BaseDao;
import com.atguigu.entity.Permission;

import java.util.List;

/**
 * @Author ZhangHaoYu
 * @Date 2023/2/9 21:03
 * @Version 1.0
 */
public interface PermissionDao extends BaseDao<Permission> {
    List<Permission> findAll();

    List<Permission> findListByAdminId(Long adminId);

    List<String> findCodeListByAdminId(Long adminId);

    List<String> findAllCodeList();
}
