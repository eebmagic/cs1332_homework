import java.util.List;
import java.util.regex.Pattern;

public class Generic {
    public static void main(String[] args) {
        String longText = "This is a very long sentence about Ethan, yes it was in fact written by Ethan, but Ethan doesn't care what you think about Ethan writing this";
        String searchPattern = "Ethan";

        CharacterComparator comparator = new CharacterComparator();
        List<Integer> comparisons = PatternMatching.boyerMoore(searchPattern, longText, comparator);
        List<Integer> second = PatternMatching.kmp(searchPattern, longText, comparator);
        System.out.println(comparisons);
        System.out.println(second);
    }
}
