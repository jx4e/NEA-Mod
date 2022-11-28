package com.github.jx4e.minecode.project;

import com.github.jx4e.minecode.util.misc.IOUtil;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import net.minecraft.util.JsonHelper;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class LuaLesson {
    // Files
    private final File lessonFile;

    // Lesson Info
    private final String name;
    private final String description;
    private final List<LessonContent> content;

    public LuaLesson(File lessonFile) throws Exception {
        this.lessonFile = lessonFile;

        // Read the lesson file
        String json = IOUtil.readFileToString(lessonFile);

        if (json == null) throw new Exception("Invalid Lesson File");

        JsonObject jsonObject = JsonHelper.deserialize(new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create(), json, JsonObject.class);

        name = JsonHelper.getString(jsonObject, "name");
        description = JsonHelper.getString(jsonObject, "description");
        content = new ArrayList<>();

        JsonArray contentArray = JsonHelper.getArray(jsonObject, "content");
        contentArray.forEach(element -> {
            if (element.isJsonObject()) {
                LessonContent contentObject = new LessonContent(
                        JsonHelper.getString(element.getAsJsonObject(), "text"),
                        JsonHelper.getString(element.getAsJsonObject(), "code")
                );
                content.add(contentObject);
            }
        });
    }

    public File getLessonFile() {
        return lessonFile;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public List<LessonContent> getContent() {
        return content;
    }

    public record LessonContent(String text, String code) {
        public String getText() {
            return text;
        }

        public String getCode() {
            return code;
        }
    }
}
