package app.quizbee.material.progress;

import javax.swing.JProgressBar;

public class ProgressCircle extends JProgressBar {

    private final ProgressCircleUI progressUI;

    public ProgressCircle() {
        setOpaque(false);
        setStringPainted(true);
        progressUI = new ProgressCircleUI();
        setUI(progressUI);
    }

    @Override
    public void updateUI() {
        super.updateUI();
        setUI(progressUI);
    }

    public void start() {
        progressUI.start();
    }
}
