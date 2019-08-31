package com.kaisn.utils;

import com.kaisn.utils.http.HttpsUtil;
import org.apache.log4j.Logger;

import java.util.HashMap;
import java.util.Map;

public class HttpsTest {

    private static Logger logger = Logger.getLogger(HttpsTest.class);

    public static void main(String[] args) {
        String url="https://192.168.109.128:8443/emp-web/emp/list";
        Map<String, String> param = new HashMap<String, String>();
        param.put("page","1");
        param.put("limit","10");
        try {
            String s = HttpsUtil.doPost(url,null,param,null);
            logger.debug("=======https=======>"+s);
        } catch (Exception e) {
            logger.error("request fail.",e);
        }
    }
}
