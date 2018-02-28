package com.phan.aconex.lib;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Scanner;
import java.util.TreeSet;
import java.util.logging.Logger;

/**
 *
 */
public class Translator {

    private static final Logger LOGGER = Logger.getLogger(Translator.class.getName());
    private static final String DASH = "-";

    private static Translator instance;

    private Translator() {
    }

    /**
     * @return
     */
    public static Translator getInstance() {
        if (null == instance)
            instance = new Translator();
        return instance;
    }

    /**
     * @param path
     * @param dictionary
     * @return
     * @throws FileNotFoundException
     */
    public TreeSet<String> translateFile(String path, Dictionary dictionary) throws FileNotFoundException {
        InputStream dict = new FileInputStream(new File(path));

        TreeSet<String> result = new TreeSet<>();

        Scanner scanner = new Scanner(dict);
        while (scanner.hasNext()) {
            String phone = scanner.nextLine();

            result.addAll(numberToWord(phone, dictionary));
        }

        return result;
    }

    /**
     * @param phoneNumber
     * @param dictionary
     * @return
     */
    public TreeSet<String> numberToWord(String phoneNumber, Dictionary dictionary) {

        /*
        validate
         */
        if (null == phoneNumber || phoneNumber == "")
            throw new IllegalArgumentException("phone number cannot be null");

        String[] numbers = phoneNumber.split("\\D+");
        if (numbers.length == 0)
            throw new IllegalArgumentException("phone number cannot parse to digits");

        TreeSet<String> result = null;
        /*
        search dictionary
         */
        for (String number : numbers) {
            TreeSet<String> words = dictionary.search(Long.parseLong(number));

            if (null == words || words.isEmpty())
                throw new IllegalArgumentException("cannot find words for: " + number);

            /*
            the output would be the combination
             */
            result = joinWords(result, words);
        }

        /*
        output
         */
        output(result);

        return result;
    }

    /**
     * @param result
     */
    private void output(TreeSet<String> result) {
        /*
        TODO append 1800 in front
         */
        LOGGER.info(String.join("\n", result));
    }

    /**
     * @param current
     * @param newMatches
     * @return
     */
    private TreeSet<String> joinWords(TreeSet<String> current, TreeSet<String> newMatches) {

        /*
        the first matching, current hasn't be initialized.
         */
        if (null == current || current.isEmpty())
            return newMatches;

        /*
        join
         */
        TreeSet<String> newCombination = new TreeSet<>();

        for (String newMatch : newMatches) {
            for (String s : current) {
                newCombination.add(String.join(DASH, new String[]{s, newMatch}));
            }
        }

        return newCombination;
    }
}
