package app.quizbee.view.form;

import app.quizbee.entity.Question;
import app.quizbee.material.panel.RoundedPanel;
import com.formdev.flatlaf.extras.components.FlatButton;
import com.formdev.flatlaf.extras.components.FlatCheckBox;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.stream.Stream;
import javax.swing.JPanel;
import javax.swing.JTextPane;
import javax.swing.SwingConstants;
import net.miginfocom.swing.MigLayout;

public class QuestionForm extends JPanel {

    private final Question question;
    private final LinkedHashMap<FlatButton, Boolean> mapSelection;
    private LinkedHashMap<String, Boolean> mapQuestion, shuffleMap;
    private RoundedPanel questionPane, answerPane;
    private FlatButton ansBtn1, ansBtn2, ansBtn3, ansBtn4, finishBtn;
    private Color colorBtn;
    private final boolean many;

    public QuestionForm(Question question) {
        this.question = question;
        this.mapSelection = new LinkedHashMap<>();
        this.mapQuestion = new LinkedHashMap<>();
        this.shuffleMap = new LinkedHashMap<>();
        this.many = this.question.isType();

        setOpaque(false);
        init();
        createQuestionPane(question);
        createAnswerPane(question);

        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                revalidate();
                repaint();
                questionPane.revalidate();
                answerPane.revalidate();
                questionPane.repaint();
                answerPane.repaint();
            }
        });
    }

    @Override
    public void updateUI() {
        super.updateUI();
        colorBtn = new FlatButton().getBackground();
    }

    private void init() {
        this.setBackground(new Color(0, 0, 0, 0));
        this.setLayout(new MigLayout("wrap", "0[fill]0",
                "0[fill]0"));

        questionPane = new RoundedPanel(15);
        answerPane = new RoundedPanel(15);
        finishBtn = new FlatButton();

        finishBtn.setText("Next");
        finishBtn.setButtonType(FlatButton.ButtonType.roundRect);

        this.add(questionPane, "w 100%, h 30%");
        this.add(answerPane, "h 60%, c");
        this.add(finishBtn, "w 100!, h 30!, c");
        finishBtn.setEnabled(false);
    }

    private void createQuestionPane(Question question) {
        MigLayout qLayout = new MigLayout("fill, wrap",
                "10[fill]10", "0[]10[]5");

        questionPane.setLayout(qLayout);
        JTextPane qArea = createQuestionArea(question);
        questionPane.add(qArea, "w 100%, h 100%");
    }

    public JTextPane createQuestionArea(Question question) {
        JTextPane qArea = new JTextPane();
        qArea.setEditable(false);
        qArea.setFocusable(false);
        qArea.setFont(getFont().deriveFont(Font.BOLD, 18f));
        qArea.setText(question.getValue());
        return qArea;
    }

    private void createAnswerPane(Question question) {
        MigLayout ansLayout = new MigLayout("filly, wrap",
                "0[]15[]0", "15[]15[]15");
        answerPane.setLayout(ansLayout);

        initAnswerButton(question);
    }

    public void initAnswerButton(Question answer) {
        mapQuestion.put(answer.getAns1(), answer.getCorrects()[0]);
        mapQuestion.put(answer.getAns2(), answer.getCorrects()[1]);
        mapQuestion.put(answer.getAns3(), answer.getCorrects()[2]);
        mapQuestion.put(answer.getAns4(), answer.getCorrects()[3]);

        List<String> list = new ArrayList<>(mapQuestion.keySet());
        Collections.shuffle(list);

        list.forEach(k -> shuffleMap.put(k, mapQuestion.get(k)));

        ansBtn1 = createAnswerButton(list.get(0));
        ansBtn2 = createAnswerButton(list.get(1));
        ansBtn3 = createAnswerButton(list.get(2));
        ansBtn4 = createAnswerButton(list.get(3));

        mapSelection.put(ansBtn1, false);
        mapSelection.put(ansBtn2, false);
        mapSelection.put(ansBtn3, false);
        mapSelection.put(ansBtn4, false);

        answerPane.add(ansBtn1, "w 50%, h 50%");
        answerPane.add(ansBtn2, "w 50%, h 50%");
        answerPane.add(ansBtn3, "w 50%, h 50%");
        answerPane.add(ansBtn4, "w 50%, h 50%");
    }

    private FlatButton createAnswerButton(String ansValue) {
        FlatButton button = new FlatButton();

        button.setLayout(new MigLayout());
        button.setButtonType(FlatButton.ButtonType.square);
        button.setText(ansValue);
        button.setHorizontalAlignment(SwingConstants.CENTER);
        button.setVerticalAlignment(SwingConstants.CENTER);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));

        button.addActionListener((ActionEvent e) -> {
            finishBtn.setEnabled(true);
            if (!many) {
                clearSeleted();
                setSelected(button);
                return;
            }
            setSelected(button);
        });

        if (many) {
            addCheckBoxButton(button);
        }

        return button;
    }

    public void addCheckBoxButton(FlatButton button) {
        FlatCheckBox cb = new FlatCheckBox();
        cb.removeMouseListener(cb.getMouseListeners()[0]);
        cb.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                cb.setSelected(!cb.isSelected());
            }
        });

        button.add(cb, "pos 0al 0al n n");

        button.addActionListener((ActionEvent e) -> {
            cb.setSelected(!cb.isSelected());
        });
    }

    public void addFinishQuestion(ActionListener l) {
        finishBtn.addActionListener(l);
    }

    private boolean getrCorrectsAns() {
        Boolean[] values = mapSelection.values().toArray(Boolean[]::new);

        if (Stream.of(values).allMatch(t -> t == false)) {
            return false;
        }

        return mapSelection.entrySet().stream()
                .allMatch(e -> e.getValue().equals(shuffleMap.get(e.getKey().getText())));
    }

    private void setSelected(FlatButton button) {
        if (mapSelection.get(button)) {
            button.setBackground(button.getBackground().brighter());
        } else {
            button.setBackground(button.getBackground().darker());
        }
        mapSelection.replace(button, !mapSelection.get(button));
    }

    private void clearSeleted() {
        mapSelection.keySet().forEach(button -> {
            mapSelection.replace(button, false);
            button.setBackground(colorBtn);
        });
    }

    public int getScore(int timeRemain) {
        long factor = mapSelection.values().stream().filter(b -> b == true).count();
        if (!getrCorrectsAns()) {
            factor = 0;
        }

        return (int) factor * 100 * timeRemain;
    }

    public void setFinishText(String value) {
        finishBtn.setText(value);
    }
}
