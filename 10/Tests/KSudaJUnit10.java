import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Ken Suda's tests.
 *
 * @author Ken Suda
 * @version 1.0
 */
@RunWith(Enclosed.class)
public class KSudaJUnit10 {

    private static final int TIMEOUT = 200;

    // Unit tests provided by TAs
    public static class MyQueueStudentTest extends GraphAlgorithmsStudentTest {
    }

    /* Add other Student unit tests here, uncomment to activate

    public static class MyKarthikGraphJUnit extends KarthikGraphJUnit {
    }

     */

    public static class TestBFSIllegalArguments {
        @Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
        public void testBFSNullStart() {
            GraphAlgorithms.bfs(null, new Graph<Integer>(new HashSet<>(), new HashSet<>()));
        }

        @Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
        public void testBFSNullGraph() {
            GraphAlgorithms.bfs(new Vertex<Integer>(1), null);
        }

        @Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
        public void testBFSVectorNotInGraph() {
            GraphAlgorithms.bfs(new Vertex<Integer>(1), new Graph<Integer>(new HashSet<>(), new HashSet<>()));
        }
    }

    public static class TestDFSIllegalArguments {
        @Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
        public void testDFSNullStart() {
            GraphAlgorithms.dfs(null, new Graph<Integer>(new HashSet<>(), new HashSet<>()));
        }

        @Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
        public void testDFSNullGraph() {
            GraphAlgorithms.dfs(new Vertex<Integer>(1), null);
        }

        @Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
        public void testDFSVectorNotInGraph() {
            GraphAlgorithms.dfs(new Vertex<Integer>(1), new Graph<Integer>(new HashSet<>(), new HashSet<>()));
        }
    }

    public static class TestDijkstrasIllegalArguments {
        @Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
        public void testDijkstrasNullStart() {
            GraphAlgorithms.dijkstras(null, new Graph<Integer>(new HashSet<>(), new HashSet<>()));
        }

        @Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
        public void testDijkstrasNullGraph() {
            GraphAlgorithms.dijkstras(new Vertex<Integer>(1), null);
        }

        @Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
        public void testDijkstrasVectorNotInGraph() {
            GraphAlgorithms.dijkstras(new Vertex<Integer>(1), new Graph<Integer>(new HashSet<>(), new HashSet<>()));
        }
    }

    public static class TestBFS {
        @Test(timeout = TIMEOUT)
        public void testSingleVertex() {
            Helper helper = Helper.buildSingle();
            testGraph('a', helper, "a");
        }

        @Test(timeout = TIMEOUT)
        public void testSingleVertexSelfLoop() {
            Helper helper = Helper.buildSingleSelfLoop();
            testGraph('a', helper, "a");
        }

        @Test(timeout = TIMEOUT)
        public void testDoubleVertexSingleDirection() {
            Helper helper = Helper.buildDoubleSingleDirection();
            testGraph('a', helper, "ab");
            testGraph('b', helper, "b");
        }

        @Test(timeout = TIMEOUT)
        public void testDoubleVertexBiDirection() {
            Helper helper = Helper.buildDoubleBiDirection();
            testGraph('a', helper, "ab");
            testGraph('b', helper, "ba");
        }

        @Test(timeout = TIMEOUT)
        public void testTripleFullDirection() {
            Helper helper = Helper.buildTripleFullDirection();
            testGraph('a', helper, "abc");
            testGraph('b', helper, "bac");
            testGraph('c', helper, "cab");
        }

        @Test(timeout = TIMEOUT)
        public void testTripleChain() {
            Helper helper = Helper.buildTripleChain();
            testGraph('a', helper, "abc");
            testGraph('b', helper, "bac");
            testGraph('c', helper, "cba");
        }

        @Test(timeout = TIMEOUT)
        public void testTripleDisconnected() {
            Helper helper = Helper.buildTripleDisconnected();
            testGraph('a', helper, "ab");
            testGraph('b', helper, "ba");
            testGraph('c', helper, "c");
        }

        @Test(timeout = TIMEOUT)
        public void testQuadCircle() {
            Helper helper = Helper.buildQuadCircle();
            testGraph('a', helper, "abcd");
            testGraph('b', helper, "bcda");
            testGraph('c', helper, "cdab");
            testGraph('d', helper, "dabc");
        }

        @Test(timeout = TIMEOUT)
        public void testQuadFull() {
            Helper helper = Helper.buildQuadFull();
            testGraph('a', helper, "abcd");
            testGraph('b', helper, "bacd");
            testGraph('c', helper, "cabd");
            testGraph('d', helper, "dabc");
        }

        @Test(timeout = TIMEOUT)
        public void testSyllabus() {
            SyllabusText st = new SyllabusText();
            List<Vertex<String>> list = GraphAlgorithms.bfs(st.vertexMap.get("cs1332"), st.graph);
            if (false) {
                //generate list
                System.out.print("private String[] bfs = new String[] {\n\t\t");
                int c = 0;
                boolean first = true;
                for (Vertex<String> vertex:list) {
                    String word = vertex.getData();
                    if (first) {
                        first = false;
                    } else {
                        System.out.print(",");
                        if (c + word.length() + 4 > 80) {
                            System.out.print("\n\t\t");
                            c = 0;
                        } else {
                            System.out.print(" ");
                        }
                    }
                    c += word.length() + 4;
                    System.out.print("\"" + word + "\"");
                }
                System.out.println("};");
            }
            String[] words = new String[list.size()];
            for (int x = 0; x < words.length; ++x) {
                words[x] = list.get(x).getData();
            }
            assertArrayEquals(st.bfs, words);
        }

        /**
         * Tests if BFS method returns the correct results in the correct order.
         *  @param startChar Starting vertex
         * @param helper Graph object to do the search
         * @param expected String representing the sequence in which the nodes should have been found
         */
        private void testGraph(Character startChar, Helper helper, String expected) {
            List<Vertex<Character>> list = GraphAlgorithms.bfs(helper.vertexMap.get(startChar), helper.graph);
            StringBuilder results = new StringBuilder(list.size());
            for (Vertex<Character> vertex:list) {
                results.append(vertex.getData());
            }
            assertEquals(expected, results.toString());
        }
    }

    public static class TestDFS {
        @Test(timeout = TIMEOUT)
        public void testSingleVertex() {
            Helper helper = Helper.buildSingle();
            testGraph('a', helper, "a");
        }

        @Test(timeout = TIMEOUT)
        public void testSingleVertexSelfLoop() {
            Helper helper = Helper.buildSingleSelfLoop();
            testGraph('a', helper, "a");
        }

        @Test(timeout = TIMEOUT)
        public void testDoubleVertexSingleDirection() {
            Helper helper = Helper.buildDoubleSingleDirection();
            testGraph('a', helper, "ab");
            testGraph('b', helper, "b");
        }

        @Test(timeout = TIMEOUT)
        public void testDoubleVertexBiDirection() {
            Helper helper = Helper.buildDoubleBiDirection();
            testGraph('a', helper, "ab");
            testGraph('b', helper, "ba");
        }

        @Test(timeout = TIMEOUT)
        public void testTripleFullDirection() {
            Helper helper = Helper.buildTripleFullDirection();
            testGraph('a', helper, "abc");
            testGraph('b', helper, "bac");
            testGraph('c', helper, "cab");
        }

        @Test(timeout = TIMEOUT)
        public void testTripleChain() {
            Helper helper = Helper.buildTripleChain();
            testGraph('a', helper, "abc");
            testGraph('b', helper, "bac");
            testGraph('c', helper, "cba");
        }

        @Test(timeout = TIMEOUT)
        public void testTripleDisconnected() {
            Helper helper = Helper.buildTripleDisconnected();
            testGraph('a', helper, "ab");
            testGraph('b', helper, "ba");
            testGraph('c', helper, "c");
        }

        @Test(timeout = TIMEOUT)
        public void testQuadCircle() {
            Helper helper = Helper.buildQuadCircle();
            testGraph('a', helper, "abcd");
            testGraph('b', helper, "bcda");
            testGraph('c', helper, "cdab");
            testGraph('d', helper, "dabc");
        }

        @Test(timeout = TIMEOUT)
        public void testQuadFull() {
            Helper helper = Helper.buildQuadFull();
            testGraph('a', helper, "abcd");
            testGraph('b', helper, "bacd");
            testGraph('c', helper, "cabd");
            testGraph('d', helper, "dabc");
        }

        @Test(timeout = TIMEOUT)
        public void testSyllabus() {
            SyllabusText st = new SyllabusText();
            List<Vertex<String>> list = GraphAlgorithms.dfs(st.vertexMap.get("cs1332"), st.graph);
            if (false) {
                //generate list
                System.out.print("private String[] dfs = new String[] {\n\t\t");
                int c = 0;
                boolean first = true;
                for (Vertex<String> vertex:list) {
                    String word = vertex.getData();
                    if (first) {
                        first = false;
                    } else {
                        System.out.print(",");
                        if (c + word.length() + 4 > 80) {
                            System.out.print("\n\t\t");
                            c = 0;
                        } else {
                            System.out.print(" ");
                        }
                    }
                    c += word.length() + 4;
                    System.out.print("\"" + word + "\"");
                }
                System.out.println("};");
            }
            String[] words = new String[list.size()];
            for (int x = 0; x < words.length; ++x) {
                words[x] = list.get(x).getData();
            }
            assertArrayEquals(st.dfs, words);
        }

        /**
         * Tests if DFS method returns the correct results in the correct order.
         *  @param startChar Starting vertex
         * @param helper Graph object to do the search
         * @param expected String representing the sequence in which the nodes should have been found
         */
        private void testGraph(Character startChar, Helper helper, String expected) {
            List<Vertex<Character>> list = GraphAlgorithms.dfs(helper.vertexMap.get(startChar), helper.graph);
            StringBuilder results = new StringBuilder(list.size());
            for (Vertex<Character> vertex:list) {
                results.append(vertex.getData());
            }
            assertEquals(expected, results.toString());
        }
    }

    public static class TestDijkstras {
        @Test(timeout = TIMEOUT)
        public void testSingleVertex() {
            Helper helper = Helper.buildSingle();
            testGraph('a', helper, new String[] {"a0"});
        }

        @Test(timeout = TIMEOUT)
        public void testSingleVertexSelfLoop() {
            Helper helper = Helper.buildSingleSelfLoop();
            testGraph('a', helper, new String[] {"a0"});
        }

        @Test(timeout = TIMEOUT)
        public void testDoubleVertexSingleDirection() {
            Helper helper = Helper.buildDoubleSingleDirection();
            testGraph('a', helper, new String[] {"a0", "b1"});
            testGraph('b', helper, new String[] {"b0", "a" + Integer.MAX_VALUE});
        }

        @Test(timeout = TIMEOUT)
        public void testDoubleVertexBiDirection() {
            Helper helper = Helper.buildDoubleBiDirection();
            testGraph('a', helper, new String[] {"a0", "b1"});
            testGraph('b', helper, new String[] {"b0", "a1"});
        }

        @Test(timeout = TIMEOUT)
        public void testTripleFullDirection() {
            Helper helper = Helper.buildTripleFullDirection();
            testGraph('a', helper, new String[] {"a0", "b1", "c1"});
            testGraph('b', helper, new String[] {"b0", "a1", "c1"});
            testGraph('c', helper, new String[] {"c0", "a1", "b1"});
        }

        @Test(timeout = TIMEOUT)
        public void testTripleChain() {
            Helper helper = Helper.buildTripleChain();
            testGraph('a', helper, new String[] {"a0", "b1", "c2"});
            testGraph('b', helper, new String[] {"a1", "b0", "c1"});
            testGraph('c', helper, new String[] {"a2", "b1", "c0"});
        }

        @Test(timeout = TIMEOUT)
        public void testTripleDisconnected() {
            Helper helper = Helper.buildTripleDisconnected();
            testGraph('a', helper, new String[] {"a0", "b1", "c" + Integer.MAX_VALUE});
            testGraph('b', helper, new String[] {"a1", "b0", "c" + Integer.MAX_VALUE});
            testGraph('c', helper, new String[] {"c0", "a" + Integer.MAX_VALUE, "b" + Integer.MAX_VALUE});
        }

        @Test(timeout = TIMEOUT)
        public void testQuadCircle() {
            Helper helper = Helper.buildQuadCircle();
            testGraph('a', helper, new String[] {"a0", "b1", "c2", "d3"});
            testGraph('b', helper, new String[] {"a3", "b0", "c1", "d2"});
            testGraph('c', helper, new String[] {"a2", "b3", "c0", "d1"});
            testGraph('d', helper, new String[] {"a1", "b2", "c3", "d0"});
        }

        @Test(timeout = TIMEOUT)
        public void testQuadFull() {
            Helper helper = Helper.buildQuadFull();
            testGraph('a', helper, new String[] {"a0", "b1", "c1", "d1"});
            testGraph('b', helper, new String[] {"a1", "b0", "c1", "d1"});
            testGraph('c', helper, new String[] {"a1", "b1", "c0", "d1"});
            testGraph('d', helper, new String[] {"a1", "b1", "c1", "d0"});
        }

        @Test(timeout = TIMEOUT)
        public void testSyllabus() {
            SyllabusText st = new SyllabusText();
            Map<Vertex<String>, Integer> map = GraphAlgorithms.dijkstras(st.vertexMap.get("cs1332"), st.graph);
            if (false) {
                //generate list
                System.out.print("private String[] dijkstras = new String[] {\n\t\t");
                int c = 0;
                boolean first = true;
                for (Vertex<String> vertex:map.keySet()) {
                    String word = vertex.getData() + ":" + map.get(vertex);
                    if (first) {
                        first = false;
                    } else {
                        System.out.print(",");
                        if (c + word.length() + 4 > 80) {
                            System.out.print("\n\t\t");
                            c = 0;
                        } else {
                            System.out.print(" ");
                        }
                    }
                    c += word.length() + 4;
                    System.out.print("\"" + word + "\"");
                }
                System.out.println("};");
            }
            assertEquals("Results size", st.dijkstras.length, map.size());
            for (String wordDistance:st.dijkstras) {
                String word = wordDistance.substring(0, wordDistance.indexOf(':'));
                Integer distance = Integer.parseInt(wordDistance.substring(word.length() + 1));
                Vertex<String> vertex = st.vertexMap.get(word);
                assertTrue("Contains " + word, map.containsKey(vertex));
                assertEquals(distance, map.get(vertex));
            }
        }

        /**
         * Tests if Dijkstras method returns the correct results with correct weights.
         *  @param startChar Starting vertex
         * @param helper Graph object to do the search
         * @param expected String array representing the nodes that should have been found and the distance
         */
        private void testGraph(Character startChar, Helper helper, String[] expected) {
            Map<Vertex<Character>, Integer> map = GraphAlgorithms.dijkstras(helper.vertexMap.get(startChar),
                helper.graph);
            assertEquals("Result size", expected.length, map.size());
            for (String e:expected) {
                Character c = e.charAt(0);
                Vertex<Character> v = helper.vertexMap.get(c);
                Integer d = Integer.parseInt(e.substring(1));
                assertTrue("Results contains " + c,  map.containsKey(v));
                assertEquals("Result distance for " + c, d, map.get(v));
            }
        }
    }

    private static class Helper {
        private Graph<Character> graph;
        private Map<Character, Vertex<Character>> vertexMap;

        /**
         * Creates a helper class that contains the graph, vertices, and edges separately.
         *
         * @param vertices String representing a node for each character
         * @param edges Array of character strings that represent the directed edges. First character is the
         *              from vertex. Second character is the to vertex. Characters 3 and beyond are converted to
         *              an integer for the weight.
         */
        private Helper(String vertices, String[] edges) {
            vertexMap = new HashMap<>();
            for (int x = 0; x < vertices.length(); ++x) {
                Character c = vertices.charAt(x);
                vertexMap.put(c, new Vertex<>(c));
            }
            Set<Edge<Character>> edgeSet = new LinkedHashSet<>();
            for (String edge:edges) {
                Vertex<Character> from = vertexMap.get(edge.charAt(0));
                Vertex<Character> to = vertexMap.get(edge.charAt(1));
                edgeSet.add(new Edge<>(from, to, Integer.parseInt(edge.substring(2))));
            }
            graph = new Graph<>(new HashSet<>(vertexMap.values()), edgeSet);
        }

        /**
         * Builds a single node with no edges graph
         * @return helper with single node and no edges.
         */
        private static Helper buildSingle() {
            return new Helper("a", new String[0]);
        }

        /**
         * Single node with a single directed edge self loop
         * @return Helper
         */
        private static Helper buildSingleSelfLoop() {
            return new Helper("a", new String[] {"aa1"});
        }

        /**
         * Two nodes with a single directed edge between
         * @return Helper
         */
        private static Helper buildDoubleSingleDirection() {
            return new Helper("ab", new String[] {"ab1"});
        }

        /**
         * Two nodes with a bi-directed edge between
         * @return Helper
         */
        private static Helper buildDoubleBiDirection() {
            return new Helper("ab", new String[] {"ab1", "ba1"});
        }

        /**
         * Three nodes with a bi-directed edge between all
         * @return Helper
         */
        private static Helper buildTripleFullDirection() {
            return new Helper("abc", new String[] {"ab1", "ac1", "ba1", "bc1", "ca1", "cb1"});
        }

        /**
         * Three nodes a<->b<->c, no connections between a and c
         * @return Helper
         */
        private static Helper buildTripleChain() {
            return new Helper("abc", new String[] {"ab1", "ba1", "bc1", "cb1"});
        }

        /**
         * Three nodes with a bi-directed edge between a and b, and no connections to c
         * @return Helper
         */
        private static Helper buildTripleDisconnected() {
            return new Helper("abc", new String[] {"ab1", "ba1"});
        }

        /**
         * Four nodes with a uni-directed edge between them in a circle
         * @return Helper
         */
        private static Helper buildQuadCircle() {
            return new Helper("abcd", new String[] {"ab1", "bc1", "cd1", "da1"});
        }

        /**
         * Four nodes with all nodes connected to all others
         * @return Helper
         */
        private static Helper buildQuadFull() {
            return new Helper("abcd", new String[] {"ab1", "ac1", "ad1", "ba1", "bc1", "bd1", "ca1", "cb1",
                "cd1", "da1", "db1", "dc1"});
        }
    }

    private static class SyllabusText {
        private String text =
            "course syllabus jump to today cs1332 data structures algorithms professor dr "
                + "mary hudachek buswell email hudachek cc gatech edu office bluejeans office "
                + "hours make appointments via gt email for mondays 10 am 1 pm wednesdays 10 am 1 "
                + "pm i do not hold office hours the first or last week of the semester course "
                + "registration details effective spring 2020 registration requires a change where "
                + "students are to register for both lecture and recitation separately cs1332 data "
                + "structures and algorithms will consist of two separate courses cs1332 3 0 "
                + "credits and cs1332r 0 0 credits you are to register for one lecture section and "
                + "one recitation section associated with lecture for example cs 1332 a lecture "
                + "and cs 1332 r a01 a06 recitation credit and billing is done based on lecture "
                + "while recitations are non billable and do not count towards full time hours the "
                + "first course cs1332 consists of online videos content and materials for the "
                + "course the second course cs1332r is the recitation section where additional "
                + "resources practice and review are provided on the learning objectives for "
                + "smaller groups of students all graded work is associated with cs1332 no graded "
                + "work takes place in recitation recitation does not cost the student anything "
                + "and provides so much benefit the professor strongly suggests that all students "
                + "register for recitation while recitations are not required they are strongly "
                + "encouraged and students cannot attend a recitation section unless they are "
                + "properly registered for it see gt rules and regulation ix students may attend "
                + "only those particular classes for which they are registered and paid therefore "
                + "we are taking attendance in recitation if you are not registered for recitation "
                + "you will have to leave we follow all gt rules and regulations all recitation "
                + "videos are available to all students teaching assistants ta list and office "
                + "hours are on the ta info help page adrianna brown head ta adrianna brown gatech "
                + "edu caroline kish head ta ckish3 gatech edu rodrigo pontes senior ta rpontes3 "
                + "gatech edu david wang senior ta dwang388 gatech edu jacob allen senior ta "
                + "jallen317 gatech edu where to find your ta help desk or ta office hours will be "
                + "held on bluejeans check piazza for links to bluejeans helpdesk or schedule via "
                + "email schedule of help desk hours will be posted on canvas you may see any "
                + "cs1332 ta for help course information lectures and final exams section a "
                + "lecture is via course videos see modules section a final exam is april 24 2020 "
                + "testing window is 12 01 am to 11 59 pm 24 hours section b lecture is via course "
                + "videos see modules section b final exam is april 29 2020 testing window is 12 "
                + "01 am to 11 59 pm 24 hours section c lecture is via course videos see modules "
                + "section c final exam is april 30 2020 testing window is 12 01 am to 11 59 pm 24 "
                + "hours prerequisite you must have a c or better in cs1331 to remain in this "
                + "course if you do not have this prerequisite you will be dropped recommended "
                + "textbook data structures and algorithms in java 6 edition by goodrich tamassia "
                + "and goldwasser 2014 isbn 9781118771334 kindle edition is fine the 5th edition "
                + "is probably also fine course website resources communication canvas is the "
                + "course management tool for the semester http canvas gatech edu links to an "
                + "external site the cs1332 visualization tool can be found at csvistool com links "
                + "to an external site gatech email is the official communication tool between "
                + "professors and students for this course you must check for messages on a "
                + "regular basis all communication with your professor should be through the "
                + "gatech email account students are responsible for any and all announcements "
                + "made during lecture or on canvas java 8 checkstyle 8 12 or 8 14 junit 4 12 ide "
                + "is your choice the tas officially endorse intellij as an ide cs1332 videos "
                + "slides cs1332 visualization tool saikrishna slides and reality checks have been "
                + "created for student use in this course they are considered intellectual "
                + "property and are not to be posted or shared anywhere except within the confines "
                + "of the course party avl course objectives develop more skills in individual "
                + "java programming work with common data structures used in software development "
                + "by coding their low level implementation arrays arraylists linkedlists singular "
                + "doubly circular stacks queues deques priority queues various trees binary "
                + "binary search avl heaps 2 4 trees etc… hash maps tables external chaining and "
                + "probing graphs and their algorithms become familiar with common algorithms on "
                + "these data structures sorting algorithms bubble insertion selection cocktail "
                + "shaker merge quick radix etc pattern matching algorithms brute force boyer "
                + "moore kmp rabin karp graph algorithms dijkstra’s shortest path and multiple "
                + "msts dynamic programming algorithms understand big o notation to promote the "
                + "ideal choice of data structure algorithm for a particular set of conditions "
                + "allowing good choices about the appropriate data structure and algorithm to use "
                + "for a particular programming problem improve one s ability to test and debug "
                + "programs lecture attendance is required the professor strongly suggests that "
                + "all students register for recitation recitation does not cost the student "
                + "anything and provides so much benefit we are taking attendance in recitation if "
                + "you are not registered for recitation you will have to leave we follow all gt "
                + "rules and regulations you are responsible for any and all announcements made "
                + "during lecture whether you are present or not all students are required to "
                + "attend the lecture that they are registered in for the duration of the semester "
                + "switching sections is not allowed sitting in is against gt policy and is "
                + "explicitly not allowed we do not violate institute policy you are not allowed "
                + "to attend without being registered problems with this will be turned over to "
                + "the dean of students and or gt police learning accommodations classroom "
                + "accommodations for students with documented disabilities will be made if "
                + "necessary these accommodations must be arranged in advance and in accordance "
                + "with the office of disability services http disability services gatech edu links "
                + "to an external site non discrimination the institute does not discriminate "
                + "against individuals on the basis of race color religion sex national origin age "
                + "disability sexual orientation gender identity or veteran status in the "
                + "administration of admissions policies educational policies employment policies "
                + "or any other institute governed programs and activities the institutes equal "
                + "opportunity and nondiscrimination policy applies to every member of the "
                + "institute community for more details see http www policy library gatech edu "
                + "policy nondiscrimination and affirmative action links to an external site "
                + "conduct policy it is expected that everyone will follow the student faculty "
                + "expectations document and the student code of conduct the professor expects a "
                + "positive respectful and engaged academic environment inside the classroom "
                + "outside the classroom in all electronic communications on all file submissions "
                + "and on any document submitted throughout the duration of the course no "
                + "inappropriate language is to be used and any assignment deemed by the professor "
                + "to contain inappropriate or offensive language will get a zero you are to use "
                + "professionalism in your work violations of this conduct policy will be turned "
                + "over to the office of student integrity for misconduct party graphs assessments "
                + "policies and grade thresholds there will be homeworks every week and exams are "
                + "roughly 3 or 4 weeks apart expect for a homework to be due during the last full "
                + "week of class of the semester there are no dropped exams or homeworks there are "
                + "no exam retakes there is no extra credit ever there is no curve in this course "
                + "letter grade cutoffs use a straight scale the final exam is mandatory passing "
                + "is hereby defined as 70 or higher removing the constraint in addition to having "
                + "a passing average you must have a passing exam average all exams and the final "
                + "are averaged together weighted to pass this class there is a participation "
                + "grade for this course students will have reality checks rc on topics every "
                + "lecture that will be turned in during lecture or the day of feedback will be "
                + "given to students who complete the work type assessment points grade scale "
                + "percentage participation 3 a 90 0 homework programming 13 b 80 0 exam 1 20 c 70 "
                + "0 exam 2 20 d 60 0 exam 3 20 f less than 60 0 final exam 24 total 100 "
                + "assignment deadlines exceptions there are no makeups for missed exams homeworks "
                + "or reality checks institute approved absences are rare and do qualify for an "
                + "exception please minimize absences to attend the georgia tech all majors career "
                + "fair for any absences due to interviews you must provide documentation from the "
                + "employer on letterhead with the exact interview travel dates in order to be "
                + "excused for attendance at a career fair you must send an email to the professor "
                + "prior to the event with the exact day and time an excuse for career fair "
                + "attendance will only cover reality checks not homework if you are absent due to "
                + "official georgia tech business please forward the electronic official "
                + "documentation to the professor in an email stating the dates you will be absent "
                + "it will be noted in an offline document that the professor tracks events such "
                + "as vacations weddings graduations errands work conflicts sleeping through your "
                + "alarm alarm malfunction forgetting to submit forgetting the date or time of an "
                + "exam or not being aware of the assignment are all not valid excuses if you miss "
                + "any assignment without a valid excuse then you receive a 0 if you will be "
                + "observing religious holidays during the semester then inform your professor the "
                + "first week of the semester in writing especially of any conflicts with exam "
                + "dates any request for exceptions to the no makeup policy must be made in "
                + "advance of the assessment unless it is physically impossible documented "
                + "incapacitating illness death in the family judicial procedures military service "
                + "or official school functions are considered valid excuses be aware that "
                + "documentation must be provided on letterhead with the signature of a physician "
                + "supervisor or other appropriate official additionally the excuse must be "
                + "encompass the date s of any assignment for which you are requesting an "
                + "exception all situations will be referred to the dean of students office for "
                + "verification therefore contact the dean of students with your documentation and "
                + "they will inform you of the proper procedures https studentlife gatech edu "
                + "links to an external site the dean of students office then contacts your "
                + "professor directly with any accommodations to be provided the final decision "
                + "regarding any exception is made solely at the discretion of your professor we "
                + "will only offer a single makeup day for each exam we write a brand new exam for "
                + "makeups so that the integrity of the exams remains intact the exam makeup day "
                + "takes place one week later on a thursday when classes are in session from 11 00 "
                + "11 50 am in a place tbd the makeup exam is a traditional exam and is "
                + "administered as such if you have an excused absence then you may take the "
                + "makeup exam at this time if you miss the makeup day then you must have another "
                + "institute approved absence in order for us to consider alternative dates if you "
                + "miss the makeup day without a valid excuse then you receive a 0 should you wake "
                + "up ill on the day of an exam do not come to the exam alert the instructor then "
                + "go immediately to stamps once you have documentation of your illness submit it "
                + "to the instructor as discussed above to be allowed to take the makeup exam if "
                + "you come take the exam we will assume you are well enough for your performance "
                + "to accurately reflect your knowledge and you will not be allowed to retake the "
                + "exam grade disputes regrades we will be using gradescope this term which allows "
                + "us to provide fast and accurate feedback on your work exam grades will be "
                + "returned through gradescope your gradescope login is your university email and "
                + "your password can be changed here links to an external site when the first exam "
                + "is released you will receive an email to set your password for the first time "
                + "as soon as grades are posted you will be notified immediately so that you can "
                + "log in and see your feedback if you feel that a regrade request is justified "
                + "visit the help desk with your laptop and discuss the dispute with any ta the ta "
                + "will give you instructions on how to submit your regrade request regrade "
                + "request results will be returned within 1 2 weeks of the assignment s regrade "
                + "request deadline grade disputes are rare but if you find yourself not clear "
                + "about why points were lost we have a strict policy and procedure to follow "
                + "disputes of grading on assignments exams etc must be discussed within 7 days of "
                + "the assignment being returned any exceptions to this timeline will be announced "
                + "before grades are released all regrade requests go through the head ta should "
                + "you find yourself having an issue with a grade contact the head ta no regrade "
                + "requests submitted after the deadline or without approval from a ta will be "
                + "considered if the head ta is unable to resolve the issue contact your "
                + "instructor every regrade request must have a detailed reason why a regrade is "
                + "needed i d like to get more points is not a valid reason and will result in the "
                + "request being promptly declined furthermore any regrade request that is not "
                + "respectful and professional will be declined be aware that any regrade request "
                + "may result in your entire exam or homework being regraded your grade may go up "
                + "or down it is your responsibility to ensure that all the grades in canvas are "
                + "correct before finals week after that the only grade discussion will be about "
                + "the grading of your final exam any discussion of your grades after the final "
                + "exam must be done in person and cannot occur until the 3rd week of the next "
                + "semester you are in school final exams are not released or returned to students "
                + "they remain on file for the college party avl academic honesty every student is "
                + "expected to read understand and abide by the georgia tech academic honor code "
                + "http osi gatech edu content honor code links to an external site links to an "
                + "external site academic misconduct is taken very seriously in this class when "
                + "working on homework assignments you may not directly copy code from any source "
                + "other than your own past submissions you are welcome to collaborate with peers "
                + "and consult external resources but you must personally write all of the code "
                + "you submit you must list at the top of each file in your submission every "
                + "student with whom you collaborated and every resource you consulted while "
                + "completing the assignment you may not directly share any files containing "
                + "assignment code with other students or post your code publicly online if you "
                + "wish to store your code online in a personal private repository you can use "
                + "github enterprise https github gatech edu links to an external site to do this "
                + "for free the only code you may share is junit test code on a pinned post on the "
                + "official course piazza use junits from other students at your own risk we do "
                + "not endorse them see each assignment’s pdf for more details if you share junits "
                + "they must be shared on the site specified in the piazza post and not anywhere "
                + "else including a personal github account violators of the collaboration policy "
                + "for this course will be turned into the office of student integrity homework "
                + "submission responsibility all course information and resources can be found in "
                + "canvas http canvas gatech edu links to an external site to include syllabus "
                + "assignments submissions announcements grades feedback resources … all homework "
                + "for all sections will have a single due date homework due dates may differ "
                + "depending on the length of the assignment homework is always due at 11 55 00 pm "
                + "for full credit consideration there is a grace period until 2 00 00 am the "
                + "following day but your submission will be marked as late by canvas and you will "
                + "be penalized 25 percentage points for the assignment do not ask for an "
                + "exception to the late policy unless you have a valid excuse accompanied by "
                + "documentation that has been submitted to the dean of students office homework "
                + "turn in is via canvas turning in homework properly on canvas is solely your "
                + "responsibility that last statement bears repeating canvas is not forgiving "
                + "about due dates and times imagine a train taking off whether or not you are "
                + "fully on board it has no love canvas always overrides previous submissions if "
                + "you are late submitting canvas will override your earlier submission we will "
                + "not grade an earlier submission or any part of an earlier submission if you are "
                + "late or under any other circumstance we only grade the latest submission "
                + "homework submissions that do not follow the conventions listed in the homework "
                + "information pdfs of the expected deliverables e g names types and number of "
                + "files will be treated as non compiling and will earn a zero if an assignment "
                + "requires the submission of multiple files all necessary deliverables must be "
                + "included in your most recent submission to be considered turning in homework "
                + "properly on canvas is solely your responsibility canvas submissions it is "
                + "completely within your power to make sure your homework is submitted properly "
                + "if you are not conscientious about your submission then there is a high "
                + "likelihood you will trip up and not turn in one or more assignments correctly "
                + "you are to upload the java files and any other files required by the assignment "
                + "class files will not be graded and will be given a 0 to receive credit the file "
                + "s must compile in java 8 using only junit 4 12 and the java standard libraries "
                + "after submitting your file s for a hw reload canvas going to the assignments "
                + "link within the cs1332 tab look at the assignment in question you should now "
                + "see that it says it has been submitted with the date and time download a fresh "
                + "copy of the files from canvas saving to a new folder and then recompile and run "
                + "that code following steps 1 and 2 is truly the only way to confirm what you "
                + "have turned in failure to upload the proper file s for a homework will result "
                + "in a zero for the assignment programs that do not compile or run also receive "
                + "no credit party avl exam instructions all exams from exam 3 going forward will "
                + "be online through canvas signing and or taking this exam signifies you are "
                + "aware of and in accordance with the academic honor code of georgia tech and the "
                + "georgia tech code of conduct this exam is 50 minutes long you will be given a "
                + "24 hour window 12 am to 11 59 pm on exam day in which to take your exam canvas "
                + "will automatically close the exam when time is up be sure to record your "
                + "answers in canvas within the necessary time frame allow a full 50 minutes for "
                + "the exam you must begin your exam before 11 09 pm in order to finish on time "
                + "you are responsible for a working a machine and internet connection in order to "
                + "complete the exam this exam is open note you may use any external resource at "
                + "your disposal in taking this exam except for another person if you bring a duck "
                + "with you to the exam you may silently consult with it at any time all code must "
                + "be in java efficiency matters for example if you code something that uses o n "
                + "time or worse when there is an obvious way to do it in o 1 time your solution "
                + "may lose credit if your code traverses the data 5 times when once would be "
                + "sufficient then this also is considered poor efficiency even though both are o "
                + "n style standards such as but not limited to use of good variable names and "
                + "proper indentation is always required don’t fret too much if your paper gets "
                + "messy use arrows or whatever it takes to make your answer clear when necessary "
                + "comments are not required unless a question explicitly asks for them final exam "
                + "instructions in addition to all the above instructions the final exam is in "
                + "three parts for a total of 2 hours 50 minutes of test taking time each of the "
                + "three parts of the final exam is 1 hour in length you may or may not need the "
                + "full hour to complete each part the three parts must be completed in the order "
                + "they appear in the module there is a 24 hour window midnight to 11 59 pm on "
                + "final exam day in which to take your final exam canvas will automatically close "
                + "the exam when time is up be sure to enter your answers in canvas before it "
                + "closes allow a full 3 5 hours for your final exam 60 minutes for each part of "
                + "the final and transitioning between parts do not upload scratch paper according "
                + "to institute rules three exams in one day constitutes a class two conflict per "
                + "those guidelines http www catalog gatech edu rules 12 links to an external site "
                + "you are expected to notify your instructor and make alternative arrangements "
                + "two weeks before the beginning of exam week this notification window is "
                + "essential because faculty members have a very busy time during finals week and "
                + "alternate exam periods are not always easy to arrange at the last minute "
                + "additionally per the posted regulations when there is a class two conflict it "
                + "is always the middle exam period that is deemed to be in conflict and the "
                + "instructor of that course should be contacted with regard to alternative "
                + "arrangements if you make a request to the professor in the two weeks before "
                + "finals week it will be denied important dates all dates are tentative and "
                + "subject to change please read the new spring 2020 grading and policies on the "
                + "registrar s page here link links to an external site first lecture day january "
                + "6 2020 first recitation day january 7 2020 exam 1 february 5 2020 exam 2 march "
                + "4 2020 withdraw deadline may 2 2020 4pm exam 3 april 8 2020 last lecture day "
                + "april 20 2020 last recitation day april 21 2020 final section a april 24 2020 "
                + "testing window 12 01 am to 11 59 pm final section b april 29 2020 testing "
                + "window 12 01 am to 11 59 pm final section c april 30 2020 testing window 12 01 "
                + "am to 11 59 pm the final exam for section a b is at 8 00 am and this is the "
                + "first exam slot of the day it will not be necessary for anyone to reschedule "
                + "their final due to a class two conflict contact your other professors of the "
                + "second exam for rescheduling party avls created by adrianna brown party graphs "
                + "created by jacob allen course summary date details mon jan 13 2020 assignment "
                + "rc01 java due by 9 05am assignment rc02 imports statements due by 9 05am "
                + "assignment rc03 recursion review due by 9 05am assignment course syllabus "
                + "homework due by 11 55pm assignment homework01 arraylists due by 11 55pm wed jan "
                + "15 2020 assignment rc04 recursion arrays due by 9 05am assignment rc05 sll "
                + "empty sum due by 9 05am assignment rc06 sll remove due by 9 05am assignment "
                + "rc09 scenarios due by 9 55am fri jan 17 2020 assignment rc07 recursion "
                + "linkedlists due by 9 05am assignment rc08 dll due by 9 05am assignment "
                + "homework02 linkedlists due by 11 55pm fri jan 24 2020 assignment homework03 "
                + "stacks queues due by 11 55pm mon jan 27 2020 assignment rc10 stack due by 9 "
                + "05am assignment rc11 queue due by 9 05am assignment rc12 ll interview questions "
                + "due by 9 05am mon feb 3 2020 assignment rc13 bst traversals due by 9 05am "
                + "assignment rc14 bst operations due by 9 05am assignment rc15 bst diagram due by "
                + "9 05am assignment rc16 bst coding due by 9 05am assignment rc18 diagramming "
                + "heaps due by 10am mon feb 10 2020 assignment homework04 binary search trees due "
                + "by 11 55pm wed feb 12 2020 assignment rc19 efficiency heaps due by 9 05am fri "
                + "feb 14 2020 assignment homework05 heaps due by 11 55pm mon feb 17 2020 "
                + "assignment rc20 hashmaps due by 9 05am wed feb 19 2020 assignment rc17 skip "
                + "lists due by 9 05am fri feb 21 2020 assignment rc21 advanced hashing due by 9 "
                + "05am assignment rc22 hash heap review due by 9 05am assignment homework06 "
                + "hashmaps due by 11 55pm fri feb 28 2020 assignment rc23 big o review due by 9 "
                + "05am assignment rc24 tree review due by 9 05am wed mar 4 2020 assignment rc25 "
                + "tree interview questions due by 9 05am assignment rc26 2 4 trees due by 9 05am "
                + "assignment rc27 big o review up to 2 4 trees due by 9 05am mon mar 9 2020 "
                + "assignment homework07 avls due by 11 55pm thu mar 12 2020 calendar event "
                + "bluejeans meeting cs 1332 1 54pm to 2 20pm mon mar 30 2020 calendar event "
                + "bluejeans meeting sorting algorithm efficiencies 5 10pm to 5 25pm assignment "
                + "homework08 sorting algorithms update due by 11 55pm mon apr 6 2020 calendar "
                + "event bluejeans meeting office hours help 12 12pm to 12 20pm assignment "
                + "homework09 pattern matching update due by 11 55pm tue apr 7 2020 assignment "
                + "practice exam 3 due by 11 59pm wed apr 8 2020 assignment exam 3 100 points due "
                + "by 11 59pm mon apr 13 2020 assignment homework10 graph algorithms due by 11 "
                + "55pm tue apr 21 2020 assignment rc28 iterative sorting due by 11 55pm "
                + "assignment rc29 divide conquer sorting due by 11 55pm assignment rc30 sort "
                + "coding due by 11 55pm assignment rc31 boyer moore due by 11 55pm assignment "
                + "rc32 knuth morris pratt due by 11 55pm assignment rc33 rabin karp due by 11 "
                + "55pm assignment rc34 graph traversal due by 11 55pm assignment rc35 dijkstra s "
                + "shortest path due by 11 55pm assignment rc36 minimum spanning trees due by 11 "
                + "55pm assignment rc37 graph algorithm efficiencies due by 11 55pm assignment "
                + "rc38 longest common subsequence lcs due by 11 55pm assignment 4 1 realty check "
                + "due by 11 59pm assignment exam 1 assignment exam 2 assignment exam 3 scores";
        private String[] bfs = new String[] {
            "cs1332", "data", "3", "consists", "no", "ta", "visualization", "videos",
            "tab", "structures", "structure", "5", "0", "or", "a", "20", "going", "april",
            "2020", "due", "100", "scores", "of", "graded", "inappropriate", "dropped",
            "exam", "extra", "curve", "makeups", "makeup", "regrade", "love", "credit",
            "list", "info", "adrianna", "ckish3", "rpontes3", "dwang388", "jallen317",
            "help", "office", "for", "the", "will", "should", "is", "tool", "content",
            "are", "see", "slides", "look", "algorithms", "and", "used", "sorting",
            "algorithm", "times", "hours", "10pm", "25pm", "credits", "homework", "final",
            "if", "to", "last", "schedule", "better", "on", "8", "shared", "not", "gt",
            "veteran", "any", "offensive", "4", "homeworks", "higher", "reality", "time",
            "official", "other", "without", "down", "returned", "post", "under", "more",
            "run", "taking", "worse", "whatever", "may", "change", "lecture", "recitation",
            "c", "regular", "particular", "positive", "zero", "straight", "passing",
            "participation", "90", "career", "valid", "physician", "single", "brand",
            "thursday", "place", "traditional", "strict", "grade", "detailed", "personal",
            "pinned", "grace", "train", "high", "hw", "fresh", "new", "24", "full",
            "working", "machine", "duck", "question", "total", "class", "very", "request",
            "b", "d", "f", "forward", "29", "30", "21", "registration", "testing",
            "grading", "first", "withdraw", "4pm", "assignment", "calendar", "during",
            "date", "dates", "at", "by", "points", "two", "online", "students",
            "conditions", "disability", "race", "admissions", "conduct", "this", "student",
            "feedback", "an", "your", "each", "files", "multiple", "georgia", "good", "2",
            "test", "that", "work", "language", "recommended", "exams", "retakes",
            "average", "1", "we", "do", "alert", "grades", "must", "instructions",
            "signifies", "day", "canvas", "when", "you", "before", "except", "60", "week",
            "periods", "period", "slot", "in", "so", "policy", "requests", "ever",
            "consideration", "party", "brown", "gatech", "page", "desk", "course", "12",
            "bluejeans", "then", "mondays", "both", "one", "example", "smaller", "it",
            "which", "links", "messages", "misconduct", "missed", "attendance",
            "exceptions", "verification", "us", "free", "all", "another", "them",
            "section", "anyone", "rescheduling", "semester", "second", "learning",
            "professor", "5th", "tas", "confines", "ideal", "appropriate", "duration",
            "dean", "institute", "basis", "administration", "institutes", "classroom",
            "constraint", "employer", "exact", "event", "electronic", "assessment",
            "family", "signature", "excuse", "proper", "discretion", "integrity",
            "instructor", "dispute", "head", "deadline", "issue", "only", "3rd", "next",
            "college", "code", "top", "site", "piazza", "collaboration", "length",
            "following", "late", "latest", "conventions", "expected", "submission", "java",
            "file", "assignments", "academic", "necessary", "above", "three", "order",
            "module", "beginning", "posted", "middle", "registrar", "consist", "have",
            "be", "follow", "get", "inform", "assume", "receive", "give", "result",
            "override", "earn", "trip", "automatically", "now", "done", "associated",
            "via", "fine", "probably", "required", "against", "explicitly", "mandatory",
            "hereby", "physically", "made", "administered", "released", "justified",
            "unable", "needed", "taken", "junit", "always", "solely", "completely",
            "submitted", "truly", "50", "up", "open", "considered", "essential", "deemed",
            "can", "between", "saikrishna", "honor", "non", "provided", "strongly",
            "properly", "registered", "available", "responsible", "present", "roughly",
            "averaged", "rare", "absent", "requesting", "well", "correct", "welcome",
            "fully", "aware", "o", "tentative", "modules", "http", "become", "bubble",
            "brute", "dijkstra’s", "understand", "update", "cs1332r", "cs", "billing",
            "materials", "review", "provides", "regulation", "paid", "regulations",
            "goldwasser", "probing", "their", "debug", "activities", "nondiscrimination",
            "affirmative", "engaged", "they", "accurate", "discuss", "procedure",
            "professional", "cannot", "abide", "consult", "every", "resources", "number",
            "internet", "transitioning", "make", "alternate", "subject", "policies",
            "efficiencies", "imagine", "prerequisite", "programming", "being", "turn",
            "submissions", "information", "decision", "today", "register", "leave", "find",
            "11", "remain", "promote", "use", "attend", "contain", "having", "pass",
            "interviews", "submit", "consider", "stamps", "take", "accurately", "retake",
            "provide", "set", "resolve", "ensure", "read", "collaborate", "store",
            "include", "upload", "confirm", "record", "finish", "complete", "enter",
            "notify", "arrange", "alternative", "reschedule", "statement", "minute",
            "these", "topics", "letterhead", "how", "board", "checkstyle", "14", "using",
            "00", "anywhere", "hold", "count", "cost", "allowed", "violate",
            "discriminate", "come", "clear", "respectful", "directly", "endorse", "ask",
            "forgiving", "conscientious", "compile", "limited", "need", "email", "rules",
            "police", "status", "document", "absences", "conflicts", "accommodations",
            "exception", "discussion", "source", "part", "external", "trees", "weeks",
            "there", "removing", "checks", "as", "download", "frame", "communication",
            "documentation", "school", "additionally", "than", "circumstance",
            "professors", "approval", "through", "within", "skills", "details", "also",
            "off", "go", "share", "differ", "silently", "lose", "where", "please", "while",
            "whether", "separately", "does", "70", "classes", "scale", "fair", "excuses",
            "reason", "supervisor", "tbd", "thresholds", "cutoffs", "disputes", "contact",
            "private", "github", "likelihood", "reload", "copy", "folder", "spring",
            "hour", "with", "seriously", "busy", "results", "80", "like", "less",
            "requires", "window", "deadlines", "s", "programs", "rc01", "rc02", "rc03",
            "homework01", "rc04", "rc05", "rc06", "rc09", "rc07", "rc08", "homework02",
            "homework03", "rc10", "rc11", "rc12", "rc13", "rc14", "rc15", "rc16", "rc18",
            "homework04", "rc19", "homework05", "rc20", "rc17", "rc21", "rc22",
            "homework06", "rc23", "rc24", "rc25", "rc26", "rc27", "homework07",
            "homework08", "homework09", "practice", "homework10", "rc28", "rc29", "rc30",
            "rc31", "rc32", "rc33", "rc34", "rc35", "rc36", "rc37", "rc38", "finals",
            "csvistool", "goodrich", "coding", "jacob", "9", "10am", "were", "separate",
            "conflict", "teaching", "who", "allowing", "services", "sexual", "color",
            "term", "timeline", "notification", "anything", "faculty", "ide", "offline",
            "excused", "earlier", "obvious", "choice", "alarm", "illness", "performance",
            "knowledge", "gradescope", "university", "password", "laptop", "entire",
            "responsibility", "own", "most", "power", "answers", "disposal", "solution",
            "paper", "answer", "assignment’s", "containing", "from", "msts", "tech",
            "choices", "variable", "march", "20pm", "everyone", "has", "uses", "takes",
            "violations", "type", "textbook", "remains", "etc", "pm", "february", "54pm",
            "realty", "write", "qualify", "after", "check", "send", "personally", "begin",
            "but", "constitutes", "january", "turning", "saving", "signing", "once",
            "miss", "wake", "feel", "collaborated", "consulted", "wish", "bring",
            "minutes", "later", "until", "cs1331", "individual", "software", "advance",
            "accordance", "addition", "writing", "session", "person", "failure", "much",
            "applies", "library", "unless", "avl", "graphs", "avls", "edu", "here",
            "syllabus", "website", "management", "objectives", "letter", "summary", "01",
            "am", "12pm", "helpdesk", "meeting", "contacts", "recompile", "10", "groups",
            "says", "closes", "allows", "therefore", "announcements", "majors",
            "situations", "sections", "switching", "dr", "expects", "prior", "tracks",
            "edition", "officially", "governed", "community", "approved", "equal",
            "outside", "interview", "communications", "judicial", "accompanied",
            "procedures", "indentation", "those", "cover", "offer", "way", "publicly",
            "something", "traverses", "specified", "steps", "submitting", "listed",
            "deliverables", "6", "standard", "efficiency", "correctly", "link",
            "environment", "honesty", "comments", "parts", "been", "turned", "held",
            "found", "arranged", "given", "noted", "observing", "encompass", "referred",
            "changed", "notified", "discussed", "announced", "declined", "about", "marked",
            "penalized", "treated", "included", "sure", "sufficient", "completed",
            "contacted", "denied", "close", "based", "don’t", "individuals", "asks",
            "defined", "impossible", "visit", "i", "overrides", "easy", "throughout",
            "ill", "note", "intellectual", "poor", "because", "log", "billable",
            "discrimination", "compiling", "suggests", "encouraged", "problems",
            "together", "enough", "notation", "n", "www", "osi", "familiar", "insertion",
            "force", "shortest", "big", "1332", "ix", "2014", "low", "action", "appear",
            "occur", "member", "resource", "…", "connection", "appointments",
            "educational", "employment", "problem", "13", "promptly", "regraded",
            "lectures", "pdfs", "regarding", "yourself", "59", "55", "09", "55pm", "59pm",
            "professionalism", "junits", "arrows", "forgetting", "reflect", "fast",
            "scratch", "what", "arrangements", "bears", "else", "towards", "sitting",
            "intellij", "hudachek", "account", "stating", "sleeping", "chaining", "binary",
            "etc…", "apart", "rc", "vacations", "such", "soon", "allow", "functions",
            "per", "7", "effective", "mon", "immediately", "depending", "additional",
            "minimize", "recitations", "completing", "percentage", "why", "regrades",
            "repository", "enterprise", "common", "documented", "peers", "whom", "regard",
            "midnight", "ability", "imports", "recursion", "arraylists", "sll",
            "scenarios", "dll", "linkedlists", "stacks", "stack", "queue", "ll", "bst",
            "diagramming", "heaps", "hashmaps", "skip", "advanced", "hash", "tree",
            "pattern", "graph", "iterative", "divide", "sort", "boyer", "knuth", "rabin",
            "dijkstra", "minimum", "longest", "com", "tamassia", "allen", "05am", "55am",
            "lost", "courses", "assistants", "orientation", "religion", "expectations",
            "members", "absence", "malfunction", "death", "login", "past", "risk",
            "recent", "gets", "according", "pdf", "dynamic", "business", "names", "intact",
            "wednesdays", "would", "long", "development", "especially", "benefit",
            "assessments", "created", "caroline", "rodrigo", "david", "jump", "develop",
            "mary", "events", "opportunity", "travel", "questions", "military", "https",
            "guidelines", "e", "libraries", "matters", "even", "inside", "over", "into",
            "religious", "furthermore", "25", "important", "fret", "previous", "property",
            "weighted", "style", "catalog", "selection", "path", "r", "isbn", "level",
            "improve", "wed", "fri", "thu", "tue", "repeating", "including", "buswell",
            "cc", "violators", "search", "expect", "weddings", "days", "jan", "feb", "mar",
            "apr", "subsequence", "disabilities", "incapacitating", "statements", "arrays",
            "empty", "remove", "singular", "queues", "traversals", "operations", "diagram",
            "lists", "hashing", "maps", "heap", "matching", "traversal", "conquer",
            "moore", "morris", "karp", "spanning", "senior", "gender", "sex", "messy",
            "types", "kish", "pontes", "wang", "service", "studentlife", "g", "though",
            "holidays", "too", "standards", "cocktail", "a01", "9781118771334",
            "implementation", "graduations", "15", "17", "27", "19", "28", "lcs", "sum",
            "doubly", "deques", "various", "tables", "kmp", "pratt", "identity",
            "national", "shaker", "a06", "kindle", "errands", "circular", "priority",
            "origin", "merge", "age", "quick", "radix"};
        private String[] dfs = new String[] {
            "cs1332", "data", "structures", "algorithms", "professor", "dr", "mary",
            "hudachek", "buswell", "email", "for", "mondays", "10", "am", "1", "pm",
            "wednesdays", "i", "do", "not", "hold", "office", "bluejeans", "check",
            "piazza", "use", "in", "recitation", "separately", "section", "and", "cs1332r",
            "0", "credits", "you", "are", "to", "today", "register", "leave", "we",
            "follow", "all", "graded", "work", "is", "done", "based", "on", "lecture",
            "while", "recitations", "completing", "the", "first", "or", "last", "week",
            "of", "two", "separate", "courses", "conflict", "per", "those", "particular",
            "classes", "set", "your", "ta", "list", "at", "csvistool", "com", "links", "a",
            "change", "where", "students", "cannot", "attend", "only", "cover", "reality",
            "checks", "have", "this", "course", "syllabus", "jump", "assignments", "exams",
            "homeworks", "every", "member", "regrade", "request", "results", "will",
            "consist", "be", "held", "posted", "regulations", "when", "working", "time",
            "hours", "make", "appointments", "via", "gt", "rules", "three", "parts",
            "must", "provide", "documentation", "from", "11", "59", "00", "50", "minutes",
            "long", "55", "09", "55pm", "assignment", "deemed", "by", "goodrich",
            "tamassia", "coding", "their", "low", "level", "implementation", "arrays",
            "arraylists", "linkedlists", "singular", "doubly", "circular", "stacks",
            "queues", "deques", "priority", "various", "trees", "binary", "search", "avl",
            "heaps", "2", "4", "12", "01", "ide", "2020", "registration", "details",
            "effective", "spring", "see", "any", "other", "institute", "policy", "applies",
            "library", "gatech", "edu", "caroline", "kish", "head", "rodrigo", "pontes",
            "senior", "david", "wang", "jacob", "allen", "content", "honor", "code",
            "http", "canvas", "java", "6", "edition", "8", "checkstyle", "14", "junit",
            "test", "taking", "attendance", "off", "whether", "using", "gradescope",
            "login", "programming", "problem", "improve", "one", "s", "ability", "page",
            "adrianna", "brown", "party", "graphs", "assessments", "policies",
            "educational", "employment", "created", "avls", "due", "during", "finals",
            "date", "homework", "if", "necessary", "these", "accommodations", "classroom",
            "outside", "deliverables", "e", "g", "names", "types", "comments", "an",
            "external", "site", "non", "billable", "discrimination", "compiling",
            "conduct", "academic", "environment", "inside", "honesty", "misconduct",
            "specified", "chaining", "resources", "practice", "exam", "retakes", "there",
            "average", "3", "20", "c", "final", "decision", "regarding", "70", "april",
            "24", "total", "100", "points", "grade", "thresholds", "cutoffs", "scale",
            "percentage", "participation", "disputes", "regrades", "contact", "may",
            "take", "result", "go", "immediately", "so", "much", "benefit", "that", "they",
            "remain", "appear", "everyone", "has", "been", "submitted", "throughout",
            "after", "submitting", "properly", "registered", "problems", "with", "common",
            "subsequence", "lcs", "documented", "disabilities", "incapacitating",
            "illness", "death", "submit", "forgetting", "it", "says", "takes", "place",
            "tbd", "closes", "allow", "peers", "whom", "regard", "no", "inappropriate",
            "language", "dropped", "recommended", "textbook", "extra", "credit", "ever",
            "consideration", "curve", "makeups", "makeup", "day", "then", "inform",
            "contacts", "recompile", "without", "being", "aware", "returned", "through",
            "within", "7", "days", "promptly", "declined", "furthermore", "regraded",
            "approval", "but", "constitutes", "january", "love", "uses", "o", "notation",
            "n", "style", "standards", "such", "as", "vacations", "weddings",
            "graduations", "errands", "discussed", "above", "instructions", "soon",
            "grades", "feedback", "late", "review", "up", "ill", "share", "junits",
            "differ", "depending", "silently", "consult", "lose", "discussion", "were",
            "lost", "hour", "window", "midnight", "29", "30", "21", "d", "60", "like", "f",
            "less", "than", "going", "forward", "5", "times", "imagine", "10pm", "25pm",
            "scores", "dates", "alert", "signifies", "before", "except", "periods",
            "period", "until", "slot", "communication", "tool", "can", "log", "between",
            "professors", "saikrishna", "slides", "…", "resource", "exception", "please",
            "minimize", "absences", "read", "understand", "big", "excuse", "accompanied",
            "offline", "document", "excused", "absence", "issue", "earlier", "submission",
            "responsibility", "obvious", "way", "turn", "submissions", "announcements",
            "made", "solely", "information", "lectures", "pdfs", "here", "link",
            "shortest", "path", "13", "b", "80", "files", "containing", "required",
            "don’t", "fret", "too", "unless", "standard", "libraries", "efficiency",
            "matters", "even", "though", "both", "turning", "always", "overrides",
            "previous", "easy", "saving", "signing", "disability", "services", "sexual",
            "orientation", "gender", "identity", "www", "catalog", "osi", "publicly",
            "online", "videos", "following", "steps", "something", "traverses",
            "nondiscrimination", "does", "governed", "programs", "community", "approved",
            "appropriate", "official", "georgia", "tech", "business", "school",
            "functions", "additionally", "circumstance", "conflicts", "sleeping",
            "exceptions", "source", "part", "modules", "each", "file", "assignment’s",
            "pdf", "mon", "jan", "15", "17", "27", "feb", "19", "28", "mar", "9", "05am",
            "fri", "wed", "apr", "55am", "requires", "testing", "grading", "withdraw",
            "deadline", "4pm", "calendar", "event", "12pm", "20pm", "weeks", "apart",
            "expect", "march", "etc…", "hash", "maps", "tables", "heap", "10am",
            "deadlines", "class", "rc01", "rc02", "imports", "statements", "rc03",
            "recursion", "homework01", "rc04", "rc05", "sll", "empty", "sum", "remove",
            "rc06", "rc09", "scenarios", "rc07", "rc08", "dll", "homework02", "homework03",
            "rc10", "stack", "rc11", "queue", "rc12", "ll", "interview", "travel",
            "questions", "rc13", "bst", "traversals", "operations", "diagram", "rc14",
            "rc15", "rc16", "rc18", "diagramming", "homework04", "rc19", "homework05",
            "rc20", "hashmaps", "rc17", "skip", "lists", "rc21", "advanced", "hashing",
            "rc22", "homework06", "rc23", "rc24", "tree", "rc25", "rc26", "rc27",
            "homework07", "homework08", "sorting", "algorithm", "efficiencies",
            "homework09", "pattern", "matching", "update", "homework10", "graph",
            "traversal", "rc28", "iterative", "rc29", "divide", "conquer", "rc30", "sort",
            "rc31", "boyer", "moore", "kmp", "rabin", "karp", "rc32", "knuth", "morris",
            "pratt", "rc33", "rc34", "rc35", "dijkstra", "rc36", "minimum", "spanning",
            "rc37", "rc38", "longest", "thu", "tue", "59pm", "fast", "send", "personally",
            "write", "compile", "begin", "police", "learning", "objectives", "develop",
            "more", "skills", "sure", "alternative", "arrangements", "prerequisite",
            "help", "desk", "download", "frame", "once", "would", "found", "turned",
            "over", "into", "arranged", "used", "given", "absent", "noted", "observing",
            "religious", "holidays", "provided", "encompass", "referred", "allowed",
            "sitting", "changed", "notified", "announced", "considered", "intellectual",
            "property", "valid", "excuses", "reason", "why", "poor", "about", "shared",
            "anywhere", "else", "including", "marked", "penalized", "25", "treated",
            "included", "sufficient", "completed", "contacted", "denied", "important",
            "get", "assume", "receive", "give", "override", "earn", "trip",
            "automatically", "close", "requests", "student", "anything", "faculty",
            "expectations", "members", "integrity", "remains", "intact", "etc",
            "correctly", "website", "management", "letter", "should", "now", "summary",
            "term", "which", "allows", "us", "timeline", "also", "fine", "notification",
            "another", "person", "rc", "offer", "occur", "teaching", "assistants", "who",
            "complete", "additional", "regular", "basis", "positive", "respectful", "zero",
            "straight", "passing", "90", "career", "fair", "physician", "supervisor",
            "single", "brand", "new", "folder", "thursday", "traditional", "strict",
            "detailed", "personal", "private", "repository", "github", "enterprise",
            "https", "studentlife", "account", "violators", "pinned", "post", "grace",
            "train", "high", "likelihood", "hw", "reload", "fresh", "copy", "full",
            "machine", "duck", "question", "explicitly", "asks", "very", "seriously",
            "busy", "info", "ckish3", "rpontes3", "dwang388", "jallen317", "choice",
            "alarm", "malfunction", "performance", "knowledge", "university", "password",
            "laptop", "instructor", "entire", "own", "past", "risk", "most", "recent",
            "power", "answers", "disposal", "solution", "paper", "gets", "messy",
            "according", "answer", "clear", "guidelines", "conditions", "allowing", "good",
            "choices", "variable", "race", "color", "religion", "sex", "national",
            "origin", "age", "admissions", "multiple", "msts", "dynamic", "later",
            "statement", "bears", "repeating", "minute", "schedule", "better", "veteran",
            "status", "offensive", "higher", "removing", "down", "under", "run", "worse",
            "whatever", "semester", "switching", "sections", "second", "5th", "tas",
            "officially", "endorse", "intellij", "them", "confines", "ideal", "duration",
            "dean", "administration", "institutes", "equal", "opportunity", "constraint",
            "employer", "exact", "electronic", "communications", "assessment", "family",
            "judicial", "procedures", "military", "service", "signature", "proper",
            "indentation", "discretion", "dispute", "3rd", "next", "college", "top",
            "collaboration", "length", "latest", "conventions", "listed", "expected",
            "order", "module", "beginning", "middle", "registrar", "topics", "letterhead",
            "how", "board", "associated", "probably", "against", "individuals",
            "mandatory", "hereby", "defined", "physically", "impossible", "administered",
            "released", "justified", "visit", "unable", "needed", "taken", "completely",
            "truly", "open", "note", "essential", "because", "violations", "type",
            "majors", "situations", "find", "yourself", "having", "promote", "contain",
            "pass", "interviews", "consider", "stamps", "accurately", "reflect", "retake",
            "resolve", "ensure", "collaborate", "store", "include", "upload", "scratch",
            "confirm", "what", "record", "finish", "enter", "notify", "arrange",
            "reschedule", "strongly", "suggests", "encouraged", "available", "responsible",
            "present", "roughly", "averaged", "together", "weighted", "rare", "requesting",
            "well", "enough", "correct", "welcome", "fully", "tentative", "miss", "wake",
            "come", "feel", "collaborated", "consulted", "wish", "bring", "cs", "1332",
            "r", "a01", "a06", "billing", "materials", "provides", "regulation", "ix",
            "paid", "therefore", "goldwasser", "2014", "isbn", "9781118771334", "kindle",
            "probing", "debug", "activities", "affirmative", "action", "engaged",
            "accurate", "discuss", "procedure", "professional", "abide", "number",
            "internet", "connection", "transitioning", "alternate", "subject", "cs1331",
            "individual", "software", "development", "advance", "accordance", "addition",
            "writing", "especially", "session", "failure", "professionalism", "arrows",
            "helpdesk", "meeting", "count", "towards", "cost", "violate", "discriminate",
            "directly", "ask", "forgiving", "conscientious", "limited", "need", "qualify",
            "february", "54pm", "realty", "example", "smaller", "groups", "messages",
            "missed", "verification", "free", "anyone", "rescheduling", "stating", "cc",
            "expects", "prior", "tracks", "events", "become", "familiar", "bubble",
            "insertion", "selection", "cocktail", "shaker", "merge", "quick", "radix",
            "brute", "force", "dijkstra’s", "structure", "consists", "visualization",
            "tab", "look"};
        private String[] dijkstras = new String[] {
            "exception:9", "functions:11", "upload:6", "allowed:7", "shaker:18", "your:4",
            "scenarios:21", "laptop:6", "without:7", "via:5", "these:7", "properly:8",
            "approved:10", "would:5", "poor:16", "absent:6", "because:13", "shortest:10",
            "…:17", "scratch:7", "circular:18", "community:9", "exam:4", "0:5",
            "standards:13", "1:5", "2:5", "regraded:8", "3:5", "folder:6", "4:5",
            "applies:7", "moore:13", "selection:15", "5:5", "6:7", "7:9", "8:5",
            "professors:10", "9:7", "institute:9", "regrades:8", "much:14",
            "appropriate:11", "weeks:5", "registrar:9", "hashmaps:12", "turn:4", "10pm:8",
            "immediately:13", "posted:6", "example:7", "result:6", "avls:8", "spanning:20",
            "procedures:10", "after:5", "morris:18", "close:21", "policy:6", "minimize:10",
            "a:5", "b:5", "website:7", "c:5", "d:5", "e:23", "f:7", "g:23", "i:5", "n:5",
            "o:5", "the:3", "straight:12", "r:9", "s:5", "police:8", "fast:10",
            "timeline:8", "obvious:9", "days:12", "information:11", "returned:8",
            "discuss:7", "register:8", "thu:7", "standard:8", "instructions:12",
            "correct:7", "resolve:9", "incapacitating:14", "official:8", "recitations:15",
            "dijkstra:20", "good:6", "empty:19", "wish:4", "spring:6", "assessment:10",
            "post:6", "leave:7", "duck:8", "repeating:17", "finish:8", "signifies:9",
            "discrimination:14", "chaining:8", "kindle:32", "weighted:8", "violators:9",
            "assistants:10", "educational:11", "need:4", "check:5", "list:4",
            "arraylists:10", "external:8", "materials:9", "circumstance:12", "http:4",
            "therefore:9", "rpontes3:10", "dijkstra’s:8", "private:13", "54pm:8",
            "provided:8", "mondays:7", "sleeping:10", "remove:20", "arranged:10",
            "provides:8", "responsible:11", "ckish3:8", "approval:8", "realty:10",
            "multiple:8", "billable:8", "noted:7", "better:8", "with:4", "environment:11",
            "pdf:21", "service:13", "there:5", "well:4", "family:6", "age:25", "taking:6",
            "referred:10", "college:7", "career:6", "smaller:7", "catalog:9",
            "considered:10", "entire:6", "number:6", "property:16", "buswell:11", "per:13",
            "write:9", "order:5", "algorithm:7", "period:6", "testing:9", "understand:8",
            "makeups:7", "classroom:9", "graduations:17", "4pm:7", "ever:8",
            "thresholds:10", "likelihood:14", "even:16", "strongly:8", "binary:10",
            "etc…:10", "hash:10", "engaged:7", "algorithms:8", "other:5", "regrade:7",
            "against:9", "login:15", "mandatory:11", "valid:5", "top:3", "honesty:9",
            "bubble:12", "too:13", "have:4", "electronic:10", "promptly:8", "share:5",
            "cutoffs:7", "question:10", "discussed:11", "intellectual:12", "names:13",
            "regard:6", "retake:8", "constraint:10", "signing:7", "com:17", "honor:3",
            "arrows:6", "points:6", "wake:4", "earlier:7", "etc:7", "whether:7",
            "academic:8", "members:7", "employer:8", "links:5", "qualify:9",
            "verification:12", "all:3", "always:6", "new:3", "read:6", "including:17",
            "everyone:8", "constitutes:11", "less:10", "basis:5", "tool:16", "finals:6",
            "were:8", "improve:15", "supervisor:14", "university:10", "policies:8",
            "hudachek:10", "slot:4", "effective:9", "times:5", "reload:10", "credits:11",
            "and:3", "today:7", "extra:7", "busy:8", "working:7", "says:6", "lcs:19",
            "comments:10", "employment:10", "any:3", "minute:6", "exams:5", "conduct:7",
            "activities:10", "consists:2", "until:7", "semester:8", "objectives:10",
            "reason:6", "silently:8", "accurate:8", "tue:7", "compiling:9",
            "discussion:10", "anywhere:8", "rabin:17", "professional:12", "skills:6",
            "password:8", "integrity:9", "java:4", "jan:7", "notation:12", "merge:19",
            "arrangements:12", "welcome:7", "meeting:11", "pontes:14", "fully:5",
            "defined:9", "events:12", "encompass:11", "apr:7", "using:7",
            "appointments:12", "separately:10", "a01:11", "remain:6", "containing:10",
            "a06:11", "expected:8", "violations:10", "completed:11", "each:4", "cs1332r:7",
            "wang:12", "grade:5", "letter:6", "reality:7", "must:4", "choices:9",
            "probably:10", "another:7", "document:8", "automatically:13", "students:8",
            "two:3", "matching:12", "promote:9", "found:7", "excuse:6", "are:3", "does:14",
            "taken:7", "grace:9", "where:9", "expectations:12", "takes:5", "latest:6",
            "receive:7", "grading:7", "efficiencies:10", "rescheduling:12",
            "saikrishna:22", "structures:8", "services:12", "religion:10", "jallen317:11",
            "radix:19", "diagram:21", "such:8", "ask:3", "bluejeans:9", "through:7",
            "slides:0", "attend:6", "administration:14", "run:3", "remains:7",
            "justified:11", "source:6", "individuals:13", "unable:8", "forgetting:10",
            "late:4", "compile:7", "holidays:12", "school:8", "msts:12", "yourself:8",
            "has:5", "results:7", "those:5", "tech:10", "given:7", "courses:9",
            "imagine:7", "last:4", "develop:13", "worse:7", "whatever:10", "checks:8",
            "page:4", "assume:6", "full:4", "next:4", "junits:6", "submit:6", "color:7",
            "absence:9", "classes:7", "impossible:12", "edition:7", "penalized:11",
            "non:3", "brute:13", "anything:8", "not:3", "55am:10", "cocktail:16", "avl:9",
            "now:9", "sorting:11", "statement:9", "jacob:9", "boyer:13", "hours:5",
            "junit:5", "race:6", "traverses:9", "conquer:19", "libraries:9", "way:5",
            "endorse:7", "equal:15", "what:12", "furthermore:11", "detailed:12", "paid:4",
            "risk:6", "observing:11", "time:4", "window:6", "pratt:19", "assignments:11",
            "when:4", "three:5", "deliverables:12", "required:8", "billing:7", "doubly:16",
            "enter:7", "traversal:19", "truly:7", "give:4", "canvas:6", "having:8",
            "dates:5", "admissions:12", "deemed:8", "priority:16", "saving:6",
            "judicial:8", "provide:7", "institutes:10", "style:9", "completely:12",
            "csvistool:11", "requires:12", "participation:13", "submitting:10",
            "explicitly:12", "log:3", "enterprise:10", "pattern:11", "teaching:8", "low:7",
            "wed:7", "consult:7", "physician:13", "more:4", "tamassia:12", "tentative:9",
            "consulted:9", "assignment’s:12", "january:7", "choice:6", "travel:12",
            "board:7", "governed:10", "feb:7", "quick:19", "before:6", "disability:10",
            "section:7", "used:6", "deadlines:11", "notification:12", "georgia:7",
            "indentation:11", "percentage:10", "inappropriate:15", "consider:10", "day:3",
            "marked:8", "absences:8", "imports:19", "created:7", "minutes:9", "tree:16",
            "particular:10", "done:6", "solely:8", "exceptions:10", "both:4", "most:4",
            "important:11", "veteran:9", "instructor:10", "personally:10", "outside:11",
            "forgiving:9", "conventions:11", "59pm:6", "who:13", "rc04:16", "request:7",
            "rc03:16", "rc06:16", "rc05:16", "rc02:16", "part:4", "rc01:16", "their:5",
            "why:7", "collaborated:12", "rc08:16", "rc07:16", "rc09:16", "reschedule:12",
            "david:11", "debug:5", "assignment:10", "clear:5", "alternative:11",
            "lectures:14", "also:4", "enough:6", "gets:6", "differ:6", "recommended:13",
            "matters:13", "listed:16", "various:15", "earn:4", "uses:4", "visit:15",
            "denied:8", "account:7", "attendance:10", "rc26:16", "rc25:16", "kish:18",
            "been:4", "rc28:16", "stack:17", "rc27:16", "rc22:16", "rc21:16",
            "nondiscrimination:17", "rc24:16", "rc23:16", "bring:5", "videos:0",
            "intellij:8", "completing:14", "rc29:16", "advance:9", "offer:5", "path:14",
            "trip:4", "depending:9", "1332:6", "homework10:10", "record:8", "rc20:16",
            "strict:10", "you:3", "knowledge:9", "addition:10", "jump:12", "rc15:16",
            "letterhead:12", "rc14:16", "rc17:16", "sure:4", "rc16:16", "rc11:16",
            "pass:6", "going:7", "rc10:16", "past:6", "stacks:14", "rc13:16",
            "documentation:13", "rc12:16", "opportunity:21", "personal:12", "am:4",
            "accurately:12", "rc19:16", "an:4", "easy:8", "rc18:16", "senior:10", "as:6",
            "at:4", "submitted:9", "turning:7", "submissions:11", "vacations:13",
            "consideration:13", "dean:4", "average:7", "be:4", "how:5", "coding:10",
            "see:3", "search:10", "communications:14", "responsibility:14", "by:6",
            "whom:4", "national:20", "term:4", "override:8", "deadline:8", "cc:16",
            "rc37:16", "rc36:16", "set:5", "contain:9", "rc38:16", "business:14",
            "rc33:16", "rc32:16", "rc35:16", "sex:15", "rc34:16", "alternate:9",
            "thursday:12", "procedure:9", "familiar:14", "march:9", "cs:4", "answer:6",
            "rc31:16", "rc30:16", "under:7", "interview:9", "essential:11", "syllabus:8",
            "visualization:7", "55pm:6", "questions:9", "excused:9", "language:12", "do:4",
            "down:6", "dr:16", "hold:4", "faculty:7", "correctly:13", "later:5",
            "diagramming:23", "prior:13", "rare:4", "contact:7", "excuses:7",
            "programming:11", "info:6", "train:9", "which:5", "test:6", "count:5",
            "prerequisite:12", "20pm:6", "publicly:8", "brown:11", "regarding:9",
            "graph:15", "take:4", "final:5", "parts:5", "party:7", "frame:5",
            "beginning:9", "requesting:10", "session:9", "additional:14", "origin:22",
            "for:3", "content:1", "miss:4", "duration:8", "alert:5", "averaged:8",
            "divide:18", "confines:8", "class:5", "over:8", "homework09:10",
            "homework08:10", "midnight:8", "dll:17", "05am:10", "go:4", "length:6",
            "homework01:10", "homework03:10", "2020:6", "homework02:10", "homework05:10",
            "gt:4", "homework04:10", "homework07:10", "subsequence:11", "tracks:12",
            "homework06:10", "schedule:10", "transitioning:13", "management:10", "2014:16",
            "fresh:9", "withdraw:10", "very:8", "big:15", "practice:10", "advanced:20",
            "peers:5", "answers:7", "dropped:9", "collaborate:13", "soon:8", "hw:6",
            "tables:12", "else:12", "guidelines:10", "action:16", "https:15", "fri:7",
            "if:4", "misconduct:10", "issue:5", "in:4", "made:6", "is:4", "it:4",
            "being:5", "disputes:8", "removing:10", "ix:18", "accompanied:11",
            "traditional:15", "sll:17", "physically:12", "asks:18", "messages:8",
            "arrays:24", "begin:5", "wednesdays:12", "status:10", "become:12",
            "longest:19", "turned:6", "curve:7", "weddings:14", "skip:16", "grades:6",
            "disposal:8", "notify:8", "malfunction:11", "file:4", "library:7", "member:6",
            "periods:7", "graded:6", "ability:11", "affirmative:11", "together:8",
            "illness:7", "mar:7", "may:3", "within:6", "missed:6", "topics:8", "forward:9",
            "change:8", "5th:3", "overrides:9", "positive:12", "off:9", "respectful:10",
            "disabilities:12", "machine:11", "complete:10", "ll:18", "adrianna:8", "use:3",
            "subject:7", "hashing:21", "feel:4", "office:6", "while:9", "second:6",
            "that:4", "suggests:8", "download:8", "high:8", "homeworks:9", "solution:8",
            "goodrich:12", "fine:4", "find:4", "www:5", "than:6", "documented:10",
            "sufficient:12", "credit:6", "directly:8", "communication:13",
            "administered:14", "officially:10", "textbook:16", "ideal:5", "discretion:10",
            "3rd:3", "level:9", "occur:7", "caroline:14", "seriously:13", "sort:16",
            "steps:13", "helpdesk:10", "professor:9", "lists:17", "deques:14", "present:7",
            "announcements:13", "problems:12", "no:4", "code:4", "accommodations:14",
            "student:7", "scores:10", "head:4", "total:7", "hour:4", "of:4", "heap:10",
            "make:4", "included:10", "on:4", "allows:6", "probing:7", "assessments:13",
            "or:4", "structure:7", "heaps:11", "100:7", "declined:8", "goldwasser:10",
            "regulation:10", "due:5", "pdfs:18", "online:6", "pm:4", "sitting:7",
            "gradescope:10", "00:4", "rodrigo:13", "01:4", "inform:6", "about:5", "09:4",
            "registered:10", "trees:9", "cover:5", "dwang388:10", "reflect:15",
            "encouraged:10", "above:5", "recent:6", "brand:9", "they:4", "released:8",
            "recursion:21", "10:8", "11:4", "12:4", "based:7", "github:6", "13:8", "14:6",
            "15:8", "17:8", "karp:18", "module:6", "19:8", "them:4", "then:4",
            "something:9", "benefit:17", "rc:12", "except:6", "programs:8", "singular:14",
            "conditions:12", "20:6", "21:8", "switching:9", "shared:8", "religious:11",
            "professionalism:15", "24:4", "bst:17", "25:18", "27:8", "28:8", "29:8",
            "don’t:11", "sum:21", "fret:12", "long:12", "into:8", "offline:9", "majors:6",
            "unless:6", "recompile:9", "lecture:7", "free:4", "so:12", "30:8", "email:7",
            "apart:5", "situations:10", "calendar:10", "offensive:11", "middle:6",
            "decision:8", "necessary:9", "though:18", "accordance:12", "one:3", "store:7",
            "ta:4", "closes:8", "confirm:9", "single:10", "appear:6", "traversals:24",
            "collaboration:13", "associated:10", "to:4", "open:6", "contacts:8", "but:3",
            "makeup:6", "separate:8", "available:9", "rules:7", "inside:16",
            "interviews:12", "zero:8", "roughly:7", "details:7", "military:12",
            "dynamic:15", "50:4", "up:4", "10am:8", "conscientious:13", "us:4", "55:4",
            "treated:9", "ensure:8", "59:4", "maps:10", "this:4", "iterative:21",
            "please:8", "ide:5", "bears:13", "look:4", "modules:7", "sections:8",
            "announced:11", "9781118771334:25", "especially:12", "once:4", "failure:9",
            "desk:4", "60:6", "internet:8", "conflict:8", "higher:8", "changed:9",
            "allow:5", "pinned:10", "needed:8", "isbn:16", "proper:6", "update:12",
            "learning:8", "we:6", "expects:11", "graphs:8", "common:6", "throughout:10",
            "70:6", "conflicts:9", "every:5", "summary:7", "types:13", "previous:10",
            "mary:18", "queues:14", "80:6", "passing:11", "osi:5", "software:10", "held:6",
            "retakes:7", "during:6", "fair:8", "type:4", "repository:16", "april:5",
            "mon:7", "consist:7", "feedback:8", "problem:15", "review:6", "lost:8", "90:6",
            "connection:10", "between:19", "gatech:6", "efficiency:10", "work:4", "lose:4",
            "cs1332:0", "come:4", "cs1331:8", "intact:8", "anyone:6", "following:9",
            "exact:5", "registration:12", "force:13", "sexual:14", "love:6", "gender:24",
            "signature:9", "requests:8", "specified:9", "kmp:15", "identity:26", "get:5",
            "writing:9", "violate:7", "course:6", "power:5", "place:5", "copy:10",
            "event:5", "regular:11", "dispute:7", "include:9", "homework:8", "abide:5",
            "help:4", "expect:6", "knuth:17", "site:4", "regulations:11",
            "additionally:12", "cannot:6", "submission:10", "hereby:8", "minimum:19",
            "first:5", "queue:17", "date:4", "allen:9", "data:2", "own:5", "piazza:6",
            "tab:3", "arrange:9", "25pm:8", "only:4", "should:6", "from:4",
            "checkstyle:14", "linkedlists:11", "development:13", "individual:12",
            "resource:8", "like:8", "tas:3", "ill:5", "towards:7", "recitation:10",
            "messy:7", "errands:21", "person:8", "tbd:7", "edu:9", "files:5",
            "insertion:15", "send:4", "here:4", "note:6", "contacted:11", "week:4",
            "death:9", "link:4", "according:9", "scale:5", "12pm:6", "statements:22",
            "aware:5", "can:3", "operations:24", "paper:5", "alarm:5", "discriminate:12",
            "stamps:8", "stating:9", "orientation:19", "cost:4", "will:4", "limited:7",
            "notified:10", "february:12", "implementation:18", "studentlife:21",
            "groups:8", "resources:9", "allowing:14", "follow:6", "performance:11",
            "variable:10"};

        private Graph<String> graph;
        private Map<String, Vertex<String>> vertexMap;

        /**
         * Graph for syllabus
         */
        private SyllabusText() {
            String[] words = text.toLowerCase().split(" ");
            vertexMap = new HashMap<>();
            Set<Edge<String>> edgeSet = new LinkedHashSet<>();
            for (int x = 0; x < words.length; ++x) {
                if (!vertexMap.containsKey(words[x])) {
                    vertexMap.put(words[x], new Vertex<>(words[x]));
                }
                if (x > 0) {
                    Vertex<String> from = vertexMap.get(words[x - 1]);
                    Vertex<String> to = vertexMap.get(words[x]);
                    edgeSet.add(new Edge<>(from, to, Math.abs(words[x - 1].length() - words[x].length())));
                }
            }
            graph = new Graph<>(new HashSet<>(vertexMap.values()), edgeSet);
        }
    }
}
