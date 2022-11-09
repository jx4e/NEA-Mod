package com.github.jx4e.minecode.lua.impl;

import com.github.jx4e.minecode.lua.api.LuaFunction;
import com.github.jx4e.minecode.lua.api.LuaLibrary;
import com.github.jx4e.minecode.manager.RenderManager;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.ast.Str;
import org.luaj.vm2.lib.jse.CoerceLuaToJava;

import java.awt.*;

/**
 * @author Jake (github.com/jx4e)
 * @since 02/11/2022
 **/

public class LuaDrawLibrary extends LuaLibrary {
    public LuaDrawLibrary() {
        super("draw", new LuaFunction[]{
                new LuaFunction(
                        "text",
                        new Class[]{MatrixStack.class, String.class},
                        params -> {
                            MatrixStack matrix = (MatrixStack) CoerceLuaToJava.coerce(params[0], MatrixStack.class);
                            RenderManager.instance().getDefaultFontRenderer().draw(
                                    matrix, params[1].tojstring(),
                                    2,2, 0xFFFFFF
                            );
                            return LuaValue.NIL;
                        }
                )
        });
    }
}
