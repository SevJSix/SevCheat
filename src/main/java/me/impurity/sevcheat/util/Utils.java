package me.impurity.sevcheat.util;

import com.mojang.realmsclient.gui.ChatFormatting;
import me.impurity.sevcheat.module.modules.render.HUD;
import net.minecraft.client.Minecraft;
import net.minecraft.util.text.ChatType;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;

import java.awt.*;
import java.util.Objects;

public class Utils {

    private static final Minecraft mc = Minecraft.getMinecraft();
    public static String ChannelPayload = "auto32k";
    public static String prefix = ChatFormatting.WHITE + "<" + TextFormatting.GOLD + "SevCheat" + ChatFormatting.RESET + ChatFormatting.WHITE + "> ";

    public static String getChannelPayload() {
        return ChannelPayload;
    }

    public static void setChannelPayload(String channelPayload) {
        ChannelPayload = channelPayload;
    }

    public static void sendMessage(String msg) {
        mc.ingameGUI.addChatMessage(ChatType.CHAT, new TextComponentString(prefix + msg));
    }

    public static int rainbow() {
        double rainbowState = Math.ceil((System.currentTimeMillis()) / 20.0);
        rainbowState %= 360;
        return Color.getHSBColor((float) (rainbowState / 360.0F), 1.0F, 1F).getRGB();
    }
}
