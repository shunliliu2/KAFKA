package com._4paradigm.util;

import com._4paradigm.entity.User;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Component
public class FenDuan {

        /**
         * 每次从文件中读取的行数，默认 5000 行
         */
        public static final int LINE_COUNT = 200;

        /**
         * 文件默认编码
         */
        public static final String FILE_ENCODING = "utf-8";


        /**
         * 文件的唯一标识Map，用于记录从文件的哪一行开始读取
         */
        private  Map<String, Integer> counterMap = Collections.synchronizedMap(new HashMap<String, Integer>());

        /**
         * 每次从文件中读取固定行数的记录
         * @param msgKey 文件的唯一标识
         * @param filePath 文件路径
         * @return List<Map<Integer, String>> 读取的文件内容
         */
        public List<Map<Integer, String>> getFileData(String msgKey, String filePath,String coding) {
            System.out.println("filePath = " + filePath);
            List<Map<Integer, String>> dataList = new ArrayList<>();

            int line = 0;
            if (counterMap.get(msgKey) == null) {
                counterMap.put(msgKey, line);
            } else {
                line = counterMap.get(msgKey);
            }

            try {
                File file = new File(filePath);
                if (file.isFile() && file.exists()) { // 判断文件是否存在
                    InputStreamReader read = new InputStreamReader(new FileInputStream(file), coding);// 考虑到编码格式
                    BufferedReader bufferedReader = new BufferedReader(read);
                    String lineTxt = null;

                    int index = 1;
                    while ((lineTxt = bufferedReader.readLine()) != null) {
                        // 每次取的时候从上次最后的行开始
                        if (index > line) {
                            Map<Integer, String> data = new HashMap<>();
                            data.put(index, lineTxt);
                            dataList.add(data);

                            // 每次只取文件的 5000 条
                            if ((index - line) == LINE_COUNT) {
                                line = index;
                                break;
                            }
                        }
                        index ++;
                    }
                    // 说明文件已经读完，插入一个读完的标记, file.renameTo(file) 用于判断当前文件是否被其他程序写入内容或占用
                    if (lineTxt == null && file.renameTo(file)) {
                        Map<Integer, String> data = new HashMap<>();
                        data.put(-1, "END OF FILE");
                        dataList.add(data);

                        line = index;
                    }

                    read.close();
                    bufferedReader.close();
                } else {
                    //log.error("找不到指定的文件:{}", new Object[]{filePath});
                }
            } catch (Exception e) {
                //log.error("读取文件内容出现异常", e);
            } finally {
                // 记录下一次从文件的哪一行开始读取
                counterMap.put(msgKey, line);
                //log.info("msgKey={},filePath={},line={}", new Object[]{msgKey,filePath,line});
            }

            return dataList;
        }

        /**
         * 重置从文件的开始读取行数
         *
         * @param msgKey 文件的唯一标识
         * @param offset 开始读取行数
         */
        public void relocateTo(String msgKey, int offset) {
            counterMap.put(msgKey, offset);
        }

        public FenDuan() {
        }

        public List<User> getgetParsing(FenDuan fenDuan,String msgkey, String file,String coding) throws ParseException {
            List <User> list= new ArrayList<>();
            User user = new User();
            List<Map<Integer, String>> fileData = fenDuan.getFileData(msgkey, file,coding);
            System.out.println("fileData.size() = " + fileData.size());
            for (Map<Integer, String> integerStringMap : fileData) {
                integerStringMap.forEach((key, value) -> {
                    if(key!=-1) {
                        String[] split = value.split("\\|", -1);
                        for (String s : split) {
                           // System.out.println("s = " + s);
                        }
                        System.out.println("split.length = " + split.length);
                        String[] yunxingshang = split[0].split("-");
                        if(yunxingshang[0].equals("01")||yunxingshang[0].equals("02")){//联通
                            user.setUserId(split[0]);
                            user.setUser_group(split[1]);
                            user.setBusiness_group(split[2]);
                            user.setStatus(Integer.parseInt(split[5]));
                            user.setService(split[6]);
                            user.setSTBID(split[7]);
                            user.setOperator("联通");
                            Map<String, String> objectObjectHashMap = new HashMap<>();
                            objectObjectHashMap.put("1","111");
                            String aLong = objectObjectHashMap.get("1");
                            System.out.println("aLong = " + aLong.equals(null));
                            String ss="20210823140438";
                            String ss1="20210823150438";
                            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
                            Date parse = null;
                            Date parse1 = null;
                            try {
                                parse = sdf.parse(ss);
                                parse1 = sdf.parse(ss1);
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                            long l = parse.getTime() - parse1.getTime();
                            System.out.println("l = " + l);
                            System.out.println("parse = " + parse);
                        }else if(yunxingshang[0].equals("B90")||yunxingshang[0].equals("B91")||yunxingshang[0].equals("B92")){//电信

                        }else if(yunxingshang[0].equals("01")||yunxingshang[0].equals("02")){//移动

                        }
                        list.add(user);
                    }
                });
            }
            return list;
        }

}
