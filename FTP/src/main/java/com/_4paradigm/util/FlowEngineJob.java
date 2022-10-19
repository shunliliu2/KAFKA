package com._4paradigm.util;
import com.alibaba.fastjson.JSONArray;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.tools.zip.ZipEntry;
import org.apache.tools.zip.ZipFile;
import java.io.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.*;
import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;


public class FlowEngineJob {

    /**
     * 每次从文件中读取的行数，默认 5000 行
     */
    public static final int LINE_COUNT = 200;

    /**
     * 文件默认编码
     */
    public static final String FILE_ENCODING = "UTF-8";
    public static String  execute() throws Exception {
        String hostname="172.25.1.195";
        Integer port=21;
        String username="btvftp1" ;
        String password ="Btvlptv1";
        String fileName="/";
        String localPath="/tmp/";

        FTPClient ftpClient = null;
        InputStream inputStream = null;
        System.err.println("执行静态定时任务时间: " + LocalDateTime.now());
        ftpClient = new FTPClient();
        DateFormat format = new SimpleDateFormat("yyyyMMdd");
        format.setTimeZone(TimeZone.getTimeZone("GMT+8"));
        Map<String, Object> map = new HashMap<>();
        ftpClient.setControlEncoding("GBK");
        Calendar c=Calendar.getInstance();
        c.add(Calendar.DAY_OF_MONTH,-1);
        readFile readFile = new readFile();
        try {
            System.out.println(hostname);
            System.out.println(username);
            System.out.println(password);
            ftpClient.connect("172.25.1.195", 21);
            ftpClient.login("btvftp1", "BtvIptv1");
            ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);
            ftpClient.enterLocalPassiveMode();

            String [] VendorName={"ZTE_"};
            for (String s : VendorName) {
                System.out.println("fileName = " + fileName+s+format.format(c.getTime())+".zip");
                ftpClient.changeWorkingDirectory(fileName+s+format.format(c.getTime())+".zip");
                File localFile = new File(localPath + File.separatorChar + s+format.format(c.getTime())+".zip");
                OutputStream os = new FileOutputStream(localFile);
                boolean b=ftpClient.retrieveFile(fileName+s+format.format(c.getTime())+".zip", os);
                System.out.println(b);
                System.out.println("c = " + format.format(c.getTime()));
                unZip(localPath + File.separatorChar +s+format.format(c.getTime())+".zip",localPath + File.separatorChar +s+format.format(c.getTime()));
                ftpClient.logout();
                if (s.equals("ZTE_")) {
                        readFile.readFile(localPath + s + format.format(c.getTime())+"/Contentviewlog_"+format.format(c.getTime())+".log",s,format.format(c.getTime()));
                        //parsing = getgetParsing(s,localPath + s + format.format(c.getTime())+"/Userinfo_"+format.format(c.getTime())+".log","GBK");
                    }else{
                        readFile.readFile(localPath + s + format.format(c.getTime())+"/Contentviewlog_"+format.format(c.getTime())+".log",s,format.format(c.getTime()));
                        //parsing = getgetParsing(s,localPath + s + format.format(c.getTime())+"/Userinfo_"+format.format(c.getTime())+".log","utf-8");


                }

                os.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (ftpClient.isConnected()) {
                try {
                    ftpClient.disconnect();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (null != inputStream) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return "hello world";
    }
    public static void unZip(String zipPath, String saveFilePath) {
        final int buffer = 2048;
        int count = -1;
        String savepath = "";
        File file = null;
        InputStream is = null;
        FileOutputStream fos = null;
        BufferedOutputStream bos = null;
        //保存解压文件目录
        if (StringUtils.isNotBlank(saveFilePath)) {
            savepath = new File(saveFilePath) + File.separator;
        } else {
            savepath = new File(zipPath).getParent() + File.separator;
        }
        new File(savepath).mkdir(); //创建保存目录
        ZipFile zipFile = null;
        try {
            //解决中文乱码问题  格式有GBK  UTF8
            zipFile = new ZipFile(zipPath, "GBK");
            Enumeration<?> entries = zipFile.getEntries();
            while (entries.hasMoreElements()) {
                byte buf[] = new byte[buffer];
                ZipEntry entry = (ZipEntry) entries.nextElement();
                String filename = entry.getName();
                boolean ismkdir = false;
                //检查此文件是否带有文件夹
                if (filename.lastIndexOf("/") != -1) {
                    ismkdir = true;
                }
                filename = savepath + filename;
                //如果是文件夹先创建
                if (entry.isDirectory()) {
                    file = new File(filename);
                    file.mkdirs();
                    continue;
                }
                file = new File(filename);
                if (!file.exists()) {
                    //如果是目录先创建
                    if (ismkdir) {
                        //目录先创建
                        new File(filename.substring(0, filename.lastIndexOf("/"))).mkdirs();
                    }
                }
                //创建文件
                file.createNewFile();
                is = zipFile.getInputStream(entry);
                fos = new FileOutputStream(file);
                bos = new BufferedOutputStream(fos, buffer);
                while ((count = is.read(buf)) > -1) {
                    bos.write(buf, 0, count);
                }
                bos.flush();
                bos.close();
                fos.close();
                is.close();
            }
            zipFile.close();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        } finally {
            try {
                if (bos != null) {
                    bos.close();
                }
                if (fos != null) {
                    fos.close();
                }
                if (is != null) {
                    is.close();
                }
                if (zipFile != null) {
                    zipFile.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    private static Map<String, Integer> counterMap = Collections.synchronizedMap(new HashMap<String, Integer>());

    public static List<Map<Integer, String>> getFileData(String msgKey, String filePath,String coding) {
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

    public static List<Map> getgetParsing(String msgkey, String file,String coding){
        List <Map> list= new ArrayList<>();
        List<Map<Integer, String>> fileData = getFileData(msgkey, file,coding);
        for (Map<Integer, String> integerStringMap : fileData) {
            integerStringMap.forEach((key, value) -> {
                if(key!=-1) {
                    String[] split = value.split("\\|", -1);
                    String[] yunxingshang = split[0].split("-");
                    Map<Object, Object> map = new HashMap<>();
                    if(yunxingshang[0].equals("01")||yunxingshang[0].equals("02")){//联通
                        map.put("userId",split[0]);
                        map.put("user_group",split[1]);
                        map.put("business_group",split[2]);
                        map.put("status",split[5]);
                        map.put("service",split[6]);
                        map.put("STBID",split[7]);
                        map.put("operator","联通");
                    }

                    list.add(map);
                }
            });
        }
        return list;
    }



    //------------------------------------------------------------------------------------------------------------------------------
    private static Map<String, Integer> counterMap1 = Collections.synchronizedMap(new HashMap<String, Integer>());

    public static List<Map<Integer, String>> getFileData1(String msgKey, String filePath,String coding) {
        List<Map<Integer, String>> dataList = new ArrayList<>();

        int line = 0;
        if (counterMap1.get(msgKey) == null) {
            counterMap1.put(msgKey, line);
        } else {
            line = counterMap1.get(msgKey);
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
            counterMap1.put(msgKey, line);
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
    public void relocateTo1(String msgKey, int offset) {
        counterMap1.put(msgKey, offset);
    }

    public static List<Map> getgetParsing1(String msgkey, String file,String coding){
        List <Map> list= new ArrayList<>();
        List<Map<Integer, String>> fileData = getFileData(msgkey, file,coding);
        System.out.println("fileData.size() = " + fileData.size());
        for (Map<Integer, String> integerStringMap : fileData) {
            integerStringMap.forEach((key, value) -> {
                if(key!=-1) {
                    String[] split = value.split("\\|", -1);
                    String[] yunxingshang = split[0].split("-");
                    Map<Object, Object> map = new HashMap<>();
                    if(yunxingshang[0].equals("01")||yunxingshang[0].equals("02")){//联通
                        map.put("userId",split[0]);
                        map.put("userGroup",split[1]);
                        map.put("businessGroup",split[2]);
                        map.put("status",split[5]);
                        map.put("service",split[6]);
                        map.put("stBid",split[7]);
                        map.put("operator","联通");
                    }

                    list.add(map);
                }
            });
        }
        return list;
    }

    //------------------------------------------------------------------------------------------------------
    private static Map<String, Integer> counterMap2 = Collections.synchronizedMap(new HashMap<String, Integer>());

    public static List<Map<Integer, String>> getFileData2(String msgKey, String filePath,String coding) {
        List<Map<Integer, String>> dataList = new ArrayList<>();

        int line = 0;
        if (counterMap2.get(msgKey) == null) {
            counterMap2.put(msgKey, line);
        } else {
            line = counterMap2.get(msgKey);
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
            counterMap2.put(msgKey, line);
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
    public void relocateTo2(String msgKey, int offset) {
        counterMap2.put(msgKey, offset);
    }

    public static List<Map> getgetParsing2(String msgkey, String file,String coding){
        List <Map> list= new ArrayList<>();
        List<Map<Integer, String>> fileData = getFileData(msgkey, file,coding);
        System.out.println("fileData.size() = " + fileData.size());
        for (Map<Integer, String> integerStringMap : fileData) {
            integerStringMap.forEach((key, value) -> {
                if(key!=-1) {
                    String[] split = value.split("\\|", -1);
                    String[] yunxingshang = split[0].split("-");
                    Map<Object, Object> map = new HashMap<>();
                    if(yunxingshang[0].equals("01")||yunxingshang[0].equals("02")){//联通
                        map.put("userId",split[0]);
                        map.put("userGroup",split[1]);
                        map.put("businessGroup",split[2]);
                        map.put("status",split[5]);
                        map.put("service",split[6]);
                        map.put("stBid",split[7]);
                        map.put("operator","联通");
                    }

                    list.add(map);
                }
            });
        }
        return list;
    }



}

