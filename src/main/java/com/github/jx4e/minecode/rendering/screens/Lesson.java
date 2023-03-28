package com.github.jx4e.minecode.rendering.screens;

import com.github.jx4e.minecode.Minecode;
import com.github.jx4e.minecode.project.LuaLesson;
import com.github.jx4e.minecode.rendering.RenderManager;
import com.github.jx4e.minecode.rendering.theme.Theme;
import com.github.jx4e.minecode.rendering.widgets.buttons.IconButton;
import com.github.jx4e.minecode.rendering.widgets.text.TextArea;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;

import java.util.HashMap;
import java.util.Map;

import static com.github.jx4e.minecode.MinecodeClient.mc;

/**
 * @author Jake (github.com/jx4e)
 * @since 11/06/2022
 **/

public class Lesson extends Screen {
    private final LuaLesson lesson;
    private TextArea area;
    private IconButton run, submit;
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

        // We do not want to add this to the children since it will be rendered when super.render() is called
        // Instead we will draw it ourselves manually.
        addDrawableChild(
                area = new TextArea(width / 5, barHeight, 4 * width / 5, height - barHeight,
                        lesson.getMainScriptFile(), this)
        );

        addDrawableChild(run = new IconButton(5, height - barHeight + buttonSize / 3,
                buttonSize, buttonSize, Text.of("Run"),
                button -> {
                    area.getDocument().save();
                    lesson.getScript().toggle();
                },
                "run.png"
        ));

        addDrawableChild(submit = new IconButton(5, height - barHeight + buttonSize / 3,
                buttonSize, buttonSize, Text.of("Check"),
                button -> {
                    Map<LuaLesson.LessonContent, Boolean> taskStatus = new HashMap<>();
                    String code = area.getDocument().getContent();

                    // Iterate through the tasks
                    for (LuaLesson.LessonContent task : lesson.getTasks()) {
                        // If the users code contains the correct code
                        boolean completed = code.contains(task.code());
                        // Add the task and if it was completed to our map
                        taskStatus.put(task, completed);
                    }

                    // display
                    mc.setScreen(new LessonSummary(this, taskStatus));
                },
                "tick.png"
        ));
    }

    @Override
    public void renderBackground(MatrixStack matrices) {
        // Render background
        RenderManager.instance().getRenderer().box(matrices, 0, 0, width, height, Theme.DEFAULT.getBackground2());
    }

    @Override
    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        // Draw the background
        renderBackground(matrices);

        float barHeight = 2 * (RenderManager.instance().getTextFontRenderer().fontHeight + 10);

        // Calculate the scroll amount
        scroll = Math.min(scroll, 0);

        float drawX = 10;
        float drawY = 10 + 2 * (RenderManager.instance().getTextFontRenderer().fontHeight + 10) + scroll;
        float drawWidth = width - drawX * 2;
        float drawHeight = 0;

        // Draw the lesson description
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

        // Move our draw pointer so it draws lower down the page
        drawY += drawHeight + 10;

        // Draw the lesson content
        for (LuaLesson.LessonContent content : lesson.getContent()) {
            matrices.push();
            drawHeight = RenderManager.instance().getTextFontRenderer().fontHeight + 10;

            RenderManager.instance().getRenderer().box(matrices, drawX, drawY, drawWidth, drawHeight, Theme.DEFAULT.getBackground1());

            RenderManager.instance().getTextFontRenderer().draw(matrices, content.text(),
                    (width / 2f) - RenderManager.instance().getTextFontRenderer().getWidth(content.text()) / 2f,
                    drawY + 5,
                    Theme.DEFAULT.getFont().getRGB()
            );

            drawY += drawHeight;

            float codeBoxHeight = drawHeight;
            drawHeight = drawHeight + 4;

            RenderManager.instance().getRenderer().box(matrices, drawX, drawY, drawWidth, drawHeight, Theme.DEFAULT.getBackground1());
            RenderManager.instance().getRenderer().box(matrices, drawX + 2, drawY + 2, drawWidth - 4, codeBoxHeight, Theme.DEFAULT.getButton());

            RenderManager.instance().getCodeFontRenderer().draw(matrices, content.code(),
                    (width / 2f) - RenderManager.instance().getCodeFontRenderer().getWidth(content.code()) / 2f,
                    drawY + 5,
                    Theme.DEFAULT.getFont().getRGB()
            );

            matrices.pop();

            drawY += drawHeight + 10;
        }

        // Draw the tasks title
        RenderManager.instance().getTextFontRenderer().draw(matrices, "Task: Create a program that:",
                (width / 2f) - RenderManager.instance().getTextFontRenderer().getWidth("Task: Create a program that:") / 2f,
                drawY + 5,
                Theme.DEFAULT.getFont().getRGB()
        );

        drawY += RenderManager.instance().getTextFontRenderer().fontHeight + 10;

        // Draw the lesson criteria
        for (LuaLesson.LessonContent content : lesson.getTasks()) {
            matrices.push();
            drawHeight = RenderManager.instance().getTextFontRenderer().fontHeight + 10;

            RenderManager.instance().getRenderer().box(matrices, drawX, drawY, drawWidth, drawHeight, Theme.DEFAULT.getBackground1());

            RenderManager.instance().getTextFontRenderer().draw(matrices, content.text(),
                    (width / 2f) - RenderManager.instance().getTextFontRenderer().getWidth(content.text()) / 2f,
                    drawY + 5,
                    Theme.DEFAULT.getFont().getRGB()
            );

            drawY += drawHeight + 10;
            matrices.pop();
        }

        // Draw the code editing area
        RenderManager.instance().getRenderer().box(matrices, drawX, drawY, drawWidth, area.getHeight() + 22, Theme.DEFAULT.getBackground1());

        run.x = (int) drawX + 2;
        run.y = (int) drawY + 2;
        run.setWidth(16);

        submit.x = (int) drawX + 20;
        submit.y = (int) drawY + 2;
        submit.setWidth(16);

        drawY += 20;

        area.x = (int) drawX + 2;
        area.y = (int) drawY;
        area.setWidth((int) drawWidth - 4);

        // Finally render the stuff that should be overlayed
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
