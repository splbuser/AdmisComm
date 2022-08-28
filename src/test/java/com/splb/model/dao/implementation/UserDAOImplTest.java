package com.splb.model.dao.implementation;

import com.splb.model.dao.UserDAO;
import com.splb.model.dao.exception.UserDAOException;
import com.splb.model.entity.Applicant;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.testng.Assert;

class UserDAOImplTest {

    UserDAO dao = UserDAOImpl.getInstance();

    @BeforeAll
    static void setUp() throws Exception {
        DBInit.startUp();
    }

    @Test
    void addUserTest() throws UserDAOException {

        Applicant applicant = new Applicant(1, "User123", "password", "FirstName",
                "LastName", "any@mail.com", "City", "Region", "HighSchool");
        Assert.assertTrue(dao.addApplicant(applicant));
        Assert.assertFalse(dao.addApplicant(applicant));
    }

    @Test
    void getUserTest() throws UserDAOException {
        Assert.assertNotNull(dao.getUser("User123", "password"));
        Assert.assertNull(dao.getUser("User", "pass"));
    }

    @Test
    void findApplicantByNameTest() throws UserDAOException {
        Assert.assertTrue(dao.findApplicantByName("User123"));
        Assert.assertTrue(dao.findApplicantByName("aramayo"));
        Assert.assertFalse(dao.findApplicantByName("cuscu"));
    }

    @Test
    void findAllApplicantsTest() throws UserDAOException {
        Assert.assertEquals(dao.findAllApplicants(10, 0).size(), 10);
        Assert.assertEquals(dao.findAllApplicants(5, 0).size(), 5);
    }

    @Test
    void getApplicantByIdTest() throws UserDAOException {
        Assert.assertEquals(dao.getApplicantById(1).getId(), 1);
        Assert.assertEquals(dao.getApplicantById(2).getId(), 2);
        Assert.assertEquals(dao.getApplicantById(10).getId(), 10);
        Assert.assertThrows(NullPointerException.class,
                () -> {
                    dao.getApplicantById(100).getId();
                });
    }

    @Test
    void blockUserByIdTest() throws UserDAOException {
        dao.blockUserById(5);
        dao.blockUserById(6);
        Assert.assertTrue(dao.isBlockedUserCheck(5));
        Assert.assertTrue(dao.isBlockedUserCheck(6));
    }

    @Test
    void unblockUserById() throws UserDAOException {
        dao.unblockUserById(5);
        dao.unblockUserById(6);
        Assert.assertFalse(dao.isBlockedUserCheck(5));
        Assert.assertFalse(dao.isBlockedUserCheck(6));
    }

    @Test
    void getApplicantsFacultyListTest() throws UserDAOException {
        Assert.assertEquals(dao.getApplicantsFacultyList(2).size(), 2);
        Assert.assertEquals(dao.getApplicantsFacultyList(4).size(), 4);
    }

    @Test
    void getApplicantsFacultyResultTest() throws UserDAOException {
        int[] intsOne = dao.getApplicantsFacultyResult(2, 2);
        Assert.assertEquals(intsOne[0], 10);
        Assert.assertEquals(intsOne[1], 9);
        int[] intsTwo = dao.getApplicantsFacultyResult(4, 4);
        Assert.assertEquals(intsTwo[0], 11);
        Assert.assertEquals(intsTwo[1], 12);
    }

    @Test
    void getLengthTest() throws UserDAOException {
       Assert.assertEquals(dao.getLength(), 15);
    }
}