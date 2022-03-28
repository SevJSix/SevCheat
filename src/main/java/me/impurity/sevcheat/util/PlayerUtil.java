package me.impurity.sevcheat.util;

import net.minecraft.block.Block;
import net.minecraft.inventory.ClickType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

public class PlayerUtil implements McWrapper {

    public static boolean holdingItem(Class clazz) {
        boolean result = false;
        ItemStack stack = mc.player.getHeldItemMainhand();
        result = isInstanceOf(stack, clazz);
        if (!result) {
            ItemStack offhand = mc.player.getHeldItemOffhand();
            result = isInstanceOf(stack, clazz);
        }
        return result;
    }

    public static boolean isInstanceOf(ItemStack stack, Class clazz) {
        if (stack == null) {
            return false;
        }
        Item item = stack.getItem();
        if (clazz.isInstance(item)) {
            return true;
        }
        if (item instanceof ItemBlock) {
            Block block = Block.getBlockFromItem(item);
            return clazz.isInstance(block);
        }
        return false;
    }

    public static int find(Item item) {
        for (int i = 0; i < 9; ++i) {
            ItemStack is = mc.player.inventory.getStackInSlot(i);
            if (is != ItemStack.EMPTY) {
                Item it = is.getItem();
                if (it.equals(item)) {
                    return i;
                }
            }
        }
        return -1;
    }

    public static int find(final Block block) {
        for (int i = 0; i < 9; ++i) {
            final ItemStack is = mc.player.inventory.getStackInSlot(i);
            if (is != ItemStack.EMPTY) {
                final Item it = is.getItem();
                if (it instanceof ItemBlock && ((ItemBlock)it).getBlock().equals(block)) {
                    return i;
                }
            }
        }
        return -1;
    }

    public static int findInv(Item item) {
        for (int i = 0; i < 36; ++i) {
            Item slot = mc.player.inventory.getStackInSlot(i).getItem();
            if (slot.equals(item)) {
                if (i < 9) {
                    i += 36;
                }
                return i;
            }
        }
        return -1;
    }

    public static void swapItem(int from, int to) {
        mc.playerController.windowClick(mc.player.inventoryContainer.windowId, from, 0, ClickType.PICKUP, mc.player);
        mc.playerController.windowClick(mc.player.inventoryContainer.windowId, to, 0, ClickType.PICKUP, mc.player);
        mc.playerController.windowClick(mc.player.inventoryContainer.windowId, from, 0, ClickType.PICKUP, mc.player);
    }
}
