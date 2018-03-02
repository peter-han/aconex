package com.phan.aconex.utils;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.stream.Collectors.joining;

public class StringUtils {

    private StringUtils() {
    }

    /**
     * @param value
     * @return
     */
    public static boolean isEmpty(final String value) {
        return (value == null || value.isEmpty() || value.trim().isEmpty());
    }

    /**
     * All punctuation and whitespace should be ignored in both phone numbers and the dictionary file.
     * The program should not be case sensitive, letting "a" == "A".
     *
     * @param source
     * @return
     */
    public static String purge(String source) {
        return isEmpty(source) ? null : source.replaceAll("\\p{P}", "").toUpperCase();
    }

    /**
     * @param str
     * @return
     */
    public static boolean isNumeric(String str) {
        return !isEmpty(str) && str.matches("\\d+(\\.\\d+)?");
    }

    /**
     * @param s
     * @return
     */
    public static boolean isTwoConsecutiveDigits(String s) {
        return s.matches(".*\\d-\\d.*");
    }

    /**
     * @param firstMatches
     * @param endMatches
     * @param currentDigit
     * @return
     */
    public static Set<String> joinWords(Set<String> firstMatches, Set<String> endMatches, String currentDigit) {

        if (CollectionUtils.isEmptyCollection(firstMatches) && CollectionUtils.isEmptyCollection(endMatches))
            throw new IllegalArgumentException("both first collection and end collection are empty");

        Set<String> join;

        /*
        digit in the beginning
         */
        if (CollectionUtils.isEmptyCollection(firstMatches))
            join = endMatches.stream()
                    .map(s -> joinStrings(new String[]{currentDigit, s}))
                    .collect(Collectors.toSet());
        /*
        digit in the end
         */
        else if (CollectionUtils.isEmptyCollection(endMatches))
            join = firstMatches.stream()
                    .map(s -> joinStrings(new String[]{s, currentDigit}))
                    .sorted(String::compareTo)
                    .collect(Collectors.toSet());
        /*
        digit in middle
         */
        else {
            join = new HashSet<>();

            for (String firstMatch : firstMatches) {
                for (String endMatch : endMatches) {
                    join.add(joinStrings(firstMatch, currentDigit, endMatch));
                }
            }
        }

        return join;
    }

    private static String joinStrings(String... strings) {
        return Stream.of(strings)
                .filter(s -> s != null && !s.isEmpty())
                .collect(joining("-"));
    }
}