package com._4paradigm.controller;//package com._4paradigm.controller;
//
//import com._4paradigm.entity.AckRequest;
//import com._4paradigm.entity.KafkaBaoDi;
//import com._4paradigm.entity.New_Standard_media_assets;
//import com._4paradigm.entity.Request;
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
//import org.apache.kafka.clients.producer.*;
//import org.apache.kafka.common.serialization.StringSerializer;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestMethod;
//import org.springframework.web.bind.annotation.RestController;
//
//import javax.servlet.http.HttpServletRequest;
//import java.io.IOException;
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//@RestController
//@RequestMapping("/APK_HomePage")
//public class AckRequestController_chuli {
//    @Value("${url}")
//    private String url;
//    private List<New_Standard_media_assets> huancun = new ArrayList<>();
//    private Long data = 1632722361810L;
//
//    @Value("${kaiguan}")
//    private String kaiguan;
//
//    @Value("${spring.kafka.bootstrap-servers}")
//    String bootstrapServers;
//    @Value("${spring.kafka.consumer.topic}")
//    String topic;
//
//    @Value("${ItemSetId}")
//    private String  ItemSetId;
//
//
//    @RequestMapping(value = "/getApkRequest", method = RequestMethod.GET)
//    public Request getAckRequest(AckRequest ackrequest, HttpServletRequest request222) {
//        List<New_Standard_media_assets> list = new ArrayList<>();
//        List<New_Standard_media_assets> list0 = new ArrayList<>();
//        New_Standard_media_assets new_standard_media_assets = new New_Standard_media_assets();
//
//
//        Request request = new Request();
//        try {
//            if (!ackrequest.getUserid().equals(null)) {
//                HashMap<String, Object> stringStringHashMap = new HashMap<>();
//
//                HashMap<String, Object> objectObjectHashMap1 = new HashMap<>();
//                objectObjectHashMap1.put("userId", ackrequest.getUserid());
//
//                Map<String, String> mapItemId = new HashMap<>();
//                mapItemId.put("itemId", "00600637000000010000000005627105");
//                List<Object> objects = new ArrayList<>();
//                objects.add(mapItemId);
//                objectObjectHashMap1.put("relatedItems", objects);
//
//                Map<String, String> mapItemId2 = new HashMap<>();
//                objectObjectHashMap1.put("debug", false);
//
//
//                stringStringHashMap.put("request", objectObjectHashMap1);
//
//                try {
//                    int count = 6;
//                    request.setCode(200);
//                    request.setMsg("OK");
//                    request.setStart(0);
//                    request.setCount(count);
//                    request.setI(200);
//
//                    //推荐接口返回数据
//                    String porthttp = porthttp(stringStringHashMap);
//                    System.out.println("porthttp = " + porthttp);
//                    Map<String, Object> map = (Map<String, Object>) JSONArray.parse(porthttp);
//
//                    List<Map<String, Object>> listdata = (List) map.get("data");
//                    request.setRequest_id((String) (listdata.get(0).get("traceId")));
//
//
//                    for (Map<String, Object> listmap : listdata) {
//                        if (kaiguan.equals("1")) {
//                            if (!listmap.keySet().contains("type1_url") || listmap.get("type1_url").equals("===")) {
//                                continue;
//                            }
//                        }
//                        String type1_url = (String) listmap.get("type1_url");
//                        System.out.println("type1_url = " + type1_url);
//                        String[] split = type1_url.split("\\.");
//                        if (!split[split.length - 1].equals("jpg")) {
//                            continue;
//                        }
//                        new_standard_media_assets.setCode((String) listmap.get("itemId"));
//                        new_standard_media_assets.setTitle((String) listmap.get("title"));
//                        new_standard_media_assets.setSeriestype("p");
//                        try {
//                            new_standard_media_assets.setPoster((String) listmap.get("type1_url"));
//                        } catch (Exception e) {
//                        }
//                        new_standard_media_assets.setCasts((String) listmap.get("casts"));
//                        new_standard_media_assets.setDirectors((String) listmap.get("directors"));
//                        new_standard_media_assets.setCompere((String) listmap.get("compere"));
//                        try {
//                            new_standard_media_assets.setRating((Double) listmap.get("rating"));
//                        } catch (Exception e) {
//                        }
//                        new_standard_media_assets.setSeriestype((String) listmap.get("seriestype"));
//                        new_standard_media_assets.setDescription((String) listmap.get("content"));
//                        //存储dynamicTags
//                        List<Map<String, Object>> list_map = new ArrayList<>();
//                        HashMap<String, Object> objectObjectHashMap = new HashMap<>();
//                        objectObjectHashMap.put("category", "tags");
//                        try {
//                            objectObjectHashMap.put("tag", ((String) listmap.get("tags")).split(","));
//                        } catch (Exception e) {
//                            objectObjectHashMap.put("tag", null);
//
//                        }
//                        HashMap<String, Object> objectObjectHashMap2 = new HashMap<>();
//                        objectObjectHashMap2.put("category", "pgmSndClass");
//                        try {
//                            objectObjectHashMap2.put("tag", ((String) listmap.get("pgmsndclass")).split(","));
//                        } catch (Exception e) {
//                            objectObjectHashMap2.put("tag", null);
//
//                        }
//                        HashMap<String, Object> objectObjectHashMap3 = new HashMap<>();
//                        objectObjectHashMap3.put("category", "PgmCategory");
//                        try {
//                            objectObjectHashMap3.put("tag", ((String) listmap.get("tag")).split(","));
//                        } catch (Exception e) {
//                            objectObjectHashMap3.put("tag", null);
//                        }
//                        list_map.add(objectObjectHashMap);
//                        list_map.add(objectObjectHashMap2);
//                        list_map.add(objectObjectHashMap3);
//
//                        new_standard_media_assets.setDynamicTags(list_map);
//                        list.add(new_standard_media_assets);
//
//                    }
//
//                    request.setSubject(list.subList(0, count < list.size() ? count : list.size()));
//
//
//
//                } catch (Exception e) {
//                    e.printStackTrace();
//                    CommonLogger.error(e.toString());
//                }
//            }
//        } catch (Exception e) {
//            CommonLogger.error("用户id不能为空");
//            request.setCode(400);
//            request.setMsg("用户id不能为空");
//            e.printStackTrace();
//        }
//        System.out.println("request = " + request);
//        List<New_Standard_media_assets> list2 = new ArrayList<>();
//        try {
//            if (request.getSubject().size() < 6) {
//                CommonLogger.error("这次请求返回的结果是走的兜底");
//                String getes = getes();
//                Map<String, Object> map = (Map<String, Object>) JSONArray.parse(getes);
//                Map<String, Object> hits = (Map<String, Object>) map.get("hits");
//                List<Map<String, Object>> hist2 = (List<Map<String, Object>>) hits.get("hits");
//                for (Map<String, Object> o : hist2) {
//                    new_standard_media_assets.setCode((String) o.get("_id"));
//                    Map<String, Object> source = (Map<String, Object>) o.get("_source");
//                    new_standard_media_assets.setTitle((String) source.get("title"));
//                    new_standard_media_assets.setDirectors((String) source.get("directors"));
//                    new_standard_media_assets.setCompere((String) source.get("compere"));
//                    new_standard_media_assets.setPoster((String) source.get("type1_url"));
//                    try {
//                        new_standard_media_assets.setRating((Double) source.get("rating"));
//                    } catch (Exception e) {
//                    }
//                    new_standard_media_assets.setSeriestype((String) source.get("seriestype"));
//                    new_standard_media_assets.setDescription((String) source.get("content"));
//                    //存储dynamicTags
//                    List<Map<String, Object>> list_map = new ArrayList<>();
//                    HashMap<String, Object> objectObjectHashMap = new HashMap<>();
//                    objectObjectHashMap.put("category", "tags");
//                    try {
//                        objectObjectHashMap.put("tag", ((String) source.get("tags")).split(","));
//                    } catch (Exception e) {
//                        objectObjectHashMap.put("tag", null);
//
//                    }
//                    HashMap<String, Object> objectObjectHashMap2 = new HashMap<>();
//                    objectObjectHashMap2.put("category", "pgmSndClass");
//                    try {
//                        objectObjectHashMap2.put("tag", ((String) source.get("pgmsndclass")).split(","));
//                    } catch (Exception e) {
//                        objectObjectHashMap2.put("tag", null);
//
//                    }
//                    HashMap<String, Object> objectObjectHashMap3 = new HashMap<>();
//                    objectObjectHashMap3.put("category", "PgmCategory");
//                    try {
//                        objectObjectHashMap3.put("tag", ((String) source.get("tag")).split(","));
//                    } catch (Exception e) {
//                        objectObjectHashMap3.put("tag", null);
//                    }
//                    list_map.add(objectObjectHashMap);
//                    list_map.add(objectObjectHashMap2);
//                    list_map.add(objectObjectHashMap3);
//
//                    new_standard_media_assets.setDynamicTags(list_map);
//                    list2.add(new_standard_media_assets);
//                    request.setSubject(list2);
//                }
//            }
//        } catch (Exception e) {
//
//            String getes = getes();
//            CommonLogger.error("这次请求返回的结果是走的兜底");
//            Map<String, Object> map = (Map<String, Object>) JSONArray.parse(getes);
//            Map<String, Object> hits = (Map<String, Object>) map.get("hits");
//            List<Map<String, Object>> hist2 = (List<Map<String, Object>>) hits.get("hits");
//            for (Map<String, Object> o : hist2) {
//                new_standard_media_assets.setCode((String) o.get("_id"));
//                Map<String, Object> source = (Map<String, Object>) o.get("_source");
//                new_standard_media_assets.setTitle((String) source.get("title"));
//                new_standard_media_assets.setDirectors((String) source.get("directors"));
//                new_standard_media_assets.setCompere((String) source.get("compere"));
//                new_standard_media_assets.setPoster((String) source.get("type1_url"));
//                try {
//                    new_standard_media_assets.setRating((Double) source.get("rating"));
//                } catch (Exception e6) {
//                }
//                new_standard_media_assets.setSeriestype((String) source.get("seriestype"));
//                new_standard_media_assets.setDescription((String) source.get("content"));
//                //存储dynamicTags
//                List<Map<String, Object>> list_map = new ArrayList<>();
//                HashMap<String, Object> objectObjectHashMap = new HashMap<>();
//                objectObjectHashMap.put("category", "tags");
//                try {
//                    objectObjectHashMap.put("tag", ((String) source.get("tags")).split(","));
//                } catch (Exception e1) {
//                    objectObjectHashMap.put("tag", null);
//
//                }
//                HashMap<String, Object> objectObjectHashMap2 = new HashMap<>();
//                objectObjectHashMap2.put("category", "pgmSndClass");
//                try {
//                    objectObjectHashMap2.put("tag", ((String) source.get("pgmsndclass")).split(","));
//                } catch (Exception e2) {
//                    objectObjectHashMap2.put("tag", null);
//
//                }
//                HashMap<String, Object> objectObjectHashMap3 = new HashMap<>();
//                objectObjectHashMap3.put("category", "PgmCategory");
//                try {
//                    objectObjectHashMap3.put("tag", ((String) source.get("tag")).split(","));
//                } catch (Exception e3) {
//                    objectObjectHashMap3.put("tag", null);
//                }
//                list_map.add(objectObjectHashMap);
//                list_map.add(objectObjectHashMap2);
//                list_map.add(objectObjectHashMap3);
//
//                new_standard_media_assets.setDynamicTags(list_map);
//                list2.add(new_standard_media_assets);
//                request.setSubject(list2);
//            }
//
//
//        }
//        CommonLogger.info(request.toString());
//        return request;
//    }
//
//
//    /**
//     * 向cess发请求
//     * @param request
//     * @return
//     */
//
//    public String porthttp(HashMap<String, Object> request) {
//        System.out.println("request = " + request);
//        //相应内容长度
//        String responseEntityString = null;
//        Object obj = JSONArray.toJSON(request);
//        String jsonObject = obj.toString();
//        System.out.println("jsonObject = " + jsonObject);
//        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
//        HttpPost httpPost = new HttpPost(url);
//        httpPost.setHeader("Content-Type", "application/json;charset=utf-8");
//        httpPost.setHeader("X-CESS-Data-Token", "0bf6ff0c10d641c99d519abbeec6ed35");
//
//        StringEntity entity = new StringEntity(jsonObject, "UTF-8");
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
//                //System.out.println("响应内容为:" + EntityUtils.toString(responseEntity));
//                responseEntityString = EntityUtils.toString(responseEntity);
//
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
//        return responseEntityString;
//    }
//
//
//    /**
//     * 保底请求es
//     * @return
//     */
//    public String getes(){
//        String url="http://172.25.3.110:9200/business_3_itemset_211_item/_search?_source=title,content,seriestype,type1_url,casts,dicectors,compere,rating,seriestype,tags,pgmsndclass,tag&pretty";
//        String responseEntityString=null;
//        String json ="{ \"from\": 0, \"size\": 6, \"query\": { \"match\": {\"biao2\":\"电影\"} } }";
//        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
//        HttpPost httpPost = new HttpPost(url);
//        httpPost.setHeader("Content-Type", "application/json;charset=utf-8");
//        StringEntity stringEntity = new StringEntity(json, "UTF-8");
//        httpPost.setEntity(stringEntity);
//
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
//                //System.out.println("响应内容为:" + EntityUtils.toString(responseEntity));
//                responseEntityString=EntityUtils.toString(responseEntity);
//
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
//        return responseEntityString;
//
//    }
//
//    /**
//     *
//     */
//    public void SendKafka(String userid,String sceneId,String traceId){
//        KafkaBaoDi kafkaBaoDi = new KafkaBaoDi();
//        kafkaBaoDi.setUserId(userid);
//        kafkaBaoDi.setSceneId(sceneId);
//        kafkaBaoDi.setTraceId(traceId);
//        Object o = JSONArray.toJSON(kafkaBaoDi);
//        String contentviewlogjson = o.toString();
//
//
//        Map<String, Object> configs = new HashMap<>();
//        configs.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,bootstrapServers);
//        configs.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
//        configs.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
//
//        KafkaProducer<String,String> producer = new KafkaProducer<>(configs);
//        producer.send(new ProducerRecord<>(topic, contentviewlogjson), new Callback() {
//            @Override
//            public void onCompletion(RecordMetadata metadata, Exception exception) {
//                if(exception == null){
//                }
//            }
//        });
//        producer.close();
//
//
//
//    }
//
//
//}
//
//
