package com.splb.service.sorting;

import com.splb.model.dao.constant.Fields;
import com.splb.model.entity.Applicant;

import java.util.Comparator;
import java.util.List;

/**
 * for sorting Applicant list by some category
 */
public class SortApplicantImpl implements Sort<Applicant> {

    private static final String LAST_NAME = "byLastName";
    private static final String CITY = "byCity";
    private static final String REGION = "byRegion";
    private static final String STATUS = "byStatus";

    @Override
    public List<Applicant> getSortedList(String type, String sortBy, List<Applicant> list) {
        switch (sortBy) {
            case LAST_NAME:
                if (type.equals(Fields.ASC)) {
                    list.sort(Comparator.comparing(Applicant::getLastName));
                } else {
                    list.sort(Comparator.comparing(Applicant::getLastName).reversed());
                }
                break;
            case CITY:
                if (type.equals(Fields.ASC)) {
                    list.sort(Comparator.comparing(Applicant::getCity));
                } else {
                    list.sort(Comparator.comparing(Applicant::getCity).reversed());
                }
                break;
            case REGION:
                if (type.equals(Fields.ASC)) {
                    list.sort(Comparator.comparing(Applicant::getRegion));
                } else {
                    list.sort(Comparator.comparing(Applicant::getRegion).reversed());
                }
                break;
            case STATUS:
                if (type.equals(Fields.ASC)) {
                    list.sort(Comparator.comparing(Applicant::getEnrollStatus));
                } else {
                    list.sort(Comparator.comparing(Applicant::getEnrollStatus).reversed());
                }
                break;
            default:
                return list;
        }
        return list;
    }
}