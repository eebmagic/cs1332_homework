public class Test {
    public static void main(String[] args) {
        ArrayList<Integer> arr = new ArrayList<>();
        arr.printThrough();
        arr.addToBack(4);
        arr.addToBack(3);
        arr.addToBack(6);
        arr.addToBack(4);
        arr.addToBack(3);
        arr.addToBack(6);
        arr.addToBack(4);
        arr.addToBack(3);
        arr.addToBack(6);
        arr.addToBack(5);
        arr.printThrough();

        arr.addAtIndex(1, 11);
        arr.addAtIndex(9, 12);
        arr.printThrough();
        arr.addAtIndex(0, 13);
        arr.addAtIndex(10, 14);
        arr.addAtIndex(14, 15);
        arr.addAtIndex(0, 16);
        arr.addToBack(15);
        arr.printThrough();

        System.out.println("--------");
        arr.addToFront(123);
        arr.addToFront(456);
        arr.addToFront(789);
        arr.printThrough();
    }
}
