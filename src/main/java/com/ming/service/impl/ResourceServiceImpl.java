package com.ming.service.impl;

import com.ming.dao.ResourceMapper;
import com.ming.dao.RoleResourceMapper;
import com.ming.pojo.Resource;
import com.ming.service.ResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ResourceServiceImpl implements ResourceService {

    @Autowired
    private ResourceMapper resourceMapper;
    @Autowired
    private RoleResourceMapper roleResourceMapper;

    @Override
    public void addResource(Resource resource) {
        resourceMapper.insertSelective(resource);
    }

    @Override
    public void delResource(Integer resourceId) {
        resourceMapper.deleteByPrimaryKey(resourceId);
    }

    @Override
    public void updateResource(Resource resource) {
        resourceMapper.updateByPrimaryKeySelective(resource);
    }

    @Override
    public Resource findResource(Integer resourceId) {
        return resourceMapper.selectByPrimaryKey(resourceId);
    }

    @Override
    public List<Resource> findResources() {
        return resourceMapper.selectAll();
    }

    @Override
    public List<Resource> findResourcesByRoleId(Integer roleId) {
        //查找出该角色拥有的所有权限的id
        List<Integer> resourceIds = roleResourceMapper.findResourceIdsByRoleId(roleId);
        //根据这些id查询出所有的权限信息

        return null;
    }


}
