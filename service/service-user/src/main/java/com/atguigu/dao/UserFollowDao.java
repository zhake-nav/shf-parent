package com.atguigu.dao;

import com.atguigu.base.BaseDao;
import com.atguigu.entity.UserFollow;
import com.atguigu.vo.UserFollowVo;
import com.github.pagehelper.Page;
import org.apache.ibatis.annotations.Param;

/**
 * @Author ZhangHaoYu
 * @Date 2023/2/9 16:27
 * @Version 1.0
 */
public interface UserFollowDao extends BaseDao<UserFollow> {
    Integer insert(@Param("userId") Long id,@Param("houseId") Long houseId);

    Integer countByUserIdAndHouseId(@Param("userId") Long userId,@Param("houseId") Long houseId);

    Page<UserFollowVo> findListPage(Long id);
}
