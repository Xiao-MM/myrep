package com.ming.service.impl;

import com.ming.dao.PermissionMapper;
import com.ming.dao.RolePermissionMapper;
import com.ming.dto.PermissionDTO;
import com.ming.dto.PermissionFindDTO;
import com.ming.exception.ExceptionManager;
import com.ming.pojo.Permission;
import com.ming.service.PermissionService;
import com.ming.utils.PageUtil;
import com.ming.utils.TimeUtil;
import com.ming.vo.PageVO;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class PermissionServiceImpl implements PermissionService {

    @Resource
    private PermissionMapper permissionMapper;
    @Resource
    private ExceptionManager exceptionManager;

    @Override
    public boolean isPermissionExist(Long permissionId) {
        Permission permission = permissionMapper.selectByPrimaryKey(permissionId);
        return permission != null && !permission.getDeleted().equals(Permission.DELETE);
    }

    @Override
    public void addPermission(PermissionDTO permissionDTO) {
        Permission permission = new Permission();
        BeanUtils.copyProperties(permissionDTO,permission);
        permission.setCreateTime(TimeUtil.getCurrentTime());
        permissionMapper.insertSelective(permission);
    }

    @Override
    public void delPermission(Long permissionId) {
        if (!this.isPermissionExist(permissionId)){
           throw  exceptionManager.create("EC02000");
        }
        Permission permission = new Permission();
        permission.setId(permissionId);
        permission.setDeleted(Permission.DELETE);
        permissionMapper.updateByPrimaryKeySelective(permission);
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

    /**
     * 查找所有权限
     *
     * @return
     */
    @Override
    public List<Permission> findAllPermissions() {
        return permissionMapper.selectAll();
    }

    /**
     * 搜索权限
     * @param permissionFindDTO
     * @return
     */
    @Override
    public PageVO<Permission> findPermissions(PermissionFindDTO permissionFindDTO) {
        PageUtil.startPage(permissionFindDTO.getPageDTO());
        Example example = new Example(Permission.class);
        Example.Criteria criteria = example.createCriteria();
        if (!StringUtils.isEmpty(permissionFindDTO.getName())){
            criteria.orLike("name","%"+permissionFindDTO.getName()+"%");
        }
        if (!StringUtils.isEmpty(permissionFindDTO.getUrl())){
            criteria.orLike("url","%"+permissionFindDTO.getUrl()+"%");
        }
        return new PageVO<>(permissionMapper.selectByExample(example));
    }

    @Override
    public List<Permission> findPermissionsByRoleId(Long roleId) {
        return permissionMapper.findPermissionsByRoleId(roleId);
    }


}
