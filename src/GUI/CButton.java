package GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;

public class CButton extends JButton {
    public CButton(String title, Integer width, Integer height, Integer fontSize, boolean isClickable){
        super(title);

        this.setVerticalAlignment(SwingConstants.CENTER);
        this.setVerticalTextPosition(SwingConstants.CENTER);
        this.setHorizontalAlignment(SwingConstants.CENTER);
        this.setHorizontalTextPosition(SwingConstants.CENTER);
        this.setBorder(BorderFactory.createEmptyBorder(2, 8, 2, 8));
        this.setMargin(new Insets(2, 8, 2, 8));
        this.setContentAreaFilled(false);
        this.setFocusPainted(false);
        this.setOpaque(false);
        this.setFont(new Font("Calibri", Font.BOLD, fontSize));
        this.setForeground(Color.WHITE);
        this.setIcon(new TranslucentButtonIcon());
        this.setPreferredSize(new Dimension(width, height));

        if(!isClickable)
            this.setEnabled(false);
    }

    static class TranslucentButtonIcon implements Icon {
        private static final int R = 8;
        private int width;
        private int height;

        @Override public void paintIcon(Component c, Graphics g, int x, int y) {
            if (c instanceof AbstractButton) {
                AbstractButton b = (AbstractButton) c;
                Insets i = b.getMargin();
                int w = c.getWidth();
                int h = c.getHeight();
                width  = w - i.left - i.right;
                height = h - i.top - i.bottom;
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                        RenderingHints.VALUE_ANTIALIAS_ON);
                Shape area = new RoundRectangle2D.Float(x - i.left, y - i.top, w - 1, h - 1, R, R);
                Color color = new Color(0f, 0f, 0f, .3f);
                ButtonModel m = b.getModel();
                if (m.isPressed()) {
                    color = new Color(0f, 0f, 0f, .3f);
                } else if (m.isRollover()) {
                    color = new Color(1f, 1f, 1f, .3f);
                }
                g2.setPaint(color);
                g2.fill(area);
                g2.setPaint(Color.WHITE);
                g2.draw(area);
                g2.dispose();
            }
        }
        @Override public int getIconWidth()  {
            return Math.max(width, 100);
        }
        @Override public int getIconHeight() {
            return Math.max(height, 24);
        }
    }
}