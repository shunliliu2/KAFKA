package com._4paradigm.mapper;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnUtil {
    public static Connection getConn() {
        Connection conn = null;
        try
        {
            Class.forName("com.mysql.jdbc.Driver");
            String url = "jdbc:mysql://172.25.3.102:3306/wuliaolishiipindao?useUnicode=true&characterEncoding=utf-8";
            //String url = "jdbc:mysql://192.168.1.12:3306/sys?useUnicode=true&characterEncoding=utf-8";
            try
        {
            conn = DriverManager.getConnection(url, "root", "Admin@4pd"); }
            //conn = DriverManager.getConnection(url, "root", "liushunli521"); }
        catch (SQLException e) {
            e.printStackTrace(); }
        }
        catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return conn;
    }
         // 关闭连接
    public static void closeConn(Connection conn) {
                 if (null != conn) {
                         try {
                                conn.close();
                             } catch (SQLException e) {
                                 System.out.println("关闭连接失败！");
                                 e.printStackTrace();
                             }
                     }
             }
}
