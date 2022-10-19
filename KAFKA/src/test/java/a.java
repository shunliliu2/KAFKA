import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

public class a {
    public static void main(String[] args) throws ParseException {
        List<String> list = new ArrayList<String>();
        list.add("A");
        list.add("B");
        list.add("C");
        list.add("D");
        Iterator<String> it = list.iterator();
        while(it.hasNext()){
            String next = it.next();
            it.remove();
            System.out.println(it.next());
        }
    }
}
