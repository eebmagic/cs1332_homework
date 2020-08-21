import java.util.ArrayList;

public class GenericTest {

    public static void printThrough(Comparable[] inputArr) {
        System.out.print("[");
        for (int i = 0; i < inputArr.length; i++) {
            System.out.print(inputArr[i]);
            if (i != inputArr.length - 1) {
                System.out.print(", ");
            }
        }
        System.out.println("]");
    }


    public static void main(String[] args) {
        MaxHeap<Integer> heap = new MaxHeap<>();

        heap.add(5);
        heap.add(4);
        heap.add(3);
        heap.add(2);

        printThrough(heap.getBackingArray());

        heap.add(6);
        printThrough(heap.getBackingArray());

        heap.add(7);
        printThrough(heap.getBackingArray());

//        heap.add(4);
//        printThrough(heap.getBackingArray());

        heap.remove();
        printThrough(heap.getBackingArray());

        System.out.println("\n\nStarting data built heap tests");
        ArrayList<Integer> dataList = new ArrayList<>();
        dataList.add(2);
        dataList.add(1);
        dataList.add(5);
        dataList.add(4);
        dataList.add(3);


        heap = new MaxHeap<Integer>(dataList);
        printThrough(heap.getBackingArray());

        System.out.println(heap.remove());
        printThrough(heap.getBackingArray());
    }
}
