package com.github.jx4e.minecode.util.render.style;

/**
 * @author Jake (github.com/jx4e)
 * @since 22/06/2022
 **/

public record BoxBorder(float thickness, BoxColorScheme colorScheme) {
    public float getThickness() {
        return thickness;
    }

    public BoxColorScheme getColorScheme() {
        return colorScheme;
    }
}
