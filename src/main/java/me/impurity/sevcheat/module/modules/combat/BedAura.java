package me.impurity.sevcheat.module.modules.combat;

import me.impurity.sevcheat.event.PacketSendingEvent;
import me.impurity.sevcheat.mixin.mixins.ICPacketPlayer;
import me.impurity.sevcheat.module.Category;
import me.impurity.sevcheat.module.Module;
import me.impurity.sevcheat.util.*;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.inventory.ClickType;
import net.minecraft.network.play.client.CPacketEntityAction;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.network.play.client.CPacketPlayerTryUseItemOnBlock;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.awt.*;
import java.util.Comparator;

public class BedAura extends Module {

    public static EnumFacing facing;
    private final TimerUtil timer = new TimerUtil();
    public int bedSlot;
    public BlockPos bedRenderPos1;
    public BlockPos bedRenderPos2;
    public BlockPos placeTarget;
    private boolean holePlace;

    public BedAura() {
        super("BedAura", Category.COMBAT, "Automatically fuck people with beds");
    }

    @Override
    public void onEnable() {
        this.bedSlot = -1;
        this.bedRenderPos1 = null;
        this.bedRenderPos2 = null;
        this.placeTarget = null;
        BedAura.facing = null;
        this.holePlace = false;
    }

    @Override
    public void onDisable() {
        this.bedSlot = -1;
        this.bedRenderPos1 = null;
        this.bedRenderPos2 = null;
        this.placeTarget = null;
        BedAura.facing = null;
        this.holePlace = false;
    }

    @Override
    public void onUpdate() {
        if (mc.world == null) return;
        this.bedSlot = PlayerUtil.find(Items.BED);
        if (bedSlot == -1) {
            int bedInvSlot = PlayerUtil.findInv(Items.BED);
            if (bedInvSlot == -1) {
                this.bedRenderPos1 = null;
                this.bedRenderPos2 = null;
                this.placeTarget = null;
                BedAura.facing = null;
                return;
            }
            mc.playerController.windowClick(mc.player.inventoryContainer.windowId, bedInvSlot, 0, ClickType.SWAP, mc.player);
            this.bedSlot = mc.player.inventory.currentItem;
        }
        EntityPlayer target = mc.world.playerEntities.stream()
                .filter(p -> mc.player.getDistance(p) < 6)
                .filter(p -> !FileUtil.isFriend(p.getName()))
                .filter(p -> !p.equals(mc.player))
                .filter(p -> p.getHealth() > 0.0F)
                .filter(p -> !p.getPosition().equals(mc.player.getPosition()))
                .min(Comparator.comparing(p -> mc.player.getDistance(p))).orElse(null);
        if (target != null) {
            if (BedUtil.isPlayerInHoleAndAbleToBeBedded(target)) {
                BlockPos[] holePositions = new BlockPos[]{
                        new BlockPos(target.getPositionVector()).north(),
                        new BlockPos(target.getPositionVector()).east(),
                        new BlockPos(target.getPositionVector()).south(),
                        new BlockPos(target.getPositionVector()).west(),
                        new BlockPos(target.getPositionVector()).north().add(0, 1, 0),
                        new BlockPos(target.getPositionVector()).east().add(0, 1, 0),
                        new BlockPos(target.getPositionVector()).south().add(0, 1, 0),
                        new BlockPos(target.getPositionVector()).west().add(0, 1, 0),
                        new BlockPos(target.getPositionVector()).north().add(0, 2, 0),
                        new BlockPos(target.getPositionVector()).east().add(0, 2, 0),
                        new BlockPos(target.getPositionVector()).south().add(0, 2, 0),
                        new BlockPos(target.getPositionVector()).west().add(0, 2, 0)};
                for (int i = 0; i < holePositions.length; i++) {
                    if (BedUtil.canPlaceABed(holePositions[i])) {
                        this.placeTarget = holePositions[i];
                        BedAura.facing = BedUtil.getFacingFromPosIndex(i);
                        break;
                    }
                }
                if (this.placeTarget == null) return;
                if (timer.hasReached(100)) {
                    doPlace(this.placeTarget, BedAura.facing, true);
                    timer.reset();
                }

            } else if (BedUtil.isPlayerAbleToBeFootPlaceBedded(target)) {

                this.placeTarget = BedUtil.evaluateBestBedPos(target);
                if (this.placeTarget == null || facing == null) {
                    this.bedRenderPos1 = null;
                    this.bedRenderPos2 = null;
                    this.placeTarget = null;
                    BedAura.facing = null;
                    return;
                }
                if (timer.hasReached(100)) {
                    doPlace(this.placeTarget, BedAura.facing, false);
                    timer.reset();
                }
            }
        } else {
            this.bedRenderPos1 = null;
            this.bedRenderPos2 = null;
            this.placeTarget = null;
            BedAura.facing = null;
        }
    }

    public void doPlace(BlockPos placeTarget, EnumFacing facing, boolean holePlace) {
        if (this.placeTarget == null || BedAura.facing == null) return;
        mc.player.inventory.currentItem = bedSlot;
        if (holePlace) {
            facing = facing.getOpposite();
        }
        Utils.sendPacketDirectly(new CPacketPlayer.Rotation(getYaw(facing), 0.0F, mc.player.onGround));
        handleBedPlacement(this.placeTarget, facing);
        timer.reset();
    }

    @SubscribeEvent
    public void onSend(PacketSendingEvent event) {
        if (event.getPacket() instanceof CPacketPlayer.Rotation && BedAura.facing != null) {
            CPacketPlayer.Rotation rotation = (CPacketPlayer.Rotation) event.packet;
            ((ICPacketPlayer) rotation).setYaw(getYaw(BedAura.facing));
        }
    }

    public float getYaw(EnumFacing facing) {
        switch (facing) {
            case NORTH:
                return 180.0F;
            case EAST:
                return -90.0F;
            case WEST:
                return 90.0F;
        }
        return 0.0F;
    }

    @Override
    public void onRender3d() {
        if (mc.world != null && this.bedRenderPos1 != null && this.bedRenderPos2 != null) {
            GraphicUtil.drawBlockOutline(this.bedRenderPos1, -0.45, new Color(1.0F, 0.0F, 0.5F, 1.0F), new Color(1.0F, 0.0F, 0.5F, 1.0F));
            GraphicUtil.drawBlockOutline(this.bedRenderPos2, -0.45, new Color(1.0F, 0.0F, 0.5F, 1.0F), new Color(1.0F, 0.0F, 0.5F, 1.0F));
            GraphicUtil.drawFilledBox(this.bedRenderPos1, -0.45, new Color(1.0F, 0.0F, 0.0F, 0.2F));
            GraphicUtil.drawFilledBox(this.bedRenderPos2, -0.45, new Color(1.0F, 0.0F, 0.0F, 0.2F));
        }
    }

    public void blowUpBed(BlockPos pos) {
        Utils.sendPacketDirectly(new CPacketPlayerTryUseItemOnBlock(pos, EnumFacing.DOWN, EnumHand.MAIN_HAND, 0, 0, 0));
        mc.player.swingArm(EnumHand.MAIN_HAND);
    }

    public void handleBedPlacement(BlockPos pos, EnumFacing facing) {
        placeBlock(pos, EnumFacing.DOWN);
        blowUpBed(pos);
        this.bedRenderPos1 = pos;
        this.bedRenderPos2 = pos.offset(facing);
    }

    public void placeBlock(BlockPos pos, EnumFacing face) {
        BlockPos adj = pos.offset(face);
        EnumFacing opposite = face.getOpposite();
        mc.player.connection.sendPacket(new CPacketEntityAction(mc.player, CPacketEntityAction.Action.START_SNEAKING));
        Vec3d hitVec = new Vec3d(adj).add(0.5, 0.5, 0.5).add(new Vec3d(opposite.getDirectionVec()).scale(0.5));
        mc.playerController.processRightClickBlock(mc.player, mc.world, adj, opposite, hitVec, EnumHand.MAIN_HAND);
        mc.player.connection.sendPacket(new CPacketEntityAction(mc.player, CPacketEntityAction.Action.STOP_SNEAKING));
    }
}
