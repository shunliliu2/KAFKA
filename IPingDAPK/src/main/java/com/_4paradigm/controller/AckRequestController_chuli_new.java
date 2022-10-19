package com._4paradigm.controller;

import com._4paradigm.entity.AckRequest;
import com._4paradigm.entity.KafkaBaoDi;
import com._4paradigm.entity.New_Standard_media_assets;
import com._4paradigm.entity.Request;
import com._4paradigm.log.CommonLogger;
import com._4paradigm.mapper.CodeMapper;
import com.alibaba.fastjson.JSONArray;
import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.apache.kafka.clients.producer.*;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.*;

@RestController
@RequestMapping("/APK")
public class AckRequestController_chuli_new {
    @Value("${url}")
    private String url;
    @Value("${url2}")
    private String url2;
    private List<New_Standard_media_assets> huancun = new ArrayList<>();
    private Long data = 1632722361810L;

    @Value("${kaiguan}")
    private String kaiguan;

    @Value("${spring.kafka.bootstrap-servers}")
    String bootstrapServers;
    @Value("${spring.kafka.consumer.topic}")
    String topic;

    @Value("${SceneId}")
    private String SceneId;
    @Value("${ItemSetId}")
    private String  ItemSetId;

    private CodeMapper codeMapper=new CodeMapper();



    @RequestMapping(value = "/IChannel", method = RequestMethod.GET)
    public Request getAckRequest(AckRequest ackrequest) {
        String interestTag = ackrequest.getInterestTag();
        String decode = null;
        try {
            decode = URLDecoder.decode(interestTag, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            decode="";
        }
        List<New_Standard_media_assets> list = new ArrayList<>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        sdf.setTimeZone(TimeZone.getTimeZone("GMT+8"));
        codeMapper.insert(ackrequest.getUserId(),sdf.format(System.currentTimeMillis()),decode);
        Request request = new Request();
        try {
            if (!ackrequest.getUserId().equals(null)) {
                System.out.println("ackrequest = " + ackrequest);

                String sss="";
                HashMap<String, Object> stringStringHashMap = new HashMap<>();

                HashMap<String, Object> objectObjectHashMap1 = new HashMap<>();
                objectObjectHashMap1.put("userId", ackrequest.getUserId());
                objectObjectHashMap1.put("InterestTag", decode.split(","));
                objectObjectHashMap1.put("count",ackrequest.getCount());
                stringStringHashMap.put("request", objectObjectHashMap1);
                try {
                    //推荐接口返回数据
                    String porthttp = porthttp(stringStringHashMap);
                    System.out.println("porthttp = " + porthttp);
                    Map<String, Object> map = (Map<String, Object>) JSONArray.parse(porthttp);

                    List<Map<String, Object>> listdata = (List) map.get("data");
                    request.setRequest_id(SceneId + "@" + ((String) listdata.get(0).get("traceId")).split("-")[0]);
                    for (Map<String, Object> listmap : listdata) {
                        System.out.println("listmap.get(\"channelIdCode\") = " + listmap.get("channelIdCode"));
                        try {
                            String channelstart = listmap.get("channelstart").toString();
                            System.out.println("Long.parseLong(channelstart) = " + Long.parseLong(channelstart));
                            System.out.println("Long.valueOf(channelstart) = " + Long.valueOf(channelstart));
                            System.out.println("listmap.get(\"channelstart\") = " + listmap.get("channelstart"));
                        } catch (Exception e) {

                        }
                        New_Standard_media_assets new_standard_media_assets = new New_Standard_media_assets();
                        new_standard_media_assets.setCode((String) listmap.get("itemId"));
                        try {
                            new_standard_media_assets.setDuration((String) listmap.get("duration"));
                        } catch (Exception e) {
                            new_standard_media_assets.setDuration("");
                        }
                        try {
                            new_standard_media_assets.setSeriestype((String) listmap.get("seriestype"));
                        } catch (Exception e) {

                        }
                        try {
                            new_standard_media_assets.setTitle((String) listmap.get("title"));
                        } catch (Exception e) {
                            new_standard_media_assets.setTitle("");
                        }
                        try {
                            if(listmap.get("channelIdCode")==null){
                                new_standard_media_assets.setChannelcode(sss);
                            }else {
                                System.out.println("直播节目的自动赋值");
                                new_standard_media_assets.setChannelcode((String) listmap.get("channelIdCode"));
                            }
                        } catch (Exception e) {
                            new_standard_media_assets.setChannelcode(sss);
                        }
                        try {
                            new_standard_media_assets.setChannelstart(Long.valueOf(listmap.get("channelstart").toString()));
                        } catch (Exception e) {
                            new_standard_media_assets.setChannelstart(0L);

                        }
                        System.out.println("new_standard_media_assets = " + new_standard_media_assets);
                        list.add(new_standard_media_assets);
                        request.setSubject(list.subList(0, ackrequest.getCount() < list.size() ? ackrequest.getCount() : list.size()));


                    }
                } catch (Exception e) {
                    CommonLogger.error("用户id不能为空");
                    e.printStackTrace();
                }
                System.out.println("request = " + request);
                List<New_Standard_media_assets> list2 = new ArrayList<>();
                try {
                    if (request.getSubject().size() < 0) {
                        CommonLogger.error("这次请求返回的结果是走的兜底");
                        String getes = getes();
                        Map<String, Object> map = (Map<String, Object>) JSONArray.parse(getes);
                        Map<String, Object> hits = (Map<String, Object>) map.get("hits");
                        List<Map<String, Object>> hist2 = (List<Map<String, Object>>) hits.get("hits");
                        for (Map<String, Object> listmap : hist2) {
                            System.out.println("listmap.get(\"channelIdCode\") = " + listmap.get("channelIdCode"));
                            try {
                                String channelstart = listmap.get("channelstart").toString();
                                System.out.println("Long.parseLong(channelstart) = " + Long.parseLong(channelstart));
                                System.out.println("Long.valueOf(channelstart) = " + Long.valueOf(channelstart));
                                System.out.println("listmap.get(\"channelstart\") = " + listmap.get("channelstart"));
                            } catch (Exception e) {

                            }
                            New_Standard_media_assets new_standard_media_assets = new New_Standard_media_assets();
                            new_standard_media_assets.setCode((String) listmap.get("itemId"));
                            try {
                                new_standard_media_assets.setDuration((String) listmap.get("duration"));
                            } catch (Exception e) {
                                new_standard_media_assets.setDuration("");
                            }
                            try {
                                new_standard_media_assets.setSeriestype((String) listmap.get("seriestype"));
                            } catch (Exception e) {

                            }
                            try {
                                new_standard_media_assets.setTitle((String) listmap.get("title"));
                            } catch (Exception e) {
                                new_standard_media_assets.setTitle("");
                            }
                            try {
                                if(listmap.get("channelIdCode")==null){
                                    new_standard_media_assets.setChannelcode(sss);
                                }else {
                                    System.out.println("直播节目的自动赋值");
                                    new_standard_media_assets.setChannelcode((String) listmap.get("channelIdCode"));
                                }
                            } catch (Exception e) {
                                new_standard_media_assets.setChannelcode(sss);
                            }
                            try {
                                new_standard_media_assets.setChannelstart(Long.valueOf(listmap.get("channelstart").toString()));
                            } catch (Exception e) {
                                new_standard_media_assets.setChannelstart(0L);

                            }
                            System.out.println("new_standard_media_assets = " + new_standard_media_assets);
                            list2.add(new_standard_media_assets);



                            request.setSubject(list2.subList(0, ackrequest.getCount() < list2.size() ? ackrequest.getCount() : list2.size()));

                        }
                    }
                } catch (Exception e1) {
                    String getes = getes();
                    CommonLogger.error("这次请求返回的结果是走的兜底");
                    Map<String, Object> map = (Map<String, Object>) JSONArray.parse(getes);
                    Map<String, Object> hits = (Map<String, Object>) map.get("hits");
                    List<Map<String, Object>> hist2 = (List<Map<String, Object>>) hits.get("hits");
                    for (Map<String, Object> listmap : hist2) {
                        System.out.println("listmap.get(\"channelIdCode\") = " + listmap.get("channelIdCode"));
                        try {
                            String channelstart = listmap.get("channelstart").toString();
                            System.out.println("Long.parseLong(channelstart) = " + Long.parseLong(channelstart));
                            System.out.println("Long.valueOf(channelstart) = " + Long.valueOf(channelstart));
                            System.out.println("listmap.get(\"channelstart\") = " + listmap.get("channelstart"));
                        } catch (Exception e) {

                        }
                        New_Standard_media_assets new_standard_media_assets = new New_Standard_media_assets();
                        new_standard_media_assets.setCode((String) listmap.get("itemId"));
                        try {
                            new_standard_media_assets.setDuration((String) listmap.get("duration"));
                        } catch (Exception e) {
                            new_standard_media_assets.setDuration("");
                        }
                        try {
                            new_standard_media_assets.setSeriestype((String) listmap.get("seriestype"));
                        } catch (Exception e) {

                        }
                        try {
                            new_standard_media_assets.setTitle((String) listmap.get("title"));
                        } catch (Exception e) {
                            new_standard_media_assets.setTitle("");
                        }
                        try {
                            if(listmap.get("channelIdCode")==null){
                                new_standard_media_assets.setChannelcode(sss);
                            }else {
                                System.out.println("直播节目的自动赋值");
                                new_standard_media_assets.setChannelcode((String) listmap.get("channelIdCode"));
                            }
                        } catch (Exception e) {
                            new_standard_media_assets.setChannelcode(sss);
                        }
                        try {
                            new_standard_media_assets.setChannelstart(Long.valueOf(listmap.get("channelstart").toString()));
                        } catch (Exception e) {
                            new_standard_media_assets.setChannelstart(0L);

                        }
                        System.out.println("new_standard_media_assets = " + new_standard_media_assets);
                        list2.add(new_standard_media_assets);



                        request.setSubject(list2.subList(0, ackrequest.getCount() < list2.size() ? ackrequest.getCount() : list2.size()));

                    }
                }
                CommonLogger.info(request.toString());
            }
        }catch (Exception e) {
            e.printStackTrace();
        }

        return request;
    }


    /**
     * 向cess发请求
     * @param request
     * @return
     */

    public String porthttp(HashMap<String, Object> request) {
        System.out.println("request = " + request);
        //相应内容长度
        String responseEntityString = null;
        Object obj = JSONArray.toJSON(request);
        String jsonObject = obj.toString();
        System.out.println("jsonObject = " + jsonObject);
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
        HttpPost httpPost = new HttpPost(url);
        httpPost.setHeader("Content-Type", "application/json;charset=utf-8");
        httpPost.setHeader("X-CESS-Data-Token", "092e4b7ea9914553a02f901769df45b8");

        StringEntity entity = new StringEntity(jsonObject, "UTF-8");
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
                //System.out.println("响应内容为:" + EntityUtils.toString(responseEntity));
                responseEntityString = EntityUtils.toString(responseEntity);

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
        return responseEntityString;
    }


    /**
     * 保底请求es
     * @return
     */
    public String getes(){
        String responseEntityString=null;
   //电视剧
         String json = "{ \"from\":0,\"size\":50,\"query\":{\"bool\":{\"must\":[{\"match_all\":{\"boost\":1.0}}],\"filter\":[{\"wrapper\":{\"query\":\"eyJib29sIjp7ImZpbHRlciI6W3siZXhpc3RzIjp7ImZpZWxkIjoidGl0bGUifX0seyJtYXRjaCI6eyJzZWdtZW50X3lsYmFpXzA2MTYiOjF9fV19fQ==\"}}]}}}";
    //电影
      //   String json = "{ \"from\":0,\"size\":10,\"query\":{\"bool\":{\"must\":[{\"match_all\":{\"boost\":1.0}}],\"filter\":[{\"wrapper\":{\"query\":\"eyJib29sIjp7ImZpbHRlciI6W3siZXhpc3RzIjp7ImZpZWxkIjoidGl0bGUifX0seyJtYXRjaCI6eyJzZWdtZW50X25lbmd0dWlfMDQwMTIiOjF9fV19fQ==\"}}]}}}";
        //少儿
     //   String json = "{ \"from\":0,\"size\":10,\"query\":{\"bool\":{\"must\":[{\"match_all\":{\"boost\":1.0}}],\"filter\":[{\"wrapper\":{\"query\":\"eyJib29sIjp7ImZpbHRlciI6W3siZXhpc3RzIjp7ImZpZWxkIjoidGl0bGUifX0seyJtYXRjaCI6eyJzZWdtZW50X2R3MDQxMiI6MX19XX19\"}}]}}}";
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
        HttpPost httpPost = new HttpPost(url2);
        httpPost.setHeader("Content-Type", "application/json;charset=utf-8");
        StringEntity stringEntity = new StringEntity(json, "UTF-8");
        httpPost.setEntity(stringEntity);

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
                //System.out.println("响应内容为:" + EntityUtils.toString(responseEntity));
                responseEntityString=EntityUtils.toString(responseEntity);

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
        return responseEntityString;

    }

    /**
     *
     */


}


