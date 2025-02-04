package com.azienda.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {

    // In-memory DB: "jdbc:h2:mem:nomeDB"
    // Con "DB_CLOSE_DELAY=-1" rimane aperto fino allo spegnimento della VM
    private static final String URL = "jdbc:h2:mem:gestionedoc;DB_CLOSE_DELAY=-1";
    private static final String USER = "sa";
    private static final String PASS = "";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASS);
    }
}
