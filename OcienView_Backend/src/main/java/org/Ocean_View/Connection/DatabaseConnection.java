package org.Ocean_View.Connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {

    // Database connection configuration
    private static final String URL = "jdbc:mysql://localhost:3306/ocean_view";
    private static final String USERNAME = "root"; // Change to your MySQL username
    private static final String PASSWORD = ""; // Change to your MySQL password

   public static Connection getConnection() throws SQLException {
       try
       {
           Class.forName("com.mysql.cj.jdbc.Driver");
           Connection conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
           System.out.println("Successfully Connected to the Database");
           return conn;
       }
       catch (ClassNotFoundException | SQLException e)
       {
            e.printStackTrace();
            System.out.println("Connection Error" + e.getMessage());
            throw new SQLException("Database connection Error: " + e.getMessage());

       }
   }

   public static void CloseConnection(Connection conn)
   {
       try
       {
           if (conn != null && !conn.isClosed())
           {
               conn.close();
               System.out.println("Connection Closed");
           }
       }
       catch (Exception e)
       {
           e.printStackTrace();
           System.out.println("Connection closed Error " + e.getMessage());
       }
   }

}