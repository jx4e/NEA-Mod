package com.github.jx4e.minecode.api.ui.button;

import com.github.jx4e.minecode.api.ui.theme.Theme;
import com.github.jx4e.minecode.impl.manager.RenderManager;
import com.github.jx4e.minecode.impl.manager.ResourceManager;
import net.minecraft.client.texture.NativeImageBackedTexture;
import net.minecraft.client.util.math.MatrixStack;
import org.lwjgl.glfw.GLFW;

import java.awt.*;

public class TextEntryButton extends AbstractButton {
    private String text;

    private boolean typing = false;

    public TextEntryButton(int x, int y, int width, int height, Theme theme, String text) {
        super(x, y, width, height, theme);
        this.text = text;
    }

    @Override
    public void draw(MatrixStack matrices, int mouseX, int mouseY) {
        RenderManager.instance().getRenderer().box(matrices, getX(), getY(), getWidth(), getHeight(), getTheme().getButton());

        RenderManager.instance().getTextFontRenderer().draw(matrices, text,
                getX() + getWidth() / 2f - RenderManager.instance().getTextFontRenderer().getWidth(text) / 2f,
                getY() + getHeight() / 2f - RenderManager.instance().getTextFontRenderer().fontHeight / 2f,
                getTheme().getFont().getRGB()
        );

        if (!typing) return;

        RenderManager.instance().getRenderer().box(matrices, getX() + getWidth() / 2f + RenderManager.instance().getTextFontRenderer().getWidth(text) / 2f, getY(), 1, getHeight(), getTheme().getFont());
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public void onLeftClick() {
        super.onLeftClick();

        typing = !typing;
    }

    @Override
    public void onLeftClickElsewhere() {
        super.onLeftClickElsewhere();

        typing = false;
    }

    @Override
    public void onRightClick() {
        super.onRightClick();
    }

    @Override
    public void charTyped(char chr, int modifiers) {
        super.charTyped(chr, modifiers);

        if (typing) text += chr;
    }

    @Override
    public void keyPressed(int keyCode, int scanCode, int modifiers) {
        super.keyPressed(keyCode, scanCode, modifiers);

        if (keyCode == GLFW.GLFW_KEY_BACKSPACE && text.length() > 0 && typing) {
            text = text.substring(0, text.length() - 1);
        }
    }
}
