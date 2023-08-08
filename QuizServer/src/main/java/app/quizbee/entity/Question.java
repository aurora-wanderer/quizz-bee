package app.quizbee.entity;

import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;
import lombok.Getter;
import lombok.Setter;
import org.json.JSONException;
import org.json.JSONObject;

@Getter
@Setter
public class Question extends Entity {

    private Integer ID;
    private String value;
    private boolean type;
    private Integer quizID;
    private String ans1;
    private String ans2;
    private String ans3;
    private String ans4;
    private Boolean[] corrects;

    public Question() {
    }

    public Question(Question question) {
        this(question.getID(),
                question.getValue(),
                question.isType(),
                question.getQuizID(),
                question.getAns1(),
                question.getAns2(),
                question.getAns3(),
                question.getAns4(),
                question.getCorrects()
        );
    }

    public Question(Integer ID,
            String value,
            boolean type,
            Integer quizID,
            String ans1,
            String ans2,
            String ans3,
            String ans4,
            Boolean[] corrects
    ) {
        this.ID = ID;
        this.value = value;
        this.type = type;
        this.quizID = quizID;
        this.ans1 = ans1;
        this.ans2 = ans2;
        this.ans3 = ans3;
        this.ans4 = ans4;
        this.corrects = corrects;
    }

    public Question(String value) {
        try {
            JSONObject json = new JSONObject(value);
            this.value = json.getString("value");
            this.type = json.getBoolean("type");
            this.ans1 = json.getString("ans1");
            this.ans2 = json.getString("ans2");
            this.ans3 = json.getString("ans3");
            this.ans4 = json.getString("ans4");
            this.corrects = new Boolean[4];
            for (int i = 0; i < corrects.length; i++) {
                corrects[i] = json.getJSONArray("corrects")
                        .getBoolean(i);
            }
        } catch (JSONException ex) {
            Logger.getLogger(Question.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public Object[] toInsertData() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public Object[] toUpdateData() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public Object[] toDeleteData() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}
