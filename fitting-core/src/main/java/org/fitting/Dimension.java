package org.fitting;

import static java.lang.String.format;

/**
 * Basic implementation of the AWT Dimension class to remove all dependencies on AWT and resulting native calls.
 */
public class Dimension {
    /**
     * The width.
     */
    private int width;
    /**
     * The height.
     */
    private int height;

    /**
     * Create a new 0 width, 0 height dimension.
     */
    public Dimension() {
        this(0, 0);
    }

    /**
     * Create a new dimension with the width and height from the provided dimension.
     *
     * @param dimension The dimension to copy.
     */
    public Dimension(Dimension dimension) {
        this(dimension.width, dimension.height);
    }

    /**
     * Create a new dimension with a specified height and width.
     *
     * @param width  The width.
     * @param height The height.
     */
    public Dimension(int width, int height) {
        this.width = width;
        this.height = height;
    }

    /**
     * Get the width.
     *
     * @return The width.
     */
    public int getWidth() {
        return width;
    }

    /**
     * Get the height.
     *
     * @return The height.
     */
    public int getHeight() {
        return height;
    }

    /**
     * Get the size of the dimension.
     *
     * @return A deep-copy of the current dimension.
     */
    public Dimension getSize() {
        return new Dimension(this);
    }

    /**
     * Set the size of the dimension to that of another dimension.
     *
     * @param dimension The dimension to copy the size of.
     */
    public void setSize(Dimension dimension) {
        setSize(dimension.width, dimension.height);
    }

    /**
     * Set the size of the dimension using Math.ceil for rounding .
     *
     * @param width  The width.
     * @param height The height.
     */
    public void setSize(double width, double height) {
        setSize((int) Math.ceil(width), (int) Math.ceil(height));
    }

    /**
     * Set the size of the dimension.
     *
     * @param width  The width.
     * @param height The height.
     */
    public void setSize(int width, int height) {
        this.width = width;
        this.height = height;
    }

    @Override
    public boolean equals(Object obj) {
        boolean eq = false;
        if (obj != null && obj instanceof Dimension) {
            Dimension d = (Dimension) obj;
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