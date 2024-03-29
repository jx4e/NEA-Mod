package com.github.jx4e.minecode.lua.impl.libs;

import com.github.jx4e.minecode.lua.library.LuaFunction;
import com.github.jx4e.minecode.lua.library.LuaLibrary;
import org.luaj.vm2.lib.jse.CoerceJavaToLua;

import java.awt.*;

/**
 * A Lua library based around color
 * @author Jake (github.com/jx4e)
 * @since 02/11/2022
 **/

public class LuaColorLibrary extends LuaLibrary {
    public LuaColorLibrary() {
        super("color", new LuaFunction[]{
                new LuaFunction(
                        "rgb",
                        varargs -> {
                            // Convert Our Values
                            int r = varargs.arg(1).toint();
                            int g = varargs.arg(2).toint();
                            int b = varargs.arg(3).toint();

                            Color color = new Color(r, g, b);

                            // Return Color
                            return CoerceJavaToLua.coerce(color);
                        }
                ),
                new LuaFunction(
                        "rgba",
                        varargs -> {
                            // Convert Our Values
                            int r = varargs.arg(1).toint();
                            int g = varargs.arg(2).toint();
                            int b = varargs.arg(3).toint();
                            int a = varargs.arg(4).toint();

                            Color color = new Color(r, g, b, a);

                            // Return Color
                            return CoerceJavaToLua.coerce(color);
                        }
                ),
                new LuaFunction(
                        "hsb",
                        varargs -> {
                            // Convert Our Values
                            float h = varargs.arg(1).tofloat();
                            float s = varargs.arg(2).tofloat();
                            float b = varargs.arg(3).tofloat();

                            Color color = new Color(Color.HSBtoRGB(h, s, b));

                            // Return Color
                            return CoerceJavaToLua.coerce(color);
                        }
                )
        });
    }
}
