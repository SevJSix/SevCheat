package me.impurity.sevcheat.command;

import me.impurity.sevcheat.command.commands.*;
import me.impurity.sevcheat.event.PacketSendingEvent;
import me.impurity.sevcheat.util.Utils;
import net.minecraft.network.play.client.CPacketChatMessage;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class CommandManager {

    private final List<Command> commands = new ArrayList<>();
    private String prefix = ".";
    public CommandManager() {
        MinecraftForge.EVENT_BUS.register(this);
        this.commands.add(new Auto32kChannelCommand());
        this.commands.add(new PrefixCommand(this));
        this.commands.add(new SuffixCommand());
        this.commands.add(new BindCommand());
        this.commands.add(new ToggleCommand());
        this.commands.add(new LazyCommand());
        this.commands.add(new FriendCommand(this));
        this.commands.add(new FakePlayerCommand());
        this.commands.add(new HoleEspCommand());
        this.commands.add(new Auto32kMode());
    }

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    @SubscribeEvent
    public void onChatSend(PacketSendingEvent event) {
        if (event.getPacket() instanceof CPacketChatMessage) {
            CPacketChatMessage packet = (CPacketChatMessage) event.getPacket();
            String message = packet.getMessage();
            if (!message.startsWith(prefix)) return;
            message = message.substring(message.indexOf(prefix));
            String[] rawArgs = message.split(" ");
            Command command = this.commands.stream().filter(c -> c.getName().equals(rawArgs[0].substring(1))).findFirst().orElse(null);
            if (command == null) {
                sendAvailableCommands();
                event.setCanceled(true);
                return;
            }
            if (rawArgs.length > 1) {
                String[] args = Arrays.copyOfRange(rawArgs, 1, rawArgs.length);
                command.run(args);
            } else {
                command.run(new String[]{});
            }
            event.setCanceled(true);
        }
    }

    private void sendAvailableCommands() {
        StringBuilder builder = new StringBuilder();
        AtomicInteger index = new AtomicInteger();
        commands.forEach(c -> {
            index.getAndIncrement();
            if (index.get() == commands.size()) {
                builder.append(c.getName());
                return;
            }
            builder.append(c.getName()).append(", ");
        });
        Utils.sendMessage("Available Commands: " + builder);
    }
}
