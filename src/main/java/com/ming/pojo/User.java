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
    /**
     * 标记已删除
     */
    public static Short DELETE = 1;
    @Id
    @KeySql(dialect = IdentityDialect.MYSQL)
    private Long id;
    private String username;
    private String password;
    private String passwordSalt;
    private String createTime;
    private Short deleted;
    @Transient
    private List<Role> roles;
}
