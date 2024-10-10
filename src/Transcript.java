import java.util.ArrayList;
import java.util.List;

// class of transcript
class Transcript {
    private List<Integer> tsc;
    private int idx;
    private boolean isWrite;

    Transcript(boolean isWrite) {
        this.constructTranscript(isWrite);
    }

    Transcript() {
        this.constructTranscript(false);
    }

    public Integer read(boolean forward) {
        Integer ret = this.tsc.get(this.idx);
        if(forward) {
            this.idx++;
        }
        return ret.intValue();
    }

    public Integer read() {
        return this.read(true);
    }

    // this method return value in the form of Integer
    // in order to detect null exception
    public Integer read(int index) {
        int maxIndex = this.tsc.size();
        if(index >= maxIndex || index < 0) {
            return null;
        }
        return this.tsc.get(index);
    }

    // rewrite the given bead to the designated index on the transcript
    public boolean write(int bead, int index) {
        int maxIndex = this.tsc.size();
        if(0 <= index && index < maxIndex) {
            this.tsc.set(index, bead);
            return true;
        } else if(index == maxIndex + 1) {
            this.tsc.add(bead);
            this.idx++;
            return true;
        }
        return false;
    }

    // add the given bead to the end of the transcript
    public boolean write(int bead) {
        return write(bead, this.idx);
    }

    // set the index with the given value
    public boolean setIndex(int index) {
        int maxIndex = this.tsc.size();
        if(index >= maxIndex || index < 0) {
            return false;
        }
        this.idx = index;
        return true;
    }

    private void constructTranscript(boolean isWrite) {
        this.tsc = new ArrayList<Integer>();
        this.idx = 0;
        this.isWrite = isWrite;
    }
}