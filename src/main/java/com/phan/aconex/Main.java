package com.phan.aconex;

import com.phan.aconex.lib.Dictionary;
import com.phan.aconex.lib.Translator;
import com.phan.aconex.utils.CollectionUtils;
import com.phan.aconex.utils.StringUtils;

import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Scanner;
import java.util.Set;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class Main {

    public static final String SYS_PROP_DICTIONARY_OVERRIDE = "dictionary.file";
    private static final Logger LOGGER = Logger.getLogger(Main.class.getName());

    private static final String outputPrefix = "1-800-";

    public static void main(String[] args) {

        /*
        1. specified dictionary?
         */
        final Dictionary dict = getDictionary();

        /*
        2. input phone number or load from file?
         */
        Translator translator = new Translator(dict);
        Set<String> translated = null;
        if (args == null || args.length == 0) {
            /*
            waiting input phone number
             */
            Scanner scan = new Scanner(System.in);
            LOGGER.info("Enter your phone number: ");
            String input = scan.next();

            translated = translator.fromPhoneNumber(input);

        } else {
            LOGGER.info("Files to process: " + Arrays.toString(args));
            for (String arg : args) {
                try {
                    translated = translator.fromFile(arg);
                } catch (FileNotFoundException e) {
                    throw new IllegalArgumentException("invalid phone number path");
                }
            }
        }

        /*
        report
         */
        report(translated);

        System.exit(0);
    }

    static Dictionary getDictionary() {
        /*
        1. specified dictionary?
         */
        final Dictionary dict;
        String dictionaryPath = System.getProperty(SYS_PROP_DICTIONARY_OVERRIDE);

        if (!StringUtils.isEmpty(dictionaryPath)) {
            try {
                dict = new Dictionary(dictionaryPath);
            } catch (FileNotFoundException e) {
                throw new IllegalArgumentException("invalid dictionary path");
            }
        } else
            dict = Dictionary.getDefault();

        return dict;
    }

    /**
     * @param result
     */
    static boolean report(Set<String> result) {
        if (CollectionUtils.isEmptyCollection(result))
            LOGGER.info("no matches");
        else
            /*
            append 1-800 in front?
             */
            LOGGER.info(
                    // one possible word encoding per line
                    String.join("\n",
                            result.stream()
                                    .map(s -> outputPrefix + s)
                                    .sorted()
                                    .collect(Collectors.toList())));

        return true; // succeed
    }

}
