package com.atguigu.dao;

import com.atguigu.base.BaseDao;
import com.atguigu.entity.House;
import com.atguigu.vo.HouseQueryVo;
import com.atguigu.vo.HouseVo;
import com.github.pagehelper.Page;
import org.apache.ibatis.annotations.Param;

/**
 * @Author ZhangHaoYu
 * @Date 2023/2/4 20:46
 * @Version 1.0
 */
public interface HouseDao extends BaseDao<House> {
    Page<HouseVo> findListPage(HouseQueryVo houseQueryVo);
}
