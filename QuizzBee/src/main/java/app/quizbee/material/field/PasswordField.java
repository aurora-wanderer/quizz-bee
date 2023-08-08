package app.quizbee.material.field;

import app.quizbee.system.SVGIcon;
import com.formdev.flatlaf.FlatIconColors;
import com.formdev.flatlaf.extras.FlatSVGIcon;
import com.formdev.flatlaf.extras.components.FlatPasswordField;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import net.miginfocom.swing.MigLayout;

public class PasswordField extends JPanel {

    private FlatPasswordField password;
    private JLabel labelMessage;

    public PasswordField() {
        setOpaque(false);
        setLayout(new MigLayout("fill, wrap, ins 0",
                "[grow, fill]", "0[fill]5[15!, fill]0"));
        init();
    }

    public PasswordField(String placeholderText) {
        this();
        setPlaceholderText(placeholderText);
    }

    private void init() {
        password = createPassword("",
                SVGIcon.PASSWORD, SVGIcon.EYE);

        password.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                super.focusGained(e);
                if (!getMessage().isEmpty()) {
                    setMessage("");
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                super.focusLost(e);
                if (e.getOppositeComponent() != null) {
                    if (getValue().isEmpty()) {
                        setMessage("This field hasn't empty!");
                    }
                }
            }
        });

        labelMessage = new JLabel();
        labelMessage.setForeground(UIManager.getColor(FlatIconColors.ACTIONS_RED.key));
        labelMessage.setHorizontalAlignment(JLabel.LEFT);
        labelMessage.setFont(getFont().deriveFont(Font.ITALIC));
        this.add(password);
        this.add(labelMessage, "pad 0 15 0 0");
    }

    public void setRoundRect(boolean roundRect) {
        password.setRoundRect(roundRect);
    }

    private void setPlaceholderText(String placeholderText) {
        password.setPlaceholderText(placeholderText);
    }

    public void setValue(String text) {
        password.setText(text);
    }

    public String getValue() {
        return String.valueOf(password.getPassword());
    }

    public String getMessage() {
        return labelMessage.getText();
    }

    public void setMessage(String message) {
        labelMessage.setText(message);
    }

    public void setOutLine(Object outLine) {
        password.setOutline(outLine);
    }

    public void setMessageIcon(FlatSVGIcon icon) {
        labelMessage.setIcon(icon);
        labelMessage.setIconTextGap(10);
    }

    private FlatPasswordField createPassword(String text,
            FlatSVGIcon prefixIcon, FlatSVGIcon suffixIcon) {
        prefixIcon.setColorFilter(SVGIcon.setColor(getForeground()));
        suffixIcon.setColorFilter(SVGIcon.setColor(getForeground()));

        FlatPasswordField pass = new FlatPasswordField();
        JLabel prefix = new JLabel(prefixIcon);
        JButton suffix = new JButton(suffixIcon);

        pass.setRoundRect(true);
        pass.setPlaceholderText(text);

        prefix.setBorder(new EmptyBorder(0, 10, 0, 0));

        pass.setLeadingComponent(prefix);
        pass.setPadding(new Insets(0, 5, 0, 0));

        suffix.setContentAreaFilled(false);
        suffix.setOpaque(false);
        suffix.setMargin(new Insets(0, 0, 0, 10));
        suffix.setCursor(new Cursor(Cursor.HAND_CURSOR));
        final char ECHO_CHAR = pass.getEchoChar();
        SVGIcon.HIDE.setColorFilter(SVGIcon.setColor(getForeground()));
        suffix.addActionListener(new ActionListener() {
            boolean isShow = false;

            @Override
            public void actionPerformed(ActionEvent e) {
                if (isShow) {
                    suffix.setIcon(SVGIcon.HIDE);
                    pass.setEchoChar((char) 0);
                } else {
                    suffix.setIcon(suffixIcon);
                    pass.setEchoChar(ECHO_CHAR);
                }
                isShow = !isShow;
            }
        });
        pass.setTrailingComponent(suffix);
        pass.setShowClearButton(true);
        return pass;
    }
}
