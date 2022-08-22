package com.github.jx4e.minecode.api.ui.theme;

import java.awt.*;

public class Theme {
    private Color accent = new Color(0x168440);
    private Color background1 = new Color(37,40,49,255);
    private Color background2 = new Color(49,51,64,255);

    private Color button = new Color(60, 62, 79,255);
    private Color font = new Color(0xFFFFFF);

    public Color getAccent() {
        return accent;
    }

    public Theme setAccent(Color accent) {
        this.accent = accent;
        return this;
    }

    public Color getBackground1() {
        return background1;
    }

    public Theme setBackground1(Color background1) {
        this.background1 = background1;
        return this;
    }

    public Color getBackground2() {
        return background2;
    }

    public Theme setBackground2(Color background2) {
        this.background2 = background2;
        return this;
    }

    public Color getButton() {
        return button;
    }

    public Theme setButton(Color button) {
        this.button = button;
        return this;
    }

    public Color getFont() {
        return font;
    }

    public Theme setFont(Color font) {
        this.font = font;
        return this;
    }
}
