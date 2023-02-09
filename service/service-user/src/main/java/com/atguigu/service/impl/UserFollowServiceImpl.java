package com.atguigu.service.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.atguigu.base.BaseDao;
import com.atguigu.base.BaseServiceImpl;
import com.atguigu.dao.UserFollowDao;
import com.atguigu.entity.HouseImage;
import com.atguigu.entity.UserFollow;
import com.atguigu.entity.UserInfo;
import com.atguigu.service.DictService;
import com.atguigu.service.HouseImageService;
import com.atguigu.service.UserFollowService;
import com.atguigu.service.UserInfoService;
import com.atguigu.vo.UserFollowVo;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Author ZhangHaoYu
 * @Date 2023/2/9 16:27
 * @Version 1.0
 */
@Service(interfaceClass = UserFollowService.class)
@Transactional
public class UserFollowServiceImpl extends BaseServiceImpl<UserFollow> implements UserFollowService {
    @Autowired
    private UserFollowDao userFollowDao;
    @Reference
    private DictService dictService;
    @Reference
    private HouseImageService houseImageService;

    @Override
    protected BaseDao<UserFollow> getEntityDao() {
        return userFollowDao;
    }

    /**
     * 关注房源
     * @param userId
     * @param houseId
     * @return
     */
    @Override
    public Integer follow(Long userId, Long houseId) {
        return userFollowDao.insert(userId, houseId);
    }

    /**
     * 判断目标房源是否关注了
     * @param userId
     * @param houseId
     * @return
     */
    @Override
    public Boolean isFollowed(Long userId, Long houseId) {
        Integer val = userFollowDao.countByUserIdAndHouseId(userId, houseId);
        return val != 0;
    }

    @Override
    public PageInfo<UserFollowVo> findListPage(Integer pageNum, Integer pageSize, Long userId) {
        PageHelper.startPage(pageNum, pageSize);
        Page<UserFollowVo> page = userFollowDao.findListPage(userId);
        List<UserFollowVo> followVoList = page.getResult();

        for (UserFollowVo userFollowVo : followVoList) {
            String houseTypeName = dictService.getNameById(userFollowVo.getHouseTypeId());
            String directionName = dictService.getNameById(userFollowVo.getDirectionId());
            String floorName = dictService.getNameById(userFollowVo.getFloorId());

            List<HouseImage> list = houseImageService.findList(userFollowVo.getHouseId(), 1);
            if (list.size() != 0) {
                userFollowVo.setDefaultImageUrl(list.get(0).getImageUrl());
            }
            userFollowVo.setHouseTypeName(houseTypeName);
            userFollowVo.setFloorName(floorName);
            userFollowVo.setDirectionName(directionName);
        }


        return new PageInfo<>(page, 5);
    }

    @Override
    public void cancelFollow(Long id) {
        userFollowDao.delete(id);
    }
}
