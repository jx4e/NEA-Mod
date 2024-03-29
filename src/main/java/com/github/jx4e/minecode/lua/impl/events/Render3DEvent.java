package com.github.jx4e.minecode.lua.impl.events;

import com.github.jx4e.minecode.lua.event.LuaEvent;
import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderContext;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.jse.CoerceJavaToLua;

/**
 * @author Jake (github.com/jx4e)
 * @since 09/11/2022
 **/

public class Render3DEvent extends LuaEvent {
    /**
     * Render event to be called when the world is rendered
     * @param context - the world render context
     * @param type - If it's before or after entities
     */
    public Render3DEvent(WorldRenderContext context, Type type) {
        super("Render3DEvent", new LuaValue[]{
                CoerceJavaToLua.coerce(context), LuaValue.valueOf(type.name())
        });
    }

    public enum Type {
        BeforeEntity, AfterEntity
    }
}
