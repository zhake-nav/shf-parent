package com.atguigu.service;

import com.atguigu.base.BaseService;
import com.atguigu.entity.UserInfo;

/**
 * @Author ZhangHaoYu
 * @Date 2023/2/8 13:06
 * @Version 1.0
 */
public interface UserInfoService extends BaseService<UserInfo> {
    UserInfo getByPhone(String phone);
}
