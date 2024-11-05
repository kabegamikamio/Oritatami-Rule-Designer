import java.util.ArrayList;
import java.util.List;

// class of conformations
// used to contain the target conformation etc.
public class Conformation {
    // List of transcript
    // do not make it 'final'
    private Transcript transcript;

    // List of points where beads are placed
    // do not make it 'final'
    private List<Point> points;

    // index which indicates the recently referred
    private int idx;

    // constructor of Conformation class
    // generate the instances of the list of the transcript and points
    // also, initialize the index with 0
    Conformation() {
        this.transcript = new Transcript();
        this.points = new ArrayList<Point>();
        this.idx = 0;
    }

    public int readTsc() {
        return this.transcript.read(this.idx++).intValue();
    }

    public List<Point> getPoints() {
        return this.points;
    }

    public Transcript getTranscript() {
        return this.transcript;
    }

    // determine whether the given conformation is the same
    public boolean isSame(Conformation conformation) {
        List<Point> points2 = conformation.getPoints();
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
}