package me.impurity.sevcheat.module.modules.combat;

import com.mojang.realmsclient.gui.ChatFormatting;
import me.impurity.sevcheat.SevCheat;
import me.impurity.sevcheat.module.Category;
import me.impurity.sevcheat.module.Module;
import me.impurity.sevcheat.util.*;
import net.minecraft.block.BlockDispenser;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.gui.inventory.GuiDispenser;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.ClickType;
import net.minecraft.inventory.ContainerHopper;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.client.CPacketHeldItemChange;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

import java.awt.*;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class Auto32k extends Module {

    public static boolean autoPlace = false;
    public static boolean renderPlace = false;
    public static Mode mode = Mode.DISPENSER;
    private final TimerUtil timer = new TimerUtil();
    private final TimerUtil timer2 = new TimerUtil();
    private int stage;
    private BlockPos placePos;
    // inventory slot locations
    private int obsidianSlot;
    private int dispenserSlot;
    private int redstoneSlot;
    private int shulkerSlot;
    private int hopperSlot;
    // block place positions
    private BlockPos obsidianPos;
    private BlockPos dispenserPos;
    private BlockPos redstonePos;
    private BlockPos hopperPos;
    private EnumFacing facing;
    private int openAttempts;

    public Auto32k() {
        super("Auto32k", Category.COMBAT, "automatically places 32ks fully clientside");
    }

    @Override
    public void onEnable() {
        if (SevCheat.moduleManager.getModuleByName("SecretClose").isEnabled()) {
            SevCheat.moduleManager.getModuleByName("SecretClose").disable();
        }
        timer.reset();
        timer2.reset();
        this.stage = -1;
        this.openAttempts = 0;
        this.obsidianSlot = PlayerUtil.find(Blocks.OBSIDIAN);
        this.dispenserSlot = PlayerUtil.find(Blocks.DISPENSER);
        this.redstoneSlot = PlayerUtil.find(Blocks.REDSTONE_BLOCK);
        this.shulkerSlot = PlayerUtil.findShulker();
        this.hopperSlot = PlayerUtil.find(Blocks.HOPPER);
        this.facing = mc.player.getHorizontalFacing();
        this.placePos = (autoPlace) ? AutoUtil.getAuto32kPlacePos(this.facing) : mc.objectMouseOver.getBlockPos();
        if (placePos == null) {
            Utils.sendMessage(ChatFormatting.RED + "couldn't find a proper place position!");
            disable();
            return;
        }
        if (AutoUtil.isAir(placePos)) {
            Utils.sendMessage(ChatFormatting.RED + "cant place on air!");
            disable();
            return;
        }
        if (obsidianSlot == -1 && mode == Mode.DISPENSER) {
            Utils.sendMessage(ChatFormatting.RED + "missing obsidian from hotbar");
            disable();
        } else if (dispenserSlot == -1 && mode == Mode.DISPENSER) {
            Utils.sendMessage(ChatFormatting.RED + "missing dispenser from hotbar");
            disable();
        } else if (redstoneSlot == -1 && mode == Mode.DISPENSER) {
            Utils.sendMessage(ChatFormatting.RED + "missing redstone from hotbar");
            disable();
        } else if (shulkerSlot == -1) {
            Utils.sendMessage(ChatFormatting.RED + "missing shulker from hotbar");
            disable();
        } else if (hopperSlot == -1) {
            Utils.sendMessage(ChatFormatting.RED + "missing hopper from hotbar");
            disable();
        } else {
            this.obsidianPos = new BlockPos(placePos.getX(), placePos.getY() + 1, placePos.getZ());
            this.dispenserPos = new BlockPos(placePos.getX(), placePos.getY() + 2, placePos.getZ());
            this.redstonePos = new BlockPos(placePos.getX(), placePos.getY() + 3, placePos.getZ());
            this.stage = 0;
        }
    }

    @Override
    public void onUpdate() {
        if (mc.world == null || mc.player == null) return;
        if (stage == -1) return;
        if (timer.hasReached(2000)) {
            timer.reset();
            disable();
            return;
        }

        if (mode == Mode.HOPPER && stage == 0) {
            switchSlot(hopperSlot);
            this.hopperPos = this.obsidianPos;
            BlockUtil.placeBlock(this.hopperPos, EnumHand.MAIN_HAND, true, false);
            if (mc.world.getBlockState(this.hopperPos).getBlock().equals(Blocks.HOPPER)) {
                mc.player.connection.sendPacket(new CPacketPlayer.Rotation(AutoUtil.getYaw(this.facing), 0.0f, mc.player.onGround));
                switchSlot(shulkerSlot);
                BlockUtil.placeBlock(AutoUtil.clone(this.hopperPos, 0, 1, 0), EnumHand.MAIN_HAND, true, false);
                BlockUtil.clickBlock(this.hopperPos);
                this.stage = 5;
                return;
            } else {
                Utils.sendMessage(ChatFormatting.RED + "Hopper fucked up");
                disable();
            }
        }

        if (stage == 0 && mode == Mode.DISPENSER) {
            switchSlot(obsidianSlot);
            BlockUtil.placeBlock(this.obsidianPos, EnumHand.MAIN_HAND, true, false);
            if (mc.world.getBlockState(this.obsidianPos).getBlock().equals(Blocks.OBSIDIAN)) {
                this.stage = 1;
            } else {
                Utils.sendMessage(ChatFormatting.RED + "Obsidian fucked up");
                disable();
            }
            return;
        }

        if (stage == 1 && mode == Mode.DISPENSER) {
            switchSlot(dispenserSlot);
            mc.player.connection.sendPacket(new CPacketPlayer.Rotation(AutoUtil.getYaw(this.facing), 0.0f, mc.player.onGround));
            BlockUtil.placeBlock(this.dispenserPos, EnumHand.MAIN_HAND, false, false);
            if (mc.world.getBlockState(this.dispenserPos).getBlock().equals(Blocks.DISPENSER)) {
                BlockUtil.clickBlock(this.dispenserPos);
                this.stage = 2;
            } else {
                Utils.sendMessage(ChatFormatting.RED + "Dispenser fucked up :(");
                disable();
            }
            return;
        }

        if (stage == 2 && mode == Mode.DISPENSER) {
            if (mc.world.getBlockState(this.dispenserPos).getBlock().equals(Blocks.DISPENSER)) {
                if (mc.currentScreen instanceof GuiDispenser) {
                    mc.playerController.windowClick(mc.player.openContainer.windowId, 0, this.shulkerSlot, ClickType.SWAP, mc.player);
                    if (mc.player.inventory.getStackInSlot(this.shulkerSlot).isEmpty()) {
                        mc.player.closeScreen();
                        this.stage = 3;
                        return;
                    }
                }
            }
        }

        if (stage == 3 && mode == Mode.DISPENSER) {
            switchSlot(redstoneSlot);
            BlockUtil.placeBlock(this.redstonePos, EnumHand.MAIN_HAND, true, false);
            if (mc.currentScreen != null) {
                mc.player.closeScreen();
            }
            this.stage = 4;
            return;
        }

        if (stage == 4 && mode == Mode.DISPENSER) {
            switchSlot(hopperSlot);
            EnumFacing dir = this.facing.getOpposite();
            this.hopperPos = AutoUtil.getHopperPos(dispenserPos, dir);
            BlockUtil.placeBlock(this.hopperPos, EnumHand.MAIN_HAND, true, false);
            this.stage = 5;
            return;
        }

        if (stage == 5) {
            mc.player.inventory.currentItem = (mc.player.inventory.getFirstEmptyStack() != -1) ? mc.player.inventory.getFirstEmptyStack() : 8;
            if (mc.player.openContainer instanceof ContainerHopper) {
                ItemStack item = mc.player.openContainer.getSlot(0).getStack();
                if (item.isEmpty()) return;
                mc.playerController.windowClick(mc.player.openContainer.windowId, 0, mc.player.inventory.currentItem, ClickType.SWAP, mc.player);
                if (!SevCheat.moduleManager.getModuleByName("SecretClose").isEnabled())
                    SevCheat.moduleManager.getModuleByName("SecretClose").enable();
                mc.player.closeScreen();
                this.disable();
            } else {
                openAttempts++;
                if (openAttempts > 10) {
                    disable();
                    return;
                }
                BlockUtil.clickBlock(this.hopperPos);
            }
        }
    }

    @Override
    public void onRender3d() {
        if (renderPlace) {
            if (this.obsidianPos != null) {
                GraphicUtil.drawBlockOutline(this.obsidianPos, 1.0D, new Color(1.0F, 0.0F, 0.5F, 1.0F), new Color(1.0F, 0.0F, 0.5F, 1.0F));
                GraphicUtil.drawFilledBox(this.obsidianPos, 1.0D, new Color(1.0F, 0.0F, 0.4F));
            }
            if (this.dispenserPos != null) {
                GraphicUtil.drawBlockOutline(AutoUtil.clone(dispenserPos, 0, -1, 0), 1.0D, new Color(1.0F, 0.0F, 0.5F, 1.0F), new Color(1.0F, 0.0F, 0.5F, 1.0F));
                GraphicUtil.drawFilledBox(AutoUtil.clone(dispenserPos, 0, -1, 0), 1.0D, new Color(1.0F, 0.0F, 0.4F));
            }
            if (this.redstonePos != null) {
                GraphicUtil.drawBlockOutline(AutoUtil.clone(redstonePos, 0, -1, 0), 1.0D, new Color(1.0F, 0.0F, 0.5F, 1.0F), new Color(1.0F, 0.0F, 0.5F, 1.0F));
                GraphicUtil.drawFilledBox(AutoUtil.clone(redstonePos, 0, -1, 0), 1.0D, new Color(1.0F, 0.0F, 0.4F));
            }
            if (this.hopperPos != null) {
                GraphicUtil.drawBlockOutline(this.hopperPos, 1.0D, new Color(1.0F, 0.0F, 0.5F, 1.0F), new Color(1.0F, 0.0F, 0.5F, 1.0F));
                GraphicUtil.drawFilledBox(this.hopperPos, 1.0D, new Color(1.0F, 0.0F, 0.4F));

            }
        }
    }

    private void switchSlot(int slot) {
        if (mc.player.inventory.currentItem == slot) return;
        mc.player.connection.sendPacket(new CPacketHeldItemChange(slot));
        mc.player.inventory.currentItem = slot;
        mc.playerController.updateController();
    }

    @Override
    public void onDisable() {
        reset();
    }

    private void reset() {
        this.stage = -1;
        this.placePos = null;
        this.obsidianSlot = -1;
        this.dispenserSlot = -1;
        this.redstoneSlot = -1;
        this.shulkerSlot = -1;
        this.hopperSlot = -1;
        this.facing = null;
        this.obsidianPos = null;
        this.dispenserPos = null;
        this.redstonePos = null;
        this.hopperPos = null;
        this.openAttempts = 0;
        timer.reset();
    }

    public enum Mode {
        DISPENSER,
        HOPPER;
    }

    public static class AutoUtil implements McWrapper {


        public static BlockPos getHopperPos(BlockPos dispenserPos, EnumFacing facing) {
            if (mc.world.getBlockState(dispenserPos).getBlock().equals(Blocks.DISPENSER)) {
                BlockDispenser dispenser = (BlockDispenser) mc.world.getBlockState(dispenserPos).getBlock();
                try {
                    EnumFacing dispenserFacing = getDirection(mc.player.world, dispenserPos, dispenser.getStateForPlacement(mc.world, dispenserPos, facing, 0.0f, 0.0f, 0.0f, dispenser.getMetaFromState(dispenser.getDefaultState().getActualState(mc.world, dispenserPos)), mc.player, EnumHand.MAIN_HAND));
                    System.out.println(dispenserFacing);
                    return BlockUtil.cloneBlockPosDownOne(dispenserPos, dispenserFacing);
                } catch (Throwable t) {
                    t.printStackTrace();
                }
            }
            return BlockUtil.cloneBlockPosDownOne(dispenserPos, facing);
        }

        public static boolean isValidPlaceLocation(BlockPos origPos, EnumFacing facing) {
            BlockPos obsidian = clone(origPos, 0, 1, 0);
            BlockPos dispenser = clone(origPos, 0, 2, 0);
            BlockPos redstone = clone(origPos, 0, 3, 0);
            BlockPos hopper = getHopperPos(dispenser, facing);
            BlockPos shulker = clone(hopper, 0, 1, 0);
            AxisAlignedBB axis = new AxisAlignedBB(origPos.getX() + 1.5, origPos.getY() + 1.5, origPos.getZ() + 1.5, origPos.getX() - 1.5, origPos.getY() - 1.5, origPos.getZ() - 1.5);
            return mc.world != null && mc.player != null && isPosInFov(origPos)
                    && !(Math.abs(mc.player.getPosition().getY() - origPos.getY()) > 2)
                    && !isAir(origPos) && mc.world.getBlockState(origPos).isFullBlock()
                    && isAir(obsidian) && !mc.world.getBlockState(obsidian).getBlock().equals(Blocks.REDSTONE_BLOCK)
                    && isAir(dispenser)
                    && isAir(redstone)
                    && isAir(hopper)
                    && isAir(shulker)
                    && mc.world.getEntitiesWithinAABB(EntityPlayer.class, axis).isEmpty();
        }

        public static BlockPos clone(BlockPos pos, int x, int y, int z) {
            BlockPos cloneOfPos = new BlockPos(pos.getX(), pos.getY(), pos.getZ());
            return cloneOfPos.add(x, y, z);
        }

        public static boolean isAir(BlockPos pos) {
            return mc.world.getBlockState(pos).getBlock().equals(Blocks.AIR);
        }

        private static BlockPos getAuto32kPlacePos(EnumFacing facing) {
            BlockPos pos = mc.player.getPosition();
            EnumFacing opposite = facing.getOpposite();
            int range = 3;
            List<BlockPos> possiblePositions = new ArrayList<>();
            for (int x = pos.getX() - range; x <= pos.getX() + range; x++) {
                for (int y = pos.getY() - range; y <= pos.getY() + range; y++) {
                    for (int z = pos.getZ() - range; z <= pos.getZ() + range; z++) {
                        BlockPos attemptPos = new BlockPos(x, y, z);
                        if (isValidPlaceLocation(attemptPos, opposite)) possiblePositions.add(attemptPos);
                    }
                }
            }
            return possiblePositions.get(ThreadLocalRandom.current().nextInt(0, possiblePositions.size()));
        }

        public static Boolean isPosInFov(BlockPos pos) {
            int direction4D = getDirection4D();
            if (direction4D == 0 && (double) pos.getZ() - mc.player.getPositionVector().z < 0.0) {
                return false;
            }
            if (direction4D == 1 && (double) pos.getX() - mc.player.getPositionVector().x > 0.0) {
                return false;
            }
            if (direction4D == 2 && (double) pos.getZ() - mc.player.getPositionVector().z > 0.0) {
                return false;
            }
            return direction4D != 3 || (double) pos.getX() - mc.player.getPositionVector().x >= 0.0;
        }

        public static int getDirection4D() {
            return MathHelper.floor((double) (mc.player.rotationYaw * 4.0f / 360.0f) + 0.5) & 3;
        }

        public static float getYaw(EnumFacing facing) {
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

        public static EnumFacing getDirection(World worldIn, BlockPos pos, IBlockState state) throws NoSuchFieldException, IllegalAccessException {
            BlockDispenser dispenser = (BlockDispenser) worldIn.getBlockState(pos).getBlock();
            Field directionF = BlockDispenser.class.getDeclaredField("FACING");
            directionF.setAccessible(true);
            PropertyDirection FACING = (PropertyDirection) directionF.get(dispenser);
            return state.getValue(FACING);
        }
    }
}


