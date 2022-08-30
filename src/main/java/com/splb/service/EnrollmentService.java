package com.splb.service;

import com.splb.model.dao.exception.EnrollmentDAOException;
import com.splb.model.entity.Applicant;
import com.splb.model.entity.EnrollStatus;
import com.splb.model.entity.Enrollment;
import com.splb.model.entity.Faculty;
import com.splb.service.exceptions.EnrollmentServiceException;

import java.util.*;

public class EnrollmentService extends Service {


    public static EnrollStatus getStatus(int i) {
        if (i == 0) {
            return EnrollStatus.NO_ENROLLED;
        }
        return i == 1 ?
                EnrollStatus.CONTRACT : EnrollStatus.BUDGET;
    }

    public List<Enrollment> getList() throws EnrollmentServiceException {
        try {
            return edao.getEnrollment();
        } catch (EnrollmentDAOException e) {
            log.error(e.getMessage());
            throw new EnrollmentServiceException(e.getMessage());
        }
    }

    public Enrollment get(int id) throws EnrollmentServiceException{
        try {
            return edao.getApplicantEnrollStatus(id);
        } catch (EnrollmentDAOException e) {
            log.error(e.getMessage());
            throw new EnrollmentServiceException(e.getMessage());
        }
    }

    List<Integer> calculateEnrolledApplicantsId(Map<Faculty, TreeSet<Applicant>> applicants) {
        List<Integer> enrolledIdList = new ArrayList<>();
        Set<Faculty> faculties = applicants.keySet();
        int count;

        for (Faculty faculty : faculties) {
            count = 0;

            Set<Applicant> currentFacultyApplicants = applicants.get(faculty);
            Iterator<Applicant> iterator = currentFacultyApplicants.iterator();

            while (iterator.hasNext()) {
                enrolledIdList.add(iterator.next().getId());
                if (++count == faculty.getTotalPlaces()) {
                    break;
                }
            }
        }
        return enrolledIdList;
    }
}
