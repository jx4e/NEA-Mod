package com.github.jx4e.minecode.api.ui.button;

import com.github.jx4e.minecode.api.ui.theme.Theme;
import com.github.jx4e.minecode.impl.manager.RenderManager;
import com.github.jx4e.minecode.impl.manager.ResourceManager;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.texture.NativeImageBackedTexture;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;
import org.lwjgl.glfw.GLFW;

import java.awt.*;

public class TextEntryButton extends ButtonWidget {
    private boolean typing = false;

    public TextEntryButton(int x, int y, int width, int height, Text message, PressAction onPress) {
        super(x, y, width, height, message, onPress);
    }

    public TextEntryButton(int x, int y, int width, int height, Text message, PressAction onPress,
                           TooltipSupplier tooltipSupplier) {
        super(x, y, width, height, message, onPress, tooltipSupplier);
    }


    @Override
    public void renderButton(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        RenderManager.instance().getRenderer().box(matrices, x, y, getWidth(), getHeight(), Theme.DEFAULT.getButton());

        RenderManager.instance().getTextFontRenderer().draw(matrices, getMessage(),
                x + getWidth() / 2f - RenderManager.instance().getTextFontRenderer().getWidth(getMessage()) / 2f,
                y + getHeight() / 2f - RenderManager.instance().getTextFontRenderer().fontHeight / 2f,
                Theme.DEFAULT.getFont().getRGB()
        );

        if (!typing) return;

        RenderManager.instance().getRenderer().box(matrices, x + getWidth() / 2f + RenderManager.instance().getTextFontRenderer().getWidth(getMessage()) / 2f, y, 1, getHeight(), Theme.DEFAULT.getFont());
    }
}
