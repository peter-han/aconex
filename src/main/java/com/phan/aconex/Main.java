package com.phan.aconex;

import com.phan.aconex.lib.Dictionary;
import com.phan.aconex.lib.Translator;

import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Scanner;
import java.util.logging.Logger;

public class Main {

    public static final String SYS_PROP_DICTIONARY_OVERRIDE = "dictionary.file";
    private static final Logger LOGGER = Logger.getLogger(Main.class.getName());

    public static void main(String[] args) {

//        /*
//        1. init dictionary
//         */
//        final Dictionary dict;
//        String dictionaryPath = System.getProperty(SYS_PROP_DICTIONARY_OVERRIDE);
//
//        if (null != dictionaryPath && dictionaryPath != "") {
//            try {
//                dict = new Dictionary(dictionaryPath);
//            } catch (FileNotFoundException e) {
//                throw new IllegalArgumentException("invalid dictionary path");
//            }
//        } else
//            dict = Dictionary.getDefault();
//
//        /*
//        2. input phone number or load from file?
//         */
//        if (args == null || args.length == 0) {
//            /*
//            waiting input phone number
//             */
//            Scanner scan = new Scanner(System.in);
//            LOGGER.info("Enter your phone number: ");
//            String input = scan.next();
//
//            Translator.getInstance().numberToWord(input, dict);
//
//        } else {
//            LOGGER.info("Files to process: " + Arrays.toString(args));
//            for (String arg : args) {
//                try {
//                    Translator.getInstance().fromFile(arg, dict);
//                } catch (FileNotFoundException e) {
//                    throw new IllegalArgumentException("invalid phone number path");
//                }
//            }
//        }

        System.exit(0);
    }

}
