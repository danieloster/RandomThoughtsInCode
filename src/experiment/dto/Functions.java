package experiment.dto;

import com.google.common.base.Function;
import com.google.common.base.Predicate;

public abstract class Functions {

    static Function<Property<?>, String> withNameAsKey() {
        return new Function<Property<?>, String>() {
            @Override
            public String apply(Property<?> property) {
                return property.getName();
            }
        };
    }

    static Predicate<Property<?>> propertyHasWriteMethod() {
        return new Predicate<Property<?>>() {
            @Override
            public boolean apply(Property<?> property) {
                return property.hasWriteMethod();
            }
        };
    }


    static Predicate<Property<?>> propertyHasReadMethod() {
        return new Predicate<Property<?>>() {
            @Override
            public boolean apply(Property<?> property) {
                return property.hasReadMethod();
            }
        };
    }
}
