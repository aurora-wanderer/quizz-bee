package app.quizbee.entity;

import java.util.HashMap;
import java.util.Map;
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
        setData(json);
    }

    public User(String s) {
        try {
            JSONObject json = new JSONObject(s);
            setData(json);
        } catch (JSONException ex) {
            Logger.getLogger(User.class.getName())
                    .log(Level.SEVERE, null, ex);
        }
    }

    public void setData(JSONObject json) {
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
    public Object[] toInsertData() {
        return new Object[]{
            username, password, email, fullname, status, active
        };
    }

    @Override
    public Object[] toUpdateData() {
        return new Object[]{
            username, password, email, fullname, gender, ID
        };
    }

    @Override
    public Object[] toDeleteData() {
        return new Object[]{
            ID, username
        };
    }

    @Override
    public boolean equals(Object obj) {
        if ((null == obj) || obj.getClass() != User.class) {
            return false;
        }

        User u = (User) obj;

        return username.equalsIgnoreCase(u.getUsername())
                && email.equalsIgnoreCase(u.getEmail());
    }

    public Map toMap() {

        HashMap<String, Object> map = new HashMap<>();

        map.put("ID", ID);
        map.put("username", username);
        map.put("password", password);
        map.put("email", email);
        map.put("fullname", fullname);
        map.put("gender", gender);
        map.put("status", status);
        map.put("active", active);

        return map;
    }
    
}
