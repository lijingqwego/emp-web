package com.kaisn.kafka;

import com.kaisn.utils.StringUtils;
import com.kaisn.utils.http.HttpUtils;
import org.apache.commons.io.IOUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

public class KafkaSupervisor {

    private static final String supervisor_url = "http://192.168.109.128:8090/druid/indexer/v1/supervisor";

    private static final String resources_url = "src/main/resources";

    public static String createDataSource(String dataSourceName,String topic){
        String ingestionSepc = loadTemplate();
        String result = null;
        try {
            if(StringUtils.isNotBlack(ingestionSepc)){
                ingestionSepc = ingestionSepc.replaceAll("%%dataSource%%", dataSourceName)
                        .replaceAll("%%topic%%", topic);
            }
            result = HttpUtils.postJson(supervisor_url, ingestionSepc);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    private static String loadTemplate(){
        BufferedReader bufferedReader = null;
        String line = null;
        StringBuilder buffer = new StringBuilder();
        try {
            File file = new File(resources_url+"/druid/kafka-supervisor.json");
            bufferedReader = new BufferedReader(new FileReader(file));
            while ((line = bufferedReader.readLine()) != null){
                buffer.append(line);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            IOUtils.closeQuietly(bufferedReader);
        }
        return buffer.toString();
    }

    public static void main(String[] args) {
        String result = createDataSource("kaisn_a01", TopicContant.emp_topic);
        System.out.println(result);
    }
}
