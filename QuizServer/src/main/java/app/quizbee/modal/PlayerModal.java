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

    @Override
    public String toString() {
        JSONObject json = new JSONObject();
        try {
            json.put("playerID", playerID);
            json.put("roomID", roomID);
            json.put("name", name);
            json.put("ready", ready);
        } catch (JSONException ex) {
            Logger.getLogger(PlayerModal.class.getName())
                    .log(Level.SEVERE, null, ex);
        }

        return json.toString();
    }
}
