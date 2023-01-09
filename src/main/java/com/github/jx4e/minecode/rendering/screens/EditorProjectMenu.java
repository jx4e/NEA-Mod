package com.github.jx4e.minecode.rendering.screens;

import com.github.jx4e.minecode.Minecode;
import com.github.jx4e.minecode.rendering.widgets.buttons.IconButton;
import com.github.jx4e.minecode.rendering.widgets.buttons.IconTextButton;
import com.github.jx4e.minecode.rendering.theme.Theme;
import com.github.jx4e.minecode.project.ProjectManager;
import com.github.jx4e.minecode.rendering.RenderManager;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

import java.util.concurrent.atomic.AtomicInteger;

import static com.github.jx4e.minecode.MinecodeClient.mc;

/**
 * @author Jake (github.com/jx4e)
 * @since 11/06/2022
 **/

public class EditorProjectMenu extends Screen {
    public EditorProjectMenu() {
        super(Text.of(Minecode.MOD_NAME));
    }

    @Override
    protected void init() {
        super.init();

        int barHeight = 2 * (RenderManager.instance().getTextFontRenderer().fontHeight + 10);
        int buttonSize = 2 * (int) (barHeight / 3f);

        addDrawableChild(new IconButton(5,  barHeight / 2 - buttonSize / 2,
                buttonSize, buttonSize, Text.of("Back"),
                button -> mc.setScreen(EditorMainMenu.getInstance()),
                "back.png"
        ));

        addDrawableChild(new IconButton(width - buttonSize - 5, barHeight / 2 - buttonSize / 2,
                buttonSize, buttonSize, Text.of("Add"),
                button -> mc.setScreen(EditorCreateProjectMenu.getInstance()),
                "add.png"
        ));

        int buttonWidth = width - 20;
        int buttonHeight = RenderManager.instance().getTextFontRenderer().fontHeight * 2;
        int buttonX = 10;
        AtomicInteger buttonY = new AtomicInteger(50);

        ProjectManager.instance().getProjects().forEach(luaProject -> {
            addDrawableChild(new IconTextButton(buttonX, buttonY.get(), buttonWidth, buttonHeight,
                    Text.of(luaProject.getName()),
                    button -> mc.setScreen(new Editor(luaProject)),
                    "folder.png"
            ));

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
        RenderManager.instance().getTextFontRenderer().draw(matrices, "Please select a " + Formatting.BOLD + "Project" + Formatting.RESET + " or press " + Formatting.BOLD + "+" + Formatting.RESET + " to create one.",
                (width / 2f) - RenderManager.instance().getTextFontRenderer().getWidth("Please select a " + Formatting.BOLD + "Project" + Formatting.RESET + " or press " + Formatting.BOLD + "+" + Formatting.RESET + " to create one.") / 2f,
                (RenderManager.instance().getTextFontRenderer().fontHeight + 5),
                Theme.DEFAULT.getFont().getRGB()
        );
        matrices.pop();

        super.render(matrices, mouseX, mouseY, delta);
    }

    private static EditorProjectMenu instance;

    public static EditorProjectMenu getInstance() {
        if (instance == null) instance = new EditorProjectMenu();

        return instance;
    }
}
