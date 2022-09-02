package com.splb.model.dao.implementation;

import com.splb.model.dao.StatementDAO;
import com.splb.model.dao.exception.StatementDAOException;
import com.splb.model.entity.Applicant;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.testng.Assert;

import java.sql.Connection;
import java.util.List;


class StatementDAOImplTest {

    private final static StatementDAO dao = StatementDAOImpl.getInstance();
    static Connection con;

    @BeforeAll
    static void setUp() throws Exception {
        con = new DirectConnectionBuilder().getConnection();
    }

    @Test
    void addUserToFacultyTest() throws StatementDAOException {
        for (int i = 1; i <= 5; i++) {
            for (int j = 2; j <= 5; j++) {
                Assert.assertTrue(dao.addUserToFaculty(i, j, con));
            }
        }
        for (int i = 1; i <= 5; i++) {
            for (int j = 2; j <= 5; j++) {
                int finalI = i;
                int finalJ = j;
                Assert.assertThrows(StatementDAOException.class,
                        () -> {
                            dao.addUserToFaculty(finalI, finalJ, con);
                        });
            }
        }
    }

    @Test
    void removeUserFromFacultyTest() throws StatementDAOException {
        for (int i = 1; i <= 3; i++) {
            for (int j = 2; j <= 3; j++) {
                Assert.assertTrue(dao.removeUserFromFaculty(i, j, con));
            }
        }
        Assert.assertEquals(dao.getFacultyFromStatementForApplicant(2, con).size(), 2);
        Assert.assertEquals(dao.getFacultyFromStatementForApplicant(3, con).size(), 2);
        Assert.assertEquals(dao.getFacultyFromStatementForApplicant(4, con).size(), 5);
        Assert.assertEquals(dao.getFacultyFromStatementForApplicant(5, con).size(), 5);
        Assert.assertEquals(dao.getStatementResult(2, con).size(), 2);
        Assert.assertEquals(dao.getStatementResult(3, con).size(), 2);
        Assert.assertEquals(dao.getStatementResult(4, con).size(), 5);
        Assert.assertEquals(dao.getStatementResult(2, con).size(), 2);
    }

    @Test
    void checkUserFacultyTest() throws StatementDAOException {
        for (int i = 1; i <= 5; i++) {
            for (int j = 4; j <= 5; j++) {
                Assert.assertTrue(dao.checkUserFaculty(i, j, con));
            }
        }
        for (int i = 1; i <= 2; i++) {
            for (int j = 1; j < 2; j++) {
                Assert.assertFalse(dao.checkUserFaculty(i, j, con));
            }
        }
    }

    @Test
    void listTest() throws StatementDAOException {
        List<Applicant> list = dao.getFacultysApplicantsFromStatement(1, con);
        Assert.assertEquals(list.size(),4);
    }

}