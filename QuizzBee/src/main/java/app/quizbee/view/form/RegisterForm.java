package app.quizbee.view.form;

import app.quizbee.controller.Validator;
import app.quizbee.entity.User;
import app.quizbee.material.field.PasswordField;
import app.quizbee.material.field.TextField;
import app.quizbee.modal.RegisterModal;
import app.quizbee.system.SVGIcon;
import com.formdev.flatlaf.FlatIconColors;
import com.formdev.flatlaf.extras.components.FlatButton;
import java.awt.Cursor;
import java.awt.Font;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.UIManager;
import net.miginfocom.swing.MigLayout;

public class RegisterForm extends JPanel {

    private RegisterModal modal;
    private TextField usernameField, emailField, fullnameField;
    private PasswordField passwordField, confirmField;
    private FlatButton registerButton;

    public RegisterForm() {
        init();
    }

    private void init() {
        this.setLayout(new MigLayout("wrap, ins 0, gap 20! 5!",
                "push[c]push",
                "push[][][][][][][]push"));

        JLabel title = new JLabel("SIGN UP");
        title.setFont(title.getFont().deriveFont(Font.BOLD, 35f));

        usernameField = new TextField("Username", SVGIcon.USER);
        fullnameField = new TextField("Fullname", SVGIcon.NAME);
        emailField = new TextField("Email", SVGIcon.MAIL);
        passwordField = new PasswordField("Password");
        confirmField = new PasswordField("Confirm");
        registerButton = createButtonAction("SIGN UP");

        this.add(title, "gapbottom 15px");
        this.add(usernameField, "w 250!, h 55!");
        this.add(fullnameField, "w 250!, h 55!");
        this.add(emailField, "w 250!, h 55!");
        this.add(passwordField, "w 250!, h 55!");
        this.add(confirmField, "w 250!, h 55!");
        this.add(registerButton, "w 150!, h 35!");
    }

    public boolean valid() {

        Boolean[] rules = new Boolean[]{
            Validator.isRequired(usernameField, "Username hasn't empty!"),
            Validator.isRequired(fullnameField, "Name hasn't empty!"),
            Validator.isRequired(emailField, "Email hasn't empty!"),
            Validator.isRequired(passwordField, "Password hasn't empty!"),
            Validator.isRequired(confirmField, "Confirm hasn't empty!"),
            Validator.isPassword(passwordField, ""),
            Validator.isPassword(confirmField, ""),
            Validator.isEmail(emailField, ""),
            Validator.compare(passwordField, confirmField,
            "Confirm password in correct!")
        };

        boolean isValidate = Validator.validate(rules);
        if (isValidate) {
            User user = new User();

            user.setUsername(usernameField.getText());
            user.setPassword(passwordField.getValue());
            user.setEmail(emailField.getText());
            user.setFullname(fullnameField.getText());
            user.setStatus("");

            modal = new RegisterModal();
            modal.setUser(user);
            modal.setCode("");
        }

        return isValidate;
    }

    public void reset() {
        usernameField.setText("");
        passwordField.setValue("");
        confirmField.setValue("");
        emailField.setText("");
        fullnameField.setText("");
        usernameField.setMessage("");
        passwordField.setMessage("");
        confirmField.setMessage("");
        fullnameField.setMessage("");
        emailField.setMessage("");
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

    public FlatButton getRegisterButton() {
        return registerButton;
    }
    
    public RegisterModal getModalForm () {
        return modal;
    }

}
