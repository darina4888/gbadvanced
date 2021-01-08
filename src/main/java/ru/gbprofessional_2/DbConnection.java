package ru.gbprofessional_2;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DbConnection {

    private static DbConnection dbIsntance = null;
    private static Connection connection = null;
    private String url = "jdbc:sqlite:firstDB";


    private DbConnection () {
        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection(url);
        } catch (Exception e) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }
    }

    public static Connection getConnection() {
        return connection;
    }

    public static DbConnection getInstance() throws SQLException {
        if (dbIsntance == null || getConnection().isClosed()) {
            dbIsntance = new DbConnection();
        }
        return dbIsntance;
    }


}
