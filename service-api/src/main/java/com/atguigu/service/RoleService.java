package com.atguigu.service;

import com.atguigu.base.BaseService;
import com.atguigu.entity.Role;

import java.util.List;

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

}
