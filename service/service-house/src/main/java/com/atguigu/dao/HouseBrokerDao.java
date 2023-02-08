package com.atguigu.dao;

import com.atguigu.base.BaseDao;
import com.atguigu.entity.House;
import com.atguigu.entity.HouseBroker;

import java.util.List;

/**
 * @Author ZhangHaoYu
 * @Date 2023/2/5 15:13
 * @Version 1.0
 */
public interface HouseBrokerDao extends BaseDao<HouseBroker> {
    List<HouseBroker> findAll();

    List<HouseBroker> findListByHouseId(Long id);
}
