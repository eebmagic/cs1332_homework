public class ReorderTest {
    public static void main(String[] args) {
        ArrayQueue<String> q = new ArrayQueue<>();
        // with starting size of 4
        q.enqueue("a");
        q.enqueue("b");
        q.enqueue("c");
        q.enqueue("d");

        q.dequeue();
        q.dequeue();

        q.enqueue("e");
        q.enqueue("f");
        // at this point: [e, f, c, d]
        q.enqueue("g");
        // should copy and resize: [c, d, e, f, g, null, null, null]
    }
}