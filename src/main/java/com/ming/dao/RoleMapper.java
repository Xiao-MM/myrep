package com.ming.dao;

import com.ming.pojo.Role;
import org.apache.ibatis.annotations.Many;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.mapping.FetchType;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface RoleMapper extends Mapper<Role> {

    @Select("select * from role where id = #{roleId} and deleted = 0")
    //查询完角色后延迟查询该角色的所有权限
    @Results(id = "rolePermissionMap",value = {
            @Result(id=true,column = "id",property = "id"),
            @Result(column = "name",property = "name"),
            @Result(column = "description",property = "description"),
            @Result(column = "create_time",property = "createTime"),
            @Result(column = "deleted",property = "deleted"),
            @Result(property = "permissions",column = "id",
                    many = @Many(select = "com.ming.dao.PermissionMapper.findPermissionsByRoleId",
                            fetchType = FetchType.LAZY))
    })
    Role findRoleById(Long roleId);

    List<Role> findAllRoles();

    @Select("select r.* from role r inner join user_role ur on r.id = ur.role_id where ur.user_id = #{userId} and r.deleted = 0")
    List<Role> findRolesByUserId(Long userId);
}
