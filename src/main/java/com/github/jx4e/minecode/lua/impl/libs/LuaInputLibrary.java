package com.github.jx4e.minecode.lua.impl.libs;

import com.github.jx4e.minecode.lua.library.LuaFunction;
import com.github.jx4e.minecode.lua.library.LuaLibrary;
import net.minecraft.client.util.InputUtil;
import org.luaj.vm2.LuaValue;
import org.lwjgl.glfw.GLFW;

import static com.github.jx4e.minecode.MinecodeClient.mc;

/**
 * @author Jake (github.com/jx4e)
 * @since 02/11/2022
 **/

public class LuaInputLibrary extends LuaLibrary {
    public LuaInputLibrary() {
        super("input", new LuaFunction[]{
                new LuaFunction(
                        "getKeyName",
                        varargs -> {
                            // Convert Our Values
                            int keycode = varargs.arg(1).toint();

                            // Return Value
                            return LuaValue.valueOf(InputUtil.fromKeyCode(keycode, GLFW.glfwGetKeyScancode(keycode)).getTranslationKey());
                        }
                ),
                new LuaFunction(
                        "getKeyCode",
                        varargs -> {
                            // Convert Our Values
                            String keyName = varargs.arg(1).tojstring();

                            // Return Value
                            return LuaValue.valueOf(InputUtil.fromTranslationKey(keyName).getCode());
                        }
                ),
                new LuaFunction(
                        "isKeyDown",
                        varargs -> {
                            // Convert Our Values
                            int keycode = varargs.arg(1).toint();

                            // Return Value
                            return LuaValue.valueOf(InputUtil.isKeyPressed(mc.getWindow().getHandle(), keycode));
                        }
                )
        });
    }
}
