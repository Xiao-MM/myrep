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
public class UserRole {
    @Id
    @KeySql(dialect = IdentityDialect.MYSQL)
    private Long id;
    private Long userId;
    private Long roleId;
}
