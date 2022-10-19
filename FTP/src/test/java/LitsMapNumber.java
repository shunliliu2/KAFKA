import java.util.*;

public class LitsMapNumber {
    public static void main(String[] args) throws InterruptedException {
        List<Map<String, String>> list = new ArrayList<>();
        Map<String, String> stringStringHashMap = new HashMap<>();
        stringStringHashMap.put("key","1");
        Map<String, String> stringStringHashMap2 = new HashMap<>();

        stringStringHashMap2.put("key","1");
        Map<String, String> stringStringHashMap3 = new HashMap<>();

        stringStringHashMap3.put("key","1");
        Map<String, String> stringStringHashMap4 = new HashMap<>();

        stringStringHashMap4.put("key","1");
        Map<String, String> stringStringHashMap5 = new HashMap<>();

        stringStringHashMap5.put("key","1");
        Map<String, String> stringStringHashMap6 = new HashMap<>();

        stringStringHashMap6.put("key","1");
        Map<String, String> stringStringHashMap7 = new HashMap<>();

        stringStringHashMap7.put("key","1");

        stringStringHashMap.put("key","2");
        Map<String, String> stringStringHashMap8 = new HashMap<>();

        stringStringHashMap8.put("key","2");
        Map<String, String> stringStringHashMap9 = new HashMap<>();

        stringStringHashMap9.put("key","2");
        Map<String, String> stringStringHashMap10 = new HashMap<>();

        stringStringHashMap10.put("key","2");
        Map<String, String> stringStringHashMap11 = new HashMap<>();

        stringStringHashMap11.put("key","2");
        Map<String, String> stringStringHashMap12 = new HashMap<>();
        Map<String, String> stringStringHashMap13 = new HashMap<>();
        Map<String, String> stringStringHashMap14 = new HashMap<>();
        Map<String, String> stringStringHashMap15 = new HashMap<>();
        Map<String, String> stringStringHashMap16 = new HashMap<>();
        Map<String, String> stringStringHashMap17 = new HashMap<>();
        Map<String, String> stringStringHashMap18 = new HashMap<>();
        Map<String, String> stringStringHashMap19 = new HashMap<>();
        Map<String, String> stringStringHashMap20 = new HashMap<>();


        stringStringHashMap12.put("key","2");

        stringStringHashMap13.put("key","3");
        stringStringHashMap14.put("key","3");
        stringStringHashMap15.put("key","3");
        stringStringHashMap16.put("key","3");


        stringStringHashMap17.put("key","4");
        stringStringHashMap18.put("key","4");
        stringStringHashMap19.put("key","4");
        stringStringHashMap20.put("key","4");

        list.add(stringStringHashMap);
        list.add(stringStringHashMap2);
        list.add(stringStringHashMap3);
        list.add(stringStringHashMap4);
        list.add(stringStringHashMap5);
        list.add(stringStringHashMap6);
        list.add(stringStringHashMap7);
        list.add(stringStringHashMap8);
        list.add(stringStringHashMap9);
        list.add(stringStringHashMap10);

        list.add(stringStringHashMap11);
        list.add(stringStringHashMap12);
        list.add(stringStringHashMap13);
        list.add(stringStringHashMap14);
        list.add(stringStringHashMap15);
        list.add(stringStringHashMap16);
        list.add(stringStringHashMap17);
        list.add(stringStringHashMap18);
        list.add(stringStringHashMap19);
        list.add(stringStringHashMap20);

        Iterator<Map<String, String>> iterator = list.iterator();
        int a1=5;
        int b1=5;
        int c1=5;
        int d1=5;
        int a=0;
        int b=0;
        int c=0;
        int d=0;
        while (iterator.hasNext()){
            Map<String, String> next = iterator.next();
            String key = next.get("key");
            if (key.equals("1")){
                a++;
            }else if(key.equals("2")){
                b++;
            }else if(key.equals("3")){
                c++;
            }else if(key.equals("4")){
                d++;
            }
        }
        System.out.println("a = " + a);
        System.out.println("b = " + b);
        System.out.println("c = " + c);
        System.out.println("d = " + d);
        Map<String,Integer> ints = new HashMap<String,Integer>();
        ints.put("1",a-a1);
        ints.put("2",b-b1);
        ints.put("3",c-c1);
        ints.put("4",d-d1);
        ints.forEach((key, value) -> {
            if(value<0){

            }

        });

        Thread.currentThread().sleep(1000);



    }

}
