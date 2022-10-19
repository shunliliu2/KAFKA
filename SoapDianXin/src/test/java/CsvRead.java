import com._4paradigm.entity.User;



import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.*;

import static java.lang.Math.log;

/**
 * csv文件读取
 *
 * @author walle
 * @date 2020/12/9 17:24
 **/
public class CsvRead {

    private List<String> header;
    private final List<String> content = new ArrayList<>();
    private String fieldSeparator = ",";
    private final Map<String, Integer> headerMap = new HashMap<>();
    private Charset charset = Charset.defaultCharset();

    public CsvRead() {}

    /**
     * 初始化 填入分隔符
     *
     * @param fieldSeparator 分隔符
     */
    public CsvRead(String fieldSeparator) {
        this.fieldSeparator = fieldSeparator;
    }

    /**
     * 初始化 分隔符 编码格式
     *
     * @param fieldSeparator 分隔符
     * @param charset        charset
     */
    public CsvRead(String fieldSeparator, Charset charset) {
        this.fieldSeparator = fieldSeparator;
        this.charset = charset;
    }

    /**
     * 读取csv文件返回 list<Map>
     *
     * @param sPath csvPath
     * @return list
     */
    public List<Map<String, String>> readMapList(String sPath) {
        read(sPath);
        List<Map<String, String>> result = new ArrayList<>();
        for (String line : content) {
            String[] vals = line.split(fieldSeparator);
            result.add(getContentMap(vals));
        }
        return result;
    }

    /**
     * 读取csv文件 返回list<Bean>
     *
     * @param sPath  csvPath
     * @param tClass clazz
     * @param <T>    T
     * @return list
     */


    /**
     * 行转bean
     *
     * @param vals   每行分割出的字符串数组
     * @param tClass t
     * @param <T>    T
     * @return T
     */

    /**
     * 行转map
     *
     * @param vals 每行分割出的字符串数组
     * @return map
     */
    private Map<String, String> getContentMap(String[] vals) {
        Map<String, String> lineMap = new HashMap<>(vals.length);
        headerMap.forEach((key, value) -> lineMap.put(key, vals[value]));
        return lineMap;
    }

    /**
     * 读取首行 header
     */
    private void getHeaderMap() {
        for (int i = 0; i < header.size(); i++) {
            final String field = header.get(i);
            headerMap.put(field, i);
        }
    }


    private void read(String sPath) {
        try {
            BufferedReader bf = new BufferedReader(new InputStreamReader(new FileInputStream(sPath), charset));
            String line;
            // 首行
            line = bf.readLine();
            header = Arrays.asList(line.split(fieldSeparator));
            while ((line = bf.readLine()) != null) {
                content.add(line);
            }
            getHeaderMap();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        CsvRead csvRead = new CsvRead();
        List<Map<String, String>> maps = csvRead.readMapList("/Users/4paradigm/Desktop/内容明细/category_20220329.csv");
        Iterator<Map<String, String>> iterator = maps.iterator();
        while (iterator.hasNext()){
            Map<String, String> next = iterator.next();
            double recallScore = Double.parseDouble(next.get("recallScore"));
            double ctr1w = Double.parseDouble(next.get("ctr1w"));
            double click1w = Double.parseDouble(next.get("click1w"));
            double score = 0.5 * recallScore + 0.3 * ctr1w + 0.2 * click1w;
            //score = a * log(recallScore) + b * log(ctr1w) + c * log(click1w);
            next.put("recallScore",score+"");
        }
        Collections.sort(maps, new Comparator<Map<String, String>>() {
            public int compare(Map<String, String> o1, Map<String, String> o2) {
                Integer value1= Double.valueOf(o1.get("value").toString()).intValue() ;//这里因为我的mapKey对应的值有小数，我去掉了小数部分
                Integer value2= Double.valueOf(o2.get("value").toString()).intValue() ;
                return value2.compareTo(value1);   //此时为升序， return value2.compareTo(value1);为降序
            }
        });

    }

}

