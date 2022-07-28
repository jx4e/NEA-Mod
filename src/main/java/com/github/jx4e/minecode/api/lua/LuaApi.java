package com.github.jx4e.minecode.api.lua;

import com.github.jx4e.minecode.api.lua.event.LuaEvent;
import com.github.jx4e.minecode.api.lua.library.LuaLibrary;
import com.github.jx4e.minecode.api.lua.script.LuaScript;
import org.luaj.vm2.Globals;
import org.luaj.vm2.lib.jse.JsePlatform;

import java.util.LinkedList;
import java.util.List;

/**
 * @author Jake (github.com/jx4e)
 * @since 08/06/2022
 **/

public class LuaApi {
    private static Globals globals;
    private static final List<LuaScript> scripts = new LinkedList<>();
    private static boolean init = false;

    public static void init(LuaLibrary... libraries) {
        globals = JsePlatform.standardGlobals();

        for (LuaLibrary library : libraries) {
            globals.load(library);
        }

        init = true;
    }

    public static void loadScript(LuaScript script) {
        if (!init) init();

        if (!scripts.contains(script)) {
            scripts.add(script);
            script.init();
        }
    }

    public static void unloadScript(LuaScript script) {
        if (!init) init();

        if (scripts.contains(script)) {
            scripts.remove(script);
            script.invoke("Exit", null);
        }
    }

    public static void postEvent(LuaEvent event) {
        scripts.forEach(script -> script.invoke(event));
    }

    public static void postEvent(Object object) {
        scripts.forEach(script -> script.invoke(object));
    }

    public static Globals getGlobals() {
        return globals;
    }

    private LuaApi() {
        throw new UnsupportedOperationException();
    }
}
