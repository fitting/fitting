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

import org.fitting.documentation.FixtureMethod;
import org.fitting.documentation.FixtureParameter;
import org.fitting.documentation.FixtureParameterDocumentation;
import org.junit.Assert;
import org.junit.Test;

public class FixtureParameterDocumentationTest {

    private static final String DESCRIPTION = "Description";
    private static final String SIGNATURE_PART1 = "Sig Part 1";
    private static final String SIGNATURE_PART2 = "Sig Part 2";

    @Test(expected = IllegalArgumentException.class)
    public void testConstructorClassNull() {
        new FixtureParameterDocumentation(null, null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testConstructorAnnotationNull() {
        new FixtureParameterDocumentation(this.getClass(), null);
    }

    @Test
    public void testFixtureDocumentation() throws NoSuchMethodException {
        FixtureParameter annotation = (FixtureParameter) this.getClass().getMethod("correct", new Class[] {String.class}).getParameterAnnotations()[0][0];
        FixtureParameterDocumentation fixtureParameterDocumentation = new FixtureParameterDocumentation(this.getClass(), annotation);
        Assert.assertEquals("s", fixtureParameterDocumentation.getName());
        Assert.assertEquals(DESCRIPTION, fixtureParameterDocumentation.getDescription());
        Assert.assertEquals(this.getClass(), fixtureParameterDocumentation.getType());
    }

    @FixtureMethod(description = DESCRIPTION, signature = {SIGNATURE_PART1, SIGNATURE_PART2})
    public Object correct(@FixtureParameter(description = DESCRIPTION, name = "s") final String s) { return null; }
}
