package ruledesigner;

import java.util.List;

public class OritatamiTemp {
    private final Transcript transcript;
    private final Conformation seedConformation;
    // private final int arity;
    private final BondingRule bondingRule;

    public OritatamiTemp(Transcript transcript, Conformation seedConformation, BondingRule bondingRule) {
        this.transcript = transcript;
        this.seedConformation = seedConformation;
        this.bondingRule = bondingRule;
    }

    public Conformation run() {
        Conformation conformation = new Conformation();

        // シード配置
        for (int i = 0; i < seedConformation.getLength(); i++) {
            conformation.add(seedConformation.getBead(i), seedConformation.getPoint(i));
        }

        int length = transcript.getLength();
        for (int t = 0; t < length; t++) {
            Bead currentBead = transcript.read(t);
            Point lastPoint = conformation.getPoint(conformation.getLength() - 1);
            List<Point> candidatePositions = lastPoint.getAdjacentPoints();

            Point bestPosition = null;
            int maxBonds = -1;

            for (Point p : candidatePositions) {
                if (conformation.containsPoint(p)) continue;

                Conformation tempConformation = new Conformation(conformation);
                tempConformation.add(currentBead, p);

                int bonds = countBonds(tempConformation, bondingRule);

                if (bonds > maxBonds) {
                    maxBonds = bonds;
                    bestPosition = p;
                }
            }

            if (maxBonds > 0 && bestPosition != null) {
                conformation.add(currentBead, bestPosition);
            } else {
                break;
            }
        }

        return conformation;
    }

    private int countBonds(Conformation conformation, BondingRule rule) {
        int count = 0;
        int length = conformation.getLength();

        for (int i = 0; i < length; i++) {
            Bead bead = conformation.getBead(i);
            int beadBonds = 0;
            Point point = conformation.getPoint(i);

            for (Point adj : point.getAdjacentPoints()) {
                int idx = conformation.indexOfPoint(adj);
                if (idx != -1) {
                    Bond bond = new Bond(bead, conformation.getBead(idx));
                    if (rule.ifContains(bond)) {
                        beadBonds++;
                    }
                }
            }
            count += beadBonds;
        }
        return count;
    }
}