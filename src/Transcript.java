import java.util.ArrayList;
import java.util.List;

// class of transcript
class Transcript {
    private List<Integer> transcriptList;
    private int idx;
    private boolean isWrite;

    // constructor with writable mode
    Transcript(boolean isWrite) {
        this.constructTranscript(isWrite);
    }

    // constructor with writable mode (set to read-only)
    Transcript() {
        this.constructTranscript(false);
    }

    // read one bead from the transcript
    // if foward=true, move the idx forward
    public Integer read(boolean forward) {
        Integer ret = this.transcriptList.get(this.idx);
        if(forward) {
            this.idx++;
        }
        return ret.intValue();
    }

    // read one bead from the transcript, and move the idx forward
    public Integer read() {
        return this.read(true);
    }

    // read one bead at the designated position on the transcript
    // use Integer instead of int, in order to detect null in the calling method
    public Integer read(int index) {
        int maxIndex = this.transcriptList.size();
        if(index >= maxIndex || index < 0) {
            return null;
        }
        return this.transcriptList.get(index);
    }

    // rewrite the given bead to the designated index on the transcript
    public boolean write(int bead, int index) {
        int maxIndex = this.transcriptList.size();
        if(0 <= index && index < maxIndex) {
            this.transcriptList.set(index, bead);
            return true;
        } else if(index == maxIndex + 1) {
            this.transcriptList.add(bead);
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
        int maxIndex = this.transcriptList.size();
        if(index >= maxIndex || index < 0) {
            return false;
        }
        this.idx = index;
        return true;
    }

    // return the transcript list
    public List<Integer> getList() {
        return this.transcriptList;
    }

    // determine wheter the given transcript is the same
    public boolean isSame(Transcript transcript) {
        List<Integer> transcriptList = transcript.getList();
        return this.transcriptList.equals(transcriptList);
    }

    // construct new transcript
    private void constructTranscript(boolean isWrite) {
        this.transcriptList = new ArrayList<Integer>();
        this.idx = 0;
        this.isWrite = isWrite;
    }
}