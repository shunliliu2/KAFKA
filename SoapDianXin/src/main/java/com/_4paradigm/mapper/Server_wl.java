package com._4paradigm.mapper;

import com._4paradigm.entity.C2Wai;
import com._4paradigm.entity.New_Standard_media_assets;
import com._4paradigm.entity.Standard_media_assets;
import com._4paradigm.entity.User;
import com.alibaba.fastjson.JSONArray;
import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;


/**
 * `itemId` char(200) NOT NULL,
 *   `title` text,
 *   `OriginalName` char(200) DEFAULT NULL,
 *   `SearchName` char(200) DEFAULT NULL,
 *   `content` text,
 *   `casts` char(200) DEFAULT NULL,
 *   `directors` char(200) DEFAULT NULL,
 *   `ScriptWriter` char(200) DEFAULT NULL,
 *   `compere` char(200) DEFAULT NULL,
 *   `Guest` char(200) DEFAULT NULL,
 *   `Reporter` char(200) DEFAULT NULL,
 *   `OPIncharge` char(200) DEFAULT NULL,
 *   `LICENSINGWINDOWSTART` char(200) DEFAULT NULL,
 *   `LICENSINGWINDOWEMD` char(200) DEFAULT NULL,
 *   `VOLUNMCOUNT` char(200) DEFAULT NULL,
 *   `VSPCode` char(200) DEFAULT NULL,
 *   `publishTime1` char(200) DEFAULT NULL,
 *   `seriestype` char(200) DEFAULT NULL,
 *   `CopyRight` char(200) DEFAULT NULL,
 *   `publisherId` char(200) DEFAULT NULL,
 *   `tag` char(200) DEFAULT NULL,
 *   `PgmSndClass` char(200) DEFAULT NULL,
 *   `rating` char(200) DEFAULT NULL,
 *   `OriginalCountry` char(200) DEFAULT NULL,
 *   `type1` varchar(200) DEFAULT NULL,
 *   `type1_url` char(200) DEFAULT NULL,
 *   `type2` varchar(200) DEFAULT NULL,
 *   `type2_url` char(200) DEFAULT NULL,
 *   `itemSetId` int(11) DEFAULT NULL,
 *   `itemStatus` varchar(255) DEFAULT NULL,
 *   `itemTime` datetime DEFAULT NULL,
 *   `coverUrl` varchar(255) DEFAULT NULL,
 *   `url` varchar(255) DEFAULT NULL,
 *   `gener` char(200) DEFAULT NULL,
 *   `tags` char(200) DEFAULT NULL,
 *   `DB_PRICE` char(200) DEFAULT NULL
 */

/**
 * 对剧头物料的操作
 */
public class Server_wl {
    public void insert(Standard_media_assets new_standard_media_assets){
        C2Wai selectwulio = selectwulio(new_standard_media_assets.getItemId());
        delete(new_standard_media_assets.getItemId());




        Connection conn = null;
        PreparedStatement ps = null;
        FileInputStream in = null;
        try {
            conn = ConnUtil.getConn();
            String sql = "insert into wuliao2 (itemId,name,originalname,sortname,searchname,actordisplay,originalcountry,language,orgairdate,licensingwindowstart,licensingwindowend,description,pricetaxin,kpeople,director,scriptwriter,compere,guest,reporter,opincharge,seriesflag,volumcpunt," +
                    "sourcetype,genre,releaseyear,vspcode,seriestype,duration,duration1,copyright,contentprovider,linkurl,pgmcategory,pgmsndclass,db_rating,tags,hou_tag,region,db_score,hu_score,video_type,short_desc,type1,type1_ur1,type2,type2_url,url,biao1,biao2,biao3,biao4,biao5,collection,serverCode) " +
                    "values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
            ps = conn.prepareStatement(sql);
            ps.setString(1, new_standard_media_assets.getItemId());
            ps.setString(2, new_standard_media_assets.getTitle());
            ps.setString(3, new_standard_media_assets.getOriginalname());
            ps.setString(4,new_standard_media_assets.getSortname());
            ps.setString(5, new_standard_media_assets.getSearchname());
            ps.setString(6, new_standard_media_assets.getActordisplay());
            ps.setString(7, new_standard_media_assets.getOriginalcountry());
            ps.setString(8, new_standard_media_assets.getLanguage());
            ps.setString(9, new_standard_media_assets.getOrgairdate());
            ps.setString(10, new_standard_media_assets.getLicensingwindowstart());
            ps.setString(11, new_standard_media_assets.getLicensingwindowend());
            ps.setString(12, new_standard_media_assets.getContent());
            ps.setString(13, new_standard_media_assets.getPricetaxin());
            ps.setString(14, new_standard_media_assets.getKpeople());
            ps.setString(15, new_standard_media_assets.getDirectors());
            ps.setString(16, new_standard_media_assets.getScriptwriter());
            ps.setString(17, new_standard_media_assets.getCompere());
            ps.setString(18, new_standard_media_assets.getGuest());
            ps.setString(19, new_standard_media_assets.getReporter());
            ps.setString(20, new_standard_media_assets.getOpincharge());
            ps.setString(21, new_standard_media_assets.getSeriesflag());
            ps.setInt(22, new_standard_media_assets.getVolumncount());
            ps.setString(23, new_standard_media_assets.getSourcetype());
            ps.setString(24, new_standard_media_assets.getGener());
            ps.setString(25, new_standard_media_assets.getPublishTime()+"");

            ps.setString(26, new_standard_media_assets.getVspcode());
            ps.setString(27, new_standard_media_assets.getSeriestype());
            ps.setString(28, new_standard_media_assets.getDuration());
            ps.setString(29, new_standard_media_assets.getDuration1());
            ps.setString(30, new_standard_media_assets.getCopyright());

            ps.setString(31, new_standard_media_assets.getContentprovider());
            ps.setString(32, new_standard_media_assets.getLinkurl());
            ps.setString(33, new_standard_media_assets.getTag());
            ps.setString(34, new_standard_media_assets.getPgmsndclass());
            ps.setString(35, new_standard_media_assets.getRating());
            ps.setString(36, new_standard_media_assets.getTags());
            ps.setString(37, selectwulio.getHou_tag());
            ps.setString(38, selectwulio.getRegion());
            try {
                ps.setDouble(39, selectwulio.getDb_score());
            } catch (Exception throwables) {
                ps.setDouble(39, 0.0);

            }
            try {
                ps.setDouble(40, selectwulio.getDb_score());
            } catch (Exception throwables) {
                ps.setDouble(40, 0.0);

            }
            ps.setString(41, selectwulio.getVideo_type());
            ps.setString(42, selectwulio.getShort_desc());
            ps.setString(43, new_standard_media_assets.getType1());
            ps.setString(44, new_standard_media_assets.getType1_url());
            ps.setString(45, new_standard_media_assets.getType2());
            ps.setString(46, new_standard_media_assets.getType2_url());
            ps.setString(47, selectwulio.getUrl());
            ps.setString(48, selectwulio.getBiao1());
            ps.setString(49, selectwulio.getBiao2());
            ps.setString(50, selectwulio.getBiao3());
            ps.setString(51, selectwulio.getBiao4());
            ps.setString(52, selectwulio.getBiao5());
            ps.setString(53,"0");
            ps.setString(54," ");
            ps.executeUpdate();
            inserturl(new_standard_media_assets.getItemId(),"");




        } catch (Exception e) {
        } finally {
            ConnUtil.closeConn(conn);
            if (null != ps) {
                try {
                    ps.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

    }
    public void delete(String code){
        Connection conn = null;
        PreparedStatement ps = null;
        FileInputStream in = null;
        try {
            conn = ConnUtil.getConn();
            String sql = "delete from   wuliao2 where  itemId=?";
            ps = conn.prepareStatement(sql);
            ps.setString(1, code);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            ConnUtil.closeConn(conn);
            if (null != ps) {
                try {
                    ps.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }



    }

    /**
     * 查询c2外的字段
     * @param code
     */

    public C2Wai selectwulio(String code){
        Connection conn = null;
        PreparedStatement ps = null;
        FileInputStream in = null;
        C2Wai c2Wai = new C2Wai();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy");

        try {
            conn = ConnUtil.getConn();
            String sql = "select hou_tag,region,db_score,hu_score,video_type,short_desc ,biao1,biao2,biao3,biao4,biao5,url from  wuliao2 where itemId = ?";
            ps = conn.prepareStatement(sql);
            ps.setString(1,code);
            ResultSet rs = ps.executeQuery();//获得查询结果
            while(rs.next()) {

                c2Wai.setHou_tag(rs.getString(1));
                c2Wai.setRegion(rs.getString(2));
                c2Wai.setDb_score(Double.parseDouble(rs.getString(3)));
                c2Wai.setHu_score(Double.parseDouble(rs.getString(4)));
                c2Wai.setVideo_type(rs.getString(5));
                c2Wai.setShort_desc(rs.getString(6));
                c2Wai.setBiao1(rs.getString(11));
                c2Wai.setBiao2(rs.getString(10));
                c2Wai.setBiao3(rs.getString(9));
                c2Wai.setBiao4(rs.getString(8));
                c2Wai.setBiao5(rs.getString(7));
                c2Wai.setUrl(rs.getString(12));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            ConnUtil.closeConn(conn);
            if (null != ps) {
                try {
                    ps.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return c2Wai;
    }

    public void selectcode1(String code){
        Connection conn = null;
        PreparedStatement ps = null;
        FileInputStream in = null;
        User user = new User();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy");

        try {
            conn = ConnUtil.getConn();
            String sql = "select * from  wuliao2 where itemId = ?";
            ps = conn.prepareStatement(sql);
            ps.setString(1,code);
            ResultSet rs = ps.executeQuery();//获得查询结果
            while(rs.next()) {
                user.setItemId(rs.getString(1));
                user.setTitle(rs.getString(2));
                user.setOriginalname(rs.getString(3));
                user.setOriginalname(rs.getString(4));
                user.setSearchname(rs.getString(5));
                user.setActordisplay(rs.getString(6));
                user.setOriginalcountry(rs.getString(7));
                user.setLanguage(rs.getString(8));
                user.setOrgairdate(rs.getString(9));
                user.setLicensingwindowstart(rs.getString(10));
                user.setLicensingwindowemd(rs.getString(11));
                user.setContent(rs.getString(12));
                user.setPricetaxin(rs.getString(13));
                user.setCasts(rs.getString(14));
                user.setDirectors(rs.getString(15));
                user.setScriptwriter(rs.getString(16));
                user.setCompere(rs.getString(17));
                user.setGuest(rs.getString(18));
                user.setReporter(rs.getString(19));
                user.setOpincharge(rs.getString(20));

                user.setSeriesflag(rs.getString(21));

                user.setVolumcount(rs.getString(22));
                user.setSourcetype(rs.getString(23));
                user.setGener(rs.getString(24));
                try {
                    user.setPublishTime(Long.parseLong(rs.getString(25)));
                } catch (Exception e) {
                    user.setPublishTime(31507200000L);
                }
                user.setVspcode(rs.getString(26));
                user.setSeriestype(rs.getString(27));
                user.setDuration(rs.getString(28));
                user.setDuration1(rs.getString(29));
                try {
                    user.setShichang2(Integer.parseInt(rs.getString(28)));
                } catch (NumberFormatException e) {

                }
                user.setCopyright(rs.getString(30));
                //user.setPublisherId((String) sss[28]);
                user.setContentprovider(rs.getString(31));
                user.setLinkurl(rs.getString(32));
                user.setPgmcategory(rs.getString(33));user.setTag(rs.getString(33));
                user.setPgmsndclass(rs.getString(34));
                try {
                    user.setRating(Double.parseDouble(rs.getString(35)));
                } catch (Exception e) {

                }
                user.setTags(rs.getString(36));
                user.setHou_tag(rs.getString(37));
                user.setRegion(rs.getString(38));

                try {
                    user.setDb_score(Double.parseDouble(rs.getString(39)));
                } catch (NumberFormatException e) {

                }
                try {
                    user.setHu_score(Double.parseDouble(rs.getString(40)));
                } catch (NumberFormatException e) {
                }
                user.setVideo_type(rs.getString(41));

                user.setShort_desc(rs.getString(42));

                user.setType1(rs.getString(43));
                user.setType1_url(rs.getString(44));
                user.setType2(rs.getString(45));
                user.setType2_url(rs.getString(46));
                user.setUrl(rs.getString(47));
                user.setCategoryLevel1(rs.getString(48));
                user.setCategoryLevel2(rs.getString(49));
                user.setCategoryLevel3(rs.getString(50));
                user.setCategoryLevel4(rs.getString(51));
                user.setCategoryLevel5(rs.getString(52));

                user.setItemSetId(139);
                user.setPublisherId(rs.getString(30));
                user.setItemStatus("1");
                user.setCoverUrl("");

                Object o = JSONArray.toJSON(user);
                String listjson = o.toString();
                System.out.println("listjson = " + listjson);
                gethttp(listjson);
                //updatecode1(code);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            ConnUtil.closeConn(conn);
            if (null != ps) {
                try {
                    ps.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 修改MySQL元数据
     * @param code
     */
    public void updatecode0(String code){
        Connection conn = null;
        PreparedStatement ps = null;
        FileInputStream in = null;
        try {
            conn = ConnUtil.getConn();
            String sql = "update  wuliao2 set itemStatus='0' where itemId=?";
            ps = conn.prepareStatement(sql);
            ps.setString(1, code);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            ConnUtil.closeConn(conn);
            if (null != ps) {
                try {
                    ps.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }



    }

    /**
     * 修改MySQL元数据
     * @param code
     */
    public void updatecode1(String code){
        Connection conn = null;
        PreparedStatement ps = null;
        FileInputStream in = null;
        try {
            conn = ConnUtil.getConn();
            String sql = "update  wuliao2 set itemStatus='1' where itemId=?";
            ps = conn.prepareStatement(sql);
            ps.setString(1, code);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            ConnUtil.closeConn(conn);
            if (null != ps) {
                try {
                    ps.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }



    }
    /**
     * 修改MySQL元数据
     * @param code
     */
    public void updatecodeurl(String code,String url){
        Connection conn = null;
        PreparedStatement ps = null;
        FileInputStream in = null;
        try {
            conn = ConnUtil.getConn();
            String sql = "update  wuliao2 set url=? where itemId=?";
            ps = conn.prepareStatement(sql);
            ps.setString(1,url);
            ps.setString(2, code);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            ConnUtil.closeConn(conn);
            if (null != ps) {
                try {
                    ps.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }



    }


    public void selectcode0(String code){
        Connection conn = null;
        PreparedStatement ps = null;
        FileInputStream in = null;
        int rowCount = 0;
        User user = new User();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy");

        try {
            conn = ConnUtil.getConn();
            String sql = "select * from  wuliao2 where itemId = ?";
            ps = conn.prepareStatement(sql);
            ps.setString(1,code);
            ResultSet rs = ps.executeQuery();//获得查询结果
            while(rs.next()) {
                user.setItemId(rs.getString(1));
                user.setTitle(rs.getString(2));
                user.setOriginalname(rs.getString(3));
                user.setOriginalname(rs.getString(4));
                user.setSearchname(rs.getString(5));
                user.setActordisplay(rs.getString(6));
                user.setOriginalcountry(rs.getString(7));
                user.setLanguage(rs.getString(8));
                user.setOrgairdate(rs.getString(9));
                user.setLicensingwindowstart(rs.getString(10));
                user.setLicensingwindowemd(rs.getString(11));
                user.setContent(rs.getString(12));
                user.setPricetaxin(rs.getString(13));
                user.setCasts(rs.getString(14));
                user.setDirectors(rs.getString(15));
                user.setScriptwriter(rs.getString(16));
                user.setCompere(rs.getString(17));
                user.setGuest(rs.getString(18));
                user.setReporter(rs.getString(19));
                user.setOpincharge(rs.getString(20));

                user.setSeriesflag(rs.getString(21));

                user.setVolumcount(rs.getString(22));
                user.setSourcetype(rs.getString(23));
                user.setGener(rs.getString(24));
                try {
                    user.setPublishTime(Long.parseLong(rs.getString(25)));
                } catch (Exception e) {
                    user.setPublishTime(31507200000L);
                }
                user.setVspcode(rs.getString(26));
                user.setSeriestype(rs.getString(27));
                user.setDuration(rs.getString(28));
                user.setDuration1(rs.getString(29));
                try {
                    user.setShichang2(Integer.parseInt(rs.getString(28)));
                } catch (NumberFormatException e) {

                }
                user.setCopyright(rs.getString(30));
                //user.setPublisherId((String) sss[28]);
                user.setContentprovider(rs.getString(31));
                user.setLinkurl(rs.getString(32));
                user.setPgmcategory(rs.getString(33));user.setTag(rs.getString(33));
                user.setPgmsndclass(rs.getString(34));
                try {
                    user.setRating(Double.parseDouble(rs.getString(35)));
                } catch (Exception e) {

                }
                user.setTags(rs.getString(36));
                user.setHou_tag(rs.getString(37));
                user.setRegion(rs.getString(38));

                try {
                    user.setDb_score(Double.parseDouble(rs.getString(39)));
                } catch (NumberFormatException e) {

                }
                try {
                    user.setHu_score(Double.parseDouble(rs.getString(40)));
                } catch (NumberFormatException e) {
                }
                user.setVideo_type(rs.getString(41));

                user.setShort_desc(rs.getString(42));

                user.setType1(rs.getString(43));
                user.setType1_url(rs.getString(44));
                user.setType2(rs.getString(45));
                user.setType2_url(rs.getString(46));
                user.setUrl(rs.getString(47));
                user.setCategoryLevel1(rs.getString(48));
                user.setCategoryLevel2(rs.getString(49));
                user.setCategoryLevel3(rs.getString(50));
                user.setCategoryLevel4(rs.getString(51));
                user.setCategoryLevel5(rs.getString(52));

                user.setCollection(rs.getString(53));
                user.setServerCode(rs.getString(54));
                user.setItemSetId(139);
                user.setPublisherId(rs.getString(30));
                user.setItemStatus("0");
                user.setCoverUrl("");

                Object o = JSONArray.toJSON(user);
                String listjson = o.toString();
                gethttp(listjson);
                //updatecode0(code);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            ConnUtil.closeConn(conn);
            if (null != ps) {
                try {
                    ps.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    public int selectcound( String code){
        Connection conn = null;
        PreparedStatement ps = null;
        FileInputStream in = null;
        int rowCount = 0;

        try {
            conn = ConnUtil.getConn();
            String sql = "select * from  wuliao2 where programcode = ?";
            ps = conn.prepareStatement(sql);

            ps.setString(1,code);
            ResultSet rs = ps.executeQuery();//获得查询结果
            while(rs.next()) {
                System.out.println("rs = " + rs);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            ConnUtil.closeConn(conn);
            if (null != ps) {
                try {
                    ps.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

        return rowCount;
    }
    public static  void gethttp(String json){
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
        HttpPost httpPost = new HttpPost("http://172.25.3.107:30000/cess-data-15/data/api/data-access/15/"+139+"/items");
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
     * 添加标签id
     * @param code
     */
    public void inserturl(String code,String parentCode){
        Connection conn = null;
        PreparedStatement ps = null;
        FileInputStream in = null;
        int rowCount = 0;
        User user = new User();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy");

        try {
            conn = ConnUtil.getConn();
            String sql = "select * from  wuliao2 where itemId = ?";
            ps = conn.prepareStatement(sql);
            ps.setString(1,code);
            ResultSet rs = ps.executeQuery();//获得查询结果
            while(rs.next()) {
                user.setItemId(rs.getString(1));
                user.setTitle(rs.getString(2));
                user.setOriginalname(rs.getString(3));
                user.setOriginalname(rs.getString(4));
                user.setSearchname(rs.getString(5));
                user.setActordisplay(rs.getString(6));
                user.setOriginalcountry(rs.getString(7));
                user.setLanguage(rs.getString(8));
                user.setOrgairdate(rs.getString(9));
                user.setLicensingwindowstart(rs.getString(10));
                user.setLicensingwindowemd(rs.getString(11));
                user.setContent(rs.getString(12));
                user.setPricetaxin(rs.getString(13));
                user.setCasts(rs.getString(14));
                user.setDirectors(rs.getString(15));
                user.setScriptwriter(rs.getString(16));
                user.setCompere(rs.getString(17));
                user.setGuest(rs.getString(18));
                user.setReporter(rs.getString(19));
                user.setOpincharge(rs.getString(20));

                user.setSeriesflag(rs.getString(21));

                user.setVolumcount(rs.getString(22));
                user.setSourcetype(rs.getString(23));
                user.setGener(rs.getString(24));
                try {
                    user.setPublishTime(Long.parseLong(rs.getString(25)));
                } catch (Exception e) {
                    user.setPublishTime(31507200000L);
                }
                user.setVspcode(rs.getString(26));
                user.setSeriestype(rs.getString(27));
                user.setDuration(rs.getString(28));
                user.setDuration1(rs.getString(29));
                try {
                    user.setShichang2(Integer.parseInt(rs.getString(28)));
                } catch (NumberFormatException e) {

                }
                user.setCopyright(rs.getString(30));
                //user.setPublisherId((String) sss[28]);
                user.setContentprovider(rs.getString(31));
                user.setLinkurl(rs.getString(32));
                user.setPgmcategory(rs.getString(33));user.setTag(rs.getString(33));
                user.setPgmsndclass(rs.getString(34));
                try {
                    user.setRating(Double.parseDouble(rs.getString(35)));
                } catch (Exception e) {

                }
                user.setTags(rs.getString(36));
                user.setHou_tag(rs.getString(37));
                user.setRegion(rs.getString(38));

                try {
                    user.setDb_score(Double.parseDouble(rs.getString(39)));
                } catch (NumberFormatException e) {

                }
                try {
                    user.setHu_score(Double.parseDouble(rs.getString(40)));
                } catch (NumberFormatException e) {
                }
                user.setVideo_type(rs.getString(41));

                user.setShort_desc(rs.getString(42));

                user.setType1(rs.getString(43));
                user.setType1_url(rs.getString(44));
                user.setType2(rs.getString(45));
                user.setType2_url(rs.getString(46));
                user.setUrl(rs.getString(47)+","+parentCode);
                user.setCategoryLevel1(rs.getString(48));
                user.setCategoryLevel2(rs.getString(49));
                user.setCategoryLevel3(rs.getString(50));
                user.setCategoryLevel4(rs.getString(51));
                user.setCategoryLevel5(rs.getString(52));
                user.setCollection(rs.getString(53));
                user.setServerCode(rs.getString(54));

                user.setItemSetId(139);
                user.setPublisherId(rs.getString(30));
                user.setItemStatus("1");
                user.setCoverUrl("");

                updatecodeurl(code,rs.getString(47)+","+parentCode);

                Object o = JSONArray.toJSON(user);
                String listjson = o.toString();
                gethttp(listjson);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            ConnUtil.closeConn(conn);
            if (null != ps) {
                try {
                    ps.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    /**
     * 删除url里要删除的code
     * @param code
     */
    public void deleteurl(String code,String parentCode){
        Connection conn = null;
        PreparedStatement ps = null;
        FileInputStream in = null;
        int rowCount = 0;
        User user = new User();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy");

        try {
            conn = ConnUtil.getConn();
            String sql = "select * from  wuliao2 where itemId = ?";
            ps = conn.prepareStatement(sql);
            ps.setString(1,code);
            ResultSet rs = ps.executeQuery();//获得查询结果
            while(rs.next()) {
                user.setItemId(rs.getString(1));
                user.setTitle(rs.getString(2));
                user.setOriginalname(rs.getString(3));
                user.setOriginalname(rs.getString(4));
                user.setSearchname(rs.getString(5));
                user.setActordisplay(rs.getString(6));
                user.setOriginalcountry(rs.getString(7));
                user.setLanguage(rs.getString(8));
                user.setOrgairdate(rs.getString(9));
                user.setLicensingwindowstart(rs.getString(10));
                user.setLicensingwindowemd(rs.getString(11));
                user.setContent(rs.getString(12));
                user.setPricetaxin(rs.getString(13));
                user.setCasts(rs.getString(14));
                user.setDirectors(rs.getString(15));
                user.setScriptwriter(rs.getString(16));
                user.setCompere(rs.getString(17));
                user.setGuest(rs.getString(18));
                user.setReporter(rs.getString(19));
                user.setOpincharge(rs.getString(20));

                user.setSeriesflag(rs.getString(21));

                user.setVolumcount(rs.getString(22));
                user.setSourcetype(rs.getString(23));
                user.setGener(rs.getString(24));
                try {
                    user.setPublishTime(Long.parseLong(rs.getString(25)));
                } catch (Exception e) {
                    user.setPublishTime(31507200000L);
                }
                user.setVspcode(rs.getString(26));
                user.setSeriestype(rs.getString(27));
                user.setDuration(rs.getString(28));
                user.setDuration1(rs.getString(29));
                try {
                    user.setShichang2(Integer.parseInt(rs.getString(28)));
                } catch (NumberFormatException e) {

                }
                user.setCopyright(rs.getString(30));
                //user.setPublisherId((String) sss[28]);
                user.setContentprovider(rs.getString(31));
                user.setLinkurl(rs.getString(32));
                user.setPgmcategory(rs.getString(33));user.setTag(rs.getString(33));
                user.setPgmsndclass(rs.getString(34));
                try {
                    user.setRating(Double.parseDouble(rs.getString(35)));
                } catch (Exception e) {

                }
                user.setTags(rs.getString(36));
                user.setHou_tag(rs.getString(37));
                user.setRegion(rs.getString(38));

                try {
                    user.setDb_score(Double.parseDouble(rs.getString(39)));
                } catch (NumberFormatException e) {

                }
                try {
                    user.setHu_score(Double.parseDouble(rs.getString(40)));
                } catch (NumberFormatException e) {
                }
                user.setVideo_type(rs.getString(41));

                user.setShort_desc(rs.getString(42));

                user.setType1(rs.getString(43));
                user.setType1_url(rs.getString(44));
                user.setType2(rs.getString(45));
                user.setType2_url(rs.getString(46));
                user.setUrl(rs.getString(47).replace(parentCode,""));
                user.setCategoryLevel1(rs.getString(48));
                user.setCategoryLevel2(rs.getString(49));
                user.setCategoryLevel3(rs.getString(50));
                user.setCategoryLevel4(rs.getString(51));
                user.setCategoryLevel5(rs.getString(52));
                user.setCollection(rs.getString(53));
                user.setServerCode(rs.getString(54));

                user.setItemSetId(139);
                user.setPublisherId(rs.getString(30));
                user.setItemStatus("1");
                user.setCoverUrl("");

                updatecodeurl(code,rs.getString(47).replace(parentCode,""));

                Object o = JSONArray.toJSON(user);
                String listjson = o.toString();
                gethttp(listjson);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            ConnUtil.closeConn(conn);
            if (null != ps) {
                try {
                    ps.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 对产品包操作(新增)
     */

    public void insertlinkUrl(String code,String parentCode){
        Connection conn = null;
        PreparedStatement ps = null;
        FileInputStream in = null;
        int rowCount = 0;
        User user = new User();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy");

        try {
            conn = ConnUtil.getConn();
            String sql = "select * from  wuliao2 where itemId = ?";
            ps = conn.prepareStatement(sql);
            ps.setString(1,code);
            ResultSet rs = ps.executeQuery();//获得查询结果
            while(rs.next()) {
                user.setItemId(rs.getString(1));
                user.setTitle(rs.getString(2));
                user.setOriginalname(rs.getString(3));
                user.setOriginalname(rs.getString(4));
                user.setSearchname(rs.getString(5));
                user.setActordisplay(rs.getString(6));
                user.setOriginalcountry(rs.getString(7));
                user.setLanguage(rs.getString(8));
                user.setOrgairdate(rs.getString(9));
                user.setLicensingwindowstart(rs.getString(10));
                user.setLicensingwindowemd(rs.getString(11));
                user.setContent(rs.getString(12));
                user.setPricetaxin(rs.getString(13));
                user.setCasts(rs.getString(14));
                user.setDirectors(rs.getString(15));
                user.setScriptwriter(rs.getString(16));
                user.setCompere(rs.getString(17));
                user.setGuest(rs.getString(18));
                user.setReporter(rs.getString(19));
                user.setOpincharge(rs.getString(20));

                user.setSeriesflag(rs.getString(21));

                user.setVolumcount(rs.getString(22));
                user.setSourcetype(rs.getString(23));
                user.setGener(rs.getString(24));
                try {
                    user.setPublishTime(Long.parseLong(rs.getString(25)));
                } catch (Exception e) {
                    user.setPublishTime(31507200000L);
                }
                user.setVspcode(rs.getString(26));
                user.setSeriestype(rs.getString(27));
                user.setDuration(rs.getString(28));
                user.setDuration1(rs.getString(29));
                try {
                    user.setShichang2(Integer.parseInt(rs.getString(28)));
                } catch (NumberFormatException e) {

                }
                user.setCopyright(rs.getString(30));
                //user.setPublisherId((String) sss[28]);
                user.setContentprovider(rs.getString(31));

                user.setPgmcategory(rs.getString(33));user.setTag(rs.getString(33));
                user.setPgmsndclass(rs.getString(34));
                try {
                    user.setRating(Double.parseDouble(rs.getString(35)));
                } catch (Exception e) {

                }
                user.setTags(rs.getString(36));
                user.setHou_tag(rs.getString(37));
                user.setRegion(rs.getString(38));

                try {
                    user.setDb_score(Double.parseDouble(rs.getString(39)));
                } catch (NumberFormatException e) {

                }
                try {
                    user.setHu_score(Double.parseDouble(rs.getString(40)));
                } catch (NumberFormatException e) {
                }
                user.setVideo_type(rs.getString(41));

                user.setShort_desc(rs.getString(42));

                user.setType1(rs.getString(43));
                user.setType1_url(rs.getString(44));
                user.setType2(rs.getString(45));
                user.setType2_url(rs.getString(46));
                user.setUrl(rs.getString(47));
                user.setLinkurl(rs.getString(32)+","+parentCode);
                user.setCategoryLevel1(rs.getString(48));
                user.setCategoryLevel2(rs.getString(49));
                user.setCategoryLevel3(rs.getString(50));
                user.setCategoryLevel4(rs.getString(51));
                user.setCategoryLevel5(rs.getString(52));
                user.setCollection(rs.getString(53));
                user.setServerCode(rs.getString(54));

                user.setItemSetId(139);
                user.setPublisherId(rs.getString(30));
                user.setItemStatus("1");
                user.setCoverUrl("");

                updatecodelinkurl(code,rs.getString(32)+","+parentCode);

                Object o = JSONArray.toJSON(user);
                String listjson = o.toString();
                gethttp(listjson);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            ConnUtil.closeConn(conn);
            if (null != ps) {
                try {
                    ps.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 修改产品包的字段
     * @param code
     * @param url
     */
    public void updatecodelinkurl(String code,String url){
        Connection conn = null;
        PreparedStatement ps = null;
        FileInputStream in = null;
        try {
            conn = ConnUtil.getConn();
            String sql = "update  wuliao2 set linkurl=? where itemId=?";
            ps = conn.prepareStatement(sql);
            ps.setString(1,url);
            ps.setString(2, code);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            ConnUtil.closeConn(conn);
            if (null != ps) {
                try {
                    ps.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }



    }

    /**
     * 对产品包删除
     * @param code
     * @param parentCode
     */
    public void deletelinkurl(String code,String parentCode){
        Connection conn = null;
        PreparedStatement ps = null;
        FileInputStream in = null;
        int rowCount = 0;
        User user = new User();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy");

        try {
            conn = ConnUtil.getConn();
            String sql = "select * from  wuliao2 where itemId = ?";
            ps = conn.prepareStatement(sql);
            ps.setString(1,code);
            ResultSet rs = ps.executeQuery();//获得查询结果
            while(rs.next()) {
                user.setItemId(rs.getString(1));
                user.setTitle(rs.getString(2));
                user.setOriginalname(rs.getString(3));
                user.setOriginalname(rs.getString(4));
                user.setSearchname(rs.getString(5));
                user.setActordisplay(rs.getString(6));
                user.setOriginalcountry(rs.getString(7));
                user.setLanguage(rs.getString(8));
                user.setOrgairdate(rs.getString(9));
                user.setLicensingwindowstart(rs.getString(10));
                user.setLicensingwindowemd(rs.getString(11));
                user.setContent(rs.getString(12));
                user.setPricetaxin(rs.getString(13));
                user.setCasts(rs.getString(14));
                user.setDirectors(rs.getString(15));
                user.setScriptwriter(rs.getString(16));
                user.setCompere(rs.getString(17));
                user.setGuest(rs.getString(18));
                user.setReporter(rs.getString(19));
                user.setOpincharge(rs.getString(20));

                user.setSeriesflag(rs.getString(21));

                user.setVolumcount(rs.getString(22));
                user.setSourcetype(rs.getString(23));
                user.setGener(rs.getString(24));
                try {
                    user.setPublishTime(Long.parseLong(rs.getString(25)));
                } catch (Exception e) {
                    user.setPublishTime(31507200000L);
                }
                user.setVspcode(rs.getString(26));
                user.setSeriestype(rs.getString(27));
                user.setDuration(rs.getString(28));
                user.setDuration1(rs.getString(29));
                try {
                    user.setShichang2(Integer.parseInt(rs.getString(28)));
                } catch (NumberFormatException e) {

                }
                user.setCopyright(rs.getString(30));
                //user.setPublisherId((String) sss[28]);
                user.setContentprovider(rs.getString(31));
                user.setPgmcategory(rs.getString(33));user.setTag(rs.getString(33));
                user.setPgmsndclass(rs.getString(34));
                try {
                    user.setRating(Double.parseDouble(rs.getString(35)));
                } catch (Exception e) {

                }
                user.setTags(rs.getString(36));
                user.setHou_tag(rs.getString(37));
                user.setRegion(rs.getString(38));

                try {
                    user.setDb_score(Double.parseDouble(rs.getString(39)));
                } catch (NumberFormatException e) {

                }
                try {
                    user.setHu_score(Double.parseDouble(rs.getString(40)));
                } catch (NumberFormatException e) {
                }
                user.setVideo_type(rs.getString(41));

                user.setShort_desc(rs.getString(42));

                user.setType1(rs.getString(43));
                user.setType1_url(rs.getString(44));
                user.setType2(rs.getString(45));
                user.setType2_url(rs.getString(46));
                user.setLinkurl(rs.getString(32).replace(parentCode,""));
                user.setUrl(rs.getString(47));
                user.setCategoryLevel1(rs.getString(48));
                user.setCategoryLevel2(rs.getString(49));
                user.setCategoryLevel3(rs.getString(50));
                user.setCategoryLevel4(rs.getString(51));
                user.setCategoryLevel5(rs.getString(52));
                user.setCollection(rs.getString(53));
                user.setServerCode(rs.getString(54));

                user.setItemSetId(139);
                user.setPublisherId(rs.getString(30));
                user.setItemStatus("1");
                user.setCoverUrl("");

                updatecodelinkurl(code,rs.getString(32).replace(parentCode,""));

                Object o = JSONArray.toJSON(user);
                String listjson = o.toString();
                gethttp(listjson);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            ConnUtil.closeConn(conn);
            if (null != ps) {
                try {
                    ps.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }




}
