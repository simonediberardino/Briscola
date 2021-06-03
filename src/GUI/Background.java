package GUI;

import javax.swing.*;
import java.awt.*;

public class Background extends JPanel {
    private Image img;

    public Background(String img) {
        this(new ImageIcon(Background.class.getResource(img)).getImage());
    }

    public Background(Image img) {
        this.img = img;
        Dimension size = new Dimension(img.getWidth(null), img.getHeight(null));
        setPreferredSize(size);
        setMinimumSize(size);
        setMaximumSize(size);
        setSize(size);
        setLayout(null);
    }

    public void paintComponent(Graphics g) {
        g.drawImage(img, 0, 0, null);
    }
}