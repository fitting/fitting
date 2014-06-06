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

/** Basic implementation of the AWT Point class to remove all dependencies on AWT and resulting native calls. */
public class Point {
    /** The X coordinate. */
    public int x;
    /** The Y coordinate. */
    public int y;

    /** Create a new Point at 0,0. */
    public Point() {
        this(0, 0);
    }

    /**
     * Create a new Point with the coordinates of the given point.
     * @param p The point to copy.
     */
    public Point(final Point p) {
        this(p.x, p.y);
    }

    /**
     * Create a new Point.
     * @param x The X-coordinate.
     * @param y The Y-coordinate.
     */
    public Point(final int x, final int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Get the X-coordinate.
     * @return The X-coordinate.
     */
    public double getX() {
        return x;
    }

    /**
     * Get the Y-coordinate.
     * @return The Y-coordinate.
     */
    public double getY() {
        return y;
    }

    /**
     * Get location as a deep-copy of the current point.
     * @return The new location.
     */
    public Point getLocation() {
        return new Point(x, y);
    }

    /**
     * Set the location based on the values of the given point.
     * @param p The point to copy.
     */
    public void setLocation(final Point p) {
        setLocation(p.x, p.y);
    }

    /**
     * Set the location.
     * @param x The X-coordinate.
     * @param y The Y-coordinate.
     */
    public void setLocation(final int x, final int y) {
        move(x, y);
    }

    /**
     * Set the location.
     * @param x The X-coordinate.
     * @param y The Y-coordinate.
     */
    public void setLocation(final double x, final double y) {
        this.x = (int) Math.floor(x + 0.5);
        this.y = (int) Math.floor(y + 0.5);
    }

    /**
     * Move the point to the given location.
     * @param x The new X-coordinate.
     * @param y The new Y-coordinate.
     */
    public void move(final int x, final int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Move the point by the provided distance.
     * Negative units can be provided for reversed movement.
     * @param dx The units to move the X-coordinate.
     * @param dy The units to move the Y-coordinate.
     */
    public void translate(final int dx, final int dy) {
        this.x += dx;
        this.y += dy;
    }

    @Override
    public boolean equals(final Object obj) {
        if (obj instanceof Point) {
            Point pt = (Point) obj;
            return (x == pt.x) && (y == pt.y);
        }
        return super.equals(obj);
    }

    @Override
    public String toString() {
        return getClass().getName() + "[x=" + x + ",y=" + y + "]";
    }

    /**
     * {@inheritDoc}
     * Algorithm was copied from the java.awt.Point for compatibility reasons.
     */
    @Override
    public int hashCode() {
        long bits = Double.doubleToLongBits(getX());
        bits ^= Double.doubleToLongBits(getY()) * 31;
        return (((int) bits) ^ ((int) (bits >> 32)));
    }
}
