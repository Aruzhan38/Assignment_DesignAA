package ClosestPair;

public class ClosestPairTesting {
    public static void main(String[] args) {

        ClosestPair.Point[] points = {
                new ClosestPair.Point(2, 3),
                new ClosestPair.Point(12, 30),
                new ClosestPair.Point(40, 50),
                new ClosestPair.Point(5, 1),
                new ClosestPair.Point(12, 10),
                new ClosestPair.Point(3, 4)
        };

        double result = ClosestPair.findClosestPair(points);
        System.out.println("Closest distance = " + result);

        java.util.Random rand = new java.util.Random();
        ClosestPair.Point[] randomPoints = new ClosestPair.Point[10];
        for (int i = 0; i < 10; i++) {
            randomPoints[i] = new ClosestPair.Point(rand.nextInt(100), rand.nextInt(100));
        }
        double randomResult = ClosestPair.findClosestPair(randomPoints);
        System.out.println("Closest distance among 10 random points = " + randomResult);
    }
}
