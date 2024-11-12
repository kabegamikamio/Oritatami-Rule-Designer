package ruledesigner.test;

import org.junit.Test;
import ruledesigner.main.Bead;
import ruledesigner.main.Bond;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

// Unit test class for Bond class
public class BondTest {
    @Test
    public void getBeadTest_sorted() {
        Bead[] beads = {new Bead("bead1"), new Bead("bead2")};
        Bond bond = new Bond(beads[0], beads[1]);
        assertThat(bond.get(), is(beads));
    }
    
    @Test
    public void getBeadTest_not_sorted() {
        Bead[] beads = {new Bead("bead1"), new Bead("bead2")};
        Bond bond = new Bond(beads[1], beads[0]);
        assertThat(bond.get(), is(beads));
    }

    @Test
    public void getBeadTest_same() {
        Bead[] beads = {new Bead("bead1"), new Bead("bead1")};
        Bond bond = new Bond(beads[0], beads[1]);
        assertThat(bond.get(), is(beads));
    }
}