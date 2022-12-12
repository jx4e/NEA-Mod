package com.github.jx4e.minecode.rendering.widgets.buttons;

import com.github.jx4e.minecode.rendering.RenderManager;
import com.github.jx4e.minecode.project.LuaProject;
import com.github.jx4e.minecode.rendering.theme.Theme;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;

import java.awt.*;

public class ProjectToggleButton extends ButtonWidget {
    private LuaProject project;

    public ProjectToggleButton(int x, int y, int width, int height, Text message, PressAction onPress, LuaProject project) {
        super(x, y, width, height, message, onPress);
        this.project = project;
    }

    @Override
    public void renderButton(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        RenderManager.instance().getRenderer().box(matrices, x, y, getWidth(), getHeight(), Theme.DEFAULT.getBackground1());
        RenderManager.instance().getTextFontRenderer().draw(matrices, getMessage().getString(),
                x + getWidth() / 2f - RenderManager.instance().getTextFontRenderer().getWidth(getMessage().getString()) / 2f,
                y + getHeight() / 2f - RenderManager.instance().getTextFontRenderer().fontHeight / 2f,
                project.isEnabled() ? Theme.DEFAULT.getAccent().getRGB() : Color.WHITE.getRGB()
        );
    }
}
