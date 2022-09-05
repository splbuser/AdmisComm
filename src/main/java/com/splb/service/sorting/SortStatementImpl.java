package com.splb.service.sorting;

import com.splb.model.dao.constant.Fields;
import com.splb.model.entity.Statement;

import java.util.Comparator;
import java.util.List;

/**
 * for sorting Statement list by some category
 */
public class SortStatementImpl implements Sort<Statement> {

    public static final String LAST_NAME = "byLastName";
    public static final String FACULTY = "byFaculty";
    public static final String TOTAL_SCORE = "byTotalScore";

    @Override
    public List<Statement> getSortedList(String type, String sortBy, List<Statement> list) {
        switch (sortBy) {
            case LAST_NAME:
                Comparator<Statement> byLastName =
                        Comparator.comparing((Statement s) -> s.getApplicant().getLastName());
                if (type.equals(Fields.ASC)) {
                    list.sort(byLastName);
                } else {
                    list.sort(byLastName.reversed());
                }
                break;
            case FACULTY:
                Comparator<Statement> byFaculty =
                        Comparator.comparing((Statement s) -> s.getFaculty().getName())
                                .thenComparing(Statement::getTotalScore).reversed();
                if (type.equals(Fields.ASC)) {
                    list.sort(byFaculty);
                } else {
                    list.sort(byFaculty.reversed());
                }
                break;
            case TOTAL_SCORE:
                Comparator<Statement> byTotalScore =
                        Comparator.comparing(Statement::getTotalScore);
                if (type.equals(Fields.ASC)) {
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