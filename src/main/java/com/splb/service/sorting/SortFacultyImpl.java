package com.splb.service.sorting;

import com.splb.model.entity.Faculty;

import java.util.Comparator;
import java.util.List;

public class SortFacultyImpl implements Sort<Faculty> {

    @Override
    public List<Faculty> getSortedList(String type, String sortBy, List<Faculty> list) {
        switch (sortBy) {
            case "byName":
                if (type.equals("ASC")) {
                    list.sort(Comparator.comparing(Faculty::getName));
                } else {
                    list.sort(Comparator.comparing(Faculty::getName).reversed());
                }
                break;
            case "byBudget":
                if (type.equals("ASC")) {
                    list.sort(Comparator.comparing(Faculty::getBudgetPlaces));
                } else {
                    list.sort(Comparator.comparing(Faculty::getBudgetPlaces).reversed());
                }
                break;
            case "byTotal":
                if (type.equals("ASC")) {
                    list.sort(Comparator.comparing(Faculty::getTotalPlaces));
                } else {
                    list.sort(Comparator.comparing(Faculty::getTotalPlaces).reversed());
                }
                break;
            default:
                return list;
        }
        return list;
    }
}