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

import org.fitting.FittingException;
import org.fitting.documentation.Fixture;
import org.fitting.documentation.FixtureDocumentation;
import org.fitting.documentation.FixtureDocumentationRenderer;
import org.fitting.documentation.FixtureMethod;
import org.fitting.fixture.FittingFixture;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.Matchers;
import org.mockito.Mockito;

public class FixtureDocumentationTest {

    private static final String DESCRIPTION = "Description";

    @Test(expected = IllegalArgumentException.class)
    public void testConstructorFixtureNull() {
        new FixtureDocumentation(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testConstructorAnnotationNull() {
        Class<? extends FittingFixture> fixtureClass = FittingFixture.class;
        new FixtureDocumentation(fixtureClass);
    }

    @Test
    public void testFixtureDocumentation() {
        Class<? extends FittingFixture> fixtureClass = TestFittingFixture.class;
        FixtureDocumentation fixtureDocumentation = new FixtureDocumentation(fixtureClass);
        Assert.assertEquals(TestFittingFixture.class.getSimpleName(), fixtureDocumentation.getName());
        Assert.assertEquals(TestFittingFixture.class.getPackage().getName(), fixtureDocumentation.getPackage());
        Assert.assertEquals(DESCRIPTION, fixtureDocumentation.getDescription());
        Assert.assertFalse(fixtureDocumentation.getMethods().isEmpty());
    }

    @Test(expected = FittingException.class)
    public void testRenderNull() {
        Class<? extends FittingFixture> fixtureClass = TestFittingFixture.class;
        FixtureDocumentation fixtureDocumentation = new FixtureDocumentation(fixtureClass);
        fixtureDocumentation.render(null);
    }

    @Test
    public void testRender() {
        Class<? extends FittingFixture> fixtureClass = TestFittingFixture.class;
        FixtureDocumentation fixtureDocumentation = new FixtureDocumentation(fixtureClass);
        FixtureDocumentationRenderer renderer = Mockito.mock(FixtureDocumentationRenderer.class);
        fixtureDocumentation.render(renderer);
        Mockito.verify(renderer, Mockito.times(1)).render(Matchers.any(FixtureDocumentation.class));
    }

    @Fixture(description = DESCRIPTION)
    private class TestFittingFixture extends FittingFixture {
        @FixtureMethod(description = DESCRIPTION, signature = {})
        public void dummy() {
        }
    }
}
