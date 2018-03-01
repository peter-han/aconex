package com.phan.aconex.utils;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.*;

@RunWith(JUnit4.class)
public class StringUtilsTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void isEmpty() {
        Assert.assertTrue(StringUtils.isEmpty(null));
        Assert.assertTrue(StringUtils.isEmpty(""));
    }

    @Test
    public void purge() {
        String email = "roc_han@163.com";
        Assert.assertEquals("ROCHAN163COM", StringUtils.purge(email));
    }

    @Test
    public void isNumeric() {
        Assert.assertFalse(StringUtils.isNumeric(null));
        Assert.assertTrue(StringUtils.isNumeric("12334"));
        Assert.assertFalse(StringUtils.isNumeric("12334AA"));
    }

    @Test
    public void joinWordsException() {
        // expect
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("both first collection and end collection are empty");

        StringUtils.joinWords(null, null, null);
    }

    @Test
    public void joinWords() {
        Set<String> firstSet = new HashSet<>(Arrays.asList("a", "b"));
        Set<String> secondSet = new HashSet<>(Arrays.asList("c", "d"));
        String currentDigit = "0";

        {
            Set<String> join = StringUtils.joinWords(null, secondSet, currentDigit);
            Assert.assertTrue(join.contains("0-c"));
            Assert.assertTrue(join.contains("0-d"));
        }

        {
            Set<String> join = StringUtils.joinWords(firstSet, null, currentDigit);
            Assert.assertTrue(join.contains("a-0"));
            Assert.assertTrue(join.contains("b-0"));
        }

        {
            Set<String> join = StringUtils.joinWords(firstSet, secondSet, currentDigit);
            Assert.assertTrue(join.contains("a-0-c"));
            Assert.assertTrue(join.contains("b-0-d"));
        }
    }
}