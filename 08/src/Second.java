import java.util.Random;

public class Second {
    private static void printThrough(int[] inputArr) {
        System.out.print("[");
        for (int i = 0; i < inputArr.length - 1; i++) {
            System.out.printf("%d, ", inputArr[i]);
        }
        System.out.printf("%d]\n", inputArr[inputArr.length - 1]);
    }

    public static void Quick(int[] arr) {
        QuickHelp(arr, 0, arr.length - 1);
//        System.out.print("FINAL FINISH: ");
//        printThrough(arr);
    }

    private static void QuickHelp(int[] arr, int start, int end) {
        if (start >= end - 1) {
            return;
        }
//        else {
//            System.out.printf("\nstart: %d\n", start);
//            System.out.printf("end: %d\n", end);
//        }

//        System.out.print("Before: ");
//        printThrough(arr);

        Random rand = new Random();
        int pivotIndex = start + rand.nextInt(end - start + 1);

//        System.out.printf("pivot: arr[%d] = %d\n", pivotIndex, arr[pivotIndex]);

        // swap pivot value with start
        int tmp = arr[start];
        arr[start] = arr[pivotIndex];
        arr[pivotIndex] = tmp;

        int i = start + 1;
        int j = end;

        while (i < j) {
            while (i <= j && arr[i] < arr[start]) {
                i++;
            }
            while (j >= i && arr[j] > arr[start]) {
                j--;
            }

            if (i <= j) {
                tmp = arr[i];
                arr[i] = arr[j];
                arr[j] = tmp;
            } else {
                tmp = arr[start];
                arr[start] = arr[j];
                arr[j] = tmp;
            }
        }

//        System.out.println(i);
//        System.out.println(j);
//
//        System.out.print("After: ");
//        printThrough(arr);

        QuickHelp(arr, start, j);
        QuickHelp(arr, j+2, end);
    }

}
