package com.splb.model.dao;

import com.splb.model.dao.connection.ConnectionBuilder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.SQLException;

public abstract class AbstractDAO {

    protected ConnectionBuilder connectionBuilder;
    protected Logger log;

    public void setConnectionBuilder(ConnectionBuilder connectionBuilder) {
        this.connectionBuilder = connectionBuilder;
    }

    protected Connection getConnection() throws SQLException {
        return connectionBuilder.getConnection();
    }

}
