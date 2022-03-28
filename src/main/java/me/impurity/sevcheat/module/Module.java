package me.impurity.sevcheat.module;

import me.impurity.sevcheat.util.FileUtil;
import me.impurity.sevcheat.util.McWrapper;
import me.impurity.sevcheat.util.MessageUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.client.registry.ClientRegistry;

public abstract class Module implements McWrapper {

    private final String name;
    private final Category category;
    private final String description;
    public Minecraft mc = Minecraft.getMinecraft();
    private boolean enabled;
    private KeyBinding keyBinding;
    private boolean isKeyDown;

    public Module(final String name, final Category category, final String description) {
        this.name = name;
        this.category = category;
        this.description = description;
        this.isKeyDown = false;
        ClientRegistry.registerKeyBinding(this.keyBinding = new KeyBinding(name, 0, "SevCheat"));
    }

    public boolean isKeyDown() {
        return isKeyDown;
    }

    public void setKeyDown(boolean keyDown) {
        isKeyDown = keyDown;
    }

    public KeyBinding getKeyBinding() {
        return keyBinding;
    }

    public void setKeyBinding(KeyBinding keyBinding) {
        this.keyBinding = keyBinding;
    }

    public void toggle() {
        if (isEnabled()) {
            this.disable();
            return;
        }
        if (!isEnabled()) {
            this.enable();
        }
    }

    public void onEnable() {
    }

    public void onDisable() {
    }

    public void onTick() {
    }

    public void onQuickUpdate() {
    }

    public void onRender2d() {
    }

    public void onRender3d() {
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public void enable() {
        this.setEnabled(true);
        MinecraftForge.EVENT_BUS.register(this);
        this.onEnable();
        FileUtil.saveModules();
        if (mc.world != null) {
            MessageUtil.sendMessage(TextFormatting.GRAY + "{}" + TextFormatting.GREEN + " enabled.", name);
        }
    }

    public void disable() {
        this.setEnabled(false);
        MinecraftForge.EVENT_BUS.unregister(this);
        this.onDisable();
        FileUtil.saveModules();
        if (mc.world != null) {
            MessageUtil.sendMessage(TextFormatting.GRAY + "{}" + TextFormatting.RED + " disabled.", name);
        }
    }

    public String getName() {
        return name;
    }
}
