package com.github.jx4e.minecode.lua.impl.events;

import com.github.jx4e.minecode.lua.LuaScript;
import com.github.jx4e.minecode.lua.event.LuaEvent;
import org.luaj.vm2.LuaValue;

/**
 * @author Jake (github.com/jx4e)
 * @since 16/02/2023
 **/

public class ScriptLoadEvent extends LuaEvent {
    /**
     * The script thats being loaded
     */
    private final LuaScript script;

    /**
     * Event that's called when a Lua script is loaded and ran
     * @param script
     */
    public ScriptLoadEvent(LuaScript script) {
        super("ScriptLoadEvent", new LuaValue[]{});
        this.script = script;
    }

    public LuaScript getScript() {
        return script;
    }
}
