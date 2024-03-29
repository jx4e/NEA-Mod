package com.github.jx4e.minecode.rendering.widgets.text;

import com.github.jx4e.minecode.rendering.theme.Theme;
import com.github.jx4e.minecode.rendering.RenderManager;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;

import java.awt.*;

import static org.lwjgl.glfw.GLFW.*;

public class TextEntry extends ButtonWidget {
    private boolean typing = false;
    private OneLineDocument document = new OneLineDocument("");
    private long last;

    public TextEntry(int x, int y, int width, int height, Text message, PressAction onPress) {
        this(x, y, width, height, message, onPress, EMPTY);
    }

    public TextEntry(int x, int y, int width, int height, Text message, PressAction onPress,
                     TooltipSupplier tooltipSupplier) {
        super(x, y, width, height, message, onPress, tooltipSupplier);
        this.last = System.currentTimeMillis();
    }

    @Override
    public void onPress() {
        typing = !typing;
        super.onPress();
    }

    @Override
    public void renderButton(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        RenderManager.instance().getRenderer().box(matrices, x, y, getWidth(), getHeight(), Theme.DEFAULT.getButton());
        RenderManager.instance().getRenderer().box(matrices, x, y,
                getWidth() / 7,
                getHeight(), Theme.DEFAULT.getBackground3());

        RenderManager.instance().getTextFontRenderer().draw(matrices, getMessage(),
                x,
                y + getHeight() / 2f - RenderManager.instance().getTextFontRenderer().fontHeight / 2f,
                Theme.DEFAULT.getFont().getRGB()
        );

        RenderManager.instance().getCodeFontRenderer().draw(matrices, getContent(),
                x + getWidth() / 7,
                y + getHeight() / 2f - RenderManager.instance().getCodeFontRenderer().fontHeight / 2f,
                Color.WHITE.getRGB()
        );

        // Return if not typing
        if (!typing) return;


        // We want it to be visible for 500ms then gone for 250ms
        // While last<500ms it shows
        // Then it updates at 750ms so that between 500-750 it was not visible
        RenderManager.instance().getRenderer().box(matrices,
                x + getWidth() / 7f + (getContent().length() <= 0 || document.getPointer() <= 0 ?
                        0 : RenderManager.instance().getCodeFontRenderer().getWidth(getContent().substring(0, document.getPointer()))),
                y + getHeight() / 2f - RenderManager.instance().getCodeFontRenderer().fontHeight / 2f,
                1,
                RenderManager.instance().getCodeFontRenderer().fontHeight,
                System.currentTimeMillis() - last < 500 ? Color.WHITE : new Color(0x0000000, true) // Render depending on the time
        );

        // If 750ms passed update last
        if (System.currentTimeMillis() - last >= 750) last = System.currentTimeMillis();
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        if (!typing) super.keyPressed(keyCode, scanCode, modifiers);

        // Actions to do when key pressed
        switch (keyCode) {
            case GLFW_KEY_BACKSPACE -> document.backspace();
            case GLFW_KEY_LEFT -> document.pointerLeft();
            case GLFW_KEY_RIGHT -> document.pointerRight();
            case GLFW_KEY_SPACE -> onPress();
        }

        return super.keyPressed(keyCode, scanCode, modifiers);
    }

    @Override
    public boolean keyReleased(int keyCode, int scanCode, int modifiers) {
        return super.keyReleased(keyCode, scanCode, modifiers);
    }

    @Override
    public boolean charTyped(char chr, int modifiers) {
        if (!typing) super.charTyped(chr, modifiers);


        document.insert(String.valueOf(chr));
        return super.charTyped(chr, modifiers);
    }

    public String getContent() {
        return document.getContent();
    }
}
