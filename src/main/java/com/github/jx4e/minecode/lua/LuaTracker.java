package com.github.jx4e.minecode.lua;

import com.github.jx4e.minecode.lua.event.LuaEvent;
import org.luaj.vm2.LuaValue;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Jake (github.com/jx4e)
 * @since 11/01/2023
 **/

public class LuaTracker {
    private LuaEvent activeEvent = null;
    private List<LuaValue> luaValues = new ArrayList<>();
    private Map<Class<? extends LuaEvent>, List<LuaValue>> eventFunctionMap = new HashMap<>();

    public LuaTracker() {}

    public void startEvent(LuaEvent event) {
        if (activeEvent != null) {
            endEvent();
        }

        activeEvent = event;
        luaValues.clear();
    }

    public void registerFunction(LuaValue value) {
        luaValues.add(value);
    }

    public void endEvent() {
        eventFunctionMap.remove(activeEvent.getClass());
        eventFunctionMap.put(activeEvent.getClass(), luaValues);
        activeEvent = null;
    }

    public Map<Class<? extends LuaEvent>, List<LuaValue>> getEventFunctionMap() {
        return eventFunctionMap;
    }
}
