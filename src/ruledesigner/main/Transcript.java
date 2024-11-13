package ruledesigner.main;

import java.util.ArrayList;
import java.util.List;

// class of transcript
public class Transcript {
    private List<Bead> transcriptList;
    private int idx;
    private boolean isWrite;

    // constructor with writable mode
    public Transcript(boolean isWrite) {
        this.constructTranscript(isWrite);
    }

    // constructor with writable mode (set to read-only)
    public Transcript() {
        this.constructTranscript(false);
    }

    // constructor with String array which defines the transcript
    public Transcript(String[] tsc, boolean isWrite) {
        // construct the list of the transcript
        this.transcriptList = new ArrayList<>();
        for (String s : tsc) {
            Bead bead = new Bead(s);
            this.transcriptList.add(bead);
        }

        // initialize the attributes of this instance
        this.idx = 0;
        this.isWrite = isWrite;
    }

    // read one bead from the transcript
    // if forward=true, move the idx forward
    public Bead read(boolean forward) {
        Bead bead = this.read(this.idx);

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
        } else if(index == maxIndex) {
            this.transcriptList.add(bead);
            this.idx++;
        } else {
            return false;
        }
        return true;
    }

    // add the given bead to the end of the transcript
    public boolean write(Bead bead) {
        int indexLast = this.transcriptList.size();
        return write(bead, indexLast);
    }

    // get the index of the instance
    public int getIndex() {
        return this.idx;
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
        int length = transcriptList.size();
        List<Bead> transcriptList2 = transcript.getList();

        if (length != transcriptList2.size()) {
            return false;
        }

        for (int i = 0; i < length; i++) {
            Bead bead1 = transcriptList.get(i);
            Bead bead2 = transcriptList2.get(i);
            if (!bead1.isSame(bead2)) {
                return false;
            }
        }

        return true;
    }

    // return the length of the transcript
    public int getLength() {
        return this.transcriptList.size();
    }

    // construct new transcript
    private void constructTranscript(boolean isWrite) {
        this.transcriptList = new ArrayList<>();
        this.idx = 0;
        this.isWrite = isWrite;
    }
}