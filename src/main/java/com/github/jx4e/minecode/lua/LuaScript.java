package com.github.jx4e.minecode.lua;

import com.github.jx4e.minecode.lua.event.LuaEvent;
import com.github.jx4e.minecode.project.IOUtil;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.jse.CoerceJavaToLua;

import java.io.File;

/**
 * @author Jake
 * @since 03/03/2022
 **/

public class LuaScript {
    private File file;
    private String content;
    private boolean enabled;
    private int priority;

    /**
     * Creates a Lua script
     * @param file - .lua file
     */
    public LuaScript(File file) {
        this.enabled = false;
        this.file = file;
        this.content = IOUtil.readFileToString(file);
        this.priority = 0;
    }

    public LuaScript(File file, int priority) {
        this.enabled = false;
        this.file = file;
        this.content = IOUtil.readFileToString(file);
        this.priority = priority;
    }


    /**
     * Load the script into the Lua Environment
     */
    public void load() {
        this.content = IOUtil.readFileToString(file);
        LuaManager.instance().loadScript(this);
    }

    /**
     * Unload the script from the Lua Environment
     */
    public void unload() {
        LuaManager.instance().unloadScript(this);
    }

    /**
     * Invoke an event
     * @param event
     */
    public void invoke(LuaEvent event) {
        invoke(event.getName(), event.getArgs());
    }

    /**
     * Invoke an event
     * @param object
     */
    public void invoke(Object object) {
        invoke(object.getClass().getSimpleName(), new LuaValue[]{CoerceJavaToLua.coerce(object)});
    }

    /**
     * Invoke an event with arguments
     * @param eventName
     * @param args
     */
    public void invoke(String eventName, LuaValue[] args) {
        LuaValue func = LuaManager.instance().getGlobals().get(eventName);

        if (func == null || func == LuaValue.NIL) return;

        try {
            func.invoke(args);
        } catch (Exception e) {
            e.printStackTrace();
            unload();
        }
    }

    /**
     * Toggles the script
     */
    public void toggle() {
        setEnabled(!isEnabled());

        if (enabled) load();
        else unload();
    }

    public void setFile(File file) {
        this.file = file;
    }

    public File getFile() {
        return file;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public int getPriority() {
        return priority;
    }

    /**
     * Change the content in the file & reload the script
     * @param content
     */
    public void setContent(String content) {
        IOUtil.writeToFile(file, content);
        this.content = content;
    }

    public String getContent() {
        return content;
    }
}
