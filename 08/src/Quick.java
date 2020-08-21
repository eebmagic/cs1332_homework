import java.util.Random;

public class Quick {
    private static void printThrough(int[] inputArr) {
        System.out.print("[");
        for (int i = 0; i < inputArr.length - 1; i++) {
            System.out.printf("%d, ", inputArr[i]);
        }
        System.out.printf("%d]\n", inputArr[inputArr.length - 1]);
    }


    public static void Quick(int[] arr) {
        QuickHelp(arr, 0, arr.length - 1);
        System.out.print("FINAL FINISH: ");
        printThrough(arr);
    }

    private static void QuickHelp(int[] arr, int start, int end) {
        if (start >= end) {
            System.out.printf("ending because %d >= %d\n", start, end);
            return;
        } else {
            System.out.println("\nbefore:");
            printThrough(arr);
            System.out.printf("start: %d\n", start);
            System.out.printf("end: %d\n", end);
        }

        Random rand = new Random();

        // make a random pivot
        int pivotIndex = rand.nextInt(arr.length);

        System.out.printf("arr[%d] = %d\n", pivotIndex, arr[pivotIndex]);

        // swap pivot value with start value
        int old = arr[pivotIndex];
        arr[pivotIndex] = arr[start];
        arr[start] = old;

        // print after init swap
        printThrough(arr);

        // init i and j
        int i = start + 1; // (right after the new pivot spot)
        int j = end;
        while (i <= j) {
            arr[i] += 0;
            arr[j] += 0;
            arr[start] += 0;

            if (!(arr[i] > arr[start])) {
                i++;
            }
            if (!(arr[j] < arr[start])) {
                j--;
            }



            if (arr[i] > arr[start] && arr[j] < arr[start]) {
                // swap at i and j
                int tmp = arr[i];
                arr[i] = arr[j];
                arr[j] = tmp;

                // move i and j in
                i++;
                j--;

                printThrough(arr);
            } else {
                if (arr[i] >= arr[start]) {
                    i++;
                }
                
                if (arr[j] <= arr[start]) {
                    j--;
                }
            }
        }

        int finaltmp = arr[start];
        arr[start] = arr[i + 1];
        arr[i + 1] = finaltmp;

        System.out.printf("finished: i = %d  j = %d\n", i, j);
        System.out.println("after:");
        printThrough(arr);

        // run recursive call
        QuickHelp(arr, start + 1, j - 1);   // left side
        QuickHelp(arr, j + 1, end);     // right side

    }



    public static void main(String[] args) {

        int[] arr = new int[]{5, 2, 53, 7, 1, 23, 3};
        
        // System.out.println("before:");
        // printThrough(arr);
        
        Quick(arr);

        // System.out.println("after:");
        // printThrough(arr);

    }
}