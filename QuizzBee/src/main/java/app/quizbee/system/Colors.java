package app.quizbee.system;

import com.formdev.flatlaf.FlatIconColors;
import java.awt.Color;
import javax.swing.UIManager;

public class Colors {

    // BG = Background
    // FG = Foreground
    public static Color BG_LOGO = UIManager.getColor(FlatIconColors.ACTIONS_YELLOW.key);
    public static Color BG_LOGO_NAME = UIManager.getColor(FlatIconColors.ACTIONS_YELLOW.key);
//    public static Color BG_LOGO_SUBNAME = UIManager.getColor("Button.focusedBorderColor");

    public static Color FG_MENU_ITEM = UIManager.getColor("Button.Foreground");
    public static Color FG_MENU_SPLITER = UIManager.getColor(FlatIconColors.ACTIONS_GREY.key);
    public static Color FG_MENU_SHOWING = UIManager.getColor("Button.Foreground");

    public static Color FG_PROFILE_SPLIT = UIManager.getColor("Borders.Color");
    public static Color FG_PROFILE_MORE_ICON = UIManager.getColor(FlatIconColors.ACTIONS_GREY.key);
    public static Color BG_LOGIN = UIManager.getColor(FlatIconColors.ACTIONS_GREY.key);
}
