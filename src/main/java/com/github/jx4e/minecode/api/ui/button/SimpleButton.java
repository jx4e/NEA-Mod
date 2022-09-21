package com.github.jx4e.minecode.api.ui.button;

import com.github.jx4e.minecode.api.ui.theme.Theme;
import com.github.jx4e.minecode.impl.manager.RenderManager;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;

import java.awt.*;

public class SimpleButton extends ButtonWidget {
    public SimpleButton(int x, int y, int width, int height, Text message, PressAction onPress) {
        super(x, y, width, height, message, onPress);
    }

    public SimpleButton(int x, int y, int width, int height, Text message, PressAction onPress,
                        TooltipSupplier tooltipSupplier) {
        super(x, y, width, height, message, onPress, tooltipSupplier);
    }

    @Override
    public void renderButton(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        RenderManager.instance().getRenderer().box(matrices, x, y, getWidth(), getHeight(), Theme.DEFAULT.getAccent());
        RenderManager.instance().getTextFontRenderer().draw(matrices, "Button",
                x + getWidth() / 2f - RenderManager.instance().getTextFontRenderer().getWidth("Button") / 2f,
                y + getHeight() / 2f - RenderManager.instance().getTextFontRenderer().fontHeight / 2f,
                Color.WHITE.getRGB()
        );
    }
}
