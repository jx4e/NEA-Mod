package com.github.jx4e.minecode.manager;

import com.github.jx4e.minecode.event.handler.EventHandler;
import com.github.jx4e.minecode.event.handler.EventHandlerImpl;
import com.github.jx4e.minecode.lua.impl.events.Render2DEvent;
import com.github.jx4e.minecode.lua.impl.events.Render3DEvent;
import com.github.jx4e.minecode.lua.impl.events.TickEvent;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderEvents;

/**
 * @author jake
 * @since 08/04/2022
 */
public class EventManager {
    private final EventHandler eventHandler;

    private EventManager() {
        eventHandler = new EventHandlerImpl();
        initEvents();
        register(this);
    }

    private void initEvents() {
        HudRenderCallback.EVENT.register((matrixStack, delta) -> {
            Render2DEvent event = new Render2DEvent(matrixStack);
            post(event);
            LuaManager.instance().postEvent(event);
        });

        WorldRenderEvents.BEFORE_ENTITIES.register((context) -> {
            Render3DEvent event = new Render3DEvent(context, Render3DEvent.Type.BeforeEntity);
            post(event);
            LuaManager.instance().postEvent(event);
        });

        WorldRenderEvents.AFTER_ENTITIES.register((context) -> {
            Render3DEvent event = new Render3DEvent(context, Render3DEvent.Type.AfterEntity);
            post(event);
            LuaManager.instance().postEvent(event);
        });

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            TickEvent event = new TickEvent();
            post(event);
            LuaManager.instance().postEvent(event);
        });
    }

    public void register(Object object) {
        eventHandler.register(object);
    }

    public void unregister(Object object) {
        eventHandler.unregister(object);
    }

    public void register(Class<?> clazz) {
        eventHandler.register(clazz);
    }

    public void unregister(Class<?> clazz) {
        eventHandler.unregister(clazz);
    }

    public boolean isRegistered(Object object) {
        return eventHandler.isRegistered(object);
    }

    public boolean isRegistered(Class<?> clazz) {
        return eventHandler.isRegistered(clazz);
    }

    public void attach(EventHandler handler) {
        eventHandler.attach(handler);
    }

    public void detach(EventHandler handler) {
        eventHandler.detach(handler);
    }

    public boolean isAttached(EventHandler handler) {
        return eventHandler.isAttached(handler);
    }

    public <T> void post(T object) {
        eventHandler.post(object);
    }

    private static EventManager instance;

    public static EventManager instance() {
        if (instance == null) instance = new EventManager();
        return instance;
    }
}
