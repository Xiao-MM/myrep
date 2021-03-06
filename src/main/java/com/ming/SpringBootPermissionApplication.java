package com.ming;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.PropertySource;
import tk.mybatis.spring.annotation.MapperScan;

@SpringBootApplication
@EnableCaching//开启缓存
@MapperScan(value = {"com.ming.dao"})
@PropertySource(value = "classpath:exception.properties",encoding = "utf-8")
public class SpringBootPermissionApplication {
    public static void main(String[] args) {
        SpringApplication.run(SpringBootPermissionApplication.class, args);
    }

}
