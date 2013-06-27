package org.fitting;

/**
 * Basic implementation of the AWT Point class to remove all dependencies on AWT and resulting native calls.
 */
public class Point {
    /**
     * The X coordinate.
     */
    public int x;
    /**
     * The Y coordinate.
     */
    public int y;

    /**
     * Create a new Point at 0,0.
     */
    public Point() {
        this(0, 0);
    }

    /**
     * Create a new Point with the coordinates of the given point.
     *
     * @param p The point to copy.
     */
    public Point(Point p) {
        this(p.x, p.y);
    }

    /**
     * Create a new Point.
     *
     * @param x The X-coordinate.
     * @param y The Y-coordinate.
     */
    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Get the X-coordinate.
     *
     * @return The X-coordinate.
     */
    public double getX() {
        return x;
    }

    /**
     * Get the Y-coordinate.
     *
     * @return The Y-coordinate.
     */
    public double getY() {
        return y;
    }

    /**
     * Get location as a deep-copy of the current point.
     *
     * @return The new location.
     */
    public Point getLocation() {
        return new Point(x, y);
    }

    /**
     * Set the location based on the values of the given point.
     *
     * @param p The point to copy.
     */
    public void setLocation(Point p) {
        setLocation(p.x, p.y);
    }

    /**
     * Set the location.
     *
     * @param x The X-coordinate.
     * @param y The Y-coordinate.
     */
    public void setLocation(int x, int y) {
        move(x, y);
    }

    /**
     * Set the location.
     *
     * @param x The X-coordinate.
     * @param y The Y-coordinate.
     */
    public void setLocation(double x, double y) {
        this.x = (int) Math.floor(x + 0.5);
        this.y = (int) Math.floor(y + 0.5);
    }

    /**
     * Move the point to the given location.
     *
     * @param x The new X-coordinate.
     * @param y The new Y-coordinate.
     */
    public void move(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Move the point by the provided distance.
     * <p>
     * Negative units can be provided for reversed movement.
     * </p>
     *
     * @param dx The units to move the X-coordinate.
     * @param dy The units to move the Y-coordinate.
     */
    public void translate(int dx, int dy) {
        this.x += dx;
        this.y += dy;
    }

    @Override
    public boolean equals(Object obj) {
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
        long bits = java.lang.Double.doubleToLongBits(getX());
        bits ^= java.lang.Double.doubleToLongBits(getY()) * 31;
        return (((int) bits) ^ ((int) (bits >> 32)));
    }
}
