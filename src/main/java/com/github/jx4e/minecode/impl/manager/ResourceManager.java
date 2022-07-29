package com.github.jx4e.minecode.impl.manager;

import com.github.jx4e.minecode.util.render.CFontRenderer;
import com.github.jx4e.minecode.util.render.Renderer;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.texture.NativeImage;
import net.minecraft.client.texture.NativeImageBackedTexture;
import org.luaj.vm2.ast.Str;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.ByteBuffer;

import static com.github.jx4e.minecode.MinecodeClient.mc;

/**
 * @author Jake (github.com/jx4e)
 * @since 29/06/2022
 **/

public class ResourceManager {
    private ResourceManager() {

    }

    public NativeImageBackedTexture getNativeImageTexture(String resourceName) {
        try {
            NativeImage image = NativeImage.read(new FileInputStream(new File(ConfigManager.instance().getResources(), resourceName)));
            return new NativeImageBackedTexture(image);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static ResourceManager instance;

    public static ResourceManager instance() {
        if (instance == null) instance = new ResourceManager();

        return instance;
    }
}
