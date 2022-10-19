package com._4paradigm.util;

import com._4paradigm.log.CommonLogger;
import org.apache.commons.net.ftp.FTPClient;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

public class ftp {
   public void downloadFile(String hostname,int port,String username,String password,String fileName ,String localPath){
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
           System.out.println("localFile.getPath() = " + localFile.getPath());
           OutputStream os = new FileOutputStream(localFile);
           ftpClient.retrieveFile(fileName, os);
           os.close();
           ftpClient.logout();

       } catch (IOException e) {
           CommonLogger.error(e.toString());
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
