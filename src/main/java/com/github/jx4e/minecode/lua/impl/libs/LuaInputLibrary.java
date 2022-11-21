package com.github.jx4e.minecode.lua.impl.libs;

import com.github.jx4e.minecode.lua.api.LuaFunction;
import com.github.jx4e.minecode.lua.api.LuaLibrary;
import com.github.jx4e.minecode.manager.RenderManager;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.client.util.math.MatrixStack;
import org.luaj.vm2.LuaInteger;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.jse.CoerceJavaToLua;
import org.luaj.vm2.lib.jse.CoerceLuaToJava;
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
