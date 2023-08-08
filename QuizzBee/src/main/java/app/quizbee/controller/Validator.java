package app.quizbee.controller;

import app.quizbee.material.field.PasswordField;
import app.quizbee.material.field.TextField;
import java.util.Arrays;
import java.util.Objects;

public class Validator {

    public Validator() {
    }

    public static boolean isRequired(Object obj, String msg) {

        String message = "This field hasn't empty!";

        if (obj instanceof TextField input) {
            String value = input.getText();

            message = !value.isEmpty() ? ""
                    : (!msg.isEmpty() ? msg : message);

            input.setMessage(message);
            return !value.trim().isEmpty();
        }

        if (obj instanceof PasswordField input) {
            String value = input.getValue();

            message = !value.isEmpty() ? ""
                    : (!msg.isEmpty() ? msg : message);

            input.setMessage(message);
            return !value.trim().isEmpty();
        }

        return Objects.isNull(obj);
    }

    public static boolean validate(Boolean[] rules) {
        return Arrays.asList(rules).stream().allMatch(rule -> rule);
    }

    public static boolean isEmail(TextField email, String msg) {
        final String regex = "^\\w+([\\.-]?\\w+)*@\\w+([\\.-]?\\w+)*(\\.\\w{2,3})+$";
        String message = "Invalid email format!";

        message = email.getText().matches(regex) ? ""
                : !msg.isEmpty() ? msg : message;
        email.setMessage(message);
        return email.getText().matches(regex);
    }

    public static boolean isPassword(PasswordField pass, String msg) {
        final String value = pass.getValue();
        final int MIN_LENGTH = 6;
        final int MAX_LENGTH = 12;
        String message = "Password must be 6-12 characters!";

        final boolean condition = value.length() >= MIN_LENGTH
                && value.length() <= MAX_LENGTH;

        message = condition ? "" : !msg.isEmpty() ? msg : message;
        pass.setMessage(message);

        return condition;
    }

    public static boolean compare(PasswordField input1, PasswordField input2, String msg) {
        String message = "Confirm value not match!";

        final boolean condition = input2.getValue()
                .equalsIgnoreCase(input1.getValue());

        message = condition ? "" : !msg.isEmpty() ? msg : message;
        input2.setMessage(message);
        return condition;
    }
}
