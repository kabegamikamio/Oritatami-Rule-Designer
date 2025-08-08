package ruledesigner;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;
import java.util.*;
import java.util.List;

public class ConformationImageExporter {

    public static void main(String[] args) {
        try {
            Conformation conformation = new Conformation();
            conformation.add(new Bead("A0"), new Point(0, 0));
            conformation.add(new Bead("A1"), new Point(0, 1));
            conformation.add(new Bead("A2"), new Point(1, 0));
            conformation.add(new Bead("A3"), new Point(1, 1));

            BondingRule bondingRule = new BondingRule();
            bondingRule.add(new Bond(conformation.getBead(0), conformation.getBead(2)));
            bondingRule.add(new Bond(conformation.getBead(1), conformation.getBead(3)));

            Transcript transcript = new Transcript();
            transcript.add(new Bead("A0"));
            transcript.add(new Bead("A1"));
            transcript.add(new Bead("A2"));
            transcript.add(new Bead("A3"));

            Conformation conformation1 = new OritatamiTemp(transcript, conformation, bondingRule).run();

            ConformationImageExporter exporter = new ConformationImageExporter();
            BufferedImage image = exporter.exportToImage(conformation1, conformation.getLength(), 10, bondingRule);
            exporter.saveImage(image, "conformation.png");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static final Color[] MATERIAL_COLORS = {
            new Color(244, 67, 54), new Color(33, 150, 243), new Color(76, 175, 80),
            new Color(255, 235, 59), new Color(156, 39, 176), new Color(255, 152, 0),
            new Color(0, 188, 212), new Color(205, 220, 57), new Color(121, 85, 72),
            new Color(158, 158, 158)
    };
    private final Map<String, Color> beadColorMap = new HashMap<>();

    private Color getBeadColor(String label) {
        return beadColorMap.computeIfAbsent(label, l -> MATERIAL_COLORS[beadColorMap.size() % MATERIAL_COLORS.length]);
    }

    private Point toTriangularLattice(Point p, int spacing) {
        int dx = (int) (p.getX() * spacing + p.getY() * spacing * Math.cos(Math.toRadians(60)));
        int dy = (int) (p.getY() * spacing * Math.sin(Math.toRadians(60)));
        return new Point(dx, dy);
    }

    public BufferedImage exportToImage(Conformation conformation, int seedLength, int beadRadius, BondingRule bondingRule) {
        int spacing = beadRadius * 3;
        int width = 400, height = 400;
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = image.createGraphics();
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, width, height);

        drawFoldingPath(g, conformation,seedLength, beadRadius, spacing, width, height);
        drawBonds(g, conformation, beadRadius, spacing, bondingRule, width, height);
        drawBeads(g, conformation, beadRadius, spacing, width, height);

        g.dispose();
        return image;
    }

    private void drawFoldingPath(Graphics2D g, Conformation conformation, int seedLength, int beadRadius, int spacing, int width, int height) {

        for (int i = 1; i < conformation.getLength(); i++) {
            g.setColor(i < seedLength ? Color.GREEN : Color.BLACK);
            g.setStroke(new BasicStroke(beadRadius * 0.5f));
            Point p1 = toTriangularLattice(conformation.getPoint(i - 1), spacing);
            Point p2 = toTriangularLattice(conformation.getPoint(i), spacing);
            g.drawLine(width / 2 + p1.getX(), height / 2 - p1.getY(), width / 2 + p2.getX(), height / 2 - p2.getY());
        }
    }

    private void drawBonds(Graphics2D g, Conformation conformation, int beadRadius, int spacing, BondingRule bondingRule, int width, int height) {
        g.setColor(Color.BLACK);
        float bondStroke = beadRadius * 0.125f;
        g.setStroke(new BasicStroke(bondStroke, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 10.0f, new float[]{bondStroke * 2, bondStroke * 2}, 0.0f));

        for(int i = 0; i< conformation.getLength(); i++) {
            Bead bead = conformation.getBead(i);
            Point p = conformation.getPoint(i);

            List<Point> adjacentPoints = p.getAdjacentPoints();

            System.out.println("Adjacent:");
            for (Point adj : adjacentPoints) {
                int idx = conformation.indexOfPoint(adj);
                if (idx != -1 && idx > i) {
                    System.out.println(adj.getX() + ", " + adj.getY());
                    Bead adjacentBead = conformation.getBead(idx);
                    Bond bond = new Bond(bead, adjacentBead);
                    if (bondingRule.ifContains(bond)) {
                        System.out.println("Bond found: " + bead.getBeadName() + " - " + adjacentBead.getBeadName());
                        Point adjPoint = toTriangularLattice(adj, spacing);
                        Point pt = toTriangularLattice(p, spacing);
                        g.drawLine(width / 2 + pt.getX(), height / 2 - pt.getY(), width / 2 + adjPoint.getX(), height / 2 - adjPoint.getY());
                    }
                }
            }
        }
    }

    private void drawBeads(Graphics2D g, Conformation conformation, int beadRadius, int spacing, int width, int height) {
        for (int i = 0; i < conformation.getLength(); i++) {
            Point p = toTriangularLattice(conformation.getPoint(i), spacing);
            Bead bead = conformation.getBead(i);
            int x = width / 2 + p.getX();
            int y = height / 2 - p.getY();

            g.setColor(getBeadColor(bead.getBeadName()));
            g.fillOval(x - beadRadius, y - beadRadius, beadRadius * 2, beadRadius * 2);

            g.setColor(Color.BLACK);
            g.setStroke(new BasicStroke(2));
            g.drawOval(x - beadRadius, y - beadRadius, beadRadius * 2, beadRadius * 2);

            drawLabel(g, bead.getBeadName(), x, y, beadRadius);
        }
    }

    private void drawLabel(Graphics2D g, String label, int x, int y, int beadRadius) {
        int fontSize = beadRadius;
        Font font;
        FontMetrics metrics;
        int textWidth, textHeight;
        do {
            font = new Font("SansSerif", Font.BOLD, fontSize);
            g.setFont(font);
            metrics = g.getFontMetrics(font);
            textWidth = metrics.stringWidth(label);
            textHeight = metrics.getHeight();
            fontSize--;
        } while ((textWidth > beadRadius * 1.7 || textHeight > beadRadius * 1.7) && fontSize > 6);

        g.setColor(Color.WHITE);
        g.drawString(label, x - textWidth / 2, y + textHeight / 2 - metrics.getDescent());
    }

    public void saveImage(BufferedImage image, String filePath) throws Exception {
        ImageIO.write(image, "png", new File(filePath));
    }
}