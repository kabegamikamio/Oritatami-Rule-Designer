package ruledesigner.main;
import java.util.ArrayList;
import java.util.List;

public class Oritatami {
    final private int delay;
    final private boolean isOblivious;
    final private BondingRule bondingRule;

    Oritatami(int delay, boolean isOblivious, BondingRule bondingRule) {
        this.delay = delay;
        this.isOblivious = isOblivious;
        this.bondingRule = bondingRule;
    }

    /**
     * This method executes Oritatami simulation by either oblivious or inertial dynamics.
     * Users can define the Oritatami by giving the delay, transcript,
     * set of bonding rules, seed conformation and dynamics.
     * @param transcript            pre-determined transcript
     * @param seedConformation   pre-determined seed conformation
     * @return final conformation as the output of the Oritatami
     */
    public Conformation executeOritatami(Transcript transcript, Conformation seedConformation) {
        int length = transcript.getLength();

        if(this.isOblivious) {
            return executeOritatamiOblivious(transcript, seedConformation);
        } else {
            return executeOritatamiInertial(transcript, seedConformation);
        }
    }

    // execute Oritatami simulation in the oblivious dynamics
    private Conformation executeOritatamiOblivious(Transcript transcript, Conformation seedConformation) {

        // produced conformation
        Conformation conformation = new Conformation();

        // length of transcript
        int transcriptLength = transcript.getLength();

        // array of nascent transcribed beads
        Bead[] nascentBeads = new Bead[delay];

        // array of the grid
        Bead[][] beadGrid = new Bead[transcriptLength * 2][transcriptLength * 2];

        // set the seed conformation to the bead grid
        placeBeadsOnGrid(beadGrid, seedConformation);

        for (int i = 0; i < transcriptLength - delay; i++) {
            // transcribe {delay} beads for the first time
            if(i == 0) {
                for(int j = 0; j < delay; j++) {
                    nascentBeads[j] = transcript.read(true);
                }
            } else { // second time and after, read one bead from the transcript
                for(int j = 0; j < delay-1; j++) {
                    nascentBeads[j] = nascentBeads[j+1];
                }
                nascentBeads[delay-1] = transcript.read(true);
            }

            conformation = this.findEnergyMinimumConformation(conformation, nascentBeads, beadGrid);
        }

        return conformation;
    }

    // find locally energy-minimum conformation with oblivious dynamics
    // temporally hard-coded for delay of 3
    // Energy minimizing is done by maximising the number of bonds
    private Conformation findEnergyMinimumConformation(Conformation currentConformation, Bead[] beads, Bead[][] beadGrid) {
        Conformation optimalConformation = new Conformation();
        int maxBonds = 0;
        int bonds;
        Point[] newPoints = new Point[3];
        Conformation tempConformation;

        // obtain the coordination of the point at the end of the current conformation
        Point terminalPoint = currentConformation.getPoint(-1);

        // process of finding energy-minimum conformation
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 6; j++) {
                for (int k = 0; k < 6; k++) {
                    newPoints[0] = terminalPoint.getAdjacentOf(i);
                    newPoints[1] = newPoints[0].getAdjacentOf(j);
                    newPoints[2] = newPoints[1].getAdjacentOf(k);

                    // Verify the validity of the partial conformation
                    if ((beadGrid[ newPoints[0].getX() ][ newPoints[0].getY() ] == null)
                        && (beadGrid[ newPoints[1].getX() ][ newPoints[1].getY() ] == null)
                        && (beadGrid[ newPoints[2].getX() ][ newPoints[2].getY() ] == null)) {
                        // make a temporary conformation by concatenating the nascent beads
                        tempConformation = currentConformation;
                        tempConformation.add(beads[0], newPoints[0]);
                        tempConformation.add(beads[1], newPoints[1]);
                        tempConformation.add(beads[2], newPoints[2]);

                        // count bonds and refresh the maximum number of bonds
                        bonds = countBonds(tempConformation, beadGrid);

                        if (bonds >= maxBonds) {
                            maxBonds = bonds;
                            optimalConformation = tempConformation;
                        }
                    }
                }
            }
        }

        return optimalConformation;
    }

    // counts the number of bonds formed among beads.
    private int countBonds(Conformation conformation, Bead[][] beadGrid) {
        int count = 0;
        int length = conformation.getLength();
        Bead bead;
        Point adjacentPoint;

        // temporary bond
        Bond tempBond;

        for (int i = 0; i < length; i++) {
            for (int j = 0; j < 5; j++) {
                bead = conformation.getBead(i);
                adjacentPoint = conformation.getPoint(i).getAdjacentOf(j);
                if (beadGrid[adjacentPoint.getX()][adjacentPoint.getY()] != null) {
                    tempBond = new Bond(bead, beadGrid[adjacentPoint.getX()][adjacentPoint.getY()]);
                    if (this.bondingRule.ifContains(tempBond)) {
                        count++;
                    }
                }
            }
        }

        return count;
    }

    // place given beads on the grid.
    // Grid array is used to boost counting bonds
    private void placeBeadsOnGrid (Bead[][] beadGrid, Conformation conformation) {
        int length = conformation.getLength();
        Point point;
        Bead bead;

        for (int i = 0; i < length; i++) {
            point = conformation.getPoint(i);
            bead = conformation.getBead(i);
            beadGrid[point.getX()][point.getY()] = bead;
        }
    }

    // execute Oritatami simulation in the inertial dynamics
    // not available atm
    private Conformation executeOritatamiInertial(Transcript transcript, Conformation seedConformation) {
        Conformation conformation = new Conformation();

        // inertial dynamics process

        return conformation;
    }
}
