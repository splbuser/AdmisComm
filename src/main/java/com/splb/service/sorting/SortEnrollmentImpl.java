package com.splb.service.sorting;

import com.splb.model.dao.constant.Fields;
import com.splb.model.entity.Enrollment;

import java.util.Comparator;
import java.util.List;

public class SortEnrollmentImpl implements Sort<Enrollment> {

    public static final String LAST_NAME = "byLastName";
    public static final String FACULTY = "byFaculty";
    public static final String STATUS = "byStatus";

    @Override
    public List<Enrollment> getSortedList(String type, String sortBy, List<Enrollment> list) {
        switch (sortBy) {
            case LAST_NAME:
                Comparator<Enrollment> byLastName = Comparator.comparing((Enrollment e) -> e.getApplicant().getLastName());
                if (type.equals(Fields.ASC)) {
                    list.sort(byLastName);
                } else {
                    list.sort(byLastName.reversed());
                }
                break;
            case FACULTY:
                Comparator<Enrollment> byFaculty = Comparator.comparing((Enrollment e) -> e.getFaculty().getName())
                        .thenComparing(Comparator.comparing(Enrollment::getStatus).reversed());
                if (type.equals(Fields.ASC)) {
                    list.sort(byFaculty);
                } else {
                    list.sort(byFaculty.reversed());
                }
                break;
            case STATUS:
                Comparator<Enrollment> byStatus = Comparator.comparing(Enrollment::getStatus);
                if (type.equals(Fields.ASC)) {
                    list.sort(byStatus);
                } else {
                    list.sort(byStatus.reversed());
                }
                break;
            default:
                return list;
        }
        return list;
    }
}