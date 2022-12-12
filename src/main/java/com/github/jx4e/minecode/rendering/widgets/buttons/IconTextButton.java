package com.github.jx4e.minecode.rendering.widgets.buttons;

import com.github.jx4e.minecode.rendering.theme.Theme;
import com.github.jx4e.minecode.rendering.RenderManager;
import com.github.jx4e.minecode.rendering.ResourceManager;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.texture.NativeImageBackedTexture;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;

import java.awt.*;

public class IconTextButton extends ButtonWidget {
    private final String iconName;

    public IconTextButton(int x, int y, int width, int height, Text message, PressAction onPress,
                          String iconName) {
        super(x, y, width, height, message, onPress);
        this.iconName = iconName;
    }

    @Override
    public void renderButton(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        RenderManager.instance().getRenderer().box(matrices, x, y, getWidth(), getHeight(), Theme.DEFAULT.getButton());
        RenderManager.instance().getTextFontRenderer().draw(matrices, getMessage(),
                x + 2,
                y + getHeight() / 2f - RenderManager.instance().getTextFontRenderer().fontHeight / 2f,
                Color.WHITE.getRGB()
        );

        NativeImageBackedTexture texture = ResourceManager.instance().getNativeImageTexture(iconName);
        RenderManager.instance().getRenderer().image(matrices, texture.getGlId(),
                x + getWidth() - getHeight(),
                y + 2,
                getHeight() - 4, getHeight() - 4
        );
        texture.close();
    }
}
