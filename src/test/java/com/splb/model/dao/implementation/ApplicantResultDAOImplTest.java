package com.splb.model.dao.implementation;

import com.splb.model.dao.ApplicantResultDAO;
import com.splb.model.dao.UserDAO;
import com.splb.model.dao.exception.ApplicantResultDAOException;
import com.splb.model.dao.exception.FacultyDAOException;
import com.splb.model.dao.exception.UserDAOException;
import com.splb.model.entity.Applicant;
import com.splb.model.entity.ApplicantResult;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testng.Assert;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class ApplicantResultDAOImplTest {

    public static final String USERNAME = "TestUser";
    public static final String PASSWORD = "password";
    final static ApplicantResultDAO dao = ApplicantResultDAOImpl.getInstance();
    final static UserDAO udao = UserDAOImpl.getInstance();

    @BeforeEach
    void setUp() throws Exception {
    }

    @AfterAll
    static void tearDown() throws SQLException, ApplicantResultDAOException {
        dao.deleteResults(new DirectConnectionBuilder().getConnection(), 15);
    }

    @Test
    void insertTest() throws ApplicantResultDAOException, UserDAOException {
        Applicant applicant = new Applicant(1, USERNAME, PASSWORD, "FirstName",
                "LastName", "any2@mail.com", "City", "Region", "HighSchool");
        udao.addApplicant(applicant);
        int id = udao.getUser(USERNAME, PASSWORD).getId();
        ApplicantResult result = new ApplicantResult(id, 12, 11, 10, 9, 8, 7);
        Assert.assertTrue(dao.insert(result));
        Assert.assertThrows(ApplicantResultDAOException.class,
                () -> {
                    dao.insert(result);
                });
    }

    @Test
    void getApplicantResultTest() throws UserDAOException, ApplicantResultDAOException {
        int id = udao.getUser(USERNAME, PASSWORD).getId();
        Assert.assertEquals(dao.getApplicantResult(id).getAlgebra(), 12);
        Assert.assertEquals(dao.getApplicantResult(id).getBiology(), 11);
        Assert.assertEquals(dao.getApplicantResult(id).getChemistry(), 10);
        Assert.assertEquals(dao.getApplicantResult(id).getEnglish(), 9);
        Assert.assertEquals(dao.getApplicantResult(id).getLiterature(), 8);
        Assert.assertEquals(dao.getApplicantResult(id).getWorldHistory(), 7);
    }

    @Test
    void isSubmittedResultTest() throws ApplicantResultDAOException {
        for (int i = 2; i <= 7; i++) {
            Assert.assertTrue(dao.isSubmittedResult(2));
        }
    }

    @Test
    void deleteResultsTest() throws UserDAOException, SQLException, ApplicantResultDAOException {
        for (int i = 7; i <= 13; i++) {
            Assert.assertTrue(dao.deleteResults
                    (new DirectConnectionBuilder().getConnection(), i));
        }
    }

}