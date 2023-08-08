package app.quizbee.material.panel;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import javax.swing.JPanel;

public class RoundedPanel extends JPanel {

    private int corner;

    public RoundedPanel() {
        this.corner = 15;
        setOpaque(false);
    }

    public RoundedPanel(int corner) {
        this();
        this.corner = corner;
        revalidate();
        repaint();
    }

    @Override
    public void paint(Graphics grphcs) {
        Graphics2D g2 = (Graphics2D) grphcs.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        int width = getWidth();
        int height = getHeight();
        g2.setColor(getBackground());
        g2.fillRoundRect(0, 0, width, height, corner, corner);
        g2.dispose();
        super.paint(grphcs);
    }

    public void setCorner(int corner) {
        this.corner = corner;
        revalidate();
        repaint();
    }
}
