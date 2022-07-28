package com.github.jx4e.minecode.api.ui.text;

import com.github.jx4e.minecode.util.math.BoundingBox2D;
import net.minecraft.client.font.TextRenderer;

/**
 * A way to store data about a character and its position in the text window
 * @author Jake (github.com/jx4e)
 * @since 05/07/2022
 **/

public class DrawableCharacter {
    private final char character;
    private final int index, lineNumber, column;
    private BoundingBox2D boundingBox;

    public DrawableCharacter(char character, int index, int lineNumber, int column) {
        this.character = character;
        this.index = index;
        this.lineNumber = lineNumber;
        this.column = column;
    }

    public void setupBoundingBox(TextRenderer textRenderer, double x, double y) {
        double width = textRenderer.getWidth(String.valueOf(character));
        double height = textRenderer.fontHeight;

        this.boundingBox = new BoundingBox2D(x, y, x + width, y + height);
    }

    public char getCharacter() {
        return character;
    }

    public int getLineNumber() {
        return lineNumber;
    }

    public int getColumn() {
        return column;
    }

    public BoundingBox2D getBoundingBox() {
        return boundingBox;
    }
}
