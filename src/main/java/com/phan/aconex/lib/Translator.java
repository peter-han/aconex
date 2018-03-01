package com.phan.aconex.lib;

import com.phan.aconex.utils.StringUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeSet;
import java.util.logging.Logger;
import java.util.stream.Collectors;

/**
 *
 */
public class Translator {

    private final Dictionary dictionary;

    public Translator(Dictionary dictionary) {
        this.dictionary = dictionary;
    }

    /**
     * translate from phone number file.
     *
     * @param filePath
     * @return
     * @throws FileNotFoundException
     */
    public Set<String> fromFile(String filePath) throws FileNotFoundException {
        InputStream dict = new FileInputStream(new File(filePath));

        TreeSet<String> result = new TreeSet<>();

        Scanner scanner = new Scanner(dict);
        while (scanner.hasNext()) {
            String phone = scanner.nextLine();
            result.addAll(fromPhoneNumber(phone));
        }

        return result;
    }

    /**
     * translate single phone number.
     *
     * @param phone
     * @return
     */
    public Set<String> fromPhoneNumber(String phone) throws IllegalArgumentException {
        /*
        trim all punctuation and whitespace.
         */
        String purgedPhone = StringUtils.purge(phone);

        /*
        validate
         */
        if (!StringUtils.isNumeric(purgedPhone))
            throw new IllegalArgumentException("phone number must be numeric, could contains punctuation or whitespace");

        return new WordQuery(dictionary, purgedPhone).getMatches();
    }
}
