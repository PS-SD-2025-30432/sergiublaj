package en.sd.chefmgmt.util;

import java.lang.reflect.Field;
import java.util.Optional;

public final class ReflectionUtil {

    private ReflectionUtil() { }


    public static Optional<Object> getFieldValue(Object filter, String fieldName) {
        try {
            Field field = filter.getClass().getDeclaredField(fieldName);
            field.setAccessible(true);

            return Optional.ofNullable(field.get(filter));
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException("Error accessing field: " + fieldName, e);
        }
    }
}
