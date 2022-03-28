package me.impurity.sevcheat.util;

import me.impurity.sevcheat.module.modules.combat.BedAura;
import me.impurity.sevcheat.module.modules.player.FakePlayer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;

public class BedUtil implements McWrapper {

    public static EnumFacing[] faces = new EnumFacing[]{EnumFacing.NORTH, EnumFacing.EAST, EnumFacing.SOUTH, EnumFacing.WEST};

    public static boolean isPlayerInHoleAndAbleToBeBedded(EntityPlayer entityPlayer) {
        return (HoleUtil.isPlayerInHole(entityPlayer) && mc.world.getBlockState(new BlockPos(entityPlayer.getPositionVector()).add(0, 1, 0)).getMaterial().isReplaceable())
                || mc.world.getBlockState(new BlockPos(entityPlayer.getPositionVector()).add(0, 1, 0)).getBlock().equals(Blocks.BED);
    }

    public static boolean isPlayerAbleToBeFootPlaceBedded(EntityPlayer entityPlayer) {
        for (BlockPos pos : getSurroundPos(entityPlayer)) {
            if (canPlaceABed(pos)) {
                return true;
            }
        }
        return false;
    }

    public static BlockPos[] getSurroundPos(EntityPlayer entityPlayer) {
        return new BlockPos[]{
                new BlockPos(entityPlayer.getPositionVector()).north(),
                new BlockPos(entityPlayer.getPositionVector()).east(),
                new BlockPos(entityPlayer.getPositionVector()).south(),
                new BlockPos(entityPlayer.getPositionVector()).west()};
    }

    public static boolean canPlaceABed(BlockPos pos) {
        return mc.world.getBlockState(new BlockPos(pos.down())).getMaterial().isSolid() && mc.world.getBlockState(pos).getMaterial().isReplaceable() || mc.world.getBlockState(pos).getBlock().equals(Blocks.AIR)
                || mc.world.getBlockState(pos).getBlock().equals(Blocks.BED);
    }

    public static boolean isAFreeSpacePosition(BlockPos pos) {
        return mc.world.getBlockState(pos).getMaterial().isReplaceable() || mc.world.getBlockState(pos).getBlock().equals(Blocks.AIR);
    }

    public static BlockPos evaluateBestBedPos(EntityPlayer target) {
        BlockPos targetPos = new BlockPos(target.getPositionVector());
        if (isAFreeSpacePosition(targetPos.down()) && canPlaceABed(targetPos.down())) {
            targetPos = targetPos.down();
        }
        for (EnumFacing face : faces) {
            BlockPos attemptPos = new BlockPos(targetPos).offset(face);
            if (canPlaceABed(attemptPos)) {
                BedAura.facing = face;
                return targetPos;
            }
        }
        return null;
    }

    public static EnumFacing getPlaceDirectionBasedFromBlockPos(BlockPos pos) {
        for (EnumFacing face : faces) {
            if (isAFreeSpacePosition(pos.offset(face))) {
                return face;
            }
        }
        return null;
    }

    public static EnumFacing getFacingFromPosIndex(int i) {
        switch (i) {
            case 0:
            case 4:
            case 8:
                return EnumFacing.NORTH;
            case 1:
            case 5:
            case 9:
                return EnumFacing.EAST;
            case 2:
            case 6:
            case 10:
                return EnumFacing.SOUTH;
            case 3:
            case 7:
            case 11:
                return EnumFacing.WEST;
        }
        return null;
    }
}
