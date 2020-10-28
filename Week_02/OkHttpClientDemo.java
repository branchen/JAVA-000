package com.company;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author bran.chen
 * @description
 */
public class OkHttpClientDemo {
    private Logger log = LoggerFactory.getLogger(this.getClass());

    public void requestContent(String url){
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder().url(url).build();
        try (Response response = client.newCall(request).execute()) {
            log.info(response.body().string());
        }catch(Exception e){
            log.error("发送到服务端出错:"+e);
        }
    }

    public static void main(String[] args){
        new OkHttpClientDemo().requestContent("http://localhost:8801/");
    }
}
