package ruledesigner;
import java.util.ArrayList;
import java.util.List;

public class Oritatami {
    final private int delay;
    final private boolean isOblivious;
    final private BondingRule bondingRule;
    final private Transcript transcript;
    final private Conformation seedConformation;

    Oritatami(Transcript _transcript, Conformation _seedConf, int delay, boolean isOblivious, BondingRule bondingRule) {
        this.transcript = _transcript;
        this.seedConformation = _seedConf;
        this.delay = delay;
        this.isOblivious = isOblivious;
        this.bondingRule = bondingRule;
    }

    /**
     * This method executes Oritatami simulation by either oblivious or inertial dynamics.
     * Users can define the Oritatami by giving the delay, transcript,
     * set of bonding rules, seed conformation and dynamics.
     * @return final conformation as the output of the Oritatami
     */
    public Conformation executeOritatami() {
        if(this.isOblivious) {
            // execute Oritatami simulation in the oblivious dynamics
            return executeOritatamiOblivious();
        } else {
            // execute Oritatami simulation in the inertial dynamics
            return executeOritatamiInertial(transcript, seedConformation);
        }
    }

    // execute Oritatami simulation in the oblivious dynamics
    // this function calls findEnergyMinimumConformation, a recursive function
    private Conformation executeOritatamiOblivious() {

        // result conformation, initialized with the seed conformation
        Conformation newConf = new Conformation(this.seedConformation);

        // loop for each bead in the transcript
        for (int i = 0; i < this.transcript.getLength(); i++) {
            // best conformations for the current bead
            BestConformations best = new BestConformations();

            // find the energy minimum conformation for the current time step
            findEnergyMinimumConformation(newConf, best, i, 0);

            if (!best.isEmpty()) {
                System.out.println("Best bonds: " + best.getBondCount());
                System.out.println("Best conformation at t = " + i + ":");
                for (int j = 0; j < best.getConformationList().size(); j++) {
                    System.out.println("\t(" + best.getConformation(j).getPoint(i + this.seedConformation.getLength()).getX() + ", " + best.getConformation(j).getPoint(i + this.seedConformation.getLength()).getY() + ") for bead: " + best.getConformation(j).getBead(i + this.seedConformation.getLength()).getBeadName());
                }
            }

            System.out.println("deterministic test: " + best.isDeterministicPoint(i + this.seedConformation.getLength()));

            // if the best conformations is empty or the i-th point is not deterministic, throw an exception
            if (best.getBondCount() <= 0 || !best.isDeterministicPoint(i + this.seedConformation.getLength())) {
                throw new IllegalStateException("No valid placement found at t = " + i + "-th bead on the transcript.");
            }

            System.out.println("Valid conformation found at t = " + (i + this.seedConformation.getLength()));

            System.out.println("Placed bead " + this.transcript.read(i) + " at point (" + best.getPoint(i + this.seedConformation.getLength()).getX() + ", " + best.getPoint(i + this.seedConformation.getLength()).getY() + ") at time " + i);

            // if the i-th point is deterministic, then place it at the end of the conformation
            newConf.add(this.transcript.read(i), new Point(best.getPoint(i + this.seedConformation.getLength())));
        }

        return newConf;
    }

    private void findEnergyMinimumConformation(Conformation partialConf, BestConformations best, int t, int depth) {
        if (depth >= this.delay || t + depth >= this.transcript.getLength()) {
            System.out.println("t = " + t);

            // count bonds at the current placement
            int bonds = countBonds(partialConf, this.bondingRule, t + this.seedConformation.getLength());

            // if the current partial conformation is better than the best found so far,
            // update the best conformations
            if (bonds > best.getBondCount()) {
                best.clear();
                best.addConformation(partialConf, bonds);
            }

            // if the current partial conformation has the same bond count as the best found so far,
            // add it to the list of best conformations
            else if (bonds == best.getBondCount()) {
                best.addConformation(partialConf, bonds);
            }

            System.out.println("bonds found: " + bonds);

            return;
        }

        Bead bead = this.transcript.read(t + depth);
        Point lastPoint = partialConf.getPoint(-1);

        if (lastPoint == null) {
            throw new IllegalStateException("Last point in the conformation is null at depth " + depth + " and time " + t);
        }

        List<Point> nextCandidates = lastPoint.getAdjacentPoints();

        // for each candidate position, evaluate the placement of the maximum bonds
        for (Point candidate : nextCandidates) {

            if (partialConf.containsPoint(candidate)) {
                continue; // Skip if the candidate point is already occupied
            }

            System.out.println("Candidate: (" + candidate.getX() + ", " + candidate.getY() + ") for bead: " + bead.getBeadName());

            Conformation candidateConf = new Conformation(partialConf);
            candidateConf.add(bead, candidate);

            // Recursively search for the next placement
            findEnergyMinimumConformation(candidateConf, best, t, depth + 1);
        }
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

    // counts the number of bonds formed among the nascent transcribed beads
    private int countBonds(Conformation conformation, BondingRule bondingRule, int index) {
        int bondCount = 0;
        int length = conformation.getLength();
        Bead bead;
        Bond tempBond;

        for (int i = index; i < length; i++) {
            bead = conformation.getBead(i);
            List<Point> adjacentPoints = conformation.getPoint(i).getAdjacentPoints();
            for (Point adjacent : adjacentPoints) {
                if (adjacent != null) {
                    int nextIndex = conformation.indexOfPoint(adjacent);
                    tempBond = new Bond(bead, conformation.getBead(nextIndex));
                    if (tempBond != null && bondingRule.ifContains(tempBond) && nextIndex >= 0 && Math.pow(nextIndex - i, 2) > 1) {
                        System.out.println("Bond found: " + bead.getBeadName() + "(" + i + ") - " + conformation.getBead(nextIndex).getBeadName() + "(" + nextIndex + ")");
                        bondCount++;
                    }
                }
            }
        }

        return bondCount;
    }

    // Class to hold the best conformations and their bond count
    private static class BestConformations {
        List<Conformation> conformations;
        int bondCount;

        // Constructor with conformations to add and bond count
        public BestConformations(List<Conformation> conformations, int bondCount) {
            this.conformations = conformations;
            this.bondCount = bondCount;
        }

        // Default constructor initializes an empty list and bond count
        public BestConformations() {
            this.conformations = new ArrayList<>();
            this.bondCount = -1;
        }

        public boolean isEmpty() {
            return this.conformations.isEmpty();
        }

        public int getBondCount() {
            return this.bondCount;
        }

        public List<Conformation> getConformationList() {
            if (this.conformations.isEmpty()) {
                return null;
            }
            return this.conformations; // Assuming we want the first conformation
        }

        public Conformation getConformation(int index) {
            if (this.conformations.isEmpty() || index < 0 || index >= this.conformations.size()) {
                return null;
            }
            return this.conformations.get(index);
        }

        // add conformation to the conformation list if the bond count equals to the current bond count
        // or if the bond count is greater than the current bond count, replace the list with the new conformation
        // and update the bond count.
        public boolean addConformation(Conformation conformation, int bondCount) {
            // if the current bond count is bigger then the given one, do nothing and return false
            if (this.bondCount > bondCount) return false;

            // if the given bond count equals to the current one, then add the given conformation to the list
            if (this.bondCount == bondCount) {
                this.conformations.add(conformation);
                return true;
            }

            // if the given bond count is greater than the current one,
            // abandon the previous conformations
            this.conformations.clear();
            this.conformations.add(conformation);
            this.bondCount = bondCount;
            return true;
        }

        // check if the point at the given index is deterministic
        public boolean isDeterministicPoint(int index) {
            boolean[] b = new boolean[3];

            b[0] = this.conformations.isEmpty();
            b[1] = (index < 0);
            b[2] = (index >= this.conformations.getFirst().getLength());

            if (b[0] || b[1] || b[2]) {
                String[] errors = new String[] {
                    "Conformations list is empty.",
                    "Index is negative.",
                    "Index is out of bounds."
                };
                for (int i = 0; i < b.length; i++) {
                    if (b[i]) {
                        System.out.println("Error: " + errors[i]);
                    }
                }
                return false; // If there are no conformations or the index is out of bounds, it's not deterministic
            }

            Point p0 = new Point(this.conformations.getFirst().getPoint(index));

            for (Conformation conf : this.conformations) {
                Point p = new Point(conf.getPoint(index));
                if (!p0.isSame(p)) {
                    System.out.println("Pfft. Point at index " + index + " is not deterministic: (" + p0.getX() + ", " + p0.getY() + ") vs (" + p.getX() + ", " + p.getY() + ")");
                    return false; // If any point is different, it's not deterministic
                }

                System.out.println("Checking point at index " + index + ", bead of " + conf.getBead(index).getBeadName() + ", : (" + p.getX() + ", " + p.getY() + ")");
            }

            System.out.println("Point " + index + "is deterministic");
            return true;
        }

        // get the point at the given index from the first conformation
        public Point getPoint(int index) {
            if (this.conformations.isEmpty() || index < 0 || index >= this.conformations.getFirst().getLength() || !this.isDeterministicPoint(index)) {
                // If there are no conformations or the index is out of bounds, return null
                return null;
            }
            return this.conformations.getFirst().getPoint(index); // Assuming we want the first conformation's point
        }

        // clear the conformations and bond count
        public void clear() {
            this.conformations.clear();
            this.bondCount = 0;
        }
    }

    // place given beads on the grid.
    // Grid array is used to boost counting bonds
    // and returns the point of the last bead placed.
    private Point placeBeadsOnGrid (Bead[][] beadGrid, Conformation conformation) {
        int length = conformation.getLength();
        Point point = new Point();
        Bead bead;

        for (int i = 0; i < length; i++) {
            point = conformation.getPoint(i);
            bead = conformation.getBead(i);
            beadGrid[point.getX()][point.getY()] = bead;
        }

        return point;
    }

    // execute Oritatami simulation in the inertial dynamics
    // not available atm
    private Conformation executeOritatamiInertial(Transcript transcript, Conformation seedConformation) {
        Conformation conformation = new Conformation();

        // inertial dynamics process

        return conformation;
    }
}
