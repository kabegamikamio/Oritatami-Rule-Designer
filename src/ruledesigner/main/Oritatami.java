package ruledesigner.main;
import java.util.ArrayList;
import java.util.List;

public class Oritatami {
    final private int delay;
    final private boolean isOblivious;
    private BondingRule bondingRule;
    private Point[][] grid;

    Oritatami(int delay, boolean isOblivious, BondingRule bondingRule) {
        this.delay = 3;
        this.isOblivious = isOblivious;
        this.bondingRule = bondingRule;
    }

    /**
     * This method executes Oritatami simulation by either oblivious or inertial dynamics.
     * Users can define the Oritatami by giving the delay, transcript, set of bonding rules, initial conformation and dynamics.
     * @param transcript            pre-determined transcript
     * @param bondingRule           pre-determined bonding rules
     * @param initialConformation   pre-determined initial conformation
     * @return final conformation as the output of the Oritatami
     */
    public Conformation executeOritatami(Transcript transcript, BondingRule bondingRule,
                                         Conformation initialConformation) {
        int length = transcript.getLength();
        this.grid = new Point[length * 2][length * 2];

        if(this.isOblivious) {
            return executeOritatamiOblivious(transcript, initialConformation);
        } else {
            return executeOritatamiInertial(transcript, initialConformation);
        }
    }

    // execute Oritatami simulation in the oblivious dynamics
    private Conformation executeOritatamiOblivious(Transcript transcript, Conformation initialConformation) {
        // produced conformation
        Conformation conformation = new Conformation();

        // nascent transcribed beads
        List<Transcript> nascent = new ArrayList<>();

        // length of transcript
        int maxIdx = transcript.getLength();

        // array of nascent transcribed beads
        Bead[] nascentBeads = new Bead[delay];

        for(int i = 0; i < maxIdx - delay; i++) {
            // transcribe {delay} beads for the first time
            if(i == 0) {
                // initialize the array of the nascent transcribed beads
                for(int j = 0; j < delay; j++) {
                    nascentBeads[j] = transcript.read(true);
                }
            } else { // second time and after, read one bead from the transcript
                for(int j = 0; j < delay-1; j++) {
                    nascentBeads[j] = nascentBeads[j+1];
                }
                nascentBeads[delay-1] = transcript.read(true);
            }

            conformation = this.findEnergyMinimumConformation(conformation, nascentBeads);
        }

        return conformation;
    }

    // find locally energy-minimum conformation with oblivious dynamics
    // temporally hard-coded for delay of 3
    // Energy minimumising is done by maximising the number of bonds
    private Conformation findEnergyMinimumConformation(Conformation currentConformation, Bead[] beads) {
        Conformation conformation = new Conformation();

        int maxBonds = 0;
        int[] maxDirection = new int[this.delay];

        int bonds;
        Point[] newPoints = new Point[this.delay];

        // obtain the coordination of the point at the end of the current conformation
        Point terminalPoint = currentConformation.getPoint(-1);

        // process of finding energy-minimum conformation
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 6; j++) {
                for (int k = 0; k < 6; k++) {
                    newPoints[0] = terminalPoint.getAdjacentOf(i);
                    newPoints[1] = newPoints[0].getAdjacentOf(j);
                    newPoints[2] = newPoints[1].getAdjacentOf(k);

                    bonds = countBonds();

                    if(bonds > maxBonds) {
                        conformation = conformation.add();
                    }
                }
            }
        }

        return conformation;
    }

    // evaluate the number of bonds
    // in developing this method, be sure to refer the formalization of the oblivious dynamics...
    private int countBonds(Conformation conformation, Bead[] beads, Point[] points) {
        Conformation newConformation = new Conformation();
        Point point;
        Bead bead;
        Bond partialBond;
        int bond = 0;

        for (int i = 0; i < conformation.getLength(); i++) {
            point = conformation.getPoint(i);
            bead = conformation.getBead(i);

            for(int j = 0; j < this.delay; j++) {
                partialBond = new Bond(bead, beads[j]);
                if(points[j].isAdjacent(point) && bondingRule.ifContains(partialBond)) {
                    bond++;
                }
            }
        }
    }

    // execute Oritatami simulation in the inertial dynamics
    // not available atm
    private Conformation executeOritatamiInertial(Transcript transcript, Conformation initialConformation) {
        Conformation conformation = new Conformation();

        // inertial dynamics process

        return conformation;
    }
}
