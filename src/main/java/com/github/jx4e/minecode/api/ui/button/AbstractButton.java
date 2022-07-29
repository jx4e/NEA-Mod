package com.github.jx4e.minecode.api.ui.button;

import com.github.jx4e.minecode.api.ui.AbstractPane;
import com.github.jx4e.minecode.util.render.style.BoxColorScheme;

public abstract class AbstractButton extends AbstractPane {
    public AbstractButton(int x, int y, int width, int height, BoxColorScheme colorScheme) {
        super(x, y, width, height, colorScheme);
    }

    @Override
    public void mouseClicked(double mouseX, double mouseY, int mouseButton) {
        super.mouseClicked(mouseX, mouseY, mouseButton);

        if (mouseOver(mouseX, mouseY, getX(), getY(), getX() + getWidth(), getY() + getHeight())) {
            if (mouseButton == 0) onLeftClick();
            if (mouseButton == 1) onRightClick();
        }
    }

    public void onLeftClick() {}

    public void onRightClick() {}
}
