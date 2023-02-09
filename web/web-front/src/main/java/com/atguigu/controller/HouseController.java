package com.atguigu.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.atguigu.entity.*;
import com.atguigu.result.Result;
import com.atguigu.service.*;
import com.atguigu.vo.HouseQueryVo;
import com.atguigu.vo.HouseVo;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
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
    @Reference
    private UserFollowService userFollowService;

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


    /**
     * 房子基本信息
     * @param id houseId
     * @return
     */
    @GetMapping("/info/{id}")
    @ResponseBody
    public Result info(@PathVariable Long id, HttpServletRequest request) {
        // 获取房源图片 用户info界面的图片展示
        List<HouseImage> houseImage1List = houseImageService.findList(id, 1);
        // 获取房子信息
        House house = houseService.getById(id);
        // 获取小区信息
        Community community = communityService.getById(house.getCommunityId());
        // 获取经纪人信息
        List<HouseBroker> houseBrokerList = houseBrokerService.findListByHouseId(id);

        UserInfo userInfo = (UserInfo) request.getSession().getAttribute("USER");
        Boolean flag = false;
        if (userInfo != null) {
            flag = userFollowService.isFollowed(userInfo.getId(), id);

        }

        Map<String, Object> map = new HashMap<>();
        map.put("house",house);
        map.put("community",community);
        map.put("houseBrokerList",houseBrokerList);
        map.put("houseImage1List",houseImage1List);
        map.put("isFollow", flag);
        return Result.ok(map);
    }
}
