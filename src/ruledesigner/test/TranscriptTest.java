package ruledesigner.test;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import ruledesigner.main.Bead;
import ruledesigner.main.Transcript;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

// Testing class for Transcript class
public class TranscriptTest {
    private Transcript constructTranscript(boolean isWrite) {
        String[] beads = {"bead1", "bead2", "bead3"};
        return new Transcript(beads, isWrite);
    }

    @Test
    public void readTest_Forward() {
        Transcript transcript = constructTranscript(false);

        String bead1 = transcript.read(true).getBeadName();
        String bead2 = transcript.read(true).getBeadName();
        String bead3 = transcript.read(true).getBeadName();
        Bead bead4 = transcript.read(true);

        assertEquals("bead1", bead1);
        assertEquals("bead2", bead2);
        assertEquals("bead3", bead3);
        assertNull(bead4);
    }

    @Test
    public void readTest_NoForward() {
        Transcript transcript = constructTranscript(false);

        String bead1 = transcript.read(false).getBeadName();
        String bead2 = transcript.read(false).getBeadName();

        assertEquals("bead1", bead1);
        assertEquals("bead1", bead2);
    }

    @ParameterizedTest
    @CsvSource(textBlock="""
            0, bead1,
            1, bead2,
            2, bead3,
            """)
    public void readTest_WithIndex(int index, String beadName) {
        Transcript transcript = constructTranscript(false);

        String bead = transcript.read(index).getBeadName();

        assertEquals(beadName, bead);
    }

    @Test
    public void readTest_WithIndex_OutOfRange() {
        Transcript transcript = constructTranscript(false);

        assertNull(transcript.read(3));
    }

    @ParameterizedTest
    @ValueSource(ints = {0, 1, 2, 3})
    public void writeTest_WithIndex(int index) {
        Transcript transcript = constructTranscript(true);
        Bead bead = new Bead("bead");

        boolean result = transcript.write(bead, index);

        assertTrue(result);
        assertEquals("bead", transcript.read(index).getBeadName());
    }

    @Test
    public void writeTest_WithIndex_OutOfRange() {
        Transcript transcript = constructTranscript(true);
        Bead bead = new Bead("bead");

        boolean result = transcript.write(bead, 4);

        assertFalse(result);
    }

    @Test
    public void writeTest_WriteForbidden() {
        Transcript transcript = constructTranscript(false);
        Bead bead = new Bead("bead");

        assertFalse(transcript.write(bead));
    }

    @Test
    public void writeTest_NoIndex() {
        Transcript transcript = constructTranscript(true);
        Bead bead = new Bead("bead");

        boolean result = transcript.write(bead);

        assertTrue(result);
        assertEquals("bead", transcript.read(3).getBeadName());
    }

    @ParameterizedTest
    @ValueSource(ints = {0, 1, 2})
    public void setIndexTest(int index) {
        Transcript transcript = constructTranscript(false);
        boolean result = transcript.setIndex(index);

        assertTrue(result);
        assertEquals(index, transcript.getIndex());
    }

    @Test
    public void setIndexTest_OutOfRange() {
        Transcript transcript = constructTranscript(false);
        boolean result = transcript.setIndex(3);

        // transcript.index should be remained, thus 0.
        assertFalse(result);
        assertEquals(0, transcript.getIndex());
    }

    @Test
    public void getListTest() {
        Transcript transcript = constructTranscript(false);
        List<Bead> list = transcript.getList();
        int i = 1;

        for (Bead bead : list) {
            assertEquals("bead" + i++, bead.getBeadName());
        }
    }

    @Test
    public void isSameTest_Same() {
        Transcript transcript1 = constructTranscript(false);
        Transcript transcript2 = constructTranscript(false);

        assertTrue(transcript1.isSame(transcript2));
        assertTrue(transcript2.isSame(transcript1));
    }

    @Test
    public void isSameTest_Different() {
        String[] beads = {"bead1", "bead2"};
        Transcript transcript1 = constructTranscript(false);
        Transcript transcript2 = new Transcript(beads, false);

        assertFalse(transcript1.isSame(transcript2));
        assertFalse(transcript2.isSame(transcript1));
    }

    @Test
    public void getLengthTest() {
        Transcript transcript = constructTranscript(false);

        assertEquals(3, transcript.getLength());
    }
}
