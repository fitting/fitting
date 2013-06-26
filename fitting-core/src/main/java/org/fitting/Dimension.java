package org.fitting;

import static java.lang.String.format;

public class Dimension {
    private int width;
    private int height;

    public Dimension() {
        this(0, 0);
    }

    public Dimension(Dimension dimension) {
        this(dimension.width, dimension.height);
    }

    public Dimension(int width, int height) {
        this.width = width;
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public Dimension getSize() {
        return new Dimension(this);
    }

    public void setSize(Dimension dimension) {
        setSize(dimension.width, dimension.height);
    }

    public void setSize(double width, double height) {
        setSize((int) Math.ceil(width), (int) Math.ceil(height));
    }

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
