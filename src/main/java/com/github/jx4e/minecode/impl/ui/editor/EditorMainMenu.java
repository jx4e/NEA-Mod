package com.github.jx4e.minecode.impl.ui.editor;

import com.github.jx4e.minecode.Minecode;
import com.github.jx4e.minecode.api.ui.AbstractPane;
import com.github.jx4e.minecode.api.ui.button.IconTextButton;
import com.github.jx4e.minecode.api.ui.theme.Theme;
import com.github.jx4e.minecode.impl.manager.RenderManager;
import com.github.jx4e.minecode.impl.manager.ResourceManager;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.texture.NativeImageBackedTexture;
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

        IconTextButton projectButton = new IconTextButton(0, 0, 0, 0, activeTheme, "Open a " + Formatting.BOLD + "Project", "folder.png") {
            @Override
            public void onLeftClick() {
                super.onLeftClick();
                mc.setScreen(EditorProjectMenu.getInstance());
            }
        };
        buttons.add(projectButton);

        IconTextButton learnButton = new IconTextButton(0, 0, 0, 0, activeTheme, "Choose a " + Formatting.BOLD + "Lesson", "learn.png") {
            @Override
            public void onLeftClick() {
                super.onLeftClick();
                mc.setScreen(EditorLearnMenu.getInstance());
            }
        };
        buttons.add(learnButton);

        IconTextButton settingsButton = new IconTextButton(0, 0, 0, 0, activeTheme, "Configure " + Formatting.BOLD + "Settings", "settings.png");
        buttons.add(settingsButton);
    }

    @Override
    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        super.render(matrices, mouseX, mouseY, delta);

        // Render background
        RenderManager.instance().getRenderer().box(matrices, 0, 0, width / 2f, height, activeTheme.getBackground1());
        RenderManager.instance().getRenderer().box(matrices, width / 2f, 0, width / 2f, height, activeTheme.getBackground2());

        // Render Logo
        NativeImageBackedTexture logoTexture = ResourceManager.instance().getNativeImageTexture("logo.png");
        RenderManager.instance().getRenderer().image(matrices, logoTexture.getGlId(), 0, RenderManager.instance().getTextFontRenderer().fontHeight + 5, width / 2f, width / 8f);
        logoTexture.close();

        // Render instructions
        matrices.push();
        RenderManager.instance().getTextFontRenderer().draw(matrices, "Please select an " + Formatting.BOLD + "Option" + Formatting.RESET + " or press " + Formatting.BOLD + "ESC" + Formatting.RESET + " to exit.",
                (3 * width / 4f) - RenderManager.instance().getTextFontRenderer().getWidth("Please select an " + Formatting.BOLD + "Option" + Formatting.RESET + " or press " + Formatting.BOLD + "ESC" + Formatting.RESET + " to exit.") / 2f,
                (RenderManager.instance().getTextFontRenderer().fontHeight + 5),
                activeTheme.getFont().getRGB()
        );
        matrices.pop();

        // Render Buttons
        matrices.push();
        int buttonWidth = width / 2 - 20;
        int buttonHeight = RenderManager.instance().getTextFontRenderer().fontHeight * 2;
        int buttonX = width / 2 + 10;
        AtomicInteger buttonY = new AtomicInteger(50);
        buttons.forEach(button -> {
            button.setX(buttonX);
            button.setY(buttonY.get());
            button.setWidth(buttonWidth);
            button.setHeight(buttonHeight);
            button.draw(matrices, mouseX, mouseY);
            buttonY.addAndGet(buttonHeight + 2);
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
