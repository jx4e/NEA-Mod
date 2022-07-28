package com.github.jx4e.minecode.util.misc;

import com.github.jx4e.minecode.util.misc.IOUtil;

import java.io.*;

/**
 * @author Jake (github.com/jx4e)
 * @since 29/06/2022
 **/

public class TextFile {
    private final File file;
    private String content;

    public TextFile(File file) {
        this.file = file;
    }

    public void read() {
       try(FileInputStream inputStream = new FileInputStream(file)) {
           content = IOUtil.writeToByteArray(inputStream).toString();
       } catch (IOException e) {
           e.printStackTrace();
       }

       if (content == null) content = "";
    }

    private void write(String content) {
        try(
                ByteArrayInputStream inputStream = new ByteArrayInputStream(content.getBytes());
                FileOutputStream outputStream = new FileOutputStream(file)
        ) {
            IOUtil.writeToOutputStream(inputStream, outputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        write(content);
    }
}
