package com._4paradigm.mapper;

import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CodeMapper {

    public void insert(String code,String parentCode){
        Connection conn = null;
        PreparedStatement ps = null;
        FileInputStream in = null;
        try {
            conn = ConnUtil.getConn();
            String sql = "insert into single (servercode,programcode,lll) values (?,?,?)";
            ps = conn.prepareStatement(sql);
            ps.setString(1, parentCode);
            ps.setString(2, code);
            ps.setString(3,"1");
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
    public void insert0(String code){
        Connection conn = null;
        PreparedStatement ps = null;
        FileInputStream in = null;
        try {
            conn = ConnUtil.getConn();
            String sql = "insert into series (programcode,lll) values (?,?)";
            ps = conn.prepareStatement(sql);
            ps.setString(1, code);
            ps.setInt(2, 1);
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



    public void  updatesingle0(String code){
        Connection conn = null;
        PreparedStatement ps = null;
        FileInputStream in = null;
        try {
            conn = ConnUtil.getConn();
            String sql = "update  single set lll='0' where programcode=?";
            ps = conn.prepareStatement(sql);
            ps.setString(1, code);
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
    public void  updatesingle1(String code){
        Connection conn = null;
        PreparedStatement ps = null;
        FileInputStream in = null;
        try {
            conn = ConnUtil.getConn();
            String sql = "update  single set lll='1' where programcode=?";
            ps = conn.prepareStatement(sql);
            ps.setString(1, code);
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
    public int select ( String code){
        Connection conn = null;
        PreparedStatement ps = null;
        FileInputStream in = null;
        int rowCount = 0;

        try {
            conn = ConnUtil.getConn();
            String sql = "select * from  single where programcode = ?";
            ps = conn.prepareStatement(sql);

            ps.setString(1,code);
            ResultSet rs = ps.executeQuery();//获得查询结果
            while(rs.next()) {
                rowCount++;
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


    public List<String> selectservercode (){
        Connection conn = null;
        PreparedStatement ps = null;
        FileInputStream in = null;
        List<String> list=new ArrayList<>();

        try {
            conn = ConnUtil.getConn();
            String sql = "select t1.servercode  from (select servercode,count(*) c from single where lll='0' group by servercode ) t1\n" +
                    "left join (select servercode,count(*) c from single group by servercode) t2\n" +
                    "on t1.servercode=t2.servercode where t1.c=t2.c";
            ps = conn.prepareStatement(sql);

            ResultSet rs = ps.executeQuery();//获得查询结果
            while(rs.next()) {
                String string = rs.getString(1);
                list.add(string);
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

        return list;
    }
    public void  updateseries0(String code){
        Connection conn = null;
        PreparedStatement ps = null;
        FileInputStream in = null;
        try {
            conn = ConnUtil.getConn();
            String sql = "update  series set lll='0' where programcode=?";
            ps = conn.prepareStatement(sql);
            ps.setString(1, code);
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
    public void  updateseries1(String code){
        Connection conn = null;
        PreparedStatement ps = null;
        FileInputStream in = null;
        try {
            conn = ConnUtil.getConn();
            String sql = "update  series set lll='1' where programcode=?";
            ps = conn.prepareStatement(sql);
            ps.setString(1, code);
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
