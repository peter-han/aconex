package com.phan.aconex.lib;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.Set;

@RunWith(JUnit4.class)
public class TranslatorTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void testInvalidPhoneEmpty() {
        // expect
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("phone number cannot be null");

        Translator.getInstance().numberToWord("", Dictionary.getDefault());
    }

    @Test
    public void testInvalidPhoneNoDigits() {
        // expect
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("phone number cannot parse to digits");

        Translator.getInstance().numberToWord("abc", Dictionary.getDefault());
    }

    @Test
    public void testNumberToWord() {
        Set<String> words = Translator.getInstance().numberToWord("2255.63", Dictionary.getDefault());
        Assert.assertTrue(words.contains("CALL-ME"));
    }

    @Test
    public void testNumberToWordWordsNotFound() {
        // expect
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("cannot find words for: 999999");

        Translator.getInstance().numberToWord("999999", Dictionary.getDefault());
    }
}