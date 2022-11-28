package com.github.jx4e.minecode.ui.widgets.buttons;

import com.github.jx4e.minecode.manager.RenderManager;
import com.github.jx4e.minecode.manager.ResourceManager;
import com.github.jx4e.minecode.ui.theme.Theme;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.texture.NativeImageBackedTexture;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;

public class TickButton extends ButtonWidget {
    public TickButton(int x, int y, int width, int height, PressAction onPress) {
        super(x, y, width, height, Text.empty(), onPress);
    }

    public TickButton(int x, int y, int width, int height, PressAction onPress,
                      TooltipSupplier tooltipSupplier) {
        super(x, y, width, height, Text.empty(), onPress, tooltipSupplier);

    }

    @Override
    public void renderButton(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        RenderManager.instance().getRenderer().box(matrices, x, y, getWidth(), getHeight(), Theme.DEFAULT.getButton());

        NativeImageBackedTexture texture = ResourceManager.instance().getNativeImageTexture("tick.png");
        RenderManager.instance().getRenderer().image(matrices, texture.getGlId(),
                x,
                y,
                getHeight(), getHeight()
        );
        texture.close();
    }
}
