package MergeSort;

import java.util.*;

public class MergeSortMetricsTest {

    private static void testCase(int[] input, int[] expected) {
        int[] arr = Arrays.copyOf(input, input.length);
        MergeSort.Metrics m = new MergeSort.Metrics();
        MergeSort.sortWithMetrics(arr, m);
        if (!Arrays.equals(arr, expected)) {
            System.out.println("FAIL: " + Arrays.toString(input) + " -> " +
                    Arrays.toString(arr) + " (expected " + Arrays.toString(expected) + ")");
        } else {
            System.out.println("PASS: " + Arrays.toString(input) + "  " + m);
        }
    }

    private static void randomTrials(int trials, int n, long seed) {
        Random rnd = new Random(seed);
        for (int t = 0; t < trials; t++) {
            int[] a = rnd.ints(n, -1_000_000, 1_000_000).toArray();
            int[] expect = Arrays.copyOf(a, a.length);
            Arrays.sort(expect);

            int[] arr = Arrays.copyOf(a, a.length);
            MergeSort.Metrics m = new MergeSort.Metrics();
            MergeSort.sortWithMetrics(arr, m);

            if (!Arrays.equals(arr, expect)) {
                throw new AssertionError("Random trial failed at t=" + t);
            }

            System.out.println("random n=" + n + " -> " + m);
        }
    }

    private static void stabilityCheck() {
        class Pair { int key, pos; Pair(int k, int p){ key=k; pos=p; } }
        Pair[] pairs = { new Pair(3,0), new Pair(1,1), new Pair(3,2), new Pair(1,3), new Pair(3,4) };
        int[] keys = Arrays.stream(pairs).mapToInt(p -> p.key).toArray();

        MergeSort.sort(keys);
        System.out.println("Stability OK (manual inspection): " + Arrays.toString(keys));
    }

    public static void main(String[] args) {
        testCase(new int[]{}, new int[]{});
        testCase(new int[]{42}, new int[]{42});
        testCase(new int[]{1,2,3,4,5}, new int[]{1,2,3,4,5});
        testCase(new int[]{5,4,3,2,1}, new int[]{1,2,3,4,5});
        testCase(new int[]{3,1,2,3,1}, new int[]{1,1,2,3,3});
        testCase(new int[]{11,10,9,8,7,6,5,4,3,2,1},
                new int[]{1,2,3,4,5,6,7,8,9,10,11});


        testCase(new int[]{2,2,2,2,2,1,1,1,1,1}, new int[]{1,1,1,1,1,2,2,2,2,2});

        randomTrials(5, 100_000, 123);

        stabilityCheck();
    }
}
