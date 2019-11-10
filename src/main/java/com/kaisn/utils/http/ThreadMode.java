package com.kaisn.utils.http;

public class ThreadMode {
    public Thread getThread() {
        Thread thread = new Thread(new Runnable() {
            public void run() {
                for (int i = 0; i < 10; i++) {
                    String str="{\"id\": 10,\"name\": \"符爱桃\",\"timestamp\": \"2019-06-27T01:01:55.103Z\",\"birthday\": \"1994-01-24\",\"gender\": \"女\",\"address\": \"浙江省温州市\",\"ismarry\": false,\"degree\": \"本科\",\"company\": \"东莞易宝软件\",\"remark\": \"前后端开发，新来的员工\"}";
                    try {
                        String postJson = HttpUtils.postJson("http://192.168.109.128:8200/v1/post/netcare-volte", str);
                        System.err.println(postJson);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        return thread;
    }

    public static void main(String[] args) {
        // 线程数量
        int threadmax = 10;
        for (int i = 0; i < threadmax; i++) {
            ThreadMode thread = new ThreadMode();
            thread.getThread().start();
        }
    }
}
