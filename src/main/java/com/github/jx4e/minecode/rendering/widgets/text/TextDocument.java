package com.github.jx4e.minecode.rendering.widgets.text;

import com.mojang.datafixers.util.Pair;
import net.minecraft.util.math.MathHelper;

import java.util.Arrays;
import java.util.LinkedList;

/**
 * @author Jake (github.com/jx4e)
 * @since 26/09/2022
 **/

public class TextDocument {
    private LinkedList<String> lines = new LinkedList<>();
    private Pair<Integer, Integer> pointer = new Pair<>(0, 0);

    public TextDocument(String content) {
        lines.addAll(Arrays.asList(content.split("\n")));
    }

    /**
     * Inserts a string into the doc
     * @param string
     */
    public void insert(String string) {
        StringBuilder lineContent = new StringBuilder(lines.get(pointer.getSecond()));
        lineContent.insert(pointer.getFirst(), string);
        lines.set(pointer.getSecond(), lineContent.toString());
        setPointer(pointer.getFirst() + string.length(), pointer.getSecond());
    }

    /**
     * Deletes the char before the pointer
     */
    public void backspace() {
        if (pointer.getFirst() == 0) {
            if (pointer.getSecond() == 0) return;
            setPointer(lines.get(pointer.getSecond() - 1).length() - 1, pointer.getSecond() - 1);
            StringBuilder lineContent = new StringBuilder(lines.get(pointer.getSecond()));
            lineContent.append(lines.get(pointer.getSecond() + 1));
            lines.set(pointer.getSecond(), lineContent.toString());
            lines.remove(pointer.getSecond() + 1);
            return;
        }

        StringBuilder lineContent = new StringBuilder(lines.get(pointer.getSecond()));
        lineContent.deleteCharAt(pointer.getFirst() - 1);
        lines.set(pointer.getSecond(), lineContent.toString());
        setPointer(pointer.getFirst() - 1, pointer.getSecond());
    }

    public void newLine() {
        String toMove = lines.get(pointer.getSecond()).substring(pointer.getFirst());
        lines.set(pointer.getSecond(), lines.get(pointer.getSecond()).substring(0, pointer.getFirst()));
        lines.add(pointer.getSecond() + 1, toMove);
        setPointer(0, pointer.getSecond() + 1);
    }

    public void pointerUp() {
        setPointer(pointer.getFirst(), pointer.getSecond() - 1);
    }

    public void pointerDown() {
        setPointer(pointer.getFirst(), pointer.getSecond() + 1);
    }

    public void pointerLeft() {
        setPointer(pointer.getFirst() - 1, pointer.getSecond());
    }

    public void pointerRight() {
        setPointer(pointer.getFirst() + 1, pointer.getSecond());
    }

    public void setPointer(int x, int y) {
        y = MathHelper.clamp(y, 0, lines.size() - 1);
        x = MathHelper.clamp(x, 0, lines.get(y).length());

        pointer = new Pair<>(x, y);
    }

    public Pair<Integer, Integer> getPointer() {
        return pointer;
    }

    public String getContent() {
        StringBuilder content = new StringBuilder();

        for (int i = 0; i < lines.size(); i++) {
            content.append(lines.get(i));

            if (i != lines.size() - 1) {
                content.append("\n");
            }
        }

        return content.toString();
    }

    public LinkedList<String> getLines() {
        return lines;
    }
}
