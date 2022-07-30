package com.github.jx4e.minecode.impl.ui.editor;

import com.github.jx4e.minecode.Minecode;
import com.github.jx4e.minecode.api.ui.AbstractPane;
import com.github.jx4e.minecode.api.ui.button.IconButton;
import com.github.jx4e.minecode.api.ui.theme.Theme;
import com.github.jx4e.minecode.impl.manager.RenderManager;
import com.github.jx4e.minecode.impl.manager.ResourceManager;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.texture.NativeImageBackedTexture;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author Jake (github.com/jx4e)
 * @since 11/06/2022
 **/

public class EditorMainMenu extends Screen {
    private List<AbstractPane> buttons = new ArrayList<>();
    private Theme activeTheme = new Theme();

    public EditorMainMenu() {
        super(Text.of(Minecode.MOD_NAME));
    }

    @Override
    protected void init() {
        super.init();
        buttons.clear();

        IconButton createProjectButton = new IconButton(0, 0, 0, 0, activeTheme, "New Project", "add.png");
        buttons.add(createProjectButton);

        IconButton openProjectButton = new IconButton(0, 0, 0, 0, activeTheme, "Open Project", "folder.png");
        buttons.add(openProjectButton);

        IconButton learnButton = new IconButton(0, 0, 0, 0, activeTheme, "Learn", "learn.png");
        buttons.add(learnButton);
    }

    @Override
    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        super.render(matrices, mouseX, mouseY, delta);

        // Render background
        NativeImageBackedTexture backgroundTexture = ResourceManager.instance().getNativeImageTexture("background.png");
        RenderManager.instance().getRenderer().image(matrices, backgroundTexture.getGlId(), 0, 0, width, height);
        backgroundTexture.close();

        // Render menu background
        RenderManager.instance().getRenderer().box(matrices,
                width / 2f - RenderManager.instance().getTextFontRenderer().getWidth("Welcome to Minecode Editor!") / 2f - 2,
                0,
                RenderManager.instance().getTextFontRenderer().getWidth("Welcome to Minecode Editor!") + 4,
                height,
                activeTheme.getBackground1()
        );

        // Render welcome message
        RenderManager.instance().getTextFontRenderer().draw(matrices, "Welcome to Minecode Editor!",
                width / 2f - RenderManager.instance().getTextFontRenderer().getWidth("Welcome to Minecode Editor!") / 2f,
                2,
                activeTheme.getFont().getRGB()
        );

        // Render instructions
        matrices.push();
        float scaling = 0.75f;
        matrices.scale(scaling, scaling, scaling);
        RenderManager.instance().getTextFontRenderer().draw(matrices, "Please select an option",
                (width / 2f) * (1f / scaling) - RenderManager.instance().getTextFontRenderer().getWidth("Please select an option") / 2f,
                (RenderManager.instance().getTextFontRenderer().fontHeight + 5) * (1f / scaling),
                activeTheme.getFont().getRGB()
        );
        matrices.pop();

        // Render Buttons
        matrices.push();
        int buttonWidth = RenderManager.instance().getTextFontRenderer().getWidth("Welcome to Minecode Editor!");
        int buttonHeight = RenderManager.instance().getTextFontRenderer().fontHeight * 2;
        int buttonX = (int) (width / 2f - RenderManager.instance().getTextFontRenderer().getWidth("Welcome to Minecode Editor!") / 2f);
        AtomicInteger buttonY = new AtomicInteger(50);
        buttons.forEach(button -> {
            button.setX(buttonX);
            button.setY(buttonY.get());
            button.setWidth(buttonWidth);
            button.setHeight(buttonHeight);
            button.draw(matrices, mouseX, mouseY);
            buttonY.addAndGet(buttonHeight);
        });
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

    private static EditorMainMenu instance;

    public static EditorMainMenu getInstance() {
        if (instance == null) instance = new EditorMainMenu();

        return instance;
    }
}
