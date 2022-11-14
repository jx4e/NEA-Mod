package com.github.jx4e.minecode.lua.impl.libs;

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
    //                        new Class[]{MatrixStack.class, String.class, Integer.class, Integer.class, Integer.class},
    public LuaDrawLibrary() {
        super("draw", new LuaFunction[]{
                new LuaFunction(
                        "text",
                        new Class[]{MatrixStack.class},
                        varargs -> {
                            // Convert Our Values
                            MatrixStack matrix = (MatrixStack) CoerceLuaToJava.coerce(varargs.arg(1), MatrixStack.class);
                            String text = varargs.arg(2).tojstring();
                            int x = varargs.arg(3).toint();
                            int y = varargs.arg(4).toint();
                            int color = varargs.arg(5).toint();

                            // Draw the text
                            RenderManager.instance().getDefaultFontRenderer().draw(matrix, text, x, y, color);

                            // No Return Value
                            return LuaValue.NIL;
                        }
                ),
//                new LuaFunction(
//                        "box",
//                        new Class[]{
//                                MatrixStack.class,
//                                Integer.class, Integer.class,
//                                Integer.class, Integer.class,
//                                Integer.class
//                        },
//                        params -> {
//                            // Convert Our Values
//                            MatrixStack matrix = (MatrixStack) CoerceLuaToJava.coerce(params[0], MatrixStack.class);
//                            int x = params[1].toint();
//                            int y = params[2].toint();
//                            int width = params[3].toint();
//                            int height = params[4].toint();
//                            int color = params[5].toint();
//
//                            // Draw the Box
//                            RenderManager.instance().getRenderer().box(matrix, x, y, width, height, new Color(color));
//
//                            // No Return Value
//                            return LuaValue.NIL;
//                        }
//                )
        });
    }
}
