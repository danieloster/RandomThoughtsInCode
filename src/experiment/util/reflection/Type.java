package experiment.util.reflection;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.util.Arrays;
import java.util.List;

public class Type {

    private final BeanInfo beanInfo;
    private Class<?> typeClass;

    public Type(Class<?> typeClass) throws IntrospectionException {
        this.typeClass = typeClass;
        beanInfo = Introspector.getBeanInfo(typeClass);
    }

    public List<PropertyDescriptor> propertyDescriptions() {
        return Arrays.asList(beanInfo.getPropertyDescriptors());
    }
}