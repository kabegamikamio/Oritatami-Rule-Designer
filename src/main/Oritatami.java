import java.util.ArrayList;
import java.util.List;

public class Oritatami {
    final private int delay;
    final private boolean isOblivious;

    Oritatami(int delay, boolean isOblivious) {
        this.delay = delay;
        this.isOblivious = isOblivious;
    }

    /**
     * This method executes Oritatami simulation by either oblivious or inertial dynamics.
     * Users can define the Oritatami by giving the delay, transcript, set of bonding rules, initial conformation and dynamics.
     * @param transcript
     * @param bondingRule
     * @param initialConformation
     * @return final conformation as the output of the Oritatami
     */
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
    private Conformation findEnergyMinimumConformation(Conformation currentConformation, Bead[] beads) {
        Conformation conformation = new Conformation();
        return conformation;
    }

    // execute Oritatami simulation in the inertial dynamics
    // not available atm
    private Conformation executeOritatamiInertial(Transcript transcript, BondingRule bondingRule,
                                                  Conformation initialConformation) {
        Conformation conformation = new Conformation();
        return conformation;
    }
}
