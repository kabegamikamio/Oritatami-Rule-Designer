package ruledesigner.test;

import org.junit.jupiter.api.Test;
import ruledesigner.main.Bead;
import ruledesigner.main.Bond;

import static org.junit.jupiter.api.Assertions.assertTrue;

// Unit test class for Bond class
public class BondTest {
    @Test
    public void getBeadTest_sorted() {
        Bead[] beads = {new Bead("bead1"), new Bead("bead2")};
        Bond bond = new Bond(beads[0], beads[1]);
        Bead[] retBeads = bond.get();
        assertTrue(retBeads[0].isSame(beads[0]));
        assertTrue(retBeads[1].isSame(beads[1]));
    }
    
    @Test
    public void getBeadTest_not_sorted() {
        Bead[] beads = {new Bead("bead1"), new Bead("bead2")};
        Bond bond = new Bond(beads[1], beads[0]);
        Bead[] retBeads = bond.get();
        assertTrue(retBeads[0].isSame(beads[0]));
        assertTrue(retBeads[1].isSame(beads[1]));
    }

    @Test
    public void getBeadTest_same() {
        Bead[] beads = {new Bead("bead1"), new Bead("bead1")};
        Bond bond = new Bond(beads[0], beads[1]);
        Bead[] retBeads = bond.get();
        assertTrue(retBeads[0].isSame(beads[0]));
        assertTrue(retBeads[1].isSame(beads[1]));
    }
}