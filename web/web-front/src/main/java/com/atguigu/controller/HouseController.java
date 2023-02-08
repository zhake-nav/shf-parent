package com.atguigu.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.atguigu.entity.Community;
import com.atguigu.entity.House;
import com.atguigu.entity.HouseBroker;
import com.atguigu.entity.HouseImage;
import com.atguigu.result.Result;
import com.atguigu.service.CommunityService;
import com.atguigu.service.HouseBrokerService;
import com.atguigu.service.HouseImageService;
import com.atguigu.service.HouseService;
import com.atguigu.vo.HouseQueryVo;
import com.atguigu.vo.HouseVo;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author ZhangHaoYu
 * @Date 2023/2/7 12:12
 * @Version 1.0
 */
@Controller
@RequestMapping("/house")
public class HouseController {
    @Reference
    private HouseService houseService;
    @Reference
    private HouseImageService houseImageService;
    @Reference
    private CommunityService communityService;
    @Reference
    private HouseBrokerService houseBrokerService;

    /**
     * 房源基本信息列表
     * @param houseQueryVo
     * @param pageNum
     * @param pageSize
     * @return
     */
    @RequestMapping("/list/{pageNum}/{pageSize}")
    @ResponseBody
    public Result list(@RequestBody HouseQueryVo houseQueryVo,
                       @PathVariable Integer pageNum,
                       @PathVariable Integer pageSize) {
        PageInfo<HouseVo> pageInfo = houseService.findListPage(pageNum, pageSize, houseQueryVo);
        return Result.ok(pageInfo);
    }


    @GetMapping("/info/{id}")
    @ResponseBody
    public Result info(@PathVariable Long id) {
        // 获取房源图片 用户info界面的图片展示
        List<HouseImage> houseImage1List = houseImageService.findList(id, 1);
        // 获取房子信息
        House house = houseService.getById(id);
        // 获取小区信息
        Community community = communityService.getById(house.getCommunityId());
        // 获取经纪人信息
        List<HouseBroker> houseBrokerList = houseBrokerService.findListByHouseId(id);

        Map<String, Object> map = new HashMap<>();
        map.put("house",house);
        map.put("community",community);
        map.put("houseBrokerList",houseBrokerList);
        map.put("houseImage1List",houseImage1List);
        // TODO 后续完成关注功能
        map.put("isFollow", false);
        return Result.ok(map);
    }
}
