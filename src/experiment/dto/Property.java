package experiment.dto;

import com.google.common.base.Function;

import java.beans.PropertyDescriptor;

public class Property<T> {


    private final String name;
    private final String setter;
    private final String getter;
    private final Class<?> propertyType;
    private final Value<T> value;

    public Property(String name, String setter, String getter, Class<T> propertyType) {
        this.name = name;
        this.setter = setter;
        this.getter = getter;
        this.propertyType = propertyType;
        this.value = new Value<T>();
    }

    public static Function<PropertyDescriptor, Property<?>> property() {
        return new Function<PropertyDescriptor, Property<?>>() {
            @Override
            public Property<?> apply(java.beans.PropertyDescriptor descriptor) {
                String name = descriptor.getName();
                String setter = descriptor.getWriteMethod() == null ? null : descriptor.getWriteMethod().getName();
                String getter = descriptor.getReadMethod() == null ? null : descriptor.getReadMethod().getName();
                return new Property(name, setter, getter, descriptor.getPropertyType());
            }
        };

    }


    public String getName() {
        return name;
    }

    public boolean hasReadMethod() {
        return getter != null;
    }

    public boolean hasWriteMethod() {
        return setter != null;
    }

    public void newValue(Object value) {
        this.value.set(value);
    }

    public Object value() {
        return value.get();
    }
}
