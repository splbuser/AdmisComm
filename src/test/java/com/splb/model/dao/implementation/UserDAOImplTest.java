package com.splb.model.dao.implementation;

import com.splb.model.dao.UserDAO;
import com.splb.model.dao.exception.FacultyDAOException;
import com.splb.model.dao.exception.UserDAOException;
import com.splb.model.entity.Applicant;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.testng.Assert;

class UserDAOImplTest {

    public static final String USER_NAME = "User123";
    public static final String PASSWORD = "password";
    final static UserDAO dao = UserDAOImpl.getInstance();

    @BeforeAll
    static void setUp() throws Exception {
        DBInit.startUp(UserDAOImplTest.class);
    }

    @AfterAll
    static void tearDown() throws UserDAOException {
        dao.delete(USER_NAME);
    }

    @Test
    void addUserTest() throws UserDAOException {

        Applicant applicant = new Applicant(1, USER_NAME, PASSWORD, "FirstName",
                "LastName", "any@mail.com", "City", "Region", "HighSchool");
        Assert.assertTrue(dao.addApplicant(applicant));
        Assert.assertFalse(dao.addApplicant(applicant));
    }

    @Test
    void getUserTest() throws UserDAOException {
        Assert.assertNotNull(dao.getUser(USER_NAME, PASSWORD));
        Assert.assertNull(dao.getUser("User", "pass"));
    }

    @Test
    void findApplicantByNameTest() throws UserDAOException {
        Assert.assertTrue(dao.findApplicantByName(USER_NAME));
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
        Assert.assertTrue(dao.getLength() >= 15);
    }

    @Test
    void deleteUserTest() throws UserDAOException {
        Assert.assertTrue(dao.delete(USER_NAME));
        Assert.assertEquals(dao.getApplicantForSearch(USER_NAME).size(),0);
        Assert.assertFalse(dao.delete(USER_NAME));
    }

    @Test
    void uploadTest() throws UserDAOException {
        String uploadImage = "user_image.jpg";
        for (int i = 2; i <= 10; i++) {
            dao.upload(i, uploadImage);
            Applicant applicant = dao.getApplicantById(i);
            Assert.assertEquals(applicant.getUploaded(), uploadImage);
        }

    }

}