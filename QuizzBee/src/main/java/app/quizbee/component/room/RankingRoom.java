package app.quizbee.component.room;

import app.quizbee.main.Application;
import app.quizbee.modal.PlayerModal;
import app.quizbee.modal.ScoreModal;
import java.awt.Color;
import java.util.List;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import net.miginfocom.swing.MigLayout;

public class RankingRoom extends JPanel {

    private MigLayout layout;

    public RankingRoom() {
        init();
        autoUpdateScore();
    }

    public RankingRoom(List<PlayerModal> lstPlayer) {
        init();
        fill(lstPlayer);
        autoUpdateScore();
    }

    private void init() {
        layout = new MigLayout("fillx, wrap, ttb",
                "10[fill, c]10",
                "10[fill, c]10");
        this.setLayout(layout);
    }

    private void fill(List<PlayerModal> lst) {
        this.removeAll();
        JLabel label = new JLabel("TOP PLAYER HIGHEST SCORE");
        label.setFont(label.getFont().deriveFont(20f));
        label.setHorizontalAlignment(SwingConstants.CENTER);
        this.add(label, "north, w 100%, h 50!");

        lst.forEach(item -> {
            ScoreView scv = new ScoreView(item, lst.indexOf(item));
            Application.getClient().getService().addScore(scv.getScoreModal());
            this.add(scv, "h 30!");
        });
        this.revalidate();
    }

    private void autoUpdateScore() {
        Application.getClient().getService().autoUpdateScore(
                (List<ScoreModal> data) -> {
                    this.removeAll();
                    JLabel label = new JLabel("TOP PLAYER HIGHEST SCORE");
                    label.setFont(label.getFont().deriveFont(20f));
                    label.setHorizontalAlignment(SwingConstants.CENTER);
                    this.add(label, "north, w 100%, h 50!");
                    data.forEach(item -> {
                        this.add(new ScoreView(item, data.indexOf(item)),
                                "h 30!");
                    });
                    this.revalidate();
                });
    }

    class ScoreView extends JPanel {

        private JLabel lblTop;
        private JLabel lblName;
        private JLabel lblScore;

        public ScoreView() {
            initScoreView();
        }

        private ScoreView(PlayerModal pm, int top) {
            this.lblTop = new JLabel(top + 1 + "");
            lblName = new JLabel(pm.getName());
            lblScore = new JLabel("0");
            initScoreView();
        }

        private ScoreView(ScoreModal pm, int top) {
            this.lblTop = new JLabel(top + 1 + "");
            checkTop(top);
            lblName = new JLabel(pm.getName());
            lblScore = new JLabel(pm.getScore() + "");
            initScoreView();
        }

        private void initScoreView() {
            this.setLayout(new MigLayout("fillx, ins 0",
                    "5[fill, c]10[fill, l]10[fill, c]5",
                    "5[fill, c]5"));

            this.add(lblTop, "w 10%, h 30!");
            this.add(lblName, "w 80%, h 30!");
            this.add(lblScore, "w 10%, h 30!");
        }

        private void checkTop(int top) {
            this.setOpaque(true);
            switch (top) {
                case 0 ->
                    this.setBackground(Color.red);
                case 1 ->
                    this.setBackground(Color.yellow);
                case 2 ->
                    this.setBackground(Color.blue);
                default ->
                    this.setOpaque(false);
            }
        }

        public ScoreModal getScoreModal() {
            return new ScoreModal(lblName.getText(),
                    Integer.parseInt(lblScore.getText()));
        }
    }
}
