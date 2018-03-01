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
    public ExpectedException thrown = ExpectedException.none();

    private Dictionary defaultDictionary = Dictionary.getDefault();

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
}