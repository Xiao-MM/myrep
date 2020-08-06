package com.ming.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import tk.mybatis.mapper.annotation.KeySql;
import tk.mybatis.mapper.code.IdentityDialect;

import javax.persistence.Id;
import javax.persistence.Transient;
import java.io.Serializable;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class User implements Serializable {
    @Id
    @KeySql(dialect = IdentityDialect.MYSQL)
    private Integer id;
    private String username;
    private String password;
    @Transient
    private List<Role> roles;
}
