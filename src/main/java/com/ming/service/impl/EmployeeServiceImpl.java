package com.ming.service.impl;

import com.ming.dao.EmployeeMapper;
import com.ming.pojo.Employee;
import com.ming.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    private EmployeeMapper employeeMapper;

    @Override
    public void addEmployee(Employee employee) {
        employeeMapper.insertSelective(employee);
    }

    @Override
    public Employee findEmployee(Integer id) {
        return employeeMapper.selectByPrimaryKey(id);
    }

}
