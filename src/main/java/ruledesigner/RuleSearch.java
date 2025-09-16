package ruledesigner;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RuleSearch {
    private final Conformation seedConf; // conformation generated with the current bonding rule
    private final Conformation targetConf;
    private final Transcript transcript;
    private BondingRule currentRules;
    private List<Bead> beadTypes;

    public RuleSearch(Conformation _seedConf, Conformation _targetConf, Transcript _transcript) {
        this.seedConf = _seedConf;
        this.targetConf = _targetConf;
        this.transcript = _transcript;

        initializeRules();
    }

    private void initializeRules() {
        this.currentRules = new BondingRule();
        beadTypes = new ArrayList<>();

        // extract the bead types from the transcript
        for (Bead bead : transcript.getList()) {
            if (!beadTypes.contains(bead)) {
                beadTypes.add(bead);
            }
        }

        // extract the bead types from the seed conformation
        for (Bead bead : seedConf.getTranscript().getList()) {
            if (!beadTypes.contains(bead)) {
                beadTypes.add(bead);
            }
        }
    }

    // search the bonding rule by SA algorithm
    public BondingRule searchBySA(int iterMax) {
        double temprature = 1000; // initial temperature
        double coolingRate = 0.9; // cooling rate

        Random rand = new Random();

        int numBeadTypes = beadTypes.size();

        boolean doAdd; // whether to add or delete a bond
        int idx1, idx2; // indices of bead types to create a bond

        Bond newBond;
        BondingRule tempRule = new BondingRule();
        BondingRule firmedRule = new BondingRule();
        Conformation newConf;
        Oritatami oritatami;

        int score, scorePrev = 0;

        for (int i=0; i<iterMax; i++) {
            temprature *= coolingRate;

            doAdd = rand.nextBoolean();
            idx1 = rand.nextInt(numBeadTypes);
            idx2 = rand.nextInt(numBeadTypes);

            newBond = new Bond(beadTypes.get(idx1), beadTypes.get(idx2));
            if (doAdd) {
                // create a new bond and add it to the current bonding rule
                tempRule.add(newBond);
            } else {
                // delete a bond
                tempRule.delete(newBond);
            }

            // execute oritatami with the new bonding rule
            oritatami = new Oritatami(transcript, seedConf, 3, true, tempRule);
            newConf = oritatami.executeOritatami();

            // evaluate the new conformation
            score = targetConf.perfectScore(newConf);

            // if the new conformation is perfect, finalize the rule and return it
            if (score == targetConf.getLength()) {
                firmedRule = currentRules;
                System.out.println("Found a perfect bonding rule!");
                return firmedRule;
            }

            if (score > scorePrev || rand.nextDouble() < threshold(temprature, score, scorePrev)) {
                // if the new conformation is better or the temperature is above the threshold, accept the new rule
                firmedRule = tempRule;
                scorePrev = score;
            }
        }

        return null; // temporary return
    }

    // this method inverse the boolean value of the rule at the given index
    private void ruleShifter(int index) {}

    private double threshold(double temperature, int score, int scorePrev) {
        return Math.exp((score - scorePrev) / temperature);

    }
}
