package com.kaisn.service;

import com.kaisn.pojo.Employee;

import java.util.List;

public interface IEmployeeService {

    List<Employee> getEmployeeList(Employee param);

    boolean addEmployee(Employee employee);

    boolean delEmployee(Employee employee);

    Employee getEmployee(Employee employee);

    boolean updEmployee(Employee employee);

    int getEmployeeListCount(Employee employee);
}
