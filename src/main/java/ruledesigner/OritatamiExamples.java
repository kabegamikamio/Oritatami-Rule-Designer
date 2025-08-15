package ruledesigner;

import java.awt.image.BufferedImage;

public class OritatamiExamples {
    public static void main(String[] args) {
        OritatamiExamples examples = new OritatamiExamples();
        // examples.gliderExample();
        examples.ANDExample(true, true);
    }

    public void gliderExample() {
        Bead g0 = new Bead("G0");
        Bead g1 = new Bead("G1");
        Bead g2 = new Bead("G2");
        Bead g3 = new Bead("G3");
        Bead g4 = new Bead("G4");
        Bead g5 = new Bead("G5");

        Conformation seedConformation = new Conformation();
        Point p = new Point(0, 0);
        seedConformation.add(g0, p);
        seedConformation.add(g1, p = p.getAdjacentOf("NE"));
        seedConformation.add(g2, p = p.getAdjacentOf("NW"));
        seedConformation.add(g3, p = p.getAdjacentOf("E"));

        BondingRule bondingRule = new BondingRule();
        bondingRule.add(new Bond(g0, g5));
        bondingRule.add(new Bond(g2, g3));

        Transcript transcript = new Transcript();
        for(int i=0; i<5; i++) {
            if(i != 0) {
                transcript.add(g0);
                transcript.add(g1);
                transcript.add(g2);
                transcript.add(g3);
            }
            transcript.add(g4);
            transcript.add(g5);
        }

        runExample(transcript, seedConformation, bondingRule, "glider_example");
    }

    private void ANDExample(boolean input1, boolean input2) {
        BondingRule bondingRule = new BondingRule();
        Conformation seedConformation = new Conformation();
        Transcript transcript = new Transcript();

        // Define the beads for the ceiling
        Bead C0 = new Bead("C0");
        Bead C1 = new Bead("C1");
        Bead C2 = new Bead("C2");
        Bead C3 = new Bead("C3");
        Bead C4 = new Bead("C4");
        Bead C5 = new Bead("C5");
        Bead C11 = new Bead("C11");
        Bead C12 = new Bead("C12");
        Bead C13 = new Bead("C13");
        Bead C14 = new Bead("C14");

        Bead[] C_False = new Bead[]{C0, C1, C2, C3, C4, C5};
        Bead[] C_True = new Bead[]{C0, C11, C12, C13, C14, C5};

        // define the beads for the right-most seed
        Bead Hi15 = new Bead("ABS15");
        Bead Hi16 = new Bead("ABS16");
        Bead Hi17 = new Bead("ABS17");

        // define the beads for the reader module
        Bead[] R = new Bead[18];
        for(int i = 0; i < 18; i++) {
            R[i] = new Bead("R" + i);
        }

        // define the beads for the AND module
        Bead[] AND = new Bead[18];
        for(int i = 0; i < 18; i++) {
            AND[i] = new Bead("AND" + i);
        }

        // define the bonding rules of the reader module
        bondingRule.add(new Bond(C1, R[11]));
        bondingRule.add(new Bond(C1, R[12]));
        bondingRule.add(new Bond(C1, R[5]));
        bondingRule.add(new Bond(C2, R[2]));
        bondingRule.add(new Bond(C2, R[5]));
        bondingRule.add(new Bond(C2, R[11]));
        bondingRule.add(new Bond(C3, R[1]));
        bondingRule.add(new Bond(C3, R[2]));
        bondingRule.add(new Bond(C3, R[4]));
        bondingRule.add(new Bond(C5, R[2]));

        bondingRule.add(new Bond(R[0], R[5]));
        bondingRule.add(new Bond(R[0], R[8]));
        bondingRule.add(new Bond(R[1], R[3]));
        bondingRule.add(new Bond(R[1], R[4]));
        bondingRule.add(new Bond(R[1], Hi16));
        bondingRule.add(new Bond(R[2], R[8]));
        bondingRule.add(new Bond(R[2], Hi15));

        bondingRule.add(new Bond(R[3], R[8]));
        bondingRule.add(new Bond(R[4], R[6]));
        bondingRule.add(new Bond(R[4], R[7]));
        bondingRule.add(new Bond(R[5], R[11]));
        bondingRule.add(new Bond(R[5], R[12]));
        bondingRule.add(new Bond(R[5], Hi15));
        bondingRule.add(new Bond(R[5], Hi16));

        bondingRule.add(new Bond(R[6], R[11]));
        bondingRule.add(new Bond(R[7], R[9]));
        bondingRule.add(new Bond(R[9], R[14]));
        bondingRule.add(new Bond(R[10], R[14]));
        bondingRule.add(new Bond(R[12], R[17]));
        bondingRule.add(new Bond(R[15], Hi17));

        bondingRule.add(new Bond(C1, AND[11]));
        bondingRule.add(new Bond(C1, AND[12]));
        bondingRule.add(new Bond(C1, AND[5]));
        bondingRule.add(new Bond(C2, AND[2]));
        bondingRule.add(new Bond(C2, AND[5]));
        bondingRule.add(new Bond(C2, AND[11]));
        bondingRule.add(new Bond(C3, AND[1]));
        bondingRule.add(new Bond(C3, AND[2]));
        bondingRule.add(new Bond(C3, AND[4]));
        bondingRule.add(new Bond(C5, AND[2]));

        bondingRule.add(new Bond(AND[0], AND[5]));
        bondingRule.add(new Bond(AND[0], AND[8]));
        bondingRule.add(new Bond(AND[1], AND[3]));
        bondingRule.add(new Bond(AND[1], AND[4]));
        bondingRule.add(new Bond(AND[1], R[16]));
        bondingRule.add(new Bond(AND[2], AND[8]));
        bondingRule.add(new Bond(AND[2], R[15]));

        bondingRule.add(new Bond(AND[3], AND[8]));
        bondingRule.add(new Bond(AND[4], AND[6]));
        bondingRule.add(new Bond(AND[4], AND[7]));
        bondingRule.add(new Bond(AND[5], AND[11]));
        bondingRule.add(new Bond(AND[5], AND[12]));
        bondingRule.add(new Bond(AND[5], R[15]));
        bondingRule.add(new Bond(AND[5], R[16]));

        bondingRule.add(new Bond(AND[6], AND[11]));
        bondingRule.add(new Bond(AND[7], AND[9]));
        bondingRule.add(new Bond(AND[9], AND[14]));
        bondingRule.add(new Bond(AND[10], AND[14]));
        bondingRule.add(new Bond(AND[12], AND[17]));
        bondingRule.add(new Bond(AND[15], R[17]));

        // define the seed conformation
        Point p = new Point(0, 0);
        seedConformation.add(C5, p);
        seedConformation.add(C0, p = p.getAdjacentOf("E"));
        seedConformation.add(C1, p = p.getAdjacentOf("E"));
        seedConformation.add(C2, p = p.getAdjacentOf("E"));
        seedConformation.add(C3, p = p.getAdjacentOf("E"));
        seedConformation.add(C4, p = p.getAdjacentOf("E"));
        seedConformation.add(C5, p = p.getAdjacentOf("E"));
        seedConformation.add(C0, p = p.getAdjacentOf("E"));
        seedConformation.add(C1, p = p.getAdjacentOf("E"));
        seedConformation.add(C2, p = p.getAdjacentOf("E"));
        seedConformation.add(C3, p = p.getAdjacentOf("E"));
        seedConformation.add(C4, p = p.getAdjacentOf("E"));
        seedConformation.add(C5, p = p.getAdjacentOf("E"));
        seedConformation.add(C0, p = p.getAdjacentOf("E"));

//        seedConformation.add(C0, p = p.getAdjacentOf("E"));
//        seedConformation.add(C0, p = p.getAdjacentOf("E"));
//        seedConformation.add(C0, p = p.getAdjacentOf("SW"));
//        seedConformation.add(C0, p = p.getAdjacentOf("SW"));
//        seedConformation.add(C0, p = p.getAdjacentOf("SE"));
//        seedConformation.add(C0, p = p.getAdjacentOf("W"));
//        seedConformation.add(C0, p = p.getAdjacentOf("NW"));
//        seedConformation.add(C0, p = p.getAdjacentOf("NE"));
//        seedConformation.add(C0, p = p.getAdjacentOf("W"));

        seedConformation.add(Hi15, p = p.getAdjacentOf("SW"));
        seedConformation.add(Hi16, p = p.getAdjacentOf("SW"));
        seedConformation.add(Hi17, p = p.getAdjacentOf("SE"));

        System.out.println("Last Point of Seed Conformation: (" + p.getX() + ", " + p.getY() + ")");

        // define the transcript
        for(int i = 0; i < 18; i++) {
            transcript.add(R[i]);
        }
        for (int i = 0; i < 18; i++) {
            transcript.add(AND[i]);
        }

        ConformationImageExporter exporter = new ConformationImageExporter();
        BufferedImage image = exporter.exportToImage(seedConformation, seedConformation.length, 10, bondingRule);
        try{
            exporter.saveImage(image, "AND_Seed" + ".png");
        } catch (Exception e) {
            e.printStackTrace();
        }

        runExample(transcript, seedConformation, bondingRule, "AND_example_" + input1 + "_" + input2);
    }

    // Example of running the Oritatami algorithm with the glider example
    private void runExample(Transcript transcript, Conformation seedConformation, BondingRule bondingRule, String fileName) {
        Oritatami oritatami = new Oritatami(transcript, seedConformation, 3, true, bondingRule);
        Conformation result = oritatami.executeOritatami();

        ConformationImageExporter exporter = new ConformationImageExporter();
        BufferedImage image = exporter.exportToImage(result, seedConformation.length, 10, bondingRule);
        try{
            exporter.saveImage(image, fileName + ".png");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
