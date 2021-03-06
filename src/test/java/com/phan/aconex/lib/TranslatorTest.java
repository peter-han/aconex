package com.phan.aconex.lib;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.io.FileNotFoundException;
import java.util.Set;

@RunWith(JUnit4.class)
public class TranslatorTest {

    @Rule
    public final ExpectedException thrown = ExpectedException.none();

    private final Dictionary defaultDictionary = Dictionary.getDefault();

    public TranslatorTest() {
    }

    @Test
    public void testInvalidPhoneEmpty() {
        // expect
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("phone number must be numeric, could contains punctuation or whitespace");

        new Translator(defaultDictionary).fromPhoneNumber(null);
    }

    @Test
    public void testInvalidPhoneNoDigits() {
        // expect
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("phone number must be numeric, could contains punctuation or whitespace");

        new Translator(defaultDictionary).fromPhoneNumber("abc");
    }

    @Test
    public void testFromPhoneNumber() {
        Set<String> words = new Translator(defaultDictionary).fromPhoneNumber("3569377");
        Assert.assertTrue(words.contains("FLOWERS"));
    }

    @Test
    public void testFromPhoneNumberConsecutiveDigits() {
        /*
        Ref: WordQueryTest.testMatchFuzzyEndWithDigit
         */
        Set<String> words = new Translator(getTestDictionary()).fromPhoneNumber("467730");
        Assert.assertEquals("There should be 1 match for the number provided", 1, words.size());
        Assert.assertTrue("Invalid matching number", words.contains("HORSE-0"));
    }

    @Test
    public void testFromPhoneNumberPunctuation() {
        Set<String> words = new Translator(defaultDictionary).fromPhoneNumber("2255.63");
        Assert.assertTrue(words.contains("CALL-ME"));
    }

    @Test
    public void testFromFile() throws FileNotFoundException {
        String filePath = getClass().getResource("test_input_phone_numbers.txt").getPath();

        Set<String> words = new Translator(defaultDictionary).fromFile(filePath);
        Assert.assertTrue(words.contains("CALL-ME"));
    }

    @Test
    public void testFromFileNotFound() throws FileNotFoundException {
        // expect
        thrown.expect(FileNotFoundException.class);

        new Translator(defaultDictionary).fromFile("invalid file path");
    }

    private Dictionary getTestDictionary() {
        try {
            return new Dictionary(getClass().getResource("dict_valid.txt").getPath());
        } catch (FileNotFoundException e) {
            // impossible, for test cases
        }

        return null;
    }
}