package me.impurity.sevcheat.module.modules.combat;

import me.impurity.sevcheat.module.Category;
import me.impurity.sevcheat.module.Module;
import me.impurity.sevcheat.util.PlayerUtil;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.init.Items;
import net.minecraft.inventory.ClickType;
import net.minecraft.item.Item;

public class AutoTotem extends Module {
    public AutoTotem() {
        super("AutoTotem", Category.COMBAT, "automatically equip totems");
    }

    @Override
    public void onQuickUpdate() {
        if (mc.world != null && mc.player.getHeldItemOffhand().getItem() != Items.TOTEM_OF_UNDYING && !(mc.currentScreen instanceof GuiContainer)) {
            int totemSlot = PlayerUtil.findInv(Items.TOTEM_OF_UNDYING);
            if (totemSlot != -1) {
                PlayerUtil.swapItem(totemSlot, 45);
            }
        }
    }
}
