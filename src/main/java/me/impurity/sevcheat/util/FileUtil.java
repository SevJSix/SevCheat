package me.impurity.sevcheat.util;

import me.impurity.sevcheat.SevCheat;
import me.impurity.sevcheat.module.Module;

import java.io.*;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

public class FileUtil {

    public static File sevcheat;
    public static File configs;
    public static List<String> friends = new ArrayList<>();
    public static List<Module> enabledModules = new ArrayList<>();

    public static void init() {
        makeDirs();
        loadFriends();
        loadModules();
    }

    public static void makeDirs() {
        sevcheat = new File("SevCheat");
        if (!sevcheat.exists()) sevcheat.mkdirs();
        configs = new File("SevCheat/Configurations");
        if (!configs.exists()) configs.mkdirs();
    }

    public static void saveFriends() {
        try {
            File friends = new File(configs.getAbsolutePath(), "Friends.txt");
            BufferedWriter bw = new BufferedWriter(new FileWriter(friends));
            FileUtil.friends.forEach(s -> {
                try {
                    bw.write(s);
                    bw.write("\r\n");
                } catch (Throwable t) {
                    t.printStackTrace();
                }
            });
            bw.close();
        } catch (Throwable t) {
            t.printStackTrace();
        }
    }

    public static void loadFriends() {
        try {
            File friends = new File(configs.getAbsolutePath(), "Friends.txt");
            boolean exists = checkFileExistence(friends);
            if (!exists) return;
            BufferedReader br = new BufferedReader(new FileReader(friends));
            Files.readAllLines(friends.toPath()).forEach(FileUtil::addFriend);
            br.close();
        } catch (Throwable t) {
            t.printStackTrace();
        }
    }

    public static void saveModules() {
        try {
            File modules = new File(configs.getAbsolutePath(), "Modules.txt");
            BufferedWriter bw = new BufferedWriter(new FileWriter(modules));
            for (Module module : SevCheat.moduleManager.getModules()) {
                bw.write(module.getName() + ":");
                if (module.isEnabled()) {
                    bw.write("true");
                } else bw.write("false");
                bw.write("\r\n");
            }
            bw.close();
        } catch (Throwable t) {
            t.printStackTrace();
        }
    }

    public static void loadModules() {
        try {
            File modules = new File(configs.getAbsolutePath(), "Modules.txt");
            boolean exists = checkFileExistence(modules);
            if (!exists) return;
            BufferedReader br = new BufferedReader(new FileReader(modules));
            List<String> allLines = Files.readAllLines(modules.toPath());
            for (String line : allLines) {
                String[] colon = line.split(":");
                Module module = SevCheat.moduleManager.getModuleByName(colon[0]);
                if (Boolean.parseBoolean(colon[1])) module.enable();
            }
            br.close();
        } catch (Throwable t) {
            t.printStackTrace();
        }
    }

    public static void addFriend(String name) {
        friends.add(name);
    }

    public static void removeFriend(String name) {
        friends.remove(name);
    }

    public static boolean isFriend(String name) {
        return friends.contains(name);
    }

    public static boolean checkFileExistence(File file) {
        if (file.exists()) {
            return true;
        } else {
            try {
                file.createNewFile();
            } catch (Throwable t) {
                t.printStackTrace();
            }
            return false;
        }
    }
}
