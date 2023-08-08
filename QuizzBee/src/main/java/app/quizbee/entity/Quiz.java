package app.quizbee.entity;

import java.util.Date;
import lombok.Getter;
import lombok.Setter;
import org.json.JSONObject;

@Getter
@Setter
public class Quiz extends Entity {

    private Integer ID;
    private String name;
    private Date createdDate;
    private Integer totalQuestion;
    private Integer collections_ID;

    @Override
    public JSONObject toJSON() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}
