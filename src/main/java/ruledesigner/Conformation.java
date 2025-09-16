package ruledesigner;

import java.util.ArrayList;
import java.util.List;

// class of conformations
// used to contain the target conformation etc.
public class Conformation {
    // List of transcript
    // do not make it 'final'
    private Transcript transcript;

    // List of points where beads are placed
    private List<Point> points;

    // the length of the transcript
    int length;

    // index which indicates the recently referred
    private int idx;

    // constructor of Conformation class
    // generate the instances of the list of the transcript and points
    // also, initialize the index with 0
    public Conformation() {
        this.transcript = new Transcript();
        this.points = new ArrayList<>();
        this.idx = 0;
        this.length = 0;
    }

    // constructor of Conformation class with the given transcript and points
    public Conformation(Transcript transcript, List<Point> points) {
        this.transcript = transcript;
        this.points = points;
        this.idx = 0;
        this.length = transcript.getLength();
    }

    // constructor of Conformation class with the given conformation
    public Conformation(Conformation conformation) {
        this.transcript = new Transcript(conformation.getTranscript());
        List<Point> newPoints = new ArrayList<>();
        for (Point p : conformation.getAllPoints()) {
            newPoints.add(new Point(p)); // Create a new Point object to avoid reference issues
        }
        this.points = newPoints;
        this.idx = conformation.idx;
        this.length = conformation.length;
    }

    /**
     * This method add new bead in the position defined in the parameters
     * @param bead  The bead to place on the grid.
     * @param point The point where the bead should be placed.
     * @return  Does it succeed to place the bead on the grid? (true, if yes)
     */
    public boolean add(Bead bead, Point point) {
        if(isPlaceable(point)) {
            this.transcript.add(bead);
            this.points.add(point);
            this.length++;
            return true;
        }
        return false;
    }

    // Sub routine of add() which checks if the point is 'blank'.
    private boolean isPlaceable(Point point) {
        return !this.points.contains(point);
    }

    /**
     * This method reads one bead from the transcript and returns it.
     * @return  one bead at the end of the transcript
     */
    public Bead readTsc() {
        return this.transcript.read(this.idx++);
    }

    /**
     * This method returns the point in the index value.
     * @param index    the index of reference
     * @return  points[index]
     */
    public Point getPoint(int index) {
        if(index < 0) {
            return this.points.getLast();
        }
        return this.points.get(index);
    }

    /**
     * This method returns the bead in the index value.
     * @param index    the index of reference
     * @return  The bead at [index].
     */
    public Bead getBead(int index) {
        if(index < 0) {
            index = this.transcript.getLength() - 1;
        }
        return this.transcript.read(index);
    }

    /**
     * This method returns its set of points.
     * @return points which the instance possesses
     */
    public List<Point> getAllPoints() {
        return this.points;
    }

    /**
     * This method returns its transcript.
     * @return  the transcript of the instance
     */
    public Transcript getTranscript() {
        return this.transcript;
    }

    /**
     * This method returns the length of the conformation.
     * @return  the length of the conformation.
     */
    public int getLength() {
        return this.points.size();
    }

    // determine whether the given conformation is the same

    /**
     * This method determines whether the given conformation is the same to its conformation
     * by comparing every single pair of beads in them.
     * @param conformation  The conformation to compare
     * @return  Are two conformations the same? (true, when yes)
     */
    public boolean isSame(Conformation conformation) {
        List<Point> points2 = conformation.getAllPoints();
        Transcript transcript2 = conformation.getTranscript();

        // if the lengths of two are different, two conformations are different
        if(this.points.size() != points2.size()) {
            return false;
        }

        // obtain points one by one, and compare
        for(int i=0; i<this.points.size(); i++) {
            // obtain the points in the same index
            Point point1 = this.points.get(i);
            Point point2 = points2.get(i);

            // if the points are not the same, two conformations are different
            if(!point1.isSame(point2)) {
                return false;
            }

            // obtain the beads in the same index
            Bead bead1 = this.transcript.read(i);
            Bead bead2 = transcript2.read(i);

            // if the beads are not the same, two conformations are different
            if(!bead1.isSame(bead2)) {
                return false;
            }
        }

        // compare the two transcripts
        return transcript2.isSame(this.transcript);// if each pair of the points is the
    }

    /**
     * This method verifies that the conformation is a self-avoiding path.
     * @return  Is the conformation a self-avoiding path? (true, if yes)
     */
    public boolean isValid() {
        return true;
    }

    /**
     * This method returns the index of the last referred point.
     * @return  The index of the last referred point.
     */
    public boolean containsPoint(Point p) {
        for (Point point : this.points) {
            if (point.isSame(p)) {
                return true;
            }
        }
        return false;
    }

    /**
     * This method returns the index of the given point in the points list.
     * @param adj The point to find in the list.
     * @return  The index of the point if found, otherwise -1.
     */
    public int indexOfPoint(Point adj) {
        for (int i = 0; i < this.points.size(); i++) {
            if (this.points.get(i).isSame(adj)) {
                return i;
            }
        }
        return -1; // Not found
    }

    /**
     * This method returns the index of the given bead in the transcript.
     * @param beadA The bead to find in the transcript.
     * @return  The index of the bead if found, otherwise -1.
     */
    public int indexOfBead(Bead beadA) {
        for (int i = 0; i < this.transcript.getLength(); i++) {
            Bead beadB = this.transcript.read(i);
            if (beadB.isSame(beadA)) {
                return i;
            }
        }
        return -1; // Not found
    }

    /**
     * This method concatenates the current conformation with another conformation.
     * @param other The other conformation to concatenate.
     * @return  A new Conformation that is the result of concatenation.
     */
    public Conformation concatenate(Conformation other) {
        Transcript newTranscript = new Transcript(this.transcript);
        newTranscript.addAll(other.getTranscript());
        List<Point> newPoints = new ArrayList<>(this.points);
        newPoints.addAll(other.getAllPoints());
        return new Conformation(newTranscript, newPoints);
    }

    public Conformation getClone() {
        return new Conformation(new Transcript(this.transcript), new ArrayList<>(this.points));
    }

    public int perfectScore(Conformation compareConf) {
        int score = 0;

        int length = this.getLength();
        if (length != compareConf.getLength()) {
            return 0;
        }

        // obtain points one by one, and compare
        for(int i=0; i<length; i++) {
            // obtain the points in the same index
            Point point1 = this.getPoint(i);
            Point point2 = compareConf.getPoint(i);

            // if the points are not the same, two conformations are different
            if(!point1.isSame(point2)) {
                return score;
            }

            // obtain the beads in the same index
            Bead bead1 = this.getBead(i);
            Bead bead2 = compareConf.getBead(i);

            // if the beads are not the same, two conformations are different
            if(!bead1.isSame(bead2)) {
                return score;
            }

            score++;
        }

        return score;
    }
}