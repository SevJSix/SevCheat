package me.impurity.sevcheat.module.modules.render;

import me.impurity.sevcheat.SevCheat;
import me.impurity.sevcheat.module.Category;
import me.impurity.sevcheat.module.Module;
import me.impurity.sevcheat.util.Utils;
import net.minecraft.client.gui.ScaledResolution;
import org.lwjgl.input.Keyboard;

public class HUD extends Module {

    public HUD() {
        super("HUD", Category.CLIENT, "Module enabled list cope larp");
    }

    @Override
    public void onRender2d() {
        ScaledResolution res = new ScaledResolution(mc);
        mc.fontRenderer.drawStringWithShadow("SevCheat b1", 1, 1, Utils.rainbow());
        int y = 2;
        for (Module module : SevCheat.moduleManager.getActiveModules()) {
            if (!module.getName().equalsIgnoreCase("")) {
                mc.fontRenderer.drawStringWithShadow(module.getName(), res.getScaledWidth() - mc.fontRenderer.getStringWidth(module.getName()) - 2, y, Utils.rainbow());
                y += mc.fontRenderer.FONT_HEIGHT;
            }
        }
    }
}
