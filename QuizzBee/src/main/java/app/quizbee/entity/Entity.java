package app.quizbee.entity;

import java.io.Serializable;
import org.json.JSONObject;

public abstract class Entity implements Serializable {

    public abstract JSONObject toJSON();
}
