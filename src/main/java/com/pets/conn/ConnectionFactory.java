package com.pets.conn;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionFactory {

    public static Connection getConnection() throws SQLException {
        String url = "jdbc:postregresql://localhost:5432/pets_db";
        String username = "root";
        String password = "root";

        return DriverManager.getConnection(url, username, password);
    }

}
