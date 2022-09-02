package com.splb.service;

import com.splb.model.dao.connection.DirectConnectionBuilder;
import com.splb.model.entity.Applicant;
import com.splb.model.entity.Faculty;
import com.splb.service.exceptions.FacultyServiceException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testng.Assert;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class FacultyServiceTest {

    public static final String BIOLOGY = "Biology";
    public static final String RENAMED_FIELD = "RenamedField";
    public static final String ENGINEERING = "Engineering";
    static FacultyService srv;

    @BeforeEach
    void setUp() {
        srv = new FacultyService(new DirectConnectionBuilder());
    }

    @Test
    void getListForRegisterTest() throws FacultyServiceException {
        List<Faculty> list;
        list = srv.getListForRegister(2);
        Assert.assertEquals(list.size(), 3);
        list = srv.getListForRegister(3);
        Assert.assertEquals(list.size(), 3);
    }

    @Test
    void getApplicantsForStatementTest() throws FacultyServiceException {
        List<Applicant> list;
        list = srv.getApplicantsForStatement(4);
        Assert.assertEquals(list.size(), 6);
    }

    @Test
    void addTest() throws FacultyServiceException {
      Assert.assertTrue(srv.add(new Faculty("FacultyTest", 2, 3, "Subj1", "Subj2")));
        Assert.assertThrows(FacultyServiceException.class,
                () -> srv.add(new Faculty("FacultyTest", 2, 3, "Subj1", "Subj2")));
    }


    @Test
    void getByNameTest() throws FacultyServiceException {
        Faculty faculty1 = srv.getByName(BIOLOGY);
        Faculty faculty2 = srv.getByName(RENAMED_FIELD);
        Faculty faculty3 = srv.getByName(ENGINEERING);
        Assert.assertEquals(faculty1.getId(), 1);
        Assert.assertEquals(faculty2.getId(), 2);
        Assert.assertEquals(faculty3.getId(), 3);
    }

    @Test
    void getByIdTest() throws FacultyServiceException {
        Faculty faculty1 = srv.getById(1);
        Faculty faculty2 = srv.getById(2);
        Faculty faculty3 = srv.getById(3);
        Assert.assertEquals(faculty1.getName(), BIOLOGY);
        Assert.assertEquals(faculty2.getName(), RENAMED_FIELD);
        Assert.assertEquals(faculty3.getName(), ENGINEERING);
    }

    @Test
    void checkByNameTest() throws FacultyServiceException {
        boolean ch1 = srv.checkByName(BIOLOGY);
        boolean ch2 = srv.checkByName("BI0L0GY");
        boolean ch3 = srv.checkByName(ENGINEERING);
        boolean ch4 = srv.checkByName("ENGINEEERING");
        Assert.assertTrue(ch1);
        Assert.assertTrue(ch3);
        Assert.assertFalse(ch2);
        Assert.assertFalse(ch4);
    }

    @Test
    void getListTest() throws FacultyServiceException {
        List<Faculty> list = srv.getList();
        Assert.assertEquals(list.size(), 6);
    }
}