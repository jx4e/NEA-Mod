package com.github.jx4e.minecode.api.project;

import com.github.jx4e.minecode.util.misc.FileUtil;
import com.github.jx4e.minecode.util.misc.IOUtil;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import net.minecraft.util.JsonHelper;
import org.apache.commons.io.filefilter.NameFileFilter;

import java.io.*;
import java.util.Arrays;

public class LuaProject {
    private File dir;
    private File scriptDir;
    private File projectFile;

    private String name;

    private boolean enabled;

    private String mainScriptName;

    public LuaProject(File dir) throws Exception {
        this.dir = dir;

        Arrays.stream(dir.listFiles()).forEach(file -> {
            switch (file.getName()) {
                case "scripts" -> scriptDir = file;
                case "project.json" -> projectFile = file;
            }
        });

        // Read the project file
        try (FileInputStream in = new FileInputStream(projectFile)) {
            String json = IOUtil.writeToByteArray(in).toString();

            if (json == null) throw new Exception("Invalid Project File");

            JsonObject jsonObject = JsonHelper.deserialize(new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create(), json, JsonObject.class);

            name = JsonHelper.getString(jsonObject, "name");
            enabled = JsonHelper.getBoolean(jsonObject, "enabled");
            mainScriptName = JsonHelper.getString(jsonObject, "main-script");
        } catch (IOException e) {
            throw new Exception("Invalid Project File");
        }
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

    public boolean isEnabled() {
        return enabled;
    }

    public String getMainScriptName() {
        return mainScriptName;
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
