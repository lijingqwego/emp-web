package com.kaisn.dao;

import com.kaisn.pojo.Employee;

import java.util.HashMap;
import java.util.List;

public interface IEmployeeDao {

    List<Employee> getEmployeeList(HashMap<String, Object> param);

    int addEmployee(Employee employee);

    int delEmployee(Employee employee);

    Employee getEmployee(Employee employee);

    int updEmployee(Employee employee);
}
