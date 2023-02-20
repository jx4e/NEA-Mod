package com.github.jx4e.minecode.mixins;

import com.github.jx4e.minecode.lua.LuaManager;
import com.github.jx4e.minecode.lua.LuaTracker;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.Varargs;
import org.luaj.vm2.lib.BaseLib;
import org.luaj.vm2.lib.LibFunction;
import org.luaj.vm2.lib.VarArgFunction;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/**
 * @author Jake (github.com/jx4e)
 * @since 11/01/2023
 **/

@Mixin(VarArgFunction.class)
public abstract class MixinVarargFunction {
    @Shadow public abstract LuaValue call();

    @Shadow public abstract LuaValue call(LuaValue arg);

    private boolean called = false;

    @Inject(method = "call(Lorg/luaj/vm2/LuaValue;)Lorg/luaj/vm2/LuaValue;", at = @At("HEAD"), remap = false)
    protected void call(LuaValue par1, CallbackInfoReturnable<LuaValue> cir) {
        register();
    }

    @Inject(method = "invoke", at = @At("HEAD"), remap = false)
    protected void call(Varargs par1, CallbackInfoReturnable<Varargs> cir) {
        register();
    }

    private void register() {
        System.out.println(called);

        called = true;

        LuaManager.instance().getScripts().forEach(script -> {
            script.getTracker().registerFunction(((LuaValue)((Object) this)));
        });
    }
}
