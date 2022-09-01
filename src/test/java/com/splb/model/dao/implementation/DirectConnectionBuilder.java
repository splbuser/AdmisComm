package com.splb.model.dao.implementation;

import com.splb.model.dao.connection.ConnectionBuilder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DirectConnectionBuilder implements ConnectionBuilder {
    private final Logger log = LogManager.getLogger(com.splb.model.dao.connection.DirectConnectionBuilder.class);
    public static final String URI_ALT = "jdbc:mysql://localhost:3306/admissionscommitteetest";
    public static final String USER = "root";
    public static final String PASSWORD = "128500";

    @Override
    public Connection getConnection() throws SQLException {
//        log.info("connection via DirectCB#2 @Test");
        return DriverManager.getConnection(URI_ALT, USER, PASSWORD);
    }
}
