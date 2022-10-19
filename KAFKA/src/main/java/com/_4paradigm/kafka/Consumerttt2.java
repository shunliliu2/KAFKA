package com._4paradigm.kafka;

import com._4paradigm.entity.Contentviewlog;
import com._4paradigm.entity.Contentviewlog_er;
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


    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");



    @KafkaListeners({@KafkaListener(topics="cdr_viewLog"),@KafkaListener(topics="cdr_exEventLog"),@KafkaListener(topics="cdr_orderLog")})
    //@KafkaListeners({@KafkaListener(topics="topicfff")})
    public void listen(ConsumerRecord<Integer, String> consumerRecord) throws ParseException {
        sdf.setTimeZone(TimeZone.getTimeZone("GMT+8"));
        Contentviewlog_er contentviewlog_er = new Contentviewlog_er();
        System.out.println("consumerRecord.value() = " + consumerRecord.value());
        String value = consumerRecord.value();
        String[] split = value.split(",", -1);
        if(split[19].equals("u")||split[10].equals("u")){
            if(split[split.length-1].equals("")||split[split.length-1].equals(null)||split[split.length-1].equals("null")) {
                if (split[1].equals("order")) {
                    if (split[21].equals(0)) {
                        contentviewlog_er.setActionTime(Long.getLong(split[5]));
                        contentviewlog_er.setSceneId(SceneId);//场景id
                        contentviewlog_er.setItemSetId(Integer.parseInt(ItemSetId));//物料id
                        contentviewlog_er.setAction("subscribe");//行为名称
                        contentviewlog_er.setUserId(split[3]);
                        contentviewlog_er.setReferType(split[35]);
                        contentviewlog_er.setAreaCode(split[2]);
                        contentviewlog_er.setCounty(split[23]);
                        contentviewlog_er.setItemId(split[4]);
                        contentviewlog_er.setProductType(Integer.parseInt(split[16]));
                        if (split[16].equals("1")) {
                            contentviewlog_er.setProductId(split[4]);
                        } else {
                            contentviewlog_er.setProductId(split[13]);
                        }
                        contentviewlog_er.setActionTime(sdf.parse(split[19]).getTime());
                        contentviewlog_er.setValidTime(split[17]);
                        contentviewlog_er.setExpiredTime(split[18]);
                        contentviewlog_er.setOrderContinue(Integer.parseInt(split[22]));
                        contentviewlog_er.setAreaCode(split[16]);
                        try {
                            if (split[23].equals("33")) {
                                contentviewlog_er.setTraceId(split[43]);
                            }
                            contentviewlog_er.setTraceId(split[43]);
                        } catch (Exception e) {

                        }

                    } else {
                        contentviewlog_er.setActionTime(sdf.parse(split[5]).getTime());
                        contentviewlog_er.setSceneId(SceneId);//场景id
                        contentviewlog_er.setItemSetId(Integer.parseInt(ItemSetId));//物料id
                        contentviewlog_er.setAction("unsubscribe");//行为名称
                        contentviewlog_er.setUserId(split[3]);
                        contentviewlog_er.setReferType(split[35]);
                        contentviewlog_er.setAreaCode(split[2]);
                        contentviewlog_er.setCounty(split[23]);
                        contentviewlog_er.setItemId(split[4]);
                        contentviewlog_er.setProductType(Integer.parseInt(split[16]));
                        if (split[16].equals("1")) {
                            contentviewlog_er.setProductId(split[4]);
                        } else {
                            contentviewlog_er.setProductId(split[13]);
                        }
                        try {
                            contentviewlog_er.setActionTime(sdf.parse(split[19]).getTime());
                        } catch (ParseException e) {
                        }
                        contentviewlog_er.setValidTime(split[17]);
                        contentviewlog_er.setExpiredTime(split[18]);
                        contentviewlog_er.setOrderContinue(Integer.parseInt(split[22]));
                        contentviewlog_er.setAreaCode(split[16]);
                        try {
                            if (split[23].equals("33")) {
                                contentviewlog_er.setTraceId(split[43]);
                            }
                            contentviewlog_er.setTraceId(split[43]);
                        } catch (Exception e) {

                        }

                    }
                } else if (split[1].equals("v")) {
                    if (split[33].equals("首页屏保&1")) {

                    } else {
                        if(split[19].equals("u")){
                            contentviewlog_er.setActionTime(sdf.parse(split[5]).getTime());
                            //System.out.println(sdf.parse(split[5]));
                            //System.out.println(sdf.parse(split[5]).getTime());
                            contentviewlog_er.setSceneId("Other");//场景id
                            contentviewlog_er.setItemSetId(Integer.parseInt(ItemSetId));//物料id
                            contentviewlog_er.setAction("detailPageShow");//行为名称
                            contentviewlog_er.setUserId(split[3]);
                            contentviewlog_er.setReferType(split[31]);
                            contentviewlog_er.setAreaCode(split[2]);
                            contentviewlog_er.setPageId(split[14]);
                            if(split[14].equals("0")){

                                contentviewlog_er.setItemId(split[4]);
                            }else {
                                contentviewlog_er.setItemId(split[15]);
                            }
                            contentviewlog_er.setAreaCode(split[16]);
                            contentviewlog_er.setPath("");
                            contentviewlog_er.setCounty(split[7]);
                            contentviewlog_er.setSysID(split[19]);
                            contentviewlog_er.setTraceId(" ");

                        }


                    }


                }
            } else {
                System.out.println("split[1] = " + split[1]);
                try {
                    if (split[1].equals("v")) {
                        if (split[33].equals("首页屏保&1")) {
                        } else {

                            String[] split1 = split[39].split("@");
                            if (split1[0].equals("53")) {
                                contentviewlog_er.setActionTime(sdf.parse(split[5]).getTime());
                                //System.out.println(sdf.parse(split[5]));
                                contentviewlog_er.setSceneId(split1[0]);//场景id
                                contentviewlog_er.setItemSetId(121);//物料id
                                contentviewlog_er.setAction("detailPageShow");//行为名称
                                contentviewlog_er.setUserId(split[3]);
                                contentviewlog_er.setReferType(split[31]);
                                contentviewlog_er.setAreaCode(split[2]);
                                try {
                                    contentviewlog_er.setReferPageID(split1[0]);//场景id
                                } catch (Exception e) {
                                }

                                contentviewlog_er.setAreaCode(split[16]);
                                contentviewlog_er.setPath("");
                                contentviewlog_er.setPageId(split[14]);
                                contentviewlog_er.setCounty(split[7]);
                                contentviewlog_er.setSysID(split[19]);
                                contentviewlog_er.setItemId(split[4]);
                                contentviewlog_er.setTraceId(split1[1] + "-" + split[4]);

                            }else {
                                contentviewlog_er.setActionTime(sdf.parse(split[5]).getTime());
                                //System.out.println(sdf.parse(split[5]));
                                contentviewlog_er.setSceneId(split1[0]);//场景id
                                contentviewlog_er.setItemSetId(79);//物料id
                                contentviewlog_er.setAction("detailPageShow");//行为名称
                                contentviewlog_er.setUserId(split[3]);
                                contentviewlog_er.setReferType(split[31]);
                                contentviewlog_er.setAreaCode(split[2]);
                                try {
                                    contentviewlog_er.setReferPageID(split1[2]);
                                } catch (Exception e) {
                                }

                                contentviewlog_er.setAreaCode(split[16]);
                                contentviewlog_er.setPath("");
                                contentviewlog_er.setPageId(split[14]);
                                contentviewlog_er.setCounty(split[7]);
                                contentviewlog_er.setSysID(split[19]);
                                if (split[14].equals("0")) {
                                    contentviewlog_er.setItemId(split[4]);
                                    contentviewlog_er.setTraceId(split1[1] + "-" + split[4]);
                                } else {
                                    contentviewlog_er.setItemId(split[15]);
                                    contentviewlog_er.setTraceId(split1[1] + "-" + split[15]);
                                }
                            }

                        }

                    } else if (split[1].equals("external_event")) {//曝光
                        System.out.println("baoguang");
                        String[] split1 = split[4].split("~");
                        String[] split2 = split[20].split("@");

                        if(split2[0].equals("53")){
                            for (String s : split1) {
                                //System.out.println(split[9]);
                                contentviewlog_er.setActionTime(Long.parseLong(split[9] + "000"));
                                contentviewlog_er.setSceneId(split2[0]);//场景id
                                contentviewlog_er.setItemSetId(121);//物料id
                                contentviewlog_er.setAction("show");//行为名称
                                contentviewlog_er.setUserId(split[3]);
                                contentviewlog_er.setStbIp(split[8]);
                                contentviewlog_er.setStbId(split[10]);
                                contentviewlog_er.setStbType(split[5]);
                                contentviewlog_er.setAreaCode(split[2]);
                                contentviewlog_er.setItemId(s);
                                try {
                                    contentviewlog_er.setReferPageID(split2[0]);
                                } catch (Exception e) {
                                }
                                contentviewlog_er.setAreaCode(split[14]);
                                contentviewlog_er.setPath("");
                                contentviewlog_er.setTraceId(split2[1] + "-" + s);
                                Object o = JSONArray.toJSON(contentviewlog_er);
                                String contentviewlogjson = o.toString();
                                gethttpu(contentviewlogjson);
                                contentviewlog_er = new Contentviewlog_er();
                            }
                        }else {
                            for (String s : split1) {
                                //System.out.println(split[9]);
                                contentviewlog_er.setActionTime(Long.parseLong(split[9] + "000"));
                                contentviewlog_er.setSceneId(split2[0]);//场景id
                                contentviewlog_er.setItemSetId(79);//物料id
                                contentviewlog_er.setAction("show");//行为名称
                                contentviewlog_er.setUserId(split[3]);
                                contentviewlog_er.setStbIp(split[8]);
                                contentviewlog_er.setStbId(split[10]);
                                contentviewlog_er.setStbType(split[5]);
                                contentviewlog_er.setAreaCode(split[2]);
                                contentviewlog_er.setItemId(s);
                                try {
                                    contentviewlog_er.setReferPageID(split2[2]);
                                } catch (Exception e) {
                                }
                                contentviewlog_er.setAreaCode(split[14]);
                                contentviewlog_er.setPath("");
                                contentviewlog_er.setTraceId(split2[1] + "-" + s);
                                Object o = JSONArray.toJSON(contentviewlog_er);
                                String contentviewlogjson = o.toString();
                                gethttpu(contentviewlogjson);
                                contentviewlog_er = new Contentviewlog_er();
                            }
                        }
                    } else if (split[1].equals("order")) {
                        if (split[21].equals(0)) {
                            contentviewlog_er.setActionTime(Long.getLong(split[5]));
                            contentviewlog_er.setSceneId(SceneId);//场景id
                            contentviewlog_er.setItemSetId(Integer.parseInt(ItemSetId));//物料id
                            contentviewlog_er.setAction("subscribe");//行为名称
                            contentviewlog_er.setUserId(split[3]);
                            contentviewlog_er.setReferType(split[35]);
                            contentviewlog_er.setAreaCode(split[2]);
                            contentviewlog_er.setCounty(split[23]);
                            contentviewlog_er.setItemId(split[4]);
                            contentviewlog_er.setProductType(Integer.parseInt(split[16]));
                            if (split[16].equals("1")) {
                                contentviewlog_er.setProductId(split[4]);
                            } else {
                                contentviewlog_er.setProductId(split[13]);
                            }
                            contentviewlog_er.setActionTime(sdf.parse(split[19]).getTime());
                            contentviewlog_er.setValidTime(split[17]);
                            contentviewlog_er.setExpiredTime(split[18]);
                            contentviewlog_er.setOrderContinue(Integer.parseInt(split[22]));
                            contentviewlog_er.setAreaCode(split[16]);
                            try {
                                if (split[23].equals("33")) {
                                    contentviewlog_er.setTraceId(split[43]);
                                }
                                contentviewlog_er.setTraceId(split[43]);
                            } catch (Exception e) {

                            }

                        } else {
                            contentviewlog_er.setActionTime(Long.getLong(split[5]));
                            contentviewlog_er.setSceneId(SceneId);//场景id
                            contentviewlog_er.setItemSetId(Integer.parseInt(ItemSetId));//物料id
                            contentviewlog_er.setAction("unsubscribe");//行为名称
                            contentviewlog_er.setUserId(split[3]);
                            contentviewlog_er.setReferType(split[35]);
                            contentviewlog_er.setAreaCode(split[2]);
                            contentviewlog_er.setCounty(split[23]);
                            contentviewlog_er.setItemId(split[4]);
                            contentviewlog_er.setProductType(Integer.parseInt(split[16]));
                            if (split[16].equals("1")) {
                                contentviewlog_er.setProductId(split[4]);
                            } else {
                                contentviewlog_er.setProductId(split[13]);
                            }
                            contentviewlog_er.setActionTime(sdf.parse(split[19]).getTime());
                            contentviewlog_er.setValidTime(split[17]);
                            contentviewlog_er.setExpiredTime(split[18]);
                            contentviewlog_er.setOrderContinue(Integer.parseInt(split[22]));
                            contentviewlog_er.setAreaCode(split[16]);
                            try {
                                if (split[23].equals("33")) {
                                    contentviewlog_er.setTraceId(split[43]);
                                }
                                contentviewlog_er.setTraceId(split[43]);
                            } catch (Exception e) {

                            }

                        }
                    }
                } catch (Exception e) {

                    e.printStackTrace();
                }

            }
            //System.out.println("contentviewlog = " + contentviewlog_er);
            Object o = JSONArray.toJSON(contentviewlog_er);
            String contentviewlogjson = o.toString();
            CommonLogger.info(contentviewlogjson);
            gethttpu(contentviewlogjson);


        }else  if(split[19].equals("t")||split[10].equals("t")){
            if(split[split.length-1].equals("")||split[split.length-1].equals(null)||split[split.length-1].equals("null")) {
                if (split[1].equals("order")) {
                    if (split[21].equals(0)) {
                        contentviewlog_er.setActionTime(Long.getLong(split[5]));
                        contentviewlog_er.setSceneId(SceneId);//场景id
                        contentviewlog_er.setItemSetId(139);//物料id
                        contentviewlog_er.setAction("subscribe");//行为名称
                        contentviewlog_er.setUserId(split[3]);
                        contentviewlog_er.setReferType(split[35]);
                        contentviewlog_er.setAreaCode(split[2]);
                        contentviewlog_er.setCounty(split[23]);
                        contentviewlog_er.setItemId(split[4]);
                        contentviewlog_er.setProductType(Integer.parseInt(split[16]));
                        if (split[16].equals("1")) {
                            contentviewlog_er.setProductId(split[4]);
                        } else {
                            contentviewlog_er.setProductId(split[13]);
                        }
                        contentviewlog_er.setActionTime(sdf.parse(split[19]).getTime());
                        contentviewlog_er.setValidTime(split[17]);
                        contentviewlog_er.setExpiredTime(split[18]);
                        contentviewlog_er.setOrderContinue(Integer.parseInt(split[22]));
                        contentviewlog_er.setAreaCode(split[16]);
                        try {
                            if (split[23].equals("33")) {
                                contentviewlog_er.setTraceId(split[43]);
                            }
                            contentviewlog_er.setTraceId(split[43]);
                        } catch (Exception e) {

                        }

                    } else {
                        contentviewlog_er.setActionTime(sdf.parse(split[5]).getTime());
                        contentviewlog_er.setSceneId(SceneId);//场景id
                        contentviewlog_er.setItemSetId(139);//物料id
                        contentviewlog_er.setAction("unsubscribe");//行为名称
                        contentviewlog_er.setUserId(split[3]);
                        contentviewlog_er.setReferType(split[35]);
                        contentviewlog_er.setAreaCode(split[2]);
                        contentviewlog_er.setCounty(split[23]);
                        contentviewlog_er.setItemId(split[4]);
                        contentviewlog_er.setProductType(Integer.parseInt(split[16]));
                        if (split[16].equals("1")) {
                            contentviewlog_er.setProductId(split[4]);
                        } else {
                            contentviewlog_er.setProductId(split[13]);
                        }
                        try {
                            contentviewlog_er.setActionTime(sdf.parse(split[19]).getTime());
                        } catch (ParseException e) {
                        }
                        contentviewlog_er.setValidTime(split[17]);
                        contentviewlog_er.setExpiredTime(split[18]);
                        contentviewlog_er.setOrderContinue(Integer.parseInt(split[22]));
                        contentviewlog_er.setAreaCode(split[16]);
                        try {
                            if (split[23].equals("33")) {
                                contentviewlog_er.setTraceId(split[43]);
                            }
                            contentviewlog_er.setTraceId(split[43]);
                        } catch (Exception e) {

                        }

                    }
                } else if (split[1].equals("v")) {
                    if (split[33].equals("首页屏保&1")) {

                    } else {
                        if(split[19].equals("t")){
                            contentviewlog_er.setActionTime(sdf.parse(split[5]).getTime());
                            // System.out.println(sdf.parse(split[5]));
                            //  System.out.println(sdf.parse(split[5]).getTime());
                            contentviewlog_er.setSceneId("Other");//场景id
                            contentviewlog_er.setItemSetId(139);//物料id
                            contentviewlog_er.setAction("detailPageShow");//行为名称
                            contentviewlog_er.setUserId(split[3]);
                            contentviewlog_er.setReferType(split[31]);
                            contentviewlog_er.setAreaCode(split[2]);
                            contentviewlog_er.setPageId(split[14]);
                            if(split[14].equals("0")){

                                contentviewlog_er.setItemId(split[4]);
                            }else {
                                contentviewlog_er.setItemId(split[15]);
                            }
                            contentviewlog_er.setAreaCode(split[16]);
                            contentviewlog_er.setPath("");
                            contentviewlog_er.setCounty(split[7]);
                            contentviewlog_er.setSysID(split[19]);
                            contentviewlog_er.setTraceId(" ");

                        }


                    }


                }
            }
            else {

                try {
                    if (split[1].equals("v")) {
                        if (split[33].equals("首页屏保&1")) {
                        } else {


                            String[] split1 = split[39].split("@");
                            if (split1[0].equals("53")) {
                                contentviewlog_er.setActionTime(sdf.parse(split[5]).getTime());
                                // System.out.println(sdf.parse(split[5]));
                                contentviewlog_er.setSceneId(split1[0]);//场景id
                                contentviewlog_er.setItemSetId(139);//物料id
                                contentviewlog_er.setAction("detailPageShow");//行为名称
                                contentviewlog_er.setUserId(split[3]);
                                contentviewlog_er.setReferType(split[31]);
                                contentviewlog_er.setAreaCode(split[2]);
                                try {
                                    contentviewlog_er.setReferPageID(split1[0]);//场景id
                                } catch (Exception e) {
                                }

                                contentviewlog_er.setAreaCode(split[16]);
                                contentviewlog_er.setPath("");
                                contentviewlog_er.setPageId(split[14]);
                                contentviewlog_er.setCounty(split[7]);
                                contentviewlog_er.setSysID(split[19]);
                                contentviewlog_er.setItemId(split[4]);
                                contentviewlog_er.setTraceId(split1[1] + "-" + split[4]);

                            }else {
                                contentviewlog_er.setActionTime(sdf.parse(split[5]).getTime());
                                //  System.out.println(sdf.parse(split[5]));
                                contentviewlog_er.setSceneId(split1[0]);//场景id
                                contentviewlog_er.setItemSetId(139);//物料id
                                contentviewlog_er.setAction("detailPageShow");//行为名称
                                contentviewlog_er.setUserId(split[3]);
                                contentviewlog_er.setReferType(split[31]);
                                contentviewlog_er.setAreaCode(split[2]);
                                try {
                                    contentviewlog_er.setReferPageID(split1[2]);
                                } catch (Exception e) {
                                }

                                contentviewlog_er.setAreaCode(split[16]);
                                contentviewlog_er.setPath("");
                                contentviewlog_er.setPageId(split[14]);
                                contentviewlog_er.setCounty(split[7]);
                                contentviewlog_er.setSysID(split[19]);
                                if (split[14].equals("0")) {
                                    contentviewlog_er.setItemId(split[4]);
                                    contentviewlog_er.setTraceId(split1[1] + "-" + split[4]);
                                } else {
                                    contentviewlog_er.setItemId(split[15]);
                                    contentviewlog_er.setTraceId(split1[1] + "-" + split[15]);
                                }
                            }

                        }

                    } else if (split[1].equals("external_event")) {//曝光
                        String[] split1 = split[4].split("~");
                        String[] split2 = split[20].split("@");

                        if(split2[0].equals("53")){
                            for (String s : split1) {
                                //  System.out.println(split[9]);
                                contentviewlog_er.setActionTime(Long.parseLong(split[9] + "000"));
                                contentviewlog_er.setSceneId(split2[0]);//场景id
                                contentviewlog_er.setItemSetId(139);//物料id
                                contentviewlog_er.setAction("show");//行为名称
                                contentviewlog_er.setUserId(split[3]);
                                contentviewlog_er.setStbIp(split[8]);
                                contentviewlog_er.setStbId(split[10]);
                                contentviewlog_er.setStbType(split[5]);
                                contentviewlog_er.setAreaCode(split[2]);
                                contentviewlog_er.setItemId(s);
                                try {
                                    contentviewlog_er.setReferPageID(split2[0]);
                                } catch (Exception e) {
                                }
                                contentviewlog_er.setAreaCode(split[14]);
                                contentviewlog_er.setPath("");
                                contentviewlog_er.setTraceId(split2[1] + "-" + s);
                                Object o = JSONArray.toJSON(contentviewlog_er);
                                String contentviewlogjson = o.toString();
                                gethttpt(contentviewlogjson);
                                contentviewlog_er = new Contentviewlog_er();
                            }
                        }else {
                            for (String s : split1) {
                                //  System.out.println(split[9]);
                                System.out.println("lll = " );
                                contentviewlog_er.setActionTime(Long.parseLong(split[9] + "000"));
                                contentviewlog_er.setSceneId(split2[0]);//场景id
                                contentviewlog_er.setItemSetId(139);//物料id
                                contentviewlog_er.setAction("show");//行为名称
                                contentviewlog_er.setUserId(split[3]);
                                contentviewlog_er.setStbIp(split[8]);
                                contentviewlog_er.setStbId(split[10]);
                                contentviewlog_er.setStbType(split[5]);
                                contentviewlog_er.setAreaCode(split[2]);
                                contentviewlog_er.setItemId(s);
                                try {
                                    contentviewlog_er.setReferPageID(split2[2]);
                                } catch (Exception e) {
                                }
                                contentviewlog_er.setAreaCode(split[14]);
                                contentviewlog_er.setPath("");
                                contentviewlog_er.setTraceId(split2[1] + "-" + s);
                                Object o = JSONArray.toJSON(contentviewlog_er);
                                String contentviewlogjson = o.toString();
                                gethttpt(contentviewlogjson);
                                contentviewlog_er = new Contentviewlog_er();
                            }
                        }
                    } else if (split[1].equals("order")) {
                        if (split[21].equals(0)) {
                            contentviewlog_er.setActionTime(Long.getLong(split[5]));
                            contentviewlog_er.setSceneId(SceneId);//场景id
                            contentviewlog_er.setItemSetId(139);//物料id
                            contentviewlog_er.setAction("subscribe");//行为名称
                            contentviewlog_er.setUserId(split[3]);
                            contentviewlog_er.setReferType(split[35]);
                            contentviewlog_er.setAreaCode(split[2]);
                            contentviewlog_er.setCounty(split[23]);
                            contentviewlog_er.setItemId(split[4]);
                            contentviewlog_er.setProductType(Integer.parseInt(split[16]));
                            if (split[16].equals("1")) {
                                contentviewlog_er.setProductId(split[4]);
                            } else {
                                contentviewlog_er.setProductId(split[13]);
                            }
                            contentviewlog_er.setActionTime(sdf.parse(split[19]).getTime());
                            contentviewlog_er.setValidTime(split[17]);
                            contentviewlog_er.setExpiredTime(split[18]);
                            contentviewlog_er.setOrderContinue(Integer.parseInt(split[22]));
                            contentviewlog_er.setAreaCode(split[16]);
                            try {
                                if (split[23].equals("33")) {
                                    contentviewlog_er.setTraceId(split[43]);
                                }
                                contentviewlog_er.setTraceId(split[43]);
                            } catch (Exception e) {

                            }

                        } else {
                            contentviewlog_er.setActionTime(Long.getLong(split[5]));
                            contentviewlog_er.setSceneId(SceneId);//场景id
                            contentviewlog_er.setItemSetId(139);//物料id
                            contentviewlog_er.setAction("unsubscribe");//行为名称
                            contentviewlog_er.setUserId(split[3]);
                            contentviewlog_er.setReferType(split[35]);
                            contentviewlog_er.setAreaCode(split[2]);
                            contentviewlog_er.setCounty(split[23]);
                            contentviewlog_er.setItemId(split[4]);
                            contentviewlog_er.setProductType(Integer.parseInt(split[16]));
                            if (split[16].equals("1")) {
                                contentviewlog_er.setProductId(split[4]);
                            } else {
                                contentviewlog_er.setProductId(split[13]);
                            }
                            contentviewlog_er.setActionTime(sdf.parse(split[19]).getTime());
                            contentviewlog_er.setValidTime(split[17]);
                            contentviewlog_er.setExpiredTime(split[18]);
                            contentviewlog_er.setOrderContinue(Integer.parseInt(split[22]));
                            contentviewlog_er.setAreaCode(split[16]);
                            try {
                                if (split[23].equals("33")) {
                                    contentviewlog_er.setTraceId(split[43]);
                                }
                                contentviewlog_er.setTraceId(split[43]);
                            } catch (Exception e) {

                            }

                        }
                    }
                } catch (Exception e) {

                    e.printStackTrace();
                }

            }
            // System.out.println("contentviewlog = " + contentviewlog_er);
            Object o = JSONArray.toJSON(contentviewlog_er);
            String contentviewlogjson = o.toString();
            CommonLogger.info(contentviewlogjson);
            gethttpt(contentviewlogjson);

        }







    }
    //action

    public void gethttpu(String json){
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

            //System.out.println("响应状态为:" + response.getStatusLine());
            if (responseEntity != null) {
                //System.out.println("响应内容长度为:" + responseEntity.getContentLength());
                //System.out.println("响应内容为:" + EntityUtils.toString(responseEntity));
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
    public void gethttpt(String json){
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
        HttpPost httpPost = new HttpPost("http://172.25.3.107:30000/cess-data-15/data/api/data-access/15/actions");
        httpPost.setHeader("Content-Type", "application/json;charset=utf-8");
        httpPost.setHeader("X-CESS-Data-Token", "27b3a680b5c94a4d9fe42f5fe77b360e");

        StringEntity entity = new StringEntity(json, "UTF-8");
        httpPost.setEntity(entity);
        // 响应模型
        CloseableHttpResponse response = null;
        try {
            // 由客户端执行(发送)Post请求
            response = httpClient.execute(httpPost);
            // 从响应模型中获取响应实体
            HttpEntity responseEntity = response.getEntity();

            //System.out.println("响应状态为:" + response.getStatusLine());
            if (responseEntity != null) {
                //System.out.println("响应内容长度为:" + responseEntity.getContentLength());
                //System.out.println("响应内容为:" + EntityUtils.toString(responseEntity));
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
