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

package org.fitting;

import org.fitting.Dimension;
import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotSame;

/**
 * Unit tests for {@link Dimension}
 *
 * @since 1.0
 */
public class DimensionTest {
    /** Default width to test with. */
    private static final int WIDTH = 10;
    /** Default height to test with. */
    private static final int HEIGHT = 15;

    /**
     * Check if the default dimension size of 0 is set with a no-argument constructor.
     *
     * @see Dimension#Dimension()
     */
    @Test
    public void shouldSetDefaultZeroSizeDimension() {
        Dimension dim = new Dimension();

        assertEquals(0, dim.getWidth());
        assertEquals(0, dim.getHeight());
    }

    /**
     * Check if the size of a provided dimension is copied at creation.
     *
     * @see Dimension#Dimension(Dimension)
     */
    @Test
    public void shouldCopyDimensionValuesOnCreation() {
        Dimension original = new Dimension(WIDTH, HEIGHT);
        Dimension dim = new Dimension(original);

        assertEquals(original.getWidth(), dim.getWidth());
        assertEquals(original.getHeight(), dim.getHeight());
        assertNotSame(original, dim);
    }

    /**
     * Check if the provided size is set at creation.
     *
     * @see Dimension#Dimension(Dimension)
     */
    @Test
    public void shouldSetSizeValuesOnCreation() {
        Dimension dim = new Dimension(WIDTH, HEIGHT);

        assertEquals(WIDTH, dim.getWidth());
        assertEquals(HEIGHT, dim.getHeight());
    }

    /**
     * Check if the returned Dimension for a size is a deep copy.
     *
     * @see Dimension#getSize()
     */
    @Test
    public void shouldMakeDeepCopyForSize() {
        Dimension dim = new Dimension(WIDTH, HEIGHT);
        Dimension sizeDim = dim.getSize();

        assertNotSame(dim, sizeDim);
        assertEquals(dim, sizeDim);
    }

    /**
     * Check if the values from a dimension are copied when setting the size.
     *
     * @see Dimension#setSize(Dimension)
     */
    @Test
    public void shouldCopySizeFromDimension() {
        Dimension original = new Dimension(WIDTH, HEIGHT);
        Dimension dim = new Dimension();
        assertNotEquals(original, dim);
        assertNotSame(original, dim);

        dim.setSize(original);
        assertEquals(original, dim);
        assertNotSame(original, dim);
    }

    @Test
    public void shouldCopySizeFromDoubles() {
        Dimension dimension = new Dimension();
        dimension.setSize(3.0, 5.0);

        Assert.assertEquals(3, dimension.getWidth());
        Assert.assertEquals(5, dimension.getHeight());

        dimension.setSize(3.1, 5.1);

        Assert.assertEquals(4, dimension.getWidth());
        Assert.assertEquals(6, dimension.getHeight());
    }

    @Test
    public void testEquals() {
        Dimension dimension1 = new Dimension(1, 2);
        Dimension dimension2 = new Dimension(dimension1);
        Dimension dimension3 = new Dimension(1, 4);
        Dimension dimension4 = new Dimension(3, 4);
        Assert.assertEquals(dimension1, dimension2);
        Assert.assertNotEquals(dimension1, dimension3);
        Assert.assertNotEquals(dimension2, dimension3);
        Assert.assertNotEquals(dimension1, dimension4);
        Assert.assertNotEquals(dimension3, dimension4);
        Assert.assertNotEquals(dimension1, new Object());
        Assert.assertNotEquals(dimension1, null);
    }

    @Test
    public void testHashCode() {
        Dimension dimension1 = new Dimension(1, 2);
        Dimension dimension2 = new Dimension(dimension1);
        Dimension dimension3 = new Dimension(1, 4);
        Dimension dimension4 = new Dimension(3, 4);
        Assert.assertEquals(dimension1.hashCode(), dimension2.hashCode());
        Assert.assertNotEquals(dimension1.hashCode(), dimension3.hashCode());
        Assert.assertNotEquals(dimension2.hashCode(), dimension3.hashCode());
        Assert.assertNotEquals(dimension1.hashCode(), dimension4.hashCode());
        Assert.assertNotEquals(dimension3.hashCode(), dimension4.hashCode());
    }

    @Test
    public void testToString() {
        String toString = new Dimension(123, 456).toString();
        Assert.assertTrue(toString.contains("123"));
        Assert.assertTrue(toString.contains("456"));
    }

}

