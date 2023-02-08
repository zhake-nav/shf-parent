package com.atguigu.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.atguigu.base.BaseDao;
import com.atguigu.base.BaseServiceImpl;
import com.atguigu.dao.DictDao;
import com.atguigu.entity.Dict;
import com.atguigu.entity.Role;
import com.atguigu.result.Result;
import com.atguigu.service.DictService;
import com.atguigu.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author ZhangHaoYu
 * @Date 2023/2/4 11:16
 * @Version 1.0
 */

@Service(interfaceClass = DictService.class)
@Transactional
public class DictServiceImpl extends BaseServiceImpl<Dict> implements DictService {
    @Autowired
    private DictDao dictDao;

    @Override
    public List<Map<String, Object>> findZnodes(Long id) {
        // 返回数据[{ id:2, isParent:true, name:"随意勾选 2"}]
        //根据id获取子节点数据
        List<Dict> listByParentId = dictDao.findListByParentId(id);
        // 构建zTree数据
        List<Map<String, Object>> zNodes = new ArrayList<>();
        //判断该节点是否是父节点
        for (Dict dict : listByParentId) {
            Integer count = dictDao.countIsParent(dict.getId());
            HashMap<String, Object> map = new HashMap<>();
            map.put("id",dict.getId());
            map.put("isParent", count > 0);
            map.put("name", dict.getName());
            zNodes.add(map);
        }
        //全部权限列表
        return zNodes;
    }

    @Override
    public List<Dict> findListByParentId(Long parentId) {
        return dictDao.findListByParentId(parentId);
    }

    @Override
    public List<Dict> findListByDictCode(String dictCode) {
        Dict dict = dictDao.getByDictCode(dictCode);
        if(null == dict) return null;
        return this.findListByParentId(dict.getId());
    }

    @Override
    protected BaseDao<Dict> getEntityDao() {
        return dictDao;
    }
}
