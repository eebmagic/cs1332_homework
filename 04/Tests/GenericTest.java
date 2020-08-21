import java.util.ArrayList;
import java.util.List;

public class GenericTest {
    public static void main(String[] args) {
        System.out.println("TEST");
        BST<Integer> tree;
        tree = new BST<Integer>();

        tree.add(1);
        tree.add(0);
        tree.add(2);
        tree.add(3);
        tree.add(4);
        System.out.println(5 == tree.size());

        Integer falseFound = tree.get(6);
        System.out.println(falseFound);

//        tree.remove(6);


//        System.out.println("\nstarting string tree");
//        List<String> data = new ArrayList<>();
//        data.add("this");
//        data.add("is");
//        data.add("a");
//        data.add("test");
//        BST<String> strTree = new BST<String>(data);
//        System.out.println(strTree.size());
//        System.out.println(strTree.getRoot());
//
//        System.out.println("\n\nStarting Empty Tree Tests:");
//        BST<Integer> emptyTree = new BST<>();
//        System.out.println(emptyTree.levelorder());
//        System.out.println(emptyTree.getMaxDataPerLevel());
    }
}
