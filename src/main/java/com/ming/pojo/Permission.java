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
public class Permission {
    /**
     * 标记已删除
     */
    public static Long EXIST = 0L;

    @Id
    @KeySql(dialect = IdentityDialect.MYSQL)
    private Long id;
    private String name;
    private String url;
    private String description;
    private String createTime;
    private Long deleted;
}
