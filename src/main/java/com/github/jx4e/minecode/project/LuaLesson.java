package com.github.jx4e.minecode.project;

import com.github.jx4e.minecode.lua.LuaScript;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import net.minecraft.util.JsonHelper;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class LuaLesson {
    // Files
    private final File dir;
    private final File lessonFile;
    private final File mainScriptFile;

    // Lesson Info
    private final String name;
    private final String description;
    private final List<LessonContent> content;
    private final List<LessonContent> tasks;
    private final LuaScript script;

    /**
     * Takes a directory and makes the lua lesson from it
     * @param dir
     * @throws Exception
     */
    public LuaLesson(File dir) throws Exception {
        this.dir = dir;

        // Get the lesson.json file
        this.lessonFile = new File(dir, "lesson.json");
        this.mainScriptFile = new File(dir, "main.lua");

        // Read the lesson file
        String json = IOUtil.readFileToString(lessonFile);

        // Check if the file has been read correctly
        if (json == null) throw new Exception("Invalid Lesson File");

        JsonObject jsonObject = JsonHelper.deserialize(new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create(), json, JsonObject.class);

        // Get the name and description from the json
        name = JsonHelper.getString(jsonObject, "name");
        description = JsonHelper.getString(jsonObject, "description");

        // Initialise array
        content = new ArrayList<>();

        // Iterate over the "content" array and create lesson content records
        // Adds them all to content
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

        // Create a JSON array of "tasks"
        JsonArray taskArray = JsonHelper.getArray(jsonObject, "tasks");
        tasks = new ArrayList<>();

        // Add all the tasks to our array
        taskArray.forEach(element -> {
            if (element.isJsonObject()) {
                LessonContent contentObject = new LessonContent(
                        JsonHelper.getString(element.getAsJsonObject(), "text"),
                        JsonHelper.getString(element.getAsJsonObject(), "code")
                );
                tasks.add(contentObject);
            }
        });

        // Lets create our lua script
        script = new LuaScript(mainScriptFile);
    }

    public File getLessonFile() {
        return lessonFile;
    }

    public File getDir() {
        return dir;
    }

    public File getMainScriptFile() {
        return mainScriptFile;
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

    public List<LessonContent> getTasks() {
        return tasks;
    }

    public LuaScript getScript() {
        return script;
    }

    public record LessonContent(String text, String code) {
        @Override
        public String text() {
            return text;
        }

        @Override
        public String code() {
            return code;
        }
    }
}
