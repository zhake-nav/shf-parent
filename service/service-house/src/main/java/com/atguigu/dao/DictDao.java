package com.atguigu.dao;

import com.atguigu.base.BaseDao;
import com.atguigu.entity.Community;
import com.atguigu.entity.Dict;

import java.util.List;

/**
 * @Author ZhangHaoYu
 * @Date 2023/2/4 11:19
 * @Version 1.0
 */
public interface DictDao extends BaseDao<Dict> {
    /**
     * 根据父节点的id查询它所有的子节点
     * @param parentId
     * @return
     */
    List<Dict> findListByParentId(Long parentId);

    /**
     * 统计是否有子节点（0：不是父节点， else：是父节点）判断该节点是否是父节点
     * @param id
     * @return
     */
    Integer countIsParent(Long id);

    String getNameById(Long id);

    Dict getByDictCode(String dictCode);
}
