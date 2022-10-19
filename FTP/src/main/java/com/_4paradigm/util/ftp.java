package com._4paradigm.util;

import org.apache.commons.net.ftp.FTPClient;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

public class ftp {
   public void downloadFile(String hostname,int port,String username,String password,String fileName ,String localPath) throws Exception{
       System.out.println("fileName = " + fileName);
       System.out.println("localPath = " + localPath);
       FTPClient ftpClient = new FTPClient();
       Map<String, Object> map = new HashMap<>();
       ftpClient.setControlEncoding("utf-8");
       try {
           ftpClient.connect(hostname, port);
           ftpClient.login(username, password);
           ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);
           ftpClient.enterLocalPassiveMode();
           ftpClient.changeWorkingDirectory(fileName);
           String[] split = fileName.split("/");
           File localFile = new File(localPath + File.separatorChar + split[split.length-1]);
           OutputStream os = new FileOutputStream(localFile);
           ftpClient.retrieveFile(fileName, os);
           os.close();
           System.out.println("liu");
           ftpClient.logout();

       } catch (IOException e) {
           System.out.println("shun");
           e.printStackTrace();
       } finally {
           if (ftpClient.isConnected()) {
               try {
                   ftpClient.disconnect();
               } catch (IOException e) {
                   e.printStackTrace();
               }
           }
       }
   }

}
