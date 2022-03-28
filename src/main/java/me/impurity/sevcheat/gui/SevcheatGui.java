package me.impurity.sevcheat.gui;

import me.impurity.sevcheat.util.Utils;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.util.text.TextFormatting;

import java.io.IOException;

public class SevcheatGui extends GuiScreen {

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        super.drawScreen(mouseX, mouseY, partialTicks);
        this.drawDefaultBackground();
        ScaledResolution sr = new ScaledResolution(mc);
        String guiTitle = "SevCheat ClickGui (Work in Progress)";
        drawStringWithShadow(guiTitle, (sr.getScaledWidth() / 2) - getStringWidth(guiTitle), 10);
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        super.mouseClicked(mouseX, mouseY, mouseButton);
    }

    @Override
    protected void mouseReleased(int mouseX, int mouseY, int state) {
        super.mouseReleased(mouseX, mouseY, state);
    }

    public void drawStringWithShadow(String text, int x, int y) {
        mc.fontRenderer.drawStringWithShadow(text, x, y, Utils.rainbow());
    }

    public int getStringWidth(String str) {
        return str.toCharArray().length;
    }
}
