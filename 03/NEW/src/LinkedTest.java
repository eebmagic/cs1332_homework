public class LinkedTest {
    public static void main(String[] args) {
        LinkedStack<Integer> stack = new LinkedStack<Integer>();

        System.out.println(stack.size());
        stack.push(5);
        System.out.println(stack.size());
        stack.push(4);
        System.out.println(stack.size());
        stack.push(12);
        System.out.println(stack.size());
        stack.push(43);
        System.out.println(stack.size());

        System.out.println("");
        System.out.println(stack.peek());

        System.out.println("\nStarting removes:");
        System.out.println(stack.pop());
        System.out.println(stack.size());
        System.out.println(stack.pop());
        System.out.println(stack.size());
        System.out.println(stack.pop());
        System.out.println(stack.size());
        System.out.println(stack.pop());
        System.out.println(stack.size());
    }
}