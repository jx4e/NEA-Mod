package com.github.jx4e.minecode.rendering.screens;

import com.github.jx4e.minecode.Minecode;
import com.github.jx4e.minecode.rendering.widgets.buttons.IconButton;
import com.github.jx4e.minecode.rendering.widgets.buttons.SimpleButton;
import com.github.jx4e.minecode.rendering.widgets.text.TextEntry;
import com.github.jx4e.minecode.rendering.theme.Theme;
import com.github.jx4e.minecode.project.ProjectManager;
import com.github.jx4e.minecode.rendering.RenderManager;
import com.github.jx4e.minecode.rendering.widgets.buttons.TextTickButton;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

import static com.github.jx4e.minecode.MinecodeClient.mc;

/**
 * @author Jake (github.com/jx4e)
 * @since 11/06/2022
 **/

public class EditorCreateProjectMenu extends Screen {
    private TextEntry projectNameButton;
    private TextEntry mainScriptButton;
    private TextTickButton useTemplateButton;

    public EditorCreateProjectMenu() {
        super(Text.of(Minecode.MOD_NAME));
    }

    /**
     * Init buttons and stuff
     */
    @Override
    protected void init() {
        super.init();

        int barHeight = 2 * (RenderManager.instance().getTextFontRenderer().fontHeight + 10);
        int buttonSize = 2 * (int) (barHeight / 3f);

        addDrawableChild(new IconButton(5,  barHeight / 2 - buttonSize / 2,
                buttonSize, buttonSize, Text.of("Back"),
                button -> mc.setScreen(EditorProjectMenu.getInstance()),
                "back.png"
        ));

        int entryHeight = RenderManager.instance().getTextFontRenderer().fontHeight * 2;
        
        addDrawableChild(projectNameButton = new TextEntry(10, 50, width - 20, entryHeight,
                Text.of("Name"), (action) -> {})
        );

        addDrawableChild(mainScriptButton = new TextEntry(10, 60 + entryHeight, width - 20, entryHeight,
                Text.of("Script"), (action) -> {})
        );

        addDrawableChild(useTemplateButton = new TextTickButton(10, 70 + entryHeight * 2,
                (width - 20) / 5, entryHeight, Text.of("Use Template Lua Script"), (p) -> {}));

        addDrawableChild(new SimpleButton(0, height - barHeight,
                width, barHeight, Text.of("Create Project"), (p) -> {
            ProjectManager.instance().createProject(projectNameButton.getContent(), mainScriptButton.getContent(), useTemplateButton.isEnabled());
            mc.setScreen(EditorProjectMenu.getInstance());
        }));
    }

    /**
     * Renders the background
     * @param matrices
     */
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

    /**
     * Called to draw the screen
     * @param matrices
     * @param mouseX
     * @param mouseY
     * @param delta
     */
    @Override
    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        renderBackground(matrices);

        // Render instructions
        matrices.push();
        RenderManager.instance().getTextFontRenderer().draw(matrices, "Create a new " + Formatting.BOLD + "Project",
                (width / 2f) - RenderManager.instance().getTextFontRenderer().getWidth("Create a new " + Formatting.BOLD + "Project") / 2f,
                (RenderManager.instance().getTextFontRenderer().fontHeight + 5),
                Theme.DEFAULT.getFont().getRGB()
        );
        matrices.pop();

        super.render(matrices, mouseX, mouseY, delta);
    }

    private static EditorCreateProjectMenu instance;

    /**
     * Get the instance of this screen
     * @return
     */
    public static EditorCreateProjectMenu getInstance() {
        if (instance == null) instance = new EditorCreateProjectMenu();

        return instance;
    }
}
