package app.quizbee.material.button;

import com.formdev.flatlaf.extras.components.FlatButton;
import java.awt.Cursor;

public class RoundedButton extends FlatButton{

    public RoundedButton() {
        setOpaque(false);
        setButtonType(FlatButton.ButtonType.roundRect);
        setCursor(new Cursor(Cursor.HAND_CURSOR));
    }
    
}
