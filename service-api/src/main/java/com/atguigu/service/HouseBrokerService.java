package com.atguigu.service;

import com.atguigu.base.BaseService;
import com.atguigu.entity.HouseBroker;

import java.util.List;

/**
 * @Author ZhangHaoYu
 * @Date 2023/2/5 15:12
 * @Version 1.0
 */
public interface HouseBrokerService extends BaseService<HouseBroker> {
    List<HouseBroker> findAll();

    List<HouseBroker> findListByHouseId(Long id);
}
