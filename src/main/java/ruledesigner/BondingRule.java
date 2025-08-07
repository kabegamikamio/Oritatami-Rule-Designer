package ruledesigner;

import java.util.ArrayList;
import java.util.List;

// class of bonding rules
// construct and hold a list of bonds
public class BondingRule {
    private List<Bond> rule;

    BondingRule() {
        this.rule = new ArrayList<>();
    }

    // add new bonding rule to the current rule
    public void add(Bond bond) {
        if(!this.ifContains(bond)) {
            this.rule.add(bond);
        }
    }

    // determine if the given pair of beads is contained in the bonding rule
    public boolean ifContains(Bond bond) {
        return this.rule.contains(bond);
    }

    // delete the designated bond from the rule
    public boolean delete(Bond bond) {
        if(this.rule.contains(bond)) {
            this.rule.remove(bond);
            return true;
        } else {
            return false;
        }
    }
}