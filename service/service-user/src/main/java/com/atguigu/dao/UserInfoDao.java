package com.atguigu.dao;

import com.atguigu.base.BaseDao;
import com.atguigu.base.BaseService;
import com.atguigu.entity.UserInfo;

/**
 * @Author ZhangHaoYu
 * @Date 2023/2/8 13:07
 * @Version 1.0
 */
public interface UserInfoDao extends BaseDao<UserInfo> {
    UserInfo getByPhone(String phone);
}
