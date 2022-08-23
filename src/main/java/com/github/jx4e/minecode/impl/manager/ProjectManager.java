package com.github.jx4e.minecode.impl.manager;

import com.github.jx4e.minecode.Minecode;
import com.github.jx4e.minecode.MinecodeClient;
import com.github.jx4e.minecode.api.project.LuaProject;
import com.github.jx4e.minecode.util.misc.FileUtil;
import com.github.jx4e.minecode.util.misc.IOUtil;
import com.google.gson.JsonObject;
import net.minecraft.client.texture.NativeImage;
import net.minecraft.client.texture.NativeImageBackedTexture;
import net.minecraft.util.JsonHelper;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Jake (github.com/jx4e)
 * @since 29/06/2022
 **/

public class ProjectManager {
    private final List<LuaProject> projects = new ArrayList<>();

    private ProjectManager() {

    }

    public void createProjectsFromDirectory(File dir) {
        if (!dir.exists() || !dir.isDirectory()) return;

        Arrays.stream(dir.listFiles()).forEach(file -> {
            try {
                projects.add(new LuaProject(file));
            } catch (Exception e) {
                // If exception thrown we just do not add the project
            }
        });
    }

    public void createProject(String projectName, String mainScriptName) {
        File projectsDirectory = ConfigManager.instance().getProjects();

        File projectDirectory = new File(projectsDirectory, projectName);
        if (projectDirectory.exists()) {
            Minecode.getInstance().getLogger().info("Project Already Exists!");
            return;
        }
        projectDirectory.mkdirs();

        Minecode.getInstance().getLogger().info("Creating project json file");
        File projectJSON = new File(projectDirectory, "project.json");
        try {
            projectJSON.createNewFile();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("name", projectName);
        jsonObject.addProperty("enabled", false);
        jsonObject.addProperty("main-script", mainScriptName);
        try (ByteArrayInputStream in = new ByteArrayInputStream(jsonObject.toString().getBytes(StandardCharsets.UTF_8));
                FileOutputStream out = new FileOutputStream(projectJSON)) {
            IOUtil.writeToOutputStream(in, out);
        } catch (IOException e) {

        }

        Minecode.getInstance().getLogger().info("Creating scripts folder");
        File scriptDir = new File(projectDirectory, "scripts");
        scriptDir.mkdirs();

        Minecode.getInstance().getLogger().info("Creating main script");
        File mainScript = new File(scriptDir, mainScriptName);
        try {
            mainScript.createNewFile();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        Minecode.getInstance().getLogger().info("Reloading all projects");
        projects.clear();
        ConfigManager.instance().loadProjects();
    }

    public List<LuaProject> getProjects() {
        return projects;
    }

    private static ProjectManager instance;

    public static ProjectManager instance() {
        if (instance == null) instance = new ProjectManager();

        return instance;
    }
}
