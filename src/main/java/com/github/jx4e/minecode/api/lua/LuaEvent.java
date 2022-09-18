package com.github.jx4e.minecode.api.lua;

import net.fabricmc.fabric.api.event.Event;
import org.luaj.vm2.LuaValue;

/**
 * @author Jake (github.com/jx4e)
 * @since 08/06/2022
 **/

public abstract class LuaEvent {
    private final String name;
    private final LuaValue[] args;

    public LuaEvent(String name, LuaValue[] args) {
        this.name = name;
        this.args = args;
    }

    protected abstract void addListener();

    public String getName() {
        return name;
    }

    public LuaValue[] getArgs() {
        return args;
    }
}
