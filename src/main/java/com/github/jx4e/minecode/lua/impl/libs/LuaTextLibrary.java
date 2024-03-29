package com.github.jx4e.minecode.lua.impl.libs;

import com.github.jx4e.minecode.lua.library.LuaFunction;
import com.github.jx4e.minecode.lua.library.LuaLibrary;
import com.github.jx4e.minecode.rendering.RenderManager;
import net.minecraft.client.util.math.MatrixStack;
import org.luaj.vm2.LuaInteger;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.jse.CoerceLuaToJava;

import java.awt.*;

/**
 * @author Jake (github.com/jx4e)
 * @since 02/11/2022
 **/

public class LuaTextLibrary extends LuaLibrary {
    public LuaTextLibrary() {
        super("text", new LuaFunction[]{
                new LuaFunction(
                        "write",
                        varargs -> {
                            // Convert Our Values
                            MatrixStack matrix = (MatrixStack) CoerceLuaToJava.coerce(varargs.arg(1), MatrixStack.class);
                            String text = varargs.arg(2).tojstring();
                            int x = varargs.arg(3).toint();
                            int y = varargs.arg(4).toint();

                            // Draw the text
                            if (varargs.arg(5).isint()) {
                                RenderManager.instance().getDefaultFontRenderer().draw(matrix, text, x, y,
                                        varargs.arg(5).toint());
                            } else {
                                RenderManager.instance().getDefaultFontRenderer().draw(matrix, text, x, y,
                                        ((Color) CoerceLuaToJava.coerce(varargs.arg(5), Color.class)).getRGB());
                            }

                            // No Return Value
                            return LuaValue.NIL;
                        }
                ),
                new LuaFunction(
                        "width",
                        varargs -> {
                            // Convert Our Values
                            String text = varargs.arg(1).tojstring();

                            // Return Value
                            return LuaInteger.valueOf(RenderManager.instance().getDefaultFontRenderer().getWidth(text));
                        }
                ),
                new LuaFunction(
                        "height",
                        varargs -> {
                            // Return Value
                            return LuaValue.valueOf(RenderManager.instance().getDefaultFontRenderer().fontHeight);
                        }
                )
        });
    }
}
