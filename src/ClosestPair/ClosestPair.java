package ClosestPair;
import java.util.*;
class ClosestPair {

    static class Point {
        double x, y;
        Point(double x, double y) {
            this.x = x;
            this.y = y;
        }
    }

    public static double findClosestPair(Point[] points) {
        Point[] ptsByX = points.clone();
        Arrays.sort(ptsByX, Comparator.comparingDouble(p -> p.x));

        Point[] ptsByY = points.clone();
        Arrays.sort(ptsByY, Comparator.comparingDouble(p -> p.y));

        return closestUtil(ptsByX, ptsByY, points.length);
    }

    private static double closestUtil(Point[] ptsByX, Point[] ptsByY, int n) {
        if (n <= 3) {
            return bruteForce(ptsByX, n);
        }

        int mid = n / 2;
        Point midPoint = ptsByX[mid];

        List<Point> leftY = new ArrayList<>();
        List<Point> rightY = new ArrayList<>();
        for (Point p : ptsByY) {
            if (p.x <= midPoint.x) leftY.add(p);
            else rightY.add(p);
        }

        double dl = closestUtil(Arrays.copyOfRange(ptsByX, 0, mid),
                leftY.toArray(new Point[0]), mid);
        double dr = closestUtil(Arrays.copyOfRange(ptsByX, mid, n),
                rightY.toArray(new Point[0]), n - mid);

        double d = Math.min(dl, dr);

        List<Point> strip = new ArrayList<>();
        for (Point p : ptsByY) {
            if (Math.abs(p.x - midPoint.x) < d) {
                strip.add(p);
            }
        }

        return Math.min(d, stripClosest(strip, d));
    }

    private static double bruteForce(Point[] pts, int n) {
        double min = Double.MAX_VALUE;
        for (int i = 0; i < n; ++i) {
            for (int j = i + 1; j < n; ++j) {
                min = Math.min(min, dist(pts[i], pts[j]));
            }
        }
        return min;
    }

    private static double stripClosest(List<Point> strip, double d) {
        double min = d;
        for (int i = 0; i < strip.size(); ++i) {
            for (int j = i + 1; j < strip.size() && (strip.get(j).y - strip.get(i).y) < min; ++j) {
                min = Math.min(min, dist(strip.get(i), strip.get(j)));
            }
        }
        return min;
    }

    private static double dist(Point p1, Point p2) {
        return Math.hypot(p1.x - p2.x, p1.y - p2.y);
    }
}
