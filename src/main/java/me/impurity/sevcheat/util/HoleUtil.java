package me.impurity.sevcheat.util;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;

import java.util.ArrayList;
import java.util.List;

public class HoleUtil {

    public static Minecraft mc;

    static {
        mc = Minecraft.getMinecraft();
    }

    public static boolean isSafeHole(BlockPos pos) {
        return isBedrock(pos.north())
                && isBedrock(pos.east())
                && isBedrock(pos.south())
                && isBedrock(pos.west())
                && isBedrock(pos.down());
    }

    public static boolean isUnsafeHole(BlockPos pos) {
        return isUnsafe(pos.north())
                && isUnsafe(pos.east())
                && isUnsafe(pos.south())
                && isUnsafe(pos.west())
                && isUnsafe(pos.down());
    }

    public static boolean isUnsafe(BlockPos pos) {
        return BlockUtil.isBlockUnSafe(HoleUtil.mc.world.getBlockState(pos).getBlock());
    }

    public static boolean isBedrock(BlockPos pos) {
        return HoleUtil.mc.world.getBlockState(pos).getBlock() == Blocks.BEDROCK;
    }

    public static boolean isPlayerInHole(EntityPlayer entityPlayer) {
        BlockPos pos = new BlockPos(entityPlayer.getPositionVector());
        return isSafeHole(pos) || isUnsafeHole(pos);
    }

    public static boolean checkValidStage1(BlockPos pos) {
        return HoleUtil.mc.world != null
                && HoleUtil.mc.world.getBlockState(pos).getBlock().equals(Blocks.AIR)
                && HoleUtil.mc.world.getBlockState(pos.add(0, 1, 0)).getBlock().equals(Blocks.AIR)
                && HoleUtil.mc.world.getBlockState(pos.add(0, 2, 0)).getBlock().equals(Blocks.AIR)
                && !pos.equals(new BlockPos(HoleUtil.mc.player.posX, HoleUtil.mc.player.posY, HoleUtil.mc.player.posZ));
    }
}
