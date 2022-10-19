package com._4paradigm.ftp;

import com._4paradigm.entity.User;
import com._4paradigm.util.FenDuan;
import com._4paradigm.util.ZipUtilApache;
import com.alibaba.fastjson.JSONArray;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.*;

@Controller
public class FtpControllersuixinkan {
    @Autowired
    private ZipUtilApache zipUtilApache;
    //@Autowired
   // private ParsingLog parsingLog;
    @Autowired
    private FenDuan fenduan;
    @Value("${hostname}")
    private String hostname;
    @Value("${port}")
    private Integer port;
    @Value("${username}")
    private String username ;
    @Value("${password}")
    private String password ;
    @Value("${fileName}")
    private String fileName="/";
    @Value("${localPath}")
    private String localPath="/opt/server/data/";
    private FTPClient ftpClient = null;
    private InputStream inputStream = null;

    /***
     * fileName：ftp文件路径
     * localPath 本地路径
     */

    @ResponseBody
    public Map<String, Object> downloadFile() {
        ftpClient = new FTPClient();
        DateFormat format = new SimpleDateFormat("yyyyMMdd");
        Map<String, Object> map = new HashMap<>();
        ftpClient.setControlEncoding("GBK");
        Calendar c = Calendar.getInstance();
        List<Map> objects = new ArrayList<>();
        c.add(Calendar.DAY_OF_MONTH, -1);
        try {
            ftpClient.connect("172.25.3.114", 21);
            ftpClient.login("root", "4paradigm_AIO");
            ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);
            ftpClient.enterLocalPassiveMode();
            String [] VendorName={"ZTE_","HW_"};
                //ftpClient.changeWorkingDirectory(fileName+s+format.format(c.getTime())+".zip");
                File localFile = new File(localPath + "A_userid.txt");
                OutputStream os = new FileOutputStream(localFile);

                boolean b = ftpClient.retrieveFile(fileName+"A_userid.txt", os);
                System.out.println("b = " + b);
                int n=250;
                FenDuan fenDuan = new FenDuan();
                while (n>=200) {
                    List<User> parsing;
                    parsing = fenduan.getgetParsing(fenDuan, "A_userid.txt", localPath + "A_userid.txt", "GBK");
                    System.out.println("parsing.size() = " + parsing.size());
                    for (User user : parsing) {
                        System.out.println("user = " + user);
                    }
                    if(parsing.size()<200){
                        System.out.println("&&&&&");
                        n=199;
                    }
                    System.out.println("parsing = " + parsing);

                }
                os.close();

            ftpClient.logout();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (ftpClient.isConnected()) {
                try {
                    ftpClient.disconnect();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (null != inputStream) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
       return map;
    }
    public void porthttp(List<User> list){
        Object obj = JSONArray.toJSON(list);
        String jsonObject = obj.toString();
        System.out.println("jsonObject = " + jsonObject);
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
        HttpPost httpPost = new HttpPost("http://172.25.3.107:30000/cess-data-3/data/api/data-access/3/users");
        httpPost.setHeader("Content-Type", "application/json;charset=utf-8");
        httpPost.setHeader("X-CESS-Data-Token", "0bf6ff0c10d641c99d519abbeec6ed35");

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
