package com.github.jx4e.minecode.rendering.screens;

import com.github.jx4e.minecode.Minecode;
import com.github.jx4e.minecode.rendering.widgets.buttons.IconTextButton;
import com.github.jx4e.minecode.rendering.theme.Theme;
import com.github.jx4e.minecode.rendering.RenderManager;
import com.github.jx4e.minecode.rendering.ResourceManager;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.texture.NativeImageBackedTexture;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

import static com.github.jx4e.minecode.MinecodeClient.mc;

/**
 * @author Jake (github.com/jx4e)
 * @since 11/06/2022
 **/

public class EditorMainMenu extends Screen {
    public EditorMainMenu() {
        super(Text.of(Minecode.MOD_NAME));
    }

    @Override
    protected void init() {
        super.init();

        int buttonWidth = width / 2 - 20;
        int buttonHeight = RenderManager.instance().getTextFontRenderer().fontHeight * 2;
        int buttonX = width / 2 + 10;
        
        addDrawableChild(new IconTextButton(buttonX, 50, buttonWidth, buttonHeight, 
                Text.of("Open a " + Formatting.BOLD + "Project"),
                button -> mc.setScreen(EditorProjectMenu.getInstance()),
                "folder.png"
        ));

        addDrawableChild(new IconTextButton(buttonX, 50 + buttonHeight + 2, buttonWidth, buttonHeight,
                Text.of("Choose a " + Formatting.BOLD + "Lesson"),
                button -> mc.setScreen(EditorLearnMenu.getInstance()),
                "learn.png"
        ));
        
        addDrawableChild(new IconTextButton(buttonX, 50 + 2 * buttonHeight + 4, buttonWidth, buttonHeight,
                Text.of("Configure " + Formatting.BOLD + "Settings"),
                button -> mc.setScreen(EditorLearnMenu.getInstance()),
                "settings.png"
        ));
    }

    @Override
    public void renderBackground(MatrixStack matrices) {
        // Render background
        RenderManager.instance().getRenderer().box(matrices, 0, 0, width / 2f, height, Theme.DEFAULT.getBackground1());
        RenderManager.instance().getRenderer().box(matrices, width / 2f, 0, width / 2f, height, Theme.DEFAULT.getBackground2());
    }

    @Override
    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        renderBackground(matrices);

        // Render Logo
        NativeImageBackedTexture logoTexture = ResourceManager.instance().getNativeImageTexture("logo.png");
        RenderManager.instance().getRenderer().image(matrices, logoTexture.getGlId(), 0, RenderManager.instance().getTextFontRenderer().fontHeight + 5, width / 2f, width / 8f);
        logoTexture.close();

        // Render instructions
        matrices.push();
        RenderManager.instance().getTextFontRenderer().draw(matrices, "Please select an " + Formatting.BOLD + "Option" + Formatting.RESET + " or press " + Formatting.BOLD + "ESC" + Formatting.RESET + " to exit.",
                (3 * width / 4f) - RenderManager.instance().getTextFontRenderer().getWidth("Please select an " + Formatting.BOLD + "Option" + Formatting.RESET + " or press " + Formatting.BOLD + "ESC" + Formatting.RESET + " to exit.") / 2f,
                (RenderManager.instance().getTextFontRenderer().fontHeight + 5),
                Theme.DEFAULT.getFont().getRGB()
        );
        matrices.pop();

        super.render(matrices, mouseX, mouseY, delta);
    }

    private static EditorMainMenu instance;

    public static EditorMainMenu getInstance() {
        if (instance == null) instance = new EditorMainMenu();

        return instance;
    }
}
