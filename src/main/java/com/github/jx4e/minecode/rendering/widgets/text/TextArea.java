package com.github.jx4e.minecode.rendering.widgets.text;

import com.github.jx4e.minecode.rendering.RenderManager;
import com.github.jx4e.minecode.rendering.theme.Theme;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.narration.NarrationMessageBuilder;
import net.minecraft.client.gui.widget.ClickableWidget;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;

import java.awt.*;
import java.io.File;

import static com.github.jx4e.minecode.MinecodeClient.mc;
import static org.lwjgl.glfw.GLFW.*;

/**
 * @author Jake (github.com/jx4e)
 * @since 21/09/2022
 **/

public class TextArea extends ClickableWidget {
    private TextDocument document;
    private final Screen parent;
    private long last;

    public TextArea(int x, int y, int width, int height, File editing, Screen parent) {
        super(x, y, width, height, Text.of(editing.getName()));
        this.document = new TextDocument(editing);
        this.last = System.currentTimeMillis();
        this.parent = parent;
        parent.setFocused(this);
    }

    @Override
    public void renderButton(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        RenderManager.instance().getRenderer().box(matrices, x, y, getWidth(), getHeight(), Theme.DEFAULT.getBackground2());

        if (document.getEditing() == null) return;

        // margin
        float marginWidth = RenderManager.instance().getCodeFontRenderer().getWidth("00000") + 2;
        float marginHeight = RenderManager.instance().getCodeFontRenderer().fontHeight * 3;

        RenderManager.instance().getRenderer().box(matrices, x, y, marginWidth, getHeight(), Theme.DEFAULT.getBackground3());

        // Saving
        RenderManager.instance().getTextFontRenderer().draw(matrices, "Currently Editing " + document.getEditing().getName(),
                x + width - RenderManager.instance().getTextFontRenderer().getWidth("Currently Editing " + document.getEditing().getName()) - 2,
                y + height - marginHeight / 2 - 5,
                Color.WHITE.getRGB()
        );

        float renderY = y + 1;
        for (int i = 0; i < document.getLines().size(); i++) {
            RenderManager.instance().getTextFontRenderer().draw(matrices, String.valueOf(i + 1),
                    x + 1, renderY, document.getPointer().getSecond() == i ? Color.WHITE.getRGB() : Color.GRAY.getRGB()
            );

            RenderManager.instance().getCodeFontRenderer().draw(matrices, document.getLines().get(i),
                    x + marginWidth + 2, renderY, Color.WHITE.getRGB()
            );

            renderY = y + (i+1) * (RenderManager.instance().getCodeFontRenderer().fontHeight + 5);
        }

        //Render the cursor
        if (parent.getFocused().equals(this)) {
            String cursorLine = document.getLines().get(document.getPointer().getSecond());

            // We want it to be visible for 500ms then gone for 250ms
            // While last<500ms it shows
            // Then it updates at 750ms so that between 500-750 it was not visible
            RenderManager.instance().getRenderer().box(matrices,
                    x + marginWidth + 2 + (cursorLine.length() <= 0 || document.getPointer().getFirst() <= 0 ? 0 : RenderManager.instance().getCodeFontRenderer().getWidth(cursorLine.substring(0, document.getPointer().getFirst()))),
                    y + 1 + (document.getPointer().getSecond()) * (RenderManager.instance().getCodeFontRenderer().fontHeight + 5),
                    1,
                    RenderManager.instance().getCodeFontRenderer().fontHeight,
                    System.currentTimeMillis() - last < 500 ? Color.WHITE : new Color(0x0000000, true)
            );

            // Update as it has passed 750ms
            if (System.currentTimeMillis() - last >= 750) last = System.currentTimeMillis();
        }
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

        if (isPaste(keyCode)) {
            document.insert(mc.keyboard.getClipboard());
        }

        return super.keyPressed(keyCode, scanCode, modifiers);
    }

    public boolean isPaste(int code) {
        return code == GLFW_KEY_V && Screen.hasControlDown() && !Screen.hasShiftDown() && !Screen.hasAltDown();
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

    public void setEditingFile(File file) {
        if (file.equals(document.getEditing())) return;

        document.save();
        document = new TextDocument(file);
    }

    public TextDocument getDocument() {
        return document;
    }

    @Override
    public void appendNarrations(NarrationMessageBuilder builder) {

    }
}
