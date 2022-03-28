package me.impurity.sevcheat.module.modules.combat;

import me.impurity.sevcheat.module.Category;
import me.impurity.sevcheat.module.Module;
import me.impurity.sevcheat.util.*;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.network.play.client.CPacketEntityAction;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.text.TextFormatting;

import java.util.Comparator;

public class AutoTrapBed extends Module {

    private final TimerUtil timer = new TimerUtil();
    private BlockPos[] placePositions;

    public AutoTrapBed() {
        super("AutoTrapBed", Category.COMBAT, "traps players in an area for bed aura to do its work");
    }

    @Override
    public void onEnable() {
        timer.reset();
        EntityPlayer target = mc.world.playerEntities.stream()
                .filter(p -> mc.player.getDistance(p) < 6)
                .filter(p -> !FileUtil.isFriend(p.getName()))
                .filter(p -> !p.equals(mc.player))
                .filter(p -> p.getHealth() > 0.0F)
                .filter(p -> !p.getPosition().equals(mc.player.getPosition()))
                .min(Comparator.comparing(p -> mc.player.getDistance(p))).orElse(null);

        int obbiSlot = PlayerUtil.find(Blocks.OBSIDIAN);
        if (obbiSlot == -1) {
            this.disable();
            Utils.sendMessageWithoutPrefix(TextFormatting.RED + "No obsidian in hotbar!");
            return;
        }
        if (target != null) {
            if (!target.onGround) {
                this.disable();
                Utils.sendMessageWithoutPrefix(TextFormatting.RED + "Target is not on the ground");
                return;
            }
            this.placePositions = new BlockPos[]{
                    new BlockPos(target.getPositionVector()).north(),
                    new BlockPos(target.getPositionVector()).east(),
                    new BlockPos(target.getPositionVector()).south(),
                    new BlockPos(target.getPositionVector()).west(),
                    new BlockPos(target.getPositionVector()).north().add(0, 1, 0),
                    new BlockPos(target.getPositionVector()).east().add(0, 1, 0),
                    new BlockPos(target.getPositionVector()).south().add(0, 1, 0),
                    new BlockPos(target.getPositionVector()).west().add(0, 1, 0)};
            for (BlockPos placePosition : placePositions) {
                if (isReplaceablePos(placePosition)) {
                    mc.player.inventory.currentItem = obbiSlot;
                    placeBlock(placePosition, EnumFacing.DOWN, true);
                }
            }
            this.disable();
        } else {
            this.disable();
            Utils.sendMessageWithoutPrefix(TextFormatting.RED + "No Target!");
        }
    }

    @Override
    public void onDisable() {
        timer.reset();
    }

    private boolean isReplaceablePos(BlockPos pos) {
        return mc.world.getBlockState(pos).getMaterial().isReplaceable() || mc.world.getBlockState(pos).getBlock().equals(Blocks.AIR);
    }

    public void placeBlock(BlockPos pos, EnumFacing face, boolean swing) {
        BlockPos adj = pos.offset(face);
        EnumFacing opposite = face.getOpposite();
        mc.player.connection.sendPacket(new CPacketEntityAction(mc.player, CPacketEntityAction.Action.START_SNEAKING));
        Vec3d hitVec = new Vec3d(adj).add(0.5, 0.5, 0.5).add(new Vec3d(opposite.getDirectionVec()).scale(0.5));
        mc.playerController.processRightClickBlock(mc.player, mc.world, adj, opposite, hitVec, EnumHand.MAIN_HAND);
        if (swing) {
            mc.player.swingArm(EnumHand.MAIN_HAND);
        }
        mc.player.connection.sendPacket(new CPacketEntityAction(mc.player, CPacketEntityAction.Action.STOP_SNEAKING));
    }
}
