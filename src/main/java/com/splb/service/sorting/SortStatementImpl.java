package com.splb.service.sorting;

import com.splb.model.entity.Statement;

import java.util.Comparator;
import java.util.List;

public class SortStatementImpl implements Sort<Statement> {

    @Override
    public List<Statement> getSortedList(String type, String sortBy, List<Statement> list) {
        switch (sortBy) {
            case "byLastName":
                Comparator<Statement> byLastName =
                        Comparator.comparing((Statement s) -> s.getApplicant().getLastName());
                if (type.equals("ASC")) {
                    list.sort(byLastName);
                } else {
                    list.sort(byLastName.reversed());
                }
                break;
            case "byFaculty":
                Comparator<Statement> byFaculty =
                        Comparator.comparing((Statement s) -> s.getFaculty().getName())
                                .thenComparing(Statement::getTotalScore).reversed();
                if (type.equals("ASC")) {
                    list.sort(byFaculty);
                } else {
                    list.sort(byFaculty.reversed());
                }
                break;
            case "byTotalScore":
                Comparator<Statement> byTotalScore =
                        Comparator.comparing(Statement::getTotalScore);
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
