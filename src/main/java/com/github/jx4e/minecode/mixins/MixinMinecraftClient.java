package com.github.jx4e.minecode.mixins;

import net.minecraft.client.MinecraftClient;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * @author Jake (github.com/jx4e)
 * @since 21/11/2022
 **/

@Mixin(MinecraftClient.class)
public class MixinMinecraftClient {
    @Inject(method = "<init>", at = @At("TAIL"))
    public void inject(CallbackInfo callbackInfo) {

    }
}
