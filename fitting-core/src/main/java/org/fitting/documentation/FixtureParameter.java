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