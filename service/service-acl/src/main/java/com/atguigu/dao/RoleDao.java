package com.atguigu.dao;

import com.atguigu.base.BaseDao;
import com.atguigu.entity.Role;

import java.util.List;

/**
 * @Author ZhangHaoYu
 * @Date 2023/1/30 19:47
 * @Version 1.0
 */
public interface RoleDao extends BaseDao<Role> {

    List<Role> listAll();
}
