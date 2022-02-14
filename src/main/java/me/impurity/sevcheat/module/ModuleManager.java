package me.impurity.sevcheat.module;

import me.impurity.sevcheat.module.modules.combat.ServersideAuto32k;
import me.impurity.sevcheat.module.modules.exploit.SecretClose;
import me.impurity.sevcheat.module.modules.player.ChatSuffix;
import me.impurity.sevcheat.module.modules.render.HUD;
import me.impurity.sevcheat.util.Utils;
import net.minecraft.client.Minecraft;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.lwjgl.input.Keyboard;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class ModuleManager {

    private final List<Module> modules;

    public ModuleManager() {
        MinecraftForge.EVENT_BUS.register(this);
        modules = new ArrayList<>();
        addModule(new HUD());
        addModule(new ServersideAuto32k());
        addModule(new SecretClose());
        addModule(new ChatSuffix());
        getModuleByName("HUD").setEnabled(true);
    }

    public List<Module> getModules() {
        return modules;
    }

    public List<Module> getActiveModules() {
        List<Module> output = new ArrayList<>();
        for (Module module : getModules()) {
            if (module.isEnabled()) {
                output.add(module);
            }
        }
        output.sort((Comparator.comparingInt(o2 -> -o2.getName().length())));
        return output;
    }

    @SubscribeEvent
    public void onTick(TickEvent.ClientTickEvent event) {
        modules.forEach(module -> {
            if (module.isEnabled()) {
                module.onTick();
            }
        });
    }

    public Module getModuleByName(String name) {
        for (Module module : modules) {
            if (module.getName().equalsIgnoreCase(name)) {
                return module;
            }
        }
        return null;
    }

    public void addModule(Module module) {
        modules.add(module);
    }

    @SubscribeEvent
    public void onKeyPress(InputEvent.KeyInputEvent event) {
        if (Minecraft.getMinecraft().currentScreen == null) {
            modules.forEach(module -> {
                try {
                    if (Keyboard.isKeyDown(module.getKeyBinding().getKeyCode()) && !module.isKeyDown()) {
                        module.setKeyDown(true);
                        module.toggle();
                    } else if (!Keyboard.isKeyDown(module.getKeyBinding().getKeyCode()) && module.isKeyDown()) {
                        module.setKeyDown(false);
                    }
                } catch (Throwable ignored) {
                }
            });
        }
    }

    @SubscribeEvent
    public void onRenderGameOverlay(RenderGameOverlayEvent.Text event) {
        modules.forEach(module -> {
            if (module.isEnabled()) {
                module.onRender2d();
            }
        });
    }
}
