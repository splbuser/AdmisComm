package com.splb.model.dao.implementation;

import com.splb.model.dao.UserDAO;
import com.splb.model.dao.exception.UserDAOException;
import com.splb.model.entity.Applicant;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.testng.Assert;

import java.sql.Connection;

class UserDAOImplTest {

    public static final String USER_NAME = "User123";
    public static final String PASSWORD = "password";
    final static UserDAO dao = UserDAOImpl.getInstance();
    static Connection con;

    @BeforeAll
    static void setUp() throws Exception {
        con = new DirectConnectionBuilder().getConnection();
    }

    @AfterAll
    static void tearDown() throws UserDAOException {
        dao.delete(USER_NAME, con);
    }

    @Test
    void addUserTest() throws UserDAOException {

        Applicant applicant = new Applicant(1, USER_NAME, PASSWORD, "FirstName",
                "LastName", "any@mail.com", "City", "Region", "HighSchool");
        Assert.assertTrue(dao.addApplicant(applicant, con));
        Assert.assertFalse(dao.addApplicant(applicant, con));
    }

    @Test
    void getUserTest() throws UserDAOException {
        Assert.assertNotNull(dao.getUser(USER_NAME, PASSWORD, con));
        Assert.assertNull(dao.getUser("User", "pass", con));
    }

    @Test
    void findApplicantByNameTest() throws UserDAOException {
        Assert.assertTrue(dao.findApplicantByName(USER_NAME, con));
        Assert.assertTrue(dao.findApplicantByName("aramayo", con));
        Assert.assertFalse(dao.findApplicantByName("cuscu", con));
    }

    @Test
    void findAllApplicantsTest() throws UserDAOException {
        Assert.assertEquals(dao.findAllApplicants(10, 0, con).size(), 10);
        Assert.assertEquals(dao.findAllApplicants(5, 0, con).size(), 5);
    }

    @Test
    void getApplicantByIdTest() throws UserDAOException {
        Assert.assertEquals(dao.getApplicantById(1, con).getId(), 1);
        Assert.assertEquals(dao.getApplicantById(2, con).getId(), 2);
        Assert.assertEquals(dao.getApplicantById(10, con).getId(), 10);
        Assert.assertThrows(NullPointerException.class,
                () -> dao.getApplicantById(100, con).getId());
    }

    @Test
    void blockUserByIdTest() throws UserDAOException {
        dao.blockUserById(5, con);
        dao.blockUserById(6, con);
        Assert.assertTrue(dao.isBlockedUserCheck(5, con));
        Assert.assertTrue(dao.isBlockedUserCheck(6, con));
    }

    @Test
    void unblockUserById() throws UserDAOException {
        dao.unblockUserById(5, con);
        dao.unblockUserById(6, con);
        Assert.assertFalse(dao.isBlockedUserCheck(5, con));
        Assert.assertFalse(dao.isBlockedUserCheck(6, con));
    }

    @Test
    void getApplicantsFacultyListTest() throws UserDAOException {
        Assert.assertEquals(dao.getApplicantsFacultyList(2, con).size(), 3);
    }

    @Test
    void getApplicantsFacultyResultTest() throws UserDAOException {
        int[] intsOne = dao.getApplicantsFacultyResult(2, 2, con);
        Assert.assertEquals(intsOne[0], 10);
        Assert.assertEquals(intsOne[1], 9);
        int[] intsTwo = dao.getApplicantsFacultyResult(2, 4, con);
        Assert.assertEquals(intsTwo[0], 9);
        Assert.assertEquals(intsTwo[1], 9);
    }

    @Test
    void getLengthTest() throws UserDAOException {
        Assert.assertTrue(dao.getLength(con) >= 15);
    }

    @Test
    void deleteUserTest() throws UserDAOException {
        Assert.assertTrue(dao.delete(USER_NAME, con));
        Assert.assertEquals(dao.getApplicantForSearch(USER_NAME, con).size(), 0);
        Assert.assertFalse(dao.delete(USER_NAME, con));
    }

    @Test
    void uploadTest() throws UserDAOException {
        String uploadImage = "user_image.jpg";
        for (int i = 2; i <= 10; i++) {
            dao.upload(i, uploadImage, con);
            Applicant applicant = dao.getApplicantById(i, con);
            Assert.assertEquals(applicant.getUploaded(), uploadImage);
        }
    }
}