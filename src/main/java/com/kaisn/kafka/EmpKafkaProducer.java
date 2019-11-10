package com.kaisn.kafka;

import com.alibaba.fastjson.JSON;
import com.kaisn.pojo.Employee;
import com.kaisn.utils.PropertiesUtil;
import com.kaisn.utils.excel.ExcelReader;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Properties;

public class EmpKafkaProducer {

    private final KafkaProducer<String, String> producer;

    public final static String TOPIC = "wikipedia";

    private EmpKafkaProducer() {

        Properties props = null;
        props = PropertiesUtil.getProperties("src/main/resources/properties/producer.properties",PropertiesUtil.defaultType);
        //props = PropertiesLoaderUtils.loadAllProperties("producer.properties");

        producer = new KafkaProducer<String, String>(props);
    }

    public void produce() {
        try {
            ExcelReader excelReader = new ExcelReader();
            String fileName="src/main/resources/员工列表.xlsx";
            List<List<String>> dataList = excelReader.readExcel(fileName, 1);
            dataList.remove(0);
            dataList.remove(0);
            SimpleDateFormat timestampFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
            int i=0;
            for (List<String> list : dataList) {
                Employee employee = new Employee();
                employee.setEmpId(list.get(0));
                employee.setEmpName(list.get(1));
                employee.setGender(list.get(2));
                employee.setBirth(list.get(3));
                employee.setEmail(list.get(4));
                employee.setDescText(list.get(5));
                employee.setAddress(list.get(6));
                employee.setTimestamp(timestampFormat.format(new Date().getTime()+i));
                producer.send(new ProducerRecord<String, String>(TopicContant.emp_topic, JSON.toJSONString(employee)));
                i++;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            producer.close();
        }
    }

    public static void main(String[] args) {
        new EmpKafkaProducer().produce();
    }
}