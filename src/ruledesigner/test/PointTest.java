package ruledesigner.test;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import ruledesigner.main.Point;

class PointTest {
    @Test
    public void getXTest() {
        Point p = new Point(1, -1);
        assertEquals(1, p.getX());
    }

    @Test
    public void getYTest() {
        Point p = new Point(1, -1);
        assertEquals(-1, p.getY());
    }

    @Test
    public void setXTest() {
        Point p = new Point(1, -1);
        p.setX(2);
        assertEquals(2, p.getX());
    }

    @Test
    public void setYTest() {
        Point p = new Point(1, -1);
        p.setY(2);
        assertEquals(2, p.getY());
    }

    @Test
    public void setAllTest() {
        Point p = new Point(1, -1);
        p.setAll(2, 2);
        assertEquals(2, p.getX());
        assertEquals(2, p.getY());
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
    @ParameterizedTest
    @ValueSource(ints = {0, 1, 2, 3, 4, 5})
    public void isAdjacentTest_true(int index) {
        Point point = new Point(0, 0);
        Point adjacent = Point.adjacentCord[index];
        assertTrue(point.isAdjacent(adjacent));
        assertTrue(adjacent.isAdjacent(point));
    }

    @ParameterizedTest
    @CsvSource({
            "1, 1",
            "-1, -1",
            "2, 0",
            "0, -2"
    })
    public void isAdjacentTest_false(int x, int y) {
        Point point1 = new Point(0, 0);
        Point point2 = new Point(x, y);
        assertFalse(point1.isAdjacent(point2));
        assertFalse(point2.isAdjacent(point1));
    }

    @ParameterizedTest
    @CsvSource({
            "1, 1, true",
            "2, 2, true",
            "-3, 2, true",
            "0, 0, false"
    })
    public void isIn(int x, int y, boolean flag) {
        Point point = new Point(x, y);
        Point[] points = {new Point(1, 1), new Point(2, 2), new Point(-3, 2)};
        assertEquals(point.isIn(points), flag);
    }

    @ParameterizedTest
    @CsvSource({
            "0, 2, 4",
            "1, 3, 3",
            "2, 3, 2",
            "3, 2, 2",
            "4, 1, 3",
            "5, 1, 4"
    })
    public void getAdjacentOfTest(int index, int x, int y) {
        Point point = new Point(2, 3);
        Point adjacent = point.getAdjacentOf(index);
        assertEquals(adjacent.getX(), x);
        assertEquals(adjacent.getY(), y);
    }
}