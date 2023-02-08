package com.atguigu.service;

import com.atguigu.base.BaseService;
import com.atguigu.entity.House;
import com.atguigu.vo.HouseQueryVo;
import com.atguigu.vo.HouseVo;
import com.github.pagehelper.PageInfo;

import java.util.List;

/**
 * @Author ZhangHaoYu
 * @Date 2023/2/4 20:43
 * @Version 1.0
 */
public interface HouseService extends BaseService<House> {

    void publish(Long id, Integer status);

    PageInfo<HouseVo> findListPage(Integer pageNum, Integer pageSize, HouseQueryVo houseQueryVo);
}
