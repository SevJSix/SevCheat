package me.impurity.sevcheat.command;

import me.impurity.sevcheat.SevCheat;
import me.impurity.sevcheat.module.Module;
import me.impurity.sevcheat.util.Utils;
import net.minecraft.util.text.TextFormatting;

import java.util.concurrent.atomic.AtomicInteger;

public abstract class Command {

    private final String name;
    public Command(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public abstract void run(String[] args);

    public void sendAllModules() {
        StringBuilder builder = new StringBuilder();
        AtomicInteger index = new AtomicInteger();
        SevCheat.moduleManager.getModules().forEach(m -> {
            index.getAndIncrement();
            if (index.get() == SevCheat.moduleManager.getModules().size()) {
                if (!m.isEnabled()) {
                    builder.append(TextFormatting.RED).append(m.getName());
                } else {
                    builder.append(TextFormatting.GREEN).append(m.getName());
                }
                return;
            }
            if (!m.isEnabled()) {
                builder.append(TextFormatting.RED).append(m.getName()).append(TextFormatting.WHITE).append(", ");
            } else {
                builder.append(TextFormatting.GREEN).append(m.getName()).append(TextFormatting.WHITE).append(", ");
            }
        });
        Utils.sendMessageWithoutPrefix("Modules: " + builder);
    }
}
