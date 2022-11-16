package com.github.jx4e.minecode.lua.impl.events;

import com.github.jx4e.minecode.lua.api.LuaEvent;
import net.minecraft.client.WindowSettings;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.screen.ScreenHandler;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.jse.CoerceJavaToLua;

import static com.github.jx4e.minecode.MinecodeClient.mc;

/**
 * @author Jake (github.com/jx4e)
 * @since 09/11/2022
 **/

public class Render2DEvent extends LuaEvent {
    public Render2DEvent(MatrixStack matrix) {
        super("Render2DEvent", new LuaValue[]{
                CoerceJavaToLua.coerce(matrix)
        });
    }
}
