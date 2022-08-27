package com.splb.model.dao.connection;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * setup connection and pool configuration in webapp/META-INF/context.xml
 */

public class PoolConnectionBuilder implements ConnectionBuilder {
    private static final Logger log = LogManager.getLogger(PoolConnectionBuilder.class);
    public static final String URI = "java:comp/env/jdbc/AdmissionsCommittee";

    private DataSource dataSource;

    public PoolConnectionBuilder() {
        try {
            Context ctx = new InitialContext();
            dataSource = (DataSource) ctx.lookup(URI);
        } catch (NamingException e) {
            log.error("PoolConnectionBuilder error: {}", e.getMessage());
        }
    }

    @Override
    public Connection getConnection() throws SQLException {
//        log.info("connection via PoolCB");
        return dataSource.getConnection();
    }
}