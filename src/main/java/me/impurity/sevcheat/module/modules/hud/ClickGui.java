package me.impurity.sevcheat.module.modules.hud;

import me.impurity.sevcheat.gui.SevcheatGui;
import me.impurity.sevcheat.module.Category;
import me.impurity.sevcheat.module.Module;

public class ClickGui extends Module {
    public ClickGui() {
        super("ClickGui", Category.HUD, "show all modules in the clickgui");
    }

    @Override
    public void onEnable() {
        mc.displayGuiScreen(new SevcheatGui());
        this.disable();
    }
}
