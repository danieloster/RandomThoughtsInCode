package experiment.dto;

public class Value<T> {

    private T value;

    public void set(Object value) {
        this.value = (T) value;
    }

    public T get() {
        return value;
    }
}
