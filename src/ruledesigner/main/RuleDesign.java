package ruledesigner.main;

// class to find the rule to obtain the target conformation by varying the rule
public class RuleDesign {
    private Bead[] beads;           // all beads used in the considered system
    private boolean[] binaryTable;  // a table that indicates which bonds are formed in true or false
    private Bond[] indexTable;      // a table that is refered to relate the binaryTable and actual bonds

    public RuleDesign(int length) {
        initializeBeads(length);
        initializeRuleBinary(length);
        initializeIndexTable(length);
    }

    /**
     * set beads used in rule designing
     * @params length   the number of beads used in the target Oritatami system
     */
    private void initializeBeads(int length) {
        beads = new Bead[length];
        String beadName;

        for (int i = 0; i < length; i++) {
            beadName = "b" + i;
            beads[i] = new Bead(beadName);
        }
    }

    /**
     *  initialize ruleBinary with false's
     * @params length   the number of beads used in the target Oritatami system
     */
    private void initializeRuleBinary(int length) {
        int tableSize = length * (length + 1) / 2;
        binaryTable = new boolean[tableSize];

        for(int i = 0; i < tableSize; i++) {
            binaryTable[i] = false;
        }
    }

    /**
     * construct the index table
     * @param length
     */
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

    /**
     * The main method to design the disireble rule by a greedy logic.
     * This method will run the Oritatami, varying the bonding rules one by one.
     * This will take so much time, like O(n^2 2^(n^2))...
     * Some enhancement should be done, such as DP-like algorithms
     * @param initialConformation   the initial conformation of the Oritatami
     * @param transcript            the transcript of the Oritatami
     * @param targetConformation    the target conformation
     * @return the set of bonding rules which gives the target conformation
     */
    public BondingRule doRuleDesign(Conformation initialConformation, Transcript transcript,
                                     Conformation targetConformation) {
        BondingRule bondingRule = new BondingRule();
        Conformation conformation;
        Bead bead1, bead2;
        Bond bond;
        int tableSize = beads.length * (beads.length + 1) / 2;

        // try ALL the possible bond pairs greedy
        // bloody O{n^2 2^(n^2)} logic
        for(int i = 0; i < Math.pow(2, tableSize); i++) {
            bondingRule = new BondingRule();
            Oritatami os;

            do {
                bondingRule = constructBondingRule();
                os = new Oritatami(3,true, bondingRule);
                conformation = os.executeOritatami(transcript, initialConformation);
                if(conformation.isSame(targetConformation)) {
                    return bondingRule;
                }
            } while(!this.shiftRule());
        }

        return bondingRule;
    }

    // Called by the method doRuleDesign(), this method varies the bonding rules one by one
    private boolean shiftRule() {
        boolean carry = true;

        for(int i = 0; i < indexTable.length; i++) {
            if(carry) {
                if(this.binaryTable[i]) {
                    this.binaryTable[i] = false;
                    continue;
                }
                this.binaryTable[i] = true;
            }
            carry = false;
        }

        return carry;
    }

    // This method constructs a bonding rule set according to the rule table (in binary) and the index table.
    // This is used in doRuleDesign() when it returns the target set of bonding rules.
    private BondingRule constructBondingRule() {
        BondingRule bondingRule = new BondingRule();
        Bond bond;

        // bonding construction process
        for(int i = 0; i < indexTable.length; i++) {
            if(this.binaryTable[i]) {
                bondingRule.add(this.indexTable[i]);
            }
        }

        return bondingRule;
    }
}
