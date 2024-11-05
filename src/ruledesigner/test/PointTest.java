package ruledesigner.test;

import ruledesigner.main.Point;

class PointTest {
    public static void main(String[] args) {
        Point p = new Point(1, -1);
        Point q = new Point(0, 0);
        Point r = new Point(1, 1);

        System.out.println(p.isAdjacent(q));
        System.out.println(p.isAdjacent(r));
    }
}