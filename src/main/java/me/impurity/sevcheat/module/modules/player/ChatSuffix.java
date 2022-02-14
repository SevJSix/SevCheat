package me.impurity.sevcheat.module.modules.player;

import me.impurity.sevcheat.event.PacketSendingEvent;
import me.impurity.sevcheat.mixin.mixins.ICPacketChatMessage;
import me.impurity.sevcheat.module.Category;
import me.impurity.sevcheat.module.Module;
import net.minecraft.network.play.client.CPacketChatMessage;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.lwjgl.input.Keyboard;

public class ChatSuffix extends Module {

    public static String suffix =  "\uA731" + "\u1D07" + "\u1D20" + "\u029C" + "\u1D00" + "\u1D04" + "\u1D0B";

    public ChatSuffix() {
        super("ChatSuffix", Category.CLIENT, "add suffix to your chat messages");
    }

    public static void setSuffix(String suffix) {
        ChatSuffix.suffix = suffix;
    }

    @SubscribeEvent
    public void onPacketSend(PacketSendingEvent event) {
        if (event.getPacket() instanceof CPacketChatMessage) {
            CPacketChatMessage packet = (CPacketChatMessage) event.getPacket();
            String msg = packet.getMessage();
            if (msg.startsWith("/") || msg.startsWith("?") || msg.startsWith("!")) return;
            msg = msg + " \u23d0 " + suffix;
            ((ICPacketChatMessage) packet).setMessage(msg);
        }
    }
}
