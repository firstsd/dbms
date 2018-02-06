package Servlet;
import entity.Staff;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DBtest {

    public List<Staff> test() {

        // Create a variable for the connection string.
        String connectionUrl = "jdbc:sqlserver://10.10.11.165:1433;databaseName=telephoneCompany;user=dbms;password=Dbms_123";

        // Declare the JDBC objects.
        Connection con = null;
        Statement stmt = null;
        ResultSet rs = null;
        List<Staff> ret = null;
        try {
            // Establish the connection.
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            con = DriverManager.getConnection(connectionUrl);

            // Create and execute an SQL statement that returns some data.
            String SQL = "SELECT * FROM staff";
            stmt = con.createStatement();
            rs = stmt.executeQuery(SQL);

            // Iterate through the data in the result set and display it.
            while (rs.next()) {
                if (ret == null) {
                    ret = new ArrayList<Staff>();
                }

                ret.add(new Staff(rs.getInt("ID"), rs.getString("staffName")));
                System.out.println((rs.getString("ID") + " " + rs.getString("staffName")));
            }
        }

        // Handle any errors that may have occurred.
        catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            if (rs != null) try { rs.close(); } catch(Exception e) {}
            if (stmt != null) try { stmt.close(); } catch(Exception e) {}
            if (con != null) try { con.close(); } catch(Exception e) {}
        }
        return ret;
    }
} 