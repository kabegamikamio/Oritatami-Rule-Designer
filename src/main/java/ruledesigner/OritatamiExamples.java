package ruledesigner;

import java.awt.image.BufferedImage;

public class OritatamiExamples {
    public static void main(String[] args) {
        OritatamiExamples examples = new OritatamiExamples();
        examples.gliderExample();
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
        seedConformation.add(g4, p = p.getAdjacentOf("SE"));
        seedConformation.add(g5, p = p.getAdjacentOf("SW"));

        BondingRule bondingRule = new BondingRule();
        bondingRule.add(new Bond(g0, g5));
        bondingRule.add(new Bond(g1, g4));
        bondingRule.add(new Bond(g2, g3));

        Transcript transcript = new Transcript();
        for(int i=0; i<3; i++) {
            transcript.add(g0);
            transcript.add(g1);
            transcript.add(g2);
            transcript.add(g3);
            transcript.add(g4);
            transcript.add(g5);
        }

        OritatamiTemp oritatami = new OritatamiTemp(transcript, seedConformation, bondingRule);
        Conformation result = oritatami.run();

        ConformationImageExporter exporter = new ConformationImageExporter();
        BufferedImage image = exporter.exportToImage(result, 6, 10, bondingRule);
        try{
            exporter.saveImage(image, "glider_example.png");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
