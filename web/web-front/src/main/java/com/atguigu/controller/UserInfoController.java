package com.atguigu.controller;

import com.alibaba.druid.util.StringUtils;
import com.alibaba.dubbo.config.annotation.Reference;
import com.atguigu.entity.UserInfo;
import com.atguigu.result.Result;
import com.atguigu.result.ResultCodeEnum;
import com.atguigu.service.UserInfoService;
import com.atguigu.util.MD5;
import com.atguigu.vo.LoginVo;
import com.atguigu.vo.RegisterVo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author ZhangHaoYu
 * @Date 2023/2/8 13:09
 * @Version 1.0
 */
@Controller
@RequestMapping("/userInfo")
public class UserInfoController {
    @Reference
    private UserInfoService userInfoService;

    /**
     * 检验数据，进行注册
     * @param registerVo
     * @param request
     * @return
     */
    @RequestMapping("/register")
    @ResponseBody
    public Result register(@RequestBody RegisterVo registerVo, HttpServletRequest request) {
        String phone = registerVo.getPhone();
        String nickName = registerVo.getNickName();
        String password = registerVo.getPassword();
        String code = registerVo.getCode();

        //校验参数
        if(StringUtils.isEmpty(nickName) ||
                StringUtils.isEmpty(phone) ||
                StringUtils.isEmpty(password) ||
                StringUtils.isEmpty(code)) {
            return Result.build(null, ResultCodeEnum.PARAM_ERROR);
        }
        // 加入验证码验证
        String currentCode = (String)request.getSession().getAttribute("CODE");
        if(!code.equals(currentCode)) {
            return Result.build(null, ResultCodeEnum.CODE_ERROR);
        }


        // 判断这个手机号是否已经注册过
        UserInfo userInfo = userInfoService.getByPhone(phone);
        if (userInfo != null) {
            return Result.build(null, ResultCodeEnum.PHONE_REGISTER_ERROR);
        }

        //

        userInfo = new UserInfo();
        userInfo.setNickName(nickName);
        userInfo.setPhone(phone);
        userInfo.setPassword(MD5.encrypt(password));
        userInfo.setStatus(1);
        userInfoService.insert(userInfo);

        return Result.ok();
    }

    /**
     * 发送验证码
     * @param moble
     * @param request
     * @return
     */
    @RequestMapping("/sendCode/{moble}")
    @ResponseBody
    public Result sendCode(@PathVariable String moble, HttpServletRequest request) {
        String code = "1111";
        request.getSession().setAttribute("CODE", code);
        return Result.ok(code);
    }

    /**
     * 检验数据，进行登录
     * @param loginVo
     * @param request
     * @return
     */
    @RequestMapping("/login")
    @ResponseBody
    public Result login(@RequestBody LoginVo loginVo, HttpServletRequest request) {
        String phone = loginVo.getPhone();
        String  password = loginVo.getPassword();

        // 检查参数是否为空
        if (StringUtils.isEmpty(password) || StringUtils.isEmpty(phone)) {
            return Result.build(null, ResultCodeEnum.PARAM_ERROR);
        }

        UserInfo userInfo = userInfoService.getByPhone(phone);
        // 检验是否存在该用户
        if (userInfo == null) {
            return Result.build(null, ResultCodeEnum.ACCOUNT_ERROR);
        }
        // 检验密码是否正确
        password = MD5.encrypt(password);
        if (!password.equals( userInfo.getPassword())) {
            return Result.build(null, ResultCodeEnum.PASSWORD_ERROR);
        }

        // 检验该账号是否被禁用
        if (userInfo.getStatus() == 0) {
            return Result.build(null, ResultCodeEnum.ACCOUNT_LOCK_ERROR);
        }

        // 登录用户放入session 中
        request.getSession().setAttribute("USER", userInfo);

        // 登录成功界面需要的用户数据
        Map<String, Object> map = new HashMap<>();
        map.put("phone", userInfo.getPhone());
        map.put("nickName", userInfo.getNickName());
        return Result.ok(map);
    }

    /**
     * 退出，并清除登录session
     * @param request
     * @return
     */
    @RequestMapping("/logout")
    @ResponseBody
    public Result logout(HttpServletRequest request) {
        request.getSession().removeAttribute("USER");
        return Result.ok();
    }
}
