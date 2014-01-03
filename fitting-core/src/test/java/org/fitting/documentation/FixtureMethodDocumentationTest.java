package org.fitting.documentation;

import javax.jws.WebParam;

import org.junit.Assert;
import org.junit.Test;

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
        new FixtureMethodDocumentation(this.getClass().getMethod("incorrect", new Class[] {String.class}));
    }

    @Test
    public void testFixtureDocumentation() throws NoSuchMethodException {
        FixtureMethodDocumentation fixtureMethodDocumentation = new FixtureMethodDocumentation(this.getClass().getMethod("correct", new Class[] {String.class}));
        Assert.assertEquals("correct", fixtureMethodDocumentation.getName());
        Assert.assertEquals(DESCRIPTION, fixtureMethodDocumentation.getDescription());
        Assert.assertArrayEquals(new String[] {SIGNATURE_PART1, SIGNATURE_PART2}, fixtureMethodDocumentation.getSignature());
        Assert.assertEquals(1, fixtureMethodDocumentation.getParameters().size());
        Assert.assertEquals(Object.class, fixtureMethodDocumentation.getReturnType());
    }

    @FixtureMethod(description = DESCRIPTION, signature = {SIGNATURE_PART1, SIGNATURE_PART2})
    public Object correct(@WebParam @FixtureParameter(description = DESCRIPTION, name = "s") final String s) { return null; }

    @FixtureMethod(description = DESCRIPTION, signature = {SIGNATURE_PART1, SIGNATURE_PART2})
    public Object incorrect(final String s) { return null; }
}
