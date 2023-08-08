package app.quizbee.gui;

import app.quizbee.main.Application;
import app.quizbee.material.panel.CurvesPanel;
import app.quizbee.material.panel.TransparentPanel;
import app.quizbee.material.progress.ProgressBar;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
import net.miginfocom.swing.MigLayout;
import org.jdesktop.animation.timing.Animator;
import org.jdesktop.animation.timing.TimingTarget;
import org.jdesktop.animation.timing.TimingTargetAdapter;

public class LoadingScreen extends JDialog {

    private final MigLayout layout = new MigLayout("fill, ins 0",
            "0[fill]0", "0[fill]0");
    private Animator open;
    private Timer timer;
    private CurvesPanel screen;
    private ProgressBar progress;

    public LoadingScreen(Frame owner, boolean modal) {
        super(owner, modal);
        setUndecorated(true);
        setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
        initComponents();
        setLocationRelativeTo(null);
    }

    private void initComponents() {
        this.setOpacity(0.0f);
        this.setType(Type.POPUP);
        this.setMinimumSize(Toolkit.getDefaultToolkit().getScreenSize());

        screen = new CurvesPanel();
        screen.setLayout(layout);

        progress = new ProgressBar();

        this.add(screen);

        screen.add(progress, "south, c, w 500!, h 20!, gapbottom 50");

        makeAnimation();
        timer = new Timer(20, new ActionListener() {
            private int count = 0;

            @Override
            public void actionPerformed(ActionEvent e) {
                progress.setValue(count++);
                if (progress.getValue() >= 100) {
                    flicker("Click here to continue!");
                }
            }
        });
    }

    @Override
    public void setVisible(boolean b) {
        super.setVisible(b);
        if (b && !open.isRunning()) {
            open.start();
            timer.start();
        }
    }

    private void makeAnimation() {
        open = new Animator(800, new TimingTargetAdapter() {
            @Override
            public void begin() {
                super.begin();
                setOpacity(0.0f);
            }

            @Override
            public void timingEvent(float fraction) {
                super.timingEvent(fraction);
                setOpacity(fraction);
            }

            @Override
            public void end() {
                super.end();
                setOpacity(1.0f);
            }
        });

        open.setResolution(0);
        open.setAcceleration(0.5f);
        open.setDeceleration(0.5f);
    }

    private void flicker(String value) {
        TransparentPanel flickPane = new TransparentPanel();

        flickPane.setOpaque(false);
        flickPane.setAlpha(1f);
        flickPane.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));

        JLabel label = new JLabel(value);
        label.setCursor(new Cursor(Cursor.HAND_CURSOR));
        label.setForeground(Color.white);

        flickPane.add(label);

        TimingTarget timing = new TimingTargetAdapter() {
            @Override
            public void timingEvent(float fraction) {
                super.timingEvent(fraction);
                flickPane.setAlpha(fraction);
            }
        };

        Animator animate = new Animator(1000, timing);
        animate.setResolution(0);
        animate.setAcceleration(0.5f);
        animate.setDeceleration(0.5f);

        new Timer(1000, (e) -> {
            if (!animate.isRunning()) {
                animate.start();
            }
        }).start();

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (SwingUtilities.isLeftMouseButton(e)
                        && e.getClickCount() == 1) {
                    dispose();
                    Application.getDashboard().setVisible(true);
                }
            }
        });

        screen.add(flickPane, "south, c, w 500!, h 20!, gapbottom 10");
        screen.revalidate();
        timer.stop();
    }
}
