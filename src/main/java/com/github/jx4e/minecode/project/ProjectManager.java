package com.github.jx4e.minecode.project;

import com.github.jx4e.minecode.Minecode;
import com.github.jx4e.minecode.rendering.ResourceManager;
import com.google.gson.JsonObject;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author Jake (github.com/jx4e)
 * @since 29/06/2022
 **/

public class ProjectManager {
    private final List<LuaProject> projects = new ArrayList<>();

    private ProjectManager() {

    }

    /**
     * Create projects from directory and adds them to projects
     * @param dir
     */
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

    /**
     * Creates project. Used when the user is on the EditorCreateProject screen
     * @param projectName
     * @param mainScriptName
     * @param useTemplate
     */
    public void createProject(String projectName, String mainScriptName, boolean useTemplate) {
        File projectsDirectory = ConfigManager.instance().getProjects();

        // Dir to make this new project in
        File projectDirectory = new File(projectsDirectory, projectName);
        if (projectDirectory.exists()) {
            Minecode.getInstance().getLogger().info("Project Already Exists!");
            return;
        }
        projectDirectory.mkdirs();

        // Project JSON file created
        Minecode.getInstance().getLogger().info("Creating project json file");
        File projectJSON = new File(projectDirectory, "project.json");
        try {
            projectJSON.createNewFile();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // Write our json and add the properties that we need
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("name", projectName);
        jsonObject.addProperty("enabled", false);
        jsonObject.addProperty("main-script", mainScriptName);

        // Write it to the file
        try (ByteArrayInputStream in = new ByteArrayInputStream(jsonObject.toString().getBytes(StandardCharsets.UTF_8));
                FileOutputStream out = new FileOutputStream(projectJSON)) {
            IOUtil.writeToOutputStream(in, out);
        } catch (IOException e) {

        }

        // Now we make the scripts file
        Minecode.getInstance().getLogger().info("Creating main script");
        File mainScript = new File(projectDirectory, mainScriptName);
        try {
            mainScript.createNewFile();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // If we want a template copy it from the template file
        if (useTemplate) {
            File templateFile = new File(ConfigManager.instance().getResources(), "template.lua");
            String content = IOUtil.readFileToString(templateFile);
            IOUtil.writeToFile(mainScript, content);
        }

        // Reload the projects
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
