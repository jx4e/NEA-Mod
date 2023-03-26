package com.github.jx4e.minecode.rendering.widgets.buttons;

import com.github.jx4e.minecode.rendering.RenderManager;
import com.github.jx4e.minecode.rendering.ResourceManager;
import com.github.jx4e.minecode.rendering.theme.Theme;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.texture.NativeImageBackedTexture;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;

import java.awt.*;
import java.io.File;

public class EditorFileButton extends ButtonWidget {
    private final File file;

    public EditorFileButton(int x, int y, int width, int height, Text message, PressAction onPress,
                            File file) {
        super(x, y, width, height, message, onPress);
        this.file = file;
    }

    @Override
    public void renderButton(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        String icon = "document.png";

        // Decide which icon we want for this specific file
        if (file.getName().endsWith(".json")) icon = "json.png";
        else if (file.getName().endsWith(".lua")) icon = "code.png";

        // Render the background and text
        RenderManager.instance().getRenderer().box(matrices, x, y, width, getHeight(), Theme.DEFAULT.getBackground2());
        RenderManager.instance().getTextFontRenderer().draw(matrices, getMessage(),
                x + 2,
                y + getHeight() / 2f - RenderManager.instance().getTextFontRenderer().fontHeight / 2f,
                Color.WHITE.getRGB()
        );

        // Render the icon
        NativeImageBackedTexture texture = ResourceManager.instance().getNativeImageTexture(icon);
        RenderManager.instance().getRenderer().image(matrices, texture.getGlId(),
                x + getWidth() - getHeight(),
                y + 2,
                getHeight() - 4, getHeight() - 4
        );
        texture.close();
    }
}
