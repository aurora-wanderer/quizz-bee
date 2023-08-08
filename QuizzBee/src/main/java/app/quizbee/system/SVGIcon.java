package app.quizbee.system;

import com.formdev.flatlaf.extras.FlatSVGIcon;
import com.formdev.flatlaf.extras.FlatSVGIcon.ColorFilter;
import com.formdev.flatlaf.icons.FlatAbstractIcon;
import com.formdev.flatlaf.icons.FlatOptionPaneErrorIcon;
import com.formdev.flatlaf.icons.FlatOptionPaneInformationIcon;
import com.formdev.flatlaf.icons.FlatOptionPaneWarningIcon;
import java.awt.Color;

public class SVGIcon {

    public static ColorFilter setColor(Color color) {
        return new ColorFilter((t) -> color);
    }

    public static final FlatSVGIcon LOGO = new FlatSVGIcon("svg/hive-logo.svg", 40, 40);
    public static final FlatSVGIcon SHOW_MENU = new FlatSVGIcon("svg/showing-item_menu.svg", 23, 23);
    public static final FlatSVGIcon HIDE_MENU = new FlatSVGIcon("svg/hide-item_menu.svg", 23, 23);
    public static final FlatSVGIcon LOGIN = new FlatSVGIcon("svg/login.svg", 40, 40);
    public static final FlatSVGIcon LOGOUT = new FlatSVGIcon("svg/logout.svg", 40, 40);

    public static final FlatSVGIcon HOME = new FlatSVGIcon("svg/home-item_menu.svg", 20, 20);
    public static final FlatSVGIcon COLLECTION = new FlatSVGIcon("svg/collection.svg", 20, 20);
    public static final FlatSVGIcon QUIZ_GAME = new FlatSVGIcon("svg/quiz-game.svg", 20, 20);
    public static final FlatSVGIcon FLASHCARD = new FlatSVGIcon("svg/flashcard.svg", 20, 20);
    public static final FlatSVGIcon USER_PROFILE = new FlatSVGIcon("svg/user-profile.svg", 20, 20);
    public static final FlatSVGIcon SETTING = new FlatSVGIcon("svg/setting.svg", 20, 20);

    public static final FlatSVGIcon NEW = new FlatSVGIcon("svg/new.svg", 15, 15);
    public static final FlatSVGIcon JOIN = new FlatSVGIcon("svg/join.svg", 15, 15);

    public static final FlatSVGIcon CALENDAR = new FlatSVGIcon("svg/calendar-item_menu.svg", 20, 20);
    public static final FlatSVGIcon EVENT = new FlatSVGIcon("svg/event-item_menu.svg", 20, 20);
    public static final FlatSVGIcon FAVORITE = new FlatSVGIcon("svg/favorites-item_menu.svg", 20, 20);
    public static final FlatSVGIcon CHEVRON_RIGHT = new FlatSVGIcon("svg/chevron_right-profile_bottom.svg", 20, 20);

    public static final FlatSVGIcon MAGNIFIER_SEARCH = new FlatSVGIcon("svg/magnifier-search_input.svg", 15, 15);
    public static final FlatSVGIcon PREVIOUS = new FlatSVGIcon("svg/previous-page.svg", 20, 20);
    public static final FlatSVGIcon NEXT = new FlatSVGIcon("svg/next-page.svg", 20, 20);

    public static final FlatSVGIcon MOON = new FlatSVGIcon("svg/moon.svg", 20, 20);
    public static final FlatSVGIcon SUN = new FlatSVGIcon("svg/sun.svg", 20, 20);

    public static final FlatAbstractIcon ERROR = new FlatOptionPaneErrorIcon();
    public static final FlatAbstractIcon INFORMATION = new FlatOptionPaneInformationIcon();
    public static final FlatAbstractIcon WARNING = new FlatOptionPaneWarningIcon();
    public static final FlatSVGIcon SUCCESS = new FlatSVGIcon("svg/success.svg",
            ERROR.getIconWidth(), ERROR.getIconHeight());

    public static final FlatSVGIcon CLOSE = new FlatSVGIcon("svg/close.svg", 15, 15);
    public static final FlatSVGIcon EYE = new FlatSVGIcon("svg/eye.svg", 20, 20);
    public static final FlatSVGIcon HIDE = new FlatSVGIcon("svg/hide.svg", 20, 20);
    public static final FlatSVGIcon USER = new FlatSVGIcon("svg/user.svg", 20, 20);
    public static final FlatSVGIcon MAIL = new FlatSVGIcon("svg/mail.svg", 20, 20);
    public static final FlatSVGIcon PASSWORD = new FlatSVGIcon("svg/password.svg", 20, 20);
    public static final FlatSVGIcon NAME = new FlatSVGIcon("svg/fullname.svg", 20, 20);
    public static final FlatSVGIcon OTP = new FlatSVGIcon("svg/otp.svg", 20, 20);

    public static final FlatSVGIcon LOADING = new FlatSVGIcon("svg/loading.svg", 100, 100);
    public static final FlatSVGIcon UPLOADFILE = new FlatSVGIcon("svg/upload.svg", 20, 20);

    public static final FlatSVGIcon QUESTION_MESSAGE = new FlatSVGIcon("svg/question_message.svg", 70, 70);
    public static final FlatSVGIcon READY = new FlatSVGIcon("svg/ready.svg", 20, 20);
    public static final FlatSVGIcon OK = new FlatSVGIcon("svg/ok.svg", 20, 20);
}
