package com.github.jx4e.minecode.project;

import com.github.jx4e.minecode.lua.LuaScript;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import net.minecraft.util.JsonHelper;

import java.io.*;
import java.util.Arrays;
import java.util.Objects;

public class LuaProject {
    // Files
    private File dir;
    private File projectFile;
    private File mainScriptFile;

    // Project Info
    private String name;
    private boolean enabled;
    private String mainScriptName;

    // Scripts
    private LuaScript mainScript;

    /**
     * Makes LuaProject from file
     * @param dir
     * @throws Exception
     */
    public LuaProject(File dir) throws Exception {
        this.dir = dir;

        Arrays.stream(Objects.requireNonNull(dir.listFiles())).forEach(file -> {
            if ("project.json".equals(file.getName())) {
                projectFile = file;
            }
        });

        // Read the project file
        String json = IOUtil.readFileToString(projectFile);

        if (json == null) throw new Exception("Invalid Project File");

        JsonObject jsonObject = JsonHelper.deserialize(new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create(), json, JsonObject.class);

        name = JsonHelper.getString(jsonObject, "name");
        enabled = JsonHelper.getBoolean(jsonObject, "enabled");
        mainScriptName = JsonHelper.getString(jsonObject, "main-script");

        mainScript = new LuaScript(mainScriptFile = new File(dir, mainScriptName));
    }

    /**
     * Creates a new file object for the mainScript
     */
    public void reloadScript() {
        mainScript = new LuaScript(mainScriptFile = new File(dir, mainScriptName));
    }

    public File getDir() {
        return dir;
    }

    public File getProjectFile() {
        return projectFile;
    }

    public File getMainScriptFile() {
        return mainScriptFile;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void toggle() {
        setEnabled(!isEnabled());
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;

        if (enabled) {
            mainScript.load();
        } else {
            mainScript.unload();
        }
    }

    public String getMainScriptName() {
        return mainScriptName;
    }

    public void setMainScriptName(String mainScriptName) {
        this.mainScriptName = mainScriptName;
    }

    public LuaScript getMainScript() {
        return mainScript;
    }

    public void setMainScript(LuaScript mainScript) {
        this.mainScript = mainScript;
    }

    @Override
    public String toString() {
        return "LuaProject{" +
                "name='" + name + '\'' +
                ", enabled=" + enabled +
                ", mainScriptName='" + mainScriptName + '\'' +
                '}';
    }
}
