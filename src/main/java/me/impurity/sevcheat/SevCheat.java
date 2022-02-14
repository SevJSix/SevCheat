package me.impurity.sevcheat;

import me.impurity.sevcheat.command.CommandManager;
import me.impurity.sevcheat.module.ModuleManager;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

@Mod(
        modid = SevCheat.MOD_ID,
        name = SevCheat.MOD_NAME,
        version = SevCheat.VERSION
)
public class SevCheat {

    public static final String MOD_ID = "sevcheat";
    public static final String MOD_NAME = "SevCheat";
    public static final String VERSION = "1.1";
    public static ModuleManager moduleManager;
    @Mod.Instance(MOD_ID)
    public static SevCheat INSTANCE;

    @Mod.EventHandler
    public void preinit(FMLPreInitializationEvent event) {

    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        moduleManager = new ModuleManager();
        new CommandManager();
    }

    @Mod.EventHandler
    public void postinit(FMLPostInitializationEvent event) {

    }
}
