package org.fitting.documentation;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.fitting.fixture.FittingFixture;

/**
 * Documentation for a fixture.
 *
 * @see Fixture
 */
public class FixtureDocumentation {
    /**
     * The class of the fixture.
     */
    private Class<? extends FittingFixture> fixture;
    /**
     * The annotation on the fixture.
     */
    private Fixture annotation;

    /**
     * The methods.
     */
    private List<FixtureMethodDocumentation> methods;

    /**
     * Create a new instance from a fixture.
     *
     * @param fixture The class of the fixture to create the documentation for.
     *
     * @throws IllegalArgumentException When the fixture does not contain any documentation.
     */
    public FixtureDocumentation(Class<? extends FittingFixture> fixture) throws IllegalArgumentException {
        this.fixture = fixture;
        if (fixture == null) {
            throw new IllegalArgumentException("Cannot create documentation for a null class.");
        }

        annotation = fixture.getAnnotation(Fixture.class);
        if (annotation == null) {
            throw new IllegalArgumentException("No @Fixture annotation present on class.");
        }

        methods = new ArrayList<FixtureMethodDocumentation>();
        methods.addAll(parseMethods());
    }

    /**
     * Parse all the methods of the fixture to {@link FixtureMethodDocumentation} instances.
     *
     * @return The parsed methods.
     */
    private List<FixtureMethodDocumentation> parseMethods() {
        List<FixtureMethodDocumentation> methods = new ArrayList<FixtureMethodDocumentation>();
        for (Method m : fixture.getMethods()) {
            try {
                methods.add(new FixtureMethodDocumentation(m));
            } catch (IllegalArgumentException e) {
                // Ignore.
            }
        }
        return methods;
    }

    /**
     * Get the Java name of the fixture (without package).
     *
     * @return The name.
     */
    public String getName() {
        return fixture.getSimpleName();
    }

    /**
     * Get the name of the package the fixture is declared in.
     *
     * @return The package.
     */
    public String getPackage() {
        return fixture.getPackage().getName();
    }

    /**
     * Get the description of the fixture.
     *
     * @return The description.
     */
    public String getDescription() {
        return annotation.description();
    }

    /**
     * Get the documented methods of this fixture as an unmodifiable list.
     *
     * @return The methods.
     */
    public List<FixtureMethodDocumentation> getMethods() {
        return Collections.unmodifiableList(methods);
    }

    /**
     * Render the documentation using the given renderer.
     *
     * @param renderer The renderer.
     *
     * @return The rendered documentation.
     *
     * @throws FittingRenderingException When rendering failed.
     */
    public String render(FixtureDocumentationRenderer renderer) throws FittingRenderingException {
        if (renderer == null) {
            throw new FittingRenderingException("Cannot render documentation for fixture " + fixture.getName() + " with a null renderer");
        }
        return renderer.render(this);
    }
}
