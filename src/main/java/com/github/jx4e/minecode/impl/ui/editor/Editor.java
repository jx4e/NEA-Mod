package com.github.jx4e.minecode.impl.ui.editor;

import com.github.jx4e.minecode.Minecode;
import com.github.jx4e.minecode.api.project.LuaProject;
import com.github.jx4e.minecode.api.ui.AbstractPane;
import com.github.jx4e.minecode.api.ui.button.IconButton;
import com.github.jx4e.minecode.api.ui.button.IconTextButton;
import com.github.jx4e.minecode.api.ui.panes.EditorPanel;
import com.github.jx4e.minecode.api.ui.theme.Theme;
import com.github.jx4e.minecode.impl.manager.ProjectManager;
import com.github.jx4e.minecode.impl.manager.RenderManager;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import static com.github.jx4e.minecode.MinecodeClient.mc;

/**
 * @author Jake (github.com/jx4e)
 * @since 11/06/2022
 **/

public class Editor extends Screen {
    private List<AbstractPane> buttons = new ArrayList<>();

    private Theme activeTheme = new Theme();

    private IconButton backButton;

    private IconButton addButton;

    private LuaProject project;

    private EditorPanel panel;

    public Editor(LuaProject project) {
        super(Text.of(Minecode.MOD_NAME));
        this.project = project;
    }

    @Override
    protected void init() {
        super.init();
        buttons.clear();

        backButton = new IconButton(0, 0, 0, 0, activeTheme, "back.png") {
            @Override
            public void onLeftClick() {
                super.onLeftClick();
                mc.setScreen(EditorMainMenu.getInstance());
            }
        };
        buttons.add(backButton);

        addButton = new IconButton(0, 0, 0, 0, activeTheme, "add.png") {
            @Override
            public void onLeftClick() {
                super.onLeftClick();
                mc.setScreen(EditorCreateProjectMenu.getInstance());
            }
        };
        buttons.add(addButton);

        panel = new EditorPanel(0, 0, 0, 0, activeTheme);
        buttons.add(panel);
    }

    @Override
    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        super.render(matrices, mouseX, mouseY, delta);

        float barHeight = 2 * (RenderManager.instance().getTextFontRenderer().fontHeight + 10);

        // Render background
        RenderManager.instance().getRenderer().box(matrices, 0, 0, width, height, activeTheme.getBackground2());

        // Render bars
        RenderManager.instance().getRenderer().box(matrices, 0, 0, width, barHeight, activeTheme.getBackground1());
        RenderManager.instance().getRenderer().box(matrices, 0, height - barHeight, width, barHeight, activeTheme.getBackground1());

        // Render Back button
        backButton.setX(5);
        backButton.setY((int) (barHeight / 2 - (2 * (int) (barHeight / 3f)) / 2));
        backButton.setWidth(2 * (int) (barHeight / 3f));
        backButton.setHeight(2 * (int) (barHeight / 3f));
        backButton.draw(matrices, mouseX, mouseY);

        // Render Add button
        addButton.setY((int) (barHeight / 2 - (2 * (int) (barHeight / 3f)) / 2));
        addButton.setWidth(2 * (int) (barHeight / 3f));
        addButton.setHeight(2 * (int) (barHeight / 3f));
        addButton.setX(width - addButton.getWidth() - 5);
        addButton.draw(matrices, mouseX, mouseY);

        // Render instructions
        matrices.push();
        RenderManager.instance().getTextFontRenderer().draw(matrices, project.getName(),
                (width / 2f) - RenderManager.instance().getTextFontRenderer().getWidth(project.getName()) / 2f,
                (RenderManager.instance().getTextFontRenderer().fontHeight + 5),
                activeTheme.getFont().getRGB()
        );
        matrices.pop();

        matrices.push();
        panel.setX(width / 5);
        panel.setY((int) barHeight);
        panel.setWidth(width);
        panel.setHeight((int) (height - barHeight * 2));
        panel.draw(matrices, mouseX, mouseY);
        matrices.pop();
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int mouseButton) {
        buttons.forEach(abstractPane -> abstractPane.mouseClicked(mouseX, mouseY, mouseButton));

        return super.mouseClicked(mouseX, mouseY, mouseButton);
    }

    @Override
    public boolean mouseReleased(double mouseX, double mouseY, int button) {
        buttons.forEach(abstractPane -> abstractPane.mouseReleased(mouseX, mouseY, button));

        return super.mouseReleased(mouseX, mouseY, button);
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        buttons.forEach(abstractPane -> abstractPane.keyPressed(keyCode, scanCode, modifiers));

        return super.keyPressed(keyCode, scanCode, modifiers);
    }

    @Override
    public boolean mouseScrolled(double mouseX, double mouseY, double amount) {
        buttons.forEach(abstractPane -> abstractPane.mouseScrolled(mouseX, mouseY, amount));

        return super.mouseScrolled(mouseX, mouseY, amount);
    }

    @Override
    public boolean charTyped(char chr, int modifiers) {
        buttons.forEach(abstractPane -> abstractPane.charTyped(chr, modifiers));

        return super.charTyped(chr, modifiers);
    }
}
