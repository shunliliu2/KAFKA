import com._4paradigm.entity.Contentviewlog_er;
import com._4paradigm.util.ConnUtil;
import com.alibaba.fastjson.JSONArray;

import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class time {
    public static void main(String[] args) throws ParseException {

        DateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
        String s="01-01006321638-01|20220405003226|20220405005224|1||相亲进行曲：宋小宝赵海燕|01010001000000010000000039341517";
        String[] split = s.split("\\|");
        long start = format.parse(split[1]).getTime();
        long end = format.parse(split[2]).getTime();
        for (String s1 : split) {
            System.out.println("s1 = " + s1);
        }
        if(Integer.parseInt(split[3])!=1){
            System.out.println("split[3] = " + split[3]);
            System.out.println("1 = " + 1);
        }
        System.out.println("end = " + end);
        System.out.println("start = " + start);
        System.out.println(((int) (end-start))/1000);
        if(end-start<20000){
            System.out.println("2 = " + 2);
        }
        Date parse = format.parse(split[1]);
        parse.getTime();
        Contentviewlog_er contentviewlog_er = new Contentviewlog_er();
        contentviewlog_er.setUserId(split[0]);
        contentviewlog_er.setItemSetId(337);//物料id
        contentviewlog_er.setAction("detailPageShow");//行为名称
        contentviewlog_er.setActionTime(format.parse(split[1]).getTime());
        contentviewlog_er.setItemId(split[6]);

        String[] splitserverid = split[7].split("0");
        System.out.println("splitserverid.length= " + splitserverid.length);
        if(splitserverid.length>1){
            System.out.println(" =1111111111 ");
        }else {
            System.out.println("=2222222222222");
        }
        Object o = JSONArray.toJSON(contentviewlog_er);
        String contentviewlogjson = o.toString();
        System.out.println("contentviewlogjson =      "+ contentviewlogjson);


        System.out.println(format.parse(split[1]).getTime());
        List<Object> objects = new ArrayList<>();

//        Calendar c = Calendar.getInstance();
//        c.add(Calendar.DAY_OF_MONTH, -1);
//        Date yesterday = c.getTime();//这是昨天
//        System.out.println("format = " + format.format(yesterday));

    }



}
