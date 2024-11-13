package ruledesigner.main;

public class Bead {
    private String beadName;

    public Bead(String beadName) {
        this.beadName = beadName;
    }

    public String getBeadName() {
        return beadName;
    }

    // if this bead is the same to the given bead then return true
    public boolean isSame(Bead b) {
        return this.beadName.equals(b.getBeadName());
    }

    // compare the given bead with this bead
    // the expression rule of the return value follows String::compareTo()
    public int compareTo(Bead b) {
        return this.beadName.compareTo(b.getBeadName());
    }
}
