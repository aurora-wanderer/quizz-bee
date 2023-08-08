package app.quizbee.component.room;

import app.quizbee.client.Client;
import app.quizbee.dialog.notification.Notification;
import app.quizbee.entity.Question;
import app.quizbee.main.Application;
import app.quizbee.modal.LoginModal;
import app.quizbee.modal.PlayerModal;
import app.quizbee.modal.RoomModal;
import app.quizbee.modal.ScoreModal;
import app.quizbee.system.Colors;
import app.quizbee.system.SVGIcon;

import com.formdev.flatlaf.extras.FlatSVGIcon;
import com.formdev.flatlaf.extras.components.FlatButton;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import net.miginfocom.swing.MigLayout;

public class WaitingRoom extends JPanel {

    private final Client client = Application.getClient();

    private JPanel header, playersPanel;
    private FlatButton prevButton, helpButton;
    private JLabel titleRoom;
    private FlatButton readyButton;
    private PlayerModal playerModal;
    private PlayQuiz playRoom;
    private RankingRoom rankingRoom;

    public WaitingRoom(PlayerModal playerModal, RoomModal roomModal) {
        this.playerModal = playerModal;
        header = new JPanel();
        playersPanel = new JPanel();
        readyButton = new FlatButton();

        playRoom = new PlayQuiz(roomModal.getList(),
                new ScoreModal(playerModal.getName(), 0));

        if (LoginModal.user != null) {
            if (LoginModal.user.equals(roomModal.getOwner())) {
                readyButton.setText("Start");
            } else {
                readyButton.setText("Ready");
            }
        } else {
            readyButton.setText("Ready");
        }

        this.setLayout(new MigLayout("fill, ins 0",
                "0[fill]0", "0[]15[]0"));

        this.add(header, "north, w 100%");
        this.add(playersPanel, "w 100%");
        this.add(readyButton, "pos 0.5al 1al n n, w 100!, h 30!");
        init();
    }

    private void init() {
        createHeader();
        createBody();
        udpateStatus();
        fillPlayers();
    }

    public void createHeader() {
        MigLayout headerLayout = new MigLayout("filly, left",
                "5[]5",
                "0[fill, c]0");
        header.setLayout(headerLayout);

        prevButton = createButton("Quit", SVGIcon.PREVIOUS);

        helpButton = new FlatButton();
        helpButton.setButtonType(FlatButton.ButtonType.help);

        header.add(helpButton, "wrap");
    }

    public void createBody() {
        MigLayout plLayout = new MigLayout("wrap, fillx, ins 0",
                "15[fill]15[fill]15[fill]15",
                "15[fill]15");
        playersPanel.setLayout(plLayout);
        this.add(playersPanel, "h 100%");
    }

    private FlatButton createButton(String text, FlatSVGIcon icon) {
        icon.setColorFilter(SVGIcon.setColor(
                Colors.FG_MENU_ITEM));
        FlatButton button = new FlatButton();

        button.setToolTipText(text);
        button.setIcon(icon);

        button.setButtonType(FlatButton.ButtonType.roundRect);
        button.setOpaque(false);
        button.setContentAreaFilled(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));

        return button;
    }

    public void addPlayerView(PlayerModal p) {
        client.getService().addPlayer(p);
        fillPlayers();
    }

    public void fillPlayers() {
        client.getService().getPlayers(playerModal.getRoomID(), players -> {
            playersPanel.removeAll();
            players.forEach(player -> {
                playersPanel.add(new PlayerView(player));
            });
            playersPanel.revalidate();
        });
    }

    public void addPrevEvent(ActionListener act) {
        prevButton.addActionListener(act);
    }

    public void addHelpButton(ActionListener act) {
        helpButton.addActionListener(act);
    }

    private JLabel createTitle(String id, String name) {
        JLabel label = new JLabel();

        label.setText(name);
        label.setFont(label.getFont().deriveFont(Font.BOLD, 30f));
        label.setHorizontalAlignment(SwingConstants.CENTER);

        LineBorder lineBorder = new LineBorder(Color.red, 2,
                true);

        TitledBorder titledBorder = new TitledBorder(
                lineBorder,
                id,
                TitledBorder.CENTER,
                TitledBorder.CENTER
        );

        label.setBorder(titledBorder);

        return label;
    }

    public void setTitle(String id, String name) {
        titleRoom = createTitle(id, name);
        header.add(titleRoom, "w 100%");
    }

    public void udpateStatus() {
        if (readyButton.getText().equalsIgnoreCase("Ready")) {
            readyButton.addActionListener((ActionEvent e) -> {
                playerModal.setReady(!playerModal.isReady());
                client.getService().updateStatus(playerModal);
                if (playerModal.isReady()) {
                    client.getService().letPlay((List<Question> data) -> {
                        Application.getDashboard().showPage(playRoom);
                    });
                }
            });
            return;
        }

        readyButton.addActionListener((act) -> {
            client.getService().checkReady((Boolean data) -> {
                if (!data) {
                    new Notification(Application.getDashboard(),
                            Notification.Type.WARNING,
                            Notification.Location.TOP_CENTER,
                            "All player isn't ready"
                    ).showNotification();
                    return;
                }
                client.getService().startGame(playerModal.getRoomID(), 
                        (List<PlayerModal> data1) -> {
                    rankingRoom = new RankingRoom(data1);
                    Application.getDashboard().showPage(rankingRoom);
                });
            });
        });
    }

    final class PlayerView extends JPanel {

        private JLabel namePlayer;
        private JLabel lblStatus;

        private PlayerView() {
            this.setOpaque(true);
            this.setBackground(this.getBackground().darker());
            namePlayer = new JLabel();
            lblStatus = new JLabel();
        }

        public PlayerView(PlayerModal modal) {
            this();
            initPlayerView();
            setNamePlayer(modal.getName());
            setStatus(modal.isReady());
        }

        private void initPlayerView() {
            MigLayout layout = new MigLayout("fillx, ins 0",
                    "5[50::, fill]20[20, fill]5",
                    "5[50::, fill]5");
            this.setLayout(layout);

            namePlayer.setHorizontalAlignment(SwingConstants.CENTER);
            namePlayer.setFont(namePlayer.getFont().deriveFont(18f));

            this.add(namePlayer);
            this.add(lblStatus);
        }

        public void setNamePlayer(String name) {
            namePlayer.setText(name);
        }

        private void setStatus(boolean ready) {
            lblStatus.setIcon(ready ? SVGIcon.OK : SVGIcon.READY);
        }
    }
}
