package com.ming.service;

import com.ming.pojo.Employee;

public interface EmployeeService {

    void addEmployee(Employee employee);

    Employee findEmployee(Integer id);
}
