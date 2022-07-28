package com.github.jx4e.minecode.util.misc;

/**
 * @author Jake (github.com/jx4e)
 * @since 04/07/2022
 **/

public class Writer {
    private StringBuilder builder;

    public Writer() {
        this("");
    }

    public Writer(String text) {
        builder = new StringBuilder(text);
    }

    public void write(char character, int index) {
        if (index >= 0 && index < builder.length()) {
            builder.insert(index, character);
        } else {
            builder.append(character);
        }
    }

    public void write(char character) {
        write(character, builder.length() - 1);
    }

    public void delete(int index) {
        if (index >= 0 && index < builder.length()) {
            builder.deleteCharAt(index);
        }
    }

    public void deleteLast() {
        delete(builder.length() - 1);
    }

    public StringBuilder getBuilder() {
        return builder;
    }

    public String getText() {
        return builder.toString();
    }
}
