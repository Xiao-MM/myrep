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

import java.util.ArrayList;
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
    public void delRole(Long roleId) {
        roleMapper.deleteByPrimaryKey(roleId);
    }

    @Override
    public void updateRole(Role role) {
        roleMapper.updateByPrimaryKeySelective(role);
    }

    @Override
    public Role findRole(Long roleId) {
        Role role = roleMapper.selectByPrimaryKey(roleId);
        List<Permission> permissions = permissionService.findPermissions(roleId);
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
    public List<Role> findRoles(Long userId) {
        List<Long> roleIds = userRoleMapper.queryRoleIdsByUserId(userId);
        //如果为空就不查了
        if (roleIds==null||roleIds.size()==0){
            return new ArrayList<>();
        }
        Example example =new Example(Role.class);
        example.createCriteria().andIn("id",roleIds);
        return roleMapper.selectByExample(example);
    }
}
