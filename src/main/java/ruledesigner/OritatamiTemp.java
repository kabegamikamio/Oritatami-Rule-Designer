package ruledesigner;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class OritatamiTemp {
    private final Transcript transcript;
    private final Conformation seedConformation;
    private final BondingRule bondingRule;

    /**
     * Constructor for OritatamiTemp.
     *
     * @param transcript The transcript containing the beads to be placed.
     * @param seedConformation The initial conformation to start from.
     * @param bondingRule The bonding rule that defines valid bonds between beads.
     */
    public OritatamiTemp(Transcript transcript, Conformation seedConformation, BondingRule bondingRule) {
        this.transcript = transcript;
        this.seedConformation = seedConformation;
        this.bondingRule = bondingRule;
    }

    /**
     * Run the Oritatami algorithm with a specified delay.
     * The delay indicates how many steps ahead to consider for placing the next bead.
     *
     * @param delay The number of steps ahead to consider for placement.
     * @return The final conformation after processing the entire transcript.
     */
    public Conformation run(int delay) {
        Conformation conf = new Conformation(seedConformation);
        int t = 0;

        while (t < transcript.getLength()) {
            System.out.println("\n\nt: " + t);
            PartialConformation best = searchPlacement(conf, t, delay, bondingRule);

            Bead beadToAdd = transcript.read(t);
            Point pointToAdd = best.getConformation().getPoint(best.getLength() - delay); // Get the first point in the best conformation that is delay steps ahead
            conf.add(beadToAdd, pointToAdd);
            t++;
        }
        return conf;
    }

    // Recursive method to search for the best placement of the next bead
    private PartialConformation searchPlacement(Conformation conf, int t, int depth, BondingRule rule) {
        Conformation temp = new Conformation(conf);

        // count bonds at the current placement
        if (depth == 0 || t + 1 >= transcript.getLength()) {
            int bonds = countBonds(conf, temp, rule);
            System.out.println("# of bonds:" + bonds);
            return new PartialConformation(conf, bonds);
        }

        Bead nextBead = transcript.read(t);
        Point p = conf.getPoint(-1); // Get the last point in the conformation

        PartialConformation best = null;
        System.out.println();

        List<Point> nextCandidates = p.getAdjacentPoints();
        for (Point candidate : nextCandidates) {
            Conformation newConformation = conf.getClone();

            // if the next point is already occupied, skip it
            if (conf.containsPoint(candidate)) continue;
            System.out.println("Trying candidate: (" + candidate.getX() + ", " + candidate.getY() + ") for bead: " + nextBead.getBeadName());
            newConformation.add(nextBead, candidate);

            PartialConformation result = searchPlacement(newConformation, t + 1, depth - 1, rule);

            if (best == null || result.getBondCount() > best.getBondCount()) {
                best = result;
            }
        }
        if (best == null) {
            throw new IllegalStateException("No valid placement found at t = " + t);
        }

        // bestのpointはdelay手先なので、最初のpとbest.bondsを紐付けて返す
        return new PartialConformation(best.getConformation(), best.getBondCount());
    }

    // Class to hold a partial conformation with its bond count
    private static class PartialConformation extends Conformation {
        private final int bondCount;

        public PartialConformation(Conformation conformation, int bondCount) {
            super(conformation.getTranscript(), new ArrayList<>(conformation.getAllPoints()));
            this.bondCount = bondCount;
        }

        public int getBondCount() {
            return bondCount;
        }

        public Conformation getConformation() {
            return new Conformation(new Transcript(this.getTranscript()), new ArrayList<>(this.getAllPoints()));
        }
    }

    // count bonds for the given conformation and partial conformation
    private int countBonds(Conformation conformation, Conformation partial, BondingRule rule) {
        int count = 0;
        int length = partial.getLength();

        for (int i = 0; i < length; i++) {
            Bead bead = partial.getBead(i);
            int beadBonds = 0;
            Point point = partial.getPoint(i);

            for (Point adj : point.getAdjacentPoints()) {
                Conformation candidateConformation = new Conformation(conformation);
                candidateConformation = candidateConformation.concatenate(partial);

                int idx = candidateConformation.indexOfPoint(adj);
                if (idx != -1 && idx > i + 1) { // Ensure we only check future beads
                    Bead otherBead = candidateConformation.getBead(idx);
                    if (otherBead != null) {
                        Bond bond = new Bond(bead, candidateConformation.getBead(idx));
                        if (rule.ifContains(bond)) {
                            System.out.println("Bond found: " + bead.getBeadName() + " - " + candidateConformation.getBead(idx).getBeadName());
                            beadBonds++;
                        }
                    }
                }
            }
            count += beadBonds;
        }
        return count;
    }
}