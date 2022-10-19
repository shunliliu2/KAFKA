package com._4paradigm.mapper;

import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CodeMapper {

    public void insert(String userId,String date,String label){
        Connection conn = null;
        PreparedStatement ps = null;
        FileInputStream in = null;
        try {
            conn = ConnUtil.getConn();
            String sql = "insert into label (userId,date,label) values (?,?,?)";
            ps = conn.prepareStatement(sql);
            ps.setString(1, userId);
            ps.setString(2, date);
            ps.setString(3,label);
            ps.executeUpdate();

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

    }


}
