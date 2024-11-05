public class RuleDesign {
    private Bead[] beads;
    private boolean[] ruleBinary;
    private Bond[] indexTable;

    public RuleDesign(int length) {
        initializeBeads(length);
        initializeRuleBinary(length);
        initializeIndexTable(length);
    }

    // set beads used in rule designing
    private void initializeBeads(int length) {
        beads = new Bead[length];
        String beadName;

        for (int i = 0; i < length; i++) {
            beadName = "b" + i;
            beads[i] = new Bead(beadName);
        }
    }

    // initialize ruleBinary with false's
    private void initializeRuleBinary(int length) {
        int tableSize = length * (length + 1) / 2;
        ruleBinary = new boolean[tableSize];

        for(int i = 0; i < tableSize; i++) {
            ruleBinary[i] = false;
        }
    }

    // initialize index table
    private void initializeIndexTable(int length) {
        Bead bead1, bead2;
        int tableSize = length * (length + 1) / 2;
        indexTable = new Bond[tableSize];

        for(int i = 0; i < length; i++) {
            for(int j = i; j < length; j++) {
                bead1 = new Bead("bead" + i);
                bead2 = new Bead("bead" + j);
                indexTable[i] = new Bond(bead1, bead2);
            }
        }
    }

    public BondingRule doRuleDesign(Conformation initialConformation, Transcript transcript,
                                     Conformation targetConformation) {
        BondingRule bondingRule;
        Conformation conformation;
        Bead bead1, bead2;
        Bond bond;
        int tableSize = beads.length * (beads.length + 1) / 2;

        // try ALL the possible bond pairs greedy
        // bloody O{n^2 2^(n^2)} logic
        for(int i = 0; i < Math.pow(2, tableSize); i++) {
            bondingRule = new BondingRule();
            Oritatami os = new Oritatami(3, true);

            do {
                bondingRule = constructBondingRule();
                conformation = os.executeOritatami(transcript, bondingRule, initialConformation);
                if(conformation.isSame(targetConformation)) {
                    return bondingRule;
                }
            } while(!this.shiftRule());
        }
    }

    // change bonding rule one by one
    private boolean shiftRule() {
        boolean carry = true;

        for(int i = 0; i < indexTable.length; i++) {
            if(carry) {
                if(this.ruleBinary[i]) {
                    this.ruleBinary[i] = false;
                    continue;
                }
                this.ruleBinary[i] = true;
            }
            carry = false;
        }

        return carry;
    }

    // construct a bonding rule set according to the rule table (in binary) and the index table
    private BondingRule constructBondingRule() {
        BondingRule bondingRule = new BondingRule();

        // bonding construction

        return bondingRule;
    }
}
