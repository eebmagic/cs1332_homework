public class Test {
    public static void main(String[] args) {

       CircularSinglyLinkedList<Integer> list = new CircularSinglyLinkedList<>();
       list.addToFront(5);
       list.addToFront(3);
       list.addToFront(7);
       list.addToBack(2);
       list.addToBack(8);

       list.addAtIndex(3, 11);
       list.addAtIndex(5, 14);
       list.addAtIndex(0, 16);
       list.addAtIndex(list.size(), 18);


       System.out.println("\n~~~~~~~starting over~~~~~~~~~");
       list.clear();
       list.addToFront(2);
       list.addToBack(5);
       list.addToFront(3);
       list.addToFront(4);
       list.addToFront(2);
       System.out.println("Removing node now.");
       list.removeLastOccurrence(5);
       list.removeLastOccurrence(2);
       list.removeLastOccurrence(4);

	}	
}
