package me.impurity.sevcheat.module.modules.combat;

import io.netty.buffer.Unpooled;
import me.impurity.sevcheat.SevCheat;
import me.impurity.sevcheat.module.Category;
import me.impurity.sevcheat.module.Module;
import me.impurity.sevcheat.util.TimerUtil;
import me.impurity.sevcheat.util.Utils;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.inventory.ClickType;
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
        if (mc.world == null) return;
        try {
            timer.reset();
            PacketBuffer buffer = new PacketBuffer(Unpooled.buffer());
            buffer.writeBlockPos(mc.objectMouseOver.getBlockPos());
            CPacketCustomPayload packet = new CPacketCustomPayload(Utils.getChannelPayload(), buffer);
            mc.player.connection.sendPacket(packet);
            mc.player.swingArm(EnumHand.MAIN_HAND);
        } catch (Throwable t) {
            Utils.sendMessage("An error occurred, disabling...");
            this.disable();
        }
    }

    @Override
    public void onUpdate() {
        if (mc.world == null) return;
        try {
            if (timer.hasReached(3000) && this.isEnabled()) {
                timer.reset();
                this.disable();
            }
            if (mc.currentScreen != null) {
                if (!((GuiContainer) mc.currentScreen).inventorySlots.getSlot(0).getStack().isEmpty()) {
                    mc.player.inventory.currentItem = 8;
                    mc.playerController.windowClick(mc.player.openContainer.windowId, 0, mc.player.inventory.currentItem, ClickType.SWAP, mc.player);
                    if (!SevCheat.moduleManager.getModuleByName("SecretClose").isEnabled())
                        SevCheat.moduleManager.getModuleByName("SecretClose").enable();
                    mc.player.closeScreen();
                    this.disable();
                }
            }
        } catch (Throwable ignored) {
        }
    }
}
