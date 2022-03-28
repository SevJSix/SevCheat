package me.impurity.sevcheat.util;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import org.lwjgl.opengl.GL11;

import java.awt.*;

public class GraphicUtil {

    public static Minecraft mc;
    public static BufferBuilder bufferbuilder;
    public static Tessellator tessellator;

    static {
        mc = Minecraft.getMinecraft();
        tessellator = Tessellator.getInstance();
        bufferbuilder = GraphicUtil.tessellator.getBuffer();
    }

    public static void glSetup3d() {
        GlStateManager.pushMatrix();
        GlStateManager.enableBlend();
        GlStateManager.disableDepth();
        GlStateManager.tryBlendFuncSeparate(770, 771, 0, 1);
        GlStateManager.disableTexture2D();
        GlStateManager.depthMask(false);
        GL11.glEnable(2848);
        GL11.glHint(3154, 4354);
        GL11.glLineWidth(2.0f);
    }

    public static void glShutdown3d() {
        GL11.glDisable(2848);
        GlStateManager.depthMask(true);
        GlStateManager.enableDepth();
        GlStateManager.enableTexture2D();
        GlStateManager.disableBlend();
        GlStateManager.popMatrix();
    }

    public static void drawFilledBox(final BlockPos blockPos, final double height, final Color color) {
        glSetup3d();
        final AxisAlignedBB box = new AxisAlignedBB(blockPos.getX() - GraphicUtil.mc.getRenderManager().viewerPosX, blockPos.getY() - GraphicUtil.mc.getRenderManager().viewerPosY, blockPos.getZ() - GraphicUtil.mc.getRenderManager().viewerPosZ, blockPos.getX() + 1 - GraphicUtil.mc.getRenderManager().viewerPosX, blockPos.getY() + 1 - GraphicUtil.mc.getRenderManager().viewerPosY, blockPos.getZ() + 1 - GraphicUtil.mc.getRenderManager().viewerPosZ);
        drawFilledBox(GraphicUtil.bufferbuilder, box.minX, box.minY, box.minZ, box.maxX, box.maxY + height, box.maxZ, color.getRed() / 255.0f, color.getGreen() / 255.0f, color.getBlue() / 255.0f, color.getAlpha() / 255.0f);
        glShutdown3d();
    }

    public static void drawFilledBox(final BufferBuilder builder, final double minX, final double minY, final double minZ, final double maxX, final double maxY, final double maxZ, final float red, final float green, final float blue, final float alpha) {
        GraphicUtil.bufferbuilder.begin(5, DefaultVertexFormats.POSITION_COLOR);
        builder.pos(minX, minY, minZ).color(red, green, blue, 0.4f).endVertex();
        builder.pos(minX, minY, minZ).color(red, green, blue, 0.4f).endVertex();
        builder.pos(minX, minY, minZ).color(red, green, blue, 0.4f).endVertex();
        builder.pos(minX, minY, maxZ).color(red, green, blue, 0.4f).endVertex();
        builder.pos(minX, maxY, minZ).color(red, green, blue, 0.4f).endVertex();
        builder.pos(minX, maxY, maxZ).color(red, green, blue, 0.4f).endVertex();
        builder.pos(minX, maxY, maxZ).color(red, green, blue, 0.4f).endVertex();
        builder.pos(minX, minY, maxZ).color(red, green, blue, 0.4f).endVertex();
        builder.pos(maxX, maxY, maxZ).color(red, green, blue, 0.4f).endVertex();
        builder.pos(maxX, minY, maxZ).color(red, green, blue, 0.4f).endVertex();
        builder.pos(maxX, minY, maxZ).color(red, green, blue, 0.4f).endVertex();
        builder.pos(maxX, minY, minZ).color(red, green, blue, 0.4f).endVertex();
        builder.pos(maxX, maxY, maxZ).color(red, green, blue, 0.4f).endVertex();
        builder.pos(maxX, maxY, minZ).color(red, green, blue, 0.4f).endVertex();
        builder.pos(maxX, maxY, minZ).color(red, green, blue, 0.4f).endVertex();
        builder.pos(maxX, minY, minZ).color(red, green, blue, 0.4f).endVertex();
        builder.pos(minX, maxY, minZ).color(red, green, blue, 0.4f).endVertex();
        builder.pos(minX, minY, minZ).color(red, green, blue, 0.4f).endVertex();
        builder.pos(minX, minY, minZ).color(red, green, blue, 0.4f).endVertex();
        builder.pos(maxX, minY, minZ).color(red, green, blue, 0.4f).endVertex();
        builder.pos(minX, minY, maxZ).color(red, green, blue, 0.4f).endVertex();
        builder.pos(maxX, minY, maxZ).color(red, green, blue, 0.4f).endVertex();
        builder.pos(maxX, minY, maxZ).color(red, green, blue, 0.4f).endVertex();
        builder.pos(minX, maxY, minZ).color(red, green, blue, 0.4f).endVertex();
        builder.pos(minX, maxY, minZ).color(red, green, blue, 0.4f).endVertex();
        builder.pos(minX, maxY, maxZ).color(red, green, blue, 0.4f).endVertex();
        builder.pos(maxX, maxY, minZ).color(red, green, blue, 0.4f).endVertex();
        builder.pos(maxX, maxY, maxZ).color(red, green, blue, 0.4f).endVertex();
        builder.pos(maxX, maxY, maxZ).color(red, green, blue, 0.4f).endVertex();
        builder.pos(maxX, maxY, maxZ).color(red, green, blue, 0.4f).endVertex();
        GraphicUtil.tessellator.draw();
    }

    public static void drawBlockOutline(final BlockPos blockPos, final double height, final Color color, final Color endColor) {
        glSetup3d();
        final AxisAlignedBB box = new AxisAlignedBB(blockPos.getX() - GraphicUtil.mc.getRenderManager().viewerPosX, blockPos.getY() - GraphicUtil.mc.getRenderManager().viewerPosY, blockPos.getZ() - GraphicUtil.mc.getRenderManager().viewerPosZ, blockPos.getX() + 1 - GraphicUtil.mc.getRenderManager().viewerPosX, blockPos.getY() + 1 - GraphicUtil.mc.getRenderManager().viewerPosY, blockPos.getZ() + 1 - GraphicUtil.mc.getRenderManager().viewerPosZ);
        drawGradientBlockOutline(GraphicUtil.bufferbuilder, box.minX, box.minY, box.minZ, box.maxX, box.maxY + height, box.maxZ, color.getRed() / 255.0f, color.getGreen() / 255.0f, color.getBlue() / 255.0f, color.getAlpha() / 255.0f, endColor);
        glShutdown3d();
    }

    public static void drawBlockOutline(final BufferBuilder buffer, final double minX, final double minY, final double minZ, final double maxX, final double maxY, final double maxZ, final float red, final float green, final float blue, final float alpha) {
        GraphicUtil.bufferbuilder.begin(3, DefaultVertexFormats.POSITION_COLOR);
        buffer.pos(minX, minY, minZ).color(red, green, blue, 0.0f).endVertex();
        buffer.pos(minX, minY, minZ).color(red, green, blue, alpha).endVertex();
        buffer.pos(maxX, minY, minZ).color(red, green, blue, alpha).endVertex();
        buffer.pos(maxX, minY, maxZ).color(red, green, blue, alpha).endVertex();
        buffer.pos(minX, minY, maxZ).color(red, green, blue, alpha).endVertex();
        buffer.pos(minX, minY, minZ).color(red, green, blue, alpha).endVertex();
        buffer.pos(minX, maxY, minZ).color(red, green, blue, alpha).endVertex();
        buffer.pos(maxX, maxY, minZ).color(red, green, blue, alpha).endVertex();
        buffer.pos(maxX, maxY, maxZ).color(red, green, blue, alpha).endVertex();
        buffer.pos(minX, maxY, maxZ).color(red, green, blue, alpha).endVertex();
        buffer.pos(minX, maxY, minZ).color(red, green, blue, alpha).endVertex();
        buffer.pos(minX, maxY, maxZ).color(red, green, blue, 0.0f).endVertex();
        buffer.pos(minX, minY, maxZ).color(red, green, blue, alpha).endVertex();
        buffer.pos(maxX, maxY, maxZ).color(red, green, blue, 0.0f).endVertex();
        buffer.pos(maxX, minY, maxZ).color(red, green, blue, alpha).endVertex();
        buffer.pos(maxX, maxY, minZ).color(red, green, blue, 0.0f).endVertex();
        buffer.pos(maxX, minY, minZ).color(red, green, blue, alpha).endVertex();
        buffer.pos(maxX, minY, minZ).color(red, green, blue, 0.0f).endVertex();
        GraphicUtil.tessellator.draw();
    }

    public static void drawGradientBlockOutline(final BufferBuilder buffer, final double minX, final double minY, final double minZ, final double maxX, final double maxY, final double maxZ, final float red, final float green, final float blue, final float alpha, final Color endColor) {
        GlStateManager.disableCull();
        GlStateManager.disableAlpha();
        GlStateManager.shadeModel(7425);
        GraphicUtil.bufferbuilder.begin(3, DefaultVertexFormats.POSITION_COLOR);
        final float red2 = endColor.getRed() / 255.0f;
        final float green2 = endColor.getGreen() / 255.0f;
        final float blue2 = endColor.getBlue() / 255.0f;
        buffer.pos(minX, minY, minZ).color(red, green, blue, alpha).endVertex();
        buffer.pos(maxX, minY, minZ).color(red2, green2, blue2, alpha).endVertex();
        buffer.pos(maxX, minY, maxZ).color(red2, green2, blue2, alpha).endVertex();
        buffer.pos(minX, minY, maxZ).color(red, green, blue, alpha).endVertex();
        buffer.pos(minX, minY, minZ).color(red, green, blue, alpha).endVertex();
        buffer.pos(minX, maxY, minZ).color(red, green, blue, alpha).endVertex();
        buffer.pos(minX, maxY, maxZ).color(red, green, blue, alpha).endVertex();
        buffer.pos(minX, minY, maxZ).color(red, green, blue, alpha).endVertex();
        buffer.pos(maxX, minY, maxZ).color(red2, green2, blue2, alpha).endVertex();
        buffer.pos(maxX, maxY, maxZ).color(red2, green2, blue2, alpha).endVertex();
        buffer.pos(maxX, maxY, minZ).color(red2, green2, blue2, alpha).endVertex();
        buffer.pos(maxX, minY, minZ).color(red2, green2, blue2, alpha).endVertex();
        buffer.pos(maxX, maxY, minZ).color(red2, green2, blue2, alpha).endVertex();
        buffer.pos(minX, maxY, minZ).color(red, green, blue, alpha).endVertex();
        buffer.pos(minX, maxY, maxZ).color(red, green, blue, alpha).endVertex();
        buffer.pos(maxX, maxY, maxZ).color(red2, green2, blue2, alpha).endVertex();
        GraphicUtil.tessellator.draw();
        GlStateManager.enableCull();
        GlStateManager.enableAlpha();
        GlStateManager.shadeModel(7424);
    }
}
