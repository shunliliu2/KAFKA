package com._4paradigm.kafka;

import com._4paradigm.entity.Contentviewlog;
import com._4paradigm.entity.Contentviewlog_er;
import com._4paradigm.entity.resultlog;
import com._4paradigm.log.CommonLogger;
import com.alibaba.fastjson.JSONArray;
import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.producer.*;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.KafkaListeners;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;

@Component
public class Consumerttt2 {
    @Value("${SceneId}")
    private String SceneId;
    @Value("${ItemSetId}")
    private String  ItemSetId;
    @Value("${actionurl}")
    private String actionurl;
    @Value("${spring.kafka.bootstrap-servers}")
    String bootstrapServers;


    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");



    @KafkaListeners({@KafkaListener(topics="cess-result-13")})

    //@KafkaListeners({@KafkaListener(topics="topicfff")})
    public void listen(ConsumerRecord<Integer, String> consumerRecord) throws ParseException {
        sdf.setTimeZone(TimeZone.getTimeZone("GMT+8"));
        resultlog resultlog = new resultlog();
        String value = consumerRecord.value();
        resultlog.setContent(value);

        System.out.println("resultlog = " + resultlog);
        Object o = JSONArray.toJSON(resultlog);
        String contentviewlogjson = o.toString();
        CommonLogger.info(contentviewlogjson);

        Map<String, Object> configs = new HashMap<>();
        configs.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,bootstrapServers);
        configs.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        configs.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);

        KafkaProducer<String,String> producer = new KafkaProducer<>(configs);
        producer.send(new ProducerRecord<>("topic2", contentviewlogjson), new Callback() {
            @Override
            public void onCompletion(RecordMetadata metadata, Exception exception) {
                if(exception == null){
                }
            }
        });
        producer.close();


    }

    public void gethttp(String json){
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
        HttpPost httpPost = new HttpPost(actionurl);
        httpPost.setHeader("Content-Type", "application/json;charset=utf-8");
        httpPost.setHeader("X-CESS-Data-Token", "092e4b7ea9914553a02f901769df45b8");

        StringEntity entity = new StringEntity(json, "UTF-8");
        httpPost.setEntity(entity);
        // 响应模型
        CloseableHttpResponse response = null;
        try {
            // 由客户端执行(发送)Post请求
            response = httpClient.execute(httpPost);
            // 从响应模型中获取响应实体
            HttpEntity responseEntity = response.getEntity();

            System.out.println("响应状态为:" + response.getStatusLine());
            if (responseEntity != null) {
                System.out.println("响应内容长度为:" + responseEntity.getContentLength());
                System.out.println("响应内容为:" + EntityUtils.toString(responseEntity));
            }
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                // 释放资源
                if (httpClient != null) {
                    httpClient.close();
                }
                if (response != null) {
                    response.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
