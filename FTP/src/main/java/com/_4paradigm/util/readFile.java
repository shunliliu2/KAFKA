package com._4paradigm.util;

import com._4paradigm.entity.Contentviewlog_er;
import com._4paradigm.scala.test;
import com.alibaba.fastjson.JSONArray;
import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

import java.io.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.TimeZone;

public class readFile {
    // 读取文件内容
    public void readFile(String path, String sssssssss,String shijian) throws IOException {
        String csvPath = test.f2(path,shijian);
        List<String> csv = getCSV(csvPath);
        for (String spath : csv) {
            File file = new File(path);
            BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(spath), "GBK"));//构造一个BufferedReader类来读取文件


            DateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
            format.setTimeZone(TimeZone.getTimeZone("GMT+8"));


            HttpPost httpPost = new HttpPost("http://172.25.3.107:30000/cess-data-13/data/api/data-access/13/actions");
            httpPost.setHeader("Content-Type", "application/json;charset=utf-8");
            httpPost.setHeader("X-CESS-Data-Token", "092e4b7ea9914553a02f901769df45b8");

            try {
                int i = 0;
                String select = "";
                String s = null;

                FileInputStream in = null;
                String rowCount = "";

                while ((s = br.readLine()) != null) {//使用readLine方法，一次读一行
                    String[] split = s.split(",");

                    long start = format.parse(split[1]).getTime();
                    long end = format.parse(split[2]).getTime();
//                    if (end - start < 20000) {
//                        continue;
//                    }
                    Contentviewlog_er contentviewlog_er = new Contentviewlog_er();
                    contentviewlog_er.setTraceId("");
                    contentviewlog_er.setSceneId("OtherC3");
                    i++;
                    contentviewlog_er.setUserId(split[0]);
                    contentviewlog_er.setItemSetId(79);//物料id
                    contentviewlog_er.setAction("detailPageShow");//行为名称
                    contentviewlog_er.setActionTime(format.parse(split[1]).getTime());
                    String[] splitserverid = split[7].split("0");
                    if(splitserverid.length>1){
                        contentviewlog_er.setItemId(split[7]);
                    }else {
                        contentviewlog_er.setItemId(split[6]);
                    }

                    Object o = JSONArray.toJSON(contentviewlog_er);
                    String contentviewlogjson = o.toString();
                    System.out.println("contentviewlogjson =   " + i + "     " + sssssssss + "   " + contentviewlogjson);

                    gethttp(contentviewlogjson);

                }
                br.close();
            } catch (Exception e) {
                e.printStackTrace();

            }
        }

    }


    public void gethttp(String json){
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
        HttpPost httpPost = new HttpPost("http://172.25.3.107:30000/cess-data-13/data/api/data-access/13/actions");
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

    /**
     * 获取当前文件下的csv文件路径
     * @param DirPath
     * @return
     * @throws IOException
     */
    public static List<String> getCSV(String DirPath) throws IOException {
        //获取存放文件的目录
        File dirpath = new File(DirPath);
        //将该目录下的所有文件放置在一个File类型的数组中
        File[] filelist = dirpath.listFiles();
        //创建一个空的file类型的list集合，用于存放符合条件的文件
        List<File> newfilelist = new ArrayList<>();
        //遍历filelist，如果是文件则存储在新建的list集合中
        for (File file : filelist) {
            if (file.isFile()){
                newfilelist.add(file);
            }
        }
        //新建list集合，用于存放读取到的数据
        List<String> list = new ArrayList<>();
        //读取文件内容
        String s = null;
        int count=0;
        for (File file : newfilelist) {
            //判断：如果是CSV文件，
            if (file.toString().endsWith(".csv")){
                FileReader fileReader = new FileReader(file);
                list.add(file.getPath());
            }

        }
        return list;

    }

}
