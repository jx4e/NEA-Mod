package com.github.jx4e.minecode.api.ui;

import com.github.jx4e.minecode.util.render.style.BoxColorScheme;
import net.minecraft.client.util.math.MatrixStack;

/**
 * @author Jake (github.com/jx4e)
 * @since 23/06/2022
 **/

public abstract class AbstractPane {
    private int x, y, width, height;
    private BoxColorScheme colorScheme;

    public AbstractPane(int x, int y, int width, int height, BoxColorScheme colorScheme) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.colorScheme = colorScheme;
    }

    public abstract void draw(MatrixStack matrices, int mouseX, int mouseY);

    public void mouseClicked(double mouseX, double mouseY, int mouseButton) {}

    public void mouseReleased(double mouseX, double mouseY, int button) {}

    public void keyPressed(int keyCode, int scanCode, int modifiers) {}

    public void mouseScrolled(double mouseX, double mouseY, double amount) {}

    public void charTyped(char chr, int modifiers) {}

    public boolean mouseOver(int mouseX, int mouseY, int minX, int minY, int maxX, int maxY) {
        return mouseX >= minX && mouseY >= minY && mouseX <= maxX && mouseY <= maxY;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public BoxColorScheme getColorScheme() {
        return colorScheme;
    }

    public void setColorScheme(BoxColorScheme colorScheme) {
        this.colorScheme = colorScheme;
    }
}
