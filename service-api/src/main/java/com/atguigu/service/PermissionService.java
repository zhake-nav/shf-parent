package com.atguigu.service;

import com.atguigu.base.BaseService;
import com.atguigu.entity.Permission;

import java.util.List;

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
}
