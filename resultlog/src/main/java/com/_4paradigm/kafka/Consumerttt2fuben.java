package com._4paradigm.kafka;//package com._4paradigm.kafka;
//
//import com._4paradigm.entity.Contentviewlog_er;
//import com._4paradigm.log.CommonLogger;
//import com.alibaba.fastjson.JSONArray;
//import org.apache.http.HttpEntity;
//import org.apache.http.client.ClientProtocolException;
//import org.apache.http.client.methods.CloseableHttpResponse;
//import org.apache.http.client.methods.HttpPost;
//import org.apache.http.entity.StringEntity;
//import org.apache.http.impl.client.CloseableHttpClient;
//import org.apache.http.impl.client.HttpClientBuilder;
//import org.apache.http.util.EntityUtils;
//import org.apache.kafka.clients.consumer.ConsumerRecord;
//import org.apache.kafka.clients.producer.*;
//import org.apache.kafka.common.serialization.StringSerializer;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.kafka.annotation.KafkaListener;
//import org.springframework.kafka.annotation.KafkaListeners;
//import org.springframework.stereotype.Component;
//
//import java.io.IOException;
//import java.text.ParseException;
//import java.text.SimpleDateFormat;
//import java.util.Date;
//import java.util.HashMap;
//import java.util.Map;
//
//@Component
//public class Consumerttt2fuben {
//    @Value("${SceneId}")
//    private String SceneId;
//    @Value("${ItemSetId}")
//    private String  ItemSetId;
//    @Value("${actionurl}")
//    private String actionurl;
//    String bootstrapServers="172.25.3.113:9097,172.25.3.114:9097,172.25.3.115:9097";
//
//    String topic="actionUT";
//
//
//
//    private static Map<String,String> map=new HashMap<>();
//   private static KafkaProducer<String,String> producer;
//
//
//    {
//    Map<String, Object> configs = new HashMap<>();
//    configs.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,bootstrapServers);
//    configs.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
//    configs.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
//
//    producer = new KafkaProducer<>(configs);
//}
//    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//    //@KafkaListeners({@KafkaListener(topics="topicttt"), @KafkaListener(topics="topica"),@KafkaListener(topics="topicb")})
//
//    @KafkaListeners({@KafkaListener(topics="cdr_viewLog"),@KafkaListener(topics="cdr_viewLoge"),@KafkaListener(topics="cdr_exEventLog"),@KafkaListener(topics="cdr_orderLog")})
//    //@KafkaListeners({@KafkaListener(topics="topicttt")})
//    public void listen(ConsumerRecord<Integer, String> consumerRecord) {
//         Contentviewlog_er contentviewlog_er = new Contentviewlog_er();
//        System.out.println("consumerRecord.value() = " + consumerRecord.value());
//        String value = consumerRecord.value();
//        String[] split = value.split(",", -1);
//
//            try {
//                if (split[1].equals("v")) {
//                    try {
//
//                        if (!map.get(split[3]).equals(null)) {
//
//                            System.out.println("split = " + split.length);
//                            String old = map.get(split[3]);
//                            String newdata = split[5];
//                            Date parse = sdf.parse(old);
//                            Date parse1 = sdf.parse(newdata);
//
//
//                            long l = (parse1.getTime() - parse.getTime());
//                            if (l > 300000 || l < 300000) {
//                                contentviewlog_er.setActionTime(Long.getLong(split[5]));
//                                contentviewlog_er.setSceneId(SceneId);//场景id
//                                contentviewlog_er.setItemSetId(Integer.parseInt(ItemSetId));//物料id
//                                contentviewlog_er.setAction("duration");//行为名称
//                                contentviewlog_er.setUserId(split[3]);
//                                contentviewlog_er.setReferType(split[31]);
//                                contentviewlog_er.setAreaCode(split[2]);
//                                contentviewlog_er.setItemId(split[4]);
//                                contentviewlog_er.setAreaCode(split[16]);
//                                contentviewlog_er.setPath("");
//                                contentviewlog_er.setActionTime(System.currentTimeMillis());
//                                try {
//                                    if (split[21].equals(33)) {
//                                        contentviewlog_er.setTraceId(split[39]);
//                                    }
//                                    contentviewlog_er.setTraceId(split[39]);
//                                } catch (Exception e) {
//                                }
//
//                                Date kaishi = null;
//                                Date jieshu = null;
//                                try {
//                                    kaishi = sdf.parse(split[5]);
//                                    jieshu = sdf.parse(split[6]);
//                                } catch (ParseException parseException) {
//                                    parseException.printStackTrace();
//                                }
//                                long ll = (jieshu.getTime() - kaishi.getTime()) / 1000;
//                                System.out.println("kaishi = " + kaishi.getTime());
//                                System.out.println("jieshu = " + jieshu.getTime());
//                                contentviewlog_er.setCounty(split[7]);
//
//                                try {
//                                    String zong = split[22];
//                                    Date zongshichang = sdf.parse(zong);
//                                    long zongs = zongshichang.getTime();
//                                    Long zongs1 = ll / zongs;
//                                    System.out.println("zongs1 = " + zongs1);
//
//                                    contentviewlog_er.setDuration_percentage(zongs1 + "");
//                                } catch (ParseException e) {
//                                }
//                                map.remove(split[3]);
//                            } else {
//
//                            }
//                        }
//                    } catch (Exception e) {
//                        contentviewlog_er.setActionTime(Long.getLong(split[5]));
//                        contentviewlog_er.setSceneId(SceneId);//场景id
//                        contentviewlog_er.setItemSetId(Integer.parseInt(ItemSetId));//物料id
//                        contentviewlog_er.setAction("detailPageShow");//行为名称
//                        contentviewlog_er.setUserId(split[3]);
//                        contentviewlog_er.setReferType(split[31]);
//                        contentviewlog_er.setAreaCode(split[2]);
//                        contentviewlog_er.setItemId(split[4]);
//                        contentviewlog_er.setAreaCode(split[16]);
//                        contentviewlog_er.setPath("");
//                        contentviewlog_er.setActionTime(System.currentTimeMillis());
//                        contentviewlog_er.setCounty(split[7]);
//                        try {
//                            if (split[21].equals(33)) {
//                                contentviewlog_er.setTraceId(split[39]);
//                            }
//                            contentviewlog_er.setTraceId(split[39]);
//
//                        } catch (Exception exception) {
//
//                        }
//                        map.put(split[3], split[5]);
//
//                    }
//
//
//                } else if (split[1].equals("external_event")) {//曝光
//                    String[] split1 = split[4].split("~");
//                    String[] split2 = split[20].split("-");
//
//
//                    for (String s : split1) {
//                        System.out.println(split[9]);
//                        contentviewlog_er.setActionTime(Long.parseLong(split[9] + "000"));
//                        contentviewlog_er.setSceneId(SceneId);//场景id
//                        contentviewlog_er.setItemSetId(Integer.parseInt(ItemSetId));//物料id
//                        contentviewlog_er.setAction("show");//行为名称
//                        contentviewlog_er.setUserId(split[3]);
//                        contentviewlog_er.setStbIp(split[8]);
//                        contentviewlog_er.setStbId(split[10]);
//                        contentviewlog_er.setStbType(split[5]);
//                        contentviewlog_er.setAreaCode(split[2]);
//                        contentviewlog_er.setItemId(s);
//                        contentviewlog_er.setAreaCode(split[14]);
//                        contentviewlog_er.setPath("");
//                        try {
//                            contentviewlog_er.setTraceId(split2[0]+'-'+s);
//                        } catch (Exception e) {
//
//                        }
//                        Object o = JSONArray.toJSON(contentviewlog_er);
//                        String contentviewlogjson = o.toString();
//                        CommonLogger.info(contentviewlogjson);
//                        producer.send(new ProducerRecord<>(topic, contentviewlogjson), new Callback() {
//                            @Override
//                            public void onCompletion(RecordMetadata metadata, Exception exception) {
//                                if(exception == null){
//                                }
//                            }
//                        });
//
//
//                        //gethttp(contentviewlogjson);
//                        contentviewlog_er = new Contentviewlog_er();
//
//                    }
//                    contentviewlog_er = new Contentviewlog_er();
//
//
//                } else if (split[1].equals("order")) {
//                    if (split[21].equals(0)) {
//                        contentviewlog_er.setActionTime(Long.getLong(split[5]));
//                        contentviewlog_er.setSceneId(SceneId);//场景id
//                        contentviewlog_er.setItemSetId(Integer.parseInt(ItemSetId));//物料id
//                        contentviewlog_er.setAction("subscribe");//行为名称
//                        contentviewlog_er.setUserId(split[3]);
//                        contentviewlog_er.setReferType(split[35]);
//                        contentviewlog_er.setAreaCode(split[2]);
//                        contentviewlog_er.setCounty(split[23]);
//                        contentviewlog_er.setItemId(split[4]);
//                        contentviewlog_er.setProductType(Integer.parseInt(split[16]));
//                        if (split[16].equals(1)) {
//                            contentviewlog_er.setProductId(split[4]);
//                        } else {
//                            contentviewlog_er.setProductId(split[13]);
//                        }
//                        contentviewlog_er.setActionTime(sdf.parse(split[19]).getTime());
//                        contentviewlog_er.setValidTime(split[17]);
//                        contentviewlog_er.setExpiredTime(split[18]);
//                        contentviewlog_er.setOrderContinue(Integer.parseInt(split[22]));
//                        contentviewlog_er.setAreaCode(split[16]);
//                        try {
//                            if (split[23].equals(33)) {
//                                contentviewlog_er.setTraceId(split[43]);
//                            }
//                            contentviewlog_er.setTraceId(split[43]);
//                        } catch (Exception e) {
//
//                        }
//
//                    } else {
//                        contentviewlog_er.setActionTime(Long.getLong(split[5]));
//                        contentviewlog_er.setSceneId(SceneId);//场景id
//                        contentviewlog_er.setItemSetId(Integer.parseInt(ItemSetId));//物料id
//                        contentviewlog_er.setAction("unsubscribe");//行为名称
//                        contentviewlog_er.setUserId(split[3]);
//                        contentviewlog_er.setReferType(split[35]);
//                        contentviewlog_er.setAreaCode(split[2]);
//                        contentviewlog_er.setCounty(split[23]);
//                        contentviewlog_er.setItemId(split[4]);
//                        contentviewlog_er.setProductType(Integer.parseInt(split[16]));
//                        if (split[16].equals(1)) {
//                            contentviewlog_er.setProductId(split[4]);
//                        } else {
//                            contentviewlog_er.setProductId(split[13]);
//                        }
//                        contentviewlog_er.setActionTime(sdf.parse(split[19]).getTime());
//                        contentviewlog_er.setValidTime(split[17]);
//                        contentviewlog_er.setExpiredTime(split[18]);
//                        contentviewlog_er.setOrderContinue(Integer.parseInt(split[22]));
//                        contentviewlog_er.setAreaCode(split[16]);
//                        try {
//                            if (split[23].equals(33)) {
//                                contentviewlog_er.setTraceId(split[43]);
//                            }
//                            contentviewlog_er.setTraceId(split[43]);
//                        } catch (Exception e) {
//
//                        }
//
//                    }
//                }
//            } catch (Exception e) {
//
//                e.printStackTrace();
//            }
//
//
//
//        System.out.println("contentviewlog = " + contentviewlog_er);
//        Object o = JSONArray.toJSON(contentviewlog_er);
//        String contentviewlogjson = o.toString();
//        CommonLogger.info(contentviewlogjson);
//       // gethttp(contentviewlogjson);
//        producer.send(new ProducerRecord<>(topic, contentviewlogjson), new Callback() {
//            @Override
//            public void onCompletion(RecordMetadata metadata, Exception exception) {
//                if(exception == null){
//                }
//            }
//        });
//
//
//
//
//
//    }
//    //action
//
//    public void gethttp(String json){
//        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
//        HttpPost httpPost = new HttpPost(actionurl);
//        httpPost.setHeader("Content-Type", "application/json;charset=utf-8");
//        httpPost.setHeader("X-CESS-Data-Token", "0bf6ff0c10d641c99d519abbeec6ed35");
//
//        StringEntity entity = new StringEntity(json, "UTF-8");
//        httpPost.setEntity(entity);
//        // 响应模型
//        CloseableHttpResponse response = null;
//        try {
//            // 由客户端执行(发送)Post请求
//            response = httpClient.execute(httpPost);
//            // 从响应模型中获取响应实体
//            HttpEntity responseEntity = response.getEntity();
//
//            System.out.println("响应状态为:" + response.getStatusLine());
//            if (responseEntity != null) {
//                System.out.println("响应内容长度为:" + responseEntity.getContentLength());
//                System.out.println("响应内容为:" + EntityUtils.toString(responseEntity));
//            }
//        } catch (ClientProtocolException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        } finally {
//            try {
//                // 释放资源
//                if (httpClient != null) {
//                    httpClient.close();
//                }
//                if (response != null) {
//                    response.close();
//                }
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//    }
//}
