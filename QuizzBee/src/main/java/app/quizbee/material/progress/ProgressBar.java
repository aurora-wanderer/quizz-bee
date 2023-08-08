package app.quizbee.material.progress;

import java.awt.Color;
import java.awt.Dimension;
import javax.swing.JProgressBar;

public class ProgressBar extends JProgressBar {
    
    public ProgressBar() {
        setPreferredSize(new Dimension(100, 5));
        setBackground(Color.white);
        setOpaque(false);
        setUI(new FancyProgressBarUI());
    }
}
