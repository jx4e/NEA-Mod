package com.github.jx4e.minecode.api.ui.button;

import com.github.jx4e.minecode.api.ui.theme.Theme;
import com.github.jx4e.minecode.impl.manager.RenderManager;
import net.minecraft.client.util.math.MatrixStack;

import java.awt.*;

public class SimpleButton extends AbstractButton {
    public SimpleButton(int x, int y, int width, int height, Theme theme) {
        super(x, y, width, height, theme);
    }

    @Override
    public void draw(MatrixStack matrices, int mouseX, int mouseY) {
        RenderManager.instance().getRenderer().box(matrices, getX(), getY(), getWidth(), getHeight(), getTheme().getAccent());
        RenderManager.instance().getTextFontRenderer().draw(matrices, "Button",
                getX() + getWidth() / 2f - RenderManager.instance().getTextFontRenderer().getWidth("Button") / 2f,
                getY() + getHeight() / 2f - RenderManager.instance().getTextFontRenderer().fontHeight / 2f,
                Color.WHITE.getRGB()
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
