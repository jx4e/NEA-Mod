package com.github.jx4e.minecode.util.misc;

/**
 * @author Jake (github.com/jx4e)
 * @since 29/06/2022
 **/

public class StringUtil {
    public static int getCharacterCount(char c, String s) {
        int count = 0;

        for (char c1 : s.toCharArray()) {
            if (c == c1) count++;
        }

        return count;
    }
}
