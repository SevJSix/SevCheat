package me.impurity.sevcheat.module.modules.render;

import me.impurity.sevcheat.module.Category;
import me.impurity.sevcheat.module.Module;
import me.impurity.sevcheat.util.BlockUtil;
import me.impurity.sevcheat.util.GraphicUtil;
import me.impurity.sevcheat.util.HoleUtil;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3i;

import java.awt.*;

public class HoleEsp extends Module {

    public static int range = 7;
    public static double height = 0.0D;
    private final float red = 1.0F;
    private final float green = 0.0F;
    private final float blue = 0.0F;
    private final float alpha = 100.0F / 255.0F;
    private final float safeRed = 0.0F;
    private final float safeGreen = 1.0F;
    private final float safeBlue = 0.0F;
    private final float safeAlpha = 100.0F / 255.0F;

    public HoleEsp() {
        super("HoleESP", Category.RENDER, "highlights where holes are");
    }

    public static void setRange(int range) {
        HoleEsp.range = range;
    }

    public static void setHeight(double height) {
        HoleEsp.height = height;
    }

    @Override
    public void onRender3d() {
        if (mc.world == null || mc.getRenderViewEntity() == null) return;
        Vec3i playerPos = new Vec3i(mc.getRenderViewEntity().posX, mc.getRenderViewEntity().posY, mc.getRenderViewEntity().posZ);
        for (int x = playerPos.getX() - HoleEsp.range; x < playerPos.getX() + HoleEsp.range; ++x) {
            for (int z = playerPos.getZ() - HoleEsp.range; z < playerPos.getZ() + HoleEsp.range; ++z) {
                for (int y = playerPos.getY() + HoleEsp.range; y > playerPos.getY() - HoleEsp.range; --y) {
                    BlockPos pos = new BlockPos(x, y, z);
                    if (HoleUtil.checkValidStage1(pos)) {
                        if (HoleUtil.isSafeHole(pos)) {
                            GraphicUtil.drawFilledBox(pos, HoleEsp.height, new Color(safeRed, safeGreen, safeBlue, safeAlpha));
                            GraphicUtil.drawBlockOutline(pos, HoleEsp.height, new Color(safeRed, safeGreen, safeBlue, 0.8F), new Color(safeRed, safeGreen, safeBlue, 0.8F));
                        } else if (HoleUtil.isUnsafeHole(pos)) {
                            GraphicUtil.drawFilledBox(pos, HoleEsp.height, new Color(red, green, blue, alpha));
                            GraphicUtil.drawBlockOutline(pos, HoleEsp.height, new Color(red, green, blue, 0.8F), new Color(red, green, blue, 0.8F));
                        }
                    }
                }
            }
        }
    }
}
