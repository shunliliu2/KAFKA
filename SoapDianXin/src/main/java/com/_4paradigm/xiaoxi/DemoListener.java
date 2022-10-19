package com._4paradigm.xiaoxi;

import com._4paradigm.entity.Standard_media_assets;
import com._4paradigm.log.CommonLogger;
import com._4paradigm.mapper.CodeMapper;
import com._4paradigm.mapper.Server_wl;
import com._4paradigm.util.EhCacheUtil;
import com._4paradigm.util.MaterialData;
import com._4paradigm.util.Timingtask;
import com.alibaba.fastjson.JSON;
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
import org.apache.kafka.clients.producer.*;
import org.apache.kafka.common.serialization.StringSerializer;
import org.dom4j.Document;
import org.dom4j.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.KafkaListeners;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;


@Component
public class DemoListener  {

    @Value("${deletees}")
    private  String deletees;
    @Value("${insertcess}")
    private  String insertcess;
    @Value("${spring.kafka.bootstrap-servers}")
    String bootstrapServers;

    @Value("${spring.kafka.consumer.topic2}")
    String topic2;

    @Value("${wuliaoid}")
    private  String wuliaoid;
    private CodeMapper codeMapper=new CodeMapper();
    private Server_wl server_wl=new Server_wl();


    @Autowired
    private com._4paradigm.util.ftp ftp;
    private String jsonObject ="";
    @Autowired
    private MaterialData materialData;
//    @Autowired
//    private ChannelProgram channelProgram;

    @Autowired
    private Standard_media_assets standard_media_assets;
    private String title;
    private String content;
    private String categoryLevel1 = null;
    private String categoryLevel2 = null;
    private String categoryLevel3 = null;
    private String categoryLevel4 = null;
    private String categoryLevel5 = null;
    private String publishTime = null;
    private String publisherId = "";
    private String tag = "";
    private String pgmcategory="";
    private String director="";
    private String copyRight="";
    private String tags="";
    private String pgmCategory="";
    private String pgmSndClass="";
    private String compere="";
    private String cpid="";
    //第二次新增属
    private String PriceTaxInc="";
    private String Kpeople="";
    private String ScriptWriter="";
    private String Guest="";
    private String Reporter="";
    private String OPIncharge="";
    private String VSPCode="";
    private String CopyRight="";
    private String Duration="";
    private String Duration1="";
    private String Result="";
    private String ErrorDescription="";
    private String OrderNumber="";
    private String OriginalName="";
    private String SortName="";
    private String SearchName="";
    private String ActorDisplay="";
    private String WriterDisplay="";
    private String OriginalCountry="";
    private String Language="";
    private String OrgAirDate="";
    private String gener="";
    private String channelcode="";

    private String licensingwindowstart="";
    private String licensingwindowend="";



    //2022年0316新加
    private String originalcountry;
    private String contentprovider;
    private String price;


    @KafkaListeners({@KafkaListener(topics="topiciptvtelecom")})
    public void listen(ConsumerRecord<Integer, String> consumerRecord) {
        String cspid = null;
        String lspid=null;
        String correlateID=null;
        String resultFileURL=null;

        String ftpurl0="ftp://root:4paradigm_AIO@172.25.3.114:21//opt/server/ftpfile/pdaim/cg.xml";
        try {
                    System.out.println(consumerRecord);
                    System.out.println("consumerRecord vvv= " + consumerRecord.value());
                    System.out.println("consumerRecord  key= " + consumerRecord.key());
                    String value = consumerRecord.value();
                    String[] split = value.split(",");
                     cspid = split[0];
                     lspid = split[1];
                     correlateID = split[2];
                     resultFileURL = split[4];
                    try {
                        String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><SOAP-ENV:Envelope xmlns:SOAP-ENV=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:SOAP-ENC=\"http://schemas.xmlsoap.org/soap/encoding/\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:impl=\"iptv\"><SOAP-ENV:Header><CSPServerURL>http://172.25.3.203:6084/</CSPServerURL></SOAP-ENV:Header><SOAP-ENV:Body SOAP-ENV:encodingStyle=\"http://schemas.xmlsoap.org/soap/encoding/\"><impl:ResultNotify><CSPID>" + cspid + "</CSPID><LSPID>" + lspid + "</LSPID><CorrelateID>" + correlateID + "</CorrelateID><CmdResult xsi:type=\"xsd:int\">0</CmdResult><ResultFileURL>" + ftpurl0 + "</ResultFileURL></impl:ResultNotify></SOAP-ENV:Body></SOAP-ENV:Envelope>\n";

                        System.out.println("000000000");
                        gethttpIptv(xml);
                        doCdn(resultFileURL);

                    } catch (Exception e) {
                        System.out.println("000000000-1");
                        String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><SOAP-ENV:Envelope xmlns:SOAP-ENV=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:SOAP-ENC=\"http://schemas.xmlsoap.org/soap/encoding/\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:impl=\"iptv\"><SOAP-ENV:Header><CSPServerURL>http://172.25.3.203:6084/</CSPServerURL></SOAP-ENV:Header><SOAP-ENV:Body SOAP-ENV:encodingStyle=\"http://schemas.xmlsoap.org/soap/encoding/\"><impl:ResultNotify><CSPID>" + cspid + "</CSPID><LSPID>" + lspid + "</LSPID><CorrelateID>" + correlateID + "</CorrelateID><CmdResult xsi:type=\"xsd:int\">0</CmdResult><ResultFileURL>" + ftpurl0 + "</ResultFileURL></impl:ResultNotify></SOAP-ENV:Body></SOAP-ENV:Envelope>\n";

                        gethttpIptv(xml);
                        e.printStackTrace();
                    }



        } catch (Exception e) {

            String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><SOAP-ENV:Envelope xmlns:SOAP-ENV=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:SOAP-ENC=\"http://schemas.xmlsoap.org/soap/encoding/\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:impl=\"iptv\"><SOAP-ENV:Header><CSPServerURL>http://172.25.3.203:6084/</CSPServerURL></SOAP-ENV:Header><SOAP-ENV:Body SOAP-ENV:encodingStyle=\"http://schemas.xmlsoap.org/soap/encoding/\"><impl:ResultNotify><CSPID>" + cspid + "</CSPID><LSPID>" + lspid + "</LSPID><CorrelateID>" + correlateID + "</CorrelateID><CmdResult xsi:type=\"xsd:int\">0</CmdResult><ResultFileURL>" + ftpurl0 + "</ResultFileURL></impl:ResultNotify></SOAP-ENV:Body></SOAP-ENV:Envelope>\n";
            System.out.println("-1000000000");
            gethttpIptv(xml);
            e.printStackTrace();
        }




    }
    public Map doCdn(String ftpurl)throws Exception {
        Map<String, Object> respostmap = new HashMap<>();
        String hostname;
        Integer port;
        String username;
        String password;
        String fileName;
        String localPath = "/opt/server/xml/";
        //String localPath = "/Users/4paradigm/Desktop/xml/";


        String[] ftpxx = ftpurl.split("//");//先分成三份 ftp  ftp信息  文件地址
        fileName = "/" + ftpxx[2];
        //fileName =  ftpxx[2];
        String[] xinxi = ftpxx[1].split(":");
        username = xinxi[0];
        port = Integer.parseInt(xinxi[2]);
        String[] yongps = xinxi[1].split("@");
        password = yongps[0];
        hostname = yongps[1];
        System.out.println("fileName = " + fileName);
        ftp.downloadFile(hostname, port, username, password, fileName, localPath);
        String[] split = fileName.split("/");
        System.out.println("localPath + split[split.length - 1] = " + localPath + split[split.length - 1]);
        Document document = materialData.getDocument(localPath + split[split.length - 1]);
        // 传入要解析的XML文件
        //获取根节点下面的所有子节点（不包过子节点的子节点）
        Element root = null;
        try {
            root = document.getRootElement();
        } catch (Exception e) {
            CommonLogger.error("未同步成功");
        }
        int size = root.elements().size();
        List<Element> mappings2 = null;

        /**
         * 新增映射关系
         */
        try {
            if(size==1){
                mappings2 = root.element("Mappings").elements();
                for (Element element : mappings2) {
                    if(element.attributeValue("Action").equals("REGIST")&&element.attributeValue("ElementType").equals("Program")&&element.attributeValue("ParentType").equals("Category")||element.attributeValue("Action").equals("REGIST")&&element.attributeValue("ElementType").equals("Series")&&element.attributeValue("ParentType").equals("Category")){
                        System.out.println("映射");
                        String elementCode = element.attributeValue("ElementCode");//片code
                        String parentCode = element.attributeValue("ParentCode");//标签code
                        server_wl.inserturl(elementCode,parentCode);
                        //server_wl2.inserturl(elementCode,parentCode);

                    }else if(element.attributeValue("Action").equals("DELETE")&&element.attributeValue("ElementType").equals("Program")&&element.attributeValue("ParentType").equals("Category")||element.attributeValue("Action").equals("DELETE")&&element.attributeValue("ElementType").equals("Series")&&element.attributeValue("ParentType").equals("Category")){
                        String elementCode = element.attributeValue("ElementCode");//片code
                        String parentCode = element.attributeValue("ParentCode");//标签code
                        server_wl.deleteurl(elementCode,parentCode);
                        //server_wl2.deleteurl(elementCode,parentCode);
                    }
                }

            }
        } catch (Exception e) {

        }
        /**
         * 新增产品包
         */
        try {
            if(size==1){
                mappings2 = root.element("Mappings").elements();
                for (Element element : mappings2) {
                    if(element.attributeValue("Action").equals("REGIST")&&element.attributeValue("ElementType").equals("Program")&&element.attributeValue("ParentType").equals("Package")||element.attributeValue("Action").equals("REGIST")&&element.attributeValue("ElementType").equals("Series")&&element.attributeValue("ParentType").equals("Package")){
                        System.out.println("产品包");
                        String elementCode = element.attributeValue("ElementCode");//片code
                        String parentCode = element.attributeValue("ParentCode");//产品包code
                        server_wl.insertlinkUrl(elementCode,parentCode);
                        //server_wl2.insertlinkUrl(elementCode,parentCode);
                    }else if(element.attributeValue("Action").equals("DELETE")&&element.attributeValue("ElementType").equals("Program")&&element.attributeValue("ParentType").equals("Package")||element.attributeValue("Action").equals("DELETE")&&element.attributeValue("ElementType").equals("Series")&&element.attributeValue("ParentType").equals("Package")){
                        String elementCode = element.attributeValue("ElementCode");//片code
                        String parentCode = element.attributeValue("ParentCode");//产品包code
                        server_wl.deletelinkurl(elementCode,parentCode);
                        //server_wl2.deletelinkurl(elementCode,parentCode);
                    }
                }

            }
        } catch (Exception e) {

        }

        List<Element> mappings = null;
        List<Map<String, String>> type=null;
        String parentCode=null;
        String  NumberCollection=null;
        try {
            mappings = root.element("Mappings").elements();
            //获取影片的层级code
            type = getType(mappings);
            //获取单集的剧头code
            List<String> series = getSeries(mappings);
            parentCode=series.get(0);
            try {
                NumberCollection=series.get(1);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
        }

        //获取Objects节点
        try {
            List<Element> objects = root.element("Objects").elements();
            String listjson = getJson(objects,type,parentCode,NumberCollection);
        } catch (Exception e) {
            CommonLogger.error(e.toString());
        }

        return respostmap;
    }

    /**
     *
     * @param objects  xml里的objects
     * @return  listjosn    多个物料
     */
    public String getJson(List<Element> objects, List<Map<String, String>> type,String parentCode,String NumberCollection) {
        Timingtask timingtask = new Timingtask();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
        sdf.setTimeZone(TimeZone.getTimeZone("GMT+8"));
        List<Standard_media_assets> lsit = new ArrayList<>();
        String listjson="";
        for (Element object : objects) {
            //执行REGIST命令
            if (object.attributeValue("Action").equals("REGIST")&&object.attributeValue("ElementType").equals("Program")||object.attributeValue("Action").equals("REGIST")&&object.attributeValue("ElementType").equals("Series") || object.attributeValue("Action").equals("UPDATE")&&object.attributeValue("ElementType").equals("Program")||object.attributeValue("Action").equals("UPDATE")&&object.attributeValue("ElementType").equals("Series")) {
                System.out.println("object.attributeValue(\"ElementType\") = " + object.attributeValue("ElementType"));
                if (object.attributeValue("ElementType").equals("Program")) {
                    for (Element element : object.elements()) {

                        if (element.attributeValue("Name").equals("Name")) {
                            title = element.getTextTrim();
                        }
                        if (element.attributeValue("Name").equals("Description")) {
                            content = element.getTextTrim();
                        }
                        if (element.attributeValue("Name").equals("ReleaseYear")) {
                            publishTime = element.getTextTrim();
                        }
                        if (element.attributeValue("Name").equals("PgmCategory")) {
                            tag = element.getTextTrim();
                            pgmcategory=element.getTextTrim();
                        }
                        if (element.attributeValue("Name").equals("PgmSndClass")) {
                            pgmSndClass = element.getTextTrim();
                        }
                        if (element.attributeValue("Name").equals("ContentProvider")) {
                            publisherId = element.getTextTrim();
                        }
                        if (element.attributeValue("Name").equals("Kpeople")) {
                            Kpeople = element.getTextTrim();
                        }
                        if (element.attributeValue("Name").equals("Director")) {
                             director = element.getTextTrim();

                        }
                        if (element.attributeValue("Name").equals("CopyRight")) {
                             copyRight = element.getTextTrim();

                        }
                        if (element.attributeValue("Name").equals("Tags")) {
                             tags = element.getTextTrim();
                        }

                        if (element.attributeValue("Name").equals("Compere")) {
                             compere = element.getTextTrim();
                        }
                        if (element.attributeValue("Name").equals("CPID")) {
                             cpid = element.getTextTrim();
                        }
                        if (element.attributeValue("Name").equals("PriceTaxIn")) {
                            PriceTaxInc = element.getTextTrim();
                        }
                        if (element.attributeValue("Name").equals("ScriptWriter")) {
                            ScriptWriter = element.getTextTrim();
                        }if (element.attributeValue("Name").equals("Guest")) {
                            Guest = element.getTextTrim();
                        }if (element.attributeValue("Name").equals("Reporter")) {
                            Reporter = element.getTextTrim();
                        }if (element.attributeValue("Name").equals("OPIncharge")) {
                            OPIncharge = element.getTextTrim();
                        }if (element.attributeValue("Name").equals("VSPCode")) {
                            VSPCode = element.getTextTrim();
                        }if (element.attributeValue("Name").equals("CopyRight")) {
                            CopyRight = element.getTextTrim();
                        }if (element.attributeValue("Name").equals("Duration")) {
                            Duration = element.getTextTrim();
                        }if (element.attributeValue("Name").equals("Duration1")) {
                            Duration1 = element.getTextTrim();
                        }if (element.attributeValue("Name").equals("Result")) {
                            Result = element.getTextTrim();
                        }if (element.attributeValue("Name").equals("ErrorDescription")) {
                            ErrorDescription = element.getTextTrim();
                        }if (element.attributeValue("Name").equals("OrderNumber")) {
                            OrderNumber = element.getTextTrim();
                        }if (element.attributeValue("Name").equals("OriginalName")) {
                            OriginalName = element.getTextTrim();
                        }if (element.attributeValue("Name").equals("SortName")) {
                            SortName = element.getTextTrim();
                        }if (element.attributeValue("Name").equals("SearchName")) {
                            SearchName = element.getTextTrim();
                        }if (element.attributeValue("Name").equals("ActorDisplay")) {
                            ActorDisplay = element.getTextTrim();
                        }if (element.attributeValue("Name").equals("WriterDisplay")) {
                            WriterDisplay = element.getTextTrim();
                        }if (element.attributeValue("Name").equals("OriginalCountry")) {
                            OriginalCountry = element.getTextTrim();
                        }if (element.attributeValue("Name").equals("Language")) {
                            Language = element.getTextTrim();
                        }if (element.attributeValue("Name").equals("OrgAirDate")) {
                            OrgAirDate = element.getTextTrim();
                        }
                        if (element.attributeValue("Name").equals("Genre")) {
                            gener = element.getTextTrim();
                        }if (element.attributeValue("Name").equals("LicensingWindowEnd")) {
                            licensingwindowend = element.getTextTrim();
                        }if (element.attributeValue("Name").equals("LicensingWindowStart")) {
                            licensingwindowstart = element.getTextTrim();
                        }if (element.attributeValue("Name").equals("OriginalCountry")) {
                            originalcountry = element.getTextTrim();
                        }if (element.attributeValue("Name").equals("ContentProvider")) {
                            contentprovider = element.getTextTrim();
                        }
                    }
                        for (Element element : object.elements()) {
                            if (element.attributeValue("Name").equals("SeriesFlag")) {
                                if (element.getTextTrim().equals("0")) {
                                    standard_media_assets.setItemId(object.attributeValue("Code"));
                                    standard_media_assets.setItemSetId(Integer.parseInt(wuliaoid));
                                    standard_media_assets.setTitle(title);
                                    standard_media_assets.setPublisherId(publisherId);
                                    standard_media_assets.setContent(content);
                                    try {
                                        standard_media_assets.setPublishTime((sdf.parse(((String)publishTime))).getTime());
                                    } catch (Exception e) {
                                        standard_media_assets.setPublishTime(31507200000L);

                                    }
                                    standard_media_assets.setTag(tag);
                                    List<String> code = getEhcacheXml(object.attributeValue("Code"));
                                    getClassification(code);
                                    standard_media_assets.setCategoryLevel1(categoryLevel1);
                                    standard_media_assets.setCategoryLevel2(categoryLevel2);
                                    standard_media_assets.setCategoryLevel3(categoryLevel3);
                                    standard_media_assets.setCategoryLevel4(categoryLevel4);
                                    standard_media_assets.setCategoryLevel5(categoryLevel5);

                                    //新增
                                    standard_media_assets.setSeriestype("p");
                                    standard_media_assets.setCpid(cpid);
                                    standard_media_assets.setCasts(Kpeople);
                                    standard_media_assets.setDirectors(director);
                                    standard_media_assets.setBasetagsname(copyRight);
                                    standard_media_assets.setTags(tags);
                                    standard_media_assets.setPgmcategory(pgmCategory);
                                    standard_media_assets.setPgmsndclass(pgmSndClass);
                                    standard_media_assets.setCompere(compere);

                                    //第二次添加
                                    standard_media_assets.setPricetaxin(PriceTaxInc);
                                    standard_media_assets.setScriptwriter(ScriptWriter);
                                    standard_media_assets.setGuest(Guest);
                                    standard_media_assets.setReporter(Reporter);
                                    standard_media_assets.setOpincharge(OPIncharge);
                                    standard_media_assets.setVspcode(VSPCode);
                                    standard_media_assets.setCopyright(CopyRight);
                                    standard_media_assets.setDuration(Duration);
                                    System.out.println("Duration1 = " + Duration1);
                                    standard_media_assets.setShichang2(Integer.parseInt(Duration));
                                    standard_media_assets.setDuration1(Duration1);
                                    standard_media_assets.setResult(Result);
                                    standard_media_assets.setErrordescription(ErrorDescription);
                                    standard_media_assets.setOrdernumber(OrderNumber);
                                    standard_media_assets.setOriginalname(OriginalName);
                                    standard_media_assets.setSortname(SortName);
                                    standard_media_assets.setSearchname(SearchName);
                                    standard_media_assets.setActordisplay(ActorDisplay);
                                    standard_media_assets.setWriterdisplay(WriterDisplay);
                                    standard_media_assets.setOriginalcountry(OriginalCountry);
                                    standard_media_assets.setLanguage(Language);
                                    standard_media_assets.setOrgairdate(OrgAirDate);
                                    standard_media_assets.setGener(gener);
                                    standard_media_assets.setLicensingwindowend(licensingwindowend);
                                    standard_media_assets.setLicensingwindowstart(licensingwindowstart);
                                    standard_media_assets.setItemStatus(1);
                                    standard_media_assets.setOriginalcountry(originalcountry);
                                    standard_media_assets.setContentprovider(contentprovider);
                                    standard_media_assets.setPublisherId(copyRight);
                                    standard_media_assets.setItemSetId(79);

                                } else {
                                    standard_media_assets.setItemId(object.attributeValue("Code"));
                                    standard_media_assets.setItemSetId(Integer.parseInt(wuliaoid));
                                    standard_media_assets.setTitle(title);
                                    standard_media_assets.setPublisherId(publisherId);
                                    standard_media_assets.setContent(content);
                                    try {
                                        standard_media_assets.setPublishTime((sdf.parse(((String)publishTime))).getTime());
                                    } catch (Exception e) {
                                        standard_media_assets.setPublishTime(31507200000L);

                                    }
                                    standard_media_assets.setTag(tag);
                                    List<String> code2 = getEhcacheXml(object.attributeValue("Code"));
                                    getClassification(code2);
                                    standard_media_assets.setCategoryLevel1(categoryLevel1);
                                    standard_media_assets.setCategoryLevel2(categoryLevel2);
                                    standard_media_assets.setCategoryLevel3(categoryLevel3);
                                    standard_media_assets.setCategoryLevel4(categoryLevel4);
                                    standard_media_assets.setCategoryLevel5(categoryLevel5);

                                    //新增
                                    standard_media_assets.setSeriestype("p");
                                    standard_media_assets.setCpid(cpid);
                                    standard_media_assets.setCasts(Kpeople);
                                    standard_media_assets.setDirectors(director);
                                    standard_media_assets.setBasetagsname(copyRight);
                                    standard_media_assets.setTags(tags);
                                    standard_media_assets.setPgmcategory(pgmCategory);
                                    standard_media_assets.setPgmsndclass(pgmSndClass);
                                    standard_media_assets.setCompere(compere);

                                    //第二次添加
                                    standard_media_assets.setPricetaxin(PriceTaxInc);
                                    standard_media_assets.setScriptwriter(ScriptWriter);
                                    standard_media_assets.setGuest(Guest);
                                    standard_media_assets.setReporter(Reporter);
                                    standard_media_assets.setOpincharge(OPIncharge);
                                    standard_media_assets.setVspcode(VSPCode);
                                    standard_media_assets.setCopyright(CopyRight);
                                    standard_media_assets.setDuration(Duration);
                                    standard_media_assets.setShichang2(Integer.parseInt(Duration));
                                    standard_media_assets.setDuration1(Duration1);
                                    standard_media_assets.setResult(Result);
                                    standard_media_assets.setErrordescription(ErrorDescription);
                                    standard_media_assets.setOrdernumber(OrderNumber);
                                    standard_media_assets.setOriginalname(OriginalName);
                                    standard_media_assets.setSortname(SortName);
                                    standard_media_assets.setSearchname(SearchName);
                                    standard_media_assets.setActordisplay(ActorDisplay);
                                    standard_media_assets.setWriterdisplay(WriterDisplay);
                                    standard_media_assets.setOriginalcountry(OriginalCountry);
                                    standard_media_assets.setLanguage(Language);
                                    standard_media_assets.setOrgairdate(OrgAirDate);
                                    standard_media_assets.setGener(gener);
                                    standard_media_assets.setLicensingwindowend(licensingwindowend);
                                    standard_media_assets.setLicensingwindowstart(licensingwindowstart);
                                    standard_media_assets.setItemStatus(1);
                                    standard_media_assets.setOriginalcountry(originalcountry);
                                    standard_media_assets.setContentprovider(contentprovider);
                                    standard_media_assets.setPublisherId(copyRight);
                                    standard_media_assets.setItemSetId(121);
                                    try {
                                        //server_wl2.insertziji(standard_media_assets,parentCode,NumberCollection);
                                    } catch (Exception e) {

                                    }

                                    String code = object.attributeValue("Code");
                                    int select = codeMapper.select(code);
                                    if(select==0){
                                        System.out.println("code = " + code);
                                        codeMapper.insert(code,parentCode);
                                        //codeMapper.updateseries1(code);
                                        //server_wl.selectcode1(parentCode);
                                        //timingtask.getupdatees1(code);
                                    }else {
                                        System.out.println("parentCode = " + parentCode);
                                        codeMapper.insert(code,parentCode);
                                        //codeMapper.updatesingle1(code);
                                        //codeMapper.updateseries1(parentCode);
                                        //server_wl.selectcode1(parentCode);
                                        //timingtask.getupdatees1(parentCode);
                                    }
                                    standard_media_assets=new Standard_media_assets();

                                }
                            }

                        }


                    } else if (object.attributeValue("ElementType").equals("Series")) {

                    for (Element element : object.elements()) {
                        if (element.attributeValue("Name").equals("Name")) {
                            title = element.getTextTrim();
                            standard_media_assets.setTitle(title);

                        }
                        if (element.attributeValue("Name").equals("Description")) {
                            content = element.getTextTrim();
                            standard_media_assets.setContent(content);
                        }
                        if (element.attributeValue("Name").equals("ReleaseYear")) {
                            publishTime = element.getTextTrim();
                            try {
                                standard_media_assets.setPublishTime((sdf.parse(((String)publishTime))).getTime());
                            } catch (Exception e) {
                                standard_media_assets.setPublishTime(31507200000L);
                            }
                        }
                        if (element.attributeValue("Name").equals("PgmCategory")) {
                            tag = element.getTextTrim();
                            standard_media_assets.setTag(tag);
                        }
                        if (element.attributeValue("Name").equals("PgmSndClass")) {
                            pgmSndClass = tag + "," + element.getTextTrim();
                            standard_media_assets.setPgmsndclass(pgmSndClass);
                        }
                        if (element.attributeValue("Name").equals("ContentProvider")) {
                            publisherId = element.getTextTrim();
                            standard_media_assets.setPublisherId(publisherId);
                        } if (element.attributeValue("Name").equals("Kpeople")) {
                            Kpeople = element.getTextTrim();
                            standard_media_assets.setCasts(Kpeople);
                        }
                        if (element.attributeValue("Name").equals("Director")) {
                            director = element.getTextTrim();
                            standard_media_assets.setDirectors(director);

                        }
                        if (element.attributeValue("Name").equals("CopyRight")) {
                            copyRight = element.getTextTrim();
                            standard_media_assets.setBasetagsname(copyRight);
                        }
                        if (element.attributeValue("Name").equals("Tags")) {
                            tags = element.getTextTrim();
                            standard_media_assets.setTags(tags);
                        }
                        if (element.attributeValue("Name").equals("Compere")) {
                            compere = element.getTextTrim();
                            standard_media_assets.setCompere(compere);
                        }
                        if (element.attributeValue("Name").equals("CPID")) {
                            cpid = element.getTextTrim();
                            standard_media_assets.setCpid(cpid);
                        }
                        //System.out.println("----------------------------------------------------------");
                        if (element.attributeValue("Name").equals("PriceTaxIn")) {
                            PriceTaxInc = element.getTextTrim();
                        }
                        if (element.attributeValue("Name").equals("ScriptWriter")) {
                            ScriptWriter = element.getTextTrim();
                        }if (element.attributeValue("Name").equals("Guest")) {
                            Guest = element.getTextTrim();
                        }if (element.attributeValue("Name").equals("Reporter")) {
                            Reporter = element.getTextTrim();
                        }if (element.attributeValue("Name").equals("OPIncharge")) {
                            OPIncharge = element.getTextTrim();
                        }if (element.attributeValue("Name").equals("VSPCode")) {
                            VSPCode = element.getTextTrim();
                        }if (element.attributeValue("Name").equals("CopyRight")) {
                            CopyRight = element.getTextTrim();
                        }if (element.attributeValue("Name").equals("Duration")) {
                            Duration = element.getTextTrim();
                        }if (element.attributeValue("Name").equals("Duration1")) {
                            Duration1 = element.getTextTrim();
                        }if (element.attributeValue("Name").equals("Result")) {
                            Result = element.getTextTrim();
                        }if (element.attributeValue("Name").equals("ErrorDescription")) {
                            ErrorDescription = element.getTextTrim();
                        }if (element.attributeValue("Name").equals("OrderNumber")) {
                            OrderNumber = element.getTextTrim();
                        }if (element.attributeValue("Name").equals("OriginalName")) {
                            OriginalName = element.getTextTrim();
                        }if (element.attributeValue("Name").equals("SortName")) {
                            SortName = element.getTextTrim();
                        }if (element.attributeValue("Name").equals("SearchName")) {
                            SearchName = element.getTextTrim();
                        }if (element.attributeValue("Name").equals("ActorDisplay")) {
                            ActorDisplay = element.getTextTrim();
                        }if (element.attributeValue("Name").equals("WriterDisplay")) {
                            WriterDisplay = element.getTextTrim();
                        }if (element.attributeValue("Name").equals("OriginalCountry")) {
                            OriginalCountry = element.getTextTrim();
                        }if (element.attributeValue("Name").equals("Language")) {
                            Language = element.getTextTrim();
                        }if (element.attributeValue("Name").equals("OrgAirDate")) {
                            OrgAirDate = element.getTextTrim();
                        }
                        if (element.attributeValue("Name").equals("Genre")) {
                            gener = element.getTextTrim();
                        }if (element.attributeValue("Name").equals("LicensingWindowEnd")) {
                            licensingwindowend = element.getTextTrim();
                        }if (element.attributeValue("Name").equals("LicensingWindowStart")) {
                            licensingwindowstart = element.getTextTrim();
                        }
                        if (element.attributeValue("Name").equals("Price")) {
                            price= element.getTextTrim();
                        }if (element.attributeValue("Name").equals("ContentProvider")) {
                            contentprovider = element.getTextTrim();
                        }if (element.attributeValue("Name").equals("OriginalCountry")) {
                            originalcountry = element.getTextTrim();
                        }
                        standard_media_assets.setSeriestype("s");
                        standard_media_assets.setItemId(object.attributeValue("Code"));
                        codeMapper.insert0(object.attributeValue("Code"));

                        standard_media_assets.setPgmcategory(tag);
                        List<String> code = getEhcacheXml(object.attributeValue("Code"));
                        getClassification(code);
                        standard_media_assets.setCategoryLevel1(categoryLevel1);
                        standard_media_assets.setCategoryLevel2(categoryLevel2);
                        standard_media_assets.setCategoryLevel3(categoryLevel3);
                        standard_media_assets.setCategoryLevel4(categoryLevel4);
                        standard_media_assets.setCategoryLevel5(categoryLevel5);

                        //第二次添加
                        standard_media_assets.setPricetaxin(PriceTaxInc);
                        standard_media_assets.setItemSetId(Integer.parseInt(wuliaoid));
                        standard_media_assets.setScriptwriter(ScriptWriter);
                        standard_media_assets.setGuest(Guest);
                        standard_media_assets.setReporter(Reporter);
                        standard_media_assets.setOpincharge(OPIncharge);
                        standard_media_assets.setVspcode(VSPCode);
                        standard_media_assets.setCopyright(CopyRight);
                        standard_media_assets.setDuration(Duration);
                        standard_media_assets.setDuration1(Duration1);
                        standard_media_assets.setResult(Result);
                        standard_media_assets.setErrordescription(ErrorDescription);
                        standard_media_assets.setOrdernumber(OrderNumber);
                        standard_media_assets.setOriginalname(OriginalName);
                        standard_media_assets.setSortname(SortName);
                        standard_media_assets.setSearchname(SearchName);
                        standard_media_assets.setActordisplay(ActorDisplay);
                        standard_media_assets.setWriterdisplay(WriterDisplay);
                        standard_media_assets.setOriginalcountry(OriginalCountry);
                        standard_media_assets.setLanguage(Language);
                        standard_media_assets.setOrgairdate(OrgAirDate);
                        standard_media_assets.setGener(gener);
                        standard_media_assets.setLicensingwindowend(licensingwindowend);
                        standard_media_assets.setLicensingwindowstart(licensingwindowstart);
                        standard_media_assets.setItemStatus(0);
                        standard_media_assets.setPrice(price);
                        standard_media_assets.setPublisherId(copyRight);
                        standard_media_assets.setItemSetId(79);
                        standard_media_assets.setContentprovider(contentprovider);
                        standard_media_assets.setOriginalcountry(OriginalCountry);
                        //server_wl.insert(standard_media_assets);

                    }

                } else if (object.attributeValue("ElementType").equals("Category")) {
                    EhCacheUtil ehCacheUtil = EhCacheUtil.getInstance();
                    String objectValue = (String) ehCacheUtil.get(EhCacheUtil.TAXI_CACHE, EhCacheUtil.GROUP_VEHICLE_TREE_KEY);
                    Map<String, String> parse = (Map) JSONArray.parse(objectValue);
                    parse.put(object.attributeValue("Code"), object.attributeValue("ParentCode"));
                    cache(parse);
                }
                //执行DELETE命令
            } else if (object.attributeValue("Action").equals("DELETE")) {
                System.out.println("object.attributeValue(\"ElementType\") = " + object.attributeValue("ElementType"));
                if(object.attributeValue("ElementType").equals("Series")){

                    System.out.println("object.attributeValue(\"Code\")"+object.attributeValue("Code"));
                    String responseEntityString = post_delete_es(object.attributeValue("Code"));
                    Map<String,Object> map = (Map<String, Object>) JSONArray.parse(responseEntityString);
                    int total = (int)map.get("total");
                    if(total==0){
                        codeMapper.updatesingle0(object.attributeValue("Code"));
                    }
                    CommonLogger.info("删除了这个剧集"+object.attributeValue("Code"));
                    post_delete_es(object.attributeValue("Code"));

                    //删除随心看物料库
                    //post_delete_es_suixinkan(object.attributeValue("Code"));



                }else if (object.attributeValue("ElementType").equals("Program")){
                    //直接在es里删除这条记录
                    post_delete_es(object.attributeValue("Code"));

                    //删除随心看物料库
                    //post_delete_es_suixinkan(object.attributeValue("Code"));
                    //Timingtask.getupdatees(object.attributeValue("code"));
                    //重启保底
                    //setBaodi();
                    CommonLogger.info("删除了这个电影"+object.attributeValue("Code"));
                }



            }
//            /**
//             * 新增频道工单
//             */
//            else if (object.attributeValue("Action").equals("REGIST")&&object.attributeValue("ElementType").equals("Channel")||object.attributeValue("Action").equals("REGIST")&&object.attributeValue("ElementType").equals("Channel")){
//                String code = object.attributeValue("Code");
//                standard_media_assets.setItemId(code);
//                for (Element element : object.elements()) {
//
//                    if (element.attributeValue("Name").equals("Name")) {
//                        title = element.getTextTrim();
//                        standard_media_assets.setTitle(title);
//
//                    }
//                }
//                standard_media_assets.setItemSetId(79);
//                standard_media_assets.setContent(" ");
//                standard_media_assets.setPublishTime(System.currentTimeMillis());
//                channelProgram.insertChannel(standard_media_assets);
//
//            }
//            /**
//             * 新增节目单 工单
//             */
//            else if (object.attributeValue("Action").equals("REGIST")&&object.attributeValue("ElementType").equals("Schedule")||object.attributeValue("Action").equals("REGIST")&&object.attributeValue("ElementType").equals("Schedule")){
//                String code = object.attributeValue("Code");
//                standard_media_assets.setItemId(code);
//                for (Element element : object.elements()) {
//
//                    if (element.attributeValue("Name").equals("Name")) {
//                        title = element.getTextTrim();
//                        standard_media_assets.setTitle(title);
//
//                    }
//                    if (element.attributeValue("Name").equals("ChannelCode")) {
//                        channelcode = element.getTextTrim();
//                        standard_media_assets.setChannelcode(channelcode);
//
//                    }
//                }
//                standard_media_assets.setItemSetId(79);
//                standard_media_assets.setContent(" ");
//                standard_media_assets.setPublishTime(System.currentTimeMillis());
//
//            }

            if (object.attributeValue("ElementType").equals("Picture")) {
            try {
                final int[] ccccc = {0};
                Map<String, String> integerStringMap = type.get(0);
                integerStringMap.forEach((key, value) -> {
                    System.out.println("Key: " + key + ", Value: " + value);
                    if (key.equals("0") ) {
                        standard_media_assets.setType0(key);
                        String gettype = gettype(objects, value);
                        String[] split = gettype.split("/");
                        String ss=split[split.length-2]+"/"+split[split.length-1];
                        standard_media_assets.setType0_url(ss);
                    } else if (key.equals("1")) {
                        ccccc[0] = ccccc[0] +1;
                        System.out.println("ccccc[0] = " + ccccc[0]);
                        standard_media_assets.setType1(key);
                        String gettype = gettype(objects, value);
                        String[] split = gettype.split("/");
                        String ss=split[split.length-2]+"/"+split[split.length-1];
                        standard_media_assets.setType1_url(ss);

                    } else if (key.equals("2")) {
                        standard_media_assets.setType2(key);
                        String gettype = gettype(objects, value);
                        String[] split = gettype.split("/");
                        String ss=split[split.length-2]+"/"+split[split.length-1];
                        standard_media_assets.setType2_url(ss);

                    } else if (key.equals("3")) {
                        standard_media_assets.setType3(key);
                        String gettype = gettype(objects, value);
                        String[] split = gettype.split("/");
                        String ss=split[split.length-2]+"/"+split[split.length-1];
                        standard_media_assets.setType3_url(ss);

                    } else if  (key.equals("4"))  {
                        standard_media_assets.setType4(key);
                        String gettype = gettype(objects, value);
                        String[] split = gettype.split("/");
                        String ss=split[split.length-2]+"/"+split[split.length-1];
                        standard_media_assets.setType4_url(ss);

                    } else if  (key.equals("5"))  {
                        standard_media_assets.setType5(key);
                        String gettype = gettype(objects, value);
                        String[] split = gettype.split("/");
                        String ss=split[split.length-2]+"/"+split[split.length-1];
                        standard_media_assets.setType5_url(ss);

                    } else if  (key.equals("6"))  {
                        standard_media_assets.setType6(key);
                        String gettype = gettype(objects, value);
                        String[] split = gettype.split("/");
                        String ss=split[split.length-2]+"/"+split[split.length-1];
                        standard_media_assets.setType6_url(ss);

                    } else if  (key.equals("7"))  {
                        standard_media_assets.setType7(key);
                        String gettype = gettype(objects, value);
                        String[] split = gettype.split("/");
                        String ss=split[split.length-2]+"/"+split[split.length-1];
                        standard_media_assets.setType7_url(ss);

                    } else if  (key.equals("9"))  {
                        standard_media_assets.setType9(key);
                        String gettype = gettype(objects, value);
                        String[] split = gettype.split("/");
                        String ss=split[split.length-2]+"/"+split[split.length-1];
                        standard_media_assets.setType9_url(ss);
                    } else if  (key.equals("10"))  {
                        standard_media_assets.setType10(key);
                        String gettype = gettype(objects, value);
                        String[] split = gettype.split("/");
                        String ss=split[split.length-2]+"/"+split[split.length-1];
                        standard_media_assets.setType10_url(ss);

                    } else if  (key.equals("11")) {
                        standard_media_assets.setType11(key);
                        String gettype = gettype(objects, value);
                        String[] split = gettype.split("/");
                        String ss=split[split.length-2]+"/"+split[split.length-1];
                        standard_media_assets.setType11_url(ss);

                    } else if  (key.equals("12"))  {
                        standard_media_assets.setType12(key);
                        String gettype = gettype(objects, value);
                        String[] split = gettype.split("/");
                        String ss=split[split.length-2]+"/"+split[split.length-1];
                        standard_media_assets.setType12_url(ss);
                    } else if  (key.equals("20"))  {
                        standard_media_assets.setType20(key);
                        String gettype = gettype(objects, value);
                        String[] split = gettype.split("/");
                        String ss=split[split.length-2]+"/"+split[split.length-1];
                        standard_media_assets.setType20_url(ss);

                    } else if  (key.equals("21"))  {
                        standard_media_assets.setType21(key);
                        String gettype = gettype(objects, value);
                        String[] split = gettype.split("/");
                        String ss=split[split.length-2]+"/"+split[split.length-1];
                        standard_media_assets.setType21_url(ss);

                    } else if  (key.equals("22"))  {
                        standard_media_assets.setType22(key);
                        String gettype = gettype(objects, value);
                        String[] split = gettype.split("/");
                        String ss=split[split.length-2]+"/"+split[split.length-1];
                        standard_media_assets.setType22_url(ss);

                    } else if  (key.equals("23"))  {
                        standard_media_assets.setType23(key);
                        String gettype = gettype(objects, value);
                        String[] split = gettype.split("/");
                        String ss=split[split.length-2]+"/"+split[split.length-1];
                        standard_media_assets.setType23_url(ss);

                    } else if  (key.equals("24"))  {
                        standard_media_assets.setType24(key);
                        String gettype = gettype(objects, value);
                        String[] split = gettype.split("/");
                        String ss=split[split.length-2]+"/"+split[split.length-1];
                        standard_media_assets.setType24_url(ss);

                    } else if  (key.equals("25"))  {
                        standard_media_assets.setType25(key);
                        String gettype = gettype(objects, value);
                        String[] split = gettype.split("/");
                        String ss=split[split.length-2]+"/"+split[split.length-1];
                        standard_media_assets.setType25_url(ss);

                    } else if  (key.equals("26"))  {
                        standard_media_assets.setType26(key);
                        String gettype = gettype(objects, value);
                        String[] split = gettype.split("/");
                        String ss=split[split.length-2]+"/"+split[split.length-1];
                        standard_media_assets.setType26_url(ss);

                    } else if  (key.equals("27"))  {
                        standard_media_assets.setType27(key);
                        String gettype = gettype(objects, value);
                        String[] split = gettype.split("/");
                        String ss=split[split.length-2]+"/"+split[split.length-1];
                        standard_media_assets.setType27_url(ss);

                    } else if  (key.equals("28"))  {
                        standard_media_assets.setType28(key);
                        String gettype = gettype(objects, value);
                        String[] split = gettype.split("/");
                        String ss=split[split.length-2]+"/"+split[split.length-1];
                        standard_media_assets.setType28_url(ss);

                    } else if  (key.equals("29"))  {
                        standard_media_assets.setType29(key);
                        String gettype = gettype(objects, value);
                        String[] split = gettype.split("/");
                        String ss=split[split.length-2]+"/"+split[split.length-1];
                        standard_media_assets.setType29_url(ss);

                    } else {
                        standard_media_assets.setType99("99");
                        String gettype = gettype(objects, value);
                        String[] split = gettype.split("/");
                        String ss=split[split.length-2]+"/"+split[split.length-1];
                        standard_media_assets.setType99_url(ss);

                    }
                }
                );
                System.out.println("ccccc[0] = " + ccccc[0]);

                if(ccccc[0]==0){
                    standard_media_assets.setType1_url("===");
                }
            }catch (Exception e) {
            }
        }
        }
        if(standard_media_assets.getItemId()==null){
            standard_media_assets=new Standard_media_assets();
        }
        Object o = JSONArray.toJSON(standard_media_assets);
        listjson = o.toString();
        //取消下发工单上报数据

        try {
            server_wl.insert(standard_media_assets);
            //server_wl2.insert(standard_media_assets);

        } catch (Exception e) {

        }

        standard_media_assets = new Standard_media_assets();
        title=content=publishTime=publisherId=tag=pgmcategory=originalcountry=contentprovider=price=channelcode=null;
        categoryLevel1=categoryLevel2=categoryLevel3=categoryLevel4=categoryLevel5= null;
        director=copyRight=tags=pgmCategory=pgmSndClass=compere=cpid=null;
        OrgAirDate=Language=PriceTaxInc=Kpeople=ScriptWriter=Guest=Reporter=OPIncharge=VSPCode=CopyRight=Duration=Duration1=Result=ErrorDescription=OrderNumber=OriginalName=SortName=SearchName=ActorDisplay=WriterDisplay=OriginalCountry=null;
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
        }

        return listjson;
    }
    /**
     * 获取mapings里的type
     * @param mappings
     * @return  type有值的，
     */
    public List<Map<String,String>> getType(List<Element> mappings){
        List<Map<String,String>> objects = new ArrayList<>();
        String parentID = null;
        Map<String, String> integerStringHashMap = new HashMap<>();
        for (Element mapping : mappings) {
            if (mapping.attributeValue("ParentType").equals("Picture")){
                List<Element> elements = mapping.elements();
                for (Element element : elements) {
                    if(element.attributeValue("Name").equals("Type")){
                        if(!element.getText().equals(null)) {
                            parentID = mapping.attributeValue("ParentID");
                            integerStringHashMap.put(element.getText(),parentID);
                        }
                    }
                }
            }

        }
        objects.add(integerStringHashMap);
        return objects;
    }

    /**
     *
     * @param mappings  xml里的mappings标签
     * @return  返回单集的剧头code
     */
    public List<String> getSeries(List<Element> mappings){
        String parentCode = null;
        String elementCode = null;
        String sequence =null;
        List<String> ListParentCodeAndSequence = new ArrayList<>();
        for (Element mapping : mappings) {
            if(mapping.attributeValue("ParentType").equals("Series")){
                 parentCode = mapping.attributeValue("ParentCode");
                elementCode = mapping.attributeValue("ElementCode");

                ListParentCodeAndSequence.add(parentCode);
                List<Element> elements = mapping.elements();
                for (Element element : elements) {
                    if (element.attributeValue("Name").equals("Sequence")) {
                        sequence = element.getTextTrim();
                        ListParentCodeAndSequence.add(sequence);
                    }
                }

            }
        }

        Map testMap = new HashMap<>();
        testMap.put("parentCode",parentCode);
        testMap.put("elementCode",elementCode);
        testMap.put("sequence",sequence);
        String str = JSON.toJSONString(testMap);


        Map<String, Object> configs = new HashMap<>();
        configs.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,bootstrapServers);
        configs.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        configs.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);

        KafkaProducer<String,String> producer = new KafkaProducer<>(configs);
        producer.send(new ProducerRecord<>(topic2, str), new Callback() {
            @Override
            public void onCompletion(RecordMetadata metadata, Exception exception) {
                if(exception == null){
                }
            }
        });
        producer.close();


        return ListParentCodeAndSequence;
    }

    /**
     *
     * @param code  需要获取父code  的code
     * @return  返回子类的父code的集合
     */
    public List<String> getEhcacheXml(String code){
        EhCacheUtil ehCacheUtil =EhCacheUtil.getInstance();
        String objectValue = (String)ehCacheUtil.get(EhCacheUtil.TAXI_CACHE, EhCacheUtil.GROUP_VEHICLE_TREE_KEY);
        Map<String,String> parse = (Map)JSONArray.parse(objectValue);

        final List<String>[] list = new List[]{new ArrayList<>()};
        try {
            while (!code.equals("0")) {
                list[0].add(parse.get(code));
                code= parse.get(code);
            }
        } catch (Exception e) {
            //e.printStackTrace();
        }
        return list[0];
    }
    /**
     *
     * @param map  需要缓存的map
     */
    public String cache(Map<String,String> map){
        EhCacheUtil ehCacheUtil = EhCacheUtil.getInstance();
        Object obj = JSONArray.toJSON(map);
        jsonObject = obj.toString();
        ehCacheUtil.put(EhCacheUtil.TAXI_CACHE, EhCacheUtil.GROUP_VEHICLE_TREE_KEY,jsonObject);
        return jsonObject;
    }

    /**
     *
     * @param list  传过来的xml  父code     判断有几级code
     */
    public void getClassification(List<String> list){
        int size = list.size();
        if (size==2){
            categoryLevel1=list.get(0);
        }else if(size==3){
            categoryLevel1=list.get(0);
            categoryLevel2=list.get(1);

        }else if(size==4){
            categoryLevel1=list.get(0);
            categoryLevel2=list.get(1);
            categoryLevel3=list.get(2);

        }else if(size==5){
            categoryLevel1=list.get(0);
            categoryLevel2=list.get(1);
            categoryLevel3=list.get(2);
            categoryLevel4=list.get(3);

        }else if(size==6){
            categoryLevel1=list.get(0);
            categoryLevel2=list.get(1);
            categoryLevel3=list.get(2);
            categoryLevel4=list.get(3);
            categoryLevel5=list.get(4);
        }
    }



    public String gettype(List<Element> objects,String code) {
        String textTrim = null;
        for (Element object : objects) {
            if (object.attributeValue("ElementType").equals("Picture") && object.attributeValue("Code").equals(code)) {
                List<Element> elements = object.elements();
                for (Element element : elements) {
                    if (element.attributeValue("Name").equals("FileURL")) {
                        textTrim = element.getTextTrim();
                    }
                }
            }
        }
        System.out.println("textTrim = " + textTrim);
        return textTrim;
    }
    /**
     * 给IPTV这边发相应
     * @param json
     */
    public void gethttpIptv(String json){
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
        HttpPost httpPost = new HttpPost("http://172.25.3.167:6060/ctmsdispatcher/services/ctms_smg");
        httpPost.setHeader("Content-Type", "application/json;charset=utf-8");
        httpPost.setHeader("SOAPAction","");
        StringEntity stringEntity = new StringEntity(json, "UTF-8");
        httpPost.setEntity(stringEntity);

        // 响应模型
        CloseableHttpResponse response = null;
        try {
            // 由客户端执行(发送)Post请求
            response = httpClient.execute(httpPost);
            // 从响应模型中获取响应实体
            HttpEntity responseEntity = response.getEntity();

            if (responseEntity != null) {
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


    public String post_delete_es(String code){
        String responseEntityString=null;
        String json ="{\n" +
                "\"query\": {\n" +
                "    \"match\": {\n" +
                "      \"_id\": \""+code+"\"\n" +
                "    }\n" +
                "  }\n" +
                "}";
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
        HttpPost httpPost = new HttpPost(deletees);
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

            if (responseEntity != null) {
                System.out.println("响应内容长度为:" + responseEntity.getContentLength());
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
     * 删除随心看物料库数据
     * @param code 删除物料code
     * @return
     */
    public String post_delete_es_suixinkan(String code){
        String responseEntityString=null;
        String json ="{\n" +
                "\"query\": {\n" +
                "    \"match\": {\n" +
                "      \"_id\": \""+code+"\"\n" +
                "    }\n" +
                "  }\n" +
                "}";
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
        HttpPost httpPost = new HttpPost("http://172.25.3.110:9200/business_13_itemset_121_item/_delete_by_query");
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

            if (responseEntity != null) {
                System.out.println("响应内容长度为:" + responseEntity.getContentLength());
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

}
