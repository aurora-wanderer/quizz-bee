package app.quizbee.modal;

import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;
import lombok.Getter;
import lombok.Setter;
import org.json.JSONException;
import org.json.JSONObject;

@Getter
@Setter
public class PlayerModal implements Serializable {

    private String playerID;
    private String roomID;
    private String name;
    private boolean ready;

    public PlayerModal() {
    }

    public PlayerModal(String playerID, String roomID, String name, boolean ready) {
        this.playerID = playerID;
        this.roomID = roomID;
        this.name = name;
        this.ready = ready;
    }

    public PlayerModal(String IDRoom, String name, boolean status) {
        this.playerID = hashCode() + "";
        this.roomID = IDRoom;
        this.name = name.equalsIgnoreCase("Guest")
                ? name + playerID : name;
        this.ready = status;
    }

    public PlayerModal(JSONObject jsonObject) {
        try {
            this.playerID = jsonObject.getString("playerID");
            this.roomID = jsonObject.getString("roomID");
            this.name = jsonObject.getString("name");
            this.ready = jsonObject.getBoolean("ready");
        } catch (JSONException ex) {
            Logger.getLogger(PlayerModal.class.getName())
                    .log(Level.SEVERE, null, ex);
        }
    }

    public JSONObject toJSON() {
        JSONObject json = new JSONObject();
        try {
            json.put("playerID", playerID);
            json.put("roomID", roomID);
            json.put("name", name);
            json.put("ready", ready);
        } catch (JSONException ex) {
            Logger.getLogger(PlayerModal.class.getName()).log(Level.SEVERE, null, ex);
        }
        return json;
    }

    @Override
    public String toString() {
        return toJSON().toString();
    }
}
