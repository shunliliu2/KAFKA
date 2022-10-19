import java.util.HashMap;
import java.util.Map;

public class shijian {
    public static void main(String[] args) {
        Map<String, String> stringStringHashMap = new HashMap<>();
        stringStringHashMap.put("liu","jajja");
        try {
            if(!stringStringHashMap.get("ll").equals(null)) {

                System.out.println("1111 = " + 1111);
    
            }else {
                System.out.println("2222 = " + 2222);
            }
        } catch (Exception e) {
            System.out.println("3333 = " + 3333);
        }
    }
    
}
