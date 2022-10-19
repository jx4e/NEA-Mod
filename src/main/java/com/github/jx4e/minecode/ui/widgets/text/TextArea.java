package com.github.jx4e.minecode.ui.widgets.text;

import com.github.jx4e.minecode.manager.RenderManager;
import com.github.jx4e.minecode.ui.theme.Theme;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.narration.NarrationMessageBuilder;
import net.minecraft.client.gui.widget.ClickableWidget;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;

import java.awt.*;

import static org.lwjgl.glfw.GLFW.*;

/**
 * @author Jake (github.com/jx4e)
 * @since 21/09/2022
 **/

public class TextArea extends ClickableWidget {
    private TextDocument document;
    private Screen parent;

    public TextArea(int x, int y, int width, int height, String content, Screen parent) {
        super(x, y, width, height, Text.of(content));
        this.document = new TextDocument(content);
        this.parent = parent;
        parent.setFocused(this);
    }

    @Override
    public void renderButton(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        RenderManager.instance().getRenderer().box(matrices, x, y, getWidth(), getHeight(), Theme.DEFAULT.getBackground2());

        float renderY = y + 1;
        for (int i = 0; i < document.getLines().size(); i++) {
            RenderManager.instance().getCodeFontRenderer().draw(matrices, document.getLines().get(i),
                    x, renderY, Color.WHITE.getRGB()
            );

            renderY = y + (i+1) * RenderManager.instance().getCodeFontRenderer().fontHeight + 1;
        }

        //Render the cursor
        String cursorLine = document.getLines().get(document.getPointer().getSecond());

        RenderManager.instance().getRenderer().box(matrices,
                x + (cursorLine.length() <= 0 || document.getPointer().getFirst() <= 0 ? 0 : RenderManager.instance().getCodeFontRenderer().getWidth(cursorLine.substring(0, document.getPointer().getFirst()))),
                y + 1 + (document.getPointer().getSecond()) * RenderManager.instance().getCodeFontRenderer().fontHeight + 1,
                1,
                RenderManager.instance().getCodeFontRenderer().fontHeight,
                Color.WHITE
        );
    }

    @Override
    public void mouseMoved(double mouseX, double mouseY) {
        super.mouseMoved(mouseX, mouseY);
    }

    @Override
    public boolean mouseScrolled(double mouseX, double mouseY, double amount) {
        return super.mouseScrolled(mouseX, mouseY, amount);
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        switch (keyCode) {
            case GLFW_KEY_BACKSPACE -> document.backspace();
            case GLFW_KEY_UP -> document.pointerUp();
            case GLFW_KEY_DOWN -> document.pointerDown();
            case GLFW_KEY_LEFT -> document.pointerLeft();
            case GLFW_KEY_RIGHT -> document.pointerRight();
            case GLFW_KEY_ENTER -> document.newLine();
        }

        return super.keyPressed(keyCode, scanCode, modifiers);
    }

    @Override
    public boolean keyReleased(int keyCode, int scanCode, int modifiers) {
        return super.keyReleased(keyCode, scanCode, modifiers);
    }

    @Override
    public boolean charTyped(char chr, int modifiers) {
        document.insert(String.valueOf(chr));
        return super.charTyped(chr, modifiers);
    }

    @Override
    public void appendNarrations(NarrationMessageBuilder builder) {

    }

    public TextDocument getDocument() {
        return document;
    }
}
