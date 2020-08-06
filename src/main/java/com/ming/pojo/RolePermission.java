package com.ming.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import tk.mybatis.mapper.annotation.KeySql;
import tk.mybatis.mapper.code.IdentityDialect;

import javax.persistence.Id;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class RolePermission {
    @Id
    @KeySql(dialect = IdentityDialect.MYSQL)
    private Integer id;
    private Integer roleId;
    private Integer permissionId;
}
