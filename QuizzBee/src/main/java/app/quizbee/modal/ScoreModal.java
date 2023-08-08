package app.quizbee.modal;

import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.JSONException;
import org.json.JSONObject;

public class ScoreModal implements Serializable {

    String name;
    int score;

    public ScoreModal() {
    }

    public ScoreModal(String name, int score) {
        this.name = name;
        this.score = score;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public JSONObject toJSON() {
        JSONObject json = new JSONObject();
        try {
            json.put("name", name);
            json.put("score", score);
        } catch (JSONException ex) {
            Logger.getLogger(ScoreModal.class.getName()).log(Level.SEVERE, null, ex);
        }

        return json;
    }

}
