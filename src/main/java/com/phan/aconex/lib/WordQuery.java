package com.phan.aconex.lib;

import com.phan.aconex.utils.StringUtils;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class WordQuery {

    private Set<String> matches = new HashSet<>();
    private final Dictionary dict;

    public WordQuery(Dictionary dict, String phone) throws IllegalArgumentException {

        if (null == dict)
            throw new IllegalArgumentException("dictionary is null");
        if (StringUtils.isEmpty(phone))
            throw new IllegalArgumentException("phone is null");

        this.dict = dict;

        /*
         accurate matching, the whole words matches.
         */
        Set<String> found = dict.search(Long.valueOf(phone));


        if (null == found || found.isEmpty()) {

            /*
            if no match can be made, a single digit can be left as is at that point.
             */
            if (phone.length() == 1) {
                matches.add(phone);
                return;
            }

            /*
            fission if more than 2 digits; otherwise we should drop this branch.
             */
            if (phone.length() <= 2)
                throw new IllegalArgumentException("No two consecutive digits can remain unchanged");

            /*
            fuzzy matching, start from the first digit, cursor move 1 each time.
             */
            for (int i = 1; i < phone.length(); i++) {
                try {
                    /*
                     fission without digit
                     */
                    String firstString = phone.substring(0, i);
                    String endString = phone.substring(i, phone.length());

                    matches.addAll(fissionMatches(firstString, endString, null));
                } catch (IllegalArgumentException e) {
                    /*
                    fission with single digit
                     */
                    try {
                        String currentDigit = String.valueOf(phone.charAt(i));
                        String firstString = phone.substring(0, i);
                        String endString = phone.substring(i + 1, phone.length());

                        matches.addAll(fissionMatches(firstString, endString, currentDigit));
                    } catch (IllegalArgumentException e1) {
                        // both firstNode and endNode have to be valid, otherwise drop this branch and continue
                        continue;
                    }
                }
            }
        } else {
            matches.addAll(found);
        }
    }

    private Set<String> fissionMatches(String firstString, String endString, String currentDigit) {
        Set<String> firstNode = null;
        // if not the first char
        if (!StringUtils.isEmpty(firstString))
            firstNode = new WordQuery(dict, firstString).getMatches();

        Set<String> endNode = null;
        // if not the end char
        if (!StringUtils.isEmpty(endString))
            endNode = new WordQuery(dict, endString).getMatches();

        // push into children. first + currentDigit + end
        return StringUtils.joinWords(firstNode, endNode, currentDigit);
    }

    /**
     * @return
     */
    public Set<String> getMatches() {
        return matches;
    }

}
