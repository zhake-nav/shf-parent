package com.atguigu.service;

import com.atguigu.base.BaseService;
import com.atguigu.entity.UserFollow;
import com.atguigu.vo.UserFollowVo;
import com.github.pagehelper.PageInfo;

/**
 * @Author ZhangHaoYu
 * @Date 2023/2/9 16:24
 * @Version 1.0
 */
public interface UserFollowService extends BaseService<UserFollow> {
    /**
     * 关注房源
     * @param userId
     * @param houseId
     * @return
     */
    Integer follow(Long userId, Long houseId);

    /**
     * 是否关注
     * @param userId
     * @param houseId
     * @return
     */
    Boolean isFollowed(Long userId, Long houseId);

    /**
     * 返回目标条件的分页数据
     * @param pageNum
     * @param pageSize
     * @param userId
     * @return
     */
    PageInfo<UserFollowVo> findListPage(Integer pageNum, Integer pageSize, Long userId);

    /**
     * 取消关注
     * @param id
     */
    void cancelFollow(Long id);
}
