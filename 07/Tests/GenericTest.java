public class GenericTest {
    public static void main(String[] args) {
        AVL<Integer> tree = new AVL<>();
//        tree.add(6);
//        tree.add(3);
//        tree.add(8);
//        tree.add(4);
////        tree.add(8);
//        System.out.println(tree.size());
//
//        tree.add(5);    // rebalance should occur here
//        System.out.println(tree.size());
//
//        tree.remove(4);
//        System.out.println(tree.size());

        tree.add(5);
        tree.add(7);
        tree.add(3);
        tree.add(2);
        tree.add(6);
        tree.add(1);
        tree.add(0);
        System.out.println(tree.size());

        tree.remove(3);
        System.out.println(tree.size());

        System.out.println(tree.elementsWithinDistance(2, 1));
    }
}
