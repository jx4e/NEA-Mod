package com.github.jx4e.minecode.api.lua.script;

import com.github.jx4e.minecode.api.lua.event.LuaEvent;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.jse.CoerceJavaToLua;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Objects;
import java.util.stream.Collectors;

import static com.github.jx4e.minecode.api.lua.LuaApi.getGlobals;

/**
 * @author Jake
 * @since 03/03/2022
 **/

public class LuaScript {
    protected String path;
    protected boolean init;
    protected final String script;

    public LuaScript(String path) {
        this.path = path;
        this.init = false;
        this.script = new BufferedReader(
                new InputStreamReader(Objects.requireNonNull(LuaScript.class.getResourceAsStream(path)), StandardCharsets.UTF_8))
                .lines().collect(Collectors.joining("\n")
        );
    }

    public void init() {
        if (init) throw new RuntimeException("Script already initialised!");

        getGlobals().load(script).call();
        init = true;
    }

    public void invoke(LuaEvent event) {
        invoke(event.getName(), event.getArgs());
    }

    public void invoke(Object object) {
        invoke(object.getClass().getSimpleName(), new LuaValue[]{CoerceJavaToLua.coerce(object)});
    }

    public void invoke(String eventName, LuaValue[] args) {
        LuaValue func = getGlobals().get(eventName);

        if (func == null || func == LuaValue.NIL) return;

        switch (args.length) {
            case 1 -> func.call(args[0]);
            case 2 -> func.call(args[0], args[1]);
            case 3 -> func.call(args[0], args[1], args[2]);
            default -> func.call();
        }
    }

    public boolean isInitialised() {
        return init;
    }

    public String getScript() {
        return script;
    }
}
