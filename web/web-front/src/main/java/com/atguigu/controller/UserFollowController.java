package com.atguigu.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.atguigu.entity.UserFollow;
import com.atguigu.entity.UserInfo;
import com.atguigu.result.Result;
import com.atguigu.service.UserFollowService;
import com.atguigu.vo.UserFollowVo;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @Author ZhangHaoYu
 * @Date 2023/2/9 16:12
 * @Version 1.0
 */


@RestController
@RequestMapping("/userFollow")
public class UserFollowController {
    @Reference
    private UserFollowService userFollowService;

    /**
     * 关注操作
     * @param houseId
     * @param request
     * @return
     */
    @RequestMapping("/auth/follow/{houseId}")
    public Result follow(@PathVariable Long houseId, HttpServletRequest request) {
        // -------拦截器执行后（所有的带有“auth”的url都会进拦截器）---------
        UserInfo userInfo = (UserInfo) request.getSession().getAttribute("USER");
        Long userId = userInfo.getId();
        userFollowService.follow(userId, houseId);
        return Result.ok();
    }

    @RequestMapping("/auth/list/{pageNum}/{pageSize}")
    public Result findListPage(@PathVariable Integer pageNum, @PathVariable Integer pageSize, HttpServletRequest request) {
        UserInfo userInfo = (UserInfo) request.getSession().getAttribute("USER");  // 不用担心空指针，经过拦截器时已登录状态了
        PageInfo<UserFollowVo> pageInfo =  userFollowService.findListPage(pageNum, pageSize, userInfo.getId());
        return Result.ok(pageInfo);
    }
    /**
     * 取消关注
     * @param id 是user——follow的表id，根据表id漏极删除关注
     * @param request
     * @return
     */
    @GetMapping("/auth/cancelFollow/{id}")
    public Result cancelFollow(@PathVariable Long id, HttpServletRequest request) {
        userFollowService.cancelFollow(id);
        return Result.ok();
    }
}
