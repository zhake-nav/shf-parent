package com.atguigu.dao;

import com.atguigu.base.BaseDao;
import com.atguigu.entity.Admin;

import java.util.List;

/**
 * @Author ZhangHaoYu
 * @Date 2023/2/1 21:16
 * @Version 1.0
 */
public interface AdminDao extends BaseDao<Admin> {
        List<Admin> findAll();
}
