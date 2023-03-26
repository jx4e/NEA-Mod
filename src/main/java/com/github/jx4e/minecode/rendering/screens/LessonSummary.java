package com.github.jx4e.minecode.rendering.screens;

import com.github.jx4e.minecode.Minecode;
import com.github.jx4e.minecode.project.LuaLesson;
import com.github.jx4e.minecode.rendering.RenderManager;
import com.github.jx4e.minecode.rendering.Renderer;
import com.github.jx4e.minecode.rendering.theme.Theme;
import com.github.jx4e.minecode.rendering.widgets.buttons.IconButton;
import com.github.jx4e.minecode.rendering.widgets.text.TextArea;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

import static com.github.jx4e.minecode.MinecodeClient.mc;

/**
 * @author Jake (github.com/jx4e)
 * @since 11/06/2022
 **/

public class LessonSummary extends Screen {
    private Lesson parentScreen;
    private Map<LuaLesson.LessonContent, Boolean> taskStatus;

    public LessonSummary(Lesson parentScreen, Map<LuaLesson.LessonContent, Boolean> taskStatus) {
        super(Text.of(Minecode.MOD_NAME));
        this.parentScreen = parentScreen;
        this.taskStatus = taskStatus;
    }

    @Override
    protected void init() {
        int barHeight = 2 * (RenderManager.instance().getTextFontRenderer().fontHeight + 10);
        int buttonSize = 2 * (int) (barHeight / 3f);

        addDrawableChild(new IconButton(5,  barHeight / 2 - buttonSize / 2,
                buttonSize, buttonSize, Text.of("Back"),
                button -> mc.setScreen(parentScreen),
                "back.png"
        ));
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
        // Draw the background
        renderBackground(matrices);

        float barHeight = 2 * (RenderManager.instance().getTextFontRenderer().fontHeight + 10);

        // Render title
        matrices.push();
        RenderManager.instance().getTextFontRenderer().draw(matrices, "Lesson Summary",
                (width / 2f) - RenderManager.instance().getTextFontRenderer().getWidth("Lesson Summary") / 2f,
                (RenderManager.instance().getTextFontRenderer().fontHeight + 5),
                Theme.DEFAULT.getFont().getRGB()
        );
        matrices.pop();

        matrices.push();
        int score = (int) taskStatus.entrySet().stream()
                .map(entry -> entry.getValue())
                .filter(aBoolean -> aBoolean)
                .count();

        String scoreString = "You scored " + score + " out of " + taskStatus.size();

        float drawX = 10;
        float drawY = barHeight + 10;
        float drawWidth = width - drawX * 2;
        float drawHeight = RenderManager.instance().getTextFontRenderer().fontHeight + 8;

        RenderManager.instance().getRenderer().box(matrices, drawX, drawY, drawWidth, drawHeight, Theme.DEFAULT.getBackground1());

        RenderManager.instance().getTextFontRenderer().draw(matrices, scoreString,
                (width / 2f) - RenderManager.instance().getTextFontRenderer().getWidth(scoreString) / 2f,
                drawY + 4,
                getColor((float) score / (float) taskStatus.size()).getRGB()
        );
        matrices.pop();

        drawY += drawHeight;

        for (Map.Entry<LuaLesson.LessonContent, Boolean> entry : taskStatus.entrySet()) {

        }

        super.render(matrices, mouseX, mouseY, delta);
    }

    private Color getColor(float fraction) {
        if (fraction < 0.5) return new Color(0xEF4040);
        else  if (fraction < 0.8) return new Color(0xEF7D40);
        else return new Color(0x417C41);
    }
}
