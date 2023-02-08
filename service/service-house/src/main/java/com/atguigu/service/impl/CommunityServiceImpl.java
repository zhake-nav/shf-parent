package com.atguigu.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.atguigu.base.BaseDao;
import com.atguigu.base.BaseServiceImpl;
import com.atguigu.dao.CommunityDao;
import com.atguigu.dao.DictDao;
import com.atguigu.entity.Community;
import com.atguigu.service.CommunityService;
import com.atguigu.util.CastUtil;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * @Author ZhangHaoYu
 * @Date 2023/2/4 15:21
 * @Version 1.0
 */

@Service(interfaceClass = CommunityService.class)
@Transactional
public class CommunityServiceImpl extends BaseServiceImpl<Community> implements CommunityService{
    @Resource
    private CommunityDao communityDao;
    @Resource
    private DictDao dictDao;

    @Override
    protected BaseDao<Community> getEntityDao() {
        return communityDao;
    }

    @Override
    public PageInfo<Community> findPage(Map<String, Object> filters) {
        //当前页数
        int pageNum = CastUtil.castInt(filters.get("pageNum"), 1);
        //每页显示的记录条数
        int pageSize = CastUtil.castInt(filters.get("pageSize"), 10);

        PageHelper.startPage(pageNum, pageSize);

        Page<Community> page = getEntityDao().findPage(filters);
        List<Community> result = page.getResult();
        for (Community community : result) {
            String areaName = dictDao.getNameById(community.getAreaId());
            String plateName = dictDao.getNameById(community.getPlateId());

            community.setAreaName(areaName);
            community.setPlateName(plateName);
        }
        return new PageInfo<>(page, 10);
    }

    @Override
    public List<Community> findAll() {
        return communityDao.findAll();
    }

    /**
     * 重写方法，从某字段id，set某字段的名字
     * @param id
     * @return
     */
    @Override
    public Community getById(Serializable id) {
        Community community = communityDao.getById(id);
        if (community != null) {
            if (community.getAreaId() != null) {
                community.setAreaName(dictDao.getNameById(community.getAreaId()));
            }
            if (community.getPlateId() != null) {
                community.setPlateName(dictDao.getNameById(community.getPlateId()));
            }
        }
        return super.getById(id);
    }
}
