/*
 * Licensed to the Fitting Project under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The Fitting Project licenses
 * this file to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package org.fitting.documentation;

import org.fitting.fixture.FittingFixture;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Documentation for a fixture.
 * @see org.fitting.documentation.Fixture
 */
public class FixtureDocumentation {
    /** The class of the fixture. */
    private final Class<? extends FittingFixture> fixture;
    /** The annotation on the fixture. */
    private final Fixture annotation;

    /** The methods. */
    private final List<FixtureMethodDocumentation> methods;

    /**
     * Create a new instance from a fixture.
     * @param fixture The class of the fixture to create the documentation for.
     * @throws IllegalArgumentException When the fixture does not contain any documentation.
     */
    public FixtureDocumentation(final Class<? extends FittingFixture> fixture) throws IllegalArgumentException {
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
     * @return The parsed methods.
     */
    private List<FixtureMethodDocumentation> parseMethods() {
        final List<FixtureMethodDocumentation> methods = new ArrayList<FixtureMethodDocumentation>();
        for (final Method m : fixture.getMethods()) {
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
     * @return The name.
     */
    public String getName() {
        return fixture.getSimpleName();
    }

    /**
     * Get the name of the package the fixture is declared in.
     * @return The package.
     */
    public String getPackage() {
        return fixture.getPackage().getName();
    }

    /**
     * Get the description of the fixture.
     * @return The description.
     */
    public String getDescription() {
        return annotation.description();
    }

    /**
     * Get the documented methods of this fixture as an unmodifiable list.
     * @return The methods.
     */
    public List<FixtureMethodDocumentation> getMethods() {
        return Collections.unmodifiableList(methods);
    }

    /**
     * Render the documentation using the given renderer.
     * @param renderer The renderer.
     * @return The rendered documentation.
     * @throws FittingRenderingException When rendering failed.
     */
    public String render(final FixtureDocumentationRenderer renderer) throws FittingRenderingException {
        if (renderer == null) {
            throw new FittingRenderingException("Cannot render documentation for fixture " + fixture.getName() + " with a null renderer");
        }
        return renderer.render(this);
    }
}
