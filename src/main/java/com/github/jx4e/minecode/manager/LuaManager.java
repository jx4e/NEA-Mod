package com.github.jx4e.minecode.manager;

import com.github.jx4e.minecode.lua.api.LuaEvent;
import com.github.jx4e.minecode.lua.api.LuaLibrary;
import com.github.jx4e.minecode.lua.api.LuaScript;
import com.github.jx4e.minecode.lua.api.LuaUtil;
import com.github.jx4e.minecode.lua.impl.LuaDrawLibrary;
import org.luaj.vm2.Globals;
import org.luaj.vm2.lib.jse.CoerceJavaToLua;
import org.luaj.vm2.lib.jse.JsePlatform;

import java.util.LinkedList;
import java.util.List;

import static com.github.jx4e.minecode.MinecodeClient.mc;

/**
 * @author Jake (github.com/jx4e)
 * @since 29/06/2022
 **/

public class LuaManager {
    private Globals globals;
    private final List<LuaScript> scripts = new LinkedList<>();
    private boolean init = false;

    private LuaManager() {}

    public void init() {
        globals = JsePlatform.standardGlobals();

        globals.load(new LuaDrawLibrary());

        getGlobals().set("mc", CoerceJavaToLua.coerce(mc));
        getGlobals().set("utils", CoerceJavaToLua.coerce(new LuaUtil()));

        init = true;
    }

    public void loadScript(LuaScript script) {
        if (!init) init();

        if (!scripts.contains(script)) {
            try {
                scripts.add(script);
                getGlobals().load(script.getContent()).call();
                script.setEnabled(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void unloadScript(LuaScript script) {
        if (!init) init();

        if (scripts.contains(script)) {
            scripts.remove(script);
            script.invoke("Exit", null);
            script.setEnabled(false);
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

    private static LuaManager instance;

    public static LuaManager instance() {
        if (instance == null) instance = new LuaManager();

        return instance;
    }
}
