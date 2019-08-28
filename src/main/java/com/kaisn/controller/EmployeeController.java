package com.kaisn.controller;

import com.kaisn.pojo.Employee;
import com.kaisn.pojo.Msg;
import com.kaisn.service.IEmployeeService;
import com.kaisn.utils.excel.ExcelUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;

@Controller
@RequestMapping("/emp")
public class EmployeeController {

    private static Logger logger = Logger.getLogger(EmployeeController.class);

    @Autowired
    IEmployeeService iEmployeeService;

    @ResponseBody
    @RequestMapping(value = "/list",method = RequestMethod.POST)
    public Msg getEmployeeList(HttpServletRequest request){
        List<Employee> employeeList = null;
        logger.debug("==========================");
        try {
            String empName = request.getParameter("empName");
            String page = request.getParameter("page");
            String limit = request.getParameter("limit");
            Employee employee = new Employee();
            employee.setEmpName(empName);
            HashMap<String, Object> param = new HashMap<String, Object>();
            param.put("empName",empName);
            param.put("offset",Integer.parseInt(page)-1);
            param.put("rows",Integer.parseInt(limit));
            employeeList = iEmployeeService.getEmployeeList(param);
        } catch (Exception e) {
            logger.error("获取列表失败！",e);
        }
        return Msg.success().add("list",employeeList);
    }

    @ResponseBody
    @RequestMapping(value = "/add",method = RequestMethod.POST)
    public Msg addEmployee(HttpServletRequest request){
        boolean isSuccess=false;
        try {
            //接收参数
            String empName = request.getParameter("empName");
            String gender = request.getParameter("gender");
            String birth = request.getParameter("birth");
            String email = request.getParameter("email");
            String descText = request.getParameter("descText");
            String address = request.getParameter("address");
            //封装对象
            Employee employee = new Employee();
            employee.setEmpName(empName);
            employee.setGender(gender);
            employee.setBirth(birth);
            employee.setEmail(email);
            employee.setDescText(descText);
            employee.setAddress(address);
            //添加数据
            isSuccess = iEmployeeService.addEmployee(employee);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Msg.success().add("result",isSuccess);
    }

    @ResponseBody
    @RequestMapping(value = "/del",method = RequestMethod.POST)
    public Msg delEmployee(HttpServletRequest request){
        boolean isSuccess=false;
        try {
            //接收参数
            String empId = request.getParameter("empId");
            //封装对象
            Employee employee = new Employee();
            employee.setEmpId(empId);
            //删除数据
            isSuccess = iEmployeeService.delEmployee(employee);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Msg.success().add("result",isSuccess);
    }

    @ResponseBody
    @RequestMapping(value = "/get",method = RequestMethod.POST)
    public Msg getEmployee(HttpServletRequest request){
        Employee employee=null;
        try {
            //接收参数
            String empId = request.getParameter("empId");
            //封装对象
            Employee param = new Employee();
            param.setEmpId(empId);
            //删除数据
            employee = iEmployeeService.getEmployee(param);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Msg.success().add("result",employee);
    }

    @ResponseBody
    @RequestMapping(value = "/upd",method = RequestMethod.POST)
    public Msg updEmployee(HttpServletRequest request){
        boolean isSuccess=false;
        try {
            //接收参数
            String empId = request.getParameter("empId");
            String empName = request.getParameter("empName");
            String gender = request.getParameter("gender");
            String birth = request.getParameter("birth");
            String email = request.getParameter("email");
            String descText = request.getParameter("descText");
            String address = request.getParameter("address");
            //封装对象
            Employee employee = new Employee();
            employee.setEmpId(empId);
            employee.setEmpName(empName);
            employee.setGender(gender);
            employee.setBirth(birth);
            employee.setEmail(email);
            employee.setDescText(descText);
            employee.setAddress(address);
            //删除数据
            isSuccess = iEmployeeService.updEmployee(employee);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Msg.success().add("result",isSuccess);
    }

    @ResponseBody
    @RequestMapping(value = "/export",method = RequestMethod.GET)
    public Msg exportEmployee(HttpServletRequest request, HttpServletResponse response){
        List<Employee> employeeList = null;
        try {
            // 这里设置的文件格式是application/x-excel
            response.setContentType("application/x-excel");
            response.setHeader("Content-Disposition",
                    "attachment;filename=" + new String("员工列表.xls".getBytes(), "ISO-8859-1"));
            ServletOutputStream outputStream = response.getOutputStream();

            employeeList = iEmployeeService.getEmployeeList(null);
            HashMap<String, Object> param = new HashMap<String, Object>();
            param.put("emps",employeeList);
            ExcelUtils.writeExcel(param,outputStream);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Msg.success().add("list",employeeList);
    }

}
