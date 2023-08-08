package app.quizbee.dialog.loading;

import app.quizbee.material.panel.TransparentPanel;
import app.quizbee.system.SVGIcon;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.geom.AffineTransform;

public class Loading extends TransparentPanel {

    private final Image icon = SVGIcon.LOADING.getImage();
    private int rad = 0;

    public Loading() {
        super.setVisible(false);
        setOpaque(false);
        setFocusCycleRoot(true);
        setFocusable(true);
        addMouseListener(new MouseAdapter() {
        });
        setAlpha(0.5f);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g.create();

        int w = icon.getWidth(null) / 2;
        int h = icon.getHeight(null) / 2;

        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);

        AffineTransform at = AffineTransform
                .getTranslateInstance(getWidth() / 2 - w,
                        getHeight() / 2 - h);

        at.rotate(Math.toRadians(rad++), w, h);
        g2.drawImage(SVGIcon.LOADING.getImage(), at, null);
        repaint();
    }
}
