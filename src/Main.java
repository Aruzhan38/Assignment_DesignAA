public class Main {
    public static void main(String[] args) {
        ClosestPair.Point[] pts = {
                new ClosestPair.Point(2, 3), new ClosestPair.Point(12, 30),
                new ClosestPair.Point(40, 50), new ClosestPair.Point(5, 1),
                new ClosestPair.Point(12, 10), new ClosestPair.Point(3, 4)
        };
        double closestDistance = ClosestPair.closest(pts);
        System.out.println("Closest distance for example points = " + closestDistance);

        ClosestPairTesting.runTests();
    }
}