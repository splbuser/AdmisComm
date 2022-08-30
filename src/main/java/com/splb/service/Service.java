package com.splb.service;

import com.splb.model.dao.*;
import com.splb.model.dao.implementation.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public abstract class Service {

    protected Logger log;
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

}
