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
    private List<AbstractPane> buttons = new ArrayList<>();

    private List<AbstractPane> projectButtons = new ArrayList<>();

    private Theme activeTheme = new Theme();

    private IconButton backButton;

    private IconButton createButton;

    private TextEntryButton projectNameButton;

    private TextEntryButton mainScriptButton;

    public EditorCreateProjectMenu() {
        super(Text.of(Minecode.MOD_NAME));
    }

    @Override
    protected void init() {
        super.init();
        buttons.clear();
        projectButtons.clear();

        backButton = new IconButton(0, 0, 0, 0, activeTheme, "back.png") {
            @Override
            public void onLeftClick() {
                super.onLeftClick();
                mc.setScreen(EditorProjectMenu.getInstance());
            }
        };
        buttons.add(backButton);

        createButton = new IconButton(0, 0, 0, 0, activeTheme, "checked.png") {
            @Override
            public void onLeftClick() {
                super.onLeftClick();
                ProjectManager.instance().createProject(projectNameButton.getText(), mainScriptButton.getText());
                mc.setScreen(EditorProjectMenu.getInstance());
            }
        };
        buttons.add(createButton);


        projectNameButton = new TextEntryButton(0, 0, 0, 0, activeTheme, "Untitled Project");
        projectButtons.add(projectNameButton);

        mainScriptButton = new TextEntryButton(0, 0, 0, 0, activeTheme, "main.lua");
        projectButtons.add(mainScriptButton);
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

        // Render create button
        createButton.setY((int) (barHeight / 2 - (2 * (int) (barHeight / 3f)) / 2));
        createButton.setWidth(2 * (int) (barHeight / 3f));
        createButton.setHeight(2 * (int) (barHeight / 3f));
        createButton.setX(width - createButton.getWidth() - 5);
        createButton.draw(matrices, mouseX, mouseY);

        // Render instructions
        matrices.push();
        RenderManager.instance().getTextFontRenderer().draw(matrices, "Create a new " + Formatting.BOLD + "Project",
                (width / 2f) - RenderManager.instance().getTextFontRenderer().getWidth("Create a new " + Formatting.BOLD + "Project") / 2f,
                (RenderManager.instance().getTextFontRenderer().fontHeight + 5),
                activeTheme.getFont().getRGB()
        );
        matrices.pop();

        // Render options
        int buttonHeight = RenderManager.instance().getTextFontRenderer().fontHeight * 2;
        int buttonY = 50;

        matrices.push();
        RenderManager.instance().getTextFontRenderer().draw(matrices, "Project Name",
                10,
                buttonY + RenderManager.instance().getTextFontRenderer().fontHeight / 2f,
                activeTheme.getFont().getRGB()
        );

        projectNameButton.setX(100);
        projectNameButton.setY(buttonY);
        projectNameButton.setWidth(width - 110);
        projectNameButton.setHeight(buttonHeight);
        projectNameButton.draw(matrices, mouseX, mouseY);
        buttonY += projectNameButton.getHeight() + 2;
        matrices.pop();

        matrices.push();
        RenderManager.instance().getTextFontRenderer().draw(matrices, "Main Script",
                10,
                buttonY + RenderManager.instance().getTextFontRenderer().fontHeight / 2f,
                activeTheme.getFont().getRGB()
        );

        mainScriptButton.setX(100);
        mainScriptButton.setY(buttonY);
        mainScriptButton.setWidth(width - 110);
        mainScriptButton.setHeight(buttonHeight);
        mainScriptButton.draw(matrices, mouseX, mouseY);
        buttonY += mainScriptButton.getHeight() + 2;
        matrices.pop();
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int mouseButton) {
        buttons.forEach(abstractPane -> abstractPane.mouseClicked(mouseX, mouseY, mouseButton));
        projectButtons.forEach(abstractPane -> abstractPane.mouseClicked(mouseX, mouseY, mouseButton));

        return super.mouseClicked(mouseX, mouseY, mouseButton);
    }

    @Override
    public boolean mouseReleased(double mouseX, double mouseY, int button) {
        buttons.forEach(abstractPane -> abstractPane.mouseReleased(mouseX, mouseY, button));
        projectButtons.forEach(abstractPane -> abstractPane.mouseReleased(mouseX, mouseY, button));

        return super.mouseReleased(mouseX, mouseY, button);
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        buttons.forEach(abstractPane -> abstractPane.keyPressed(keyCode, scanCode, modifiers));
        projectButtons.forEach(abstractPane -> abstractPane.keyPressed(keyCode, scanCode, modifiers));

        return super.keyPressed(keyCode, scanCode, modifiers);
    }

    @Override
    public boolean mouseScrolled(double mouseX, double mouseY, double amount) {
        buttons.forEach(abstractPane -> abstractPane.mouseScrolled(mouseX, mouseY, amount));
        projectButtons.forEach(abstractPane -> abstractPane.mouseScrolled(mouseX, mouseY, amount));

        return super.mouseScrolled(mouseX, mouseY, amount);
    }

    @Override
    public boolean charTyped(char chr, int modifiers) {
        buttons.forEach(abstractPane -> abstractPane.charTyped(chr, modifiers));
        projectButtons.forEach(abstractPane -> abstractPane.charTyped(chr, modifiers));

        return super.charTyped(chr, modifiers);
    }

    private static EditorCreateProjectMenu instance;

    public static EditorCreateProjectMenu getInstance() {
        if (instance == null) instance = new EditorCreateProjectMenu();

        return instance;
    }
}
