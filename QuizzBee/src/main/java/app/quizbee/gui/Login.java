package app.quizbee.gui;

import app.quizbee.client.Client;
import app.quizbee.dialog.verify.VerifyPanel;
import app.quizbee.view.form.Detail;
import app.quizbee.view.form.LaRForm;
import app.quizbee.dialog.loading.Loading;
import app.quizbee.dialog.notification.Notification;
import app.quizbee.entity.User;
import app.quizbee.main.Application;
import app.quizbee.modal.LoginModal;
import app.quizbee.modal.MailModal;
import app.quizbee.modal.NotificationModal;
import app.quizbee.modal.RegisterModal;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.MouseAdapter;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.text.DecimalFormat;
import javax.swing.JFrame;
import javax.swing.JLayeredPane;
import javax.swing.WindowConstants;
import net.miginfocom.swing.MigLayout;
import org.jdesktop.animation.timing.Animator;
import org.jdesktop.animation.timing.TimingTarget;
import org.jdesktop.animation.timing.TimingTargetAdapter;

public class Login extends JFrame {

    private final Client client = Application.getDashboard().getClient();
    private JLayeredPane bg;
    private MigLayout layout;
    private Animator animator;

    private Detail cover;
    private VerifyPanel verifyCode;
    private Loading loading;
    private LaRForm loginAndRegister;

    private boolean isLogin = false;
    private final double addSize = 30;
    private final double coverSize = 40;
    private final double loginSize = 60;

    public Login() {
        setUndecorated(true);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        init();
        setOpacity(0f);
        addMouseListener(new MouseAdapter() {
        });
        addKeyListener(new KeyAdapter() {
        });
        setFocusCycleRoot(true);
    }

    private void init() {
        this.setOpacity(0.0f);
        this.setBackground(new Color(0, 0, 0, 0));
        this.setMinimumSize(new Dimension(900, 500));
        this.setLocationRelativeTo(null);
        this.setLayout(new MigLayout("fill, ins 0",
                "", ""));

        bg = new JLayeredPane();
        layout = new MigLayout("fill, insets 0");
        cover = new Detail();
        loading = new Loading();
        verifyCode = new VerifyPanel();
        loginAndRegister = new LaRForm();
        makeAnimation();
        initEvent();

        bg.setOpaque(true);
        bg.setLayout(layout);
        bg.setLayer(loading, JLayeredPane.POPUP_LAYER);
        bg.setLayer(verifyCode, JLayeredPane.POPUP_LAYER);

        this.add(bg, "w 900!, h 500!, center");
        bg.add(loading, "pos 0 0 100% 100%");
        bg.add(verifyCode, "pos 0 0 100% 100%");
        bg.add(cover, "width " + coverSize + "%, pos 0al 0 n 100%");
        bg.add(loginAndRegister, "width " + loginSize + "%, pos 1al 0 n 100%");
    }

    private void initEvent() {
        loginAndRegister.addRegisterEvent((ActionEvent e) -> {
            if (!loginAndRegister.getRegisterValidation()) {
                new Notification(
                        this,
                        Notification.Type.INFO,
                        Notification.Location.TOP_CENTER,
                        "Please check and try again!"
                ).showNotification();
                return;
            }

            register();
        });

        loginAndRegister.addLoginEvent((ActionEvent e) -> {
            if (!loginAndRegister.getLoginValidation()) {
                new Notification(
                        this,
                        Notification.Type.INFO,
                        Notification.Location.TOP_CENTER,
                        "Please check and try again!"
                ).showNotification();
                return;
            }

            login();
        });

        verifyCode.addEventButtonOK((ae) -> {
            RegisterModal rModal = loginAndRegister.getRegisterModal();
            if (verifyCode.getInputCode()
                    .equalsIgnoreCase(rModal.getCode())) {
                rModal.getUser().setStatus("Verified");
                rModal.getUser().setActive("Offline");
                client.getService().register(rModal.getUser());
                showMessage(new NotificationModal(true,
                        "Register Successfully"));
                verifyCode.setVisible(false);
                verifyCode.resetInputCode();
                loginAndRegister.resetRegister();
            } else {
                showMessage(new NotificationModal(false,
                        "Verify Code Incorrect!"));
                verifyCode.resetInputCode();
            }
        });

        verifyCode.addResendMail((ActionEvent e) -> {
            RegisterModal modal = loginAndRegister.getRegisterModal();
            sendMail(modal);
        });

        loading.setVisible(false);
    }

    private void register() {
        RegisterModal modal = loginAndRegister.getRegisterModal();
        client.getService().findUser(modal.getUser(), (Boolean exists) -> {
            if (!exists) {
                sendMail(modal);
                return;
            }

            NotificationModal m = new NotificationModal(!exists,
                    "Username or email has exists!");
            showMessage(m);
        });
    }

    private void login() {
        if (client == null) {
            showMessage(new NotificationModal(false,
                    "Error connect to server!"));
            return;
        }

        client.getService().checkActive(LoginModal.user, (User data) -> {
            if (data == null) {
                showMessage(new NotificationModal(false,
                        "Account not exists!"));
                return;
            }

            if (data.getActive().contains("Online")) {
                showMessage(new NotificationModal(false,
                        "The account is already logged in"));
                return;
            }

            data.setActive("Online");
            LoginModal.user = data;
            client.getService().login(LoginModal.user);
            this.dispose();
            Application.getDashboard().reset();
        });
    }

    private void sendMail(RegisterModal modal) {
        new Thread(() -> {
            loading.setVisible(true);
            NotificationModal msgModal = new NotificationModal();
            client.getService().sendMail(modal.getUser().getEmail(),
                    (MailModal data) -> {
                        msgModal.setSuccess(data.getSuccess());
                        msgModal.setMessage(data.getMessage());
                        modal.setCode(data.getCode());
                        if (msgModal.isSuccess()) {
                            loading.setVisible(false);
                            verifyCode.setVisible(true);
                            showMessage(msgModal);
                        } else {
                            loading.setVisible(false);
                            showMessage(msgModal);
                        }
                    });
        }).start();
    }

    private void showMessage(NotificationModal modal) {
        new Notification(this,
                modal.isSuccess()
                ? Notification.Type.SUCCESS
                : Notification.Type.ERROR,
                Notification.Location.TOP_CENTER,
                modal.getMessage()
        ).showNotification();
    }

    public void makeAnimation() {
        // animation slide
        final DecimalFormat formatter = new DecimalFormat("##0.###");
        TimingTarget target = new TimingTargetAdapter() {
            @Override
            public void timingEvent(float fraction) {
                double fractionCover;
                double fractionLogin;
                double size = coverSize;
                if (fraction <= 0.5f) {
                    size += fraction * addSize;
                } else {
                    size += addSize - fraction * addSize;
                }
                if (isLogin) {
                    fractionCover = 1f - fraction;
                    fractionLogin = fraction;
                    if (fraction >= 0.5f) {
                        cover.registerRight(fractionCover * 100);
                    } else {
                        cover.loginRight(fractionLogin * 100);
                    }
                } else {
                    fractionCover = fraction;
                    fractionLogin = 1f - fraction;
                    if (fraction <= 0.5f) {
                        cover.registerLeft(fraction * 100);
                    } else {
                        cover.loginLeft((1f - fraction) * 100);
                    }
                }
                if (fraction >= 0.5f) {
                    loginAndRegister.showRegister(isLogin);
                }
                fractionCover = Double.parseDouble(formatter.format(fractionCover));
                fractionLogin = Double.parseDouble(formatter.format(fractionLogin));
                layout.setComponentConstraints(cover,
                        "width " + size + "%, pos " + fractionCover + "al 0 n 100%");
                layout.setComponentConstraints(loginAndRegister,
                        "width " + loginSize + "%, pos " + fractionLogin + "al 0 n 100%");
                bg.revalidate();
            }

            @Override
            public void end() {
                isLogin = !isLogin;
                loginAndRegister.resetRegister();
                loginAndRegister.resetLogin();
            }
        };

        animator = new Animator(800, target);
        animator.setAcceleration(0.5f);
        animator.setDeceleration(0.5f);
        animator.setResolution(0);  //  for smooth animation
        cover.addActionEvent((ActionEvent ae) -> {
            if (!animator.isRunning()) {
                animator.start();
            }
        });

        // close / login form animation
        Animator openAnimation = new Animator(800, new TimingTargetAdapter() {
            @Override
            public void timingEvent(float fraction) {
                super.timingEvent(fraction);
                setOpacity(fraction);
            }
        });

        openAnimation.setResolution(0);
        openAnimation.setAcceleration(0.5f);
        openAnimation.setDeceleration(0.5f);

        Animator closeAnimation = new Animator(800, new TimingTargetAdapter() {
            float fr = 1f;

            @Override
            public void timingEvent(float fraction) {
                super.timingEvent(fraction);
                setOpacity(fr - fraction);
            }

            @Override
            public void end() {
                super.end();
                setVisible(false);
            }
        });

        closeAnimation.setResolution(0);
        closeAnimation.setAcceleration(0.5f);
        closeAnimation.setDeceleration(0.5f);

        cover.addCloseEvent((ActionEvent ae) -> {
            if (!closeAnimation.isRunning()) {
                closeAnimation.start();
                Application.getDashboard().setVisible(true);
            }
        });

        // showPage frame animation
        if (!openAnimation.isRunning()) {
            openAnimation.start();
        }

        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                super.windowClosed(e);
                if (!closeAnimation.isRunning()) {
                    closeAnimation.start();
                    Application.getDashboard().setVisible(true);
                }
            }
        });
    }

}
