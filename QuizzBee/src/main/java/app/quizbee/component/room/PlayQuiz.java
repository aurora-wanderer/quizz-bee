package app.quizbee.component.room;

import app.quizbee.component.slideshow.SlideShow;
import app.quizbee.entity.Question;
import app.quizbee.main.Application;
import app.quizbee.material.progress.ProgressCircle;
import app.quizbee.modal.ScoreModal;
import app.quizbee.view.form.QuestionForm;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.Timer;
import net.miginfocom.swing.MigLayout;

public class PlayQuiz extends JPanel {

    private JLabel lblScore;
    private int totalScore;
    private ProgressCircle timeCountDown;
    private Timer timer;
    private SlideShow slideQuestion;
    private List<QuestionForm> questionsForm;
    private ScoreModal sm;

    public PlayQuiz(List<Question> list, ScoreModal sm) {
        this.sm = sm;
        setOpaque(false);
        init();
        setQuestions(list);
        timer.start();
    }

    private void init() {
        setLayout(new MigLayout("fill, ins 0",
                "10[fill]10", "10[fill]10"));
        setBackground(new Color(0, 0, 0, 0));

        questionsForm = new ArrayList<>();
        slideQuestion = new SlideShow();

        JPanel scoreAndTime = createScoreAndTime();
        this.add(scoreAndTime, "north");
        this.add(slideQuestion, "center");
    }

    private void setQuestions(List<Question> list) {
        Collections.shuffle(list);
        list.forEach(q -> {
            QuestionForm qf = new QuestionForm(q);
            qf.addFinishQuestion((ActionEvent e) -> {
                updateScore(qf.getScore(timeCountDown.getValue()));
                if (questionsForm.indexOf(qf) == questionsForm.size() - 1) {
                    RankingRoom rankingRoom = new RankingRoom();
                    Application.getDashboard().showPage(rankingRoom);
                    return;
                }
                nextQuestion();
            });
            questionsForm.add(qf);
        });

        questionsForm.forEach(item -> {
            if (questionsForm.indexOf(item) == questionsForm.size() - 1) {
                item.setFinishText("Finish");
            }
        });
        slideQuestion.initSlideshow(questionsForm.toArray(Component[]::new));
        slideQuestion.setAutoNext(false);
    }

    public void nextQuestion() {
        timer.stop();
        timer = new Timer(1000, countDown());
        timer.start();
        slideQuestion.next();
    }

    private JPanel createScoreAndTime() {
        JPanel panel = new JPanel();

        panel.setLayout(new MigLayout("fill",
                "20[fill]0[fill]20", "10[fill]10"));
        totalScore = 0;
        lblScore = new JLabel("Score: " + totalScore);
        lblScore.setFont(getFont().deriveFont(Font.BOLD, 20f));
        lblScore.setHorizontalAlignment(SwingConstants.LEFT);

        timeCountDown = new ProgressCircle();
        timeCountDown.setStringPainted(true);
        timeCountDown.getModel().setMaximum(30);
        timeCountDown.setString("30s");
        timeCountDown.setValue(30);
        timeCountDown.start();

        timer = new Timer(1000, countDown());
        panel.add(lblScore);
        panel.add(timeCountDown, "w 50!, h 50!, r");

        return panel;
    }

    private ActionListener countDown() {
        return new ActionListener() {
            int count = 30;

            @Override
            public void actionPerformed(ActionEvent e) {
                timeCountDown.setValue(count);
                timeCountDown.setString(count + "s");
                if (count == 0) {
                    timeCountDown.setValue(0);
                    timeCountDown.setString("0s");
                    if (slideQuestion.checkIndex()) {
                        slideQuestion.stop();
                        ((Timer) e.getSource()).stop();
                    } else {
                        nextQuestion();
                    }
                    return;
                }
                count--;
            }
        };
    }

    private void updateScore(int score) {
        totalScore += score;
        lblScore.setText("Score: " + totalScore);
        sm.setScore(totalScore);
        Application.getClient().getService().updateScore(sm);
    }

    public int getTotalScore() {
        return totalScore;
    }
}
