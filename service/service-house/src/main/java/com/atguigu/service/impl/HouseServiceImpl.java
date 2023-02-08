package com.atguigu.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.atguigu.base.BaseDao;
import com.atguigu.base.BaseServiceImpl;
import com.atguigu.dao.DictDao;
import com.atguigu.dao.HouseDao;
import com.atguigu.entity.Dict;
import com.atguigu.entity.House;
import com.atguigu.entity.HouseImage;
import com.atguigu.service.HouseImageService;
import com.atguigu.service.HouseService;
import com.atguigu.vo.HouseQueryVo;
import com.atguigu.vo.HouseVo;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.List;

/**
 * @Author ZhangHaoYu
 * @Date 2023/2/4 20:44
 * @Version 1.0
 */
@Service(interfaceClass = HouseService.class)
@Transactional
public class HouseServiceImpl extends BaseServiceImpl<House> implements HouseService {
    @Autowired
    private HouseDao houseDao;

    @Autowired
    private DictDao dictDao;

    @Autowired
    private  HouseImageService houseImageService;

    @Override
    protected BaseDao<House> getEntityDao() {
        return houseDao;
    }

    @Override
    public void publish(Long id, Integer status) {
        House house = new House();
        house.setId(id);
        house.setStatus(status);
        houseDao.update(house);
    }

    @Override
    public PageInfo<HouseVo> findListPage(Integer pageNum, Integer pageSize, HouseQueryVo houseQueryVo) {


        // 两个参数，第一个参数是页数。第二个参数是条数，每页查询的条数
        PageHelper.startPage(pageNum,pageSize);
        // 获取房源数据
        Page<HouseVo> page = houseDao.findListPage(houseQueryVo);
        List<HouseVo> list = page.getResult();
        for (HouseVo houseVo : list) {
            // 将HouseVo中id所对应的name复制
            houseVo.setHouseTypeName(dictDao.getNameById(houseVo.getHouseTypeId()));
            houseVo.setDirectionName(dictDao.getNameById(houseVo.getDirectionId()));
            houseVo.setFloorName(dictDao.getNameById(houseVo.getFloorId()));

            List<HouseImage> houseImage1List = houseImageService.findList(houseVo.getId(), 1);
            if (houseImage1List.size() != 0) {
                houseVo.setDefaultImageUrl(houseImage1List.get(0).getImageUrl());
            }
        }
        return new PageInfo<>(page, 10);
    }

    /**
     * 重写方法，从某字段id，set某字段的名字
     * @param id
     * @return
     */
    @Override
    public House getById(Serializable id) {
        House house = houseDao.getById(id);
        house.setHouseTypeName(dictDao.getNameById(house.getHouseTypeId()));
        house.setFloorName(dictDao.getNameById(house.getFloorId()));
        house.setBuildStructureName(dictDao.getNameById(house.getBuildStructureId()));
        house.setDirectionName(dictDao.getNameById(house.getDirectionId()));
        house.setDecorationName(dictDao.getNameById(house.getDecorationId()));
        house.setHouseUseName(dictDao.getNameById(house.getHouseUseId()));
        house.setFloorName(dictDao.getNameById(house.getFloorId()));
        return house;
    }


}
