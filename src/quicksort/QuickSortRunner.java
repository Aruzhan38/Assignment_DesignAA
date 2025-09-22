import java.util.concurrent.ThreadLocalRandom;

public class QuickSortRunner {
    public static void main(String[] args) {
        System.out.println("algo,n,timeMs,depth,comparisons,swaps,seed");
        int[] Ns = {1<<10, 1<<12, 1<<14, 1<<16, 1<<18};

        for (int n : Ns) {
            for (int trial = 0; trial < 5; trial++) {
                long seed = ThreadLocalRandom.current().nextLong();
                int[] a = ThreadLocalRandom.current().ints(n, -1_000_000, 1_000_000).toArray();

                QuickSort.Metrics m = new QuickSort.Metrics();
                QuickSort.sort(a, m);

                double ms = m.timeNanos / 1e6;
                System.out.printf("quicksort,%d,%.3f,%d,%d,%d,%d%n",
                        n, ms, m.maxDepth, m.comparisons, m.swaps, seed);
            }
        }
    }
}