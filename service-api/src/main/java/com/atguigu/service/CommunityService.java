package com.atguigu.service;

import com.atguigu.base.BaseService;
import com.atguigu.entity.Community;

import java.util.List;

/**
 * @Author ZhangHaoYu
 * @Date 2023/2/4 14:57
 * @Version 1.0
 */

public interface CommunityService extends BaseService<Community> {

    List<Community> findAll();
}
