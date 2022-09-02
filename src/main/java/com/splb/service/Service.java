package com.splb.service;

import com.splb.model.dao.*;
import com.splb.model.dao.connection.ConnectionBuilder;
import com.splb.model.dao.implementation.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.SQLException;


public abstract class Service {

    protected Logger log;
    protected ConnectionBuilder connectionBuilder;
    UserDAO udao;
    FacultyDAO fdao;
    StatementDAO sdao;
    EnrollmentDAO edao;
    ApplicantResultDAO adao;

    {
        udao = UserDAOImpl.getInstance();
        fdao = FacultyDAOImpl.getInstance();
        sdao = StatementDAOImpl.getInstance();
        edao = EnrollmentDAOImpl.getInstance();
        adao = ApplicantResultDAOImpl.getInstance();
        log = LogManager.getLogger(getClass().getName());
    }

    public void setConnectionBuilder(ConnectionBuilder connectionBuilder) {
        this.connectionBuilder = connectionBuilder;
    }

    protected Connection getConnection() throws SQLException {
        return connectionBuilder.getConnection();
    }


}
