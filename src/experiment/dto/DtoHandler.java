package experiment.dto;

import experiment.util.reflection.Type;

import java.beans.IntrospectionException;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

import static experiment.dto.Property.property;
import static experiment.util.collections.Collections.from;
import static experiment.util.reflection.ReflectionHelper.typeOf;

public class DtoHandler<T> implements InvocationHandler {

    private DtoDataContainer container;
    private Type type;

    DtoHandler(Class<T> dtoClass) throws IntrospectionException {
        this.type = typeOf(dtoClass);
        container = new DtoDataContainer(from(type.propertyDescriptions()).select(property()).toList());
    }

    @Override
    public Object invoke(Object target, Method method, Object[] parameters)
            throws Throwable {
        if (container.knowAsSetter(method)) {
            container.setValue(method, parameters[0]);
        } else if (container.knowAsGetter(method)) {
            return container.getValue(method);
        } else {
            return method.invoke(this, parameters);
        }
        return null;
    }

}
