package org.fitting.documentation;

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
