package app.quizbee.view.page;

import app.quizbee.component.room.Created;
import app.quizbee.component.room.WaitingRoom;
import app.quizbee.material.scrollbar.ScrollBarCustom;
import app.quizbee.modal.RoomModal;
import app.quizbee.main.Application;
import app.quizbee.modal.LoginModal;
import app.quizbee.modal.PlayerModal;
import app.quizbee.system.Colors;
import app.quizbee.system.SVGIcon;
import com.formdev.flatlaf.FlatIconColors;
import com.formdev.flatlaf.extras.FlatSVGIcon;
import com.formdev.flatlaf.extras.components.FlatButton;
import com.formdev.flatlaf.extras.components.FlatTextArea;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.event.ActionEvent;
import java.awt.geom.Area;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RoundRectangle2D;
import java.util.List;
import java.util.Random;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import net.miginfocom.swing.MigLayout;

public class Home extends JLayeredPane {

    private JPanel panelRooms, header, homePanel;
    private JLabel title;
    private JLabel lblEmpty;
    private FlatButton create, join;
    private Created createdForm;
    private WaitingRoom waitingRoom;

    public Home() {
        init();
    }

    public void reset() {
        create.setVisible(true);
    }

    private void init() {
        setOpaque(false);
        setLayout(new CardLayout(0, 0));

        homePanel = new JPanel(new MigLayout("",
                "[fill]"));
        header = new JPanel();
        title = new JLabel();
        panelRooms = new JPanel();
        lblEmpty = new JLabel();

        createHomePage();
        initScroller();
        add(homePanel);

        createdForm = new Created(Application.getDashboard(),
                true);
        initEvent();
        fillRooms();
    }

    public void createHomePage() {
        MigLayout roomsLayout = new MigLayout("wrap, fillx, ins 0",
                "10[]40[]40[]10",
                "10[]35");
        panelRooms.setLayout(roomsLayout);

        MigLayout headerLayout = new MigLayout("right",
                "0[c]20[c]20",
                "0[fill]0");
        header.setLayout(headerLayout);

        lblEmpty.setBackground(lblEmpty.getBackground().darker());
        lblEmpty.setHorizontalAlignment(JLabel.CENTER);
        lblEmpty.setFont(lblEmpty.getFont().deriveFont(35f));
        lblEmpty.setText("No room exists yet!");

        create = createButton("Create", SVGIcon.NEW);
        create.setBackground(new Color(0, 0, 0, 0));

        join = createButton("Join", SVGIcon.JOIN);

        join.setButtonType(FlatButton.ButtonType.roundRect);
        header.add(create, "w 120!, h 40!");
        header.add(join, "w 100!, h 40!");
        create.setVisible(false);

        title.setText("Room");
        title.setFont(new Font("Tahoma", Font.BOLD, 15));

        homePanel.add(title, "split, h 40!");
        homePanel.add(header, "wrap, h 40!");
        homePanel.add(lblEmpty, "pos 0al 0al+45 100% 100%");
    }

    public void initEvent() {
        create.addActionListener((act) -> {
            createdForm.setVisible(true);
        });

        createdForm.addEventCreateButton((ActionEvent e) -> {
            if (!createdForm.validation()) {
                return;
            }
            addRoom(createdForm.getRoomModal());
            createdForm.setVisible(false);
        });
    }

    public void initScroller() {
        JScrollPane scrollBarCustom = new JScrollPane(panelRooms) {
            @Override
            public void updateUI() {
                super.updateUI();
                setBorder(null);
            }
        };

        ScrollBarCustom scrolling = new ScrollBarCustom();

        scrollBarCustom.setBorder(null);
        scrollBarCustom.setViewportBorder(null);
        scrollBarCustom.getViewport().setOpaque(false);
        scrollBarCustom.setVerticalScrollBar(scrolling);
        scrollBarCustom.setHorizontalScrollBarPolicy(
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER
        );
        homePanel.add(scrollBarCustom, "w 100%, h 100%");
    }

    private FlatButton createButton(String text, FlatSVGIcon icon) {
        icon.setColorFilter(SVGIcon.setColor(Colors.FG_MENU_ITEM));
        FlatButton button = new FlatButton();

        button.setButtonType(FlatButton.ButtonType.roundRect);
        button.setText(text);
        button.setOpaque(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 14));

        button.setIcon(icon);
        button.setIconTextGap(15);
        button.setHorizontalAlignment(SwingConstants.LEFT);
        button.setHorizontalTextPosition(SwingConstants.RIGHT);

        return button;
    }

    public void addRoom(RoomModal modal) {
        Application.getClient()
                .getService()
                .addRoom(modal);
        fillRooms();
        joinWaitingRoom(modal);
    }

    public void checkNoneRoom() {
        if (panelRooms.getComponentCount() <= 0) {
            lblEmpty.setVisible(true);
        }

        if (panelRooms.getComponentCount() > 0) {
            lblEmpty.setVisible(false);
        }
    }

    public void joinWaitingRoom(RoomModal roomModal) {
        PlayerModal p = new PlayerModal(roomModal.getRoomID(),
                LoginModal.user != null
                        ? LoginModal.user.getFullname()
                        : "Guest",
                false);

        waitingRoom = new WaitingRoom(p, roomModal);

        if (p.getName().contains("Guest")
                || (LoginModal.user != null
                && !LoginModal.user.equals(roomModal.getOwner()))) {
            waitingRoom.addPlayerView(p);
        }

        waitingRoom.setTitle(roomModal.getRoomID(),
                roomModal.getRoomName());

        add(waitingRoom);
        homePanel.setVisible(false);
        waitingRoom.setVisible(true);
    }

    public void removeRoom(RoomModal room) {
        Application.getClient().getService().removeRoom(room);
        fillRooms();
    }

    private void fillRooms() {
        Application
                .getClient()
                .getService()
                .autoFillRooms((List<RoomModal> data) -> {
                    panelRooms.removeAll();
                    data.forEach(room -> {
                        panelRooms.add(new RoomCard(room),
                                "w 250!, h 150!");
                    });
                    panelRooms.revalidate();
                    checkNoneRoom();
                });
    }

    @Override
    protected void paintComponent(Graphics grphcs) {
        Graphics2D g2 = (Graphics2D) grphcs.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(getBackground());
        g2.fill(createShape());
        g2.dispose();
        super.paintComponent(grphcs);
    }

    private Shape createShape() {
        float width = getWidth();
        float height = getHeight();

        Area area = new Area();
        area.add(new Area(new RoundRectangle2D.Float(0f, 0f,
                width, height,
                15f, 15f)));

        area.add(new Area(new Rectangle2D.Float(0, 0,
                15, 15)));

        area.add(new Area(new Rectangle2D.Float(0, height - 15,
                15, 15)));

        area.add(new Area(new Rectangle2D.Float(width - 15, 0,
                15, 15)));

        return area;
    }

    public class RoomCard extends JPanel {

        private final RoomModal modal;
        private MigLayout layout;
        private JPanel info;
        private FlatTextArea lblName;
        private JLabel participantsCount;
        private FlatButton joinBtn;

        public RoomCard(RoomModal modal) {
            setOpaque(false);
            this.modal = modal;
            initCard();
            setBorder(new TitledBorder(
                    new LineBorder(
                            UIManager.getColor(
                                    FlatIconColors.ACTIONS_GREYINLINE_DARK.key),
                            3, true
                    ),
                    modal.getRoomID(),
                    TitledBorder.CENTER,
                    TitledBorder.TOP));
        }

        private void initCard() {
            setBackground(randomColor(getForeground()).brighter());
            layout = new MigLayout("wrap, fill, ins 0",
                    "0[fill]0", "0[80%, fill]10[20%]10");

            info = new JPanel(new GridLayout(1, 1));

            setLayout(layout);
            lblName = new FlatTextArea();
            lblName.setForeground(Color.black);
            lblName.setLineWrap(true);
            lblName.setText(modal.getRoomName());
            lblName.setEditable(false);
            lblName.setFocusable(false);

            info.add(lblName);
            info.setBackground(Color.white);
            info.setOpaque(true);
            participantsCount = new JLabel(modal.getNumOfParticipants()
                    + " / " + modal.getParticipants().length);
            participantsCount.setHorizontalAlignment(JLabel.CENTER);
            joinBtn = new FlatButton();

            joinBtn.addActionListener((ActionEvent e) -> {
                joinWaitingRoom(modal);
            });

            joinBtn.setText("Join");
            joinBtn.setOpaque(false);
            joinBtn.setButtonType(FlatButton.ButtonType.roundRect);

            add(info, "c");
            add(participantsCount, "split, w 80%");
            add(joinBtn, "w 20%, h 30!");
        }

        @Override
        protected void paintComponent(Graphics grphcs) {
            super.paintComponent(grphcs);
            Graphics2D g2 = (Graphics2D) grphcs.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(getBackground());
            g2.fillRoundRect(0, 0, getWidth(),
                    getHeight(), 15, 15);
            g2.dispose();
        }

        private Color randomColor(Color anotherColor) {
            Random rand = new Random();

            int r = rand.nextInt(256);
            int g = rand.nextInt(256);
            int b = rand.nextInt(256);

            Color color;
            do {
                color = new Color(r, g, b);
            } while (color.equals(anotherColor)
                    || color.equals(Color.white)
                    || color.equals(Color.black));

            return color;
        }
    }
}
