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

/**
 * Documentation for a fixture method parameter.
 * @see org.fitting.documentation.FixtureMethodDocumentation
 * @see org.fitting.documentation.FixtureParameter
 */
public class FixtureParameterDocumentation {
    /** The parameter type. */
    private final Class<?> parameterType;
    /** The annotation on the parameter. */
    private final FixtureParameter annotation;

    /**
     * Create a new instance.
     * @param parameterType The parameter type.
     * @param annotation    The annotation.
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
     * @return The name.
     */
    public String getName() {
        return annotation.name();
    }

    /**
     * Get the description of the parameter.
     * @return The description.
     */
    public String getDescription() {
        return annotation.description();
    }

    /**
     * Get the type of the parameter.
     * @return The type.
     */
    public Class<?> getType() {
        return parameterType;
    }
}
