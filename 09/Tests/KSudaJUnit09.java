import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static org.hamcrest.CoreMatchers.anyOf;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

/**
 * Ken Suda's tests.
 *
 * @author Ken Suda
 * @version 1.0
 */
@RunWith(Enclosed.class)
public class KSudaJUnit09 {

    private static final int TIMEOUT = 200;

    // Unit tests provided by TAs
    public static class MyQueueStudentTest extends PatternMatchingStudentTest {
    }

    /* Add other Student unit tests here, uncomment to activate
    public static class MyKarthikStringSearchingJUnit extends KarthikStringSearchingJUnit {
    }

     */

    public static class TestPatternMatchingBruteForceIllegalArguments {
        @Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
        public void testBruteForceNullPattern() {
            PatternMatching.bruteForce(null, "a", new CharacterComparator());
        }

        @Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
        public void testBruteForceNullText() {
            PatternMatching.bruteForce("a", null, new CharacterComparator());
        }

        @Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
        public void testBruteForceNullComparator() {
            PatternMatching.bruteForce("a", "a", null);
        }

        @Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
        public void testBruteForceEmptyPattern() {
            PatternMatching.bruteForce("", "a", new CharacterComparator());
        }
    }

    public static class TestPatternMatchingBuildFailureTableIllegalArguments {
        @Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
        public void testBuildFailureTableNullPattern() {
            PatternMatching.buildFailureTable(null, new CharacterComparator());
        }

        @Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
        public void testBuildFailureTableNullComparator() {
            PatternMatching.buildFailureTable("a", null);
        }
    }

    public static class TestPatternMatchingKMPIllegalArguments {
        @Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
        public void testBruteForceNullPattern() {
            PatternMatching.kmp(null, "a", new CharacterComparator());
        }

        @Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
        public void testBruteForceNullText() {
            PatternMatching.kmp("a", null, new CharacterComparator());
        }

        @Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
        public void testBruteForceNullComparator() {
            PatternMatching.kmp("a", "a", null);
        }

        @Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
        public void testBruteForceEmptyPattern() {
            PatternMatching.kmp("", "a", new CharacterComparator());
        }
    }

    public static class TestPatternMatchingBuildLastTableIllegalArguments {
        @Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
        public void testBuildLastTableNullPattern() {
            PatternMatching.buildLastTable(null);
        }
    }

    public static class TestPatternMatchingBoyerMooreIllegalArguments {
        @Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
        public void testBoyerMooreNullPattern() {
            PatternMatching.boyerMoore(null, "a", new CharacterComparator());
        }

        @Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
        public void testBoyerMooreNullText() {
            PatternMatching.boyerMoore("a", null, new CharacterComparator());
        }

        @Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
        public void testBoyerMooreNullComparator() {
            PatternMatching.boyerMoore("a", "a", null);
        }

        @Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
        public void testBoyerMooreEmptyPattern() {
            PatternMatching.boyerMoore("", "a", new CharacterComparator());
        }
    }

    public static class TestPatternBruteForce {
        @Test(timeout = TIMEOUT)
        public void testPatternAAinA() {
            testBruteForce("aa", "a", new int[] {}, new Character[][] {
            });
        }

        @Test(timeout = TIMEOUT)
        public void testPatternAinA() {
            testBruteForce("a", "a", new int[] {0}, new Character[][] {
                {'a', 'a'}
            });
        }

        @Test(timeout = TIMEOUT)
        public void testPatternAinAA() {
            testBruteForce("a", "aa", new int[] {0, 1}, new Character[][] {
                {'a', 'a'},
                {'a', 'a'}
            });
        }

        @Test(timeout = TIMEOUT)
        public void testPatternAinABA() {
            testBruteForce("a", "aba", new int[] {0, 2}, new Character[][] {
                {'a', 'a'},
                {'a', 'b'},
                {'a', 'a'}
            });
        }

        @Test(timeout = TIMEOUT)
        public void testPatternAinABAB() {
            testBruteForce("a", "abab", new int[] {0, 2}, new Character[][] {
                {'a', 'a'},
                {'a', 'b'},
                {'a', 'a'},
                {'a', 'b'}
            });
        }

        @Test(timeout = TIMEOUT)
        public void testPatternAAinAA() {
            testBruteForce("aa", "aa", new int[] {0}, new Character[][] {
                {'a', 'a'},
                {'a', 'a'}
            });
        }

        @Test(timeout = TIMEOUT)
        public void testPatternAAinAAA() {
            testBruteForce("aa", "aaa", new int[] {0, 1}, new Character[][] {
                {'a', 'a'},
                {'a', 'a'},
                {'a', 'a'},
                {'a', 'a'}
            });
        }

        /**
         * Checks a specific pattern with expected results
         * @param pattern Pattern to use
         * @param text Text to use
         * @param expected Expected offsets
         * @param pairs Pairs of comparisons in the order they should have been made
         */
        private void testBruteForce(CharSequence pattern, CharSequence text, int[] expected, Character[][] pairs) {
            List<Integer> expectedList = new ArrayList<>();
            for (int i:expected) {
                expectedList.add(i);
            }
            MyOrderingComparator comparator = new MyOrderingComparator(pairs);
            assertEquals(expectedList, PatternMatching.bruteForce(pattern, text, comparator));
            comparator.assertFinished();
        }
    }

    public static class TestPatternBuildFailureTable {

        @Test(timeout = TIMEOUT)
        public void testPatternEmpty() {
            testBuildFailureTable("", new int[] {}, new Character[0][]);
        }

        @Test(timeout = TIMEOUT)
        public void testPatternA() {
            testBuildFailureTable("a", new int[] {0}, new Character[0][]);
        }

        @Test(timeout = TIMEOUT)
        public void testPatternAA() {
            testBuildFailureTable("aa", new int[] {0, 1}, new Character[][] {
                {'a', 'a'}
            });
        }

        @Test(timeout = TIMEOUT)
        public void testPatternABA() {
            testBuildFailureTable("aba", new int[] {0, 0, 1}, new Character[][] {
                {'a', 'b'},
                {'a', 'a'}
            });
        }

        @Test(timeout = TIMEOUT)
        public void testPatternABAB() {
            testBuildFailureTable("abab", new int[] {0, 0, 1, 2}, new Character[][] {
                {'a', 'b'},
                {'a', 'a'},
                {'b', 'b'}
            });
        }

        @Test(timeout = TIMEOUT)
        public void testPatternABAA() {
            testBuildFailureTable("abaa", new int[] {0, 0, 1, 1}, new Character[][] {
                {'a', 'b'},
                {'a', 'a'},
                {'b', 'a'},
                {'a', 'a'}
            });
        }

        /**
         * Checks a specific pattern with expected results
         * @param pattern Pattern to use
         * @param expected Expected failure table
         * @param pairs Pairs of comparisons in the order they should have been made
         */
        private void testBuildFailureTable(CharSequence pattern, int[] expected, Character[][] pairs) {
            MyOrderingComparator comparator = new MyOrderingComparator(pairs);
            assertArrayEquals(expected, PatternMatching.buildFailureTable(pattern, comparator));
            comparator.assertFinished();
        }
    }

    public static class TestPatternKMP {
        @Test(timeout = TIMEOUT)
        public void testPatternAAinA() {
            testKMP("aa", "a", new int[] {}, new Character[][] {
            });
        }

        @Test(timeout = TIMEOUT)
        public void testPatternAinA() {
            testKMP("a", "a", new int[] {0}, new Character[][] {
                {'a', 'a'}
            });
        }

        @Test(timeout = TIMEOUT)
        public void testPatternAinAA() {
            testKMP("a", "aa", new int[] {0, 1}, new Character[][] {
                {'a', 'a'},
                {'a', 'a'}
            });
        }

        @Test(timeout = TIMEOUT)
        public void testPatternAinABA() {
            testKMP("a", "aba", new int[] {0, 2}, new Character[][] {
                {'a', 'a'},
                {'a', 'b'},
                {'a', 'a'}
            });
        }

        @Test(timeout = TIMEOUT)
        public void testPatternAinABAB() {
            testKMP("a", "abab", new int[] {0, 2}, new Character[][] {
                {'a', 'a'},
                {'a', 'b'},
                {'a', 'a'},
                {'a', 'b'}
            });
        }

        @Test(timeout = TIMEOUT)
        public void testPatternAAinAA() {
            testKMP("aa", "aa", new int[] {0}, new Character[][] {
                {'a', 'a'},
                {'a', 'a'},
                {'a', 'a'}
            });
        }

        @Test(timeout = TIMEOUT)
        public void testPatternAAinAAA() {
            testKMP("aa", "aaa", new int[] {0, 1}, new Character[][] {
                {'a', 'a'},
                {'a', 'a'},
                {'a', 'a'},
                {'a', 'a'}
            });
        }

        @Test(timeout = TIMEOUT)
        public void testPatternAAinABAA() {
            testKMP("aa", "abaa", new int[] {2}, new Character[][] {
                {'a', 'a'},
                {'a', 'a'},
                {'b', 'a'},
                {'b', 'a'},
                {'a', 'a'},
                {'a', 'a'}
            });
        }

        /**
         * Checks a specific pattern with expected results
         * @param pattern Pattern to use
         * @param text Text to use
         * @param expected Expected offsets
         * @param pairs Pairs of comparisons in the order they should have been made
         */
        private void testKMP(CharSequence pattern, CharSequence text, int[] expected, Character[][] pairs) {
            List<Integer> expectedList = new ArrayList<>();
            for (int i:expected) {
                expectedList.add(i);
            }
            MyOrderingComparator comparator = new MyOrderingComparator(pairs);
            assertEquals(expectedList, PatternMatching.kmp(pattern, text, comparator));
            comparator.assertFinished();
        }
    }

    public static class TestPatternBuildLastTable {

        @Test(timeout = TIMEOUT)
        public void testPatternEmpty() {
            testBuildLastTable("", new Character[] {}, new int[] {});
        }

        @Test(timeout = TIMEOUT)
        public void testPatternA() {
            testBuildLastTable("a", new Character[] {'a'}, new int[] {0});
        }

        @Test(timeout = TIMEOUT)
        public void testPatternAA() {
            testBuildLastTable("aa", new Character[] {'a'}, new int[] {1});
        }

        @Test(timeout = TIMEOUT)
        public void testPatternABA() {
            testBuildLastTable("aba", new Character[] {'a', 'b'}, new int[] {2, 1});
        }

        @Test(timeout = TIMEOUT)
        public void testPatternABAB() {
            testBuildLastTable("abab", new Character[] {'a', 'b'}, new int[] {2, 3});
        }

        @Test(timeout = TIMEOUT)
        public void testPatternABAA() {
            testBuildLastTable("abaa", new Character[] {'a', 'b'}, new int[] {3, 1});
        }

        /**
         * Checks a specific pattern with expected results
         * @param pattern Pattern to use
         * @param letters Expected character table
         * @param offset Expected offset
         */
        private void testBuildLastTable(CharSequence pattern, Character[] letters, int[] offset) {
            Map<Character, Integer> last = PatternMatching.buildLastTable(pattern);
            assertEquals(letters.length, last.size());
            for (int i = 0; i < letters.length; ++i) {
                assertTrue("Contains Letter " + letters[i], last.containsKey(letters[i]));
                assertEquals("Letter " + letters[i] + " offset", offset[i], last.get(letters[i]).intValue());
            }
        }
    }

    public static class TestPatternBoyerMoore {
        @Test(timeout = TIMEOUT)
        public void testPatternAAinA() {
            testBoyerMoore("aa", "a", new int[] {}, new Character[][] {
            });
        }

        @Test(timeout = TIMEOUT)
        public void testPatternAinA() {
            testBoyerMoore("a", "a", new int[] {0}, new Character[][] {
                {'a', 'a'}
            });
        }

        @Test(timeout = TIMEOUT)
        public void testPatternAinAA() {
            testBoyerMoore("a", "aa", new int[] {0, 1}, new Character[][] {
                {'a', 'a'},
                {'a', 'a'}
            });
        }

        @Test(timeout = TIMEOUT)
        public void testPatternAinABA() {
            testBoyerMoore("a", "aba", new int[] {0, 2}, new Character[][] {
                {'a', 'a'},
                {'a', 'b'},
                {'a', 'a'}
            });
        }

        @Test(timeout = TIMEOUT)
        public void testPatternAinABAB() {
            testBoyerMoore("a", "abab", new int[] {0, 2}, new Character[][] {
                {'a', 'a'},
                {'a', 'b'},
                {'a', 'a'},
                {'a', 'b'}
            });
        }

        @Test(timeout = TIMEOUT)
        public void testPatternAAinAA() {
            testBoyerMoore("aa", "aa", new int[] {0}, new Character[][] {
                {'a', 'a'},
                {'a', 'a'}
            });
        }

        @Test(timeout = TIMEOUT)
        public void testPatternAAinAAA() {
            testBoyerMoore("aa", "aaa", new int[] {0, 1}, new Character[][] {
                {'a', 'a'},
                {'a', 'a'},
                {'a', 'a'},
                {'a', 'a'}
            });
        }

        @Test(timeout = TIMEOUT)
        public void testPatternAAinABAA() {
            testBoyerMoore("aa", "abaa", new int[] {2}, new Character[][] {
                {'a', 'b'},
                {'a', 'a'},
                {'a', 'a'}
            });
        }

        @Test(timeout = TIMEOUT)
        public void testPatternABAAinABAABA() {
            testBoyerMoore("aba", "abaaba", new int[] {0, 3}, new Character[][] {
                {'a', 'a'},
                {'b', 'b'},
                {'a', 'a'},
                {'a', 'a'},
                {'a', 'b'},
                {'a', 'b'},
                {'a', 'a'},
                {'b', 'b'},
                {'a', 'a'}
            });
        }

        /**
         * Checks a specific pattern with expected results
         * @param pattern Pattern to use
         * @param text Text to use
         * @param expected Expected offsets
         * @param pairs Pairs of comparisons in the order they should have been made
         */
        private void testBoyerMoore(CharSequence pattern, CharSequence text, int[] expected, Character[][] pairs) {
            List<Integer> expectedList = new ArrayList<>();
            for (int i:expected) {
                expectedList.add(i);
            }
            MyOrderingComparator comparator = new MyOrderingComparator(pairs);
            assertEquals(expectedList, PatternMatching.boyerMoore(pattern, text, comparator));
            comparator.assertFinished();
        }
    }

//    public static class TestPatternMass {
//        @Test(timeout = 1000 * 60 * 10) // 10 minutes, mine runs in ~4:35
//        public void testMass() {
//            Set<CharSequence> combinations = new HashSet<>();
//            // not a good way to build this, but meh, it's a mass test.
//            buildCombinations("aaabbbccc", "", new boolean[9], combinations);
//            CharacterComparator comparator = new CharacterComparator();
//            //uncomment out next line to disable this test
//            //if (combinations.size() > 0) return;
//            for (CharSequence pattern:combinations) {
//                if (pattern.length() <= 4) {
//                    for (CharSequence text : combinations) {
//                        if (pattern.length() <= text.length()) {
//                            List<Integer> bruteForce = PatternMatching.bruteForce(pattern, text, comparator);
//                            List<Integer> kmp = PatternMatching.kmp(pattern, text, comparator);
//                            List<Integer> boyerMoore = PatternMatching.boyerMoore(pattern, text, comparator);
//                            assertTrue("pattern=" + pattern + " text=" + text + " bruteForce="
//                                    + Arrays.toString(bruteForce.toArray()) + " kmp=" + Arrays.toString(kmp.toArray())
//                                    + " boyerMore=" + Arrays.toString(boyerMoore.toArray()),
//                                bruteForce.equals(kmp) && bruteForce.equals(boyerMoore));
//                        }
//                    }
//                }
//            }
//            System.out.println("For information, there were " + comparator.getComparisonCount() +  " comparisons");
//            // mine was 216682888
//        }
//
//        /**
//         * Builds a set of all the possible combinations.
//         * @param base Full set of characters to use
//         * @param pattern Current pattern for recursion
//         * @param used Is the specific index of base used
//         * @param combinations List to add combinations to.
//         */
//        private void buildCombinations(CharSequence base, CharSequence pattern, boolean[] used,
//                                       Set<CharSequence> combinations) {
//            if (pattern.length() > 0) {
//                combinations.add(pattern);
//            }
//            StringBuilder childPattern = new StringBuilder(pattern.length() + 1);
//            for (int i = 0; i < base.length(); ++i) {
//                if (!used[i]) {
//                    childPattern.setLength(0);
//                    childPattern.append(pattern);
//                    childPattern.append(base.charAt(i));
//                    used[i] = true;
//                    buildCombinations(base, childPattern, used, combinations);
//                    used[i] = false;
//                }
//            }
//        }
//    }

    private static class MyOrderingComparator extends CharacterComparator {
        private Character[][] pairs;
        private int offset;

        /**
         * Take comparison order
         * @param pairs Pairs of comparisons that should be made
         */
        public MyOrderingComparator(Character[][] pairs) {
            this.pairs = pairs;
        }

        @Override
        public int compare(Character a, Character b) {
            //System.err.println("#" + offset + " " + a + "=" + b);
            assertTrue("Too many comparisons with " + a + " " + b, offset < pairs.length);
            String comparisonString = "Comparison#" + offset + " expected " + pairs[offset][0] + " "
                + pairs[offset][1] + " actual " + a + " " + b;
            if (pairs[offset][0] == a) {
                assertEquals(comparisonString, pairs[offset][1], b);
            } else if (pairs[offset][1] == a) {
                assertEquals(comparisonString, pairs[offset][0], b);
            } else {
                assertThat(comparisonString, a, anyOf(equalTo(pairs[offset][0]), equalTo(pairs[offset][1])));
            }
            ++offset;
            return a - b;
        }

        /**
         * Checks if comparator is done.
         */
        public void assertFinished() {
            assertEquals("Not enough comparisons", pairs.length, offset);
        }
    }
}
