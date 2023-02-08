package com.atguigu.dao;

import com.atguigu.base.BaseDao;
import com.atguigu.entity.HouseImage;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Author ZhangHaoYu
 * @Date 2023/2/6 11:12
 * @Version 1.0
 */
public interface HouseImageDao extends BaseDao<HouseImage> {
    List<HouseImage> findList(@Param("houseId") Long houseId,@Param("type") Integer type);
}
