package com.github.jx4e.minecode.api.ui.button;

import com.github.jx4e.minecode.api.ui.AbstractPane;
import com.github.jx4e.minecode.api.ui.theme.Theme;

public abstract class AbstractButton extends AbstractPane {
    public AbstractButton(int x, int y, int width, int height, Theme theme) {
        super(x, y, width, height, theme);
    }

    @Override
    public void mouseClicked(double mouseX, double mouseY, int mouseButton) {
        super.mouseClicked(mouseX, mouseY, mouseButton);

        if (mouseOver(mouseX, mouseY, getX(), getY(), getX() + getWidth(), getY() + getHeight())) {
            if (mouseButton == 0) onLeftClick();
            if (mouseButton == 1) onRightClick();
        } else {
            if (mouseButton == 0) onLeftClickElsewhere();
            if (mouseButton == 1) onRightClickElsewhere();
        }
    }

    public void onLeftClick() {}

    public void onRightClick() {}

    public void onLeftClickElsewhere() {}

    public void onRightClickElsewhere() {}
}
