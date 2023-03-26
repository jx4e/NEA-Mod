package com.github.jx4e.minecode.rendering.screens;

import com.github.jx4e.minecode.Minecode;
import com.github.jx4e.minecode.rendering.theme.Theme;
import com.github.jx4e.minecode.project.ProjectManager;
import com.github.jx4e.minecode.rendering.RenderManager;
import com.github.jx4e.minecode.rendering.widgets.buttons.ProjectToggleButton;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author Jake (github.com/jx4e)
 * @since 11/06/2022
 **/

public class QuickToggleMenu extends Screen {
    public QuickToggleMenu() {
        super(Text.of(Minecode.MOD_NAME));
    }

    @Override
    protected void init() {
        super.init();

        int buttonWidth = width - 20;
        int buttonHeight = RenderManager.instance().getTextFontRenderer().fontHeight * 2;
        int buttonX = 10;
        AtomicInteger buttonY = new AtomicInteger(50);

        ProjectManager.instance().getProjects().forEach(luaProject -> {
            // Add the button at the buttn X and Y
            addDrawableChild(new ProjectToggleButton(buttonX, buttonY.get(), buttonWidth, buttonHeight,
                    Text.of(luaProject.getName()),
                    button -> luaProject.setEnabled(!luaProject.isEnabled()),
                    luaProject
            ));

            // Increment Y so that we get the buttons in a column
            buttonY.addAndGet(buttonHeight + 2);
        });
    }

    @Override
    public void renderBackground(MatrixStack matrices) {
        float barHeight = 2 * (RenderManager.instance().getTextFontRenderer().fontHeight + 10);

        // Render background
        RenderManager.instance().getRenderer().box(matrices, 0, 0, width, height, Theme.DEFAULT.getBackground2());

        // Render bars
        RenderManager.instance().getRenderer().box(matrices, 0, 0, width, barHeight, Theme.DEFAULT.getBackground1());
        RenderManager.instance().getRenderer().box(matrices, 0, height - barHeight, width, barHeight, Theme.DEFAULT.getBackground1());
        RenderManager.instance().getRenderer().box(matrices, 0, barHeight - 2, width, 2, Theme.DEFAULT.getAccent());
    }

    @Override
    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        renderBackground(matrices);

        // Render instructions
        matrices.push();
        RenderManager.instance().getTextFontRenderer().draw(matrices, "Click to toggle a project",
                (width / 2f) - RenderManager.instance().getTextFontRenderer().getWidth("Click to toggle a project") / 2f,
                (RenderManager.instance().getTextFontRenderer().fontHeight + 5),
                Theme.DEFAULT.getFont().getRGB()
        );
        matrices.pop();

        super.render(matrices, mouseX, mouseY, delta);
    }

    private static QuickToggleMenu instance;

    public static QuickToggleMenu getInstance() {
        if (instance == null) instance = new QuickToggleMenu();

        return instance;
    }
}
