package com.splb.service.sorting;

import com.splb.model.dao.constant.Fields;
import com.splb.model.entity.Faculty;

import java.util.Comparator;
import java.util.List;

/**
 * for sorting Faculty list by some category
 */
public class SortFacultyImpl implements Sort<Faculty> {

    public static final String NAME = "byName";
    public static final String BUDGET = "byBudget";
    public static final String TOTAL = "byTotal";

    @Override
    public List<Faculty> getSortedList(String type, String sortBy, List<Faculty> list) {
        switch (sortBy) {
            case NAME:
                if (type.equals(Fields.ASC)) {
                    list.sort(Comparator.comparing(Faculty::getName));
                } else {
                    list.sort(Comparator.comparing(Faculty::getName).reversed());
                }
                break;
            case BUDGET:
                if (type.equals(Fields.ASC)) {
                    list.sort(Comparator.comparing(Faculty::getBudgetPlaces)
                            .thenComparing(Faculty::getTotalPlaces));
                } else {
                    list.sort(Comparator.comparing(Faculty::getBudgetPlaces)
                            .thenComparing(Faculty::getTotalPlaces).reversed());
                }
                break;
            case TOTAL:
                if (type.equals(Fields.ASC)) {
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