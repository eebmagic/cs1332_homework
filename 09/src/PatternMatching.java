import java.util.Comparator;
import java.util.List;
import java.util.Map;

import java.util.LinkedList;
import java.util.HashMap;

/**
 * Your implementations of various pattern matching algorithms.
 *
 * @author Ethan Bolton
 * @version 1.0
 * @userid ebolton8
 * @GTID 903368012
 *
 * Collaborators: None
 *
 * Resources: Google and all old Java homeworks
 */
public class PatternMatching {

    /**
     * Brute force pattern matching algorithm to find all matches.
     *
     * You should check each substring of the text from left to right,
     * stopping early if you find a mismatch and shifting down by 1.
     *
     * @param pattern    the pattern you are searching for in a body of text
     * @param text       the body of text where you search for pattern
     * @param comparator you MUST use this for checking character equality
     * @return list containing the starting index for each match found
     * @throws java.lang.IllegalArgumentException if the pattern is null or of
     *                                            length 0
     * @throws java.lang.IllegalArgumentException if text or comparator is null
     */
    public static List<Integer> bruteForce(CharSequence pattern,
                                           CharSequence text,
                                           CharacterComparator comparator) {
        if (pattern == null || pattern.length() == 0) {
            throw new IllegalArgumentException("Cannot scan over a null or empty pattern.");
        }
        if (text == null) {
            throw new IllegalArgumentException("Cannot check for null text in pattern");
        }
        if (comparator == null) {
            throw new IllegalArgumentException("Cannot check for pattern with a comparator that is null");
        }

        /// CHANGE THIS TO MORE BASIC LIST
        LinkedList<Integer> found = new LinkedList<Integer>();

        for (int i = 0; i < text.length() - pattern.length() + 1; i++) {
            boolean correct = true;
            for (int x = 0; x < pattern.length() && correct; x++) {
                if (comparator.compare(pattern.charAt(x), text.charAt(x + i)) != 0) {
                    correct = false;
                }
            }
            if (correct) {
                found.add(i);
            }
        }

        return found;
    }

    /**
     * Builds failure table that will be used to run the Knuth-Morris-Pratt
     * (KMP) algorithm.
     *
     * The table built should be the length of the input text.
     *
     * Note that a given index i will be the largest prefix of the pattern
     * indices [0..i] that is also a suffix of the pattern indices [1..i].
     * This means that index 0 of the returned table will always be equal to 0
     *
     * Ex. ababac
     *
     * table[0] = 0
     * table[1] = 0
     * table[2] = 1
     * table[3] = 2
     * table[4] = 3
     * table[5] = 0
     *
     * If the pattern is empty, return an empty array.
     *
     * @param pattern    a pattern you're building a failure table for
     * @param comparator you MUST use this for checking character equality
     * @return integer array holding your failure table
     * @throws java.lang.IllegalArgumentException if the pattern or comparator
     *                                            is null
     */
    public static int[] buildFailureTable(CharSequence pattern,
                                          CharacterComparator comparator) {
        if (pattern == null) {
            throw new IllegalArgumentException("Cannot make table from null pattern");
        }
        if (comparator == null) {
            throw new IllegalArgumentException("Cannot make table with null comparator");
        }

        int i = 0;
        int j = 1;
        int[] arr = new int[pattern.length()];
        while (j < arr.length) {
            if (comparator.compare(pattern.charAt(i), pattern.charAt(j)) == 0) {
                arr[j] = i + 1;
                i++;
                j++;
            } else {
                if (i == 0) {
                    arr[j] = 0;
                    j++;
                } else {
                    i = arr[i - 1];
                }
            }
        }

        return arr;
    }


    /**
     * Knuth-Morris-Pratt (KMP) algorithm that relies on the failure table (also
     * called failure function). Works better with small alphabets.
     *
     * Make sure to implement the failure table before implementing this
     * method. The amount to shift by upon a mismatch will depend on this table.
     *
     * @param pattern    the pattern you are searching for in a body of text
     * @param text       the body of text where you search for pattern
     * @param comparator you MUST use this for checking character equality
     * @return list containing the starting index for each match found
     * @throws java.lang.IllegalArgumentException if the pattern is null or of
     *                                            length 0
     * @throws java.lang.IllegalArgumentException if text or comparator is null
     */
    public static List<Integer> kmp(CharSequence pattern, CharSequence text,
                                    CharacterComparator comparator) {
        if (pattern == null || pattern.length() == 0) {
            throw new IllegalArgumentException("Cannot scan over a null or empty pattern.");
        }
        if (text == null) {
            throw new IllegalArgumentException("Cannot check for null text in pattern");
        }
        if (comparator == null) {
            throw new IllegalArgumentException("Cannot check for pattern with a comparator that is null");
        }

        LinkedList<Integer> found = new LinkedList<>();

        // pattern is too long to appear in the text
        if (pattern.length() > text.length()) {
            return found;
        }

        int[] f = buildFailureTable(pattern, comparator);
        int k = 0;
        int j = 0;
        Comparator secondary = new CharacterComparator();
        while (k + (pattern.length() - 1 - j) < text.length()) {
            if (comparator.compare(pattern.charAt(j), text.charAt(k)) == 0) {
                if (j == pattern.length() - 1) {
                    // full match found
                    found.add(k - j);
                }
                j++;
                k++;
                if (j == pattern.length()) {
                    j = f[j - 1];
                }
            } else {
                // handle failures
                if (j == 0) {
                    k++;
                } else {
                    j = f[j - 1];

                }
            }
        }

        return found;
    }

    /**
     * Builds last occurrence table that will be used to run the Boyer Moore
     * algorithm.
     *
     * Note that each char x will have an entry at table.get(x).
     * Each entry should be the last index of x where x is a particular
     * character in your pattern.
     * If x is not in the pattern, then the table will not contain the key x,
     * and you will have to check for that in your Boyer Moore implementation.
     *
     * Ex. octocat
     *
     * table.get(o) = 3
     * table.get(c) = 4
     * table.get(t) = 6
     * table.get(a) = 5
     * table.get(everything else) = null, which you will interpret in
     * Boyer-Moore as -1
     *
     * If the pattern is empty, return an empty map.
     *
     * @param pattern a pattern you are building last table for
     * @return a Map with keys of all of the characters in the pattern mapping
     * to their last occurrence in the pattern
     * @throws java.lang.IllegalArgumentException if the pattern is null
     */
    public static Map<Character, Integer> buildLastTable(CharSequence pattern) {
        if (pattern == null) {
            throw new IllegalArgumentException("cannot make table with null pattern");
        }

        HashMap<Character, Integer> last = new HashMap<>();
        int[] arr = new int[pattern.length()];
        for (int i = 0; i < pattern.length(); i++) {
            last.put(pattern.charAt(i), i); 
        }
        return last;
    }

    /**
     * Boyer Moore algorithm that relies on last occurrence table. Works better
     * with large alphabets.
     *

     *
     * Note: You may find the getOrDefault() method useful from Java's Map.
     *
     * @param pattern    the pattern you are searching for in a body of text
     * @param text       the body of text where you search for the pattern
     * @param comparator you MUST use this for checking character equality
     * @return list containing the starting index for each match found
     * @throws java.lang.IllegalArgumentException if the pattern is null or of
     *                                            length 0
     * @throws java.lang.IllegalArgumentException if text or comparator is null
     */
    public static List<Integer> boyerMoore(CharSequence pattern,
                                           CharSequence text,
                                           CharacterComparator comparator) {
        if (pattern == null || pattern.length() == 0) {
            throw new IllegalArgumentException("Cannot scan over a null or empty pattern.");
        }
        if (text == null) {
            throw new IllegalArgumentException("Cannot check for null text in pattern");
        }
        if (comparator == null) {
            throw new IllegalArgumentException("Cannot check for pattern with a comparator that is null");
        }

        LinkedList<Integer> found = new LinkedList<>();
        Map<Character, Integer> last = buildLastTable(pattern);

        int i = 0;
        while (i < text.length() - pattern.length() + 1) {
            int j = pattern.length() - 1;

            // try to check each char in the pattern against the text
            while (j >= 0 && comparator.compare(text.charAt(i + j), pattern.charAt(j)) == 0) {
                j--;
            }

            if (j == -1) {
                // pattern had a full match, add index to list and increment by one
                found.add(i);
                i++;
            } else {
                // readjust pattern by value in table
                char x = text.charAt(i + j);
                int shift = last.getOrDefault(x, -1);
                if (shift > j) {
                    i++;
                } else {
                    i = i + (j - shift);
                }
            }
        }

        return found;
    }
}
