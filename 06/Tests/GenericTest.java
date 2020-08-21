import static org.junit.Assert.assertNull;

public class GenericTest {
    public static void main(String[] args) {
        ExternalChainingHashMap<Integer, String> map;
        map = new ExternalChainingHashMap<>();

        map.put(0, "A");
        map.put(1, "B");
        map.put(2, "C");
        map.put(3, "D");
        map.put(4, "E");
        map.put(15, "Secondary");
        map.put(15 + 13, "Tertiary");
        System.out.println(map);
//        map.remove(2); // should remove (2, "C")
//        map.remove(15); // should remove "Secondary"
        map.remove(28); // should remove "Tertiary"

        System.out.println(map);
    }
}
