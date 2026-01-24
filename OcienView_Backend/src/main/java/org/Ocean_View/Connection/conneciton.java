package org.Ocean_View.Connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class conneciton {

    // Database connection configuration
    private static final String URL = "jdbc:mysql://localhost:3306/mega_city";
    private static final String USERNAME = "root"; // Change to your MySQL username
    private static final String PASSWORD = ""; // Change to your MySQL password

    public static void main(String[] args) {
        System.out.println("=== MySQL Connection Test ===");
        testConnection();

        // Alternative: Test with custom parameters
        // testConnection("jdbc:mysql://localhost:3306/test", "user", "pass");
    }

    /**
     * Tests MySQL connection with default configuration
     */
    public static void testConnection() {
        testConnection(URL, USERNAME, PASSWORD);
    }

    /**
     * Tests MySQL connection with custom parameters
     * @param url Database URL
     * @param username Database username
     * @param password Database password
     */
    public static void testConnection(String url, String username, String password) {
        Connection connection = null;

        try {
            // Load MySQL JDBC Driver
            Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.println("✓ MySQL JDBC Driver loaded successfully");

            // Establish connection
            System.out.println("Attempting to connect to: " + url);
            connection = DriverManager.getConnection(url, username, password);

            if (connection != null && !connection.isClosed()) {
                System.out.println("\n✅ CONNECTION SUCCESSFUL!");
                System.out.println("==================================");
                System.out.println("Database: " + connection.getMetaData().getDatabaseProductName());
                System.out.println("Version: " + connection.getMetaData().getDatabaseProductVersion());
                System.out.println("Driver: " + connection.getMetaData().getDriverName());
                System.out.println("Driver Version: " + connection.getMetaData().getDriverVersion());
                System.out.println("URL: " + connection.getMetaData().getURL());
                System.out.println("User: " + connection.getMetaData().getUserName());
                System.out.println("==================================\n");

                // Test if connection is valid
                if (connection.isValid(5)) {
                    System.out.println("✓ Connection is valid and responsive");
                }
            }

        } catch (ClassNotFoundException e) {
            System.err.println("\n❌ ERROR: MySQL JDBC Driver not found!");
            System.err.println("Make sure mysql-connector-java-8.0.33.jar is in your classpath");
            System.err.println("Error details: " + e.getMessage());

        } catch (SQLException e) {
            System.err.println("\n❌ ERROR: Failed to connect to MySQL database!");
            System.err.println("SQL State: " + e.getSQLState());
            System.err.println("Error Code: " + e.getErrorCode());
            System.err.println("Message: " + e.getMessage());

            // Provide troubleshooting tips based on error
            provideTroubleshootingTips(e);

        } finally {
            // Close connection
            if (connection != null) {
                try {
                    connection.close();
                    System.out.println("✓ Connection closed properly");
                } catch (SQLException e) {
                    System.err.println("Warning: Error closing connection: " + e.getMessage());
                }
            }
        }
    }

    /**
     * Provides troubleshooting tips based on SQLException
     * @param e SQLException that occurred
     */
    private static void provideTroubleshootingTips(SQLException e) {
        System.err.println("\n🔧 TROUBLESHOOTING TIPS:");

        if (e.getErrorCode() == 0) {
            System.err.println("1. Check if MySQL server is running");
            System.err.println("2. Verify the database URL is correct");
            System.err.println("3. Check firewall settings");
        } else if (e.getErrorCode() == 1045) {
            System.err.println("1. Check username and password");
            System.err.println("2. Verify user has access to the database");
            System.err.println("3. Try with default MySQL root user (if testing locally)");
        } else if (e.getSQLState().equals("08S01")) {
            System.err.println("1. Communication link failure - check network connection");
            System.err.println("2. Verify MySQL server is accessible from this machine");
        }

        System.err.println("\n📝 Connection URL format: jdbc:mysql://host:port/database_name");
        System.err.println("Example: jdbc:mysql://localhost:3306/ocean_view_db");
    }

    /**
     * Quick method to check if connection is possible
     * @return true if connection successful, false otherwise
     */
    public static boolean isDatabaseAvailable() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            try (Connection conn = DriverManager.getConnection(URL, USERNAME, PASSWORD)) {
                return conn != null && conn.isValid(2);
            }
        } catch (ClassNotFoundException | SQLException e) {
            return false;
        }
    }

    /**
     * Creates and returns a database connection
     * @return Connection object or null if failed
     */
    public static Connection getConnection() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            return DriverManager.getConnection(URL, USERNAME, PASSWORD);
        } catch (ClassNotFoundException | SQLException e) {
            System.err.println("Failed to create connection: " + e.getMessage());
            return null;
        }
    }

    /**
     * Creates a connection with custom parameters
     * @param url Database URL
     * @param username Database username
     * @param password Database password
     * @return Connection object or null if failed
     */
    public static Connection getConnection(String url, String username, String password) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            return DriverManager.getConnection(url, username, password);
        } catch (ClassNotFoundException | SQLException e) {
            System.err.println("Failed to create connection: " + e.getMessage());
            return null;
        }
    }
}