package app.quizbee.component.menu;

import app.quizbee.system.Colors;
import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;
import java.awt.geom.RoundRectangle2D;
import javax.swing.JButton;
import javax.swing.border.EmptyBorder;
import org.jdesktop.animation.timing.Animator;
import org.jdesktop.animation.timing.TimingTarget;
import org.jdesktop.animation.timing.TimingTargetAdapter;

public class ButtonMenu extends JButton {

    private int targetSize;
    private float animatSize;
    private Point pressedPoint;
    private float alpha;
    private int index;
    private Color effectColor = new Color(173, 173, 173);
    private Animator animator;

    public ButtonMenu() {
        setCursor(new Cursor(Cursor.HAND_CURSOR));
        setContentAreaFilled(false);
        setForeground(Colors.FG_MENU_ITEM);
        setOpaque(false);
        setBorder(new EmptyBorder(10, 28, 10, 0));
        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent me) {
                targetSize = Math.max(getWidth(), getHeight()) * 2;
                animatSize = 0;
                pressedPoint = me.getPoint();
                alpha = 0.5f;
                if (animator.isRunning()) {
                    animator.stop();
                }
                animator.start();
            }
        });
        TimingTarget target = new TimingTargetAdapter() {
            @Override
            public void timingEvent(float fraction) {
                if (fraction > 0.5f) {
                    alpha = 1 - fraction;
                }
                animatSize = fraction * targetSize;
                repaint();
            }
        };

        animator = new Animator(400, target);
        animator.setResolution(0);
        animator.setAcceleration(0.5f);
        animator.setDeceleration(0.5f);
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(getBackground());
        if (pressedPoint != null) {
            Area area = createShape();
            g2.setColor(effectColor);
            g2.setComposite(AlphaComposite.getInstance(
                    AlphaComposite.SRC_ATOP, alpha));
            area.intersect(new Area(
                    new Ellipse2D.Double(pressedPoint.x - animatSize / 2,
                            pressedPoint.y - animatSize / 2,
                            animatSize, animatSize)));
            g2.fill(area);
        }
        g2.setComposite(AlphaComposite.SrcOver);
        super.paintComponent(g);
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    private Area createShape() {
        int width = getParent().getParent().getWidth();
        int height = getHeight();
        int r = 15;
        Area area = new Area(new RoundRectangle2D.Float(15, 0,
                width - 35, height, r, r));
        return area;
    }
}
