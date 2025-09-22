import java.util.concurrent.ThreadLocalRandom;

public final class QuickSort {

    private static final int CUTOFF = 16;

    public static final class Metrics {
        public long comparisons;
        public long swaps;
        public long timeNanos;
        public int  maxDepth;
        @Override public String toString() {
            return "Metrics{comparisons=" + comparisons +
                    ", swaps=" + swaps +
                    ", maxDepth=" + maxDepth +
                    ", timeNanos=" + timeNanos + "}";
        }
    }

    public static void sort(int[] a, Metrics m) {
        if (a == null || a.length < 2) return;
        if (m == null) m = new Metrics();
        long t0 = System.nanoTime();
        quicksort(a, 0, a.length - 1, 0, m);
        m.timeNanos += System.nanoTime() - t0;
    }

    public static void sort(int[] a) { sort(a, new Metrics()); }

    private static void quicksort(int[] a, int lo, int hi, int depth, Metrics m) {
        while (lo < hi) {
            if (hi - lo + 1 <= CUTOFF) {
                insertion(a, lo, hi, m);
                return;
            }

            int pivotIdx = ThreadLocalRandom.current().nextInt(lo, hi + 1);
            swap(a, pivotIdx, hi, m);

            int p = partitionLomuto(a, lo, hi, m);

            if (depth > m.maxDepth) m.maxDepth = depth;

            int leftSize  = p - lo;
            int rightSize = hi - p;

            if (leftSize < rightSize) {
                if (lo < p - 1) quicksort(a, lo, p - 1, depth + 1, m);
                lo = p + 1;
            } else {
                if (p + 1 < hi) quicksort(a, p + 1, hi, depth + 1, m);
                hi = p - 1;
            }
        }
    }

    private static int partitionLomuto(int[] a, int lo, int hi, Metrics m) {
        int pivot = a[hi];
        int i = lo;
        for (int j = lo; j < hi; j++) {
            m.comparisons++;
            if (a[j] <= pivot) { swap(a, i, j, m); i++; }
        }
        swap(a, i, hi, m);
        return i;
    }

    private static void insertion(int[] a, int lo, int hi, Metrics m) {
        for (int i = lo + 1; i <= hi; i++) {
            int key = a[i];
            int j = i - 1;
            while (j >= lo) {
                m.comparisons++;
                if (a[j] > key) { a[j + 1] = a[j]; j--; }
                else break;
            }
            a[j + 1] = key;
        }
    }

    private static void swap(int[] a, int i, int j, Metrics m) {
        if (i == j) return;
        int t = a[i]; a[i] = a[j]; a[j] = t;
        if (m != null) m.swaps++;
    }

    public static boolean isSorted(int[] a) {
        for (int i = 1; i < a.length; i++) if (a[i - 1] > a[i]) return false;
        return true;
    }
}