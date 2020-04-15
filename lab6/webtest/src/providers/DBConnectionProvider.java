package providers;

//import java.sql.Connection;
//import java.sql.DriverManager;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnectionProvider {
    private static String url = "jdbc:mysql://remotemysql.com:3306/1WxvvnNvoF?allowPublicKeyRetrieval=true&serverTimezone=Europe/Moscow&useSSL=false";
    private final String adminPassword = "vwgRJ7gbzD";
    private final String  adminUserName = "1WxvvnNvoF";
    private final String driverName = "com.mysql.cj.jdbc.Driver";
    private Connection connection;

    public DBConnectionProvider(){
        initConnection();
    }

    public Connection getConnection() {
        return connection;
    }

    private void initConnection(){
        try {
            Class.forName(driverName);
            connection = DriverManager.getConnection(url, adminUserName, adminPassword);
        }
        catch(ClassNotFoundException e)
        {
            System.out.println("CLASS NOT FOUND");
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
