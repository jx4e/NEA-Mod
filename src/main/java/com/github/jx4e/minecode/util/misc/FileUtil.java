package com.github.jx4e.minecode.util.misc;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;

public class FileUtil {
    public static boolean downloadFile(URL inputURL, File outputFile) {
        if (inputURL == null || outputFile == null) return false;

        try (BufferedInputStream in = new BufferedInputStream(inputURL.openStream());
             FileOutputStream out = new FileOutputStream(outputFile)) {
            IOUtil.writeToOutputStream(in, out);
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }
}
