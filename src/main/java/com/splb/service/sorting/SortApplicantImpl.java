package com.splb.service.sorting;

import com.splb.model.entity.Applicant;

import java.util.Comparator;
import java.util.List;

public class SortApplicantImpl implements Sort<Applicant> {

    @Override
    public List<Applicant> getSortedList(String type, String sortBy, List<Applicant> list) {
        switch (sortBy) {
            case "byLastName":
                if (type.equals("ASC")) {
                    list.sort(Comparator.comparing(Applicant::getLastName));
                } else {
                    list.sort(Comparator.comparing(Applicant::getLastName).reversed());
                }
                break;
            case "byCity":
                if (type.equals("ASC")) {
                    list.sort(Comparator.comparing(Applicant::getCity));
                } else {
                    list.sort(Comparator.comparing(Applicant::getCity).reversed());
                }
                break;
            case "byRegion":
                if (type.equals("ASC")) {
                    list.sort(Comparator.comparing(Applicant::getRegion));
                } else {
                    list.sort(Comparator.comparing(Applicant::getRegion).reversed());
                }
                break;
            case "byEdIn":
                if (type.equals("ASC")) {
                    list.sort(Comparator.comparing(Applicant::getEducationalInstitution));
                } else {
                    list.sort(Comparator.comparing(Applicant::getEducationalInstitution).reversed());
                }
                break;
            default:
                return list;
        }
        return list;
    }
}
