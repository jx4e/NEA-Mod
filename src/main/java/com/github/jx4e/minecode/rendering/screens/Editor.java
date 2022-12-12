package com.github.jx4e.minecode.rendering.screens;

import com.github.jx4e.minecode.Minecode;
import com.github.jx4e.minecode.project.LuaProject;
import com.github.jx4e.minecode.rendering.widgets.buttons.IconButton;
import com.github.jx4e.minecode.rendering.theme.Theme;
import com.github.jx4e.minecode.rendering.RenderManager;
import com.github.jx4e.minecode.rendering.widgets.text.TextArea;
import com.github.jx4e.minecode.project.IOUtil;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.nio.charset.StandardCharsets;

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

        addDrawableChild(area = new TextArea(width / 5, barHeight, width, height - barHeight,
                project.getMainScript().getContent(), this));
    }

    @Override
    public void removed() {
        File editingFile = project.getMainScriptFile();
        try {
            IOUtil.writeToOutputStream(
                    new ByteArrayInputStream(area.getDocument().getContent().getBytes(StandardCharsets.UTF_8)),
                    new FileOutputStream(editingFile)
            );
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void renderBackground(MatrixStack matrices) {
        float barHeight = 2 * (RenderManager.instance().getTextFontRenderer().fontHeight + 10);

        // Render background
        RenderManager.instance().getRenderer().box(matrices, 0, 0, width, height, Theme.DEFAULT.getBackground2());

        // Render bars
        RenderManager.instance().getRenderer().box(matrices, 0, 0, width, barHeight, Theme.DEFAULT.getBackground1());
        RenderManager.instance().getRenderer().box(matrices, 0, 0, width / 5, height, Theme.DEFAULT.getBackground1());
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

        RenderManager.instance().getCodeFontRenderer().draw(matrices, area.getDocument().getPointer().getSecond() + ":" + area.getDocument().getPointer().getFirst(),
                0, height - RenderManager.instance().getCodeFontRenderer().fontHeight - 2,
                Theme.DEFAULT.getFont().getRGB()
        );
        matrices.pop();

        super.render(matrices, mouseX, mouseY, delta);
    }
}
