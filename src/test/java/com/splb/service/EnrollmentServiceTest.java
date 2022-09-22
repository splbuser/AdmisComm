package com.splb.service;

import com.splb.model.dao.connection.DirectConnectionBuilder;
import com.splb.model.entity.EnrollStatus;
import com.splb.model.entity.Enrollment;
import com.splb.service.exceptions.EnrollmentServiceException;
import com.splb.service.exceptions.StatementServiceException;
import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testng.Assert;

import java.util.List;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class EnrollmentServiceTest {

    static EnrollmentService srv;

    @BeforeEach
    void setUp() {
        srv = new EnrollmentService(new DirectConnectionBuilder());
        StatementService service = new StatementService(new DirectConnectionBuilder());
        try {
            service.finalizeStatement();
        } catch (StatementServiceException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void getTest() throws EnrollmentServiceException {
        Enrollment enrollment1 = srv.get(1);
        Enrollment enrollment2 = srv.get(2);
        Enrollment enrollment3 = srv.get(3);
        String faculty1 = enrollment1.getFaculty().getName();
        String faculty2 = enrollment2.getFaculty().getName();
        String faculty3 = enrollment3.getFaculty().getName();
        Assert.assertEquals(faculty1, "Biology");
        Assert.assertEquals(faculty2, "RenamedField");
        Assert.assertEquals(faculty3, "Engineering");
    }

    @Test
    void getListTest() throws EnrollmentServiceException {
        List<Enrollment> list = srv.getList();
        Assert.assertEquals(list.size(), 5);
    }


    @Test
    void getEnrollmentsForRequestTest() throws EnrollmentServiceException {
        final HttpSession session = mock(HttpSession.class);
        List<Enrollment> enrollments = srv.getEnrollmentsForRequest(session, "ASC", "byStatus");
        List<Enrollment> enrollmentsNull = srv.getEnrollmentsForRequest(session, null, null);
        when(session.getAttribute("type")).thenReturn("DSC");
        when(session.getAttribute("sortBy")).thenReturn("byFaculty");
        String userName = enrollments.get(1).getApplicant().getUserName();
        String faculty = enrollments.get(1).getFaculty().getName();
        EnrollStatus status = enrollments.get(2).getStatus();
        Assert.assertEquals(enrollments.size(), 5);
        Assert.assertEquals(enrollmentsNull.size(), 5);
        Assert.assertEquals(userName, "999eddyy999");
        Assert.assertEquals(faculty, "Economics");
        Assert.assertEquals(status, EnrollStatus.CONTRACT);

    }

}