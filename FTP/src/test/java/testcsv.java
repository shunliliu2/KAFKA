import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Stream;

public class testcsv {
    public static List<String> getCSV(String DirPath) throws IOException {
        //获取存放文件的目录
        File dirpath = new File(DirPath);
        //将该目录下的所有文件放置在一个File类型的数组中
        File[] filelist = dirpath.listFiles();
        //创建一个空的file类型的list集合，用于存放符合条件的文件
        List<File> newfilelist = new ArrayList<>();
        //遍历filelist，如果是文件则存储在新建的list集合中
        for (File file : filelist) {
            if (file.isFile()){
                newfilelist.add(file);
            }
        }
        //新建list集合，用于存放读取到的数据
        List<String> list = new ArrayList<>();
        //读取文件内容
        String s = null;
        int count=0;
        for (File file : newfilelist) {
            //判断：如果是CSV文件，
            if (file.toString().endsWith(".csv")){
                FileReader fileReader = new FileReader(file);
                    list.add(file.getPath());
                }

        }
        return list;

    }


    public static void main(String[] args) {
        ArrayList<Object> objects = new ArrayList<>();
        objects.get(0);
        System.out.println("objects.size() = " + objects.size());
    }

}
