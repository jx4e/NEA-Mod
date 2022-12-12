package com.github.jx4e.minecode.lua;

import com.github.jx4e.minecode.lua.event.LuaEvent;
import com.github.jx4e.minecode.lua.impl.libs.LuaColorLibrary;
import com.github.jx4e.minecode.lua.impl.libs.LuaDrawLibrary;
import com.github.jx4e.minecode.lua.impl.libs.LuaInputLibrary;
import com.github.jx4e.minecode.lua.impl.libs.LuaTextLibrary;
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

    private LuaManager() {
        init();
    }

    private void init() {
        globals = JsePlatform.standardGlobals();

        globals.load(new LuaTextLibrary());
        globals.load(new LuaInputLibrary());
        globals.load(new LuaDrawLibrary());
        globals.load(new LuaColorLibrary());

        getGlobals().set("mc", CoerceJavaToLua.coerce(mc));
    }

    public void loadScript(LuaScript script) {
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
