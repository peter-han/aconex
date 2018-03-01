package com.phan.aconex.utils;

import java.util.Collection;

public class CollectionUtils {

    private CollectionUtils() {
    }

    public static boolean isEmptyCollection(Collection collection) {
        return (null == collection || collection.isEmpty());
    }
}
