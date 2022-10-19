import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class test {
    public static void main(String[] args) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
       String sss="2021";
        Date parse = sdf.parse(sss);
        long time = parse.getTime();
        System.out.println("time = " + time);
    }
}
