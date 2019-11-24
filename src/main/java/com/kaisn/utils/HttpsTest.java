package com.kaisn.utils;

import com.kaisn.utils.http.HttpUtils;
import com.kaisn.utils.http.HttpsUtil;
import org.apache.log4j.Logger;

import java.util.HashMap;
import java.util.Map;

public class HttpsTest {

    private static Logger logger = Logger.getLogger(HttpsTest.class);

    public static void main(String[] args) {
        String url="http://localhost:8080/emp-web/emp/query";
        Map<String, String> param = new HashMap<String, String>();
        param.put("tableName","t_employee");
        param.put("column","emp_name");
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
