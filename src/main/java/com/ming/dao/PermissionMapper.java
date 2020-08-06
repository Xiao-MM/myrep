package com.ming.dao;

import com.ming.pojo.Permission;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface PermissionMapper extends Mapper<Permission> {
    @Select({"<script>" +
            "select * from permission where " +
            "id in " +
            "<foreach item = 'permissionId' collection = 'permissionIds' open='(' separator=',' close=')'>" +
            "   #{permissionId}" +
            "</foreach>" +
            "</script>"})
    List<Permission> queryPermissionsInPermissionIds(List<Integer> permissionIds);
}
