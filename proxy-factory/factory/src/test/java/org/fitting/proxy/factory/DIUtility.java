package org.fitting.proxy.factory;

import java.lang.reflect.Field;

import static org.junit.Assert.fail;

/** Dependency injection utility. */
public final class DIUtility {

    /** Constructor. */
    private DIUtility() {
    }

    /**
     * Injects the given value on the given object for the given field.
     * @param object    The object to inject the field value on.
     * @param fieldName The field to inject the value for.
     * @param value     The value to inject.
     */
    public static void inject(final Object object, final String fieldName, final Object value) {
        try {
            final Field field = object.getClass().getDeclaredField(fieldName);
            field.setAccessible(true);
            field.set(object, value);
        } catch (NoSuchFieldException e) {
            fail("Field [" + fieldName + "] does not exist.");
        } catch (IllegalAccessException e) {
            fail("Field [" + fieldName + "] is not accessible.");
        }
    }

    /**
     * Injects the given value on the given object for the given field.
     * @param clazz     The class to inject the field value on.
     * @param fieldName The field to inject the value for.
     * @param value     The value to inject.
     */
    public static void inject(final Class clazz, final String fieldName, final Object value) {
        try {
            final Field field = clazz.getDeclaredField(fieldName);
            field.setAccessible(true);
            final Object object = field.get(clazz);
            field.set(object, value);
        } catch (NoSuchFieldException e) {
            fail("Field [" + fieldName + "] does not exist.");
        } catch (IllegalAccessException e) {
            fail("Field [" + fieldName + "] is not accessible.");
        }
    }

    /**
     * Gets the field from the object.
     * @param object    The object to extract the field from.
     * @param fieldName The field to extract.
     */
    public static Object extract(final Object object, final String fieldName) {
        try {
            final Field field = object.getClass().getDeclaredField(fieldName);
            field.setAccessible(true);
            return field.get(object);
        } catch (NoSuchFieldException e) {
            fail("Field [" + fieldName + "] does not exist.");
        } catch (IllegalAccessException e) {
            fail("Field [" + fieldName + "] is not accessible.");
        }
        return null;
    }

    /**
     * Gets the field from the object.
     * @param clazz     The class to extract the field from.
     * @param fieldName The field to extract.
     */
    public static Object extract(final Class clazz, final String fieldName) {
        try {
            final Field field = clazz.getDeclaredField(fieldName);
            field.setAccessible(true);
            return field.get(clazz);
        } catch (NoSuchFieldException e) {
            fail("Field [" + fieldName + "] does not exist.");
        } catch (IllegalAccessException e) {
            fail("Field [" + fieldName + "] is not accessible.");
        }
        return null;
    }
}
