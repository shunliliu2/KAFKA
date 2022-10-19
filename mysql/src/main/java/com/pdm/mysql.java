package com.pdm;

import com.alibaba.fastjson.JSONArray;
import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;

@RestController
@RequestMapping("/mysql")
public class mysql {
    static String selectsql = null;
    static ResultSet rs = null;
    public static final String url = "jdbc:mysql://172.25.3.102/wuliaolishi";
    public static final String name = "com.mysql.jdbc.Driver";
    public static final String user = "root";
    public static final String password = "Admin@4pd";

    public static Connection conn = null;
    public static PreparedStatement pst = null;

    @RequestMapping(value = "/dopost", method = RequestMethod.POST)
    @ResponseBody
    public void doPost(Integer qq) throws InterruptedException {
        System.out.println("qq 1= " + qq);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
        selectsql = "select * from wuliao2";//SQL语句
        try {
            Class.forName(name);//指定连接类型
            conn = DriverManager.getConnection(url, user, password);//获取连接
            pst = conn.prepareStatement(selectsql);//准备执行语句
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            User user = new User();
            rs = pst.executeQuery();//执行语句，得到结果集

            while (rs.next()) {
                int count =0;
                user.setItemId(rs.getString(1));
                user.setTitle(rs.getString(2));
                user.setOriginalname(rs.getString(3));
                user.setSearchname(rs.getString(4));
                user.setContent(rs.getString(5));
                user.setCasts(rs.getString(6));
                user.setDirectors(rs.getString(7));
                user.setScriptwriter(rs.getString(8));
                user.setCompere(rs.getString(9));
                user.setGuest(rs.getString(10));
                user.setReporter(rs.getString(11));
                user.setOpincharge(rs.getString(12));
                user.setLicensingwindowstart(rs.getString(13));
                user.setLicensingwindowemd(rs.getString(14));
                user.setVolumcount(rs.getString(15));
                user.setVspcode(rs.getString(16));
                try {
                    user.setPublishTime((sdf.parse(((String)rs.getString(17)))).getTime());
                } catch (Exception throwables) {
                    user.setPublishTime(31507200000L);
                }
                user.setSeriestype("s");
                user.setCopyright(rs.getString(19));
                user.setPublisherId(rs.getString(20));
                user.setTag(rs.getString(21));
                user.setPgmsndclass(rs.getString(22));
                user.setRating(rs.getDouble(23));
                user.setOriginalcountry(rs.getString(24));
                user.setType1(rs.getString(25));
                user.setType1_url(rs.getString(26));
                user.setType2(rs.getString(27));
                user.setType2_url(rs.getString(28));
                user.setItemSetId(qq);
                user.setItemStatus(rs.getString(30));
                user.setItemTime(rs.getString(31));
                user.setCoverUrl(rs.getString(32));
                user.setUrl(rs.getString(33));
                user.setTags(rs.getString(35));
                user.setGener(rs.getString(34));
                //user.setItemStatus("1");
                System.out.println("user = " + user);
                Object o = JSONArray.toJSON(user);
                String listjson = o.toString();
                System.out.println("listjson = " + listjson);
                System.out.println("count = " + count);
                count++;
                Thread.sleep(50);
                gethttp(listjson,qq);

            }
            rs.close();
            conn.close();//关闭连接
            pst.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static  void gethttp(String json,Integer qq){
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
        HttpPost httpPost = new HttpPost("http://172.25.3.107:30000/cess-data-13/data/api/data-access/13/"+qq+"/items");
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
