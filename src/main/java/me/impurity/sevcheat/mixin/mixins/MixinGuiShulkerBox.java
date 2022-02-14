package me.impurity.sevcheat.mixin.mixins;

import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.inventory.GuiShulkerBox;
import net.minecraft.inventory.IInventory;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(GuiShulkerBox.class)
public class MixinGuiShulkerBox {

    @Shadow @Final private IInventory inventory;

    @Redirect(method = "drawGuiContainerForegroundLayer", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/FontRenderer;drawString(Ljava/lang/String;III)I", ordinal = 0))
    public int drawString(FontRenderer fontRenderer, String text, int x, int y, int color) {
        return fontRenderer.drawString(this.inventory.getDisplayName().getUnformattedText(), 8, 6, 0xF08080);
    }
}
