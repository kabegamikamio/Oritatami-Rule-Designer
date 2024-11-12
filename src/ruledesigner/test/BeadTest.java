package ruledesigner.test;

import org.junit.Test;
import ruledesigner.main.Bead;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

// Unit testing class of Bead class
public class BeadTest {
    /**
     * Target: Bead::getBeadName()
     * This method verifies if Bead::getBeadName() is working correctly.
     */
    @Test
    public void getBeadNameTest() {
        String beadName = "beadName";
        Bead bead = new Bead(beadName);
        assertThat(beadName, is(bead.getBeadName()));
    }

    /**
     * Target: Bead::isSame()
     * This method verifies if Bead::isSame() is working correctly
     * by giving it a pair of beads which have the same names of beads.
     * Thus, the expected return value of isSame() is 'true'.
     */
    @Test
    public void isSameTest_same_value() {
        Bead bead1 = new Bead("bead1");
        Bead bead2 = new Bead("bead1");
        assertThat(bead1.isSame(bead2), is(true));
    }

    /**
     * Target: Bead:isSame()
     * This method verifies if Bead::isSame() is working correctly
     * by giving it a pair of beads which have different names of beads.
     * Thus, the expected return value of isSame() is 'false'.
     */
    @Test
    public void isSameTest_different_value() {
        Bead bead1 = new Bead("bead1");
        Bead bead2 = new Bead("bead2");
        assertThat(bead1.isSame(bead2), is(false));
    }

    /**
     * Target: Bead::compareTo()
     * This method verifies if Bead::compareTo() working correctly
     * by giving it a pair of beads of which the first bead comes first
     * when sorted by their names.
     * Thus, the return value should be negative.
     */
    @Test
    public void compareToTest_first_bead_first() {
        Bead bead1 = new Bead("bead1");
        Bead bead2 = new Bead("bead2");
        assertTrue(bead1.compareTo(bead2) < 0);
    }

    /**
     * Target: Bead::compareTo()
     * This method verifies if Bead::compareTo() is working correctly
     * by giving it a pair of beads of which the first bead comes last
     * when sorted by their names.
     * Thus, the return value should be positive.
     */
    @Test
    public void compareToTest_first_bead_last() {
        Bead bead1 = new Bead("bead2");
        Bead bead2 = new Bead("bead1");
        assertTrue(bead1.compareTo(bead2) > 0);
    }

    /**
     * Target: Bead::compareTo()
     * This method verifies if Bead::compareTo(0 is working correctly
     * by giving it a pair of beads which have the same name.
     * Thus, the return value should be zero.
     */
    @Test
    public void compareToTest_same_beads() {
        Bead bead1 = new Bead("bead1");
        Bead bead2 = new Bead("bead1");
        assertTrue(bead1.compareTo(bead2) == 0);
    }
}
