package com.ming.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import tk.mybatis.mapper.annotation.KeySql;
import tk.mybatis.mapper.code.IdentityDialect;

import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Transient;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Role {
    @Id
    @KeySql(dialect = IdentityDialect.MYSQL)
    private Long id;
    private String name;
    private String description;
    //@ManyToMany
    @Transient
    private List<Permission> permissions;
}
