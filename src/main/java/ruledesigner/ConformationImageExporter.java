import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;

public class ConformationImageExporter {

    public BufferedImage exportToImage(Conformation conformation, int beadRadius) {
        int width = 400, height = 400;
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = image.createGraphics();
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, width, height);

        for (int i = 0; i < conformation.getLength(); i++) {
            Point p = conformation.getPoint(i);
            Bead bead = conformation.getBead(i);
            int x = width / 2 + p.getX() * beadRadius * 2;
            int y = height / 2 + p.getY() * beadRadius * 2;

            g.setColor(Color.BLUE);
            g.fillOval(x - beadRadius, y - beadRadius, beadRadius * 2, beadRadius * 2);
            g.setColor(Color.BLACK);
            g.drawString(bead.getBeadName(), x - beadRadius / 2, y);
        }
        g.dispose();
        return image;
    }

    public void saveImage(BufferedImage image, String filePath) throws Exception {
        ImageIO.write(image, "png", new File(filePath));
    }
}