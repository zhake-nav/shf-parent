package com.atguigu.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.atguigu.entity.Dict;
import com.atguigu.entity.Role;
import com.atguigu.result.Result;
import com.atguigu.service.DictService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @Author ZhangHaoYu
 * @Date 2023/2/4 11:42
 * @Version 1.0
 */
@Controller
@RequestMapping("/dict")
@SuppressWarnings({"rawtypes"})
public class DictController {
    private final static String PAGE_INDEX = "dict/index";

    @Reference
    DictService dictService;

    @GetMapping
    public String index() {
        return  PAGE_INDEX;
    }

    /**
     * 根据id返回数字字典数据
     * @param id
     * @return
     */
    @GetMapping(value = "findZnodes")
    @ResponseBody
    public Result findByParentId(@RequestParam(value = "id", defaultValue = "0") Long id) {
        List<Map<String, Object>> znodes = dictService.findZnodes(id);
        return Result.ok(znodes);
    }

    /**
     * 根据上级id获取子节点数据列表
     * @param id
     * @return
     */
    @GetMapping(value = "findListByParentId/{id}")
    @ResponseBody
    public Result<List<Dict>> findListByParentId(@PathVariable Long id) {
        List<Dict> list = dictService.findListByParentId(id);
        return Result.ok(list);
    }

    /**
     * 根据编码获取子节点数据列表
     * @param dictCode
     * @return
     */
    @GetMapping(value = "findListByDictCode/{dictCode}")
    @ResponseBody
    public Result<List<Dict>> findListByDictCode(@PathVariable String dictCode) {
        List<Dict> list = dictService.findListByDictCode(dictCode);
        return Result.ok(list);
    }


}
