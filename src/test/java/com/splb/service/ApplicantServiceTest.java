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
    void addTest() throws UserServiceException {
        Applicant applicant = new Applicant(1, USERNAME, PASSWORD, "FirstName",
                "LastName", "any22@mail.com", "City", "Region", "HighSchool");
        Assert.assertTrue(srv.add(applicant));
        Assert.assertFalse(srv.add(applicant));
//        Assert.assertThrows(UserServiceException.class,
//                () -> srv.add(applicant));
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
        Assert.assertTrue(srv.check(name1));
        Assert.assertTrue(srv.check(name2));
        Assert.assertFalse(srv.check(nameFalse1));
        Assert.assertFalse(srv.check(nameFalse2));
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
        Assert.assertEquals(customList.size(), 3);
    }
}