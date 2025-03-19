package en.sd.chefmgmt.util;

import java.lang.reflect.Field;
import java.util.Optional;

import en.sd.chefmgmt.repository.spec.predicate.PredicateStrategy;
import lombok.experimental.UtilityClass;

@UtilityClass
public final class ReflectionUtil {

    public static Optional<Object> getFieldValue(Object filter, String fieldName) {
        try {
            Field field = filter.getClass().getDeclaredField(fieldName);
            field.setAccessible(true);

            return Optional.ofNullable(field.get(filter));
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException("Error accessing field: " + fieldName, e);
        }
    }

    public static Class<?> getGenericType(PredicateStrategy<?> strategy) {
        return (Class<?>) ((java.lang.reflect.ParameterizedType) strategy.getClass()
                .getGenericInterfaces()[0])
                .getActualTypeArguments()[0];
    }
}
