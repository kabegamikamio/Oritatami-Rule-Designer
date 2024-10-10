// class of bond
// defines a bond with two beads as integers
class Bond {
    private int bead1, bead2;

    Bond(int bead1, int bead2) {
        if(bead1 <= bead2) {
            this.bead1 = bead1;
            this.bead2 = bead2;
        } else {
            this.bead1 = bead2;
            this.bead2 = bead1;
        }
    }

    // returns the bond
    public int[] get() {
        int ret[] = {this.bead1, this.bead2};
        return ret;
    }
}