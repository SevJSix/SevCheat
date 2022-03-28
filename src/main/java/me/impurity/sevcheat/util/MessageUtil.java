package me.impurity.sevcheat.util;

import net.minecraft.client.Minecraft;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;

import java.awt.*;
import java.util.concurrent.ThreadLocalRandom;
import java.util.regex.Pattern;

public final class MessageUtil {
    public static final String prefix;
    public static final Minecraft mc;

    public static void sendMessage(Object message, Object... arguments) {
        sendMessageWithDeletionID(message, 6969, arguments);
    }

    public static void sendMessageWithDeletionID(Object message, int id, Object... arguments) {
        String stringMessage = message.toString();
        for (Object argument : arguments) {
            String regex = Pattern.quote("{}");
            stringMessage = stringMessage.replaceFirst(regex, argument.toString());
        }
        TextComponentString textComponent = new TextComponentString(MessageUtil.prefix + stringMessage);
        MessageUtil.mc.ingameGUI.getChatGUI().printChatMessageWithOptionalDeletion(textComponent, id);
    }

    static {
        mc = Minecraft.getMinecraft();
        prefix = TextFormatting.WHITE + "<" + TextFormatting.AQUA + "SevCheat" + TextFormatting.WHITE + "> ";
    }
}

