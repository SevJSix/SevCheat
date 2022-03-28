package me.impurity.sevcheat.util;

import com.mojang.realmsclient.gui.ChatFormatting;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.util.text.ChatType;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;

import java.awt.*;

public class Utils {

    public static final Minecraft mc;
    public static String ChannelPayload = "auto32k";
    public static String prefix = ChatFormatting.WHITE + "<" + TextFormatting.GOLD + "SevCheat" + ChatFormatting.RESET + ChatFormatting.WHITE + "> ";

    static {
        mc = Minecraft.getMinecraft();
    }

    public static String getChannelPayload() {
        return ChannelPayload;
    }

    public static void setChannelPayload(String channelPayload) {
        ChannelPayload = channelPayload;
    }

    public static void sendMessage(String msg) {
        mc.ingameGUI.addChatMessage(ChatType.CHAT, new TextComponentString(prefix + msg));
    }

    public static void sendMessageWithoutPrefix(String msg) {
        mc.ingameGUI.addChatMessage(ChatType.CHAT, new TextComponentString(msg));
    }

    public static void sendPacketDirectly(Packet<?> packet) {
        if (mc.getConnection() != null) {
            NetworkManager networkManager = mc.getConnection().getNetworkManager();
            networkManager.channel().writeAndFlush(packet);
        }
    }

    public static int rainbow() {
        double rainbowState = Math.ceil((System.currentTimeMillis()) / 20.0);
        rainbowState %= 360;
        return Color.getHSBColor((float) (rainbowState / 360.0F), 1.0F, 1F).getRGB();
    }

    public static boolean is32k(ItemStack stack) {
        if (stack.getTagCompound() == null) {
            return false;
        }
        NBTTagList enchants = (NBTTagList) stack.getTagCompound().getTag("ench");
        if (enchants == null) {
            return false;
        }
        int i = 0;
        while (i < enchants.tagCount()) {
            final NBTTagCompound enchant = enchants.getCompoundTagAt(i);
            if (enchant.getInteger("id") == 16) {
                final int lvl = enchant.getInteger("lvl");
                if (lvl >= 50) {
                    return true;
                }
                break;
            } else {
                ++i;
            }
        }
        return false;
    }
}
