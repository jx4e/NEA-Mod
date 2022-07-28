package com.github.jx4e.minecode.impl.ui.editor.panes;

import com.github.jx4e.minecode.util.render.style.BoxColorScheme;
import com.github.jx4e.minecode.api.ui.AbstractPane;
import com.github.jx4e.minecode.impl.manager.RenderManager;
import net.minecraft.client.util.math.MatrixStack;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.util.LinkedList;
import java.util.List;

/**
 * @author Jake (github.com/jx4e)
 * @since 23/06/2022
 **/

public class SelectionPanel extends AbstractPane {
    private List<Selection> selections = new LinkedList<>();
    private Selection selected;

    public SelectionPanel(int x, int y, int width, int height, BoxColorScheme colorScheme) {
        super(x, y, width, height, colorScheme);
    }

    public void addSelection(@NotNull Selection selection) {
        selections.add(selection);

        if (selected == null) {
            selected = selection;
        }
    }

    @Override
    public void draw(MatrixStack matrices, int mouseX, int mouseY) {
        float divisionSize = (float) getWidth() / (float) Math.max(selections.size(), 3);
        int drawX = getX();

        for (Selection selection : selections) {
            if (selected == selection) {
                RenderManager.instance().getRenderer().box(matrices, getX(), getY(), (int) divisionSize, getHeight(), getColorScheme(), null);
            }

            RenderManager.instance().getCodeFontRenderer().draw(matrices, selection.title,
                    drawX + divisionSize / 2f - RenderManager.instance().getCodeFontRenderer().getWidth(selection.title) / 2f,
                    getY() + RenderManager.instance().getCodeFontRenderer().fontHeight / 2f,
                    Color.WHITE.getRGB()
            );

            drawX += divisionSize;
        }
    }

    public static class Selection {
        private String title;
        private Runnable task;

        public Selection(String title, Runnable task) {
            this.title = title;
            this.task = task;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public Runnable getTask() {
            return task;
        }

        public void setTask(Runnable task) {
            this.task = task;
        }
    }
}