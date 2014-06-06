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

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Documentation for a fixture method.
 * @see org.fitting.documentation.FixtureDocumentation
 * @see org.fitting.documentation.FixtureMethod
 */
public class FixtureMethodDocumentation {
    /** The method. */
    private final Method method;
    /** The annotation on the method. */
    private final FixtureMethod annotation;

    /** The documentation for the parameters of the method. */
    private List<FixtureParameterDocumentation> parameters;

    /**
     * Create a new instance from a method.
     * @param method The method to create the documentation for.
     * @throws IllegalArgumentException When invalid information was provided and the documentation could not be created.
     */
    public FixtureMethodDocumentation(final Method method) throws IllegalArgumentException {
        this.method = method;
        if (method == null) {
            throw new IllegalArgumentException("Cannot create documentation for a null method.");
        }

        annotation = method.getAnnotation(FixtureMethod.class);
        if (annotation == null) {
            throw new IllegalArgumentException("No @FixtureMethod annotation present on method.");
        }

        parameters = new ArrayList<FixtureParameterDocumentation>();
        parameters.addAll(parseParameters());
    }

    /**
     * Parse the method parameters.
     * @return The documentation for the parameters.
     * @throws IllegalArgumentException When one of the parameters couldn't be parsed.
     */
    private List<FixtureParameterDocumentation> parseParameters() throws IllegalArgumentException {
        final List<FixtureParameterDocumentation> parameters = new ArrayList<FixtureParameterDocumentation>();
        for (int i = 0; i < method.getParameterTypes().length; i++) {
            // This might throw an IllegalArgumentException which will propagate through.
            parameters.add(new FixtureParameterDocumentation(method.getParameterTypes()[i], getParameterAnnotation(i)));
        }
        return parameters;
    }

    /**
     * Get the {@link org.fitting.documentation.FixtureParameter} annotation for a method parameter.
     * @param parameterIndex The index of the parameter.
     * @return The {@link org.fitting.documentation.FixtureParameter} or <code>null</code> if none was present.
     */
    private FixtureParameter getParameterAnnotation(final int parameterIndex) {
        FixtureParameter parameter = null;
        for (final Annotation a : method.getParameterAnnotations()[parameterIndex]) {
            if (a instanceof FixtureParameter) {
                parameter = (FixtureParameter) a;
                break;
            }
        }
        return parameter;
    }

    /**
     * Get the return type for the method.
     * @return The return type.
     */
    public Class<?> getReturnType() {
        return method.getReturnType();
    }

    /**
     * Get the Java name of the method.
     * @return The name of the method.
     */
    public String getName() {
        return method.getName();
    }

    /**
     * Get the description of the method.
     * @return The description.
     */
    public String getDescription() {
        return annotation.description();
    }

    /**
     * Get the signature.
     * @return The signature in segments.
     */
    public String[] getSignature() {
        return annotation.signature();
    }

    /**
     * Get the parsed parameters as an unmodifiable (ordered) list.
     * @return The parameters.
     */
    public List<FixtureParameterDocumentation> getParameters() {
        return Collections.unmodifiableList(parameters);
    }
}
