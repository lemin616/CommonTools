package com.lemin.commontools.helper;

import java.util.Iterator;
import java.util.List;

public class CollectionsHelper {
    public static <T> List<T> filterNull(List<T> list) {
        Iterator<T> iterator = list.iterator();
        while (iterator.hasNext()) {
            T next = iterator.next();
            if (next == null)
                iterator.remove();
        }
        return list;
    }
}
