package com.ming.service.impl;

import com.ming.dao.RoleMapper;
import com.ming.dao.UserRoleMapper;
import com.ming.pojo.Permission;
import com.ming.pojo.Role;
import com.ming.service.PermissionService;
import com.ming.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleMapper roleMapper;
    @Autowired
    private PermissionService permissionService;
    @Autowired
    private UserRoleMapper userRoleMapper;

    @Override
    public void addRole(Role role) {
        roleMapper.insertSelective(role);
    }

    @Override
    public void delRole(Integer roleId) {
        roleMapper.deleteByPrimaryKey(roleId);
    }

    @Override
    public void updateRole(Role role) {
        roleMapper.updateByPrimaryKeySelective(role);
    }

    @Override
    public Role findRole(Integer roleId) {
        Role role = roleMapper.selectByPrimaryKey(roleId);
        List<Permission> permissions = permissionService.findPermissionsByRoleId(roleId);
        role.setPermissions(permissions);
        return role;
    }

    @Override
    public List<Role> findRoles() {
        return roleMapper.selectAll();
    }

    /**
     * 查找用户包含的角色
     *
     * @param userId
     * @return
     */
    @Override
    public List<Role> findRoles(Integer userId) {
        List<Integer> roleIds = userRoleMapper.queryRoleIdsByUserId(userId);
        Example example1 =new Example(Role.class);
        example1.createCriteria().andIn("id",roleIds);
        return roleMapper.selectByExample(example1);
    }
}
