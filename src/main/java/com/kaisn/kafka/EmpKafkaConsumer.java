package com.kaisn.kafka;

import java.util.Arrays;
import java.util.Properties;

import com.kaisn.utils.PropertiesUtil;
import net.sf.jsqlparser.statement.select.Top;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;


public class EmpKafkaConsumer {

    private final Consumer<String, String> consumer;

    public final static String TOPIC = "itsm-test";

    public EmpKafkaConsumer(){
        Properties props = PropertiesUtil.getProperties("src/main/resources/properties/consumer.properties",PropertiesUtil.defaultType);
        consumer = new KafkaConsumer<String,String>(props);
        consumer.subscribe(Arrays.asList(TopicContant.emp_topic));
    }

    void consume() {
        while(true){
            ConsumerRecords<String, String> records = consumer.poll(100);
            if (records.count() > 0) {
                for (ConsumerRecord<String, String> record : records) {
                    String message = record.value();
                    System.out.println("从kafka接收到的消息是：" + message);
                }
            }
        }

    }

    public static void main(String[] args){
        new EmpKafkaConsumer().consume();
    }
}
