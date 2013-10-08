package experiment.util.reflection;

import java.beans.IntrospectionException;
import java.lang.reflect.Method;

public class ReflectionHelper {
    public static MethodWrapper method(Method method) {
        return new MethodWrapper(method);
    }

    public static Type typeOf(Class<?> typeClass) throws IntrospectionException {
        return new Type(typeClass);
    }

}
