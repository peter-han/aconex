package com.phan.aconex.lib;

import com.phan.aconex.utils.StringUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.*;
import java.util.logging.Logger;

public class Dictionary {

    private static final Logger LOGGER = Logger.getLogger(Dictionary.class.getName());
    private static final String DEFAULT_DICTIONARY_PATH = "google-10000-english.txt";

    private static Dictionary defaultInstance;

    private Map<Long, Set<String>> index = new HashMap<>();

    /**
     * using system default dict.
     */
    private Dictionary() {
        InputStream dict = Dictionary.class.getResourceAsStream(DEFAULT_DICTIONARY_PATH);
        loadDictionary(dict);
    }

    /**
     * using customer dict
     *
     * @param dictPath
     * @throws FileNotFoundException
     */
    public Dictionary(String dictPath) throws FileNotFoundException {
        InputStream dict = new FileInputStream(new File(dictPath));
        loadDictionary(dict);
    }

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
            for (char c : StringUtils.purge(word).toCharArray()) {
                stringBuilder.append(encoding(c));
            }

            /*
            index
             */
            Long key = Long.parseLong(stringBuilder.toString());
            Set<String> words = index.get(key);
            if (null == words) {
                words = new HashSet<>();
                index.put(key, words);
            }
            words.add(StringUtils.purge(word));
        }

        LOGGER.fine("loading dictionary END");

        if (index.size() == 0)
            throw new IllegalArgumentException("dictionary is empty");
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
     * @param key
     * @return
     */
    public Set<String> search(Long key) {
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
