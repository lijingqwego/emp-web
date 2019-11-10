package com.kaisn.utils;

import com.alibaba.fastjson.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

public class JsonUtils {

    private static final int HOUR_LONG = 1234 * 34 *111 * 24;

    private  static final String[] names = {"王丰春(P512023)","陆建荣(P510032)","陈世均(P970139)","陈江平(P103357)","余成长(P511034)","陈晓伟(P515001)","翁巍(P510014)","薛飞(P511045)","遆文新(P511046)","薛飞(P511045)","费克勋(P511049)","王荣山(P106407)","赵建仓(P516020)","吴树辉(P100835)","王淦刚(P100833)","李建勇(P516021)","张锦飞(P514008)","上官志洪(P512018)","陈洋(P103342)","陈超峰(P124745)","张启明(P102536)","夏子世(P930118)","金心明(P514001)","陶于春(P970107)","聂沈斌(P920179)","童忠贵(P511011)","唐西明(P201118)","杨哲(P960213)","陈徐坤(P512001)","那福利(P512013)","王强(P132117)","李书周(P960076)","冯伟岗(P950166)","王君(P970215)","袁亮(P102273)","涂丰盛(P513015)","卢文跃(P870149)","陈捷飞(P980152)","圣国龙(P108227)","陈宇(P980137)","瞿勐(P980225)","张文中(P511005)","刘立(P512049)","邹平国(P101739)","杨文彬(P100887)"};
    public static void toJsonFile(){

        SimpleDateFormat timestampFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        SimpleDateFormat birthFormat = new SimpleDateFormat("yyyy-MM-dd");
        Calendar instance = Calendar.getInstance();
        instance.set(1992,1,1);
        long time = new Date().getTime();
        for (int i = 0; i < 45; i++) {
            instance.add(Calendar.DAY_OF_MONTH,23);
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("id",i+5);
            jsonObject.put("name",names[i]);
            jsonObject.put("timestamp",timestampFormat.format(  time+ HOUR_LONG+i));
            jsonObject.put("birthday",birthFormat.format(instance.getTime()));
            jsonObject.put("gender",i % 5 == 0 ? "男":"女");
            jsonObject.put("address",i % 6 == 0 ? "江西省上饶市余干县":"广西省百色田阳县");
            jsonObject.put("ismarry",i % 4 == 0 ? true:false);
            jsonObject.put("degree",i % 3 == 0 ? "本科":"大专");
            jsonObject.put("company","东莞易宝软件");
            jsonObject.put("remark",""+i+ Math.random());
            System.out.println(jsonObject.toJSONString());
        }


    }

    public static void main(String[] args) {
        toJsonFile();
    }
}
