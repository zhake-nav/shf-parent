package com.atguigu.service;

import com.atguigu.base.BaseService;
import com.atguigu.entity.Dict;

import java.util.List;
import java.util.Map;

/**
 * @Author ZhangHaoYu
 * @Date 2023/2/4 11:14
 * @Version 1.0
 */
public interface DictService extends BaseService<Dict> {
    List<Map<String, Object>> findZnodes(Long id);

    /**
     * 根据上级id获取子节点数据列表
     * @param parentId
     * @return
     */
    List<Dict> findListByParentId(Long parentId);

    /**
     * 根据编码获取子节点数据列表
     * @param dictCode
     * @return
     */
    List<Dict> findListByDictCode(String dictCode);

    /**
     * 根据字典id获取字典名
     * @param id
     * @return
     */
    String getNameById(Long id);
}
