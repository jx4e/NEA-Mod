package com.github.jx4e.minecode.rendering.widgets.text;

/**
 * @author Jake (github.com/jx4e)
 * @since 26/09/2022
 **/

public class OneLineDocument {
    private StringBuilder content;
    private int pointer = 0;

    public OneLineDocument(String content) {
        this.content = new StringBuilder(content);
    }

    /**
     * Inserts a string into the doc
     * @param string
     */
    public void insert(String string) {
        content.insert(pointer, string);
        setPointer(pointer + string.length());
    }

    /**
     * Deletes the char before the pointer
     */
    public void backspace() {
        if (pointer == 0) return;

        content.deleteCharAt(pointer - 1);
        setPointer(pointer - 1);
    }

    public void pointerLeft() {
        setPointer(pointer - 1);
    }

    public void pointerRight() {
        setPointer(pointer + 1);
    }

    public void setPointer(int x) {
        pointer = x;
    }

    public int getPointer() {
        return pointer;
    }

    public String getContent() {
        return content.toString();
    }
}
