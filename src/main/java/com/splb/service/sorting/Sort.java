package com.splb.service.sorting;

import java.util.List;

/**
 * common interface for sort classes
 * @param <T>
 */
public interface Sort<T> {

    default List<T> getSortedList(String type, String sortBy, List<T> list) {
        return list;
    }
}