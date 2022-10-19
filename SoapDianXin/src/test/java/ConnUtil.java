import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnUtil {
    public static Connection getConn() {
        Connection conn = null;
        try
        {
            Class.forName("com.mysql.jdbc.Driver");
            String url = "jdbc:mysql://localhost:3306/sys?useUnicode=true&characterEncoding=utf-8";
            try
        {
            conn = DriverManager.getConnection(url, "root", "liushunli521"); }
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
