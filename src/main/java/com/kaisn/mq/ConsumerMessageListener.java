package com.kaisn.mq;


import com.alibaba.fastjson.JSONObject;
import com.kaisn.dao.IEmployeeDao;
import com.kaisn.pojo.Employee;
import com.kaisn.service.impl.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

@Component
public class ConsumerMessageListener implements MessageListener {

    @Autowired
    private IEmployeeDao iEmployeeDao;

    public void onMessage(Message message) {
        //这里我们知道生产者发送的就是一个纯文本消息，所以这里可以直接进行强制转换，或者直接把onMessage方法的参数改成Message的子类TextMessage
        TextMessage textMsg = (TextMessage) message;
        System.out.println("接收到一个纯文本消息。");
        try {
            String text = textMsg.getText();
            JSONObject jsonObject = JSONObject.parseObject(text);
            String operateType = jsonObject.getString("operateType");
            if ("add".equals(operateType)) {
                JSONObject data = jsonObject.getJSONObject("data");
                Employee employee = getEmployeeFromJSON(data);
                iEmployeeDao.addEmployee(employee);
            } else if ("upd".equals(operateType)) {

            } else {

            }
            System.out.println("消息内容是：" + textMsg.getText());
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }

    private Employee getEmployeeFromJSON(JSONObject data) {
        Employee employee = new Employee();
        String empName = data.getString("empName");
        String gender = data.getString("gender");
        String email = data.getString("email");
        String birth = data.getString("birth");
        String address = data.getString("address");
        String descText = data.getString("descText");
        employee.setEmpName(empName);
        employee.setGender(gender);
        employee.setEmail(email);
        employee.setBirth(birth);
        employee.setAddress(address);
        employee.setDescText(descText);
        return employee;
    }
}