package com.github.jx4e.minecode.rendering.widgets.buttons;

import com.github.jx4e.minecode.rendering.RenderManager;
import com.github.jx4e.minecode.rendering.ResourceManager;
import com.github.jx4e.minecode.rendering.theme.Theme;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.texture.NativeImageBackedTexture;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;

public class IconButton extends ButtonWidget {
    private final String iconName;

    public IconButton(int x, int y, int width, int height, Text message, PressAction onPress, String iconName) {
        super(x, y, width, height, message, onPress);
        this.iconName = iconName;
    }

    public IconButton(int x, int y, int width, int height, Text message, PressAction onPress,
                      TooltipSupplier tooltipSupplier, String iconName) {
        super(x, y, width, height, message, onPress, tooltipSupplier);
        this.iconName = iconName;
    }

    @Override
    protected void renderBackground(MatrixStack matrices, MinecraftClient client, int mouseX, int mouseY) {
        RenderManager.instance().getRenderer().box(matrices, x, y, getWidth(), getHeight(), Theme.DEFAULT.getButton());
    }

    @Override
    public void renderButton(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        // Render the icon with the texture
        NativeImageBackedTexture texture = ResourceManager.instance().getNativeImageTexture(iconName);
        RenderManager.instance().getRenderer().image(matrices, texture.getGlId(),
                x,
                y,
                getWidth(), getWidth()
        );
        texture.close();
    }
}
