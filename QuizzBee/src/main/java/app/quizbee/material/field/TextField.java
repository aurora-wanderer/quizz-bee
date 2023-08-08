package app.quizbee.material.field;

import app.quizbee.system.SVGIcon;
import com.formdev.flatlaf.FlatIconColors;
import com.formdev.flatlaf.extras.FlatSVGIcon;
import com.formdev.flatlaf.extras.components.FlatTextField;
import java.awt.Font;
import java.awt.Insets;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import net.miginfocom.swing.MigLayout;

public final class TextField extends JPanel {

    private FlatTextField input;
    private JLabel labelMessage;

    public TextField() {
        setOpaque(false);
        setLayout(new MigLayout("fill, wrap, ins 0",
                "[grow, fill]", "0[fill]5[15!, fill]0"));
        init();
    }

    public TextField(String placeholderText, FlatSVGIcon icon) {
        this();
        setPlaceholderText(placeholderText);
        setPrefixIcon(icon);
    }

    private void init() {
        input = createInput("", SVGIcon.USER);
        input.addFocusListener(new FocusAdapter() {
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
                    if (getText().isEmpty()) {
                        setMessage("This field hasn't empty!");
                    }
                }
            }
        });
        labelMessage = new JLabel();
        labelMessage.setForeground(UIManager.getColor(FlatIconColors.ACTIONS_RED.key));
        labelMessage.setHorizontalAlignment(JLabel.LEFT);
        labelMessage.setFont(getFont().deriveFont(Font.ITALIC));
        this.add(input);
        this.add(labelMessage, "pad 0 15 0 0");
    }

    private FlatTextField createInput(String placeholder, FlatSVGIcon prefixIcon) {
        prefixIcon.setColorFilter(SVGIcon.setColor(getForeground()));

        FlatTextField text = new FlatTextField();
        JLabel prefix = new JLabel(prefixIcon);
        prefix.setBorder(new EmptyBorder(0, 10, 0, 0));

        text.setRoundRect(true);
        text.setPlaceholderText(placeholder);

        text.setLeadingComponent(prefix);
        text.setPadding(new Insets(0, 5, 0, 0));

        text.setShowClearButton(true);

        return text;
    }

    public void setRoundRect(boolean roundRect) {
        input.setRoundRect(roundRect);
    }

    public void setPlaceholderText(String placeholderText) {
        input.setPlaceholderText(placeholderText);
    }

    public void setText(String text) {
        input.setText(text);
    }

    public String getText() {
        return input.getText();
    }

    public String getMessage() {
        return labelMessage.getText();
    }

    public void setMessage(String message) {
        labelMessage.setText(message);
    }

    public void setOutLine(Object outLine) {
        input.setOutline(outLine);
    }

    public void setPrefixIcon(FlatSVGIcon icon) {
        JLabel prefixIcon = new JLabel(icon);
        icon.setColorFilter(SVGIcon.setColor(getForeground()));
        prefixIcon.setBorder(new EmptyBorder(0, 10, 0, 0));
        input.setLeadingComponent(prefixIcon);
    }

    public void setMessageIcon(FlatSVGIcon icon) {
        labelMessage.setIcon(icon);
        labelMessage.setIconTextGap(10);
    }
}