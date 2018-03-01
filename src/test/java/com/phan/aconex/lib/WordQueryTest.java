package com.phan.aconex.lib;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.io.FileNotFoundException;
import java.util.Set;

public class WordQueryTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private Dictionary defaultDictionary = Dictionary.getDefault();

    @Test
    public void testNullDictionary() {
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("dictionary is null");

        new WordQuery(null, null);
    }

    @Test
    public void testNullPhone() {
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("phone is null");

        new WordQuery(defaultDictionary, null);
    }

    @Test
    public void testMatchAccurate() {
        Set<String> matches = new WordQuery(getTestDictionary(), "7426").getMatches();

        Assert.assertNotNull("Matches shouldn't be null", matches);
        Assert.assertEquals("There should be 1 match for the number provided", 1, matches.size());
        Assert.assertTrue("Invalid matching number", matches.contains("PHAN"));
    }

    @Test
    public void testFixMatchFuzzyStartWithDigitDiedLoop() {
        Set<String> matches = new WordQuery(getTestDictionary(), "8437").getMatches();

        Assert.assertNotNull("Matches shouldn't be null", matches);
        Assert.assertEquals("There should be 1 match for the number provided", 1, matches.size());
        Assert.assertTrue("Invalid matching number", matches.contains("THE-7"));
    }

    @Test
    public void testMatchFuzzyStartWithDigit() {
        Set<String> matches = new WordQuery(getTestDictionary(), "843728").getMatches();

        Assert.assertNotNull("Matches shouldn't be null", matches);
        Assert.assertEquals("There should be 1 match for the number provided", 2, matches.size());
        Assert.assertTrue("Invalid matching number", matches.contains("THE-RAT"));
    }

    @Test
    public void testMatchFuzzyEndWithDigit() {
        Set<String> matches = new WordQuery(getTestDictionary(), "467730").getMatches();

        Assert.assertNotNull("Matches shouldn't be null", matches);
        Assert.assertEquals("There should be 1 match for the number provided", 1, matches.size());
        Assert.assertTrue("Invalid matching number", matches.contains("HORSE-0"));
    }

    @Test
    public void testMatchFuzzyDigitInMiddle() {
        Set<String> matches = new WordQuery(getTestDictionary(), "462882").getMatches();

        Assert.assertNotNull("Matches shouldn't be null", matches);
        Assert.assertEquals("There should be 1 match for the number provided", 1, matches.size());
        Assert.assertTrue("Invalid matching number", matches.contains("GOAT-8-A"));
    }

    @Test
    public void testFissionFailedNoMatch2CharsLeft() {
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("No two consecutive digits can remain unchanged");

        new WordQuery(getTestDictionary(), "99");
    }

    /**
     * for testing, only a few words
     *
     * @return
     */
    private Dictionary getTestDictionary() {
        try {
            return new Dictionary(getClass().getResource("dict_valid.txt").getPath());
        } catch (FileNotFoundException e) {
            // impossible, for test cases
        }

        return null;
    }
}