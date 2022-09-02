package com.splb.model.dao.implementation;

import com.splb.model.dao.ApplicantResultDAO;
import com.splb.model.dao.UserDAO;
import com.splb.model.dao.exception.ApplicantResultDAOException;
import com.splb.model.dao.exception.FacultyDAOException;
import com.splb.model.dao.exception.UserDAOException;
import com.splb.model.entity.Applicant;
import com.splb.model.entity.ApplicantResult;
import org.junit.jupiter.api.*;
import org.testng.Assert;

import java.sql.Connection;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class ApplicantResultDAOImplTest {

    public static final String USERNAME = "TestUser";
    public static final String PASSWORD = "password";
    final static ApplicantResultDAO dao = ApplicantResultDAOImpl.getInstance();
    final static UserDAO udao = UserDAOImpl.getInstance();
    static Connection con;

    @BeforeAll
    static void setUp() throws Exception {
        con = new DirectConnectionBuilder().getConnection();
    }

    @AfterAll
    static void tearDown() throws SQLException, ApplicantResultDAOException {
        dao.deleteResults(new DirectConnectionBuilder().getConnection(), 15);
    }

    @Test
    void insertTest() throws ApplicantResultDAOException, UserDAOException {
        Applicant applicant = new Applicant(1, USERNAME, PASSWORD, "FirstName",
                "LastName", "any2@mail.com", "City", "Region", "HighSchool");
        udao.addApplicant(applicant, con);
        int id = udao.getUser(USERNAME, PASSWORD, con).getId();
        ApplicantResult result = new ApplicantResult(id, 12, 11, 10, 9, 8, 7);
        Assert.assertTrue(dao.insert(result, con));
        Assert.assertThrows(ApplicantResultDAOException.class,
                () -> dao.insert(result, con));
    }

    @Test
    void getApplicantResultTest() throws UserDAOException, ApplicantResultDAOException {
        int id = udao.getUser(USERNAME, PASSWORD, con).getId();
        Assert.assertEquals(dao.getApplicantResult(id, con).getAlgebra(), 12);
        Assert.assertEquals(dao.getApplicantResult(id, con).getBiology(), 11);
        Assert.assertEquals(dao.getApplicantResult(id, con).getChemistry(), 10);
        Assert.assertEquals(dao.getApplicantResult(id, con).getEnglish(), 9);
        Assert.assertEquals(dao.getApplicantResult(id, con).getLiterature(), 8);
        Assert.assertEquals(dao.getApplicantResult(id, con).getWorldHistory(), 7);
    }

    @Test
    void isSubmittedResultTest() throws ApplicantResultDAOException {
        for (int i = 2; i <= 7; i++) {
            Assert.assertTrue(dao.isSubmittedResult(2, con));
        }
    }

    @Test
    void deleteResultsTest() throws SQLException, ApplicantResultDAOException {
        for (int i = 7; i <= 13; i++) {
            Assert.assertTrue(dao.deleteResults(con, i));
        }
    }

    @Test
    void addFacultySubjectResultTest() throws ApplicantResultDAOException {
        Assert.assertTrue(dao.addFacultySubjectResult(2, 12, 12, 1, con));
        Assert.assertThrows(ApplicantResultDAOException.class,
                () -> dao.addFacultySubjectResult(2, 12, 12, 1, con));
    }

}