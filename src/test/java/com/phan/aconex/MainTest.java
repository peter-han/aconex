/*
 * Copyright (c) 2018. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.phan.aconex;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.contrib.java.lang.system.ExpectedSystemExit;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.io.ByteArrayInputStream;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

@RunWith(JUnit4.class)
public class MainTest {

    @Rule
    public final ExpectedSystemExit exit = ExpectedSystemExit.none();

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Before
    public void before() {
        System.clearProperty(Main.SYS_PROP_DICTIONARY_OVERRIDE);
    }

    @Test
    public void testValidPhoneInput() {
        exit.expectSystemExit();

        ByteArrayInputStream in = new ByteArrayInputStream("2255.65".getBytes());
        System.setIn(in);

        Main.main(null);
    }

    @Test
    public void testInputFile() {
        exit.expectSystemExit();

        String phoneNumberFilePath = getClass().getResource("test_input_phone_numbers.txt").getPath();
        Main.main(new String[]{phoneNumberFilePath});
    }

    @Test
    public void testInputFileNotFound() {
        // expect
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("invalid phone number path");

        Main.main(new String[]{"no such file"});
    }

    @Test
    public void testInputDictFileNotFound() {
        // expect
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("invalid dictionary path");

        System.setProperty(Main.SYS_PROP_DICTIONARY_OVERRIDE, "test invalid dictionary path");
        Main.getDictionary();
    }

    @Test
    public void testInputDictFile() {
        String path = getClass().getResource("test_input_dict.txt").getPath();
        System.setProperty(Main.SYS_PROP_DICTIONARY_OVERRIDE, path);

        Assert.assertNotNull(Main.getDictionary());
    }

    @Test
    public void testOutput() {
        Assert.assertTrue(Main.report(null));

        Set<String> data = new HashSet<>(Arrays.asList("a", "b"));
        Assert.assertTrue(Main.report(data));
    }
}