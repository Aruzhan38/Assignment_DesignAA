package DetermenisticSelect;
import java.util.Arrays;
import java.util.Random;

public class DeterministicSelectTest {
    private static final int TRIALS = 100;
    private static final int N_MIN = 1;
    private static final int N_MAX = 500;
    private static final int VAL_RANGE = 200;

    public static void main(String[] args) {
        Random rnd = new Random(42);

        for (int t = 1; t <= TRIALS; t++) {
            int n = rnd.nextInt(N_MAX - N_MIN + 1) + N_MIN;
            int[] a = new int[n];
            for (int i = 0; i < n; i++) a[i] = rnd.nextInt(VAL_RANGE) - VAL_RANGE / 2;

            if (n <= 10) {
                for (int k = 0; k < n; k++) checkOne(a, k);
            } else {
                for (int i = 0; i < 5; i++) {
                    int k = rnd.nextInt(n);
                    checkOne(a, k);
                }
            }
        }

        checkOne(new int[]{5}, 0);
        checkOne(new int[]{2,2,2,2,2}, 3);
        checkOne(new int[]{-3,-1,-1,0,7,7,9}, 0);
        checkOne(new int[]{-3,-1,-1,0,7,7,9}, 6);
        checkOne(new int[]{9,8,7,6,5,4,3,2,1}, 4);

        System.out.println("All deterministic-select tests passed");
    }

    private static void checkOne(int[] original, int k) {
        int[] a = Arrays.copyOf(original, original.length);
        int got = DeterministicSelect.select(a, k);

        int[] sorted = Arrays.copyOf(original, original.length);
        Arrays.sort(sorted);
        int expected = sorted[k];

        if (got != expected) {
            throw new AssertionError(String.format(
                    "Mismatch: k=%d, expected=%d, got=%d, original=%s",
                    k, expected, got, Arrays.toString(original)
            ));
        }
    }
}

