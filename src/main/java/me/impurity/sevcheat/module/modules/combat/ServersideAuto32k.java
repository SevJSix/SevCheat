package me.impurity.sevcheat.module.modules.combat;

import io.netty.buffer.Unpooled;
import me.impurity.sevcheat.module.Category;
import me.impurity.sevcheat.module.Module;
import me.impurity.sevcheat.util.TimerUtil;
import me.impurity.sevcheat.util.Utils;
import net.minecraft.client.gui.GuiHopper;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.client.CPacketCustomPayload;
import net.minecraft.util.EnumHand;

public class ServersideAuto32k extends Module {

    private final TimerUtil timer = new TimerUtil();

    public ServersideAuto32k() {
        super("Serverside32k", Category.COMBAT, "Automatically use 32ks with hoppers");
    }

    @Override
    public void onEnable() {
        try {
            timer.reset();
            PacketBuffer buffer = new PacketBuffer(Unpooled.buffer());
            buffer.writeBlockPos(mc.objectMouseOver.getBlockPos());
            CPacketCustomPayload packet = new CPacketCustomPayload(Utils.getChannelPayload(), buffer);
            mc.player.connection.sendPacket(packet);
        } catch (Throwable t) {
            Utils.sendMessage("An error occurred, disabling...");
            this.disable();
        }
    }

    @Override
    public void onTick() {
        try {
            if (timer.hasReached(500) && this.isEnabled()) {
                timer.reset();
                this.disable();
                return;
            }
            mc.player.swingArm(EnumHand.MAIN_HAND);
            if (mc.currentScreen != null && mc.currentScreen instanceof GuiHopper) {
                mc.player.closeScreen();
                this.disable();
            }
        } catch (Throwable t) {
            Utils.sendMessage("An error occurred, disabling...");
            this.disable();
        }
    }
}
