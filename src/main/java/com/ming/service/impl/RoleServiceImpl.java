package com.ming.service.impl;

import com.ming.dao.RoleMapper;
import com.ming.dao.RolePermissionMapper;
import com.ming.dto.RoleDTO;
import com.ming.exception.ExceptionManager;
import com.ming.pojo.Permission;
import com.ming.pojo.Role;
import com.ming.service.PermissionService;
import com.ming.service.RoleService;
import com.ming.utils.TimeUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {

    @Resource
    private RoleMapper roleMapper;
    @Resource
    private RolePermissionMapper rolePermissionMapper;
    @Resource
    private PermissionService permissionService;
    @Resource
    private ExceptionManager exceptionManager;

    /**
     * 判断角色是否存在
     *
     * @param roleId
     * @return
     */
    @Override
    public boolean isRoleExist(Long roleId) {
        Role role = roleMapper.selectByPrimaryKey(roleId);
        return role != null && role.getDeleted().equals(Role.EXIST);
    }

    /**
     * 添加角色
     * @param roleDTO
     */
    @Override
    public void addRole(RoleDTO roleDTO) {
        Role role = new Role();
        BeanUtils.copyProperties(roleDTO,role);
        role.setCreateTime(TimeUtil.getCurrentTime());
        roleMapper.insertSelective(role);
    }

    /**
     * 删除角色
     * @param roleId
     */
    @Override
    public void deleteRole(Long roleId) {
        if (!this.isRoleExist(roleId)){
            throw exceptionManager.create("EC03000");
        }
        Role role = new Role();
        role.setId(roleId);
        role.setDeleted(System.currentTimeMillis());
        roleMapper.updateByPrimaryKeySelective(role);
        rolePermissionMapper.deleteRolePermissionsByRoleId(roleId);
    }

    /**
     * 修改角色
     * @param role
     */
    @Override
    public void updateRole(Role role) {
        if (!this.isRoleExist(role.getId())){
            throw exceptionManager.create("EC03000");
        }
        roleMapper.updateByPrimaryKeySelective(role);
    }

    /**
     * 查找角色
     * @param roleId
     * @return
     */
    @Override
    public Role findRole(Long roleId) {
        return roleMapper.findRoleById(roleId);
    }

    /**
     * 查找所有角色
     * @return
     */
    @Override
    public List<Role> findRoles() {
        return roleMapper.findAllRoles();
    }

    /**
     * 查找用户包含的角色
     *
     * @param userId
     * @return
     */
    @Override
    public List<Role> findRolesById(Long userId) {
        return roleMapper.findRolesByUserId(userId);
    }
}
