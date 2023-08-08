package app.quizbee.view.form;

import app.quizbee.modal.RegisterModal;
import app.quizbee.system.Colors;
import java.awt.CardLayout;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JLayeredPane;
import javax.swing.SwingUtilities;

public class LaRForm extends JLayeredPane {

    private LoginForm login;
    private RegisterForm register;

    public LaRForm() {
        setOpaque(false);
        setLayout(new CardLayout(0, 0));
        initLogin();
        initRegister();
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                if (SwingUtilities.isLeftMouseButton(e)
                        && e.getClickCount() >= 1) {
                    grabFocus();
                }
            }
        });
        login.setBackground(Colors.BG_LOGIN);
        register.setBackground(Colors.BG_LOGIN);
    }

    public void showRegister(boolean show) {
        if (!show) {
            register.setVisible(true);
            login.setVisible(false);
        } else {
            register.setVisible(false);
            login.setVisible(true);
        }
    }

    private void initLogin() {
        login = new LoginForm();
        this.add(login);
    }

    private void initRegister() {
        register = new RegisterForm();
        this.add(register);
    }

    public void addLoginEvent(ActionListener act) {
        login.getLoginButton().addActionListener(act);
    }

    public void addRegisterEvent(ActionListener act) {
        register.getRegisterButton().addActionListener(act);
    }

    public boolean getLoginValidation() {
        return login.valid();
    }

    public boolean getRegisterValidation() {
        return register.valid();
    }

    public void resetRegister() {
        register.reset();
    }

    public void resetLogin() {
        login.reset();
    }
    
    public RegisterModal getRegisterModal () {
        return register.getModalForm();
    }
}
