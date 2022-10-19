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
        Connection conn = null;
        PreparedStatement ps = null;
        FileInputStream in = null;
        try {
            conn = ConnUtil.getConn();
            String sql = "insert into wuliao2 (itemId,title,OriginalName,SearchName,content,casts,directors,ScriptWriter,compere,Guest,Reporter,OPIncharge,LICENSINGWINDOWSTART,LICENSINGWINDOWEMD," +
                    "VOLUNMCOUNT,VSPCode,publishTime1,seriestype,CopyRight,publisherId,tag,PgmSndClass,rating,OriginalCountry," +
                    "type1,type1_url,type2,type2_url,itemSetId,itemStatus,itemTime,coverUrl,url,gener,tags,DB_PRICE) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
            ps.setString(1, new_standard_media_assets.getCode());
            ps.setString(2, new_standard_media_assets.getTitle());
            ps.setString(3, new_standard_media_assets.getOriginalname());
            ps.setString(4, new_standard_media_assets.getSearchname());
            ps.setString(5, new_standard_media_assets.getContent());
            ps.setString(6, new_standard_media_assets.getCasts());
            ps.setString(7, new_standard_media_assets.getDirectors());
            ps.setString(8, new_standard_media_assets.getScriptwriter());
            ps.setString(9, new_standard_media_assets.getCompere());
            ps.setString(10, new_standard_media_assets.getGuest());
            ps.setString(11, new_standard_media_assets.getReporter());
            ps.setString(12, new_standard_media_assets.getOpincharge());
            ps.setString(13, new_standard_media_assets.getLicensingwindowstart());
            ps.setString(14, new_standard_media_assets.getLicensingwindowend());
            ps.setString(15, new_standard_media_assets.getVolumcount());
            ps.setString(16, new_standard_media_assets.getVspcode());
            ps.setString(17, new_standard_media_assets.getPublishTime()+"");
            ps.setString(18, new_standard_media_assets.getSeriestype());
            ps.setString(19, new_standard_media_assets.getCopyright());
            ps.setString(20, new_standard_media_assets.getPublisherId());
            ps.setString(21, new_standard_media_assets.getTag());
            ps.setString(22, new_standard_media_assets.getPgmsndclass());
            ps.setString(23, new_standard_media_assets.getRating());
            ps.setString(24, new_standard_media_assets.getOriginalcountry());
            ps.setString(25, new_standard_media_assets.getType1());
            ps.setString(26, new_standard_media_assets.getType1_url());
            ps.setString(27, new_standard_media_assets.getType2());
            ps.setString(28, new_standard_media_assets.getType2_url());
            ps.setInt(29, new_standard_media_assets.getItemSetId());
            ps.setString(30, new_standard_media_assets.getItemStatus()+"");
            ps.setDate(31, (java.sql.Date)new_standard_media_assets.getItemTime());
            ps.setString(32, new_standard_media_assets.getCoverUrl());
            ps.setString(33, new_standard_media_assets.getUrl());
            ps.setString(34, new_standard_media_assets.getGener());
            ps.setString(35, new_standard_media_assets.getTags());
            ps.setString(36, "");

            ps = conn.prepareStatement(sql);
            int result =ps.executeUpdate();// 返回值代表收到影响的行数

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
    public void selectcode(String code){
        Connection conn = null;
        PreparedStatement ps = null;
        FileInputStream in = null;
        int rowCount = 0;
        User user = new User();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy");

        try {
            conn = ConnUtil.getConn();
            String sql = "select * from  wuliao where itemId = ?";
            ps = conn.prepareStatement(sql);
            ps.setString(1,code);
            ResultSet rs = ps.executeQuery();//获得查询结果
            while(rs.next()) {
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
                user.setItemSetId(111);
                user.setItemStatus(rs.getString(30));
                user.setItemTime(rs.getString(31));
                user.setCoverUrl(rs.getString(32));
                user.setUrl(rs.getString(33));
                user.setTags(rs.getString(35));
                user.setGener(rs.getString(34));
                Object o = JSONArray.toJSON(user);
                String listjson = o.toString();
                System.out.println("listjson = " + listjson);
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
    public int selectcound ( String code){
        Connection conn = null;
        PreparedStatement ps = null;
        FileInputStream in = null;
        int rowCount = 0;

        try {
            conn = ConnUtil.getConn();
            String sql = "select * from  wuliao where programcode = ?";
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
        HttpPost httpPost = new HttpPost("http://172.25.3.107:30000/cess-data-3/data/api/data-access/3/"+111+"/items");
        httpPost.setHeader("Content-Type", "application/json;charset=utf-8");
        httpPost.setHeader("X-CESS-Data-Token", "0bf6ff0c10d641c99d519abbeec6ed35");
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
