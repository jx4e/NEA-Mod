package com.github.jx4e.minecode.mixins;

import com.github.jx4e.minecode.rendering.screens.EditorMainMenu;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.TitleScreen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * @author Jake (github.com/jx4e)
 * @since 11/06/2022
 **/

@Mixin(TitleScreen.class)
public abstract class MixinTitleScreen extends Screen {
    protected MixinTitleScreen(Text title) {
        super(title);
    }

    /**
     * Adds button to the screen
     * @param ci
     */
    @Inject(method = "init", at = @At("TAIL"))
    protected void init(CallbackInfo ci) {
        this.addDrawableChild(
                new ButtonWidget(
                        10,
                        10,
                        98, 20,
                        Text.of("Editor"),
                        button -> this.client.setScreen(EditorMainMenu.getInstance())
                )
        );
    }
}
