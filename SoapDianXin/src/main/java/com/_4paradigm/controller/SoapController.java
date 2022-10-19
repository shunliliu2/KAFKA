package com._4paradigm.controller;

import com._4paradigm.log.CommonLogger;
import org.apache.kafka.clients.producer.*;
import org.apache.kafka.common.serialization.StringSerializer;
import org.apache.soap.util.xml.XMLParserUtils;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/soap")
public class SoapController {
    @Value("${CSPID}")
    private String cspID;
    @Value("${LSPID}")
    private String  lspID;
    @Value("${spring.kafka.bootstrap-servers}")
    String bootstrapServers;
    @Value("${spring.kafka.consumer.topic}")
    String topic;

    @RequestMapping(value = "/dopost", method = RequestMethod.POST)
    @ResponseBody
    public String doPost(HttpServletRequest request) throws InterruptedException {
        String cspid="";
        String lspid="";
        String ftpurl="";
        String correlateid="";

        Map<String, Object> respostmap = new HashMap<>();
        // Traverse the HTTP headers and show them on the screen
        for (Enumeration lll = request.getHeaderNames(); lll.hasMoreElements(); ) {
            String header = (String) lll.nextElement();
            String value = request.getHeader(header);
        }
        if (request.getContentLength() > 0) {
            try {
                java.io.BufferedReader reader = request.getReader();

                // 获取DocumentBuilder
                javax.xml.parsers.DocumentBuilder xdb =
                        XMLParserUtils.getXMLDocBuilder();

                // 接下来我们将文件解析为一个DOM树，得到一个Document对象。
                org.w3c.dom.Document doc =
                        xdb.parse(new org.xml.sax.InputSource(reader));
                if (doc == null) {
                    // Error occured
                    throw new org.apache.soap.SOAPException
                            (org.apache.soap.Constants.FAULT_CODE_CLIENT, "parsing error");
                } else {
                    //在接收端我们已经有了一个发送过来的SOAP封套。SOAP封套是SOAP文档的最外层元素，也是根元素。我们可以遍历这个DOM树从而直接得到封套以及它的子节点。通过调用unmarshall()方法从文件中得到一个Envelope实例。
                    org.apache.soap.Envelope env =
                            org.apache.soap.Envelope.unmarshall(doc.getDocumentElement());
                    // 现在我们得到了一个封套，我们按照和前面相反的过程来操作它：从Envelope中取得BodyEntrys的向量Vector，然后从向量中取得Body。
                    org.apache.soap.Body body = env.getBody();
                    java.util.Vector bodyEntries = body.getBodyEntries();
                    java.io.StringWriter writer = new java.io.StringWriter();
                    for (java.util.Enumeration e = bodyEntries.elements(); e.hasMoreElements(); ) {
                        org.w3c.dom.Element el = (org.w3c.dom.Element) e.nextElement();


                        //在当前情况下，向量中只有一个条目：<PurchaseOrder>元素。从而我们也就得到了一个DOM对象。这个DOM对象和前面我们为建立PO.xml而建立的DOM对象完全一样。我们调用静态方法DOM2Writer.serializeAsXML()将PurchaseOrder元素及其子元素全部序列化为一个StringWriter对象。
                        org.apache.soap.util.xml.DOM2Writer.serializeAsXML((org.w3c.dom.Node) el, writer);
                    }
                    String value = writer.toString();
                    org.dom4j.Document document = DocumentHelper.parseText(value);
                    Element root = document.getRootElement();
                    //上游约定
                    Element CSPID = root.element("CSPID");
                    cspid = CSPID.getTextTrim();

                    //下游约定
                    Element LSPID = root.element("LSPID");
                    lspid = LSPID.getTextTrim();

                    Element cmdFileURL = root.element("CmdFileURL");
                    ftpurl = cmdFileURL.getTextTrim();
                    Element correlateID = root.element("CorrelateID");
                    correlateid = correlateID.getTextTrim();

                }
            } catch (Exception e) {
                CommonLogger.error("",e);

            }
        }
        if (cspID.equals(cspid)&&lspID.equals(lspID)) {
            String s = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><SOAP-ENV:Envelope xmlns:SOAP-ENV=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:SOAP-ENC=\"http://schemas.xmlsoap.org/soap/encoding/\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:impl=\"iptv\"><SOAP-ENV:Body SOAP-ENV:encodingStyle=\"http://schemas.xmlsoap.org/soap/encoding/\"><impl:ExecCmdResponse><ExecCmdReturn xsi:type=\"impl:CSPResult\"><Result xsi:type=\"xsd:int\">0</Result><ErrorDescription>Received request OK.</ErrorDescription></ExecCmdReturn></impl:ExecCmdResponse></SOAP-ENV:Body></SOAP-ENV:Envelope>";
            Map<String, Object> configs = new HashMap<>();
            configs.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,bootstrapServers);
            configs.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
            configs.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);

            KafkaProducer<String,String> producer = new KafkaProducer<>(configs);
                producer.send(new ProducerRecord<>(topic, lspID+","+cspid+","+correlateid+","+0+","+ftpurl), new Callback() {
                    @Override
                    public void onCompletion(RecordMetadata metadata, Exception exception) {
                        if(exception == null){
                        }
                    }
                });
                producer.close();


                return s;
        }else {
            String s = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><SOAP-ENV:Envelope xmlns:SOAP-ENV=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:SOAP-ENC=\"http://schemas.xmlsoap.org/soap/encoding/\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:impl=\"iptv\"><SOAP-ENV:Body SOAP-ENV:encodingStyle=\"http://schemas.xmlsoap.org/soap/encoding/\"><impl:ExecCmdResponse><ExecCmdReturn xsi:type=\"impl:CSPResult\"><Result xsi:type=\"xsd:int\">-1</Result><ErrorDescription>CSPID和LSPID不正确</ErrorDescription></ExecCmdReturn></impl:ExecCmdResponse></SOAP-ENV:Body></SOAP-ENV:Envelope>";
            CommonLogger.error("CSPID和LSPID不正确");
            return s;
        }
    }

}
