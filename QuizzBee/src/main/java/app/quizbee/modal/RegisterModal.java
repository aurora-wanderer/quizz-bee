package app.quizbee.modal;

import app.quizbee.entity.User;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegisterModal {
    
    private User user;
    private String code;
}
