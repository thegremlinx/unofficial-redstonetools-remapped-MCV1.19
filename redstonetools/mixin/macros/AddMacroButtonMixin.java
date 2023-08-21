package tools.redstone.redstonetools.mixin.macros;

import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.option.ControlsOptionsScreen;
import net.minecraft.client.gui.screen.option.GameOptionsScreen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.option.GameOptions;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import tools.redstone.redstonetools.macros.gui.screen.MacroSelectScreen;

@Mixin(ControlsOptionsScreen.class)
public class AddMacroButtonMixin extends GameOptionsScreen {
    public AddMacroButtonMixin(Screen parent, GameOptions gameOptions, Text title) {
        super(parent, gameOptions, title);
    }

    @Inject(method = "init", at = @At("TAIL"))
    public void init(CallbackInfo ci) {
        this.addDrawableChild(new ButtonWidget(this.width / 2 + 5, this.height / 6 + 36, 150, 20, Text.of("Macros..."), (button) -> {
            this.client.setScreen(new MacroSelectScreen(this,super.gameOptions,Text.of("Macros")));
        }));
    }
}
