package ruledesigner;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class OritatamiTempTest {

    @Test
    void constructorReturnsCorrectLengthForEmptyTranscript() {
        Transcript transcript = mock(Transcript.class);
        Conformation seedConformation = mock(Conformation.class);
        BondingRule bondingRule = mock(BondingRule.class);

        when(transcript.getLength()).thenReturn(0);
        when(seedConformation.getLength()).thenReturn(1);
        when(seedConformation.getBead(0)).thenReturn(new Bead("Seed"));
        when(seedConformation.getPoint(0)).thenReturn(new Point(0, 0));

        OritatamiTemp oritatami = new OritatamiTemp(transcript, seedConformation, bondingRule);
        Conformation result = oritatami.run();
        assertNotNull(result);
        assertEquals(1, result.getLength());
        assertEquals("Seed", result.getBead(0).getBeadName());
    }

    @Test
    void runReturnsCorrectConformationForValidTranscript() {
        Transcript transcript = mock(Transcript.class);
        Conformation seedConformation = mock(Conformation.class);
        BondingRule bondingRule = mock(BondingRule.class);

        when(transcript.getLength()).thenReturn(3);
        when(transcript.read(0)).thenReturn(new Bead("A"));
        when(transcript.read(1)).thenReturn(new Bead("B"));
        when(transcript.read(2)).thenReturn(new Bead("C"));

        when(seedConformation.getLength()).thenReturn(1);
        when(seedConformation.getBead(0)).thenReturn(new Bead("Seed"));
        when(seedConformation.getPoint(0)).thenReturn(new Point(0, 0));

        when(bondingRule.ifContains(any(Bond.class))).thenReturn(true);

        OritatamiTemp oritatami = new OritatamiTemp(transcript, seedConformation, bondingRule);
        Conformation result = oritatami.run();

        assertNotNull(result);
        assertEquals(4, result.getLength());
    }

    @Test
    void runHaltsWhenNoValidPositionIsAvailable() {
        Transcript transcript = mock(Transcript.class);
        Conformation seedConformation = mock(Conformation.class);
        BondingRule bondingRule = mock(BondingRule.class);

        when(transcript.getLength()).thenReturn(1);
        when(transcript.read(0)).thenReturn(new Bead("A"));

        when(seedConformation.getLength()).thenReturn(1);
        when(seedConformation.getBead(0)).thenReturn(new Bead("Seed"));
        when(seedConformation.getPoint(0)).thenReturn(new Point(0, 0));

        when(bondingRule.ifContains(any(Bond.class))).thenReturn(false);

        OritatamiTemp oritatami = new OritatamiTemp(transcript, seedConformation, bondingRule);
        Conformation result = oritatami.run();

        assertNotNull(result);
        assertNull(result.getBead(1));
        assertEquals("Seed", result.getBead(0).getBeadName());
        assertEquals(1, result.getLength());
    }

    @Test
    void runHandlesEmptyTranscriptGracefully() {
        Transcript transcript = mock(Transcript.class);
        Conformation seedConformation = mock(Conformation.class);
        BondingRule bondingRule = mock(BondingRule.class);

        when(transcript.getLength()).thenReturn(0);

        OritatamiTemp oritatami = new OritatamiTemp(transcript, seedConformation, bondingRule);
        Conformation result = oritatami.run();

        assertNotNull(result);
        assertEquals(seedConformation.getLength(), result.getLength());
    }
}