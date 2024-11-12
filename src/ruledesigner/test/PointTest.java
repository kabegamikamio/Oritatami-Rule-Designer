package ruledesigner.test;

import org.junit.Test;
import ruledesigner.main.Point;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

class PointTest {
    public static void main(String[] args) {
        Point p = new Point(1, -1);
        Point q = new Point(0, 0);
        Point r = new Point(1, 1);

        System.out.println(p.isAdjacent(q));
        System.out.println(p.isAdjacent(r));
    }

    @Test
    public void getXTest() {
        Point p = new Point(1, -1);
        assertTrue(p.getX() == 1);
    }

    @Test
    public void getYTest() {
        Point p = new Point(1, -1);
        assertTrue(p.getY() == -1);
    }

    @Test
    public void setXTest() {
        Point p = new Point(1, -1);
        p.setX(2);
        assertTrue(p.getX() == 2);
    }

    @Test
    public void setYTest() {
        Point p = new Point(1, -1);
        p.setY(2);
        assertTrue(p.getY() == 2);
    }

    @Test
    public void setAllTest() {
        Point p = new Point(1, -1);
        p.setAll(2, 2);
        assertTrue(p.getX() == 2);
        assertTrue(p.getY() == 2);
    }

    @Test
    public void isSameTest_same() {
        Point p = new Point(1, -1);
        Point q = new Point(1, -1);
        assertTrue(p.isSame(q));
        assertTrue(q.isSame(p));
    }

    @Test
    public void isSameTest_different() {
        Point p = new Point(1, -1);
        Point q = new Point(1, -2);
        assertFalse(p.isSame(q));
        assertFalse(q.isSame(p));
    }

    // This method may use @ParameterizedTest of JUnit5.
    @Test
    public void isAdjacentTest() {}

    // This one may use @ParameterizedTest too.
    @Test
    public void isIn() {}

    // This one, @ParameterizedTest, too.
    @Test
    public void getAdjacentOfTest() {}
}