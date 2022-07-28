package com.github.jx4e.minecode.util.render;

import com.github.jx4e.minecode.util.misc.IOUtil;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import net.minecraft.client.font.Font;
import net.minecraft.client.font.FontStorage;
import net.minecraft.client.font.FontType;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.util.Identifier;
import net.minecraft.util.JsonHelper;

import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

import static com.github.jx4e.minecode.MinecodeClient.mc;

/**
 * @author Jake (github.com/jx4e)
 * @since 22/06/2022
 **/

public class CFontRenderer {
    private final TextRenderer textRenderer;
    private final Identifier identifier;

    public CFontRenderer(String config) {
        identifier = new Identifier(UUID.randomUUID().toString().toLowerCase());
        textRenderer = createContext(config, identifier);
    }

    public TextRenderer getTextRenderer() {
        return textRenderer;
    }

    public TextRenderer createContext(String config, Identifier id) {
        List<Font> fonts = new LinkedList<>();

        String json = IOUtil.readResourceStream("/assets/minecraft/minecode/font/" + config + ".json");

        if (json == null) return null;

        JsonArray jsonArray = JsonHelper.getArray(Objects.requireNonNull(JsonHelper.deserialize(new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create(), json, JsonObject.class)), "providers");

        for(int i = jsonArray.size() - 1; i >= 0; --i) {
            JsonObject jsonObject = JsonHelper.asObject(jsonArray.get(i), "providers[" + i + "]");
            try {
                String stringType = JsonHelper.getString(jsonObject, "type");
                FontType fontType = FontType.byId(stringType);
                Font font = fontType.createLoader(jsonObject).load(mc.getResourceManager());
                if (font != null)
                    fonts.add(font);
            } catch (RuntimeException e) {
                e.printStackTrace();
            }
        }

        FontStorage fontStorage = new FontStorage(mc.getTextureManager(), id);
        fontStorage.setFonts(fonts);
        return new TextRenderer(identifier -> fontStorage, true);
    }
}
