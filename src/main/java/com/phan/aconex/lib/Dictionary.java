package com.phan.aconex.lib;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.*;
import java.util.logging.Logger;

public class Dictionary {

    private static final String DEFAULT_DICTIONARY_NAME = "google-10000-english.txt";
    private static final Logger LOGGER = Logger.getLogger(Dictionary.class.getName());

    private static Dictionary defaultInstance;

    private Map<Long, TreeSet<String>> index = new HashMap<>();

    /**
     * using customer dict
     *
     * @param dictPath
     * @throws FileNotFoundException
     */
    public Dictionary(String dictPath) throws FileNotFoundException {
        LOGGER.fine("using customer dictionary");
        InputStream dict = new FileInputStream(new File(dictPath));
        loadDictionary(dict);
    }

    /**
     * using system default dict.
     */
    private Dictionary() {
        LOGGER.fine("using default dictionary");
        InputStream dict = getClass().getResourceAsStream(DEFAULT_DICTIONARY_NAME);
        loadDictionary(dict);
    }

    /**
     * @return
     */
    public static Dictionary getDefault() {
        if (null == defaultInstance)
            defaultInstance = new Dictionary();

        return defaultInstance;
    }

    /**
     * @param dict
     */
    private void loadDictionary(InputStream dict) {

        LOGGER.fine("loading dictionary START");

        Scanner scanner = new Scanner(dict);
        while (scanner.hasNext()) {
            String word = scanner.nextLine();

            /*
            encode
             */
            StringBuilder stringBuilder = new StringBuilder();
            /*
            all caps & no spaces
             */
            for (char c : word.toUpperCase().trim().toCharArray()) {
                stringBuilder.append(encoding(c));
            }

            /*
            index
             */
            Long key = Long.parseLong(stringBuilder.toString());
            TreeSet<String> words = index.get(key);
            if (null == words) {
                words = new TreeSet<>();
                index.put(key, words);
            }
            words.add(word.toUpperCase());
        }

        LOGGER.fine("loading dictionary END");

        if (index.size() == 0)
            throw new IllegalArgumentException("dictionary is empty");
    }

    /**
     * @param key
     * @return
     */
    public TreeSet<String> search(Long key) {
        return index.get(key);
    }

    /**
     * @param c
     * @return
     */
    private int encoding(char c) {
        switch (c) {
            case 'A':
            case 'B':
            case 'C':
                return 2;
            case 'D':
            case 'E':
            case 'F':
                return 3;
            case 'G':
            case 'H':
            case 'I':
                return 4;
            case 'J':
            case 'K':
            case 'L':
                return 5;
            case 'M':
            case 'N':
            case 'O':
                return 6;
            case 'P':
            case 'Q':
            case 'R':
            case 'S':
                return 7;
            case 'T':
            case 'U':
            case 'V':
                return 8;
            case 'W':
            case 'X':
            case 'Y':
            case 'Z':
                return 9;
            default:
                throw new IllegalArgumentException("unknown character");
        }
    }
}
