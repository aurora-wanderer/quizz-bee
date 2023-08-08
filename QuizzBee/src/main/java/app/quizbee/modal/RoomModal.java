package app.quizbee.modal;

import app.quizbee.entity.Question;
import app.quizbee.entity.User;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import lombok.Getter;
import lombok.Setter;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

@Getter
@Setter
public class RoomModal implements Serializable {

    User owner;
    List<Question> list;
    String roomID;
    String roomName;
    String roomPass;
    String[] participants;
    Integer numOfParticipants;

    public RoomModal() {
    }

    public RoomModal(User owner,
            List<Question> list,
            String roomID,
            String roomName,
            String roomPass,
            String[] participants,
            Integer numOfParticipants) {
        this.owner = owner;
        this.list = list;
        this.roomID = roomID;
        this.roomName = roomName;
        this.roomPass = roomPass;
        this.participants = participants;
        this.numOfParticipants = numOfParticipants;
    }

    public RoomModal(JSONObject json) {
        try {
//            this.owner = 
            this.list = new ArrayList<>();
            JSONArray arr = json.getJSONArray("list");
            for (int i = 0; i < arr.length(); i++) {
                Question q = new Question(arr.getJSONObject(i));
                this.list.add(q);
            }
            this.roomID = json.getString("roomID");
            this.roomName = json.getString("roomName");
            this.roomPass = json.getString("roomPass");
            JSONArray arrJSON = json.getJSONArray("participants");
            this.participants = new String[arrJSON.length()];

            for (int i = 0; i < arrJSON.length(); i++) {
                this.participants[i] = arrJSON.get(i).toString();
            }
            this.numOfParticipants = json.getInt("numOfParticipants");
        } catch (JSONException ex) {
            Logger.getLogger(Question.class.getName())
                    .log(Level.SEVERE, null, ex);
        }
    }

    public JSONObject toJSON() {
        JSONObject jsonObj = new JSONObject();
        try {
            jsonObj.put("owner", owner.toJSON());
            jsonObj.put("list", list);
            jsonObj.put("roomID", roomID);
            jsonObj.put("roomName", roomName);
            jsonObj.put("roomPass", roomPass);
            jsonObj.put("participants", participants);
            jsonObj.put("numOfParticipants", numOfParticipants);
        } catch (JSONException ex) {
            Logger.getLogger(RoomModal.class.getName())
                    .log(Level.SEVERE, null, ex);
        }

        return jsonObj;
    }

    @Override
    public String toString() {
        return toJSON().toString();
    }
}
