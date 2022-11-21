package com.github.jx4e.minecode;

import com.github.jx4e.minecode.manager.ConfigManager;
import com.github.jx4e.minecode.manager.EventManager;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;

@Environment(EnvType.CLIENT)
public class MinecodeClient implements ClientModInitializer {
    public static MinecraftClient mc = MinecraftClient.getInstance();

    @Override
    public void onInitializeClient() {
        Minecode.getInstance().getLogger().info("Client Init");
        ConfigManager.instance().load();
        EventManager.instance().register(this);
        System.out.println(mc);
    }
}
