package com.splb.model.dao.implementation;

import com.splb.model.dao.FacultyDAO;
import com.splb.model.dao.exception.FacultyDAOException;
import com.splb.model.entity.Faculty;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.testng.Assert;

class FacultyDAOImplTest {

    FacultyDAO dao = FacultyDAOImpl.getInstance();
    public static final String NAME_ONE = "TestFaculty1";
    public static final String NAME_TWO = "TestFaculty2";
    public static final String SUBJ_ONE = "TestSubject1";
    public static final String SUBJ_TWO = "TestSubject2";

    @BeforeAll
    static void setUp() throws Exception {
        DBInit.startUp();
    }

    @AfterAll
    static void tearDown() throws FacultyDAOException {
        FacultyDAO dao = FacultyDAOImpl.getInstance();
        dao.deleteFacultyByID(dao.getFacultyByName(NAME_ONE).getId());
        dao.deleteFacultyByID(dao.getFacultyByName(NAME_TWO).getId());
    }

    @Test
    void addFacultyTest() throws FacultyDAOException {
        Faculty faculty = new Faculty(NAME_ONE, 3, 6, SUBJ_ONE, SUBJ_TWO);
        Assert.assertTrue(dao.addFaculty(faculty));
        Assert.assertThrows(FacultyDAOException.class,
                () -> {
                    dao.addFaculty(faculty);
                });
    }

    @Test
    void deleteFacultyByID() throws FacultyDAOException {
        int id = dao.getFacultyByName(NAME_ONE).getId();
        Assert.assertTrue(dao.deleteFacultyByID(id));
        Assert.assertFalse(dao.deleteFacultyByID(id));
    }

    @Test
    void checkFacultyByName() throws FacultyDAOException {
        Faculty faculty = new Faculty(NAME_TWO, 3, 6, SUBJ_ONE, SUBJ_TWO);
        dao.addFaculty(faculty);
        Assert.assertTrue(dao.checkFacultyByName(NAME_ONE));
        Assert.assertFalse(dao.checkFacultyByName(SUBJ_ONE));
    }

    @Test
    void checkFacultyById() throws FacultyDAOException {
        int idTrue = dao.getFacultyByName(NAME_TWO).getId();
        int idFalse = 333;
        Assert.assertTrue(dao.checkFacultyById(idTrue));
        Assert.assertFalse(dao.checkFacultyById(idFalse));
    }

    @Test
    void getFacultyList() {
    }

    @Test
    void getApplicantsForFaculty() {
    }

    @Test
    void getFacultyById() {
    }

    @Test
    void getFacultyByName() {
    }

    @Test
    void updateFaculty() {
    }

    @Test
    void getSum() {
    }
}