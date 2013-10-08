package experiment.util.reflection;

import java.lang.reflect.Method;

public class MethodWrapper {

    private Method method;

    MethodWrapper(Method method) {
        this.method = method;
    }

    public String asFieldKey() {
        return method.getName();
    }

}
