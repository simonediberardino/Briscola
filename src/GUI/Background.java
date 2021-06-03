package GUI;

import javax.swing.*;
import java.awt.*;

public class Background extends JPanel{
    private Image img;

    public Background(String img) {
        this(new ImageIcon(Background.class.getResource(img)).getImage());
    }

    public Background(Image img) {
        this.img = img;
        Dimension size = new Dimension(img.getWidth(null), img.getHeight(null));
        this.setPreferredSize(size);
        this.setMinimumSize(size);
        this.setMaximumSize(size);
        this.setSize(size);
        this.setLayout(null);
    }

    public void paintComponent(Graphics g) {
        g.drawImage(img, 0, 0, null);
    }
}