package app.quizbee.view.form;

import app.quizbee.controller.Validator;
import app.quizbee.entity.User;
import app.quizbee.modal.LoginModal;
import app.quizbee.material.field.PasswordField;
import app.quizbee.material.field.TextField;
import app.quizbee.system.SVGIcon;
import com.formdev.flatlaf.FlatIconColors;
import com.formdev.flatlaf.extras.components.FlatButton;
import com.formdev.flatlaf.extras.components.FlatCheckBox;
import com.formdev.flatlaf.icons.FlatHelpButtonIcon;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.UIManager;
import net.miginfocom.swing.MigLayout;

public class LoginForm extends JPanel {

    private TextField usernameField;
    private PasswordField passwordField;
    private FlatCheckBox rememberButton;
    private FlatButton loginButton;

    public LoginForm() {
        init();
        if (LoginModal.hasRemember) {
            usernameField.setText(LoginModal.user.getUsername());
            passwordField.setValue(LoginModal.user.getPassword());
            rememberButton.setSelected(true);
        }
    }

    private void init() {
        this.setLayout(new MigLayout("wrap, gap 25! 10!, ins 0",
                "push[c]push",
                "push[c][][][][][c]push"));

        JLabel title = new JLabel("SIGN IN");
        title.setFont(title.getFont().deriveFont(Font.BOLD, 35f));
        title.setForeground(getForeground());

        usernameField = new TextField("Username", SVGIcon.USER);
        passwordField = new PasswordField("Password");

        rememberButton = new FlatCheckBox();
        rememberButton.setText("Remember?");
        rememberButton.setForeground(Color.white);

        loginButton = createButtonAction("SIGN IN");

        this.add(title, "gapbottom 40px");
        this.add(usernameField, "w 300!, h 55!");
        this.add(passwordField, "w 300!, h 55!");
        this.add(rememberButton, "w 100!, r");
        this.add(loginButton, "gaptop 30px, w 150!, h 35!");
    }

    public boolean valid() {
        Boolean[] rules = new Boolean[]{
            Validator.isRequired(usernameField, "Username hasn't empty!"),
            Validator.isRequired(passwordField, "Password hasn't empty!"),
            Validator.isPassword(passwordField, "Password incorrect!")
        };

        boolean isValid = Validator.validate(rules);

        if (isValid) {
            LoginModal.user = new User();
            LoginModal.user.setUsername(usernameField.getText());
            LoginModal.user.setPassword(passwordField.getValue());
            LoginModal.hasRemember = rememberButton.isSelected();
            return isValid;
        }

        return isValid;
    }

    public void reset() {
        if (LoginModal.hasRemember) {
            return;
        }
        usernameField.setText("");
        usernameField.setMessage("");
        passwordField.setValue("");
        passwordField.setMessage("");
    }

    private FlatButton createButtonAction(String text) {
        FlatButton button = new FlatButton();

        button.setText(text);
        button.setBackground(UIManager.getColor(FlatIconColors.ACTIONS_YELLOW.key));
        button.setOpaque(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setButtonType(FlatButton.ButtonType.roundRect);

        return button;
    }

    public FlatButton getLoginButton() {
        return loginButton;
    }
}
