package com.github.jx4e.minecode.rendering.screens;

import com.github.jx4e.minecode.Minecode;
import com.github.jx4e.minecode.project.IOUtil;
import com.github.jx4e.minecode.project.LuaLesson;
import com.github.jx4e.minecode.rendering.RenderManager;
import com.github.jx4e.minecode.rendering.theme.Theme;
import com.github.jx4e.minecode.rendering.widgets.buttons.IconButton;
import com.github.jx4e.minecode.rendering.widgets.text.TextArea;
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

public class Lesson extends Screen {
    private final LuaLesson lesson;
    private float scroll = 0;

    public Lesson(LuaLesson lesson) {
        super(Text.of(Minecode.MOD_NAME));
        this.lesson = lesson;
    }

    @Override
    protected void init() {
        super.init();

        int barHeight = 2 * (RenderManager.instance().getTextFontRenderer().fontHeight + 10);
        int buttonSize = 2 * (int) (barHeight / 3f);

        addDrawableChild(new IconButton(5,  barHeight / 2 - buttonSize / 2,
                buttonSize, buttonSize, Text.of("Back"),
                button -> mc.setScreen(EditorLearnMenu.getInstance()),
                "back.png"
        ));
    }

    @Override
    public void renderBackground(MatrixStack matrices) {

        // Render background
        RenderManager.instance().getRenderer().box(matrices, 0, 0, width, height, Theme.DEFAULT.getBackground2());
    }

    @Override
    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        renderBackground(matrices);

        float barHeight = 2 * (RenderManager.instance().getTextFontRenderer().fontHeight + 10);

        scroll = Math.min(scroll, 0);

        float drawX = 10;
        float drawY = 10 + 2 * (RenderManager.instance().getTextFontRenderer().fontHeight + 10) + scroll;
        float drawWidth = width - drawX * 2;
        float drawHeight = 0;

        matrices.push();
        drawHeight = RenderManager.instance().getTextFontRenderer().fontHeight + 10;

        RenderManager.instance().getRenderer().box(matrices, drawX, drawY, drawWidth, drawHeight, Theme.DEFAULT.getBackground1());
        RenderManager.instance().getRenderer().box(matrices, 0, barHeight - 2, width, 2, Theme.DEFAULT.getAccent());

        RenderManager.instance().getTextFontRenderer().draw(matrices, lesson.getDescription(),
                (width / 2f) - RenderManager.instance().getTextFontRenderer().getWidth(lesson.getDescription()) / 2f,
                drawY + 5,
                Theme.DEFAULT.getFont().getRGB()
        );
        matrices.pop();

        drawY += drawHeight + 10;

        for (LuaLesson.LessonContent content : lesson.getContent()) {
            matrices.push();
            drawHeight = RenderManager.instance().getTextFontRenderer().fontHeight + 10;

            RenderManager.instance().getRenderer().box(matrices, drawX, drawY, drawWidth, drawHeight, Theme.DEFAULT.getBackground1());

            RenderManager.instance().getTextFontRenderer().draw(matrices, content.getText(),
                    (width / 2f) - RenderManager.instance().getTextFontRenderer().getWidth(content.getText()) / 2f,
                    drawY + 5,
                    Theme.DEFAULT.getFont().getRGB()
            );

            drawY += drawHeight;

            float codeBoxHeight = drawHeight;
            drawHeight = drawHeight + 4;

            RenderManager.instance().getRenderer().box(matrices, drawX, drawY, drawWidth, drawHeight, Theme.DEFAULT.getBackground1());
            RenderManager.instance().getRenderer().box(matrices, drawX + 2, drawY + 2, drawWidth - 4, codeBoxHeight, Theme.DEFAULT.getButton());

            RenderManager.instance().getCodeFontRenderer().draw(matrices, content.getCode(),
                    (width / 2f) - RenderManager.instance().getCodeFontRenderer().getWidth(content.getCode()) / 2f,
                    drawY + 5,
                    Theme.DEFAULT.getFont().getRGB()
            );

            matrices.pop();

            drawY += drawHeight + 10;
        }

        // Render bars
        RenderManager.instance().getRenderer().box(matrices, 0, 0, width, barHeight, Theme.DEFAULT.getBackground1());

        // Render instructions
        matrices.push();
        RenderManager.instance().getTextFontRenderer().draw(matrices, lesson.getName(),
                (width / 2f) - RenderManager.instance().getTextFontRenderer().getWidth(lesson.getName()) / 2f,
                (RenderManager.instance().getTextFontRenderer().fontHeight + 5),
                Theme.DEFAULT.getFont().getRGB()
        );
        matrices.pop();

        super.render(matrices, mouseX, mouseY, delta);
    }

    @Override
    public boolean mouseScrolled(double mouseX, double mouseY, double amount) {
        if (mouseY > 2 * (RenderManager.instance().getTextFontRenderer().fontHeight + 10)) {
            scroll += amount;
        }

        return super.mouseScrolled(mouseX, mouseY, amount);
    }
}
