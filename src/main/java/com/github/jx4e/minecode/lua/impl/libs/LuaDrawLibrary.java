package com.github.jx4e.minecode.lua.impl.libs;

import com.github.jx4e.minecode.lua.api.LuaFunction;
import com.github.jx4e.minecode.lua.api.LuaLibrary;
import com.github.jx4e.minecode.manager.RenderManager;
import com.github.jx4e.minecode.manager.ResourceManager;
import com.github.jx4e.minecode.ui.theme.BoxColorScheme;
import net.minecraft.client.texture.NativeImageBackedTexture;
import net.minecraft.client.util.math.MatrixStack;
import org.luaj.vm2.LuaValue;
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
                        "box",
                        varargs -> {
                            // Convert Our Values
                            MatrixStack matrix = (MatrixStack) CoerceLuaToJava.coerce(varargs.arg(1), MatrixStack.class);
                            int x = varargs.arg(2).toint();
                            int y = varargs.arg(3).toint();
                            int width = varargs.arg(4).toint();
                            int height = varargs.arg(5).toint();

                            if (varargs.arg(6).isint()) {
                                // Draw the box
                                RenderManager.instance().getRenderer().box(matrix, x, y, width, height,
                                        new Color(varargs.arg(6).toint()));
                            } else {
                                RenderManager.instance().getRenderer().box(matrix, x, y, width, height,
                                        (Color) CoerceLuaToJava.coerce(varargs.arg(6), Color.class));
                            }

                            // Return Nil
                            return LuaValue.NIL;
                        }
                ),
                new LuaFunction(
                        "texture",
                        varargs -> {
                            // Convert Our Values
                            MatrixStack matrix = (MatrixStack) CoerceLuaToJava.coerce(varargs.arg(1), MatrixStack.class);
                            String textureName = varargs.arg(2).tojstring();
                            int x = varargs.arg(3).toint();
                            int y = varargs.arg(4).toint();
                            int width = varargs.arg(5).toint();
                            int height = varargs.arg(6).toint();

                            // Draw the texture
                            NativeImageBackedTexture texture = ResourceManager.instance().getNativeImageTexture(textureName);
                            RenderManager.instance().getRenderer().image(matrix, texture.getGlId(), x, y, width, height);
                            texture.close();

                            // Return Nil
                            return LuaValue.NIL;
                        }
                ),
        });
    }
}
