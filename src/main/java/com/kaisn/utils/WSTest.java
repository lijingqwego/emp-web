package com.kaisn.utils;

import com.alibaba.fastjson.JSON;
import com.kaisn.pojo.Employee;
import com.kaisn.service.IEmployeeService;
import com.kaisn.service.impl.EmployeeService;
import org.springframework.remoting.jaxws.JaxWsPortProxyFactoryBean;

import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import java.net.URL;
import java.util.List;

public class WSTest{
    public static void main(String[] args) throws Exception{

        //创建WSDL的URL，注意不是服务地址
        URL url = new URL("http://localhost:8080/emp-web/webservice/EmployeeService?wsdl");
        // 指定命名空间和服务名称
        QName qName = new QName("http://impl.service.kaisn.com/", "EmployeeService");
        Service service = Service.create(url, qName);
        // 通过getPort方法返回指定接口
        EmployeeService employeeService = service.getPort(EmployeeService.class);

        int listCount = employeeService.getEmployeeListCount(null);
        System.out.println("list size:"+listCount);
        // 调用方法 获取返回值
        Employee employee = new Employee();
        employee.setOffset(0);
        employee.setRows(listCount);
        List<Employee> employeeList = employeeService.getEmployeeList(employee);
        System.out.println("list data:"+JSON.toJSONString(employeeList));
    }

}