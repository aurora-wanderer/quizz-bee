package app.quizbee.modal;

import app.quizbee.entity.Question;
import app.quizbee.entity.User;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RoomModal {

    User owner;
    List<Question> list;
    String roomID;
    String roomName;
    String roomPass;
    String[] participants;
    Integer numOfParticipants;

    @Override
    public boolean equals(Object obj) {
        if ((null == obj) || obj.getClass() != RoomModal.class) {
            return false;
        }

        RoomModal o = (RoomModal) obj;
        return roomID.equalsIgnoreCase(o.getRoomID());
    }

}
