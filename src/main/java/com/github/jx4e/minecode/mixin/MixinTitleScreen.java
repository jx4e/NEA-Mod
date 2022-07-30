package com.github.jx4e.minecode.mixin;

import com.github.jx4e.minecode.Minecode;
import com.github.jx4e.minecode.impl.ui.editor.EditorMainMenu;
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

    @Inject(method = "init", at = @At("TAIL"))
    protected void init(CallbackInfo ci) {
        this.addDrawableChild(
                new ButtonWidget(
                        10,
                        10,
                        98, 20,
                        Text.of(Minecode.MOD_NAME),
                        button -> this.client.setScreen(EditorMainMenu.getInstance())
                )
        );
    }
}
