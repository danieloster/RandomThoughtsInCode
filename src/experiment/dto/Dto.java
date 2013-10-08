package experiment.dto;

import java.beans.IntrospectionException;
import java.lang.reflect.Proxy;

public class Dto {

    public static <T> T create(Class<T> type) throws IllegalArgumentException, IntrospectionException {
        return (T) Proxy.newProxyInstance(type.getClassLoader(), new Class<?>[]{type}, new DtoHandler<T>(type));
    }
}
