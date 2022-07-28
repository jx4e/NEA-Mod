package com.github.jx4e.minecode.impl.manager;

import com.github.jx4e.minecode.util.render.CFontRenderer;
import com.github.jx4e.minecode.util.render.Renderer;
import net.minecraft.client.font.TextRenderer;

import static com.github.jx4e.minecode.MinecodeClient.mc;

/**
 * @author Jake (github.com/jx4e)
 * @since 29/06/2022
 **/

public class RenderManager {
    private final Renderer renderer = new Renderer();
    private final CFontRenderer codeFontRenderer = new CFontRenderer("code");
    private final CFontRenderer textFontRenderer = new CFontRenderer("text");

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

    private static RenderManager instance;

    public static RenderManager instance() {
        if (instance == null) instance = new RenderManager();

        return instance;
    }
}
