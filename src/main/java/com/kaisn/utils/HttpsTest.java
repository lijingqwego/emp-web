package com.kaisn.utils;

import com.kaisn.druid.ResourceReaderUtil;
import com.kaisn.utils.http.HttpUtils;
import com.kaisn.utils.http.HttpsUtil;
import org.apache.log4j.Logger;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class HttpsTest {

    private static Logger logger = Logger.getLogger(HttpsTest.class);

    public static void main(String[] args) {
        //testPush();
        //testQuery();
        testSend();
    }

    public static void testSend(){
        String url="http://localhost:8080/emp-web/emp/sendEmail";
        Map<String, String> param = new HashMap<String, String>();
        //收件人邮箱
        String toEmailAddress = "2623463347@qq.com";
        //邮件主题
        String emailTitle = "Druid查询结构";
        //邮件内容
        String values = ResourceReaderUtil.loadData("druid/mysql-query-values.json");
        param.put("toEmailAddress",toEmailAddress);
        param.put("emailTitle",emailTitle);
        param.put("emailContent",values);
        try {
            String s = HttpUtils.postParameters(url,param);
            System.out.println(s);
        } catch (Exception e) {
            logger.error("request fail.",e);
        }
    }

    public static void testPush(){
        String url="http://localhost:8080/emp-web/emp/push";
        File file = new File("/home/lijjing/桌面/ingestion_data.json");
        Map<String, String> params =new HashMap<>();
        params.put("tableName","ims_chr");
        String s = HttpUtils.postFile(url, params, file);
        System.out.println(s);
    }

    public static void testQuery(){
        String url="http://localhost:8080/emp-web/emp/query";
        Map<String, String> param = new HashMap<String, String>();
        String values = ResourceReaderUtil.loadData("druid/mysql-query-values.json");
        param.put("values",values);
        try {
            String s = HttpUtils.postParameters(url,param);
            System.out.println(s);
//            String s = HttpsUtil.doPost(url,null,null,null);
            logger.debug("=======https=======>"+s);
        } catch (Exception e) {
            logger.error("request fail.",e);
        }
    }
}
