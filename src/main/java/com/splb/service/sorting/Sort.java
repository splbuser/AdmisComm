package com.splb.service.sorting;

import java.util.List;

public interface Sort<T> {

    default List<T> getSortedList(String type, String sortBy, List<T> list) {
        return list;
    }
}