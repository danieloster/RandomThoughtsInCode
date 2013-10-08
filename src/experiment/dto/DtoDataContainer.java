package experiment.dto;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static experiment.dto.Functions.*;
import static experiment.util.Validate.validate;
import static experiment.util.collections.Collections.from;

class DtoDataContainer {
    private Map<String, Property<?>> mappedByName = new HashMap<String, Property<?>>();

    private Map<String, Property<?>> mappedBySetter = new HashMap<String, Property<?>>();
    private Map<String, Property<?>> mappedByGetter = new HashMap<String, Property<?>>();


    public DtoDataContainer(List<Property<?>> propertyList) {
        mappedByName = from(propertyList).createMap(withNameAsKey());
        mappedBySetter = from(propertyList)
                .where(propertyHasWriteMethod())
                .createMap(withNameAsKey());
        mappedByGetter = from(propertyList)
                .where(propertyHasReadMethod())
                .createMap(withNameAsKey());
    }

    void setValue(String name, Object value) {
        validate(exist(name));
        mappedByName.get(name).newValue(value);
    }

    void setValue(Method set, Object value) {
        validate(setterExist(set));
        mappedBySetter.get(set).newValue(value);
    }

    Object getValue(String name) {
        validate(exist(name));
        return mappedByName.get(name).value();
    }

    Object getValue(Method get) {
        validate(getterExist(get));
        return mappedByGetter.get(get).value();
    }

    boolean knowAsSetter(Method method) {
        return setterExist(method);
    }

    boolean knowAsGetter(Method method) {
        return getterExist(method);
    }

    private boolean setterExist(Method set) {
        return mappedBySetter.containsKey(set.getName());
    }

    private boolean getterExist(Method set) {
        return mappedByGetter.containsKey(set.getName());

    }

    private boolean exist(String name) {
        return mappedByName.containsKey(name);
    }


}
