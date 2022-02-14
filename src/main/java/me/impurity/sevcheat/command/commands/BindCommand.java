package me.impurity.sevcheat.command.commands;

import me.impurity.sevcheat.SevCheat;
import me.impurity.sevcheat.command.Command;
import me.impurity.sevcheat.module.Module;
import me.impurity.sevcheat.util.Utils;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.util.text.TextFormatting;
import org.lwjgl.input.Keyboard;

import java.util.concurrent.atomic.AtomicInteger;

public class BindCommand extends Command {
    public BindCommand() {
        super("bind");
    }

    @Override
    public void run(String[] args) {
        try {
            if (args.length == 0) {
                Utils.sendMessage("Specify a module to bind to a key");
                sendAllModules();
                return;
            }
            Module module = SevCheat.moduleManager.getModuleByName(args[0]);
            if (module == null) {
                Utils.sendMessage("That module does not exist");
                return;
            }
            String keyArg = args[1].toUpperCase();
            int key = Keyboard.getKeyIndex(keyArg);
            String keyName = Keyboard.getKeyName(key);
            module.setKeyBinding(new KeyBinding(module.getName(), key, "SevCheat"));
            Utils.sendMessage(TextFormatting.AQUA + "bound " + module.getName() + " to: " + keyName);
        } catch (Throwable t) {
            Utils.sendMessage("Specify a key to bind");
        }
    }

    private void sendAllModules() {
        StringBuilder builder = new StringBuilder();
        AtomicInteger index = new AtomicInteger();
        SevCheat.moduleManager.getModules().forEach(m -> {
            index.getAndIncrement();
            if (index.get() == SevCheat.moduleManager.getModules().size()) {
                builder.append(m.getName());
                return;
            }
            builder.append(m.getName()).append(", ");
        });
        Utils.sendMessage("Available Modules: " + builder);
    }
}
