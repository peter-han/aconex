package com.phan.aconex.utils;

import org.junit.Assert;
import org.junit.Test;

import java.util.Collections;

public class CollectionUtilsTest {

    @Test
    public void isEmptyCollection() {
        Assert.assertTrue(CollectionUtils.isEmptyCollection(null));
        Assert.assertTrue(CollectionUtils.isEmptyCollection(Collections.EMPTY_LIST));
    }
}