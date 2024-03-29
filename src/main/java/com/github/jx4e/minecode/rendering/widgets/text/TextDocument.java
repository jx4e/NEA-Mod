package com.github.jx4e.minecode.rendering.widgets.text;

import com.github.jx4e.minecode.project.IOUtil;
import com.mojang.datafixers.util.Pair;
import net.minecraft.util.math.MathHelper;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.LinkedList;

/**
 * @author Jake (github.com/jx4e)
 * @since 26/09/2022
 **/

public class TextDocument {
    private final File editing;
    private final LinkedList<String> lines = new LinkedList<>();
    private Pair<Integer, Integer> pointer = new Pair<>(0, 0);

    public TextDocument(File editing) {
        this.editing = editing;
        lines.addAll(Arrays.asList(IOUtil.readFileToString(editing).split("\n")));
    }

    /**
     * Saves the document by writing the updated content to the file
     */
    public void save() {
        if (editing == null) return;

        try {
            IOUtil.writeToOutputStream(
                    new ByteArrayInputStream(getContent().getBytes(StandardCharsets.UTF_8)),
                    new FileOutputStream(editing)
            );
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
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
        // This is for going back a line
        if (pointer.getFirst() == 0) {
            // return if we are on the very first line since there's nothing to do
            if (pointer.getSecond() == 0) return;
            // move pointer to last line
            setPointer(lines.get(pointer.getSecond() - 1).length() - 1, pointer.getSecond() - 1);
            // Add the line to the previous line
            StringBuilder lineContent = new StringBuilder(lines.get(pointer.getSecond()));
            lineContent.append(lines.get(pointer.getSecond() + 1));
            // update
            lines.set(pointer.getSecond(), lineContent.toString());
            // Remove old lne
            lines.remove(pointer.getSecond() + 1);
            return;
        }

        // Delete the previous character
        StringBuilder lineContent = new StringBuilder(lines.get(pointer.getSecond()));
        lineContent.deleteCharAt(pointer.getFirst() - 1);
        lines.set(pointer.getSecond(), lineContent.toString());
        setPointer(pointer.getFirst() - 1, pointer.getSecond());
    }

    /**
     * Adds a new line
     */
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
        // Validate the inputs so that they are within the right range
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

    public File getEditing() {
        return editing;
    }
}
