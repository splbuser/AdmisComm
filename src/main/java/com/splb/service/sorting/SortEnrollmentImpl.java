package com.splb.service.sorting;

import com.splb.model.entity.Enrollment;
import com.splb.model.entity.Statement;

import java.util.Comparator;
import java.util.List;

public class SortEnrollmentImpl implements Sort<Enrollment> {
    @Override
    public List<Enrollment> getSortedList(String type, String sortBy, List<Enrollment> list) {
        switch (sortBy) {
            case "byLastName":
                Comparator<Enrollment> byLastName = Comparator.comparing((Enrollment s) -> s.getApplicant().getLastName());
                if (type.equals("ASC")) {
                    list.sort(byLastName);
                } else {
                    list.sort(byLastName.reversed());
                }
                break;
            case "byFaculty":
                Comparator<Enrollment> byFaculty = Comparator.comparing((Enrollment s) -> s.getFaculty().getName());
                if (type.equals("ASC")) {
                    list.sort(byFaculty);
                } else {
                    list.sort(byFaculty.reversed());
                }
                break;
            case "byStatus":
                Comparator<Enrollment> byTotalScore = Comparator.comparing(Enrollment::getStatus);
                if (type.equals("ASC")) {
                    list.sort(byTotalScore);
                } else {
                    list.sort(byTotalScore.reversed());
                }
                break;
            default:
                return list;
        }
        return list;
    }
}
