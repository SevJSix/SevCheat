package me.impurity.sevcheat.module.modules.render;

import me.impurity.sevcheat.module.Category;
import me.impurity.sevcheat.module.Module;
import me.impurity.sevcheat.util.GraphicUtil;
import me.impurity.sevcheat.util.Utils;

import java.awt.*;

public class BlockHighlight extends Module {
    public BlockHighlight() {
        super("BlockHighlight", Category.RENDER, "draws box around block ur looking at");
    }

    @Override
    public void onRender3d() {
        try {
            if (mc.world != null && mc.objectMouseOver != null && mc.objectMouseOver.getBlockPos() != null && mc.world.getBlockState(mc.objectMouseOver.getBlockPos()).getMaterial().isSolid()) {
                GraphicUtil.drawBlockOutline(mc.objectMouseOver.getBlockPos(), 0.0, new Color(Utils.rainbow()), new Color(Utils.rainbow()));
            }
        } catch (NullPointerException ignored) {
        }
    }
}
