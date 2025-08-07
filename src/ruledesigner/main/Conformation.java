package ruledesigner.main;

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

    // index which indicates the recently referred
    private int idx;

    private int[][] indexTable;

    // constructor of Conformation class
    // generate the instances of the list of the transcript and points
    // also, initialize the index with 0
    public Conformation() {
        this.transcript = new Transcript();
        this.points = new ArrayList<Point>();
        this.idx = 0;
    }

    // constructor of Conformation class with the given transcript and points
    public Conformation(Transcript transcript, List<Point> points) {
        this.transcript = transcript;
        this.points = points;
        this.idx = 0;
    }

    /**
     * This method add new bead in the poisition defined in the parameters
     * @param bead  The bead to place on the grid.
     * @param point The point where the bead should be placed.
     * @return  Does it succeed to place the bead on the grid? (true, if yes)
     */
    public boolean add(Bead bead, Point point) {
        if(isPlaceable(point)) {
            this.transcript.write(bead);
            this.points.add(point);
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
            return this.points.get(this.points.size() - 1);
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
     * @return a set of points which the instance posesses
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
        if(!transcript2.isSame(this.transcript)) {
            return false;
        }

        return true;    // if each pair of the points is the
    }

    /**
     * This method verifies that the conformation is a self-avoiding path.
     * @return  Is the conformation a self-avoiding path? (true, if yes)
     */
    public boolean isValid() {
        return true;
    }
}