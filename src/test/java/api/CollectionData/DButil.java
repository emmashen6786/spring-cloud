package api.CollectionData;



import groovy.util.logging.Slf4j;

import java.sql.*;

/**
 * 连接数据库的工具类
 */
@Slf4j
public class DButil {
    public static Connection GetAmquecCnnection() {
        Connection conn = null;
        try {
//            加载驱动
            Class.forName("com.mysql.jdbc.Driver");
            System.out.println("连接数据库。。。");
//            创建连接
            String url = "jdbc:mysql://10.18.27.66:3326/amque?useSSL=false&serverTimezone=UTC";
            String user = "amque";
            String password = "FZBdB20VE6";
            conn = DriverManager.getConnection(url, user, password);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return conn;
    }

    public static Connection GetCollectionDataCnnection() {
        Connection conn = null;
        try {
//            加载驱动
            Class.forName("com.mysql.jdbc.Driver");
            System.out.println("连接数据库。。。");
//            创建连接
            String url = "jdbc:mysql://10.18.27.68:3326/collection_data?useSSL=false&serverTimezone=UTC";
            String user = "collection_data";
            String password = "6U3YvUmBnb";
            conn = DriverManager.getConnection(url, user, password);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return conn;
    }

    public static void Close(ResultSet resultSet, Connection connection, Statement statement) {
        if (resultSet != null) {
            try {
                resultSet.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (connection != null) {
            try {
                connection.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (statement != null) {
            try {
                statement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
