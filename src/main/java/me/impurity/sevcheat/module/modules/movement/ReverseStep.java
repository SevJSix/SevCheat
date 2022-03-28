package me.impurity.sevcheat.module.modules.movement;

import me.impurity.sevcheat.module.Category;
import me.impurity.sevcheat.module.Module;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;

public class ReverseStep extends Module {
    public ReverseStep() {
        super("ReverseStep", Category.MOVEMENT, "step but in reverse");
    }

    @Override
    public void onTick() {
        if (mc.world == null) return;
        if (mc.player.isInLava() || mc.player.isInWater() || !mc.player.onGround) return;
        IBlockState touchState = mc.world.getBlockState(new BlockPos(mc.player.posX, mc.player.posY, mc.player.posZ).down(2));
        IBlockState touchState2 = mc.world.getBlockState(new BlockPos(mc.player.posX, mc.player.posY, mc.player.posZ).down(3));
        if (isBlockSolid(touchState)) {
            mc.player.motionY -= 1.0;
        }
        if (isBlockSolid(touchState2)) {
            mc.player.motionY -= 1.0;
        }
    }

    private boolean isBlockSolid(IBlockState state) {
        return state.getMaterial().isSolid() && state.isFullBlock();
    }
}
