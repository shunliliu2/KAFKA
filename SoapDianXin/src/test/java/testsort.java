import java.util.*;
import java.util.stream.Collectors;

public class testsort {
    public static void main(String[] args) {
        List<Map<String,Object>> arrayList = new ArrayList<>();

        Map<String, Object> securityMap1 = new HashMap<>();
        securityMap1.put("name","期货");
        securityMap1.put("value","40000.11");
        arrayList.add(securityMap1);

        Map<String, Object> securityMap2 = new HashMap<>();
        securityMap2.put("name","基金");
        securityMap2.put("value","40000.22");
        arrayList.add(securityMap2);

        Map<String, Object> securityMap3 = new HashMap<>();
        securityMap3.put("name","股票");
        securityMap3.put("value","40000.33");
        arrayList.add(securityMap3);

        Map<String, Object> securityMap4 = new HashMap<>();
        securityMap4.put("name","债券");
        securityMap4.put("value","400.44");
        arrayList.add(securityMap4);


        Collections.sort(arrayList, new Comparator<Map<String, Object>>() {
            public int compare(Map<String, Object> o1, Map<String, Object> o2) {
                Double value1= Double.valueOf(o1.get("value").toString()) ;//这里因为我的mapKey对应的值有小数，我去掉了小数部分
                Double value2= Double.valueOf(o2.get("value").toString()) ;
                return value2.compareTo(value1);   //此时为升序， return value2.compareTo(value1);为降序
            }
        });
        Iterator<Map<String, Object>> iterator = arrayList.iterator();
        while (iterator.hasNext()){
            Map<String, Object> next = iterator.next();
            next.forEach((k,v)->{
                System.out.println(k);
                System.out.println(v);
            });
        }


    }
}
