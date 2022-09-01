package com.splb.model.dao.implementation;

import com.splb.model.dao.StatementDAO;
import com.splb.model.dao.exception.StatementDAOException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.testng.Assert;


class StatementDAOImplTest {

    private final static StatementDAO dao = StatementDAOImpl.getInstance();

    @BeforeAll
    static void beforeAll() {
    }

    @Test
    void addUserToFacultyTest() throws StatementDAOException {
        for (int i = 1; i <= 5; i++) {
            for (int j = 2; j <= 5; j++) {
                Assert.assertTrue(dao.addUserToFaculty(i, j));
            }
        }
        for (int i = 1; i <= 5; i++) {
            for (int j = 2; j <= 5; j++) {
                int finalI = i;
                int finalJ = j;
                Assert.assertThrows(StatementDAOException.class,
                        () -> {
                            dao.addUserToFaculty(finalI, finalJ);
                        });
            }
        }
    }

    @Test
    void removeUserFromFacultyTest() throws StatementDAOException {
        for (int i = 1; i <= 3; i++) {
            for (int j = 2; j <= 3; j++) {
                Assert.assertTrue(dao.removeUserFromFaculty(i, j));
            }
        }
//        Assert.assertEquals(dao.getStatementList().size(), 9);

        Assert.assertEquals(dao.getFacultyFromStatementForApplicant(2).size(), 2);
        Assert.assertEquals(dao.getFacultyFromStatementForApplicant(3).size(), 2);
        Assert.assertEquals(dao.getFacultyFromStatementForApplicant(4).size(), 5);
        Assert.assertEquals(dao.getFacultyFromStatementForApplicant(5).size(), 5);

        Assert.assertEquals(dao.getStatementResult(2).size(), 2);
        Assert.assertEquals(dao.getStatementResult(3).size(), 2);
        Assert.assertEquals(dao.getStatementResult(4).size(), 5);
        Assert.assertEquals(dao.getStatementResult(2).size(), 2);
    }

    @Test
    void checkUserFacultyTest() throws StatementDAOException {
        for (int i = 1; i <= 5; i++) {
            for (int j = 4; j <= 5; j++) {
                Assert.assertTrue(dao.checkUserFaculty(i, j));
            }
        }
        for (int i = 1; i <= 2; i++) {
            for (int j = 1; j < 2; j++) {
                Assert.assertFalse(dao.checkUserFaculty(i, j));
            }
        }
    }

}