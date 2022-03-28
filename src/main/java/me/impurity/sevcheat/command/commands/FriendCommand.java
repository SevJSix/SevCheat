package me.impurity.sevcheat.command.commands;

import me.impurity.sevcheat.command.Command;
import me.impurity.sevcheat.command.CommandManager;
import me.impurity.sevcheat.util.FileUtil;
import me.impurity.sevcheat.util.Utils;
import net.minecraft.client.Minecraft;
import net.minecraft.util.text.TextFormatting;

public class FriendCommand extends Command {

    CommandManager manager;

    public FriendCommand(CommandManager manager) {
        super("friend");
        this.manager = manager;
    }

    @Override
    public void run(String[] args) {
        try {
            if (args.length > 1) {
                String name = args[1];
                if (name.equalsIgnoreCase(Minecraft.getMinecraft().player.getName())) {
                    Utils.sendMessage("You cannot friend yourself");
                    return;
                }
                if (FileUtil.isFriend(name) && !args[0].equalsIgnoreCase("del")) {
                    Utils.sendMessage(name + TextFormatting.GREEN + " is already a friend");
                    return;
                }
                switch (args[0]) {
                    case "add":
                        FileUtil.addFriend(name);
                        FileUtil.saveFriends();
                        Utils.sendMessage(name + TextFormatting.GREEN + " is now a friend");
                        break;
                    case "del":
                        FileUtil.removeFriend(name);
                        FileUtil.saveFriends();
                        Utils.sendMessage(name + TextFormatting.RED + " is now not a friend");
                        break;
                }
            } else if (FileUtil.isFriend(args[0])) {
                Utils.sendMessage(args[0] + TextFormatting.GREEN + " is a friend");
            } else {
                Utils.sendMessage(args[0] + TextFormatting.RED + " is not a friend");
            }
        } catch (Throwable ignored) {
            Utils.sendMessage("Please specify a player to add to, or remove from your friends list. (" + manager.getPrefix() + "friend add <playername>," +
                    " " + manager.getPrefix() + "friend del <playername>)");
        }
    }
}
