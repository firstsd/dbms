package Database;

import java.sql.*;
import java.util.Enumeration;

public class DBconnector {

    private Connection conn;

    private DBconnector() {
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            conn = DriverManager.getConnection("jdbc:sqlserver://10.0.0.26:1433;databaseName=telephoneCompany;user=dbms;password=Dbms_123");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static DBconnector connector = new DBconnector();

    public static DBconnector getconnector() {
        return connector;
    }

    public Connection getconnection() {
        return conn;
    }

    public void closeConnection() {
        try {
            if (conn != null && !conn.isClosed()) {
                conn.close();
            }
            Enumeration<Driver> drivers = DriverManager.getDrivers();
            Driver driver = null;
            // clear drivers
            while (drivers.hasMoreElements()) {
                try {
                    driver = drivers.nextElement();
                    DriverManager.deregisterDriver(driver);
                } catch (SQLException ex) {
                }
            }
        } catch (Exception e) {
        }
    }
}
