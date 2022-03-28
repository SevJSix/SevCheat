package me.impurity.sevcheat.module;

import me.impurity.sevcheat.event.PacketRecievingEvent;
import me.impurity.sevcheat.event.TotemPopEvent;
import me.impurity.sevcheat.module.modules.combat.*;
import me.impurity.sevcheat.module.modules.exploit.SecretClose;
import me.impurity.sevcheat.module.modules.exploit.Velocity;
import me.impurity.sevcheat.module.modules.hud.ClickGui;
import me.impurity.sevcheat.module.modules.hud.HUD;
import me.impurity.sevcheat.module.modules.movement.ReverseStep;
import me.impurity.sevcheat.module.modules.player.ChatSuffix;
import me.impurity.sevcheat.module.modules.player.FakePlayer;
import me.impurity.sevcheat.module.modules.render.BlockHighlight;
import me.impurity.sevcheat.module.modules.render.HoleEsp;
import me.impurity.sevcheat.util.McWrapper;
import me.impurity.sevcheat.util.Utils;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.play.server.SPacketEntityStatus;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.lwjgl.input.Keyboard;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class ModuleManager implements McWrapper {

    private final List<Module> modules;

    public ModuleManager() {
        MinecraftForge.EVENT_BUS.register(this);
        modules = new ArrayList<>();
        addModule(new HUD());
        addModule(new ServersideAuto32k());
        addModule(new SecretClose());
        addModule(new ChatSuffix());
        addModule(new ClickGui());
        addModule(new TotemPopNotify());
        addModule(new Aura32k());
        addModule(new AutoTotem());
        addModule(new Velocity());
        addModule(new BlockHighlight());
        addModule(new FakePlayer());
        addModule(new ReverseStep());
        addModule(new HoleEsp());
        addModule(new BedAura());
        addModule(new AutoTrapBed());
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
    public void onTick(TickEvent.ClientTickEvent event) {
        modules.forEach(module -> {
            if (module.isEnabled()) {
                module.onTick();
            }
        });
    }

    @SubscribeEvent
    public void onFastTick(TickEvent event) {
        getActiveModules().forEach(m -> {
            if (mc.world != null && mc.player != null) {
                try {
                    m.onQuickUpdate();
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @SubscribeEvent
    public void onPacket(PacketRecievingEvent event) {
        if (mc.world == null) return;
        if (event.getPacket() instanceof SPacketEntityStatus) {
            SPacketEntityStatus packet = (SPacketEntityStatus) event.getPacket();
            if (packet.getOpCode() == 35) {
                Entity entity = packet.getEntity(Minecraft.getMinecraft().world);
                if (!(entity instanceof EntityPlayer) || entity.getName().equalsIgnoreCase(Minecraft.getMinecraft().player.getName()))
                    return;
                MinecraftForge.EVENT_BUS.post(new TotemPopEvent((EntityPlayer) entity));
                if (getModuleByName("TotemPopNotify").isEnabled()) {
                    Utils.sendMessageWithoutPrefix(entity.getName() + " popped a totem!");
                }
            }
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

    @SubscribeEvent
    public void onRender3d(RenderWorldLastEvent event) {
        getActiveModules().forEach(m -> {
            if (mc.world != null && mc.player != null) {
                try {
                    m.onRender3d();
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
