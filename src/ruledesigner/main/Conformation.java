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

    // 2D array of points for verifying if any bead is on the point
    private Point[][] grid;

    // constructor of Conformation class
    // generate the instances of the list of the transcript and points
    // also, initialize the index with 0
    public Conformation() {
        this.transcript = new Transcript();
        this.points = new ArrayList<Point>();
        this.idx = 0;
    }

    /**
     * This method add new bead in the poisition defined in the parameters
     * @param bead  The bead to place on the grid.
     * @param point The point where the bead should be placed.
     * @return  Does it succeed to place the bead on the grid? (true, if yes)
     */
    public boolean add(Bead bead, Point point) {
        if(isPlacable(point)) {
            this.transcript.write(bead);
            this.points.add(point);
            return true;
        }
        return false;
    }

    // Sub routine of add() which checks if the point is 'blank'.
    private boolean isPlacable(Point point) {
        int x = point.getX();
        int y = point.getY();

        if(grid[x][y] == null) {
            return true;
        }
        return false;
    }

    /**
     * This method reads one bead from the transcript and return it.
     * @return  one bead at the end of the transcript
     */
    public Bead readTsc() {
        return this.transcript.read(this.idx++);
    }

    /**
     * This method returns the point in the index value.
     * @params index    the index of reference
     * @return  points[index]
     */
    public Point getPoint(int index) {
        if(index < 0) {
            index = this.transcript.getLength() - 1;
        }
        return this.points.get(index);
    }

    /**
     * This method returs the bead in the index value.
     * @params index    the index of reference
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
     * @return  set of points which the instance posesses
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
            Point point1 = this.points.get(i);
            Point point2 = points2.get(i);
            if(!point1.isSame(point2)) {
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