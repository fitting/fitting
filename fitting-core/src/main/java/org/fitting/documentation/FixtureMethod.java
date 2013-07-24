package org.fitting.documentation;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Annotation for documenting fixture methods.
 */
@Retention(RUNTIME)
@Target({METHOD})
@Documented
public @interface FixtureMethod {
    /**
     * The signature of the fixture, split between method name segments and variables. The variables are matched to the Java method parameters that are annotated with {@link
     * FixtureParameter}.
     *
     * @return The signature in segments.
     */
    String[] signature() default {""};

    /**
     * The description of the method.
     *
     * @return The description.
     */
    String description() default "";
}
