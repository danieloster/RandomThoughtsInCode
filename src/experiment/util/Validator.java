package experiment.util;

import com.google.common.base.Predicate;

public class Validator<T> {

    private String message;
    private Predicate<T> predicate;
    private T value;

    private Validator(Predicate<T> predicate, T value) {
        this.predicate = predicate;
        this.value = value;
    }


    public void withMessage(String message) {
        this.message = message;
    }

    public String message() {

        return message.replace("{value}", value.toString());
    }

    public boolean isValid() {
        return predicate.apply(value);
    }


}
