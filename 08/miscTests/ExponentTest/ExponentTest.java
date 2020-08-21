public class ExponentTest {
    public static void main(String[] args) {

        System.out.println("Hello World!");

        System.out.println(10 ^ 1);
        System.out.println(exponent(10, 1));
        System.out.println(exponent(10, 2));

        System.out.println("\nStarting tests:");
        int curr = 123;
        System.out.println(curr);
        int iterations = 3;
        for (int i = 0; i < iterations; i++) {
            int factor = exponent(10, (i + 1));
            
            int remain = (curr % factor) / (factor / 10);
            System.out.printf("%d - %d\n", i, remain);
        }

	}

    private static int exponent(int a, int b) {
        int out = 1;
        for (int i = 0; i < b; i++) {
            out *= a;
        }
        return out;
    }
}
