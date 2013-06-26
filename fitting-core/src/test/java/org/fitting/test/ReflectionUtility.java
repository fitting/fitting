package org.fitting.test;

import java.lang.annotation.Annotation;
import java.lang.reflect.*;

import static java.lang.String.format;
import static org.apache.commons.lang.StringUtils.isEmpty;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.fail;

/** Reflection and dependency injection utility. */
public final class ReflectionUtility {

    /** Constructor. */
    private ReflectionUtility() {
    }

    /**
     * Create a new instance for a singleton class and set the new instance to the singleton field.
     * This method can handle private constructors and static final singleton methods. Creation of the new instance and setting of the field is double checked.
     * @param clazz                The class to update the instance for.
     * @param singletonFieldName   The name of the singleton field.
     * @param constructorArguments The constructor arguments (optional).
     * @param <K>                  The type of the singleton.
     * @return The new singleton instance.
     */
    public static <K> K createNewInstanceAndUpdateSingleton(final Class<K> clazz, final String singletonFieldName, final Object... constructorArguments) {
        K newInstance = null;
        // Get the old instance to ensure at the end that a new instance was created.
        try {
            // Get the singleton field and make it accessible for read/write.
            final Field singletonField = clazz.getDeclaredField(singletonFieldName);
            singletonField.setAccessible(true);
            final Field modifiersField = Field.class.getDeclaredField("modifiers");
            modifiersField.setAccessible(true);
            modifiersField.setInt(singletonField, singletonField.getModifiers() & ~Modifier.FINAL);

            // Store the old instance for ensuring that a new instance of the singleton class is created.
            final K oldInstance = (K) singletonField.get(null);

            // Create a new instance and set it to the singleton field.
            final Constructor constructor = clazz.getDeclaredConstructor();
            constructor.setAccessible(true);
            newInstance = (K) constructor.newInstance();
            singletonField.set(null, newInstance);

            // Ensure that a new instance is created and set.
            final Field newSingletonField = clazz.getDeclaredField(singletonFieldName);
            newSingletonField.setAccessible(true);

            assertNotSame(oldInstance, newInstance);
            assertNotSame(oldInstance, newSingletonField.get(null));
            assertSame(newInstance, newSingletonField.get(null));
        } catch (IllegalAccessException e) {
            fail(format("Unable to access the singleton field [%s] and/or the constructor of class [%s].", singletonFieldName, clazz.getName()));
        } catch (NoSuchFieldException e) {
            fail(format("Singleton field [%s] not found on class [%s].", singletonFieldName, clazz.getName()));
        } catch (NoSuchMethodException e) {
            fail(format("Singleton field [%s] not found on class [%s].", singletonFieldName, clazz.getName()));
        } catch (InstantiationException e) {
            fail(format("Can't instantiate a new instance of [%s].", clazz.getName()));
        } catch (InvocationTargetException e) {
            fail(format("Error calling constructor of [%s], with exception [%s].", clazz.getName(), e.getTargetException().getMessage()));
        }
        return newInstance;
    }

    /**
     * Get a method from a class, failing a unit test if the method was not found or if the extraction failed.
     * @param clazz      The class.
     * @param method     The name of the method.
     * @param parameters The method parameter types.
     * @return The method.
     */
    public static Method getMethod(final Class<?> clazz, final String method, final Class<?>... parameters) {
        try {
            return clazz.getDeclaredMethod(method, parameters);
        } catch (NoSuchMethodException e) {
            fail("Method [" + method + "] with parameters [" + parameters + "] not found on class [" + clazz.getName() + "]");
        } catch (NullPointerException e) {
            fail("Invalid parameters given for method extraction, using method [" + method + "], parameters [" + parameters + "] and class [" + clazz + "]");
        }
        return null;
    }

    /**
     * Get a specific annotation from a method parameter. Fails the unit tests if the annotation could not be retrieved.
     * @param method          The method to get the parameter from.
     * @param index           The index of the parameter on the method.
     * @param annotationClass The class of the annotation.
     * @param <K>             The annotation.
     * @return The annotation instance.
     */
    public static <K> K getParameterAnnotation(final Method method, final int index, final Class<K> annotationClass) {
        try {
            K annotation = null;
            for (final Annotation a : method.getParameterAnnotations()[index]) {
                if (annotationClass.equals(a.annotationType())) {
                    annotation = (K) a;
                    break;
                }
            }
            if (annotation == null) {
                fail("Annotation [" + annotationClass.getName() + "] not found on parameter [" + index + "] on method [" + method + "]");
            }
            return annotation;
        } catch (NullPointerException e) {
            fail("Unable to extract the annotation [" + annotationClass.getName() + "] from parameter [" + index + "] on method [" + method + "]");
        } catch (IndexOutOfBoundsException e) {
            fail("Parameter index [" + index + "] on method [" + method + "] is not a valid parameter, while trying to get the annotation [" + annotationClass.getName() + "]");
        }
        return null;
    }

    /**
     * Get the parameter type for a method parameter. Fails the unit tests if the parameter type could not be retrieved.
     * @param method The method.
     * @param index  The index of the parameter on the method.
     * @return The parameter type.
     */
    public static Class<?> getParameterType(final Method method, final int index) {
        try {
            return method.getParameterTypes()[index];
        } catch (NullPointerException e) {
            fail("Invalid parameters given for parameter type extraction for parameter [" + index + " on method [" + method + "]");
        } catch (IndexOutOfBoundsException e) {
            fail("Parameter index [" + index + "] on method [" + method + "] is not a valid parameter, while trying to get the parameter type.");
        }
        return null;

    }

    /**
     * Injects the given value on the given object for the given field.
     * @param object    The object to inject the field value on.
     * @param fieldName The field to inject the value for.
     * @param value     The value to inject.
     */
    public static void inject(final Object object, final String fieldName, final Object value) {
        try {
            final Field field = getField(object.getClass(), fieldName);
            field.setAccessible(true);
            field.set(object, value);
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
            final Field field = getField(clazz, fieldName);
            field.setAccessible(true);
            final Object object = field.get(clazz);
            field.set(object, value);
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
            final Field field = getField(object.getClass(), fieldName);
            field.setAccessible(true);
            return field.get(object);
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
            final Field field = getField(clazz, fieldName);
            field.setAccessible(true);
            return field.get(clazz);
        } catch (IllegalAccessException e) {
            fail("Field [" + fieldName + "] is not accessible.");
        }
        return null;
    }

    /**
     * Gets the field with the given name from the given class or its super class.
     * If the field is not found on the class or any of its super classes, the result is failure.
     * @param clazz     The class to find the field on.
     * @param fieldName The field name.
     * @return field The field.
     */
    private static Field getField(final Class clazz, final String fieldName) {
        try {
            return clazz.getDeclaredField(fieldName);
        } catch (NoSuchFieldException e) {
            if (clazz != Object.class) {
                return getField(clazz.getSuperclass(), fieldName);
            }
            fail("Field [" + fieldName + "] does not exist.");
        }
        return null;
    }


    /**
     * Calls the private default constructor.
     * @param clazz The class to construct.
     * @return instance The instance.
     */
    public static Object callDefaultPrivateConstructor(final Class clazz) {
        try {
            final Constructor constructor = clazz.getDeclaredConstructor();
            constructor.setAccessible(true);
            return constructor.newInstance();
        } catch (NoSuchMethodException e) {
            fail("No default constructor found.");
        } catch (InvocationTargetException e) {
            fail("Could not invoke constructor.");
        } catch (InstantiationException e) {
            fail("Could not initialize constructor.");
        } catch (IllegalAccessException e) {
            fail("Could not access constructor.");
        }
        return null;
    }

    /**
     * Calls the private method.
     * @param object         The object to call the method on.
     * @param name           The name of the method.
     * @param parameterTypes The parameter types.
     * @param objects        The parameters.
     * @return
     */
    public static <T> T callPrivateMethod(final Object object, final String name, final Class[] parameterTypes, final Object[] objects, final Class<T> returnType) throws Throwable {
        try {
            final Method method = object.getClass().getDeclaredMethod(name, parameterTypes);
            method.setAccessible(true);
            if (!method.getReturnType().equals(returnType)) {
                fail("Expected return type [" + returnType.getName() + "] but found returnType [" + method.getReturnType().getName() + "] for method [" + name + "]");
            }
            return (T) method.invoke(object, objects);
        } catch (NoSuchMethodException e) {
            fail("Method [" + name + "] does not exist.");
        } catch (InvocationTargetException e) {
            if (e.getCause() != null) {
                // If there is a cause exception, let the original method exception propagate.
                throw e.getCause();
            } else {
                fail("Method [" + name + "] could not be invoked: " + getExceptionMessage(e));
            }
        } catch (IllegalAccessException e) {
            fail("Method [" + name + "] is not accessible.");
        }
        return null;
    }

    /**
     * Get the exception message for the given exception, retrieving it from the cause exception whn needed.
     * @param throwable The exception to get the message for.
     * @return The message or <code>null</code> if no message could be retrieved.
     */
    private static String getExceptionMessage(final Throwable throwable) {
        String message = null;
        if (throwable != null) {
            if (!isEmpty(throwable.getMessage())) {
                message = throwable.getMessage();
            } else if (throwable.getCause() != null) {
                message = getExceptionMessage(throwable.getCause());
            }
        }
        return message;
    }
}
