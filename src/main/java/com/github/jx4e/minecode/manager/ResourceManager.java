package com.github.jx4e.minecode.manager;

import net.minecraft.client.texture.NativeImage;
import net.minecraft.client.texture.NativeImageBackedTexture;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

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
