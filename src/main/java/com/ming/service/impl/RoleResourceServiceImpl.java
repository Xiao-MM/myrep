package com.ming.service.impl;

import com.ming.dao.RoleResourceMapper;
import com.ming.pojo.RoleResource;
import com.ming.service.RoleResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

@Service
@Transactional
public class RoleResourceServiceImpl implements RoleResourceService {

    @Autowired
    private RoleResourceMapper roleResourceMapper;

    /**
     * 给角色分配权限操作
     *
     * @param roleId
     * @param resourceId
     */
    @Override
    public void addResource(Integer roleId, Integer resourceId) {

        RoleResource roleResource = new RoleResource();
        roleResource.setRoleId(roleId);
        roleResource.setResourceId(resourceId);

        roleResourceMapper.insertSelective(roleResource);
    }

    /**
     * 撤销角色权限
     *
     * @param roleResourceId
     */
    @Override
    public void removeResource(Integer roleResourceId) {
        roleResourceMapper.deleteByPrimaryKey(roleResourceId);
    }

    /**
     * 查询角色拥有的操作id
     *
     * @param roleId
     * @return
     */
    @Override
    public List<Integer> findResourceIdsByRoleId(Integer roleId) {
        return roleResourceMapper.findResourceIdsByRoleId(roleId);
    }
}
