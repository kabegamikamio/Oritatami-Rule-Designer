package ruledesigner;

// class of bond
// defines a bond with two beads as integers
public class Bond {
    private Bead bead1, bead2;

    public Bond(Bead bead1, Bead bead2) {
        // the one which lexicographically comes first is put in bead1, the other is put in bead2
        if(bead1.compareTo(bead2) <= 0) {
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

    // checks if this bond is the same as another bond
    public Boolean isSame(Bond other) {
        String bead1Name = this.bead1.getBeadName();
        String bead2Name = this.bead2.getBeadName();

        String otherBead1Name = other.bead1.getBeadName();
        String otherBead2Name = other.bead2.getBeadName();

        return (bead1Name.equals(otherBead1Name) && bead2Name.equals(otherBead2Name)) ||
               (bead1Name.equals(otherBead2Name) && bead2Name.equals(otherBead1Name));
    }

    public Bead getBeadA() {
        return this.bead1;
    }

    public Bead getBeadB() {
        return this.bead2;
    }
}