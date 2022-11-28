package com.github.jx4e.minecode.manager;

import com.github.jx4e.minecode.project.LuaLesson;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author Jake (github.com/jx4e)
 * @since 29/06/2022
 **/

public class LessonManager {
    private final List<LuaLesson> lessons = new ArrayList<>();

    private LessonManager() {}

    public void createLessonsFromDirectory(File dir) {
        if (!dir.exists() || !dir.isDirectory()) return;

        Arrays.stream(dir.listFiles()).forEach(file -> {
            try {
                lessons.add(new LuaLesson(file));
            } catch (Exception e) {
                // If exception thrown we just do not add the project
            }
        });
    }

    public List<LuaLesson> getLessons() {
        return lessons;
    }

    private static LessonManager instance;

    public static LessonManager instance() {
        if (instance == null) instance = new LessonManager();

        return instance;
    }
}
