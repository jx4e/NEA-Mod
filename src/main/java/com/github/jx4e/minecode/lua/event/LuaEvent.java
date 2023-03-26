package com.github.jx4e.minecode.lua.event;

import org.luaj.vm2.LuaValue;

/**
 * @author Jake (github.com/jx4e)
 * @since 08/06/2022
 **/

public abstract class LuaEvent {
    /**
     * Name of the event to be called in the Lua script
     */
    private final String name;

    /**
     * Arguments to be parsed into the function
     */
    private final LuaValue[] args;

    public LuaEvent(String name, LuaValue[] args) {
        this.name = name;
        this.args = args;
    }

    public String getName() {
        return name;
    }

    public LuaValue[] getArgs() {
        return args;
    }
}
