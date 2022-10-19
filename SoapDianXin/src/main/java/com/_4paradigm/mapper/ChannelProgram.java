//package com._4paradigm.mapper;
//
//import com._4paradigm.entity.C2Wai;
//import com._4paradigm.entity.Standard_media_assets;
//import com.alibaba.fastjson.JSONArray;
//import org.apache.http.HttpEntity;
//import org.apache.http.client.ClientProtocolException;
//import org.apache.http.client.methods.CloseableHttpResponse;
//import org.apache.http.client.methods.HttpPost;
//import org.apache.http.entity.StringEntity;
//import org.apache.http.impl.client.CloseableHttpClient;
//import org.apache.http.impl.client.HttpClientBuilder;
//import org.apache.http.util.EntityUtils;
//
//import java.io.FileInputStream;
//import java.io.IOException;
//import java.sql.Connection;
//import java.sql.PreparedStatement;
//import java.sql.SQLException;
//
///**
// * 新增节目和时间表
// */
//public class ChannelProgram {
//    /**
//     * 新增频道表
//     * @param new_standard_media_assets
//     */
//    public void insertChannel(Standard_media_assets new_standard_media_assets){
//
//
//
//        Connection conn = null;
//        PreparedStatement ps = null;
//        FileInputStream in = null;
//        try {
//            conn = ConnUtil.getConn();
//            String sql = "insert into channel (itemid,name) values (?,?)";
//            ps = conn.prepareStatement(sql);
//            ps.setString(1, new_standard_media_assets.getItemId());
//            ps.setString(2,new_standard_media_assets.getTitle());
//
//            ps.executeUpdate();
//            Object o = JSONArray.toJSON(new_standard_media_assets);
//            String listjson = o.toString();
//            gethttp(listjson);
//
//
//
//
//
//        } catch (Exception e) {
//        } finally {
//            ConnUtil.closeConn(conn);
//            if (null != ps) {
//                try {
//                    ps.close();
//                } catch (SQLException e) {
//                    e.printStackTrace();
//                }
//            }
//        }
//
//    }
//
//    /**
//     * 新增节目表
//     * @param new_standard_media_assets
//     */
//
//    public void insertProgram(Standard_media_assets new_standard_media_assets){
//
//
//
//        Connection conn = null;
//        PreparedStatement ps = null;
//        FileInputStream in = null;
//        try {
//            conn = ConnUtil.getConn();
//            String sql = "insert into program (itemid,name,channalcode) values (?,?,?)";
//            ps = conn.prepareStatement(sql);
//            ps.setString(1, new_standard_media_assets.getItemId());
//            ps.setString(2,new_standard_media_assets.getTitle());
//            ps.setString(3,new_standard_media_assets.getChannelcode());
//
//            ps.executeUpdate();
//            Object o = JSONArray.toJSON(new_standard_media_assets);
//            String listjson = o.toString();
//            gethttp(listjson,);
//
//
//
//
//
//
//
//        } catch (Exception e) {
//        } finally {
//            ConnUtil.closeConn(conn);
//            if (null != ps) {
//                try {
//                    ps.close();
//                } catch (SQLException e) {
//                    e.printStackTrace();
//                }
//            }
//        }
//
//    }
//    public static  void gethttp(String json,String  wuliaoid){
//        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
//        HttpPost httpPost = new HttpPost("http://172.25.3.107:30000/cess-data-3/data/api/data-access/3/"+wuliaoid+"/items");
//        httpPost.setHeader("Content-Type", "application/json;charset=utf-8");
//        httpPost.setHeader("X-CESS-Data-Token", "0bf6ff0c10d641c99d519abbeec6ed35");
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
//
//
//
//
//}
