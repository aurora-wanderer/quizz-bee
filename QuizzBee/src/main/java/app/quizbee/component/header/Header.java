package app.quizbee.component.header;

import app.quizbee.event.SwitchAdapter;
import app.quizbee.gui.Login;
import app.quizbee.main.Application;
import app.quizbee.material.button.SwitchButton;
import app.quizbee.system.Colors;
import app.quizbee.system.SVGIcon;
import com.formdev.flatlaf.FlatLaf;
import com.formdev.flatlaf.extras.FlatAnimatedLafChange;
import com.formdev.flatlaf.intellijthemes.FlatCyanLightIJTheme;
import com.formdev.flatlaf.intellijthemes.FlatDarkPurpleIJTheme;
import java.awt.Cursor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;
import net.miginfocom.swing.MigLayout;

public class Header extends JPanel {

    private final JPanel profilePane;
    private final MigLayout layout;
    private SwitchButton switchButton;
    private JButton loginBtn;

    public Header() {
        profilePane = new JPanel();

        layout = new MigLayout("fill, ins 0",
                "0[100%, r]20",
                "0[c]0");
        setOpaque(false);
        setLayout(layout);

        createHeader();
    }

    private void createHeader() {
        final MigLayout profileLayout = new MigLayout("fill, r",
                "0[fill, c]15",
                "0[c]0"
        );
        profilePane.setOpaque(false);
        profilePane.setLayout(profileLayout);

        createSwitchButton();
        crateLoginButton();
        this.add(profilePane, "h 60!");
    }

    private void createSwitchButton() {
        switchButton = new SwitchButton();
        if (FlatLaf.isLafDark()) {
            switchButton.setSelected(true, true);
        } else {
            switchButton.setSelected(false, true);
        }
        switchButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        switchButton.setBorder(new EmptyBorder(5, 0, 0, 0));
        switchButton.addEventToggleSelected(new SwitchAdapter() {
            @Override
            public void onSelected(boolean selected) {
                if (!selected) {
                    SwingUtilities.invokeLater(() -> {
                        FlatAnimatedLafChange.showSnapshot();
                        FlatCyanLightIJTheme.setup();
                        FlatCyanLightIJTheme.updateUI();
                        FlatAnimatedLafChange.hideSnapshotWithAnimation();
                    });
                } else {
                    SwingUtilities.invokeLater(() -> {
                        FlatAnimatedLafChange.showSnapshot();
                        FlatDarkPurpleIJTheme.setup();
                        FlatDarkPurpleIJTheme.updateUI();
                        FlatAnimatedLafChange.hideSnapshotWithAnimation();
                    });
                }
            }
        });
        profilePane.add(switchButton, "w 50!, h 30!, c");
    }

    private void crateLoginButton() {
        SVGIcon.LOGIN.setColorFilter(
                SVGIcon.setColor(Colors.FG_MENU_SHOWING));
        SVGIcon.LOGOUT.setColorFilter(
                SVGIcon.setColor(Colors.FG_MENU_SHOWING));

        loginBtn = new JButton(SVGIcon.LOGIN);
        loginBtn.setOpaque(false);
        loginBtn.setHorizontalAlignment(JButton.CENTER);
        loginBtn.setVerticalAlignment(JButton.CENTER);

        loginBtn.addActionListener((ActionEvent e) -> {
            SwingUtilities.invokeLater(() -> {
                Application.getDashboard().setVisible(false);
                new Login().setVisible(true);
            });
        });

        loginBtn.setHorizontalTextPosition(JButton.RIGHT);
        loginBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        loginBtn.setContentAreaFilled(false);
        profilePane.add(loginBtn);
    }

    public void resetProfile() {
        loginBtn.setIcon(SVGIcon.LOGOUT);
        loginBtn.removeActionListener(loginBtn.getActionListeners()[0]);
    }

    public void addLogoutEvent(ActionListener act) {
        loginBtn.addActionListener(act);
    }
}
