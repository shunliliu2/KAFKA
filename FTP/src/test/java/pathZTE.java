import com._4paradigm.entity.Contentviewlog_er;
import com.alibaba.fastjson.JSONArray;

import java.io.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class pathZTE {

    public static void main(String[] args) throws IOException, ParseException {
        File file = new File("/Users/4paradigm/Desktop/SPARKlog/ZTE/20220420/part-00000-03fc4001-1ad7-44bc-af54-d3b8589b80ed-c000.csv");
        BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file), "GBK"));//构造一个BufferedReader类来读取文件

        DateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");

        int i = 0;
        int i2=0;
        int i3=0;
        String s = null;
        while ((s = br.readLine()) != null) {//使用readLine方法，一次读一行
            try {
                String[] split = s.split(",");
                if(split[6].equals("1010001000000010000000076961242")){
                long start = format.parse(split[1]).getTime();
                long end = format.parse(split[2]).getTime();
                if (Integer.parseInt(split[3]) != 1) {
                    i2++;
                    continue;
                }
                if (end-start<20000) {
                    i3++;
                    continue;
                }

                Contentviewlog_er contentviewlog_er = new Contentviewlog_er();
                contentviewlog_er.setTraceId("");
                contentviewlog_er.setSceneId("OtherC3");

                try {
                    contentviewlog_er.setItemId(split[6]);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                i++;
                contentviewlog_er.setUserId(split[0]);
                contentviewlog_er.setItemSetId(337);//物料id
                contentviewlog_er.setAction("detailPageShow");//行为名称
                contentviewlog_er.setActionTime(format.parse(split[1]).getTime());
                Object o = JSONArray.toJSON(contentviewlog_er);
                String contentviewlogjson = o.toString();
                System.out.println("contentviewlogjson =   " + i + "        " + contentviewlogjson);

            }
            } catch (ParseException e) {

            } catch (Exception e) {
            }
        }
        System.out.println("i2 = " + i2);
        System.out.println("i3 = " + i3);
    }
}
