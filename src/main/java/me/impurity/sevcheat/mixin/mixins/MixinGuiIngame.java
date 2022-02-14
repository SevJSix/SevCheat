package me.impurity.sevcheat.mixin.mixins;

import me.impurity.sevcheat.SevCheat;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiIngame;
import net.minecraft.util.text.TextFormatting;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GuiIngame.class)
public class MixinGuiIngame extends Gui {

    Minecraft mc = Minecraft.getMinecraft();

    @Inject(method = "<init>", at = @At("RETURN"))
    public void init(Minecraft mcIn, CallbackInfo ci) {
        mc.fontRenderer.drawStringWithShadow("SevCheat" + TextFormatting.RESET + TextFormatting.GRAY + " " + SevCheat.VERSION,1, 1, 0x9FE2BF);
        mc.fontRenderer.drawStringWithShadow(TextFormatting.WHITE + "made by SevJ6", 1, mc.fontRenderer.FONT_HEIGHT + 3, -1);
    }
}
