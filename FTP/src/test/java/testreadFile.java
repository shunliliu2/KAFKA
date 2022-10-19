import com._4paradigm.util.readFile;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class testreadFile {
    public static void main(String[] args) throws IOException {
        Calendar now = Calendar.getInstance();

        String i = now.get(Calendar.YEAR)+"";
        System.out.println("i = " + i);
        System.out.println("年: " + now.get(Calendar.YEAR));

        String i1 = String.format("02d",now.get(Calendar.MONTH) + 1);
        System.out.println("i1 = " + i1);
        System.out.println("月: " + String.format("%02d",now.get(Calendar.MONTH) + 1) + "");

        String i2 =  String.format("02d",now.get(Calendar.DAY_OF_MONTH));
        System.out.println("i2 = " + i2);
        System.out.println("日: " + now.get(Calendar.DAY_OF_MONTH));

        System.out.println("时: " + now.get(Calendar.HOUR_OF_DAY));

        System.out.println("分: " + now.get(Calendar.MINUTE));

        System.out.println("秒: " + now.get(Calendar.SECOND));

        System.out.println("当前时间毫秒数：" + now.getTimeInMillis());


    }
}
