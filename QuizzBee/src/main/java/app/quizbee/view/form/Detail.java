package app.quizbee.view.form;

import app.quizbee.system.SVGIcon;
import com.formdev.flatlaf.extras.components.FlatButton;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.text.DecimalFormat;
import javax.swing.JLabel;
import javax.swing.JPanel;
import net.miginfocom.swing.MigLayout;

public class Detail extends JPanel {

    private final DecimalFormat df = new DecimalFormat("##0.###");
    private ActionListener actionEvent, closeEvent;
    private final MigLayout layout;
    private JLabel title;
    private JLabel description;
    private JLabel description1;
    private FlatButton button, closeButton;
    private boolean isLogin;

    public Detail() {
        setOpaque(false);
        setFocusCycleRoot(true);
        addMouseListener(new MouseAdapter() {
        });
        layout = new MigLayout("wrap, fill",
                "[center]",
                "push[]25[]10[]25[]push");
        setLayout(layout);
        setFont(new Font("Seoge UI", Font.PLAIN, 12));
        init();
    }

    private void init() {
        title = new JLabel("Welcome Back !!!");
        title.setFont(getFont().deriveFont(Font.BOLD, 35));
        add(title);
        description = new JLabel("To keep connected with us please");
        add(description);
        description1 = new JLabel("Login with your personal info");
        add(description1);
        button = new FlatButton();
        button.setButtonType(FlatButton.ButtonType.roundRect);
        button.setContentAreaFilled(false);
        button.setOpaque(false);
        button.setText("Don't have an account?");
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setForeground(Color.blue);
        button.addActionListener((ActionEvent ae) -> {
            actionEvent.actionPerformed(ae);
        });

        add(button, "w 60%, h 40");

        closeButton = new FlatButton();
        closeButton.setContentAreaFilled(false);
        closeButton.setOpaque(false);
        closeButton.setIcon(SVGIcon.CLOSE);
        closeButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        closeButton.addActionListener((ActionEvent e) -> {
            closeEvent.actionPerformed(e);
        });

        add(closeButton, "pos 10 10 n n");
    }

    @Override
    protected void paintComponent(Graphics grphcs) {
        Graphics2D g2 = (Graphics2D) grphcs;
        GradientPaint gra
                = new GradientPaint(0, 0, new Color(76, 191, 151),
                        0, getHeight(), new Color(58, 167, 174));
        g2.setPaint(gra);
        g2.fillRect(0, 0, getWidth(), getHeight());
        super.paintComponent(grphcs);
    }

    public void addActionEvent(ActionListener actionEvent) {
        this.actionEvent = actionEvent;
    }

    public void addCloseEvent(ActionListener closeEvent) {
        this.closeEvent = closeEvent;
    }

    public void registerLeft(double v) {
        v = Double.parseDouble(df.format(v));
        login(false);
        layout.setComponentConstraints(title,
                "pad 0 -" + v + "% 0 0");
        layout.setComponentConstraints(description,
                "pad 0 -" + v + "% 0 0");
        layout.setComponentConstraints(description1,
                "pad 0 -" + v + "% 0 0");
        layout.setComponentConstraints(closeButton,
                "pos 100%+10-" + v + " 10 n n");
    }

    public void registerRight(double v) {
        v = Double.parseDouble(df.format(v));
        login(false);
        layout.setComponentConstraints(title,
                "pad 0 -" + v + "% 0 0");
        layout.setComponentConstraints(description,
                "pad 0 -" + v + "% 0 0");
        layout.setComponentConstraints(description1,
                "pad 0 -" + v + "% 0 0");
        layout.setComponentConstraints(closeButton,
                "pos 10-" + v + " 10 n n");
    }

    public void loginLeft(double v) {
        v = Double.parseDouble(df.format(v));
        login(true);
        layout.setComponentConstraints(title,
                "pad 0 " + v + "% 0 " + v + "%");
        layout.setComponentConstraints(description,
                "pad 0 " + v + "% 0 " + v + "%");
        layout.setComponentConstraints(description1,
                "pad 0 " + v + "% 0 " + v + "%");
        layout.setComponentConstraints(closeButton,
                "pos 100%-40-" + v + " 10 n n");
    }

    public void loginRight(double v) {
        v = Double.parseDouble(df.format(v));
        login(true);
        layout.setComponentConstraints(title,
                "pad 0 " + v + "% 0 " + v + "%");
        layout.setComponentConstraints(description,
                "pad 0 " + v + "% 0 " + v + "%");
        layout.setComponentConstraints(description1,
                "pad 0 " + v + "% 0 " + v + "%");
        layout.setComponentConstraints(closeButton,
                "pos -40+" + v + " 10 n n");
    }

    public void login(boolean login) {
        if (this.isLogin != login) {
            if (login) {
                title.setText("Hello, Friend!");
                description.setText("Enter your personal details");
                description1.setText("and start journey with us");
                button.setText("Already have an account?");
            } else {
                title.setText("Welcome Back!!");
                description.setText("To keep connected with us please");
                description1.setText("login with your personal info");
                button.setText("Don't have an account?");
            }
            this.isLogin = login;
        }
    }
}
