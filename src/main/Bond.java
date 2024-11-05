// class of bond
// defines a bond with two beads as integers
public class Bond {
    private Bead bead1, bead2;

    Bond(Bead bead1, Bead bead2) {
        // the one which lexicographically comes first is put in bead1, the other is put in bead2
        if(bead1.compareTo(bead2) < 0) {
            this.bead1 = bead1;
            this.bead2 = bead2;
        } else {
            this.bead1 = bead2;
            this.bead2 = bead1;
        }
    }

    // returns the bond
    public Bead[] get() {
        Bead[] ret = new Bead[2];
        ret[0] = this.bead1;
        ret[1] = this.bead2;
        return ret;
    }
}