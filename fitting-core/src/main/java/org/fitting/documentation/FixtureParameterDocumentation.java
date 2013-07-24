package org.fitting.documentation;

/**
 * Documentation for a fixture method parameter.
 *
 * @see FixtureMethodDocumentation
 * @see FixtureParameter
 */
public class FixtureParameterDocumentation {
    /**
     * The parameter type.
     */
    private Class<?> parameterType;
    /**
     * The annotation on the parameter.
     */
    private FixtureParameter annotation;

    /**
     * Create a new instance.
     *
     * @param parameterType The parameter type.
     * @param annotation    The annotation.
     *
     * @throws IllegalArgumentException When invalid information was provided and the documentation could not be created.
     */
    public FixtureParameterDocumentation(Class<?> parameterType, FixtureParameter annotation) throws IllegalArgumentException {
        this.parameterType = parameterType;
        if (parameterType == null) {
            throw new IllegalArgumentException("Cannot create parameter documentation with a null type.");
        }

        this.annotation = annotation;
        if (annotation == null) {
            throw new IllegalArgumentException("No @FixtureParameter annotation provided for fixture parameter documentation");
        }
    }

    /**
     * Get the name.
     *
     * @return The name.
     */
    public String getName() {
        return annotation.name();
    }

    /**
     * Get the description of the parameter.
     *
     * @return The description.
     */
    public String getDescription() {
        return annotation.description();
    }

    /**
     * Get the type of the parameter.
     *
     * @return The type.
     */
    public Class<?> getType() {
        return parameterType;
    }
}
