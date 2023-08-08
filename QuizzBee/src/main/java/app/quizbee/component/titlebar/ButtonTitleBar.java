package app.quizbee.component.titlebar;

import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import javax.swing.JButton;
import javax.swing.border.EmptyBorder;

public class ButtonTitleBar extends JButton {

    public ButtonTitleBar() {
        this.setContentAreaFilled(false);
        this.setBorder(new EmptyBorder(6, 6, 6, 6));
        this.setCursor(new Cursor(Cursor.HAND_CURSOR));
        this.setText(null);
        this.setEnabled(true);
    }

    @Override
    public void paint(Graphics grphcs) {
        super.paint(grphcs);
        Graphics2D g2 = (Graphics2D) grphcs.create();
        
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);

        int width = getWidth();
        int height = getHeight();

        int size = Math.min(width, height);

        int x = (width - size) / 2;
        int y = (height - size) / 2;

        g2.setColor(getBackground());
        g2.fillOval(x, y, size, size);
    }
}
