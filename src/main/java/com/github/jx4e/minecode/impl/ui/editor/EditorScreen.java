package com.github.jx4e.minecode.impl.ui.editor;

import com.github.jx4e.minecode.Minecode;
import com.github.jx4e.minecode.api.ui.button.IconButton;
import com.github.jx4e.minecode.api.ui.button.SimpleButton;
import com.github.jx4e.minecode.impl.manager.ResourceManager;
import com.github.jx4e.minecode.util.render.style.BoxColorScheme;
import com.github.jx4e.minecode.impl.manager.RenderManager;
import com.github.jx4e.minecode.impl.ui.editor.panes.SelectionPanel;
import com.github.jx4e.minecode.impl.ui.editor.panes.EditorPanel;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.report.ChatReportScreen;
import net.minecraft.client.texture.NativeImageBackedTexture;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;

import java.awt.*;

/**
 * @author Jake (github.com/jx4e)
 * @since 11/06/2022
 **/

public class EditorScreen extends Screen {
    private BoxColorScheme accent1;
    private BoxColorScheme accent2;
    private EditorPanel pane;
    private SelectionPanel bar;

    public EditorScreen() {
        super(Text.of(Minecode.MOD_NAME));
    }

    @Override
    protected void init() {
        super.init();
        accent1 = new BoxColorScheme.Flat(new Color(49,51,64,255));
        accent2 = new BoxColorScheme.Flat(new Color(37,40,49,255));
        bar = new SelectionPanel(200, 0, width - 200, 20, accent1);
        pane = new EditorPanel(200, 20, width - 200, height - 20, accent1);

        bar.addSelection(new SelectionPanel.Selection("Task", () -> {}));
    }

    @Override
    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        super.render(matrices, mouseX, mouseY, delta);

        RenderManager.instance().getRenderer().box(matrices, 0, 0, width, height, accent2, null);

        IconButton button = new IconButton(0, 100, 100, 25, accent1);
        button.draw(matrices, mouseX, mouseY);

        RenderManager.instance().getTextFontRenderer().draw(matrices, Minecode.MOD_NAME + " Editor", 2, 2, Color.WHITE.getRGB());

        // Draw the code pane
        matrices.push();
        bar.draw(matrices, mouseX, mouseY);
        pane.draw(matrices, mouseX, mouseY);
        matrices.pop();
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int mouseButton) {
        pane.mouseClicked(mouseX, mouseY, mouseButton);

        return super.mouseClicked(mouseX, mouseY, mouseButton);
    }

    @Override
    public boolean mouseReleased(double mouseX, double mouseY, int button) {
        pane.mouseReleased(mouseX, mouseY, button);

        return super.mouseReleased(mouseX, mouseY, button);
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        pane.keyPressed(keyCode, scanCode, modifiers);

        return super.keyPressed(keyCode, scanCode, modifiers);
    }

    @Override
    public boolean mouseScrolled(double mouseX, double mouseY, double amount) {
        pane.mouseScrolled(mouseX, mouseY, amount);

        return super.mouseScrolled(mouseX, mouseY, amount);
    }

    @Override
    public boolean charTyped(char chr, int modifiers) {
        pane.charTyped(chr, modifiers);

        return super.charTyped(chr, modifiers);
    }

    private static EditorScreen instance;

    public static EditorScreen getInstance() {
        if (instance == null) instance = new EditorScreen();

        return instance;
    }
}
