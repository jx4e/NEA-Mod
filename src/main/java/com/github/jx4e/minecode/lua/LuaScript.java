package com.github.jx4e.minecode.lua;

import com.github.jx4e.minecode.manager.ScriptManager;
import com.github.jx4e.minecode.util.misc.IOUtil;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.jse.CoerceJavaToLua;

import java.io.File;

/**
 * @author Jake
 * @since 03/03/2022
 **/

public class LuaScript {
    private boolean enabled;
    private File file;
    private String content;

    public LuaScript(File file) {
        this.enabled = false;
        this.file = file;
        this.content = IOUtil.readFileToString(file);
    }

    public void load() {
        ScriptManager.instance().loadScript(this);
        enabled = true;
    }

    public void unload() {
        ScriptManager.instance().unloadScript(this);
        enabled = false;
    }

    public void invoke(LuaEvent event) {
        invoke(event.getName(), event.getArgs());
    }

    public void invoke(Object object) {
        invoke(object.getClass().getSimpleName(), new LuaValue[]{CoerceJavaToLua.coerce(object)});
    }

    public void invoke(String eventName, LuaValue[] args) {
        LuaValue func = ScriptManager.instance().getGlobals().get(eventName);

        if (func == null || func == LuaValue.NIL) return;

        switch (args.length) {
            case 1 -> func.call(args[0]);
            case 2 -> func.call(args[0], args[1]);
            case 3 -> func.call(args[0], args[1], args[2]);
            default -> func.call();
        }
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getContent() {
        return content;
    }
}
