package com.splb.model.dao.connection;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * interface for getting connection
 */
public interface ConnectionBuilder {
    Connection getConnection() throws SQLException;
}