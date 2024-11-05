package ruledesigner.main;

import java.util.ArrayList;
import java.util.List;

// class of transcript
public class Transcript {
    private List<Bead> transcriptList;
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
    // if forward=true, move the idx forward
    public Bead read(boolean forward) {
        Bead bead = this.transcriptList.get(this.idx);
        if(forward) {
            this.idx++;
        }
        return bead;
    }

    // read one bead from the transcript, and move the idx forward
    public Bead read() {
        return this.read(true);
    }

    // read one bead at the designated position on the transcript
    // use Integer instead of int, in order to detect null in the calling method
    public Bead read(int index) {
        int maxIndex = this.transcriptList.size();
        if(index >= maxIndex || index < 0) {
            return null;
        }
        return this.transcriptList.get(index);
    }

    // rewrite the given bead to the designated index on the transcript
    public boolean write(Bead bead, int index) {
        if(!isWrite) {
            return false;
        }
        int maxIndex = this.transcriptList.size();
        if(0 <= index && index < maxIndex) {
            this.transcriptList.set(index, bead);
        } else if(index == maxIndex + 1) {
            this.transcriptList.add(bead);
            this.idx++;
        }
        return true;
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
    public List<Bead> getList() {
        return this.transcriptList;
    }

    // determine whether the given transcript is the same
    public boolean isSame(Transcript transcript) {
        List<Bead> transcriptList = transcript.getList();
        return this.transcriptList.equals(transcriptList);
    }

    // return the length of the transcript
    public int getLength() {
        return this.transcriptList.size();
    }

    // construct new transcript
    private void constructTranscript(boolean isWrite) {
        this.transcriptList = new ArrayList<Bead>();
        this.idx = 0;
        this.isWrite = isWrite;
    }
}