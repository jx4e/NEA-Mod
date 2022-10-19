package com.github.jx4e.minecode.manager;

import com.github.jx4e.minecode.lua.LuaEvent;
import com.github.jx4e.minecode.lua.LuaLibrary;
import com.github.jx4e.minecode.lua.LuaScript;
import org.luaj.vm2.Globals;
import org.luaj.vm2.lib.jse.JsePlatform;

import java.util.LinkedList;
import java.util.List;

/**
 * @author Jake (github.com/jx4e)
 * @since 29/06/2022
 **/

public class ScriptManager {
    private Globals globals;
    private final List<LuaScript> scripts = new LinkedList<>();
    private boolean init = false;

    private ScriptManager() {

    }

    public void init(LuaLibrary... libraries) {
        globals = JsePlatform.standardGlobals();

        for (LuaLibrary library : libraries) {
            globals.load(library);
        }

        init = true;
    }

    public void loadScript(LuaScript script) {
        if (!init) init();

        if (!scripts.contains(script)) {
            scripts.add(script);
            getGlobals().load(script.getContent()).call();
        }
    }

    public void unloadScript(LuaScript script) {
        if (!init) init();

        if (scripts.contains(script)) {
            scripts.remove(script);
            script.invoke("Exit", null);
        }
    }

    public void postEvent(LuaEvent event) {
        scripts.forEach(script -> script.invoke(event));
    }

    public void postEvent(Object object) {
        scripts.forEach(script -> script.invoke(object));
    }

    public Globals getGlobals() {
        return globals;
    }

    private static ScriptManager instance;

    public static ScriptManager instance() {
        if (instance == null) instance = new ScriptManager();

        return instance;
    }
}
