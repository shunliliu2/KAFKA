import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class shijian {
    public static void main(String[] args) {
        Calendar cal = Calendar.getInstance();
        Date date = cal.getTime();
        String format = new SimpleDateFormat("yyyy/MM/dd-HH:mm:ss:SSS").format(date);
        System.out.println("format = " + format);
        long l = System.currentTimeMillis();

        System.out.println(System.currentTimeMillis());
    }
}
