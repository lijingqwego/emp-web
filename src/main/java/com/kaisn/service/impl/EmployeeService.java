package com.kaisn.service.impl;

import com.kaisn.dao.IEmployeeDao;
import com.kaisn.pojo.Employee;
import com.kaisn.service.IEmployeeService;
import com.kaisn.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

//import javax.jws.WebService;
import java.util.List;

@Service(value = "employeeService")
//@WebService(serviceName = "EmployeeService")
public class EmployeeService implements IEmployeeService {

    @Autowired
    private IEmployeeDao iEmployeeDao;

    public List<Employee> getEmployeeList(Employee param) {
        List<Employee> employeeList = iEmployeeDao.getEmployeeList(param);
        for (Employee employee:employeeList) {
            String birth = employee.getBirth();
            if(StringUtils.isNotBlack(birth))
            {
                employee.setBirth(StringUtils.subString(birth,10));
            }
        }
        return employeeList;
    }

    public boolean addEmployee(Employee employee) {
        int total = iEmployeeDao.addEmployee(employee);
        return total > 0 ? true : false;
    }

    public boolean delEmployee(Employee employee) {
        int total = iEmployeeDao.delEmployee(employee);
        return total > 0 ? true : false;
    }

    public Employee getEmployee(Employee employee) {
        return iEmployeeDao.getEmployee(employee);
    }

    public boolean updEmployee(Employee employee) {
        int total = iEmployeeDao.updEmployee(employee);
        return total > 0 ? true : false;
    }

    public int getEmployeeListCount(Employee employee) {

        return iEmployeeDao.getEmployeeListCount(employee);
    }
}
