package com.github.jx4e.minecode.mixins;

import com.github.jx4e.minecode.rendering.screens.EditorMainMenu;
import com.github.jx4e.minecode.rendering.screens.QuickToggleMenu;
import net.minecraft.client.gui.screen.GameMenuScreen;
import net.minecraft.client.gui.screen.Screen;
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

@Mixin(GameMenuScreen.class)
public abstract class MixinGameMenuScreen extends Screen {
    protected MixinGameMenuScreen(Text title) {
        super(title);
    }

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

        this.addDrawableChild(
                new ButtonWidget(
                        10,
                        40,
                        98, 20,
                        Text.of("Quick-Toggle"),
                        button -> this.client.setScreen(QuickToggleMenu.getInstance())
                )
        );
    }
}
