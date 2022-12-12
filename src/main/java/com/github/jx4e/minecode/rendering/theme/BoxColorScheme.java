package com.github.jx4e.minecode.rendering.theme;

import java.awt.*;

/**
 * @author Jake (github.com/jx4e)
 * @since 22/06/2022
 **/

public class BoxColorScheme {
    private final Color topLeft;
    private final Color topRight;
    private final Color bottomLeft;
    private final Color bottomRight;

    public BoxColorScheme(Color topLeft, Color topRight, Color bottomLeft, Color bottomRight) {
        this.topLeft = topLeft;
        this.topRight = topRight;
        this.bottomLeft = bottomLeft;
        this.bottomRight = bottomRight;
    }

    public Color getTopLeft() {
        return topLeft;
    }

    public Color getTopRight() {
        return topRight;
    }

    public Color getBottomLeft() {
        return bottomLeft;
    }

    public Color getBottomRight() {
        return bottomRight;
    }

    public static class Flat extends BoxColorScheme {
        public Flat(Color color) {
            super(color, color, color, color);
        }

        public Color getColor() {
            return super.getTopLeft();
        }
    }

    public static class Gradient extends BoxColorScheme {
        private final boolean horizontal;

        public Gradient(Color start, Color end, boolean horizontal) {
            super(start, horizontal ? end : start, horizontal ? start : end, end);
            this.horizontal = horizontal;
        }

        public boolean isHorizontal() {
            return horizontal;
        }

        public Color getStart() {
            return super.getTopLeft();
        }

        public Color getEnd() {
            return super.getTopLeft();
        }
    }
}
