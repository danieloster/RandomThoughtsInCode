package experiment.util;

public class Validate {
    public static void validate(boolean valid) {
        if (!valid) {
            throw new IllegalArgumentException();
        }
    }

    public static void validate(Validator<?> validator) {
        if (!validator.isValid()) {
            throw new IllegalArgumentException(validator.message());
        }
    }
}
