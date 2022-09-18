package com.github.jx4e.minecode.api.project;

import com.github.jx4e.minecode.api.lua.LuaScript;
import com.github.jx4e.minecode.util.misc.FileUtil;
import com.github.jx4e.minecode.util.misc.IOUtil;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import net.minecraft.util.JsonHelper;
import org.apache.commons.io.filefilter.NameFileFilter;

import java.io.*;
import java.util.Arrays;

public class LuaProject {
    // Files
    private File dir;
    private File scriptDir;
    private File projectFile;

    // Project Info
    private String name;
    private boolean enabled;
    private String mainScriptName;

    // Scripts
    private LuaScript mainScript;

    public LuaProject(File dir) throws Exception {
        this.dir = dir;

        Arrays.stream(dir.listFiles()).forEach(file -> {
            switch (file.getName()) {
                case "scripts" -> scriptDir = file;
                case "project.json" -> projectFile = file;
            }
        });

        // Read the project file
        String json = IOUtil.readFileToString(projectFile);

        if (json == null) throw new Exception("Invalid Project File");

        JsonObject jsonObject = JsonHelper.deserialize(new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create(), json, JsonObject.class);

        name = JsonHelper.getString(jsonObject, "name");
        enabled = JsonHelper.getBoolean(jsonObject, "enabled");
        mainScriptName = JsonHelper.getString(jsonObject, "main-script");

        mainScript = new LuaScript(new File(scriptDir, mainScriptName));
    }

    public File getDir() {
        return dir;
    }

    public File getScriptDir() {
        return scriptDir;
    }

    public File getProjectFile() {
        return projectFile;
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
