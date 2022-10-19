package com._4paradigm.util;

import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CodeMapper {
    public  String select ( String code){
        Connection conn = null;
        PreparedStatement ps = null;
        FileInputStream in = null;
        String rowCount = "";

        try {
            conn = ConnUtil.getConn();
            String sql = "select servercode from  single where programcode = ?";
            ps = conn.prepareStatement(sql);

            ps.setString(1,code);
            ResultSet rs = ps.executeQuery();//获得查询结果
            while(rs.next()) {
                rowCount=rs.getString(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            ConnUtil.closeConn(conn);
            if (null != ps) {
                try {
                    ps.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

        return rowCount;
    }
    public  String selectcode ( String code){
        Connection conn = null;
        PreparedStatement ps = null;
        FileInputStream in = null;
        String rowCount = "";

        try {
            conn = ConnUtil.getConn();
            String sql = "select servercode from  single where programcode = ?";
            ps = conn.prepareStatement(sql);

            ps.setString(1,code);
            ResultSet rs = ps.executeQuery();//获得查询结果
            while(rs.next()) {
               rowCount=rs.getString(1);

            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            ConnUtil.closeConn(conn);
            if (null != ps) {
                try {
                    ps.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

        return rowCount;
    }
}
