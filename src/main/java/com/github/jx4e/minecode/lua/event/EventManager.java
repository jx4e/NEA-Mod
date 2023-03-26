package com.github.jx4e.minecode.lua.event;

import com.github.jx4e.minecode.lua.impl.events.Render2DEvent;
import com.github.jx4e.minecode.lua.impl.events.Render3DEvent;
import com.github.jx4e.minecode.lua.impl.events.TickEvent;
import com.github.jx4e.minecode.lua.LuaManager;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderEvents;

/**
 * @author jake
 * @since 08/04/2022
 */
public class EventManager {
    private EventManager() {
        initEvents();
    }

    /**
     * Initialise all the events (Register them with the fabric event system)
     */
    private void initEvents() {
        HudRenderCallback.EVENT.register((matrixStack, delta) -> {
            Render2DEvent event = new Render2DEvent(matrixStack);
            LuaManager.instance().postEvent(event);
        });

        WorldRenderEvents.BEFORE_ENTITIES.register((context) -> {
            Render3DEvent event = new Render3DEvent(context, Render3DEvent.Type.BeforeEntity);
            LuaManager.instance().postEvent(event);
        });

        WorldRenderEvents.AFTER_ENTITIES.register((context) -> {
            Render3DEvent event = new Render3DEvent(context, Render3DEvent.Type.AfterEntity);
            LuaManager.instance().postEvent(event);
        });

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            TickEvent event = new TickEvent();
            LuaManager.instance().postEvent(event);
        });
    }

    private static EventManager instance;

    public static EventManager instance() {
        if (instance == null) instance = new EventManager();
        return instance;
    }
}
