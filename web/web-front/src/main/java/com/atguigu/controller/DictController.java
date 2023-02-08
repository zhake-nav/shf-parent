package com.atguigu.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.atguigu.entity.Dict;
import com.atguigu.result.Result;
import com.atguigu.service.DictService;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * @Author ZhangHaoYu
 * @Date 2023/2/7 10:46
 * @Version 1.0
 */
@Controller
@RequestMapping("/dict")
public class DictController {
    @Reference
    DictService dictService;

    @RequestMapping("/findListByDictCode/{dictCode}")
    @ResponseBody
    public Result findListByDictCode(@PathVariable("dictCode") String dictCode) {
        List<Dict> listByDictCode = dictService.findListByDictCode(dictCode);
        return Result.ok(listByDictCode);
    }

    @RequestMapping("/findListByParentId/{id}")
    @ResponseBody
    public Result findListByParentId(@PathVariable Long id) {
        List<Dict> list = dictService.findListByParentId(id);
        return Result.ok(list);
    }
}
