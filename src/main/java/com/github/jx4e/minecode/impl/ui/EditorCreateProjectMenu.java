package com.github.jx4e.minecode.impl.ui;

import com.github.jx4e.minecode.Minecode;
import com.github.jx4e.minecode.api.ui.AbstractPane;
import com.github.jx4e.minecode.api.ui.button.IconButton;
import com.github.jx4e.minecode.api.ui.button.TextEntryButton;
import com.github.jx4e.minecode.api.ui.theme.Theme;
import com.github.jx4e.minecode.impl.manager.ProjectManager;
import com.github.jx4e.minecode.impl.manager.RenderManager;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

import java.util.ArrayList;
import java.util.List;

import static com.github.jx4e.minecode.MinecodeClient.mc;

/**
 * @author Jake (github.com/jx4e)
 * @since 11/06/2022
 **/

public class EditorCreateProjectMenu extends Screen {
    private TextEntryButton projectNameButton;
    private TextEntryButton mainScriptButton;

    public EditorCreateProjectMenu() {
        super(Text.of(Minecode.MOD_NAME));
    }

    @Override
    protected void init() {
        super.init();

        int barHeight = 2 * (RenderManager.instance().getTextFontRenderer().fontHeight + 10);
        int buttonSize = 2 * (int) (barHeight / 3f);

        addDrawableChild(new IconButton(5,  barHeight / 2 - buttonSize / 2,
                buttonSize, buttonSize, Text.of("Back"),
                button -> mc.setScreen(EditorProjectMenu.getInstance()), "back.png"
        ));

        addDrawableChild(new IconButton(width - buttonSize - 5, barHeight / 2 - buttonSize / 2,
                buttonSize, buttonSize, Text.of("Add"),
                button -> {
                    ProjectManager.instance().createProject(projectNameButton.getMessage().getString(), mainScriptButton.getMessage().getString());
                    mc.setScreen(EditorProjectMenu.getInstance());
                }
                ,"checked.png"
        ));

        int entryHeight = RenderManager.instance().getTextFontRenderer().fontHeight * 2;
        
        addDrawableChild(projectNameButton = new TextEntryButton(100, 50, width - 110, entryHeight,
                Text.of("Name"), null)
        );

        addDrawableChild(mainScriptButton = new TextEntryButton(100, 50, width - 110, entryHeight,
                Text.of("Script"), null)
        );
    }

    @Override
    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        float barHeight = 2 * (RenderManager.instance().getTextFontRenderer().fontHeight + 10);

        // Render background
        RenderManager.instance().getRenderer().box(matrices, 0, 0, width, height, Theme.DEFAULT.getBackground2());

        // Render bars
        RenderManager.instance().getRenderer().box(matrices, 0, 0, width, barHeight, Theme.DEFAULT.getBackground1());
        RenderManager.instance().getRenderer().box(matrices, 0, height - barHeight, width, barHeight, Theme.DEFAULT.getBackground1());

        // Render instructions
        matrices.push();
        RenderManager.instance().getTextFontRenderer().draw(matrices, "Create a new " + Formatting.BOLD + "Project",
                (width / 2f) - RenderManager.instance().getTextFontRenderer().getWidth("Create a new " + Formatting.BOLD + "Project") / 2f,
                (RenderManager.instance().getTextFontRenderer().fontHeight + 5),
                Theme.DEFAULT.getFont().getRGB()
        );
        matrices.pop();

        super.render(matrices, mouseX, mouseY, delta);

        // Render options
        int buttonHeight = RenderManager.instance().getTextFontRenderer().fontHeight * 2;
        int buttonY = 50;

//        matrices.push();
//        RenderManager.instance().getTextFontRenderer().draw(matrices, "Project Name",
//                10,
//                buttonY + RenderManager.instance().getTextFontRenderer().fontHeight / 2f,
//                Theme.DEFAULT.getFont().getRGB()
//        );
//        matrices.pop();
//
//        matrices.push();
//        RenderManager.instance().getTextFontRenderer().draw(matrices, "Main Script",
//                10,
//                buttonY + RenderManager.instance().getTextFontRenderer().fontHeight / 2f,
//                Theme.DEFAULT.getFont().getRGB()
//        );
//        matrices.pop();
    }

    private static EditorCreateProjectMenu instance;

    public static EditorCreateProjectMenu getInstance() {
        if (instance == null) instance = new EditorCreateProjectMenu();

        return instance;
    }
}
