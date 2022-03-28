package me.impurity.sevcheat.module.modules.combat;

import me.impurity.sevcheat.event.TotemPopEvent;
import me.impurity.sevcheat.module.Category;
import me.impurity.sevcheat.module.Module;
import me.impurity.sevcheat.util.FileUtil;
import me.impurity.sevcheat.util.Utils;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.play.client.CPacketUseEntity;
import net.minecraft.util.EnumHand;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class Aura32k extends Module {

    public EntityPlayer target;

    public Aura32k() {
        super("Aura", Category.COMBAT, "hit people fast with 32k's and kill them");
    }

    @Override
    public void onTick() {
        if (mc.world == null) return;
        if (!Utils.is32k(mc.player.inventory.getCurrentItem())) return;
        for (EntityPlayer player : mc.world.playerEntities) {
            if (FileUtil.isFriend(player.getName())) return;
            if (player == mc.player) continue;
            if (mc.player.getDistance(player) <= 7 && !player.isDead && player.getHealth() > 0) {
                target = player;
                mc.playerController.attackEntity(mc.player, player);
            } else {
                target = null;
            }
        }
    }

    @SubscribeEvent
    public void onTotemPop(TotemPopEvent event) {
        if (target == null) return;
        if (FileUtil.isFriend(target.getName())) return;
        if (event.getPlayer() == target) {
            for (int threads = 0; threads < 5; ++threads) {
                new Thread(() -> {
                    for (int iterations = 0; iterations < 8; ++iterations) {
                        Utils.sendPacketDirectly(new CPacketUseEntity(this.target));
                        this.mc.player.swingArm(EnumHand.MAIN_HAND);
                    }
                }).start();
            }
        }
    }
}
