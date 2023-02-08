package com.atguigu.dao;

import com.atguigu.base.BaseDao;
import com.atguigu.entity.Community;

import java.util.List;

/**
 * @Author ZhangHaoYu
 * @Date 2023/2/4 14:51
 * @Version 1.0
 */
public interface CommunityDao extends BaseDao<Community> {

    List<Community> findAll();
}
