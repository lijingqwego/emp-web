package com.kaisn.druid;

import com.alibaba.fastjson.JSONObject;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class IngestionData {

    public static void main(String[] args) throws IOException {
        ingestionTestData();
    }

    private static void ingestionTestData() throws IOException {
        String[] phone={"1365784","1360084","13653448","1365214","1772265","1773565","1772405","1776635","1521704","1521704","1521707","1521742"};
        String[] neType={"CSCF","MTELAS","SCCAS","PSBC"};
        String[] chrType={"37","38","39","53"};
        String[] errorCode={"100000","100001","100002","100003","100004","100005","100006","100007","100008","100009","1000010","100011","100012","100013","100014","100015"};
        String[] sipCode={"500","501","502","503","504","505","506","507","508","509","510","511","512","513","514","515"};

        String[] r2 = {"10","11","12","13"};
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Calendar calendar = Calendar.getInstance();
        FileWriter fileWriter = new FileWriter("/home/lijing/桌面/ingestion_data.json");
        BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
        for (int i = 1; i < 1001; i++) {
            calendar.add(Calendar.MINUTE,-15);
            long longTime = calendar.getTimeInMillis();
            String dateTime = dateFormat.format(longTime);
            int n = (int)(Math.random()*4);
            int m = (int)(Math.random()*errorCode.length);
            int a = (int)(Math.random()*sipCode.length);
            int b = (int)(Math.random()*phone.length);
            int ra2 = (int)(Math.random()*r2.length);
            int random4 = (int)((Math.random()*9+1)*1000);

            JSONObject jsonObject = new JSONObject();
            jsonObject.put("productFamily","IMS");
            jsonObject.put("dateTime",dateTime);
            jsonObject.put("productTime",longTime+"");
            jsonObject.put("splitTime",dateTime.substring(11));
            jsonObject.put("time",longTime+"");
            jsonObject.put("CHR Type",chrType[n]);
            jsonObject.put("CHRVersion","64");

            jsonObject.put("Release Node Type","ATS");
            jsonObject.put("neType",neType[n]);

            jsonObject.put("neName","NJ"+neType[n]+r2[ra2]+"BHW");
            String neId = 2000000+i+"";
            jsonObject.put("neId",neId.substring(1));
            jsonObject.put("neVersion","V200R006");

            jsonObject.put("Caller IMPU",phone[b]+random4);
            jsonObject.put("Callee IMPU",phone[b]+random4);
            jsonObject.put("Internal Error Code",errorCode[m]);
            jsonObject.put("Sip Status Code",sipCode[a]);

            bufferedWriter.write(jsonObject.toJSONString());
            bufferedWriter.newLine();
            bufferedWriter.flush();
        }
        bufferedWriter.close();
        fileWriter.close();
    }
}
