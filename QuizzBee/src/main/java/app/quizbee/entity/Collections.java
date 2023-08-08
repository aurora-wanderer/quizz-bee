package app.quizbee.entity;

import lombok.Getter;
import lombok.Setter;
import org.json.JSONObject;

@Getter
@Setter
public class Collections extends Entity {

    Integer ID;
    String userID;
    Integer total_collections;

    @Override
    public JSONObject toJSON() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
