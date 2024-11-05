import java.util.ArrayList;
import java.util.List;

public class Oritatami {
    final private int delay;
    final private boolean isOblivious;

    Oritatami(int delay, boolean isOblivious) {
        this.delay = delay;
        this.isOblivious = isOblivious;
    }

    // execute Oritatami simulation according to the designated dynamics
    public Conformation executeOritatami(Transcript transcript, BondingRule bondingRule,
                                         Conformation initialConformation) {
        if(this.isOblivious) {
            return executeOritatamiOblivious(transcript, bondingRule, initialConformation);
        } else {
            return executeOritatamiInertial(transcript, bondingRule, initialConformation);
        }
    }

    // execute Oritatami simulation in the oblivious dynamics
    private Conformation executeOritatamiOblivious(Transcript transcript, BondingRule bondingRule,
                                                   Conformation initialConformation) {
        // produced conformation
        Conformation conformation = new Conformation();

        // nascent transcribed beads
        List<Transcript> nascent = new ArrayList<>();

        // length of transcript
        int maxIdx = transcript.getLength();

        // array of nascent transcribed beads
        Bead[] nascentBeads = new Bead[delay];

        for(int i = 0; i < maxIdx - delay; i++) {
            // transcribe {delay} beads for the first time
            if(i == 0) {
                // initialize the array of the nascent transcribed beads
                for(int j = 0; j < delay; j++) {
                    nascentBeads[j] = transcript.read(true);
                }
            }

            for(int j = 0; )
        }
    }

    // find locally energy-minimum conformation with oblivious dynamics
    private Conformation

    // execute Oritatami simulation in the inertial dynamics
    // not available atm
    private Conformation executeOritatamiInertial(Transcript transcript, BondingRule bondingRule,
                                                  Conformation initialConformation) {
        Conformation conformation = new Conformation();
        return conformation;
    }
}
