package org.fitting.documentation;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Annotation for marking fixtures.
 */
@Retention(RUNTIME)
@Target({TYPE})
@Documented
public @interface Fixture {
    /**
     * The global description of the fixture.
     * @return The global description.
     */
    String description() default "";
}
