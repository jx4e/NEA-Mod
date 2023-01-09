package com.github.jx4e.minecode.rendering.theme;

import java.awt.*;

public class Theme {
    private Color accent = new Color(0x138BEC);
    private Color background1 = new Color(30, 30, 30,255);
    private Color background2 = new Color(43, 43, 43,255);
    private Color background3 = new Color(50, 50, 50,255);
    private Color button = new Color(60, 63, 65,255);
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

    public Color getBackground3() {
        return background3;
    }

    public Theme setBackground3(Color background3) {
        this.background3 = background3;
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

    public static final Theme DEFAULT = new Theme();
}
