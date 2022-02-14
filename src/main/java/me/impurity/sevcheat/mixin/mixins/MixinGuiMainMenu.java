package me.impurity.sevcheat.mixin.mixins;

import me.impurity.sevcheat.SevCheat;
import net.minecraft.client.gui.GuiMainMenu;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.util.text.TextFormatting;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GuiMainMenu.class)
public class MixinGuiMainMenu extends GuiScreen {

    @Inject(method = "drawScreen", at = @At(value = "TAIL"), cancellable = true)
    public void drawScreen(int mouseX, int mouseY, float partialTicks, CallbackInfo ci) {
        mc.fontRenderer.drawStringWithShadow("SevCheat" + TextFormatting.RESET + TextFormatting.GRAY + " " + SevCheat.VERSION,1, 1, 0x9FE2BF);
        mc.fontRenderer.drawStringWithShadow(TextFormatting.WHITE + "made by SevJ6", 1, mc.fontRenderer.FONT_HEIGHT + 3, -1);
    }
}
