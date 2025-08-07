package ruledesigner.test;

import ruledesigner.main.Bead;
import ruledesigner.main.Conformation;
import ruledesigner.main.Point;

import org.junit.jupiter.api.Test;
import ruledesigner.main.Transcript;

import static org.junit.jupiter.api.Assertions.*;
import java.util.List;

class ConformationTest {

    @Test
    void addBeadToPlaceablePointReturnsTrue() {
        Conformation conformation = new Conformation();
        Bead bead = new Bead("TestBead");
        Point point = new Point(0, 0);

        assertTrue(conformation.add(bead, point));
    }

    @Test
    void addBeadToNonPlaceablePointReturnsFalse() {
        Conformation conformation = new Conformation();
        Bead bead = new Bead("TestBead");
        Point point = new Point(0, 0);

        conformation.add(bead, point);
        assertFalse(conformation.add(new Bead("TestBead2"), point));
    }

    @Test
    void getBeadReturnsCorrectBeadForValidIndex() {
        Conformation conformation = new Conformation();
        Bead bead1 = new Bead("A");
        Bead bead2 = new Bead("B");
        conformation.add(bead1, new Point(0, 0));
        conformation.add(bead2, new Point(1, 1));

        assertEquals(bead1, conformation.getBead(0));
        assertEquals(bead2, conformation.getBead(1));
    }

    @Test
    void getBeadWithNegativeIndexReturnsLastBead() {
        Conformation conformation = new Conformation();
        Bead bead1 = new Bead("A");
        Bead bead2 = new Bead("B");
        conformation.add(bead1, new Point(0, 0));
        conformation.add(bead2, new Point(1, 1));

        assertEquals(bead2, conformation.getBead(-1));
    }

    @Test
    void getBeadWithOutOfRangeIndexReturnsNull() {
        Conformation conformation = new Conformation();
        conformation.add(new Bead("A"), new Point(0, 0));

        assertNull(conformation.getBead(10));
    }

    @Test
    void getPointReturnsCorrectPoint() {
        Conformation conformation = new Conformation();
        Point point = new Point(1, 1);
        Point point2 = new Point(2, 2);
        conformation.add(new Bead("TestBead"), point);
        conformation.add(new Bead("TestBead2"), point2);

        assertEquals(point, conformation.getPoint(0));
        assertEquals(point2, conformation.getPoint(1));
    }

    @Test
    void getPointWithNegativeIndexReturnsLastPoint() {
        Conformation conformation = new Conformation();
        Point point1 = new Point(1, 1);
        Point point2 = new Point(2, 2);
        conformation.add(new Bead("TestBead1"), point1);
        conformation.add(new Bead("TestBead2"), point2);

        assertEquals(point1, conformation.getPoint(0));
        assertEquals(point2, conformation.getPoint(1));
        // Negative index should return the last point
        assertEquals(point2, conformation.getPoint(-1));
    }

    @Test
    void isSameReturnsTrueForIdenticalConformations() {
        Conformation conformation1 = new Conformation();
        Conformation conformation2 = new Conformation();
        Point point = new Point(1, 1);
        Bead bead = new Bead("TestBead");

        conformation1.add(bead, point);
        conformation2.add(bead, point);

        assertEquals("TestBead", conformation1.getBead(0).getBeadName());
        assertEquals("TestBead", conformation2.getBead(0).getBeadName());
        assertTrue(conformation1.isSame(conformation2));
    }

    @Test
    void isSameReturnsFalseForDifferentConformations() {
        Conformation conformation1 = new Conformation();
        Conformation conformation2 = new Conformation();
        conformation1.add(new Bead("TestBead"), new Point(1, 1));
        conformation2.add(new Bead("TestBead"), new Point(2, 2));

        assertFalse(conformation1.isSame(conformation2));
    }

    @Test
    void isSameReturnsFalseForDifferentBeads() {
        Conformation conformation1 = new Conformation();
        Conformation conformation2 = new Conformation();
        conformation1.add(new Bead("TestBead1"), new Point(1, 1));
        conformation2.add(new Bead("TestBead2"), new Point(1, 1));

        assertFalse(conformation1.isSame(conformation2));
    }

    @Test
    void getLengthReturnsCorrectNumberOfPoints() {
        Conformation conformation = new Conformation();
        conformation.add(new Bead("TestBead1"), new Point(1, 1));
        conformation.add(new Bead("TestBead2"), new Point(2, 2));

        assertEquals(2, conformation.getLength());
    }

    @Test
    void getAllPointsReturnsAllAddedPoints() {
        Conformation conformation = new Conformation();
        Point point1 = new Point(1, 1);
        Point point2 = new Point(2, 2);
        conformation.add(new Bead("TestBead1"), point1);
        conformation.add(new Bead("TestBead2"), point2);

        List<Point> points = conformation.getAllPoints();
        assertTrue(points.contains(point1));
        assertTrue(points.contains(point2));
    }

    @Test
    void isValidAlwaysReturnsTrue() {
        Conformation conformation = new Conformation();
        assertTrue(conformation.isValid());
    }

    @Test
    void getTranscriptReturnsCorrectTranscript() {
        String[] beads = {"TestBead1", "TestBead2"};
        List<Point> points = List.of(new Point(0, 0), new Point(1, 1));
        Transcript transcript = new Transcript(beads, false);

        Conformation conformation = new Conformation(transcript, points);

        assertTrue(conformation.getTranscript().isSame(transcript));

        assertEquals(beads[0], conformation.getTranscript().read(0).getBeadName());
        assertEquals(beads[1], conformation.getTranscript().read(1).getBeadName());
    }
}