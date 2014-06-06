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
import org.fitting.documentation.FixtureMethodDocumentation;
import org.fitting.documentation.FixtureParameter;
import org.junit.Assert;
import org.junit.Test;

import javax.jws.WebParam;

public class FixtureMethodDocumentationTest {

    private static final String DESCRIPTION = "Description";
    private static final String SIGNATURE_PART1 = "Sig Part 1";
    private static final String SIGNATURE_PART2 = "Sig Part 2";

    @Test(expected = IllegalArgumentException.class)
    public void testConstructorFixtureNull() {
        new FixtureMethodDocumentation(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testConstructorAnnotationNull() throws NoSuchMethodException {
        new FixtureMethodDocumentation(this.getClass().getMethod("testConstructorAnnotationNull"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testFixtureDocumentationIncorrect() throws NoSuchMethodException {
        new FixtureMethodDocumentation(this.getClass().getMethod("incorrect", new Class[]{String.class}));
    }

    @Test
    public void testFixtureDocumentation() throws NoSuchMethodException {
        FixtureMethodDocumentation fixtureMethodDocumentation = new FixtureMethodDocumentation(this.getClass().getMethod("correct", new Class[]{String.class}));
        Assert.assertEquals("correct", fixtureMethodDocumentation.getName());
        Assert.assertEquals(DESCRIPTION, fixtureMethodDocumentation.getDescription());
        Assert.assertArrayEquals(new String[]{SIGNATURE_PART1, SIGNATURE_PART2}, fixtureMethodDocumentation.getSignature());
        Assert.assertEquals(1, fixtureMethodDocumentation.getParameters().size());
        Assert.assertEquals(Object.class, fixtureMethodDocumentation.getReturnType());
    }

    @FixtureMethod(description = DESCRIPTION, signature = {SIGNATURE_PART1, SIGNATURE_PART2})
    public Object correct(@WebParam @FixtureParameter(description = DESCRIPTION, name = "s") final String s) {
        return null;
    }

    @FixtureMethod(description = DESCRIPTION, signature = {SIGNATURE_PART1, SIGNATURE_PART2})
    public Object incorrect(final String s) {
        return null;
    }
}
