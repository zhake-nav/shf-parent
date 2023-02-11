package com.atguigu.service;

import com.atguigu.base.BaseService;
import com.atguigu.entity.Admin;

import java.util.List;

/**
 * @Author ZhangHaoYu
 * @Date 2023/2/1 21:14
 * @Version 1.0
 */
public interface AdminService extends BaseService<Admin> {
    List<Admin> findAll();

    Admin getByUserName(String username);
}
