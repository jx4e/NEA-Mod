package com.github.jx4e.minecode.rendering.screens;

import com.github.jx4e.minecode.Minecode;
import com.github.jx4e.minecode.project.LuaProject;
import com.github.jx4e.minecode.rendering.RenderManager;
import com.github.jx4e.minecode.rendering.theme.Theme;
import com.github.jx4e.minecode.rendering.widgets.buttons.IconButton;
import com.github.jx4e.minecode.rendering.widgets.buttons.EditorFileButton;
import com.github.jx4e.minecode.rendering.widgets.text.TextArea;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;

import java.io.File;

import static com.github.jx4e.minecode.MinecodeClient.mc;

/**
 * @author Jake (github.com/jx4e)
 * @since 11/06/2022
 **/

public class Editor extends Screen {
    private final LuaProject project;
    private TextArea area;

    public Editor(LuaProject project) {
        super(Text.of(Minecode.MOD_NAME));
        this.project = project;
    }

    @Override
    protected void init() {
        super.init();

        project.reloadScript();

        int barHeight = 2 * (RenderManager.instance().getTextFontRenderer().fontHeight + 10);
        int buttonSize = 2 * (int) (barHeight / 3f);

        addDrawableChild(new IconButton(5,  barHeight / 2 - buttonSize / 2,
                buttonSize, buttonSize, Text.of("Back"),
                button -> mc.setScreen(EditorProjectMenu.getInstance()),
                "back.png"
        ));

        addDrawableChild(area = new TextArea(width / 5, barHeight, 4 * width / 5, height - barHeight,
                project.getMainScriptFile(), this));

        addChildFiles("", project.getDir(), barHeight);

        addDrawableChild(new IconButton(5,  height - barHeight + buttonSize / 3,
                buttonSize, buttonSize, Text.of("Run"),
                button -> project.toggle(),
                "run.png"
        ));

        addDrawableChild(new IconButton(width / 5 - 5 - buttonSize,  height - barHeight + buttonSize / 3,
                buttonSize, buttonSize, Text.of("Settings"),
                button -> {},
                "settings.png"
        ));
    }

    private void addChildFiles(String prefix, File dir, int drawY) {
        for (File file : dir.listFiles()) {
            if (file.isDirectory()){
                addChildFiles(prefix + file.getName() + "/", file, drawY);
            } else if (file.isFile()) {
                addDrawableChild(new EditorFileButton(0,  drawY,
                        width/5, 18, Text.of(prefix + file.getName()),
                        button -> area.setEditingFile(file),
                        file
                ));
                drawY += 18;
            }
        }
    }

    @Override
    public void removed() {
        area.getDocument().save();
    }

    @Override
    public void renderBackground(MatrixStack matrices) {
        float barHeight = 2 * (RenderManager.instance().getTextFontRenderer().fontHeight + 10);

        // Render background
        RenderManager.instance().getRenderer().box(matrices, 0, 0, width, height, Theme.DEFAULT.getBackground2());

        // Render bars
        RenderManager.instance().getRenderer().box(matrices, 0, 0, width, barHeight, Theme.DEFAULT.getBackground1());
        RenderManager.instance().getRenderer().box(matrices, 0, 0, width / 5, height, Theme.DEFAULT.getBackground1());
        RenderManager.instance().getRenderer().box(matrices, 0, barHeight - 2, width, 2, Theme.DEFAULT.getAccent());

        // Render the run buttons bar
        RenderManager.instance().getRenderer().box(matrices, 0, height - barHeight, width / 5, height, Theme.DEFAULT.getBackground2());
    }

    @Override
    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        renderBackground(matrices);

        // Render instructions
        matrices.push();
        RenderManager.instance().getTextFontRenderer().draw(matrices, project.getName(),
                (width / 2f) - RenderManager.instance().getTextFontRenderer().getWidth(project.getName()) / 2f,
                (RenderManager.instance().getTextFontRenderer().fontHeight + 5),
                Theme.DEFAULT.getFont().getRGB()
        );
        matrices.pop();

        super.render(matrices, mouseX, mouseY, delta);
    }
}
