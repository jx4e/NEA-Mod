package com.github.jx4e.minecode.api.ui.text;

import com.github.jx4e.minecode.api.ui.AbstractPane;
import com.github.jx4e.minecode.impl.manager.RenderManager;
import com.github.jx4e.minecode.util.misc.Writer;
import com.github.jx4e.minecode.util.render.style.BoxColorScheme;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.MathHelper;
import org.jetbrains.annotations.Nullable;
import org.lwjgl.glfw.GLFW;

import java.awt.*;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Text pane that is made from a grid of characters. Will draw text on the screen
 * @author Jake (github.com/jx4e)
 * @since 04/07/2022
 **/

public class TextPane extends AbstractPane {
    /**
     * List of characters
     */
    private List<DrawableCharacter> drawables = new LinkedList<>();

    /**
     * Content of each line
     */
    private List<Writer> lines;

    /**
     * The cursor
     */
    private TextCursor cursor;

    public TextPane(int x, int y, int width, int height, BoxColorScheme colorScheme) {
        super(x, y, width, height, colorScheme);

        String text = "Hello World\nNew\nLines\nAre\nCool";

        lines = text.lines().map(Writer::new).collect(Collectors.toList());
        cursor = new TextCursor();
    }

    /**
     * Draw the text grid
     * @param matrices
     * @param mouseX
     * @param mouseY
     */
    @Override
    public void draw(MatrixStack matrices, int mouseX, int mouseY) {
        boolean drawLineNumbers = true;
        int marginWidth = 5;

        /*
         * Text
         */
        matrices.push();

        // Clear the list
        drawables.clear();

        // Add the characters
        int index = 0;
        int lineNumber = -1;

        // Iterate through the lines
        for (Writer line : lines) {
            lineNumber++;
            int column = 0;

            for (char c : line.getText().toCharArray()) {
                DrawableCharacter drawable = new DrawableCharacter(c, index++, lineNumber, column++);
                drawables.add(drawable);
            }
        }

        // Draw the characters
        float x = getX();
        float y = getY();

        for (int currentLineNumber = 0; currentLineNumber <= lineNumber; currentLineNumber++) {
            if (drawLineNumbers) {
                RenderManager.instance().getCodeFontRenderer().draw(
                        matrices, String.valueOf(currentLineNumber + 1), x, y + 2, Color.GRAY.getRGB()
                );

                x += RenderManager.instance().getCodeFontRenderer().getWidth(String.valueOf(currentLineNumber + 1)) * marginWidth;
            }

            for (DrawableCharacter drawable : drawables) {
                if (drawable.getLineNumber() != currentLineNumber) continue;

                drawable.setupBoundingBox(RenderManager.instance().getCodeFontRenderer(), x, y);

                RenderManager.instance().getCodeFontRenderer().draw(
                        matrices, String.valueOf(drawable.getCharacter()), x, y + 2, Color.WHITE.getRGB()
                );

                x += drawable.getBoundingBox().getWidth();
            }

            y += RenderManager.instance().getCodeFontRenderer().fontHeight + 4;
            x = getX();
        }

        // Draw the cursor
//        if (cursor.columnNumber == 0 && lines.get(cursor.lineNumber).getText().length() == 0) {
//            RenderManager.instance().getRenderer().box(matrices,
//                    getX() + RenderManager.instance().getCustomFontRenderer().getWidth(String.valueOf(cursor.lineNumber + 1)) * marginWidth, getY() + cursor.lineNumber * (RenderManager.instance().getCustomFontRenderer().fontHeight + 4),
//                    1f, RenderManager.instance().getCustomFontRenderer().fontHeight + 4,
//                    new BoxColorScheme.Flat(Color.WHITE), null
//            );
//        }

        DrawableCharacter cursorChar = cursor.fetchCharacter();
        if (cursorChar != null) {
            RenderManager.instance().getRenderer().box(matrices,
                    (float) cursorChar.getBoundingBox().getMinX(), (float) cursorChar.getBoundingBox().getMinY(),
                    1f, (float) cursorChar.getBoundingBox().getHeight() + 4,
                    new BoxColorScheme.Flat(Color.WHITE), null
            );
        } else {
            cursorChar = cursor.fetchPreviousCharacter();
            if (cursorChar != null) {
                RenderManager.instance().getRenderer().box(matrices,
                        (float) cursorChar.getBoundingBox().getMaxX(), (float) cursorChar.getBoundingBox().getMinY(),
                        1f, (float) cursorChar.getBoundingBox().getHeight() + 4,
                        new BoxColorScheme.Flat(Color.WHITE), null
                );
            }
        }
    }

    /**
     * Write to the text when the user types
     * @param chr
     * @param modifiers
     */
    @Override
    public void charTyped(char chr, int modifiers) {
        super.charTyped(chr, modifiers);

        lines.get(cursor.lineNumber).write(chr, cursor.columnNumber);
        cursor.progressHorizontal(1);
    }

    /**
     * Handle input from the keyboard to perform operations to the text
     * @param keyCode
     * @param scanCode
     * @param modifiers
     */
    @Override
    public void keyPressed(int keyCode, int scanCode, int modifiers) {
        super.keyPressed(keyCode, scanCode, modifiers);

        switch (keyCode) {
            case GLFW.GLFW_KEY_LEFT -> cursor.left();
            case GLFW.GLFW_KEY_RIGHT -> cursor.right();
            case GLFW.GLFW_KEY_UP -> cursor.up();
            case GLFW.GLFW_KEY_DOWN -> cursor.down();
            case GLFW.GLFW_KEY_BACKSPACE -> {
                lines.get(cursor.lineNumber).delete(cursor.columnNumber - 1);

                // Delete the line if it's empty
                if (lines.get(cursor.lineNumber).getText().isEmpty() && cursor.lineNumber != 0) {
                    lines.remove(cursor.lineNumber);
                    cursor.left();
                    cursor.up();
                    return;
                }

                cursor.left();
            }
            case GLFW.GLFW_KEY_ENTER -> {
                lines.add(new Writer());
                cursor.down();
            }
        }
    }

    /**
     * A cursor that can move through the text.
     * @author Jake (github.com/jx4e)
     * @since 05/07/2022
     **/

    public class TextCursor {
        private int lineNumber;
        private int columnNumber;

        public TextCursor() {
            lineNumber = 0;
            columnNumber = 0;
        }

        /**
         * Moves the cursor left 1 space
         */
        public void left() {
            progressHorizontal(-1);
        }

        /**
         * Moves the cursor right 1 space
         */
        public void right() {
            progressHorizontal(1);
        }

        /**
         * Moves the cursor up 1 space
         */
        public void up() {
            progressVertical(-1);
        }

        /**
         * Moves the character down 1 space
         */
        public void down() {
            progressVertical(1);
        }

        /**
         *
         * @param c - number of columns to move across
         */
        public void progressHorizontal(int c) {
            // Check we dont run off the start of the first line
            if (columnNumber <= 0 && lineNumber == 0 && c < 0) return;

            // Check we dont run off the end of the last line
            if (!lines.isEmpty() && lineNumber == lines.size() - 1
                    && columnNumber >= lines.get(lines.size() - 1).getText().length() && c > 0) return;

            // Increase the columns by the desired amount
            columnNumber += c;

            // If we move the cursor past the first index we want it to go up a line
            if (columnNumber < 0) {
                // Make sure line number isnt 0
                if (lineNumber > 0) {
                    // Decrement line number
                    lineNumber--;

                    // Set the column number to the last in that row
                    columnNumber = lines.get(lineNumber).getText().length() - 1;
                }
            } else if (columnNumber > lines.get(lineNumber).getText().length()) {
                // Make sure line number isnt above the last line
                if (lineNumber < lines.size() - 1) {
                    // Increment line number
                    lineNumber++;

                    // Set the column number to the first in that row
                    columnNumber = 0;
                }
            }
        }

        /**
         *
         * @param l - number of lines to move across
         */
        public void progressVertical(int l) {
            lineNumber = MathHelper.clamp(lineNumber + l, 0, lines.size() - 1);

            if (columnNumber > lines.get(lineNumber).getText().length()) {
                columnNumber = lines.get(lineNumber).getText().length();
            }
        }

        /**
         * Method to get the selected character
         * @return character the cursor is selecting
         */
        @Nullable
        public DrawableCharacter fetchCharacter() {
            return fetchCharacter(lineNumber, columnNumber);
        }

        /**
         * Method to get the previous character
         * @return character the cursor is selecting
         */
        @Nullable
        public DrawableCharacter fetchPreviousCharacter() {
            return fetchCharacter(lineNumber, columnNumber - 1);
        }

        /**
         * Method to get the selected character
         * @return character the cursor is selecting
         */
        @Nullable
        private DrawableCharacter fetchCharacter(int lineNumber, int column) {
            return drawables.stream()
                    .filter(drawable -> drawable.getLineNumber() == lineNumber && drawable.getColumn() == column)
                    .findAny()
                    .orElse(null);
        }
    }
}
