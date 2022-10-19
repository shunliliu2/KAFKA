import com._4paradigm.log.CommonLogger;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class httpTest {
    public static void main(String[] args) throws ParseException {
        Map<String, String> objectObjectHashMap = new HashMap<>();
        objectObjectHashMap.put("1","111");
        String aLong = objectObjectHashMap.get("1");
        System.out.println("aLong = " + aLong.equals(null));
        String ss="20210823140438";
        int length = ss.split(" ").length;
        CommonLogger.error("失败");
        String ss1="20210823150438";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        Date parse = sdf.parse(ss);
        Date parse1 = sdf.parse(ss1);
        long l = parse.getTime() - parse1.getTime();
        System.out.println("l = " + l);
        System.out.println("parse = " + parse);

        for (int i = -31; i > -180; i--) {
            System.out.println("i = " + i);
        }


    }
}
