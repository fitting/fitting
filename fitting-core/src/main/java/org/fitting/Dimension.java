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

import static java.lang.String.format;

/** Basic implementation of the AWT Dimension class to remove all dependencies on AWT and resulting native calls. */
public class Dimension {
    /** The width. */
    private int width;
    /** The height. */
    private int height;

    /** Create a new 0 width, 0 height dimension. */
    public Dimension() {
        this(0, 0);
    }

    /**
     * Create a new dimension with the width and height from the provided dimension.
     * @param dimension The dimension to copy.
     */
    public Dimension(final Dimension dimension) {
        this(dimension.width, dimension.height);
    }

    /**
     * Create a new dimension with a specified height and width.
     * @param width The width.
     * @param height The height.
     */
    public Dimension(final int width, final int height) {
        this.width = width;
        this.height = height;
    }

    /**
     * Get the width.
     * @return The width.
     */
    public int getWidth() {
        return width;
    }

    /**
     * Get the height.
     * @return The height.
     */
    public int getHeight() {
        return height;
    }

    /**
     * Get the size of the dimension.
     * @return A deep-copy of the current dimension.
     */
    public Dimension getSize() {
        return new Dimension(this);
    }

    /**
     * Set the size of the dimension to that of another dimension.
     * @param dimension The dimension to copy the size of.
     */
    public void setSize(final Dimension dimension) {
        setSize(dimension.width, dimension.height);
    }

    /**
     * Set the size of the dimension using Math.ceil for rounding .
     * @param width The width.
     * @param height The height.
     */
    public void setSize(final double width, final double height) {
        setSize((int) Math.ceil(width), (int) Math.ceil(height));
    }

    /**
     * Set the size of the dimension.
     * @param width The width.
     * @param height The height.
     */
    public void setSize(final int width, final int height) {
        this.width = width;
        this.height = height;
    }

    @Override
    public boolean equals(final Object obj) {
        boolean eq = false;
        if (obj != null && obj instanceof Dimension) {
            final Dimension d = (Dimension) obj;
            eq = (width == d.width) && (height == d.height);
        }
        return eq;
    }

    /**
     * {@inheritDoc}
     * Hashing algorithm copied from java.awt.Dimension for compatibility reasons.
     */
    @Override
    public int hashCode() {
        int sum = width + height;
        return sum * (sum + 1) / 2 + width;
    }

    @Override
    public String toString() {
        return format("Dimension [width=%d, height=%d]", width, height);
    }
}
