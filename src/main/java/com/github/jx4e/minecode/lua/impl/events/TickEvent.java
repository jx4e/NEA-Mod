package com.github.jx4e.minecode.lua.impl.events;

import com.github.jx4e.minecode.lua.event.LuaEvent;
import org.luaj.vm2.LuaValue;

/**
 * @author Jake (github.com/jx4e)
 * @since 09/11/2022
 **/

public class TickEvent extends LuaEvent {
    /**
     * An event which is called every game tick (every 1/20th of a second)
     */
    public TickEvent() {
        super("TickEvent", new LuaValue[]{});
    }
}
