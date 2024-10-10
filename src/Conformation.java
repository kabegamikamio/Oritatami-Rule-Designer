import java.util.ArrayList;
import java.util.List;

// class of conformations
// used to contain the target conformation etc.
class Conformation {
    // List of transcript
    // do not make it 'final'
    private Transcript tsc;

    // List of points where beads are placed
    // do not make it 'final'
    private List<Point> points;

    // index which indicates the recently refered
    private int idx;

    // constructor of Conformation class
    // generate the instances of the list of the transcript and points
    // also, initialize the index with 0
    Conformation() {
        this.tsc = new ArrayList<Integer>();
        this.points = new ArrayList<Point>();
        this.idx = 0;
    }

    public int readTsc() {
        return this.tsc.read(this.idx++).intValue();
    }

    public List<Point> getPoints() {
        return this.points;
    }

    // determine whether the given conformation is the same
    public boolean isSame(Conformation conformation) {
        List<Point> points2 = conformation.getPoints();
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
        return true;    // if each pair of the points is the
    }
}