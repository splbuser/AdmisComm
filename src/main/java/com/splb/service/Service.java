package com.splb.service;

import com.splb.model.dao.*;
//import com.splb.model.dao.factory.DAOFactory;
//import com.splb.model.dao.factory.DAOType;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public abstract class Service {

    protected Logger log;

//    ApplicantResultDAO applicantResultDAO;
//    EnrollmentDAO enrollmentDao;
//    RegisterDAO registerDAO;
//    FacultyDAO facultyDao;
//    StatementDAO statementDAO;
//    UserDAO userDao;

    {
        log = LogManager.getLogger(getClass().getName());
//        applicantResultDAO = (ApplicantResultDAO) DAOFactory.getDao(DAOType.RESULT_DAO);
//        enrollmentDao = (EnrollmentDAO) DAOFactory.getDao(DAOType.ENROLLMENT_DAO);
//        registerDAO = (RegisterDAO) DAOFactory.getDao(DAOType.REGISTER_DAO);
//        facultyDao = (FacultyDAO) DAOFactory.getDao(DAOType.FACULTY_DAO);
//        statementDAO = (StatementDAO) DAOFactory.getDao(DAOType.STATEMENT_DAO);
//        userDao = (UserDAO) DAOFactory.getDao(DAOType.USER_DAO);
    }

}
