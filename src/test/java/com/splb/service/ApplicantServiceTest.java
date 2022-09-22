package com.splb.service;

import com.splb.model.dao.connection.DirectConnectionBuilder;
import com.splb.model.dao.exception.ApplicantResultDAOException;
import com.splb.model.entity.Applicant;
import com.splb.model.entity.Faculty;
import com.splb.service.exceptions.UserServiceException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testng.Assert;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ApplicantServiceTest {

    static ApplicantService srv;
    public static final String USERNAME = "TestUser2";
    public static final String PASSWORD = "password2";

    @BeforeEach
    void setUp() {
        srv = new ApplicantService(new DirectConnectionBuilder());
    }

    @Test
    void loginTest() throws UserServiceException {
        Applicant applicant = srv.login("user", "pass");
        Assert.assertNull(applicant);
    }

    @Test
    void addUpdateTest() throws UserServiceException {
        Applicant applicant = new Applicant(1, USERNAME, PASSWORD, "FirstName",
                "LastName", "any22@mail.com", "City", "Region", "HighSchool");
        Assert.assertTrue(srv.add(applicant));
        Assert.assertFalse(srv.add(applicant));
        applicant.setFirstName("NameUpdated");
        applicant.setLastName("LastNameUpdated");
        applicant.setRegion("RegionUpdated");
        boolean upload1 = srv.upload(1, "image1.jpg");
        boolean upload2 = srv.upload(2, "image2.jpg");
        boolean reUpload2 = srv.upload(2, "image2.jpg");
        boolean block = srv.block(1);
        boolean reBlock = srv.block(1);
        boolean unBlock = srv.unblock(1);
        boolean reUnBlock = srv.unblock(1);
        Assert.assertTrue(srv.update(applicant));
        Assert.assertTrue(upload1);
        Assert.assertTrue(upload2);
        Assert.assertTrue(reUpload2);
        Assert.assertTrue(block);
        Assert.assertFalse(reBlock);
        Assert.assertTrue(unBlock);
        Assert.assertFalse(reUnBlock);
    }

    @Test
    void getTest() throws UserServiceException {
        for (int i = 2; i < 5; i++) {
            Applicant applicant = srv.get(i);
            Assert.assertNotNull(applicant);
        }
        for (int i = 20; i < 25; i++) {
            Applicant applicant = srv.get(i);
            Assert.assertNull(applicant);
        }
    }

    @Test
    void checkTest() throws UserServiceException {
        String name1 = "Boniadi99";
        String name2 = "aramayo";
        String nameFalse1 = "uuu1";
        String nameFalse2 = "Jerebco";
        String mail1 = "any22@mail.com";
        String mail2 = "2any2@mail.com";
        String mail3 = "@";
        Assert.assertTrue(srv.check(name1));
        Assert.assertTrue(srv.check(name2));
        Assert.assertFalse(srv.check(nameFalse1));
        Assert.assertFalse(srv.check(nameFalse2));
        Assert.assertTrue(srv.checkUsername(name2));
        Assert.assertFalse(srv.checkUsername(nameFalse1));
        Assert.assertTrue(srv.checkEmail(mail1));
        Assert.assertFalse(srv.checkEmail(mail2));
        Assert.assertFalse(srv.checkEmail(mail3));
    }

    @Test
    void listTest() throws UserServiceException {
        List<Applicant> all = null;
        List<Faculty> customList = null;
        all = srv.getAll(5, 0);
        Assert.assertEquals(all.size(), 5);
        all = srv.getAll(10, 0);
        Assert.assertEquals(all.size(), 10);
        customList = srv.getCustomList(2);
        int length = srv.length();
        List<Applicant> notEnroll = srv.getNotEnroll();
        Assert.assertEquals(customList.size(), 3);
        Assert.assertEquals(length, 15);
        Assert.assertEquals(notEnroll.size(), 16);
    }
}