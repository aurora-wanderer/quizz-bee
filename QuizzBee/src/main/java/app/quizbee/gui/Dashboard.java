package app.quizbee.gui;

import app.quizbee.client.Client;
import app.quizbee.component.menu.Menu;
import app.quizbee.component.titlebar.TitleBar;
import app.quizbee.component.header.Header;
import app.quizbee.controller.ComponentResizer;
import app.quizbee.dialog.message.Message;
import app.quizbee.main.Application;
import app.quizbee.material.panel.RoundedPanel;
import app.quizbee.modal.LoginModal;
import app.quizbee.view.page.Flashcard;
import app.quizbee.view.page.Home;
import app.quizbee.view.page.Profile;
import app.quizbee.view.page.Setting;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Map;
import javax.swing.JFrame;
import javax.swing.WindowConstants;
import net.miginfocom.swing.MigLayout;
import org.jdesktop.animation.timing.Animator;
import org.jdesktop.animation.timing.TimingTarget;
import org.jdesktop.animation.timing.TimingTargetAdapter;

public class Dashboard extends JFrame {

    private final Client client = Application.getClient();
    private final Map<Integer, Component> pages;

    private final Dimension minimized = new Dimension(1080, 720);
    private final Dimension fullscreen
            = Toolkit.getDefaultToolkit().getScreenSize();
    private Animator showingAnimation, open, close;

    private final TitleBar titleBar;
    private final Menu menu;
    private final RoundedPanel main;
    private final Header header;
    private final MigLayout miglayout;
    private boolean menuShow = true;

    private final Home home;
    private final Flashcard flashcard;
    private final Profile profile;
    private final Setting setting;

    public Dashboard() {
        initComponents();
        home = new Home();
        flashcard = new Flashcard();
        profile = new Profile();
        setting = new Setting();

        pages = Map.of(1, home,
                3, flashcard,
                5, profile,
                6, setting);

        titleBar = new TitleBar();
        menu = new Menu();
        header = new Header();
        miglayout = new MigLayout("fill, inset 0",
                "0[fill]0", "0[fill]0");
        main = new RoundedPanel();
        main.setLayout(new MigLayout("fill, ins 0",
                "0[fill]0", "35[fill]0"));

        setContentPane(roundedBackground);
        init();
        client.connect();
    }

    public void reset() {
        header.resetProfile();
        menu.reset();
        home.reset();

        header.addLogoutEvent((ActionEvent e) -> {
            Message ms = new Message(this);
            ms.showMessage("SIGN OUT",
                    "Are you sure you want to sign out?");

            if (ms.getMessageType() == Message.MessageType.OK) {
                LoginModal.user.setActive("Offline");
                client.getService().logout(LoginModal.user);
                this.dispose();
                Application.setDashboard(new Dashboard());
                Application.getDashboard().setVisible(true);
            }
        });
        client.getService().disconnect(LoginModal.user);
    }

    private void init() {
        ComponentResizer resizer = new ComponentResizer();

        resizer.registerComponent(this);
        resizer.setSnapSize(new Dimension(10, 10));

        this.setOpacity(0.0f);
        this.setBackground(new Color(0, 0, 0, 0f));
        this.setLocationRelativeTo(this);
        this.makeAnimation();

        roundedBackground.setLayout(miglayout);
        roundedBackground.add(titleBar, "north"); // always on top
        roundedBackground.add(menu, "west, w 200!"); // left and width resizable
        roundedBackground.add(main, "center, w 100%"); // center

        titleBar.initMoving(this);
        titleBar.initEvent(this, roundedBackground);

        menu.addEventMenu((int index) -> {
            showPage(pages.get(index));
        });

        menu.setShowingAction((ActionEvent e) -> {
            if (!showingAnimation.isRunning()) {
                showingAnimation.start();
            }
        });
        menu.setShow(!menuShow);

        main.setOpaque(false);
        main.setBackground(new Color(0, 0, 0, 0));
        main.add(header, BorderLayout.NORTH);
        showingButtonAnimation();
        showPage(home);
    }

    public void showingButtonAnimation() {
        TimingTarget target = new TimingTargetAdapter() {
            @Override
            public void timingEvent(float fraction) {
                double width;
                if (menuShow) {
                    width = 80 + (120 * (1f - fraction));
                    menu.setAlpha(1f - fraction);
                } else {
                    width = 80 + (120 * fraction);
                    menu.setAlpha(fraction);
                }
                miglayout.setComponentConstraints(menu, "west, w " + width + "!");
                roundedBackground.revalidate();
                menu.setShow(menuShow);
            }

            @Override
            public void end() {
                menuShow = !menuShow;
            }
        };
        showingAnimation = new Animator(400, target);
        showingAnimation.setResolution(0);
        showingAnimation.setAcceleration(0.5f);
        showingAnimation.setDeceleration(0.5f);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        roundedBackground = new app.quizbee.material.panel.RoundedPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(0, 0, 0));
        setMinimumSize(minimized);
        setUndecorated(true);

        roundedBackground.setMaximumSize(fullscreen);
        roundedBackground.setMinimumSize(minimized);
        roundedBackground.setPreferredSize(new java.awt.Dimension(1080, 720));

        javax.swing.GroupLayout roundedBackgroundLayout = new javax.swing.GroupLayout(roundedBackground);
        roundedBackground.setLayout(roundedBackgroundLayout);
        roundedBackgroundLayout.setHorizontalGroup(
            roundedBackgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1080, Short.MAX_VALUE)
        );
        roundedBackgroundLayout.setVerticalGroup(
            roundedBackgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 720, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(roundedBackground, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(roundedBackground, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private app.quizbee.material.panel.RoundedPanel roundedBackground;
    // End of variables declaration//GEN-END:variables

    private void makeAnimation() {
        open = new Animator(800, new TimingTargetAdapter() {
            @Override
            public void timingEvent(float fraction) {
                super.timingEvent(fraction);
                setOpacity(fraction);
            }

            @Override
            public void end() {
                super.end();
                setOpacity(1.0f);
            }
        });

        open.setResolution(0);
        open.setAcceleration(0.5f);
        open.setDeceleration(0.5f);

        close = new Animator(800, new TimingTargetAdapter() {
            float fr = 1f;

            @Override
            public void begin() {
                super.begin();
                setOpacity(fr);
            }

            @Override
            public void timingEvent(float fraction) {
                super.timingEvent(fraction);
                setOpacity(fr - fraction);
            }

            @Override
            public void end() {
                setOpacity(0.0f);
            }
        });

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                Application.getDashboard().setDefaultCloseOperation(
                        WindowConstants.DO_NOTHING_ON_CLOSE);
                Message ms = new Message((JFrame) e.getSource());
                ms.showMessage("Exit", "Are you sure exit?");
                if (ms.getMessageType() == Message.MessageType.OK) {
                    if (LoginModal.user != null) {
                        client.getService().logout(LoginModal.user);
                    }
                    System.exit(0);
                }
            }
        });
        close.setResolution(1);
        close.setAcceleration(0.5f);
        close.setDeceleration(0.5f);
    }

    public void showPage(Component form) {
        main.removeAll();
        main.add(header, BorderLayout.NORTH);
        main.add(form, BorderLayout.CENTER);
        roundedBackground.revalidate();
        main.revalidate();
        main.repaint();
    }

    public Client getClient() {
        return client;
    }
    
    @Override
    public void setVisible(boolean b) {
        if (!b && !close.isRunning()) {
            close.start();
            return;
        }

        super.setVisible(b);
        if (b && !open.isRunning()) {
            open.start();
        }
    }

    @Override
    public void dispose() {
        super.dispose();
        client.disconnect();
    }
}
