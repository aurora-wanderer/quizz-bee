package app.quizbee.modal;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MailModal {

    private Boolean success;
    private String message;
    private String code;

    public MailModal(Boolean success, String message, String code) {
        super();
        this.success = success;
        this.message = message;
        this.code = code;
    }

}
