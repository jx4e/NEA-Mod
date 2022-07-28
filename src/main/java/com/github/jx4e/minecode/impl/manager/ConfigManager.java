package com.github.jx4e.minecode.impl.manager;

import com.github.jx4e.minecode.Minecode;
import com.github.jx4e.minecode.util.misc.IOUtil;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import net.minecraft.client.font.Font;
import net.minecraft.client.font.FontType;
import net.minecraft.util.JsonHelper;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;

import static com.github.jx4e.minecode.MinecodeClient.mc;

/**
 * @author Jake (github.com/jx4e)
 * @since 29/06/2022
 **/

public class ConfigManager {
    private final File directory = new File(mc.runDirectory, Minecode.MOD_NAME.toLowerCase());
    private final File resources = new File(directory, "resources");
    private final File projects = new File(directory, "projects");

    private ConfigManager() {}

    public void load() {
        setupDirectory(directory);
        setupDirectory(resources);
        setupDirectory(projects);

        loadResources();
    }

    private boolean setupDirectory(File directory) {
        if (!directory.exists() || directory.isFile()) {
            directory.delete();
            directory.mkdir();

            return false;
        }

        return true;
    }

    private void loadResources() {
        String json = IOUtil.readResourceStream("/minecode.resources.json");

        if (json == null) return;

        JsonArray resourceArray = JsonHelper.getArray(JsonHelper.deserialize(new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create(), json, JsonObject.class), "resources");

        resourceArray.forEach(jsonElement -> {
            System.out.println(JsonHelper.getString(jsonElement.getAsJsonObject(), "name"));
        });
    }

    public File getDirectory() {
        return directory;
    }

    public File getResources() {
        return resources;
    }

    public File getProjects() {
        return projects;
    }

    private static ConfigManager instance;

    public static ConfigManager instance() {
        if (instance == null) instance = new ConfigManager();

        return instance;
    }
}
