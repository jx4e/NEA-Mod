package com.github.jx4e.minecode.manager;

import com.github.jx4e.minecode.util.render.CFontRenderer;
import com.github.jx4e.minecode.util.render.Renderer;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.util.math.MatrixStack;

import static com.github.jx4e.minecode.MinecodeClient.mc;

/**
 * @author Jake (github.com/jx4e)
 * @since 29/06/2022
 **/

public class RenderManager {
    private final Renderer renderer = new Renderer();
    private final CFontRenderer codeFontRenderer = new CFontRenderer("code");
    private final CFontRenderer textFontRenderer = new CFontRenderer("text");
    private MatrixStack currentMatrix;

    private RenderManager() {

    }

    public Renderer getRenderer() {
        return renderer;
    }

    public TextRenderer getCodeFontRenderer() {
        return codeFontRenderer.getTextRenderer();
    }

    public TextRenderer getTextFontRenderer() {
        return textFontRenderer.getTextRenderer();
    }

    public TextRenderer getDefaultFontRenderer() {
        return mc.textRenderer;
    }

    public MatrixStack getCurrentMatrix() {
        return currentMatrix;
    }

    public void setCurrentMatrix(MatrixStack currentMatrix) {
        this.currentMatrix = currentMatrix;
    }

    private static RenderManager instance;

    public static RenderManager instance() {
        if (instance == null) instance = new RenderManager();

        return instance;
    }
}
