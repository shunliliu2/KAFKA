package com._4paradigm.xiaoxi;
import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.StringRequestEntity;
public class httpPost {

    /**
     * 发送xml数据请求到server端
     * @param url xml请求数据地址
     * @param xmlString 发送的xml数据流
     * @return null发送失败，否则返回响应内容
     */
    public static String post(String url,String xmlString){
        //关闭合同谈判
        System.setProperty("org.apache.commons.logging.Log", "org.apache.commons.logging.impl.SimpleLog");
        System.setProperty("org.apache.commons.logging.simplelog.showdatetime", "true");
        System.setProperty("org.apache.commons.logging.simplelog.log.org.apache.commons.httpclient", "stdout");
        HttpClient client = new HttpClient();
        PostMethod myPost = new PostMethod(url);
        client.getParams().setSoTimeout(300*1000);

        String responseString = null;
        try{
            myPost.setRequestEntity(new StringRequestEntity(xmlString,"text/xml","utf-8"));
            int statusCode = client.executeMethod(myPost);
            if(statusCode == HttpStatus.SC_OK){
                BufferedInputStream bis = new BufferedInputStream(myPost.getResponseBodyAsStream());
                byte[] bytes = new byte[1024];
                ByteArrayOutputStream bos = new ByteArrayOutputStream();
                int count = 0;
                while((count = bis.read(bytes))!= -1){
                    bos.write(bytes, 0, count);
                }
                byte[] strByte = bos.toByteArray();
                responseString = new String(strByte,0,strByte.length,"utf-8");
                bos.close();
                bis.close();
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
        myPost.releaseConnection();
        client.getHttpConnectionManager().closeIdleConnections(0);
        return responseString;
    }

    public static void main(String[] args) {
        String post = post("http://localhost:8082/SOAP/soap/ExecCmdReq", "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                "<SOAP-ENV:Envelope xmlns:SOAP-ENV=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:SOAP-ENC=\"http://schemas.xmlsoap.org/soap/encoding/\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:impl=\"iptv\">\n" +
                "<SOAP-ENV:Body SOAP-ENV:encodingStyle=\"http://schemas.xmlsoap.org/soap/encoding/\">\n" +
                "<impl:ExecCmd>\n" +
                "<CSPID xsi:type=\"xsd:string\">LTSOP</CSPID>\n" +
                "<LSPID xsi:type=\"xsd:string\">LTDS</LSPID>\n" +
                "<CorrelateID xsi:type=\"xsd:string\">40241687</CorrelateID>\n" +
                "<CmdFileURL xsi:type=\"xsd:string\">ftp://root:root@192.168.1.5:21//数据111/新增节目/BTVSOP_4688794_BTVBK_smp_18533797-144970555-1-9-69568154-1-11.xml</CmdFileURL>\n" +
                "</impl:ExecCmd>\n" +
                "</SOAP-ENV:Body>\n" +
                "</SOAP-ENV:Envelope>");
        System.out.println("post = " + post);
    }


}
