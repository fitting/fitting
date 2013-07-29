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
