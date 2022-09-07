package com.github.jx4e.minecode.api.ui.panes;

import com.github.jx4e.minecode.api.ui.AbstractPane;
import com.github.jx4e.minecode.api.ui.theme.Theme;
import com.github.jx4e.minecode.impl.manager.RenderManager;
import net.minecraft.client.util.math.MatrixStack;

/**
 * @author Jake (github.com/jx4e)
 * @since 23/06/2022
 **/

public class EditorPanel extends AbstractPane {
    private final com.github.jx4e.minecode.api.ui.text.TextPane textPane;

    public EditorPanel(int x, int y, int width, int height, Theme theme) {
        super(x, y, width, height, theme);
        textPane = new com.github.jx4e.minecode.api.ui.text.TextPane(getX(), getY(), getWidth(), getHeight(), theme);
    }

    @Override
    public void draw(MatrixStack matrices, int mouseX, int mouseY) {
        /* Background */
        matrices.push();
        RenderManager.instance().getRenderer().box(
                matrices, getX(), getY(), getWidth(), getHeight(), getTheme().getBackground2()
        );
        matrices.pop();

        /* Text */
        matrices.push();
        textPane.setX(getX());
        textPane.setY(getY());
        textPane.setWidth(getWidth());
        textPane.setHeight(getHeight());
        textPane.draw(matrices, mouseX, mouseY);
        matrices.pop();
    }

    @Override
    public void charTyped(char chr, int modifiers) {
        super.charTyped(chr, modifiers);
        textPane.charTyped(chr, modifiers);
    }

    @Override
    public void keyPressed(int keyCode, int scanCode, int modifiers) {
        super.keyPressed(keyCode, scanCode, modifiers);
        textPane.keyPressed(keyCode, scanCode, modifiers);
    }
}