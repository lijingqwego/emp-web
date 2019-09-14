package com.kaisn.mq;


import com.alibaba.fastjson.JSONObject;
import com.kaisn.dao.IEmployeeDao;
import com.kaisn.pojo.Employee;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

@Component
public class ConsumerMessageListener implements MessageListener {

    private static Logger logger = Logger.getLogger(ConsumerMessageListener.class);

    @Autowired
    private IEmployeeDao iEmployeeDao;

    public void onMessage(Message message) {
        //这里我们知道生产者发送的就是一个纯文本消息，所以这里可以直接进行强制转换，或者直接把onMessage方法的参数改成Message的子类TextMessage
        TextMessage textMsg = (TextMessage) message;
        logger.info("接收到一个纯文本消息。");
        try {
            String text = textMsg.getText();
            JSONObject jsonObject = JSONObject.parseObject(text);
            String operateType = jsonObject.getString("operateType");
            if ("add".equals(operateType)) {
                JSONObject data = jsonObject.getJSONObject("data");
                Employee employee = getEmployeeFromJSON(data);
                iEmployeeDao.addEmployee(employee);
            } else if ("upd".equals(operateType)) {
                JSONObject data = jsonObject.getJSONObject("data");
                Employee employee = getEmployeeFromJSON(data);
                iEmployeeDao.updEmployee(employee);
            } else if ("del".equals(operateType)) {
                JSONObject data = jsonObject.getJSONObject("data");
                Employee employee = getEmployeeFromJSON(data);
                iEmployeeDao.updEmployee(employee);
            } else {

            }
            logger.info("消息内容是：" + textMsg.getText());
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }

    private Employee getEmployeeFromJSON(JSONObject data) {
        Employee employee = new Employee();

        if(data.containsKey("empId")){
            String empId = data.getString("empId");
            employee.setEmpId(empId);
        }
        if(data.containsKey("empName")){
            String empName = data.getString("empName");
            employee.setEmpName(empName);
        }
        if(data.containsKey("gender")){
            String gender = data.getString("gender");
            employee.setGender(gender);
        }
        if(data.containsKey("email")){
            String email = data.getString("email");
            employee.setEmail(email);
        }
        if(data.containsKey("birth")){
            String birth = data.getString("birth");
            employee.setBirth(birth);
        }
        if(data.containsKey("address")){
            String address = data.getString("address");
            employee.setAddress(address);
        }
        if(data.containsKey("descText")){
            String descText = data.getString("descText");
            employee.setDescText(descText);
        }
        return employee;
    }
}