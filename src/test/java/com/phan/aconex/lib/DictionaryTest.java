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
public class DictionaryTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void testCustomizedDictionary() throws FileNotFoundException {
        String path = getClass().getResource("dict_valid.txt").getPath();
        Dictionary dict = new Dictionary(path);

        Assert.assertNotNull(dict);
        Assert.assertTrue(dict.search(Long.valueOf(7426)).contains("PHAN"));
    }

    @Test
    public void testCustomizedDictionaryNotFound() throws FileNotFoundException {
        // expect
        thrown.expect(FileNotFoundException.class);

        String path = "no such file path";
        new Dictionary(path);
    }

    @Test
    public void testCustomizedDictionaryEmpty() throws FileNotFoundException {
        // expect
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("dictionary is empty");

        String path = getClass().getResource("dict_invalid_empty.txt").getPath();
        new Dictionary(path);
    }

    @Test
    public void testDefaultDictionary() {
        Assert.assertNotNull(Dictionary.getDefault());
    }

    @Test
    public void encodingChar() throws FileNotFoundException {
        // expect
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("unknown character");

        String path = getClass().getResource("dict_invalid.txt").getPath();
        new Dictionary(path);
    }

    @Test
    public void search() {
        Dictionary dictionary = Dictionary.getDefault();
        Set<String> words = dictionary.search(Long.valueOf(63));

        Assert.assertTrue(words.contains("ME"));
    }
}