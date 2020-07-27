package com.ming.dao;

import com.ming.pojo.Employee;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

@Repository
@org.apache.ibatis.annotations.Mapper
public interface EmployeeMapper extends Mapper<Employee> {
}
