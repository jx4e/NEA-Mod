package com.github.jx4e.minecode.api.ui.button;

import com.github.jx4e.minecode.impl.manager.RenderManager;
import com.github.jx4e.minecode.impl.manager.ResourceManager;
import com.github.jx4e.minecode.util.render.style.BoxColorScheme;
import net.minecraft.client.texture.NativeImageBackedTexture;
import net.minecraft.client.util.math.MatrixStack;

import java.awt.*;

public class IconButton extends AbstractButton {
    public IconButton(int x, int y, int width, int height, BoxColorScheme colorScheme) {
        super(x, y, width, height, colorScheme);
    }

    @Override
    public void draw(MatrixStack matrices, int mouseX, int mouseY) {
        RenderManager.instance().getRenderer().box(matrices, getX(), getY(), getWidth(), getHeight(), getColorScheme(), null);
        RenderManager.instance().getTextFontRenderer().draw(matrices, "Button",
                getX() + 2,
                getY() + getHeight() / 2f - RenderManager.instance().getTextFontRenderer().fontHeight / 2f,
                Color.WHITE.getRGB()
        );

        NativeImageBackedTexture texture = ResourceManager.instance().getNativeImageTexture("settings.png");
        RenderManager.instance().getRenderer().image(matrices, texture.getGlId(),
                getX() + getWidth() - RenderManager.instance().getTextFontRenderer().fontHeight - 2,
                getY() + getHeight() / 2f - RenderManager.instance().getTextFontRenderer().fontHeight / 2f,
                RenderManager.instance().getTextFontRenderer().fontHeight, RenderManager.instance().getTextFontRenderer().fontHeight
        );
    }

    @Override
    public void onLeftClick() {
        super.onLeftClick();
        System.out.printf("Left");
    }

    @Override
    public void onRightClick() {
        super.onRightClick();
        System.out.println("Right");
    }
}
