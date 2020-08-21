public class ResizeTest {
    public static void main(String[] args) {
        // LinkedListQueue<Integer> q = new LinkedListQueue<>();
        ArrayQueue<Integer> q = new ArrayQueue<>();

        q.enqueue(5);
        q.enqueue(11);
        q.enqueue(15);
        q.enqueue(22);
        q.enqueue(45);

        System.out.println("");
        System.out.println(q.peek());

        //=============================================//
        System.out.println("\nStarting removes:");
        int removed;

        removed = q.dequeue();
        System.out.println(removed);

        removed = q.dequeue();
        System.out.println(removed);

        removed = q.dequeue();
        System.out.println(removed);

        removed = q.dequeue();
        System.out.println(removed);
    }
}