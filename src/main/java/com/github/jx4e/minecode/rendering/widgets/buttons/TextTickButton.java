package com.github.jx4e.minecode.rendering.widgets.buttons;

import com.github.jx4e.minecode.rendering.RenderManager;
import com.github.jx4e.minecode.rendering.ResourceManager;
import com.github.jx4e.minecode.rendering.theme.Theme;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.texture.NativeImageBackedTexture;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;

import java.awt.*;

public class TextTickButton extends ButtonWidget {
    boolean enabled = false;

    public TextTickButton(int x, int y, int width, int height, Text message, PressAction onPress) {
        super(x, y, width, height, message, onPress);
    }

    @Override
    public void renderButton(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        // Render background and text
        RenderManager.instance().getRenderer().box(matrices, x, y,
                getWidth() - getHeight(),
                getHeight(), Theme.DEFAULT.getBackground3());
        RenderManager.instance().getRenderer().box(matrices, x + getWidth() - getHeight(), y,
                getHeight(),
                getHeight(), Theme.DEFAULT.getButton());


        RenderManager.instance().getTextFontRenderer().draw(matrices, getMessage(),
                x + 2,
                y + getHeight() / 2f - RenderManager.instance().getTextFontRenderer().fontHeight / 2f,
                Color.WHITE.getRGB()
        );

        // Only render the tick if it's enabled
        if (!enabled) return;

        NativeImageBackedTexture texture = ResourceManager.instance().getNativeImageTexture("tick.png");
        RenderManager.instance().getRenderer().image(matrices, texture.getGlId(),
                x + getWidth() - getHeight() + 2,
                y + 2,
                getHeight() - 4, getHeight() - 4
        );
        texture.close();
    }

    @Override
    public void onClick(double mouseX, double mouseY) {
        enabled = !enabled;

        super.onClick(mouseX, mouseY);
    }

    public boolean isEnabled() {
        return enabled;
    }
}
