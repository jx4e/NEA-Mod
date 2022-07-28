package com.github.jx4e.minecode.util.math;

/**
 * @author Jake (github.com/jx4e)
 * @since 29/06/2022
 **/

public class BoundingBox2D {
    private final double minX, maxX, minY, maxY;

    public BoundingBox2D(double x1, double y1, double x2, double y2) {
        if (x1 <= x2) {
            minX = x1;
            maxX = x2;
        } else {
            maxX = x1;
            minX = x2;
        }

        if (y1 <= y2) {
            minY = y1;
            maxY = y2;
        } else {
            maxY = y1;
            minY = y2;
        }
    }

    public double getWidth() {
        return maxX - minX;
    }

    public double getHeight() {
        return maxY - minY;
    }

    public double getMinX() {
        return minX;
    }

    public double getMaxX() {
        return maxX;
    }

    public double getMinY() {
        return minY;
    }

    public double getMaxY() {
        return maxY;
    }
}
