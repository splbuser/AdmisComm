package com.splb.model.dao.implementation;

import com.splb.model.dao.FacultyDAO;
import com.splb.model.dao.exception.FacultyDAOException;
import com.splb.model.entity.Applicant;
import com.splb.model.entity.Faculty;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.testng.Assert;

import java.util.List;

class FacultyDAOImplTest {


    private static final Logger log = LogManager.getLogger(FacultyDAOImplTest.class);
    FacultyDAO dao = FacultyDAOImpl.getInstance();
    public static final String NAME_ONE = "TestFaculty1";
    public static final String NAME_TWO = "TestFaculty2";
    public static final String NAME_THREE = "TestFaculty3";
    public static final String NAME_FOUR = "TestFaculty4";
    public static final String NAME_FIVE = "TestFaculty5";
    public static final String SUBJ_ONE = "TestSubject1";
    public static final String SUBJ_TWO = "TestSubject2";
    public static final String RENAMED = "RenamedField";
    public static final String BIOLOGY = "Biology";
    public static final String ENGINEERING = "Engineering";

    @BeforeAll
    static void setUp() throws Exception {
        DBInit.startUp(FacultyDAOImplTest.class);
    }

    @AfterAll
    static void tearDown() throws FacultyDAOException {
        FacultyDAO dao = FacultyDAOImpl.getInstance();
        dao.deleteFacultyByID(dao.getFacultyByName(NAME_ONE).getId());
        dao.deleteFacultyByID(dao.getFacultyByName(NAME_TWO).getId());
        dao.deleteFacultyByID(dao.getFacultyByName(NAME_THREE).getId());
        dao.deleteFacultyByID(dao.getFacultyByName(NAME_FIVE).getId());
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
        Faculty faculty = new Faculty(NAME_FOUR, 3, 6, SUBJ_ONE, SUBJ_TWO);
        dao.addFaculty(faculty);
        int id = dao.getFacultyByName(NAME_FOUR).getId();
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
        Faculty faculty = new Faculty(NAME_THREE, 3, 6, SUBJ_ONE, SUBJ_TWO);
        dao.addFaculty(faculty);
        int idTrue = dao.getFacultyByName(NAME_THREE).getId();
        int idFalse = 333;
        Assert.assertTrue(dao.checkFacultyById(idTrue));
        Assert.assertFalse(dao.checkFacultyById(idFalse));
    }

    @Test
    void getFacultyList() throws FacultyDAOException {
        Assert.assertEquals(dao.getFacultyList().size(), 6);
        Faculty faculty = new Faculty(NAME_FOUR, 3, 6, SUBJ_ONE, SUBJ_TWO);
        dao.addFaculty(faculty);
        Assert.assertEquals(dao.getFacultyList().size(), 7);
        int id = dao.getFacultyByName(NAME_FOUR).getId();
        dao.deleteFacultyByID(id);
        Assert.assertEquals(dao.getFacultyList().size(), 6);
    }

    @Test
    void getApplicantsForFaculty() throws FacultyDAOException {
        List<Applicant> list1 = dao.getApplicantsForFaculty(4);
        List<Applicant> list2 = dao.getApplicantsForFaculty(5);
        Assert.assertEquals(list1.size(), 8);
        Assert.assertEquals(list2.size(), 6);

    }

    @Test
    void getFacultyById() throws FacultyDAOException {
        Assert.assertEquals(dao.getFacultyById(1).getName(), BIOLOGY);
        Assert.assertEquals(dao.getFacultyById(3).getName(), ENGINEERING);
    }

    @Test
    void getFacultyByName() throws FacultyDAOException {
        Assert.assertEquals(dao.getFacultyByName(BIOLOGY).getName(), BIOLOGY);
        Assert.assertEquals(dao.getFacultyByName(ENGINEERING).getName(), ENGINEERING);
        Faculty faculty = new Faculty(NAME_FIVE, 3, 6, SUBJ_ONE, SUBJ_TWO);
        Assert.assertTrue(dao.addFaculty(faculty));
        Assert.assertEquals(dao.getFacultyByName(NAME_FIVE).getName(), NAME_FIVE);
    }

    @Test
    void updateFaculty() throws FacultyDAOException {
        Faculty faculty = dao.getFacultyById(2);
        faculty.setName(RENAMED);
        faculty.setSubjOne(RENAMED);
        dao.updateFaculty(faculty);
        Assert.assertEquals(dao.getFacultyByName(RENAMED).getName(), RENAMED);
        Assert.assertEquals(dao.getFacultyByName(RENAMED).getSubjOne(), RENAMED);
    }

    @Test
    void getSum() throws FacultyDAOException {
        Assert.assertEquals(dao.getSum(2, 2), 19);
        Assert.assertEquals(dao.getSum(4, 4), 23);
    }
}