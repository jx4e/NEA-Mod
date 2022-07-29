package com.github.jx4e.minecode.impl.manager;

import com.github.jx4e.minecode.Minecode;
import com.github.jx4e.minecode.util.misc.FileUtil;
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
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.List;
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

    /**
     * Load the config
     */
    public void load() {
        Minecode.getInstance().getLogger().info("Creating Directories...");
        setupDirectory(directory);
        setupDirectory(resources);
        setupDirectory(projects);

        Minecode.getInstance().getLogger().info("Downloading Resources...");
        loadResources();
    }

    /**
     * Create a directory
     * @param directory - location to make the dir
     * @return if the directory already exists
     */
    private boolean setupDirectory(File directory) {
        if (!directory.exists() || directory.isFile()) {
            directory.delete();
            directory.mkdir();

            return false;
        }

        return true;
    }

    /**
     * Download all the resources from the resources json
     */
    private void loadResources() {
        String json = IOUtil.readResourceStream("/minecode.resources.json");

        if (json == null) return;

        JsonArray resourceArray = JsonHelper.getArray(JsonHelper.deserialize(new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create(), json, JsonObject.class), "resources");

        List<String> fileNames = Arrays.stream(resources.listFiles()).map(file -> file.getName()).toList();

        resourceArray.forEach(jsonElement -> {
            String name = JsonHelper.getString(jsonElement.getAsJsonObject(), "name");

            if (fileNames.contains(name)) return;

            String url = JsonHelper.getString(jsonElement.getAsJsonObject(), "url");

            try {
                FileUtil.downloadFile(new URL(url), new File(resources, name));
            } catch (MalformedURLException e) {
                throw new RuntimeException(e);
            }
        });
    }

    private static ConfigManager instance;

    public static ConfigManager instance() {
        if (instance == null) instance = new ConfigManager();

        return instance;
    }
}
