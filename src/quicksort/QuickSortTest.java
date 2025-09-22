import java.util.Arrays;
import java.util.concurrent.ThreadLocalRandom;

public class QuickSortTest {
    public static void main(String[] args) {
        testRandom();
        testSorted();
        testReverse();
        testDuplicates();
        testDepth();
        System.out.println("All self-tests passed ");
    }

    private static void testRandom() {
        for (int n : new int[]{0,1,2,5,10,100,1000}) {
            int[] a = ThreadLocalRandom.current().ints(n, -1000, 1000).toArray();
            int[] b = a.clone(); Arrays.sort(b);
            QuickSort.Metrics m = new QuickSort.Metrics();
            QuickSort.sort(a, m);
            if (!Arrays.equals(a, b)) throw new AssertionError("random n=" + n);
        }
    }

    private static void testSorted() {
        int[] a = new int[2000];
        for (int i = 0; i < a.length; i++) a[i] = i;
        int[] b = a.clone(); Arrays.sort(b);
        QuickSort.Metrics m = new QuickSort.Metrics();
        QuickSort.sort(a, m);
        if (!Arrays.equals(a, b)) throw new AssertionError("already sorted");
    }

    private static void testReverse() {
        int[] a = new int[2000];
        for (int i = 0; i < a.length; i++) a[i] = a.length - i;
        int[] b = a.clone(); Arrays.sort(b);
        QuickSort.Metrics m = new QuickSort.Metrics();
        QuickSort.sort(a, m);
        if (!Arrays.equals(a, b)) throw new AssertionError("reverse");
    }

    private static void testDuplicates() {
        int[] a = new int[5000];
        for (int i = 0; i < a.length; i++) a[i] = i % 7;
        int[] b = a.clone(); Arrays.sort(b);
        QuickSort.Metrics m = new QuickSort.Metrics();
        QuickSort.sort(a, m);
        if (!Arrays.equals(a, b)) throw new AssertionError("duplicates");
    }

    private static void testDepth() {
        for (int n : new int[]{256, 1024, 4096, 16384}) {
            int[] a = ThreadLocalRandom.current().ints(n, -1_000_000, 1_000_000).toArray();
            QuickSort.Metrics m = new QuickSort.Metrics();
            QuickSort.sort(a, m);
            if (!QuickSort.isSorted(a)) throw new AssertionError("depth-sort fail");
            int log2 = 31 - Integer.numberOfLeadingZeros(n);
            int bound = 4 * log2; // мягкая граница для «типично O(log n)»
            if (m.maxDepth > bound)
                throw new AssertionError("depth=" + m.maxDepth + " bound=" + bound + " n=" + n);
        }
    }
}