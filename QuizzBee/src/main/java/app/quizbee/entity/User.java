package app.quizbee.entity;

import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;
import lombok.Getter;
import lombok.Setter;
import org.json.JSONException;
import org.json.JSONObject;

@Getter
@Setter
public class User extends Entity {

    private Integer ID;
    private String username;
    private String password;
    private String email;
    private String fullname;
    private Boolean gender;
    private String status;
    private String active;

    public User() {
    }

    public User(JSONObject json) {
        try {
            this.ID = json.getInt("ID");
            this.username = json.getString("username");
            this.password = json.getString("password");
            this.email = json.getString("email");
            this.fullname = json.getString("fullname");
            this.gender = json.getBoolean("gender");
            this.status = json.getString("status");
            this.active = json.getString("active");
        } catch (JSONException ex) {
            Logger.getLogger(User.class.getName())
                    .log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public JSONObject toJSON() {
        JSONObject json = new JSONObject();
        try {
            json.put("ID", ID);
            json.put("username", username);
            json.put("password", password);
            json.put("email", email);
            json.put("fullname", fullname);
            json.put("gender", gender);
            json.put("status", status);
            json.put("active", active);
        } catch (JSONException ex) {
            Logger.getLogger(User.class.getName())
                    .log(Level.SEVERE, null, ex);
        }
        return json;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 67 * hash + Objects.hashCode(this.ID);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final User other = (User) obj;
        return Objects.equals(this.username, other.username);
    }
}
