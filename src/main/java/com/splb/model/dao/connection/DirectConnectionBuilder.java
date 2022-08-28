package com.splb.model.dao.connection;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * for @test purpose only
 */
public class DirectConnectionBuilder implements ConnectionBuilder {
    private final Logger log = LogManager.getLogger(DirectConnectionBuilder.class);
    public static final String URI_ALT = "jdbc:mysql://localhost:3306/admissionscommittee";
    public static final String USER = "root";
    public static final String PASSWORD = "128500";

    @Override
    public Connection getConnection() throws SQLException {
        log.info("connection via DirectCB");
        return DriverManager.getConnection(URI_ALT, USER, PASSWORD);
    }
}
