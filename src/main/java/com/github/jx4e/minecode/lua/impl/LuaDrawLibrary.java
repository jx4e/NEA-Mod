package com.github.jx4e.minecode.lua.impl;

import com.github.jx4e.minecode.lua.api.LuaFunction;
import com.github.jx4e.minecode.lua.api.LuaLibrary;
import com.github.jx4e.minecode.manager.RenderManager;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.ast.Str;

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
                        new Class[]{MatrixStack.class},
                        params -> {
                            System.out.println(params[0]);
                            return LuaValue.NIL;
                        }
                )
        });
    }
}
