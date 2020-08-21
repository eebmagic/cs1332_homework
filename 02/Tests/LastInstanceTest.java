public class LastInstanceTest {

    public static void main(String[] args) {
        CircularSinglyLinkedList<Integer> list = new CircularSinglyLinkedList<>();
        list.addToFront(5);
        list.addToFront(5);
        list.addToFront(5);
        list.addToFront(5);
        list.addToFront(5);
        list.addToFront(2);
        list.removeLastOccurrence(2);
        System.out.println(list.getHead().getData());
    }
}
