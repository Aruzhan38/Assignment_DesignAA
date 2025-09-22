import java.util.Random;
public class ClosestPairTesting {

    public static double bruteForceTest(ClosestPair.Point[] pts) {
        double min = Double.POSITIVE_INFINITY;
        for (int i = 0; i < pts.length; i++) {
            for (int j = i + 1; j < pts.length; j++) {
                min = Math.min(min, dist(pts[i], pts[j]));
            }
        }
        return min;
    }

    private static double dist(ClosestPair.Point a, ClosestPair.Point b) {
        double dx = a.x - b.x, dy = a.y - b.y;
        return Math.sqrt(dx * dx + dy * dy);
    }

    public static void runTests() {
        System.out.println("Running ClosestPair tests...");
        Random rand = new Random();

        for (int n = 10; n <= 2000; n += 100) {
            ClosestPair.Point[] pts = new ClosestPair.Point[n];
            for (int i = 0; i < n; i++) {
                pts[i] = new ClosestPair.Point(rand.nextDouble() * 1000, rand.nextDouble() * 1000);
            }
            double resultFast = ClosestPair.closest(pts);
            double resultSlow = bruteForceTest(pts);
            if (Math.abs(resultFast - resultSlow) > 1e-9) {
                System.out.println("Error: Mismatch for n = " + n);
                return;
            }
        }
        System.out.println("Tests on small datasets (up to 2000) passed successfully.");

        for (int n = 2000; n <= 200000; n += 10000) {
            ClosestPair.Point[] pts = new ClosestPair.Point[n];
            for (int i = 0; i < n; i++) {
                pts[i] = new ClosestPair.Point(rand.nextDouble() * 1000, rand.nextDouble() * 1000);
            }
            long startTime = System.nanoTime();
            ClosestPair.closest(pts);
            long endTime = System.nanoTime();
            System.out.printf("Fast version for n = %d took %.2f ms\n", n, (endTime - startTime) / 1e6);
        }
    }
}