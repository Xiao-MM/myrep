package com.ming.service.impl;

import com.ming.dao.PermissionMapper;
import com.ming.dao.RolePermissionMapper;
import com.ming.exception.ExceptionManager;
import com.ming.pojo.Permission;
import com.ming.service.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.ArrayList;
import java.util.List;

@Service
public class PermissionServiceImpl implements PermissionService {

    @Autowired
    private PermissionMapper permissionMapper;
    @Autowired
    private RolePermissionMapper rolePermissionMapper;
    @Autowired
    private ExceptionManager exceptionManager;

    @Override
    public boolean isPermissionExist(Long permissionId) {
        return permissionMapper.existsWithPrimaryKey(permissionId);
    }

    @Override
    public void addPermission(Permission permission) {
        permissionMapper.insertSelective(permission);
    }

    @Override
    public void delPermission(Long permissionId) {
        if (!this.isPermissionExist(permissionId)){
           throw  exceptionManager.create("EC02000");
        }
        permissionMapper.deleteByPrimaryKey(permissionId);
    }

    @Override
    public void updatePermission(Permission permission) {
        if (!this.isPermissionExist(permission.getId())){
            throw  exceptionManager.create("EC02000");
        }
        permissionMapper.updateByPrimaryKeySelective(permission);
    }

    @Override
    public Permission findPermission(Long permissionId) {
        return permissionMapper.selectByPrimaryKey(permissionId);
    }

    @Override
    public List<Permission> findPermissions() {
        return permissionMapper.selectAll();
    }

    @Override
    public List<Permission> findPermissions(Long roleId) {
        //查找出该角色拥有的所有权限的id
        List<Long> permissionIds = rolePermissionMapper.queryPermissionIdsByRoleId(roleId);
        if (permissionIds==null||permissionIds.size()==0){
            return new ArrayList<>();
        }
        //根据这些id查询出所有的权限信息
        Example example = new Example(Permission.class);
        example.createCriteria().andIn("id",permissionIds);
        return permissionMapper.selectByExample(example);
    }


}
