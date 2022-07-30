package com.github.jx4e.minecode.api.ui.button;

import com.github.jx4e.minecode.api.ui.theme.Theme;
import com.github.jx4e.minecode.impl.manager.RenderManager;
import com.github.jx4e.minecode.impl.manager.ResourceManager;
import net.minecraft.client.texture.NativeImageBackedTexture;
import net.minecraft.client.util.math.MatrixStack;

import java.awt.*;

public class IconButton extends AbstractButton {
    private final String displayMessage;
    private final String iconName;

    public IconButton(int x, int y, int width, int height, Theme theme, String displayMessage, String iconName) {
        super(x, y, width, height, theme);
        this.displayMessage = displayMessage;
        this.iconName = iconName;
    }

    @Override
    public void draw(MatrixStack matrices, int mouseX, int mouseY) {
        RenderManager.instance().getRenderer().box(matrices, getX(), getY(), getWidth(), getHeight(), getTheme().getBackground2());
        RenderManager.instance().getTextFontRenderer().draw(matrices, displayMessage,
                getX() + 2,
                getY() + getHeight() / 2f - RenderManager.instance().getTextFontRenderer().fontHeight / 2f,
                Color.WHITE.getRGB()
        );

        NativeImageBackedTexture texture = ResourceManager.instance().getNativeImageTexture(iconName);
        RenderManager.instance().getRenderer().image(matrices, texture.getGlId(),
                getX() + getWidth() - getHeight(),
                getY() + 2,
                getHeight() - 4, getHeight() - 4
        );
        texture.close();
    }

    @Override
    public void onLeftClick() {
        super.onLeftClick();
        System.out.println("Left");
    }

    @Override
    public void onRightClick() {
        super.onRightClick();
        System.out.println("Right");
    }
}
