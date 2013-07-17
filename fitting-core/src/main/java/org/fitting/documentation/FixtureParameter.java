package org.fitting.documentation;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Annotation for documenting fixture method parameters
 */
@Retention(RUNTIME)
@Target({PARAMETER})
@Documented
public @interface FixtureParameter {
    /**
     * The name of the parameter.
     * <p>
     * This is to circumvent that problem that the parameter name is not accessible at runtime until Java 8 (http://openjdk.java.net/projects/jdk8/features#118)
     * </p>
     *
     * @return
     */
    String name();

    /**
     * The description of the parameter.
     *
     * @return The description.
     */
    String description() default "";
}
