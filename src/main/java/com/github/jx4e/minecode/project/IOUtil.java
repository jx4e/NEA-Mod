package com.github.jx4e.minecode.project;

import javax.annotation.Nullable;
import java.io.*;
import java.net.URL;
import java.nio.charset.StandardCharsets;

/**
 * @author Jake (github.com/jx4e)
 * @since 29/06/2022
 **/

public class IOUtil {
    public static ByteArrayOutputStream writeToByteArray(InputStream in) {
        try (ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            byte[] data = new byte[1024];
            int read;
            while ((read = in.read(data, 0, 1024)) != -1) {
                out.write(data, 0, read);
            }

            return out;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static void writeToOutputStream(InputStream in, OutputStream out) {
        try {
            byte[] data = new byte[1024];
            int read;
            while ((read = in.read(data, 0, 1024)) != -1) {
                out.write(data, 0, read);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Nullable
    public static String readResourceStream(String path) {
        String content = null;

        try (InputStream in = IOUtil.class.getResourceAsStream(path)) {
            content = writeToByteArray(in).toString();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return content;
    }

    public static String readFileToString(File file) {
        try (FileInputStream in = new FileInputStream(file)) {
            return IOUtil.writeToByteArray(in).toString();
        } catch (IOException e) {
            throw new RuntimeException();
        }
    }

    public static void writeToFile(File file, String content) {
        try (FileOutputStream out = new FileOutputStream(file);
             ByteArrayInputStream in = new ByteArrayInputStream(content.getBytes(StandardCharsets.UTF_8));
        ) {
           IOUtil.writeToOutputStream(in , out);
        } catch (IOException e) {
            throw new RuntimeException();
        }
    }
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

    private IOUtil() {
        throw new UnsupportedOperationException();
    }
}
